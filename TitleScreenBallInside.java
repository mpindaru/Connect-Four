package com.connectfour;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.min;

public class TitleScreenBallInside extends JPanel {

    private int currentY;
    private int column;
    private int lastY;
    private int circleSpeedY;
    private boolean continueMoving = false;
    private boolean hasMoved = false;
    private int lineLimit;
    Color ballColor;
    MyInsideBallThread t;

    public void setThread (MyInsideBallThread th) {
        t = th;
    }

    public int getColumn () {
        return column;
    }

    public TitleScreenBallInside(int ballColumn) {
        column = ballColumn;
        lastY = getHeight() / 2 + 5*(min(getWidth()/9, getHeight()/8))/2 -
                3*(min(getWidth()/9, getHeight()/8))/8;
        if(column % 2 == 0) {
            ballColor = Color.RED;
        } else {
            ballColor = Color.BLUE;
        }
        if(column == 1 || column == 5) {
            lineLimit = 2;
        } else if(column == 2 || column == 4) {
            lineLimit = 4;
        } else {
            lineLimit = 6;
        }
    }

    public void setBallMoving() {
        continueMoving = true;
        hasMoved = true;
        lastY = getHeight() / 2 + 5*(min(getWidth()/9, getHeight()/8))/2 -
                3*(min(getWidth()/9, getHeight()/8))/8;
        circleSpeedY =  - min(getWidth()/9, getHeight()/8) / 10;
        System.out.println(circleSpeedY);
    }

    public void paintComponent(Graphics g) {

        Graphics2D gg = (Graphics2D) g;
        int squareDimension = min(getWidth()/9, getHeight()/8);
        int circleDiameter = 3*squareDimension/4;
        int ballLeftX =  getWidth() / 2 - 7 * squareDimension / 2 + column*squareDimension + squareDimension/2
                - circleDiameter/2;
        int topLimit = getHeight() /2 + (3-lineLimit) * squareDimension + squareDimension /2 - circleDiameter/2;

        if(continueMoving) {

            circleSpeedY = - min(getWidth()/9, getHeight()/8) / 8;
            currentY = lastY + circleSpeedY;

            if(currentY <= topLimit) {
                continueMoving = false;
                t.stop();
            }

            gg.setColor(ballColor);
            gg.fillOval(ballLeftX, currentY, circleDiameter, circleDiameter);
            lastY = currentY;
        } else if(hasMoved) {
            gg.setColor(ballColor);
            gg.fillOval(ballLeftX, topLimit, circleDiameter, circleDiameter);
        } else {
            int bottomY = getHeight() / 2 + 5*(min(getWidth()/9, getHeight()/8))/2 -
                    3*(min(getWidth()/9, getHeight()/8))/8;

            gg.setColor(ballColor);
            gg.fillOval(ballLeftX, bottomY, circleDiameter, circleDiameter);
        }

    }

}
