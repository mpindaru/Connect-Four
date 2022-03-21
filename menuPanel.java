package com.connectfour;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class menuPanel extends JPanel {

    private GUI gui;

    public menuPanel(GUI g) {
        gui=g;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        add(new JLabel("<html><h1><strong><i>Four in a row </i></strong></h1><hr></html>"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttons = new JPanel(new GridBagLayout());
        JButton start1v1Game = new JButton("Start 1 versus 1 game");
        start1v1Game.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui.start1v1Game();
            }
        });

        JButton start1v1GameWithLimits = new JButton("Start 1 versus 1 game with limits");
        start1v1GameWithLimits.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui.start1v1GameWithLimits();
            }
        });

        JButton titleScreenButton = new JButton("Return to title screen");
        titleScreenButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui.displayTitleScreen();
            }
        });
        JButton exitButton = new JButton("Exit game");
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        buttons.add(start1v1Game, gbc);
        buttons.add(start1v1GameWithLimits, gbc);
        buttons.add(titleScreenButton, gbc);
        buttons.add(exitButton, gbc);

        gbc.weighty = 1;
        add(buttons, gbc);
    }
}
