package src.java.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;


public class NumberMemory extends JPanel implements ActionListener {
    JLabel numberLabel = new JLabel("", SwingConstants.CENTER);
    JLabel timerLabel = new JLabel("", SwingConstants.CENTER);
    JLabel askLabel = new JLabel("", SwingConstants.CENTER);
    JButton submitButton = new JButton();
    JTextField inputText = new JTextField(20);
    JLabel answerLabel = new JLabel("", SwingConstants.CENTER);
    JButton nextButton = new JButton();
    JButton backButton = new JButton();

    int frameWidth;
    int frameHeight;

    Timer timer;	
    int second, minute;
    String ddSecond, ddMinute;	
    DecimalFormat dFormat = new DecimalFormat("00");
    int level;
    String currentNum;
    String submitNum;


    public NumberMemory(){
        start(); 
    }


    private void start(){
        level = 1;    
        frameWidth = 1280;
        frameHeight = 720;
        
        setSize(frameWidth, frameHeight);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);

        setOpaque(false);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    
        numberLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 100));
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setBounds(0,100,frameWidth,400);
        setNum();

        timerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
        timerLabel.setForeground(Color.ORANGE);
        timerLabel.setBounds(0,250,frameWidth,400);
        
        
        askLabel.setVisible(false);
        askLabel.setText("What was the number?");
        askLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 50));
        askLabel.setForeground(Color.WHITE);
        askLabel.setBounds(0,0,frameWidth,400);

        inputText.setVisible(false);
        inputText.setFont(new Font("Helvetica Neue", Font.BOLD, 25));
        inputText.setForeground(Color.WHITE);
        inputText.setBackground(Color.GRAY);
        inputText.setBounds(frameWidth/2-115,300,200,50);

        submitButton = new RoundedButton("",20);
        submitButton.setVisible(false);
        submitButton.setText("Submit");
        submitButton.setActionCommand("submit");
        submitButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        submitButton.setBackground(new Color(255,209,84));
        submitButton.setForeground(Color.BLACK);
        submitButton.setBounds(frameWidth/2-63,400,100,50);
        submitButton.addActionListener(this);

        answerLabel.setVisible(false);
        answerLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        answerLabel.setForeground(Color.WHITE);
        answerLabel.setBounds(0,-100,frameWidth,frameHeight);

        nextButton = new RoundedButton("", 20);
        nextButton.setVisible(false);
        nextButton.setText("Next");
        nextButton.setActionCommand("next");
        nextButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        nextButton.setBackground(new Color(0xaacfed));
        nextButton.setForeground(new Color(0x242424));
        nextButton.setBounds(frameWidth/2-75,400,150,50);
        nextButton.addActionListener(this);
        nextButton.setFocusPainted(false);
        
        backButton = new RoundedButton("", 20);
        backButton.setVisible(false);
        backButton.setText("Back");
        backButton.setActionCommand("back");
        backButton.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
        backButton.setBackground(new Color(255,209,84));
        backButton.setForeground(Color.BLACK);
        backButton.setBounds(frameWidth/2-75,500,150,50);
        backButton.addActionListener(this);
  
        panel.add(numberLabel);
        panel.add(timerLabel);
        panel.add(askLabel);
        panel.add(inputText);
        panel.add(submitButton);    
        panel.add(answerLabel);    
        panel.add(nextButton);   
        panel.add(backButton); 

		resetTimer();
    }


    // Generate a random string of numbers with length equal to the current level
    private void setNum() {

        int intNum;
        String strNum;

        strNum = "";

        for (int i = 1; i <= level; i++) {
            intNum = (int)Math.floor(Math.random() * 10);
            strNum = strNum + intNum;
        }
        
        currentNum = strNum;
        numberLabel.setText(currentNum);
    }

    public void countdownTimer() {

		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;
				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);	
				timerLabel.setText(ddMinute + ":" + ddSecond);
				
				if(second==-1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);	
					timerLabel.setText(ddMinute + ":" + ddSecond);
				}
				if(minute==0 && second==0) {
					timer.stop();

                    askLabel.setVisible(true);
                    inputText.setVisible(true);
                    submitButton.setVisible(true);
                    inputText.requestFocus();
                    
                    numberLabel.setVisible(false);
                    timerLabel.setVisible(false);
				}
			}
		});		
	}	
	
    public void resetTimer() {     
        if (level < 10){
            timerLabel.setText("00:0" + level);
        }
        else if (level < 60){
                timerLabel.setText("00:0" + level);
        }

        second = level;
        minute = 0;
        countdownTimer();
        timer.start();	
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("submit")) {
            // set the text of the label to the text of the field
            submitNum = inputText.getText();
            
            if (submitNum.equals(currentNum)) {
                answerLabel.setText("Correct Answer. You passed level " + level);
                nextButton.setText("Next");
                backButton.setVisible(false);
            }
            else {
                answerLabel.setText("Incorrect Answer. The number was " + currentNum + ". Your answer was " + submitNum + ". You failed at level " + level);
                nextButton.setText("Try again");
                backButton.setVisible(true);
            }

            answerLabel.setVisible(true);
            nextButton.setVisible(true);

            askLabel.setVisible(false);
            inputText.setVisible(false);
            submitButton.setVisible(false);
        }
        else if (action.equals("next")) {
            if (nextButton.getText().equals("Next")){
                level++;
            }
            if (nextButton.getText().equals("Try again")){
                level = 1;
            }

            setNum();

            numberLabel.setVisible(true);
            timerLabel.setText("");
            timerLabel.setVisible(true);

            answerLabel.setVisible(false);
            askLabel.setVisible(false);
            inputText.setVisible(false);
            inputText.setText("");
            submitButton.setVisible(false);
            nextButton.setVisible(false);
            backButton.setVisible(false);

            resetTimer();
        }
        else if (action.equals("back")) {
            Menu menu = (Menu) SwingUtilities.getWindowAncestor(NumberMemory.this);
            menu.getContentPane().removeAll();
            menu.getContentPane().add(new Modes());
            menu.revalidate();
            menu.repaint();
        }
    }

}
