package com.connectfour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

import static java.lang.Math.min;

public class MainPanel extends JPanel {
    GameBoardComponent boardComponent;
    MovingArrowComponent arrowComponent;
    Game game;
    int currentPlayerTurn;
    Vector<String> playerColors = new Vector<>();
    JPanel panel;
    Stopwatch currentPlayerStopwatch;
    StopwatchTopLeft player1StopwatchTop;
    StopwatchTopRight player2StopwatchTop;
    StaticCountdownLabel player1Label;
    StaticCountdownLabel player2Label;
    DroppingDiscComponent[][] discs;
    private int timeRemainingPlayer1;
    private int timeRemainingPlayer2;
    private int currentLine;
    private int currentColumn;
    private int totalTimeLimit;
    private int perMoveTimeLimit;
    private String p1Name;
    private String p2Name;
    private String p1Colors;
    private String p2Colors;
    private GUI gui;
    private boolean gameOver = false;

    public MainPanel(String firstPlayerName, String firstPlayerColor, String secondPlayerName,
                     String secondPlayerColor, int totalTimeLim, int perMoveLim, GUI g) {

        gui = g;
        p1Name= firstPlayerName;
        p2Name = secondPlayerName;
        p1Colors = firstPlayerColor;
        p2Colors = secondPlayerColor;
        totalTimeLimit = totalTimeLim;
        perMoveTimeLimit = perMoveLim;
        discs = new DroppingDiscComponent[6][7];
        timeRemainingPlayer1 = totalTimeLim;
        timeRemainingPlayer2 = totalTimeLim;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panel = new JPanel();
        currentPlayerTurn = 1;
        playerColors.add(firstPlayerColor);
        playerColors.add(secondPlayerColor);
        boardComponent = new GameBoardComponent();
        game = new Game();
        arrowComponent = new MovingArrowComponent();
        arrowComponent.setGame(game);
        boardComponent.setOpaque(false);
        panel.setLayout( new OverlayLayout(panel) );
        panel.add(boardComponent, BorderLayout.CENTER);
        panel.add(arrowComponent, BorderLayout.CENTER);
        arrowComponent.setMainPanel(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        player1StopwatchTop = new StopwatchTopLeft(totalTimeLim, playerColors.get(0), this);
        add(player1StopwatchTop, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        player2Label = new StaticCountdownLabel(totalTimeLim, playerColors.get(1));
        add(player2Label, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        currentPlayerStopwatch = new Stopwatch(perMoveLim, playerColors.get(0), this, game);
        add(currentPlayerStopwatch, c);
        c.fill=GridBagConstraints.BOTH;
        c.anchor=GridBagConstraints.CENTER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        add(panel, c);
    }

    public void deleteMovingArrowPanel(Point arrowPosition, int dropPosition, int columnNumber) {
        if(currentPlayerTurn == 1) {
            player1StopwatchTop.cancelStopwatch();
        } else {
            player2StopwatchTop.cancelStopwatch();
        }
        currentPlayerStopwatch.cancelStopwatch();
        game.dropDiscOnColumn(columnNumber, currentPlayerTurn);
        panel.remove(arrowComponent);
        DroppingDiscComponent discComponent = new DroppingDiscComponent();
        discComponent.setOpaque(false);
        panel.add(discComponent);
        revalidate();
        repaint();
        currentColumn = columnNumber;
        currentLine = dropPosition;
        discs[dropPosition][columnNumber] = discComponent;
        MyThread animationThread = new MyThread(discComponent, this, arrowPosition,
                playerColors.get(currentPlayerTurn - 1), dropPosition);
        currentPlayerTurn = 3-currentPlayerTurn;
    }

    public void setGameOver() {
        gameOver = true;
    }

    public void animateWinningBalls() {
        for(int i=game.startX, j=game.startY; i!= game.endX||j!=game.endY; i+= game.directionX, j+= game.directionY) {
            discs[i][j].setWinningDisk();
        }
        discs[game.endX][game.endY].setWinningDisk();
        discs[game.winningDiscX][game.winningDiscY].repaint();
    }

    public void addLeftMostBall() {
        if(currentPlayerTurn == 1) {
            player1StopwatchTop.cancelStopwatch();
        } else {
            player2StopwatchTop.cancelStopwatch();
        }
        currentPlayerStopwatch.cancelStopwatch();
        int columnNumber = game.getMostLeftValidColumn();
        int dropPosition = game.getDropPosition(columnNumber);
        game.dropDiscOnColumn(columnNumber, currentPlayerTurn);
        panel.remove(arrowComponent);
        DroppingDiscComponent discComponent = new DroppingDiscComponent();
        discComponent.setOpaque(false);
        panel.add(discComponent);
        revalidate();
        repaint();
        currentColumn = columnNumber;
        currentLine = dropPosition;
        discs[dropPosition][columnNumber] = discComponent;
        MyThreadLeftMost animationThreadLeftMost = new MyThreadLeftMost(discComponent, this,
                playerColors.get(currentPlayerTurn - 1), dropPosition, columnNumber);
        currentPlayerTurn = 3-currentPlayerTurn;
    }

    public void addArrowComponent() {
        if(game.isBoardFilled()) {
            popUpIsDraw();
            return;
        }
        if(game.isGameWon(currentLine, currentColumn)) {
            discs[currentLine][currentColumn].setWinningDisk();
            animateWinningBalls();
            popUpIsWon(currentPlayerTurn);
            return;
        }
        panel.add(arrowComponent);
        panel.remove(currentPlayerStopwatch);
        int secondsRemaining1, secondsRemaining2;
        GridBagConstraints c = new GridBagConstraints();
        if(currentPlayerTurn == 2) {
            panel.remove(player1StopwatchTop);
            secondsRemaining1 = player1StopwatchTop.getInterval();
            secondsRemaining2 = player2Label.getInterval();
            panel.remove(player2Label);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 0;
            player1Label = new StaticCountdownLabel(secondsRemaining1, playerColors.get(0));
            add(player1Label, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 2;
            c.gridy = 0;
            player2StopwatchTop = new StopwatchTopRight(secondsRemaining2, playerColors.get(1), this);
            add(player2StopwatchTop, c);
        } else {
            panel.remove(player2StopwatchTop);
            secondsRemaining2 = player2StopwatchTop.getInterval();
            panel.remove(player1Label);
            secondsRemaining1 = player1Label.getInterval();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 0;
            player1StopwatchTop = new StopwatchTopLeft(secondsRemaining1, playerColors.get(0), this);
            add(player1StopwatchTop, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 2;
            c.gridy = 0;
            player2Label = new StaticCountdownLabel(secondsRemaining2, playerColors.get(1));
            add(player2Label, c);
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        currentPlayerStopwatch = new Stopwatch(perMoveTimeLimit, playerColors.get(currentPlayerTurn-1), this, game);
        add(currentPlayerStopwatch, c);
        panel.revalidate();
        panel.repaint();
    }

    public void popUpIsDraw() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton newGameButton = new JButton("New Game");
        JButton returnButton = new JButton("Return to menu");
        JLabel message = new JLabel("Game ended in draw!");
        Font bigFont = message.getFont().deriveFont(Font.PLAIN, 20f);
        message.setFont(bigFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 0;
        buttonPanel.add(message, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        buttonPanel.add(newGameButton, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        buttonPanel.add(returnButton, c);
        JFrame drawFrame = new JFrame("Game ended in draw");
        drawFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        drawFrame.setLocationByPlatform(true);
        drawFrame.setVisible(true);
        drawFrame.add(buttonPanel);
        drawFrame.pack();
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                drawFrame.setVisible(false);
                gui.returnToMenuWithLimits();
            }
        });
        newGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                drawFrame.setVisible(false);
                gui.startNewGameWithLimits(p1Name, p1Colors, p2Name, p2Colors, totalTimeLimit, perMoveTimeLimit);
            }
        });
    }

    public void popUpIsWon(int playerNumber) {
        GridBagConstraints c = new GridBagConstraints();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton newGameButton = new JButton("New Game");
        JButton returnButton = new JButton("Return to menu");
        String winningPlayerName;
        if(playerNumber==1)
            winningPlayerName = p2Name;
        else {
            winningPlayerName = p1Name;
        }
        String winMessage = "Congratulations, " + winningPlayerName + "!";
        JLabel message = new JLabel(winMessage);
        Font bigFont = message.getFont().deriveFont(Font.PLAIN, 20f);
        message.setFont(bigFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 0;
        buttonPanel.add(message, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        buttonPanel.add(newGameButton, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        buttonPanel.add(returnButton, c);
        JFrame drawFrame = new JFrame(winMessage);
        drawFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        drawFrame.setLocationByPlatform(true);
        drawFrame.setVisible(true);
        drawFrame.add(buttonPanel);
        drawFrame.pack();
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                drawFrame.setVisible(false);
                gui.returnToMenuWithLimits();
            }
        });
        newGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                drawFrame.setVisible(false);
                gui.startNewGameWithLimits(p1Name, p1Colors, p2Name, p2Colors, totalTimeLimit, perMoveTimeLimit);
            }
        });
    }

    public void cancelAllTimersLeft() {
        panel.remove(arrowComponent);
        if(player2StopwatchTop!=null){
            player2StopwatchTop.cancelStopwatch();
        }
        currentPlayerStopwatch.cancelStopwatch();
    }

    public void cancelAllTimersRight() {
        panel.remove(arrowComponent);
        if(player1StopwatchTop!=null) {
            player1StopwatchTop.cancelStopwatch();
        }
        currentPlayerStopwatch.cancelStopwatch();
    }
}

class MyThreadLeftMost implements Runnable {

    private boolean exit;
    Thread t;
    private DroppingDiscComponent discComponent;
    private MainPanel panel;
    private String playerColour;

    MyThreadLeftMost(DroppingDiscComponent discComp, MainPanel mainPanel, String pColour, int dropPosition, int column)
    {
        panel = mainPanel;
        discComponent = discComp;
        playerColour = pColour;
        t = new Thread(this);
        discComponent.setColour(playerColour);
        discComponent.setThreadLeftMost(this);
        discComponent.setDropPosition(dropPosition);
        discComponent.setLeftMostCoordinates(mainPanel.panel.getHeight(), mainPanel.panel.getWidth(), column);
        exit = false;
        t.start();
    }

    public void run()
    {
        while (!exit) {
            discComponent.repaint();
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
        panel.addArrowComponent();
    }
}

class MyThread implements Runnable {

    private boolean exit;
    Thread t;
    private DroppingDiscComponent discComponent;
    private MainPanel panel;
    private String playerColour;

    MyThread(DroppingDiscComponent discComp, MainPanel mainPanel, Point arrowPosition, String pColour, int dropPosition)
    {
        panel = mainPanel;
        discComponent = discComp;
        playerColour = pColour;
        t = new Thread(this);
        discComponent.setColour(playerColour);
        discComponent.setThread(this);
        discComponent.setDropPosition(dropPosition);
        discComponent.setArrowCoordinates(arrowPosition, mainPanel.panel.getHeight(), mainPanel.panel.getWidth());
        exit = false;
        t.start();
    }

    public void run()
    {
        while (!exit) {
            discComponent.repaint();
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
        panel.addArrowComponent();
    }
}

class Stopwatch extends JPanel{
    private JLabel text;
    static int interval;
    static Timer timer;
    private MainPanel mainPanel;
    private Game game;

    public Stopwatch(int sec, String playerColour, MainPanel panel, Game g) {

        mainPanel = panel;
        game = g;

        HashMap<String, Color> colourMap = new HashMap<String, Color>();
        colourMap.put("RED", Color.RED);
        colourMap.put("GREEN", Color.GREEN);
        colourMap.put("BLUE", Color.BLUE);
        colourMap.put("CYAN", Color.CYAN);
        colourMap.put("MAGENTA", Color.MAGENTA);

        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = sec;
        text = new JLabel();
        Font bigFont = text.getFont().deriveFont(Font.PLAIN, 35f);
        text.setFont(bigFont);
        text.setOpaque(true);
        text.setBackground(colourMap.get(playerColour));
        text.setText("Current time: " + String.valueOf(interval));
        add(text);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                text.setText("Current time: " + String.valueOf(setInterval()));
            }
        }, delay, period);
    }

    private int setInterval() {
        if (interval == 1) {

            timer.cancel();
            mainPanel.addLeftMostBall();
        }
        return --interval;
    }

    public void cancelStopwatch() {
        timer.cancel();
    }
}

class StopwatchTopLeft extends JPanel{
    private JLabel text;
    static int interval;
    static Timer timer;
    MainPanel panel;

    public StopwatchTopLeft(int sec, String playerColour, MainPanel p) {

        panel = p;
        HashMap<String, Color> colourMap = new HashMap<String, Color>();
        colourMap.put("RED", Color.RED);
        colourMap.put("GREEN", Color.GREEN);
        colourMap.put("BLUE", Color.BLUE);
        colourMap.put("CYAN", Color.CYAN);
        colourMap.put("MAGENTA", Color.MAGENTA);

        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = sec;
        text = new JLabel();
        Font bigFont = text.getFont().deriveFont(Font.PLAIN, 40f);
        text.setFont(bigFont);
        text.setOpaque(true);
        text.setBackground(colourMap.get(playerColour));
        text.setText(String.valueOf(interval));
        add(text);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                text.setText(String.valueOf(setInterval()));
            }
        }, delay, period);
    }

    public int setInterval() {
        if (interval == 1) {
            panel.cancelAllTimersLeft();
            timer.cancel();
            panel.popUpIsWon(1);
        }

        return --interval;
    }

    public void cancelStopwatch() {
        timer.cancel();
    }

    public int getInterval() {
        return interval;
    }
}

class StopwatchTopRight extends JPanel{
    private JLabel text;
    static int interval;
    static Timer timer;
    private MainPanel panel;

    public StopwatchTopRight(int sec, String playerColour, MainPanel p) {

        panel = p;
        HashMap<String, Color> colourMap = new HashMap<String, Color>();
        colourMap.put("RED", Color.RED);
        colourMap.put("GREEN", Color.GREEN);
        colourMap.put("BLUE", Color.BLUE);
        colourMap.put("CYAN", Color.CYAN);
        colourMap.put("MAGENTA", Color.MAGENTA);

        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = sec;
        text = new JLabel();
        Font bigFont = text.getFont().deriveFont(Font.PLAIN, 40f);
        text.setFont(bigFont);
        text.setOpaque(true);
        text.setBackground(colourMap.get(playerColour));
        text.setText(String.valueOf(interval));
        add(text);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                text.setText(String.valueOf(setInterval()));
            }
        }, delay, period);
    }

    public int setInterval() {
        if (interval == 1) {
            panel.cancelAllTimersRight();
            timer.cancel();
            panel.popUpIsWon(2);
        }
        return --interval;
    }

    public void cancelStopwatch() {
        timer.cancel();
    }

    public int getInterval() {
        return interval;
    }
}

class StaticCountdownLabel extends JPanel {

    private JLabel text;
    int secs;

    public StaticCountdownLabel(int seconds, String playerColour) {
        secs=seconds;
        text = new JLabel();
        HashMap<String, Color> colourMap = new HashMap<String, Color>();
        colourMap.put("RED", Color.RED);
        colourMap.put("GREEN", Color.GREEN);
        colourMap.put("BLUE", Color.BLUE);
        colourMap.put("CYAN", Color.CYAN);
        colourMap.put("MAGENTA", Color.MAGENTA);
        Font bigFont = text.getFont().deriveFont(Font.PLAIN, 40f);
        text.setFont(bigFont);
        text.setOpaque(true);
        text.setBackground(colourMap.get(playerColour));
        text.setText(String.valueOf(seconds));
        add(text);
    }

    public int getInterval() {
        return secs;
    }

}