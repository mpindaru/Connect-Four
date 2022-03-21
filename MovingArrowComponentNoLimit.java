package com.connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.min;

public class MovingArrowComponentNoLimit extends JComponent {

    Point upperLeft, upperRight, midPoint;
    Polygon arrow;
    boolean isFirstTime = true;
    private int previousMidPointX;
    MainPanelNoLimit mainPanel;
    Game game;

    public void setMainPanel(MainPanelNoLimit panel) {
        mainPanel = panel;
    }

    public MovingArrowComponentNoLimit() {
        setOpaque(false);
        addMouseMotionListener(new MyMouseAdapter());
        addMouseListener(new MyMouseAdapter());
    }

    public void setGame(Game g) {
        game = g;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareDimension = min(getWidth()/9, getHeight()/8);
        int arrowHeight = 2*(getHeight()/2-3*squareDimension)/5;
        int arrowWidth = 3 * squareDimension / 4;
        Graphics2D g2d = (Graphics2D) g;
        if (isFirstTime) {
            midPoint = new Point (getWidth()/2-3*squareDimension, 7*(getHeight()/2-3*squareDimension)/8);
            upperLeft = new Point (midPoint.x-arrowWidth/2, midPoint.y-arrowHeight);
            upperRight = new Point (midPoint.x+arrowWidth/2, midPoint.y-arrowHeight);
            previousMidPointX = midPoint.x;
            int[] xCoordinates = {midPoint.x, upperLeft.x, upperRight.x};
            int[] yCoordinates = {midPoint.y, upperLeft.y, upperRight.y};
            arrow = new Polygon(xCoordinates, yCoordinates, 3);
            isFirstTime = false;
        }

        g2d.setColor(Color.black);
        g2d.fill(arrow);

    }

    private class MyMouseAdapter extends MouseAdapter {

        public Point getMidPointPosition(int cursorX) {
            int squareDimension = min(getWidth() / 9, getHeight() / 8);
            int circleDiameter = 3*squareDimension/4;
            int midPointY = 7 * (getHeight() / 2 - 3 * squareDimension) / 8;
            int leftGameBoardBorder = getWidth() / 2 - 7 * squareDimension / 2;
            if((cursorX - leftGameBoardBorder) % squareDimension == 0)
                return new Point(previousMidPointX, midPointY);
            int columnNumber = (cursorX - leftGameBoardBorder) / squareDimension;
            if(columnNumber < 0 || columnNumber > 6)
                return new Point(previousMidPointX, midPointY);
            int boardColumnStartingX = leftGameBoardBorder + columnNumber * squareDimension;
            int middleOfSquareX = boardColumnStartingX + squareDimension/2;
            if(cursorX >= middleOfSquareX-circleDiameter/2 && cursorX<=middleOfSquareX+circleDiameter/2)
                return new Point(middleOfSquareX, midPointY);
            return new Point(previousMidPointX, midPointY);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int squareDimension = min(getWidth() / 9, getHeight() /8 );
            int arrowHeight = 2 * (getHeight() / 2 - 3 * squareDimension) / 5;
            int arrowWidth = 3 * squareDimension / 4;
            midPoint = getMidPointPosition(e.getX());
            previousMidPointX = midPoint.x;
            upperLeft = new Point (midPoint.x-arrowWidth / 2, midPoint.y - arrowHeight);
            upperRight = new Point (midPoint.x+arrowWidth / 2, midPoint.y - arrowHeight);
            int[] xCoordinates = {midPoint.x, upperLeft.x, upperRight.x};
            int[] yCoordinates = {midPoint.y, upperLeft.y, upperRight.y};
            arrow = new Polygon(xCoordinates, yCoordinates, 3);
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int squareDimension = min(getWidth() / 9, getHeight() / 8);
            int leftGameBoardBorder = getWidth() / 2 - 7 * squareDimension / 2;
            int columnNumber = (e.getX() - leftGameBoardBorder) / squareDimension;
            if(columnNumber>=0 && columnNumber<=6 && game.isDropPositionValid(columnNumber)) {
                mainPanel.deleteMovingArrowPanel(getMidPointPosition(e.getX()), game.getDropPosition(columnNumber),
                        columnNumber);
            }

        }
    }
}
