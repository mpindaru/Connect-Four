package com.connectfour;


import javax.swing.*;

public class GUI {

    JFrame frame;
    JPanel titlePanel;
    JPanel menuPanel;
    JPanel gameStart1v1;
    JPanel gameStart1v1WithLimits;
    MainPanel gameWithLimits;
    MainPanelNoLimit gameNoLimits;

    void createAndShowGUI() {
        frame = new JFrame("Four in a row");
        titlePanel = new TitleScreenPanel(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(new MainPanel("RED", "GREEN"));
        frame.add(titlePanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void startGame() {
        menuPanel = new menuPanel(this);
        frame.remove(titlePanel);
        frame.add(menuPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void start1v1Game() {
        gameStart1v1 = new Starting1v1GamePanel(this);
        frame.remove(menuPanel);
        frame.add(gameStart1v1);
        frame.revalidate();
        frame.repaint();
    }

    public void start1v1GameWithLimits() {
        gameStart1v1WithLimits = new Starting1v1GamePanelWithLimits(this);
        frame.remove(menuPanel);
        frame.add(gameStart1v1WithLimits);
        frame.revalidate();
        frame.repaint();
    }

    public void displayTitleScreen() {
        titlePanel = new TitleScreenPanel(this);
        frame.remove(menuPanel);
        frame.add(titlePanel);
        frame.revalidate();
        frame.repaint();
    }

    public void removeStart1v1NoLimit() {
        menuPanel = new menuPanel(this);
        frame.remove(gameStart1v1);
        frame.add(menuPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void removeStart1v1() {
        menuPanel = new menuPanel(this);
        frame.remove(gameStart1v1WithLimits);
        frame.add(menuPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void startGameNoLimits(String player1Name, String player1Color, String player2Name, String player2Color) {
        gameNoLimits = new MainPanelNoLimit(player1Name, player1Color, player2Name, player2Color, this);
        frame.remove(gameStart1v1);
        frame.add(gameNoLimits);
        frame.revalidate();
        frame.repaint();
    }

    public void startGameWithLimits(String player1Name, String player1Color, String player2Name, String player2Color,
                                    int totalTimeLimit, int perMoveTimeLimit) {
        frame.remove(gameStart1v1WithLimits);
        gameWithLimits = new MainPanel(player1Name, player1Color, player2Name, player2Color, totalTimeLimit,
                perMoveTimeLimit, this);
        frame.add(gameWithLimits);
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    public void returnToMenuWithLimits() {
        menuPanel = new menuPanel(this);
        frame.remove(gameWithLimits);
        frame.add(menuPanel);
        //frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    public void startNewGameWithLimits(String firstPlayerName, String firstPlayerColor, String secondPlayerName,
                                       String secondPlayerColor, int totalTimeLim, int perMoveLim) {
        frame.remove(gameWithLimits);
        gameWithLimits = new MainPanel(firstPlayerName, firstPlayerColor, secondPlayerName, secondPlayerColor,
                totalTimeLim, perMoveLim, this);
        frame.add(gameWithLimits);
        //frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    public void returnToMenuWithNoLimits() {
        menuPanel = new menuPanel(this);
        frame.remove(gameNoLimits);
        frame.add(menuPanel);
        //frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    public void startNewGameWithNoLimits(String firstPlayerName, String firstPlayerColor, String secondPlayerName,
                                       String secondPlayerColor) {
        frame.remove(gameNoLimits);
        gameNoLimits = new MainPanelNoLimit(firstPlayerName, firstPlayerColor, secondPlayerName, secondPlayerColor, this);
        frame.add(gameNoLimits);
        //frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}

