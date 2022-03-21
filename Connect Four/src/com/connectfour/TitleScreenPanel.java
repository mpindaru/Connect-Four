package com.connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreenPanel extends JPanel {

    private static final int BUTTON_LOCATION_X = 1000;  // location x
    private static final int BUTTON_LOCATION_Y = 50;   // location y
    private static final int BUTTON_SIZE_X = 140;      // size height
    private static final int BUTTON_SIZE_Y = 50;

    private boolean animatedInsideBalls = false;

    private TitleScreenBallOutside ballLeft;
    private TitleScreenBallOutside ballRight;
    private TitleScreenBallInside insideBall1;
    private TitleScreenBallInside insideBall2;
    private TitleScreenBallInside insideBall3;
    private TitleScreenBallInside insideBall4;
    private TitleScreenBallInside insideBall5;
    private GameBoardComponent gameBoard;
    private JPanel buttonPanel;
    private GUI gui;

    public TitleScreenPanel(GUI g) {
        gui = g;
        setLayout( new OverlayLayout(this) );
        buttonPanel = new JPanel();
        gameBoard = new GameBoardComponent();
        ballLeft = new TitleScreenBallOutside(0);
        ballRight = new TitleScreenBallOutside(6);
        insideBall1 = new TitleScreenBallInside(1);
        insideBall2 = new TitleScreenBallInside(2);
        insideBall3 = new TitleScreenBallInside(3);
        insideBall4 = new TitleScreenBallInside(4);
        insideBall5 = new TitleScreenBallInside(5);
        buttonPanel.setOpaque(false);
        gameBoard.setOpaque(false);
        ballLeft.setOpaque(false);
        ballRight.setOpaque(false);
        insideBall1.setOpaque(false);
        insideBall2.setOpaque(false);
        insideBall3.setOpaque(false);
        buttonPanel.setVisible(false);
        add(buttonPanel, BorderLayout.NORTH);
        add(gameBoard, BorderLayout.CENTER);
        add(ballLeft, BorderLayout.CENTER);
        add(ballRight, BorderLayout.CENTER);
        add(insideBall1, BorderLayout.CENTER);
        add(insideBall2, BorderLayout.CENTER);
        add(insideBall3, BorderLayout.CENTER);
        add(insideBall4, BorderLayout.CENTER);
        add(insideBall5, BorderLayout.CENTER);
        MyBallThread ballAnimationLeft = new MyBallThread(ballLeft, this);
        MyBallThread ballAnimationRight = new MyBallThread(ballRight, this);
    }

    public void animateInsideBalls() {
        if(!animatedInsideBalls){
            insideBall1.setBallMoving();
            MyInsideBallThread ball1 = new MyInsideBallThread(insideBall1, this);
            insideBall5.setBallMoving();
            MyInsideBallThread ball5 = new MyInsideBallThread(insideBall5, this);
            insideBall4.setBallMoving();
            MyInsideBallThread ball4 = new MyInsideBallThread(insideBall4, this);
            insideBall2.setBallMoving();
            MyInsideBallThread ball2 = new MyInsideBallThread(insideBall2, this);
            insideBall3.setBallMoving();
            MyInsideBallThread ball3 = new MyInsideBallThread(insideBall3, this);
            animatedInsideBalls = true;
        }

    }

    public void addStartButton() {
        JButton button = new JButton("START GAME");
        buttonPanel.add(button);
        buttonPanel.setVisible(true);
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui.startGame();
            }
        });
    }

}

class MyBallThread implements Runnable {

    private boolean exit;
    Thread t;
    private TitleScreenBallOutside ball;
    private TitleScreenPanel panel;

    MyBallThread(TitleScreenBallOutside tBall, TitleScreenPanel tPanel)
    {
        panel = tPanel;
        ball = tBall;
        t = new Thread(this);
        ball.setThread(this);
        exit = false;
        t.start();
    }

    public void run()
    {
        while (!exit) {
            ball.repaint();
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                System.out.println("Caught:" + e);
            }
        }
    }

    public void stop()
    {
        exit = true;
        panel.animateInsideBalls();
    }
}

class MyInsideBallThread implements Runnable {

    private boolean exit;
    Thread t;
    private TitleScreenBallInside ball;
    TitleScreenPanel panel;

    MyInsideBallThread(TitleScreenBallInside tBall, TitleScreenPanel tPanel)
    {
        panel = tPanel;
        ball = tBall;
        t = new Thread(this);
        ball.setThread(this);
        exit = false;
        t.start();
    }

    public void run()
    {
        while (!exit) {
            ball.repaint();
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                System.out.println("Caught:" + e);
            }
        }
    }

    public void stop()
    {
        exit = true;
        if(ball.getColumn()==3) {
            ball.repaint();
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                System.out.println("Caught:" + e);
            }
            panel.addStartButton();

        }

    }
}


