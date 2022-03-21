package com.connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Starting1v1GamePanelWithLimits extends JPanel {

    private JButton startGameButton;
    private GUI gui;
    public Starting1v1GamePanelWithLimits (GUI g) {

        gui = g;

        String[] discColors = {"RED", "GREEN", "BLUE", "MAGENTA", "CYAN"};

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel player1Label = new JLabel("P1 name and color");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        add(player1Label, c);

        JLabel player2Label = new JLabel("P2 name and color");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 0;
        add(player2Label, c);

        JTextField player1TextField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(player1TextField, c);

        JTextField player2TextField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        add(player2TextField, c);

        JLabel errorNamePlayer1 = new JLabel("Enter a name");
        errorNamePlayer1.setVisible(false);
        errorNamePlayer1.setForeground(Color.RED);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 2;
        add(errorNamePlayer1, c);

        JLabel errorNamePlayer2 = new JLabel("Enter a name");
        errorNamePlayer2.setVisible(false);
        errorNamePlayer2.setForeground(Color.RED);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 2;
        add(errorNamePlayer2, c);

        JComboBox player1ColorBox = new JComboBox(discColors);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 3;
        add(player1ColorBox, c);

        JComboBox player2ColorBox = new JComboBox(discColors);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 3;
        add(player2ColorBox, c);

        JLabel errorColor = new JLabel("Select different color");
        errorColor.setForeground(Color.RED);
        errorColor.setVisible(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.weightx = 0.0;
        c.gridx = 1;
        c.gridy = 4;
        add(errorColor, c);

        JLabel playerTotalTimeLabel = new JLabel("Player total time");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(70,0,0,0);
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 5;
        add(playerTotalTimeLabel, c);

        JLabel playerPerMoveTimeLabel = new JLabel("Player time/move");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(70,0,0,0);
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 5;
        add( playerPerMoveTimeLabel, c);

        JTextField playerTotalTimeTextField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 6;
        add(playerTotalTimeTextField, c);

        JTextField playerPerMoveTextField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 6;
        add(playerPerMoveTextField, c);

        JLabel errorTotalTime = new JLabel("Enter an integer");
        errorTotalTime.setVisible(false);
        errorTotalTime.setForeground(Color.RED);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 7;
        add(errorTotalTime, c);

        JLabel errorPerMoveTime = new JLabel("Enter an integer");
        errorPerMoveTime.setForeground(Color.RED);
        errorPerMoveTime.setVisible(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 7;
        add( errorPerMoveTime, c);

        startGameButton = new JButton("Start Game");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10,0,0,0);
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 8;
        add(startGameButton, c);
        startGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean canStartGame = true;
                String player1Name = player1TextField.getText();
                if(player1Name.equals("")) {
                    errorNamePlayer1.setVisible(true);
                    canStartGame = false;
                } else {
                    errorNamePlayer1.setVisible(false);
                }
                String player2Name = player2TextField.getText();
                if(player2Name.equals("")) {
                    errorNamePlayer2.setVisible(true);
                    canStartGame = false;
                } else {
                    errorNamePlayer2.setVisible(false);
                }
                String player1Color = String.valueOf(player1ColorBox.getSelectedItem());
                String player2Color = String.valueOf(player2ColorBox.getSelectedItem());
                if(player1Color.equals(player2Color)) {
                    errorColor.setVisible(true);
                    canStartGame = false;
                } else {
                    errorColor.setVisible(false);
                }
                String totalTimeLimit = playerTotalTimeTextField.getText();
                if(!isNumeric(totalTimeLimit)) {
                    errorTotalTime.setVisible(true);
                    canStartGame = false;
                } else {
                    errorTotalTime.setVisible(false);
                }
                String perMoveTimeLimit = playerPerMoveTextField.getText();
                if(!isNumeric(perMoveTimeLimit)) {
                    errorPerMoveTime.setVisible(true);
                    canStartGame = false;
                } else {
                    errorPerMoveTime.setVisible(false);
                }
                if(canStartGame) {
                    int totalTime = Integer.parseInt(totalTimeLimit);
                    int perMoveTime = Integer.parseInt(perMoveTimeLimit);
                    gui.startGameWithLimits(player1Name, player1Color, player2Name, player2Color, totalTime,
                            perMoveTime);
                }
            }
        });

        JButton returnButton = new JButton("Return to menu");
        c.fill = GridBagConstraints.NONE;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.insets = new Insets(10,0,0,0);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 8;
        add(returnButton, c);
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui.removeStart1v1();
            }
        });
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
            if(d<0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
