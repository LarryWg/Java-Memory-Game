package memorygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceMemory extends JPanel implements ActionListener {
    private static final int BUTTON_SIZE = 125;
    private static final int MARGIN = 10;
    private static final String GAME_MODE = "sequence_memory";
    private final HighScoreRepository highScoreRepo = new HighScoreRepository();
    private final String playerName;

    private JPanel sequencePanel;
    private final List<Integer> pattern = new ArrayList<>();
    private final List<JButton> buttons = new ArrayList<>();
    private final List<Integer> buttonIds = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private int buttonIndex;
    private boolean displayingPattern;
    public int level;

    public SequenceMemory(String playerName) {
        this.playerName = playerName != null ? playerName : "Anonymous";
        setSize(1280, 720);
        buttonIndex = 0;
        displayingPattern = false;
        level = 1;

        sequencePanel = new JPanel(new GridBagLayout());
        sequencePanel.setOpaque(false);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        JLabel levelLabel = new JLabel("Level: " + level);
        levelLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 31));
        levelLabel.setForeground(new Color(0xaacfed));
        labelConstraints.gridx = 1;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 1;
        labelConstraints.insets = new Insets(-120, -35, 0, 0);
        sequencePanel.add(levelLabel, labelConstraints);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(MARGIN + 70, MARGIN, MARGIN - 70, MARGIN);
        for (int i = 0; i < 9; i++) {
            JButton button = new RoundedButton("", 20);
            button.setActionCommand(String.valueOf(i + 1));
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            c.gridx = i % 3;
            c.gridy = i / 3;
            button.setBackground(new Color(0x2573c1));
            button.setBorderPainted(false);
            button.setFocusPainted(true);
            sequencePanel.add(button, c);
            buttons.add(button);
        }

        setOpaque(false);
        setLayout(new BorderLayout());
        setVisible(true);
        add(sequencePanel);
        start();
    }

    public void start() {
        level = 1;
        SwingUtilities.invokeLater(() -> {
            JLabel levelLabel = (JLabel) sequencePanel.getComponent(0);
            levelLabel.setText("Level: " + level);
        });
        pattern.clear();
        buttonIndex = 0;
        addButtonToPattern();
        showPattern();
    }

    private void addButtonToPattern() {
        Random r = new Random();
        pattern.add(buttonIds.get(r.nextInt(buttonIds.size())));
    }

    private void showPattern() {
        displayingPattern = true;
        for (JButton button : buttons) button.setEnabled(false);

        buttonIndex = 0;
        int delay = 300;
        int flashCount = pattern.size() * 2;
        Timer timer = new Timer(delay, new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent evt) {
                int i = count / 2;
                int buttonId = pattern.get(i);
                JButton btn = buttons.get(buttonId - 1);
                if (count % 2 == 0) {
                    changeButtonColour(btn, Color.WHITE);
                } else {
                    changeButtonColour(btn, btn.getBackground());
                }
                count++;
                if (count >= flashCount) {
                    ((Timer) evt.getSource()).stop();
                    displayingPattern = false;
                    for (JButton button : buttons) button.setEnabled(true);
                }
            }
        });
        timer.start();
    }

    private void buttonClicked(int buttonId) {
        if (displayingPattern) return;
        if (buttonIndex >= pattern.size()) return; // Guard: already completed, waiting for next level

        int expectedButtonId = pattern.get(buttonIndex);
        JButton button = buttons.get(buttonId - 1);

        if (buttonId == expectedButtonId) {
            changeButtonColour(button, Color.WHITE);
            buttonIndex++;
            if (buttonIndex == pattern.size()) {
                displayingPattern = false;
                highScoreRepo.saveScore(GAME_MODE, playerName, level);
                level++;
                SwingUtilities.invokeLater(() -> {
                    JLabel levelLabel = (JLabel) sequencePanel.getComponent(0);
                    levelLabel.setText("Level: " + level);
                });
                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        addButtonToPattern();
                        showPattern();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        } else {
            if (level > 1) highScoreRepo.saveScore(GAME_MODE, playerName, level - 1);
            changeButtonColour(button, Color.RED);
            gameOver(level);
        }
    }

    private void changeButtonColour(JButton button, Color colour) {
        Color originalColour = button.getBackground();
        button.setBackground(colour);
        new Thread(() -> {
            try {
                Thread.sleep(250);
                button.setBackground(originalColour);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonClicked(Integer.parseInt(e.getActionCommand()));
    }

    private void gameOver(int level) {
        displayingPattern = false;
        sequencePanel.setVisible(false);
        JPanel gameOverPanel = new JPanel(new GridBagLayout());
        gameOverPanel.setOpaque(false);
        gameOverPanel.setSize(1280, 720);

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 150));
        gameOverLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-100, 0, 50, 0);
        gameOverPanel.add(gameOverLabel, gbc);

        JLabel levelLabel = new JLabel("Level " + level);
        levelLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 50));
        levelLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 50, 0);
        gameOverPanel.add(levelLabel, gbc);

        JButton tryAgainButton = new RoundedButton("Try Again", 20);
        tryAgainButton.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
        tryAgainButton.setPreferredSize(new Dimension(170, 50));
        tryAgainButton.setBackground(new Color(0xaacfed));
        tryAgainButton.setForeground(new Color(0x242424));
        tryAgainButton.setFocusPainted(false);
        tryAgainButton.addActionListener(e -> {
            sequencePanel.setVisible(true);
            gameOverPanel.setVisible(false);
            start();
            revalidate();
        });
        gbc.gridy = 2;
        gbc.insets = new Insets(100, 0, 0, 0);
        gameOverPanel.add(tryAgainButton, gbc);

        JButton backButton = new RoundedButton("Back", 20);
        backButton.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
        backButton.setPreferredSize(new Dimension(170, 50));
        backButton.setBackground(new Color(255, 209, 84));
        backButton.setForeground(new Color(0x242424));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            gameOverPanel.setVisible(false);
            sequencePanel.setVisible(false);
            Menu menu = (Menu) SwingUtilities.getWindowAncestor(SequenceMemory.this);
            JPanel root = (JPanel) menu.getContentPane();
            root.removeAll();
            root.add(new Modes(menu), BorderLayout.CENTER);
            menu.revalidate();
            menu.repaint();
        });
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        gameOverPanel.add(backButton, gbc);

        add(gameOverPanel);
        gameOverPanel.setVisible(true);
    }
}
