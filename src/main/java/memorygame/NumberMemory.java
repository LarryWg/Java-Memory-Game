package memorygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class NumberMemory extends JPanel implements ActionListener {
    private static final String GAME_MODE = "number_memory";
    private final HighScoreRepository highScoreRepo = new HighScoreRepository();
    private final String playerName;


    private JLabel numberLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel timerLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel askLabel = new JLabel("", SwingConstants.CENTER);
    private JButton submitButton;
    private JTextField inputText = new JTextField(20);
    private JLabel answerLabel = new JLabel("", SwingConstants.CENTER);
    private JButton nextButton;
    private JButton backButton;

    private int frameWidth = 1280;
    private int frameHeight = 720;
    private Timer timer;
    private int second, minute;
    private final DecimalFormat dFormat = new DecimalFormat("00");
    private int level;
    private String currentNum;
    private String submitNum;

    public NumberMemory(String playerName) {
        this.playerName = playerName != null ? playerName : "Anonymous";
        start();
    }

    private void start() {
        level = 1;
        setSize(frameWidth, frameHeight);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);

        setOpaque(false);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        numberLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 100));
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setBounds(0, 100, frameWidth, 400);
        setNum();

        timerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
        timerLabel.setForeground(Color.ORANGE);
        timerLabel.setBounds(0, 250, frameWidth, 400);

        askLabel.setVisible(false);
        askLabel.setText("What was the number?");
        askLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 50));
        askLabel.setForeground(Color.WHITE);
        askLabel.setBounds(0, 0, frameWidth, 400);

        inputText.setVisible(false);
        inputText.setFont(new Font("Helvetica Neue", Font.BOLD, 25));
        inputText.setForeground(Color.WHITE);
        inputText.setBackground(Color.GRAY);
        inputText.setBounds(frameWidth / 2 - 115, 300, 200, 50);
        inputText.addActionListener(this);

        submitButton = new RoundedButton("Submit", 20);
        submitButton.setVisible(false);
        submitButton.setActionCommand("submit");
        submitButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        submitButton.setBackground(new Color(255, 209, 84));
        submitButton.setForeground(Color.BLACK);
        submitButton.setBounds(frameWidth / 2 - 63, 400, 100, 50);
        submitButton.addActionListener(this);

        answerLabel.setVisible(false);
        answerLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        answerLabel.setForeground(Color.WHITE);
        answerLabel.setBounds(0, -100, frameWidth, frameHeight);

        nextButton = new RoundedButton("Next", 20);
        nextButton.setVisible(false);
        nextButton.setActionCommand("next");
        nextButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        nextButton.setBackground(new Color(0xaacfed));
        nextButton.setForeground(new Color(0x242424));
        nextButton.setBounds(frameWidth / 2 - 75, 400, 150, 50);
        nextButton.addActionListener(this);
        nextButton.setFocusPainted(false);

        backButton = new RoundedButton("Back", 20);
        backButton.setVisible(false);
        backButton.setActionCommand("back");
        backButton.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
        backButton.setBackground(new Color(255, 209, 84));
        backButton.setForeground(Color.BLACK);
        backButton.setBounds(frameWidth / 2 - 75, 500, 150, 50);
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

    private void setNum() {
        StringBuilder strNum = new StringBuilder();
        for (int i = 1; i <= level; i++) {
            strNum.append((int) Math.floor(Math.random() * 10));
        }
        currentNum = strNum.toString();
        numberLabel.setText(currentNum);
    }

    private void countdownTimer() {
        timer = new Timer(1000, e -> {
            second--;
            String ddSecond = dFormat.format(second);
            String ddMinute = dFormat.format(minute);
            timerLabel.setText(ddMinute + ":" + ddSecond);

            if (second == -1) {
                second = 59;
                minute--;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                timerLabel.setText(ddMinute + ":" + ddSecond);
            }
            if (minute == 0 && second == 0) {
                timer.stop();
                askLabel.setVisible(true);
                inputText.setVisible(true);
                submitButton.setVisible(true);
                inputText.requestFocus();
                numberLabel.setVisible(false);
                timerLabel.setVisible(false);
            }
        });
    }

    private void resetTimer() {
        timerLabel.setText(level < 10 ? "00:0" + level : "00:" + level);
        second = level;
        minute = 0;
        countdownTimer();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if ("submit".equals(action) || e.getSource() == inputText) {
            submitNum = inputText.getText();
            if (submitNum.equals(currentNum)) {
                highScoreRepo.saveScore(GAME_MODE, playerName, level);
                answerLabel.setText("Correct Answer. You passed level " + level);
                nextButton.setText("Next");
                backButton.setVisible(false);
            } else {
                if (level > 1) highScoreRepo.saveScore(GAME_MODE, playerName, level - 1);
                answerLabel.setText("Incorrect Answer. The number was " + currentNum + ". Your answer was " + submitNum + ". You failed at level " + level);
                nextButton.setText("Try again");
                backButton.setVisible(true);
            }
            answerLabel.setVisible(true);
            nextButton.setVisible(true);
            askLabel.setVisible(false);
            inputText.setVisible(false);
            submitButton.setVisible(false);
        } else if ("next".equals(action)) {
            if (nextButton.getText().equals("Next")) level++;
            if (nextButton.getText().equals("Try again")) level = 1;

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
        }         else if ("back".equals(action)) {
            Menu menu = (Menu) SwingUtilities.getWindowAncestor(NumberMemory.this);
            JPanel root = (JPanel) menu.getContentPane();
            root.removeAll();
            root.add(new Modes(menu), BorderLayout.CENTER);
            menu.revalidate();
            menu.repaint();
        }
    }
}
