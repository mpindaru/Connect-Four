package com.connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import static java.lang.Math.min;

class GameBoardComponent extends JPanel {

    private static final int OPENING_RECT_X = 40;
    private static final int OPENING_RECT_Y = 40;
    private static final int OPENING_RECT_WIDTH = 280;
    private static final int OPENING_RECT_HEIGHT = 240;

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graph2 = (Graphics2D)g;
        int squareDimension = min(getWidth()/9, getHeight()/8);
        int circleDiameter = 3*squareDimension/4;
        Area a = new Area(new RoundRectangle2D.Float((int)(getWidth()/2-7*squareDimension/2), (int)(getHeight()/2
                -3*squareDimension), (int)(7*squareDimension), (int)(6*squareDimension), 10, 10));
        for(int i = -3; i <= 3; i++) {
            for(int j = -3; j <= 2; j++) {
                a.subtract(new Area(new Ellipse2D.Double((int) (getWidth() / 2 + i * squareDimension - circleDiameter / 2),
                        (int)(getHeight() / 2 + j * squareDimension + squareDimension / 2 - circleDiameter / 2),
                        (int) circleDiameter, (int) circleDiameter) {
                }));
            }
        }
        graph2.setColor(Color.BLACK);
        graph2.fill(a);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(OPENING_RECT_WIDTH + 2 * OPENING_RECT_X,
                OPENING_RECT_HEIGHT + 2 * OPENING_RECT_Y);
    }

}
