package src.java.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton playButton;
    private JButton instructionsButton;
    private JPanel instructionPanel;
    private int panelY;
    private JButton closeButton;

    public Menu() {
        super("Memory Test");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setBackground(new Color(0x007AFF));
        setLayout(null);
        setResizable(false);

        JLabel titleLabel = new JLabel("Memory Test");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 150));
        titleLabel.setForeground(new Color(0xFFFFFF));
        titleLabel.setBounds(180,100,1000,300);

        playButton = new RoundedButton("Play",20);
        playButton.setFont(new Font("Helvetica Neue", Font.BOLD, 40));
        playButton.setBackground(new Color(255,209,84));
        playButton.setForeground(new Color(0x242424));
        playButton.setFocusPainted(false);
        playButton.setBounds(540,520,200,60);
        playButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(new Modes());
                validate();
                repaint();
            }
        });
        
        closeButton = new RoundedButton("Close",20);
        closeButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        closeButton.setBackground(new Color(0xaacfed));
        closeButton.setForeground(new Color(0x242424));
        closeButton.setFocusPainted(false);
        closeButton.setVisible(false);
        closeButton.setBounds(540,520,200,60);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                instructionPanel.setVisible(false);
                titleLabel.setVisible(true);
                playButton.setVisible(true);
                instructionsButton.setVisible(true);

            }
        });
        instructionsButton = new RoundedButton("Instructions",20);
        instructionsButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        instructionsButton.setBackground(new Color(0xaacfed));
        instructionsButton.setForeground(new Color(0x242424));
        instructionsButton.setFocusPainted(false);
        instructionsButton.setBounds(540,600,200,40);
        instructionsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                showInstructionsPanel();
                titleLabel.setVisible(false);
                playButton.setVisible(false);
                instructionsButton.setVisible(false);
                closeButton.setVisible(true);
            }
        });

        instructionPanel = new RoundedPanel(20);

        instructionPanel.setLayout(null);
        instructionPanel.setBounds(280, 720, 720, 420);
        instructionPanel.setBackground(new Color(0x010B3D));
        JLabel instructionLabel = new JLabel("<html><center>Instructions<br>efaf.</center></html>");
        instructionLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setBounds(0,0,720,220);
        instructionPanel.add(instructionLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(playButton);
        buttonPanel.add(instructionsButton);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setLayout(null);
        menuPanel.setOpaque(false);
        menuPanel.add(titleLabel);
        menuPanel.add(instructionsButton);
        menuPanel.add(playButton);
        menuPanel.add(closeButton);
        menuPanel.add(instructionPanel);

        setContentPane(menuPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void showInstructionsPanel(){
        instructionPanel.setVisible(true);
        animate();
        
    }

    private void animate(){
        int frameHeight = getContentPane().getHeight();
        int panelHeight = instructionPanel.getHeight();
        panelY = (frameHeight - panelHeight)/ 2;

        Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            int currentY = instructionPanel.getY();

            @Override
            public void actionPerformed(ActionEvent e){
                if (currentY > panelY){
                    currentY-=100;
                    instructionPanel.setLocation(instructionPanel.getX(), currentY);
                    instructionPanel.revalidate();
                    instructionPanel.repaint();

                }else{
                    timer.stop();
                }
            }
            
        });
        timer.start();
    }

    public Menu(String string) {
    }
    
}
