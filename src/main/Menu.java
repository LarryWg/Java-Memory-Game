package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton playButton;

    public Menu() {
        super("Memory Test");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setBackground(new Color(0x007AFF));
        setLayout(null);
        setResizable(false);

        JLabel titleLabel = new JLabel("Memory Test");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 150));
        titleLabel.setForeground(new Color(0xFFFFFF));
        titleLabel.setBounds(180,100,1000,300);

        playButton = new JButton("Play");
        playButton.setFont(new Font("Helvetica Neue", Font.BOLD, 40));
        playButton.setBackground(new Color(255,209,84));
        playButton.setForeground(new Color(0x242424));
        playButton.setFocusPainted(false);
        playButton.setBounds(540,520,200,60);
    
        playButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Modes();
            }
        });
       
        

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(playButton);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setLayout(null);
        menuPanel.setOpaque(false);
        menuPanel.add(titleLabel);
        menuPanel.add(playButton);

        setContentPane(menuPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

    }
}
