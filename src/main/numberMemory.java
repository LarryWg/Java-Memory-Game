package src.main;

import javax.swing.*;
import java.awt.*;



public class NumberMemory extends JFrame{
    
    public NumberMemory(){
        super("Number Memory");
        setSize(1280, 720);
        setBackground(new Color(0x007AFF));
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titleLabel = new JLabel("pASDPONNNNNNNNWFE");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 300));
        
        

        JPanel panel = new JPanel();
        
        panel.add(titleLabel);
        panel.setBackground(new Color(0x007AFF));
        setContentPane(panel);











        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


}