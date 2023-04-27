package src.main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SequenceMemory implements ActionListener {
    private final int BUTTON_SIZE = 125;
    private final int MARGIN = 10;

    private JFrame frame;
    private JPanel panel;
    private ArrayList<Integer> pattern = new ArrayList<Integer>(); // Stores the sequence of button ids
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<Integer> buttonIds = new ArrayList<Integer>(); // Store all possible button ids
    private int buttonIndex;
    private boolean displayingPattern;

    public int level;

    public SequenceMemory() {
        frame = new JFrame("Sequence Memory");
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0x007AFF));
        buttonIndex = 0;
        displayingPattern = false;

        buttonIds.add(1);
        buttonIds.add(2);
        buttonIds.add(3);
        buttonIds.add(4);
        buttonIds.add(5);
        buttonIds.add(6);
        buttonIds.add(7);
        buttonIds.add(8);
        buttonIds.add(9);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 31));
        levelLabel.setForeground(new Color(0xaacfed));
        labelConstraints.gridx = 1;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 1;
        labelConstraints.insets = new Insets(-120, -35, 0, 0);
        panel.add(levelLabel, labelConstraints);
        

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(MARGIN+70, MARGIN, MARGIN-70, MARGIN);

        for (int i = 0; i < 9; i++){
            JButton button = new JButton("");
            button.setActionCommand("");
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            c.gridx = i%3;
            c.gridy = i/3;
            button.setBackground(Color.BLUE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            panel.add(button, c);
            buttons.add(button);
        }

        frame.add(panel);
        frame.pack();
        frame.setSize(1280, 720);
        frame.setBackground(Color.BLUE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);\



    }

    public void start(){
    }

    public void addButtonToPattern(){
        
    }

    public void showPattern(){

    }

    public void buttonClicked(){


    }

    public void changeButtonColour(){

    }

    public void actionPerformed(ActionEvent e){

    }
}