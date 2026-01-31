package memorygame;

import javax.swing.*;
import java.awt.*;

// Displays the buttons to play number memory or sequence memory
public class Modes extends JPanel {
    private static final Color BG_PRIMARY = new Color(0x1a1a2e);
    private static final Color ACCENT_GOLD = new Color(0xf4d03f);
    private static final Color ACCENT_SKY = new Color(0x4fc3f7);
    private static final Color TEXT_WHITE = new Color(0xf0f0f0);

    private final Menu menu;

    public Modes(Menu menu) {
        this.menu = menu;
        setSize(1280, 720);
        setBackground(BG_PRIMARY);
        setOpaque(true);
        setLayout(null);

        JLabel titleLabel = new JLabel("Choose Mode");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 72));
        titleLabel.setForeground(TEXT_WHITE);
        titleLabel.setBounds(0, 80, 1280, 80);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton sequenceButton = new RoundedButton("Sequence Memory", 24);
        sequenceButton.setFont(new Font("Helvetica Neue", Font.BOLD, 26));
        sequenceButton.setBackground(ACCENT_GOLD);
        sequenceButton.setForeground(BG_PRIMARY);
        sequenceButton.setFocusPainted(false);
        sequenceButton.setBounds(390, 250, 500, 65);
        sequenceButton.addActionListener(e -> {
            JPanel root = (JPanel) menu.getContentPane();
            root.removeAll();
            root.add(new SequenceMemory(menu.getPlayerName()), BorderLayout.CENTER);
            menu.revalidate();
            menu.repaint();
        });

        JButton numberButton = new RoundedButton("Number Memory", 24);
        numberButton.setFont(new Font("Helvetica Neue", Font.BOLD, 26));
        numberButton.setBackground(ACCENT_GOLD);
        numberButton.setForeground(BG_PRIMARY);
        numberButton.setFocusPainted(false);
        numberButton.setBounds(390, 340, 500, 65);
        numberButton.addActionListener(e -> {
            JPanel root = (JPanel) menu.getContentPane();
            root.removeAll();
            root.add(new NumberMemory(menu.getPlayerName()), BorderLayout.CENTER);
            menu.revalidate();
            menu.repaint();
        });

        JButton backButton = new RoundedButton("\u2190 Back to Menu", 20);
        backButton.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        backButton.setBackground(ACCENT_SKY);
        backButton.setForeground(BG_PRIMARY);
        backButton.setFocusPainted(false);
        backButton.setBounds(490, 460, 300, 55);
        backButton.addActionListener(e -> menu.showMainMenu());

        add(titleLabel);
        add(sequenceButton);
        add(numberButton);
        add(backButton);
    }
}
