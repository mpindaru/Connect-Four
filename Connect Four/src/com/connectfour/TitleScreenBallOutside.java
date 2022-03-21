package com.connectfour;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static java.lang.Math.min;

public class TitleScreenBallOutside extends JPanel {

    private int currentY;
    private int column;
    private int lastY;
    private int circleSpeedY;
    private boolean continueMoving = true;
    MyBallThread t;

    public void setThread (MyBallThread th) {
        t = th;
    }


    public TitleScreenBallOutside(int ballColumn) {
        column = ballColumn;
        lastY = getHeight()/16 - 3*min(getWidth()/9, getHeight()/8)/8;
    }

    public void paintComponent(Graphics g) {

        Graphics2D gg = (Graphics2D) g;
        int squareDimension = min(getWidth()/9, getHeight()/8);
        int circleDiameter = 3*squareDimension/4;
        int bottomGameBoardY = getHeight()/2 + 3 * squareDimension - squareDimension/8;
        int ballLeftX =  getWidth() / 2 - 7 * squareDimension / 2 + column*squareDimension + squareDimension/2
                - circleDiameter/2;
        if(continueMoving) {

            circleSpeedY = squareDimension/8;
            currentY = lastY + circleSpeedY;

            if(currentY + circleDiameter >= bottomGameBoardY) {
                continueMoving = false;
                t.stop();
            }

            gg.setColor(Color.red);
            gg.fillOval(ballLeftX, currentY, circleDiameter, circleDiameter);
            lastY = currentY;
        }

        else {
            int bottomY = getHeight() / 2 + 5*(min(getWidth()/9, getHeight()/8))/2 -
                    3*(min(getWidth()/9, getHeight()/8))/8;
            gg.setColor(Color.RED);
            gg.fillOval(ballLeftX, bottomY, circleDiameter, circleDiameter);
        }

    }
}

