package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Modes extends JFrame{
    private JButton numberButton, sequenceButton;

    public Modes(){
        super("Modes");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setBackground(new Color(0x007AFF));
        setLayout(null);
        setResizable(false);

        JLabel titleLabel = new JLabel("Modes");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 150));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(400, 100, 900, 150);

        numberButton = new JButton("Number Memory");
        numberButton.setFont(new Font("Helvetica Neue", Font.BOLD, 29));
        numberButton.setBackground(new Color(255, 209, 84));
        numberButton.setForeground(new Color(0x242424));
        numberButton.setFocusPainted(false);
        numberButton.setBounds(480,500,330,60);

        sequenceButton = new JButton("Sequence Memory");
        sequenceButton.setFont(new Font("Helvetica Neue", Font.BOLD, 29));
        sequenceButton.setBackground(new Color(255, 209, 84));
        sequenceButton.setForeground(new Color(0x242424));
        sequenceButton.setFocusPainted(false);
        sequenceButton.setBounds(480, 320, 330, 60);
        sequenceButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e){
                new SequenceMemory();
                dispose();
            }
        });

        JPanel modesPanel = new JPanel(null);
        modesPanel.setOpaque(false);
        modesPanel.add(titleLabel);
        modesPanel.add(numberButton);
        modesPanel.add(sequenceButton);

        setContentPane(modesPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
  
}
