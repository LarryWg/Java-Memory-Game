package memorygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main menu
public class Menu extends JFrame {
    private static final Color BG_PRIMARY = new Color(0x1a1a2e);
    private static final Color BG_ACCENT = new Color(0x16213e);
    private static final Color ACCENT_GOLD = new Color(0xf4d03f);
    private static final Color ACCENT_SKY = new Color(0x4fc3f7);
    private static final Color TEXT_WHITE = new Color(0xf0f0f0);
    private static final Color TEXT_MUTED = new Color(0xb0b0b0);

    private JButton playButton;
    private JButton instructionsButton;
    private JButton leaderboardButton;
    private JPanel instructionPanel;
    private JPanel menuPanel;
    private JTextField playerNameField;
    private int panelY;
    private JButton closeButton;

    public String getPlayerName() {
        return playerNameField != null ? playerNameField.getText() : "Anonymous";
    }

    public Menu() {
        super("Memory Test");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_PRIMARY);
        setLayout(null);
        setResizable(false);

        JLabel titleLabel = new JLabel("Memory Test");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 120));
        titleLabel.setForeground(TEXT_WHITE);
        titleLabel.setBounds(0, 80, 1280, 120);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Challenge your recall");
        subtitleLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
        subtitleLabel.setForeground(TEXT_MUTED);
        subtitleLabel.setBounds(0, 200, 1280, 40);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel("Your name:");
        nameLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        nameLabel.setForeground(TEXT_MUTED);
        nameLabel.setBounds(440, 260, 120, 30);

        playerNameField = new JTextField(15);
        playerNameField.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        playerNameField.setForeground(BG_PRIMARY);
        playerNameField.setBackground(TEXT_WHITE);
        playerNameField.setCaretColor(BG_PRIMARY);
        playerNameField.setBounds(560, 255, 280, 40);
        playerNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_SKY, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        playerNameField.setHorizontalAlignment(JTextField.CENTER);

        playButton = new RoundedButton("Play", 24);
        playButton.setFont(new Font("Helvetica Neue", Font.BOLD, 32));
        playButton.setBackground(ACCENT_GOLD);
        playButton.setForeground(BG_PRIMARY);
        playButton.setFocusPainted(false);
        playButton.setBounds(490, 320, 300, 65);
        playButton.addActionListener(e -> showModes());

        closeButton = new RoundedButton("Close", 20);
        closeButton.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        closeButton.setBackground(ACCENT_SKY);
        closeButton.setForeground(BG_PRIMARY);
        closeButton.setFocusPainted(false);
        closeButton.setVisible(false);
        closeButton.setBounds(540, 600, 200, 50);
        closeButton.addActionListener(e -> {
            instructionPanel.setVisible(false);
            titleLabel.setVisible(true);
            subtitleLabel.setVisible(true);
            nameLabel.setVisible(true);
            playerNameField.setVisible(true);
            playButton.setVisible(true);
            instructionsButton.setVisible(true);
            leaderboardButton.setVisible(true);
        });

        instructionsButton = new RoundedButton("How to Play", 20);
        instructionsButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        instructionsButton.setBackground(ACCENT_SKY);
        instructionsButton.setForeground(BG_PRIMARY);
        instructionsButton.setFocusPainted(false);
        instructionsButton.setBounds(490, 410, 300, 55);
        instructionsButton.addActionListener(e -> {
            showInstructionsPanel();
            titleLabel.setVisible(false);
            subtitleLabel.setVisible(false);
            nameLabel.setVisible(false);
            playerNameField.setVisible(false);
            playButton.setVisible(false);
            instructionsButton.setVisible(false);
            leaderboardButton.setVisible(false);
            closeButton.setVisible(true);
        });

        leaderboardButton = new RoundedButton("Leaderboard", 20);
        leaderboardButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        leaderboardButton.setBackground(ACCENT_SKY);
        leaderboardButton.setForeground(BG_PRIMARY);
        leaderboardButton.setFocusPainted(false);
        leaderboardButton.setBounds(490, 485, 300, 55);
        leaderboardButton.addActionListener(e -> showLeaderboard());

        instructionPanel = new RoundedPanel(24);
        instructionPanel.setLayout(null);
        instructionPanel.setBounds(280, 720, 720, 420);
        instructionPanel.setBackground(BG_ACCENT);
        JLabel instructionLabel = new JLabel("<html><center><b><font size='+3'>How to Play</b></font></center></html>");
        JLabel howtoplayLabel = new JLabel("<html><font size='+1'><b>Sequence Memory</font></b><br>Memorize the sequence of the pattern shown and recreate it in order.<br>The pattern gets longer as you advance.<br><br><font size='+1'><b>Number Memory</b></font><br>Memorize the numbers displayed, then type them in the text box.<br>One more digit each level. How far can you go?</html>");
        instructionLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        howtoplayLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        howtoplayLabel.setForeground(TEXT_WHITE);
        howtoplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        howtoplayLabel.setBounds(0, 125, 720, 220);
        instructionLabel.setForeground(TEXT_WHITE);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setBounds(0, -20, 720, 220);
        instructionPanel.add(instructionLabel);
        instructionPanel.add(howtoplayLabel);

        menuPanel = new JPanel(null);
        menuPanel.setBackground(BG_PRIMARY);
        menuPanel.setOpaque(true);
        menuPanel.add(titleLabel);
        menuPanel.add(subtitleLabel);
        menuPanel.add(nameLabel);
        menuPanel.add(playerNameField);
        menuPanel.add(instructionsButton);
        menuPanel.add(leaderboardButton);
        menuPanel.add(playButton);
        menuPanel.add(closeButton);
        menuPanel.add(instructionPanel);

        // Use a root panel as content pane so we can swap between menuPanel, Modes, etc.
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(BG_PRIMARY);
        rootPanel.add(menuPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showMainMenu() {
        JPanel root = (JPanel) getContentPane();
        root.removeAll();
        root.add(menuPanel, BorderLayout.CENTER);
        root.setBackground(BG_PRIMARY);
        revalidate();
        repaint();
    }

    private void showModes() {
        JPanel root = (JPanel) getContentPane();
        root.removeAll();
        root.add(new Modes(this), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showLeaderboard() {
        JPanel root = (JPanel) getContentPane();
        root.removeAll();
        root.add(new Leaderboard(this), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showInstructionsPanel() {
        instructionPanel.setVisible(true);
        animate();
    }

    private void animate() {
        int frameHeight = getContentPane().getHeight();
        int panelHeight = instructionPanel.getHeight();
        panelY = (frameHeight - panelHeight) / 2;
        instructionPanel.setLocation(instructionPanel.getX(), frameHeight);
        closeButton.setLocation(closeButton.getX(), 1075);

        int initialPanelY = instructionPanel.getY();
        int initialButtonY = closeButton.getY();

        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int currentY = frameHeight;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentY > panelY) {
                    currentY -= 15;
                    int changeInY = initialPanelY - currentY;
                    int newPanelY = initialPanelY - changeInY;
                    int buttonY = initialButtonY - changeInY;
                    instructionPanel.setLocation(instructionPanel.getX(), newPanelY);
                    closeButton.setLocation(closeButton.getX(), buttonY);
                    instructionPanel.revalidate();
                    instructionPanel.repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}
