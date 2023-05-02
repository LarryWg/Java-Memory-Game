package src.java.main;

import javax.swing.*;
import java.awt.*;



public class NumberMemory extends JPanel{

    public NumberMemory(){
        
        setSize(1280, 720);
    

        JLabel titleLabel = new JLabel("Number");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 100));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(400,100,500,400);
        
        

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);
        panel.add(titleLabel);

        setOpaque(false);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        


        




    }


}