package com.connectfour;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static java.lang.Math.min;

public class DroppingDiscComponentNoLimit extends JComponent {

    private int lastY = 0;
    private int discLeftX;
    private int discCenterX;
    private int discBottomY;
    private boolean continueMoving = true;
    private int reachedBottom = 0;
    private int currentY;
    private int circleSpeed;
    private int restingColumn;
    private int isDescending = 1;
    private int speedDecrease = 0;
    private int speedIncrease = 0;
    private int discTopY;
    private String discColour;
    private int restingLine;
    public boolean isWinningBall = false;
    MyThreadNoLimit t;

    public void setWinningDisk() {
        isWinningBall = true;
    }
    public void setDropPosition (int dropPosition) {
        restingLine = dropPosition;
    }

    public void setThread (MyThreadNoLimit th) {
        t = th;
    }

    public void setArrowCoordinates(Point arrowPosition, int height, int width) {
        discCenterX = arrowPosition.x;
        discBottomY = arrowPosition.y;
        int squareDimension = min(width/9, height/8);
        int circleDiameter = 3*squareDimension/4;
        int leftGameBoardBorder = width / 2 - 7 * squareDimension / 2;
        restingColumn = (discCenterX - leftGameBoardBorder) / squareDimension;
        lastY = discBottomY - circleDiameter;
    }

    public void setColour(String playerColour) {
        discColour = playerColour;
    }

    public void paintComponent(Graphics g) {

        HashMap<String, Color> colourMap = new HashMap<String, Color>();
        colourMap.put("RED", Color.RED);
        colourMap.put("GREEN", Color.GREEN);
        colourMap.put("BLUE", Color.BLUE);
        colourMap.put("CYAN", Color.CYAN);
        colourMap.put("MAGENTA", Color.MAGENTA);

        Graphics2D gg = (Graphics2D) g;
        int squareDimension = min(getWidth()/9, getHeight()/8);
        int circleDiameter = 3*squareDimension/4;
        int leftGameBoardBorder = getWidth() / 2 - 7 * squareDimension / 2;
        discTopY = getHeight() / 2 - (3-restingLine)*squareDimension + squareDimension/2
                - circleDiameter / 2;
        discCenterX = leftGameBoardBorder + restingColumn*squareDimension + squareDimension/2;
        discLeftX = discCenterX - circleDiameter/2;
        currentY = lastY;
        circleSpeed = isDescending * (squareDimension/4 - speedDecrease + speedIncrease);
        if(continueMoving) {
            if (reachedBottom > 1) circleSpeed/=2;
            currentY = lastY + circleSpeed;
            int bounceBackLimit = discTopY - (restingLine +1) * (2*squareDimension + circleDiameter/2) / 6;
            int secondBounceBackLimit = discTopY - (restingLine + 1) * (squareDimension + circleDiameter/2) / 6;
            if((currentY  >= discTopY) && (reachedBottom <=1)) {
                speedDecrease = 0;
                isDescending *= -1;
                reachedBottom ++;
            } else if(reachedBottom == 1 && (currentY <= bounceBackLimit)) {
                isDescending *= -1;
            } else if(reachedBottom == 2 && (currentY <= secondBounceBackLimit)) {
                isDescending *= -1;
            }
            else if((currentY >= discTopY) && (reachedBottom == 2)) {
                currentY = discTopY;
                continueMoving = false;
                t.stop();
            } else if (reachedBottom > 0 && isDescending == 1) {
                speedIncrease = squareDimension * currentY / bounceBackLimit / 14;
            } else if (reachedBottom > 0 && isDescending == -1) {
                speedDecrease = squareDimension * bounceBackLimit / currentY / 7;
            }
            gg.setColor(colourMap.get(discColour));
            gg.fillOval(discLeftX, currentY, circleDiameter, circleDiameter);
            lastY = currentY;
        } else if(isWinningBall) {
            gg.setColor(Color.YELLOW);
            gg.fillOval(discLeftX, discTopY, circleDiameter, circleDiameter);
        } else {
            gg.setColor(colourMap.get(discColour));
            gg.fillOval(discLeftX, discTopY, circleDiameter, circleDiameter);
        }

    }

}

