package src.java.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Displays the buttons to play number memory or sequence memory
public class Modes extends JPanel {
    private JButton numberButton, sequenceButton;

    public Modes(){
        setSize(1280,720);

        JLabel titleLabel = new JLabel("Modes");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 150));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(400, 100, 900, 150);

        numberButton = new RoundedButton("Number Memory",20);
        numberButton.setFont(new Font("Helvetica Neue", Font.BOLD, 29));
        numberButton.setBackground(new Color(255, 209, 84));
        numberButton.setForeground(new Color(0x242424));
        numberButton.setFocusPainted(false);
        numberButton.setBounds(480,500,330,60);
        numberButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Menu menu = (Menu) SwingUtilities.getWindowAncestor(Modes.this);
                menu.getContentPane().removeAll();
                menu.getContentPane().add(new NumberMemory());
                menu.validate();
                menu.repaint();
            }
        });

        sequenceButton = new RoundedButton("Sequence Memory",20);
        sequenceButton.setFont(new Font("Helvetica Neue", Font.BOLD, 29));
        sequenceButton.setBackground(new Color(255, 209, 84));
        sequenceButton.setForeground(new Color(0x242424));
        sequenceButton.setFocusPainted(false);
        sequenceButton.setBounds(480, 320, 330, 60);
        sequenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = (Menu) SwingUtilities.getWindowAncestor(Modes.this);
                menu.getContentPane().removeAll();
                menu.getContentPane().add(new SequenceMemory());
                menu.validate();
                menu.repaint();
            }
        });
        

        JPanel modesPanel = new JPanel(null);
        modesPanel.setOpaque(false);
        modesPanel.add(titleLabel);
        modesPanel.add(numberButton);
        modesPanel.add(sequenceButton);

        setOpaque(false);
        setLayout(new BorderLayout());
        add(modesPanel, BorderLayout.CENTER);
    }
}
