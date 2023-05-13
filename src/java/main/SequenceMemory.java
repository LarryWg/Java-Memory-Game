package src.java.main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class SequenceMemory extends JPanel implements ActionListener {
    private final int BUTTON_SIZE = 125;
    private final int MARGIN = 10;

    
    private JPanel sequencePanel;
    // Stores the random pattern
    private ArrayList<Integer> pattern = new ArrayList<Integer>(); 
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    // Stores button ids from 1 to 9 which will be randomly added to the pattern
    private ArrayList<Integer> buttonIds = new ArrayList<Integer>(); 
    private int buttonIndex;
    private boolean displayingPattern;
    public int level;

    public SequenceMemory() {
        setSize(1280, 720 );

        buttonIndex = 0;
        displayingPattern = false;
        level = 1;

        // Availble buttons
        buttonIds.add(1);
        buttonIds.add(2);
        buttonIds.add(3);
        buttonIds.add(4);
        buttonIds.add(5);
        buttonIds.add(6);
        buttonIds.add(7);
        buttonIds.add(8);
        buttonIds.add(9);

        sequencePanel = new JPanel(new GridBagLayout());
        sequencePanel.setOpaque(false);
        
    
        GridBagConstraints labelConstraints = new GridBagConstraints();
        JLabel levelLabel = new JLabel("Level: " + level);
        levelLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 31));
        levelLabel.setForeground(new Color(0xaacfed));
        labelConstraints.gridx = 1;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 1;
        labelConstraints.insets = new Insets(-120, -35, 0, 0);
        

        sequencePanel.add(levelLabel, labelConstraints);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(MARGIN+70, MARGIN, MARGIN-70, MARGIN);

        // Creates the button in a 3x3
        for (int i = 0; i < 9; i++){
            JButton button = new RoundedButton("",20);
            button.setActionCommand(""+(i+1));
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            c.gridx = i%3;
            c.gridy = i/3;
            button.setBackground(new Color(0x2573c1));
            button.setBorderPainted(false);
            button.setFocusPainted(true);
            sequencePanel.add(button, c);
            buttons.add(button);
        }
        
        setOpaque(false);
        setLayout(new BorderLayout());
        setVisible(true);
        add(sequencePanel);

        // Starts the game
        start();
        
        
    }

    // Starts game and resets
    public void start(){
        level = 1;
        SwingUtilities.invokeLater(() -> {
            JLabel levelLabel = (JLabel) sequencePanel.getComponent(0);
            levelLabel.setText("Level: " + level);
        });
        pattern.clear();
        buttonIndex = 0;
        addButtonToPattern();
        showPattern();
    }

    // from the buttonIds add them to the random pattern
    public void addButtonToPattern(){
        Random r = new Random();
        int randomIndex = r.nextInt(buttonIds.size());
        pattern.add(buttonIds.get(randomIndex));
    }

    // Displays the pattern
    public void showPattern() {
        displayingPattern = true;
        // When the pattern is showing, disable the buttons
        for (JButton button : buttons) {
            button.setEnabled(false);
        }      
        
        // Flashes/changes colour of the buttons within the pattern
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
                    for (JButton button : buttons) {
                        button.setEnabled(true);
                    }
                    
                }
            }
        });
        timer.start();
    }

    // Checks if the user recreated the correct pattern
    public void buttonClicked(int buttonId){
        if(displayingPattern){
            return;
            
        }
        //Expected button to be clicked according to the pattern
        int expectedButtonId = pattern.get(buttonIndex);
        JButton button = buttons.get(buttonId - 1);
        
        if (buttonId == expectedButtonId){
            changeButtonColour(button, Color.WHITE);
            buttonIndex++;
            if(buttonIndex == pattern.size()){
                displayingPattern = false;
                level++;
                SwingUtilities.invokeLater(() -> {
                    JLabel levelLabel = (JLabel) sequencePanel.getComponent(0);
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
            gameOver(level);
            
        }
    }
   
    // Changes button colour to desired colour and changes it back to original
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

    public void actionPerformed(ActionEvent e){
        String actionCommand = e.getActionCommand();
        int buttonId = Integer.parseInt(actionCommand);
        buttonClicked(buttonId);
    }

    // Game over
    public void gameOver(int level) {
        displayingPattern = false;
        sequencePanel.setVisible(false); 
        JPanel gameOverPanel = new JPanel(new GridBagLayout());
        gameOverPanel.setBackground(new Color(0x007AFF));
        gameOverPanel.setOpaque(false);
        gameOverPanel.setSize(1280, 720);
    
        GridBagConstraints gameOverLabelConstraints = new GridBagConstraints();
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 150));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabelConstraints.gridx = 0;
        gameOverLabelConstraints.gridy = 0;
        gameOverLabelConstraints.gridwidth = 1;
        gameOverLabelConstraints.insets = new Insets(-100, 0, 50, 0);
        gameOverPanel.add(gameOverLabel, gameOverLabelConstraints);
    
        // Displays which level user failed on
        GridBagConstraints levelLabelConstraints = new GridBagConstraints();
        JLabel levelLabel = new JLabel("Level " + level);
        levelLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 50));
        levelLabel.setForeground(Color.WHITE);
        levelLabelConstraints.gridx = 0;
        levelLabelConstraints.gridy = 1;
        levelLabelConstraints.gridwidth = 1;
        levelLabelConstraints.insets = new Insets(0, 0, 50, 0);
        gameOverPanel.add(levelLabel, levelLabelConstraints);
    
        GridBagConstraints tryAgainButtonConstraints = new GridBagConstraints();
        JButton tryAgainButton = new RoundedButton("Try Again",20);
        tryAgainButton.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
        tryAgainButton.setPreferredSize(new Dimension(170,50));
        tryAgainButton.setBackground(new Color(0xaacfed));
        tryAgainButton.setForeground(new Color(0x242424));
        tryAgainButton.setFocusPainted(false);
        tryAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                sequencePanel.setVisible(true);
                gameOverPanel.setVisible(false); 
                start();
                revalidate();
                
            }
        });
        tryAgainButtonConstraints.gridx = 0;
        tryAgainButtonConstraints.gridy = 2;
        tryAgainButtonConstraints.gridwidth = 1;
        tryAgainButtonConstraints.insets = new Insets(100, 0, 0, 0);
        gameOverPanel.add(tryAgainButton, tryAgainButtonConstraints);


        JButton backButton = new RoundedButton("Back",20);
        backButton.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
        backButton.setPreferredSize(new Dimension(170,50));
        backButton.setBackground(new Color(255, 209, 84));
        backButton.setForeground(new Color(0x242424));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameOverPanel.setVisible(false);
                sequencePanel.setVisible(false);
                Menu menu = (Menu) SwingUtilities.getWindowAncestor(SequenceMemory.this);
                menu.getContentPane().removeAll();
                menu.getContentPane().add(new Modes());
                menu.revalidate();
                menu.repaint();
                
            }
        });
        GridBagConstraints backButtonConstraints = new GridBagConstraints();
        backButtonConstraints.gridx = 0;
        backButtonConstraints.gridy = 3;
        backButtonConstraints.gridwidth = 1;
        backButtonConstraints.insets = new Insets(10, 0, 0, 0);
        gameOverPanel.add(backButton, backButtonConstraints);

        add(gameOverPanel); 
        gameOverPanel.setVisible(true); 
    }
}
