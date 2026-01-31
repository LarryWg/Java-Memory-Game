package memorygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Leaderboard extends JPanel {
    private static final int TOP_COUNT = 5;
    private static final Color BG_PRIMARY = new Color(0x1a1a2e);
    private static final Color ACCENT_GOLD = new Color(0xf4d03f);
    private static final Color ACCENT_SKY = new Color(0x4fc3f7);

    private final Menu menu;

    public Leaderboard(Menu menu) {
        this.menu = menu;
        setSize(1280, 720);
        setBackground(BG_PRIMARY);
        setOpaque(true);
        setLayout(new BorderLayout());

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        content.setBackground(BG_PRIMARY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 80));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 40, 0);
        content.add(titleLabel, gbc);

        HighScoreRepository repo = new HighScoreRepository();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 40, 10, 40);
        JLabel numberTitle = new JLabel("Number Memory");
        numberTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 36));
        numberTitle.setForeground(new Color(0xaacfed));
        content.add(numberTitle, gbc);

        JPanel numberPanel = createScorePanel(repo.getTopScores("number_memory", TOP_COUNT));
        gbc.gridy = 2;
        content.add(numberPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel seqTitle = new JLabel("Sequence Memory");
        seqTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 36));
        seqTitle.setForeground(new Color(0xaacfed));
        content.add(seqTitle, gbc);

        JPanel seqPanel = createScorePanel(repo.getTopScores("sequence_memory", TOP_COUNT));
        gbc.gridy = 2;
        content.add(seqPanel, gbc);

        JButton backButton = new RoundedButton("\u2190 Back to Menu", 20);
        backButton.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        backButton.setBackground(ACCENT_GOLD);
        backButton.setForeground(BG_PRIMARY);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> menu.showMainMenu());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 0, 0, 0);
        content.add(backButton, gbc);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createScorePanel(List<HighScoreRepository.HighScore> scores) {
        JPanel panel = new JPanel(new GridLayout(TOP_COUNT + 1, 3, 10, 5));
        panel.setOpaque(false);

        panel.add(createLabel("Rank", true));
        panel.add(createLabel("Name", true));
        panel.add(createLabel("Level", true));

        for (int i = 0; i < TOP_COUNT; i++) {
            if (i < scores.size()) {
                HighScoreRepository.HighScore hs = scores.get(i);
                panel.add(createLabel(String.valueOf(i + 1), false));
                panel.add(createLabel(hs.playerName(), false));
                panel.add(createLabel(String.valueOf(hs.score()), false));
            } else {
                panel.add(createLabel("-", false));
                panel.add(createLabel("-", false));
                panel.add(createLabel("-", false));
            }
        }
        return panel;
    }

    private JLabel createLabel(String text, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Helvetica Neue", bold ? Font.BOLD : Font.PLAIN, bold ? 20 : 18));
        label.setForeground(Color.WHITE);
        return label;
    }
}
