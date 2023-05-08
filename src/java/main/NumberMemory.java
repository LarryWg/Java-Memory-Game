package src.java.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;


public class NumberMemory extends JPanel{
    JLabel numberLabel = new JLabel();
    JLabel timerLabel = new JLabel();
    JLabel askLabel = new JLabel();
    JButton submitButton = new JButton();
    JTextField inputText = new JTextField(16);

    Timer timer;	
    int second, minute;
    String ddSecond, ddMinute;	
    DecimalFormat dFormat = new DecimalFormat("00");
    int level;
    int currentNum;


    public NumberMemory(){
        
        start();
        
    }


    private void start(){

        level = 1;    

        setSize(1280, 720);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);

        setOpaque(false);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        
        showNum(level, panel);         
        //askNum(panel);

    }

    private void showNum(int level, JPanel panel){
        System.out.println("In show num");
        int randomInt;
        int min, max;

        min = 0; max = 00;
        switch (level) {
            case 1:
                min = 0; max = 9;
                break;
            case 2:
                min = 10; max = 99;
                break;
            case 3:
                min = 100; max = 999;
                break;
            case 4:
                min = 1000; max = 9999;
                break;
            case 5:
                min = 10000; max = 99999;
                break;
        }
        randomInt = getNum(min, max); 

        numberLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 100));
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setBounds(400,100,500,400);

        timerLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 50));
        timerLabel.setForeground(Color.GRAY);
        timerLabel.setBounds(400,250,500,400);
        
        askLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 50));
        askLabel.setForeground(Color.WHITE);
        askLabel.setBounds(250,0,700,400);


        inputText.setVisible(false);
        inputText.setFont(new Font("Helvetica Neue", Font.BOLD, 25));
        inputText.setForeground(Color.WHITE);
        inputText.setBackground(Color.GRAY);
        inputText.setBounds(250,300,200,50);

        submitButton.setVisible(false);
        submitButton.setText("Submit");
        submitButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        submitButton.setBackground(new Color(255,209,84));
        submitButton.setForeground(Color.BLACK);
        submitButton.setBounds(250,400,100,50);

        // addActionListener to button
        //submitButton.addActionListener(inputText);
 
        panel.add(numberLabel);
        panel.add(timerLabel);
        panel.add(askLabel);
        panel.add(inputText);
        panel.add(submitButton);    

        numberLabel.setText("" + randomInt);

		// Countdown Timer
		timerLabel.setText("00:03");
		second = 3;
		minute = 0;
		countdownTimer();
		timer.start();	

    }

    
    private void  askNum(JPanel panel){
        
        JLabel askLabel = new JLabel("What was the number");
        askLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 50));
        askLabel.setForeground(Color.RED);
        askLabel.setBounds(200,350,700,400);

 
        panel.add(askLabel);
        
        System.out.println("In ask  num");
    }


    private int getNum(int min, int max) {
        // Generate a random value in int from min to max
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
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
                    
                    numberLabel.setText("");
                    timerLabel.setText("");
                    askLabel.setText("What was the number?");
                    inputText.setVisible(true);
                    submitButton.setVisible(true);
				}
			}
		});		

	}	
		

}
