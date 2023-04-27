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
    private ArrayList<Integer> pattern = new ArrayList<Integer>(); 
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<Integer> buttonIds = new ArrayList<Integer>(); 
    private int buttonIndex;
    private boolean displayingPattern;
    private int level;

    public SequenceMemory() {
        buttonIndex = 0;
        displayingPattern = false;
        level = 1;


        buttonIds.add(1);
        buttonIds.add(2);
        buttonIds.add(3);
        buttonIds.add(4);
        buttonIds.add(5);
        buttonIds.add(6);
        buttonIds.add(7);
        buttonIds.add(8);
        buttonIds.add(9);

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Sequence Memory");
            panel = new JPanel(new GridBagLayout());
            panel.setBackground(new Color(0x007AFF));
        
            GridBagConstraints labelConstraints = new GridBagConstraints();
            JLabel levelLabel = new JLabel("Level:" + level);
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
                button.setActionCommand(""+(i+1));
                button.addActionListener(this);
                button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                c.gridx = i%3;
                c.gridy = i/3;
                button.setBackground(new Color(0x2573c1));
                button.setBorderPainted(false);
                button.setFocusPainted(true);
                panel.add(button, c);
                buttons.add(button);
            }


            frame.add(panel);
            frame.pack();
            frame.setSize(1280, 720);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);


            start();
        });
        
    }

    public void start(){
        pattern.clear();
        buttonIndex = 0;
        addButtonToPattern();
        showPattern();
    }

    public void addButtonToPattern(){
        Random r = new Random();
        int randomIndex = r.nextInt(buttonIds.size());
        pattern.add(buttonIds.get(randomIndex));
    }

    public void showPattern() {
        displayingPattern = true;
        buttonIndex = 0;
        int delay = 300; 
        int flashCount = pattern.size() * 2; 
        Timer timer = new Timer(delay, new ActionListener() {
            int count = 0;
            public void actionPerformed(ActionEvent evt) {
                int i = count / 2;
                if (count % 2 == 0) {

                    int buttonId = pattern.get(i);
                    JButton button = buttons.get(buttonId - 1);
                    changeButtonColour(button, Color.WHITE);
                } else {
                    int buttonId = pattern.get(i);
                    JButton button = buttons.get(buttonId - 1);
                    changeButtonColour(button, button.getBackground());
                }
                count++;
                if (count >= flashCount) {
                    ((Timer) evt.getSource()).stop();
                    displayingPattern = false;
                }
            }
        });
        timer.start();
    }

    public void buttonClicked(int buttonId){
        if(displayingPattern){
            return;
        }

        int expectedButtonId = pattern.get(buttonIndex);
        JButton button = buttons.get(buttonId - 1);
        if (buttonId == expectedButtonId){
            changeButtonColour(button, Color.WHITE);
            buttonIndex++;
            if(buttonIndex == pattern.size()){
                displayingPattern = false;
                level++;
                SwingUtilities.invokeLater(() -> {
                    JLabel levelLabel = (JLabel) panel.getComponent(0);
                    levelLabel.setText("Level: " + level);
                });
                new Thread(new Runnable(){
                    public void run(){
                        try{
                            Thread.sleep(500);
                            addButtonToPattern();
                            showPattern();
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } else{
            changeButtonColour(button, Color.RED);
            gameOver();
        }
    }

    public void changeButtonColour(JButton button, Color colour){
        Color originalColour = button.getBackground();
        button.setBackground(colour);
        new Thread(new Runnable(){
            public void run(){
                try{
                    Thread.sleep(250);
                    button.setBackground(originalColour);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void gameOver(){
        displayingPattern = false;
        new Thread(new Runnable(){
            public void run(){
                try{
                    Thread.sleep(500);
                    frame.setTitle("Game Over");
                    level = 1;
                    SwingUtilities.invokeLater(() -> {
                        JLabel levelLabel = (JLabel) panel.getComponent(0);
                        levelLabel.setText("Level: " + level);
                    });
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void actionPerformed(ActionEvent e){
        String actionCommand = e.getActionCommand();
        int buttonId = Integer.parseInt(actionCommand);
        buttonClicked(buttonId);
    }

}
