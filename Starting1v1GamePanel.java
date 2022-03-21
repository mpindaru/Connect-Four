package com.connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Starting1v1GamePanel extends JPanel {

    private JButton startGameButton;
    private JButton returnButton;
    private JLabel player1Label;
    private JLabel player2Label;
    private JTextField player1TextField;
    private JTextField player2TextField;
    private JLabel errorNamePlayer1;
    private JLabel errorNamePlayer2;
    private JComboBox player1ColorBox;
    private JComboBox player2ColorBox;
    private JLabel errorColor;
    private GUI gui;

    public Starting1v1GamePanel(GUI g) {

        gui = g;
        String[] discColors = {"RED", "GREEN", "BLUE", "MAGENTA", "CYAN"};

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        player1Label = new JLabel("P1 name and color");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(100,0,0,0);
        add(player1Label, c);

        player2Label = new JLabel("P2 name and color");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(100,0,0,0);
        add(player2Label, c);

        player1TextField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,0,0,0);
        add(player1TextField, c);

        player2TextField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        add(player2TextField, c);

        errorNamePlayer1 = new JLabel("Enter a name");
        errorNamePlayer1.setVisible(false);
        errorNamePlayer1.setForeground(Color.RED);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 2;
        add(errorNamePlayer1, c);

        errorNamePlayer2 = new JLabel("Enter a name");
        errorNamePlayer2.setVisible(false);
        errorNamePlayer2.setForeground(Color.RED);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 2;
        add(errorNamePlayer2, c);

        player1ColorBox = new JComboBox(discColors);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 3;
        add(player1ColorBox, c);

        player2ColorBox = new JComboBox(discColors);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 3;
        add(player2ColorBox, c);

        errorColor = new JLabel("Select different color");
        errorColor.setVisible(false);
        errorColor.setForeground(Color.RED);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(10,0,0,0);
        add(errorColor, c);

        startGameButton = new JButton("Start Game");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10,0,0,0);
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 5;
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
                if(canStartGame) {
                    gui.startGameNoLimits(player1Name, player1Color, player2Name, player2Color);
                }
            }
        });

        returnButton = new JButton("Return to menu");
        c.fill = GridBagConstraints.NONE;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.insets = new Insets(10,0,0,0);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        add(returnButton, c);
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui.removeStart1v1NoLimit();
            }
        });
    }

}
