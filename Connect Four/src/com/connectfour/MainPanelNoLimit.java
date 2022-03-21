package com.connectfour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

import static java.lang.Math.min;

public class MainPanelNoLimit extends JPanel {
    GameBoardComponent boardComponent;
    MovingArrowComponentNoLimit arrowComponent;
    Game game;
    int currentPlayerTurn;
    Vector<String> playerColors = new Vector<>();
    DroppingDiscComponentNoLimit[][] discs;
    private int currentColumn, currentLine;
    private String p1Name;
    private String p2Name;
    private String p1Color;
    private String p2Color;
    private GUI gui;

    public MainPanelNoLimit(String firstPlayerName, String firstPlayerColor, String secondPlayerName,
                            String secondPlayerColor, GUI g) {

        gui = g;
        p1Color = firstPlayerColor;
        p2Color = secondPlayerColor;
        p1Name = firstPlayerName;
        p2Name = secondPlayerName;
        discs = new DroppingDiscComponentNoLimit[6][7];
        setLayout(new OverlayLayout(this) );
        currentPlayerTurn = 1;
        playerColors.add(firstPlayerColor);
        playerColors.add(secondPlayerColor);
        boardComponent = new GameBoardComponent();
        game = new Game();
        arrowComponent = new MovingArrowComponentNoLimit();
        arrowComponent.setGame(game);
        boardComponent.setOpaque(false);
        add(boardComponent, BorderLayout.CENTER);
        add(arrowComponent, BorderLayout.CENTER);
        arrowComponent.setMainPanel(this);
    }

    public void deleteMovingArrowPanel(Point arrowPosition, int dropPosition, int columnNumber) {
        game.dropDiscOnColumn(columnNumber, currentPlayerTurn);
        remove(arrowComponent);
        DroppingDiscComponentNoLimit discComponent = new DroppingDiscComponentNoLimit();
        discComponent.setOpaque(false);
        add(discComponent);
        revalidate();
        repaint();
        currentColumn = columnNumber;
        currentLine = dropPosition;
        discs[dropPosition][columnNumber] = discComponent;
        MyThreadNoLimit animationThread = new MyThreadNoLimit(discComponent, this, arrowPosition,
                playerColors.get(currentPlayerTurn - 1), dropPosition);
        currentPlayerTurn = 3-currentPlayerTurn;
    }

    public void animateWinningBalls() {
        for(int i=game.startX, j=game.startY; i!= game.endX||j!=game.endY; i+= game.directionX, j+= game.directionY) {
            discs[i][j].setWinningDisk();
        }
        discs[game.endX][game.endY].setWinningDisk();
        discs[game.winningDiscX][game.winningDiscY].repaint();
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
        add(arrowComponent);
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
                gui.returnToMenuWithNoLimits();
            }
        });
        newGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                drawFrame.setVisible(false);
                gui.startNewGameWithNoLimits(p1Name, p1Color, p2Name, p2Color);
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
                gui.returnToMenuWithNoLimits();
            }
        });
        newGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                drawFrame.setVisible(false);
                gui.startNewGameWithNoLimits(p1Name, p1Color, p2Name, p2Color);
            }
        });
    }

}

class MyThreadNoLimit implements Runnable {

    private boolean exit;
    Thread t;
    private DroppingDiscComponentNoLimit discComponent;
    private MainPanelNoLimit panel;
    private String playerColour;

    MyThreadNoLimit(DroppingDiscComponentNoLimit discComp, MainPanelNoLimit mainPanel, Point arrowPosition, String pColour,
                    int dropPosition)
    {
        panel = mainPanel;
        discComponent = discComp;
        playerColour = pColour;
        t = new Thread(this);
        discComponent.setColour(playerColour);
        discComponent.setThread(this);
        discComponent.setDropPosition(dropPosition);
        discComponent.setArrowCoordinates(arrowPosition, panel.getHeight(), panel.getWidth());
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
