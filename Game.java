package com.connectfour;

import java.io.*;
import java.util.*;

public class Game {
    private final int gameBoardWidth;
    private final int gameBoardHeight;
    private int[][] gameBoard;
    private int[] columnDropPosition;
    private int sequence;
    public int startX, startY, endX, endY, directionX, directionY, winningDiscX, winningDiscY;

    public Game() {
        gameBoardWidth = 7;
        gameBoardHeight = 6;
        gameBoard = new int[6][7];
        for(int i = 0; i < gameBoardHeight; i++){
            for(int j = 0; j < gameBoardWidth; j++){
                gameBoard[i][j] = 0;
            }
        }
        columnDropPosition = new int[7];
        for(int j = 0; j < gameBoardWidth; j++){
            columnDropPosition[j] = gameBoardHeight - 1;
        }
    }

    public boolean isDropPositionValid(int dropColumn) {
        return columnDropPosition[dropColumn] >= 0;
    }

    public boolean dropDiscOnColumn(int dropColumn, int playerNumber) {
        if(!isDropPositionValid(dropColumn)) {
            return false;
        }
        gameBoard[columnDropPosition[dropColumn]][dropColumn] = playerNumber;
        columnDropPosition[dropColumn]--;
        return true;
    }

    public boolean isBoardFilled() {
        for(int j = 0; j < gameBoardWidth; j++) {
            if(columnDropPosition[j] >= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameWon(int lastLine, int lastColumn) {
        winningDiscX = lastLine;
        winningDiscY = lastColumn;
        sequence = 1;
        int playerNumber = gameBoard[lastLine][lastColumn];
        int currentLine, currentColumn;
        currentLine = lastLine - 1;
        currentColumn = lastColumn;
        directionX = 1;
        directionY = 0;
        startY = lastColumn;
        endY = lastColumn;
        while(currentLine>=0 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentLine--;
            sequence++;
        }
        startX = currentLine + 1;
        currentLine = lastLine + 1;
        while(currentLine<6 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentLine++;
            sequence++;
        }
        endX = currentLine - 1;
        if(sequence>=4) {
            return true;
        }

        sequence = 1;
        currentLine = lastLine;
        currentColumn = lastColumn - 1;
        startX = lastLine;
        endX = lastLine;
        directionX = 0;
        directionY = 1;
        while(currentColumn>=0 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentColumn--;
            sequence++;
        }
        startY = currentColumn + 1;
        currentColumn = lastColumn + 1;
        while(currentColumn<7 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentColumn++;
            sequence++;
        }
        endY = currentColumn - 1;
        if(sequence>=4) {
            return true;
        }

        sequence = 1;
        currentLine = lastLine - 1;
        currentColumn = lastColumn + 1;
        directionX = 1;
        directionY = -1;
        while(currentColumn<7 && currentLine>=0 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentLine--;
            currentColumn++;
            sequence++;
        }
        startX = currentLine + 1;
        startY = currentColumn - 1;
        currentLine = lastLine + 1;
        currentColumn = lastColumn - 1;
        while(currentLine<6 && currentColumn >= 0 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentLine++;
            currentColumn--;
            sequence++;
        }
        endX = currentLine - 1;
        endY = currentColumn + 1;
        if(sequence>=4) {
            return true;
        }

        sequence = 1;
        currentLine = lastLine - 1;
        currentColumn = lastColumn - 1;
        directionX = 1;
        directionY = 1;
        while(currentColumn>=0 && currentLine>=0 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentLine--;
            currentColumn--;
            sequence++;
        }
        startX = currentLine + 1;
        startY = currentColumn + 1;
        currentLine = lastLine + 1;
        currentColumn = lastColumn + 1;
        while(currentLine<6 && currentColumn < 7 && gameBoard[currentLine][currentColumn] == playerNumber) {
            currentLine++;
            currentColumn++;
            sequence++;
        }
        endX = currentLine - 1;
        endY =  currentColumn - 1;
        if(sequence>=4) {
            return true;
        }

        return false;
    }

    public int getDropPosition(int column) {
        return columnDropPosition[column];
    }

    private void setDropPosition(int column, int value) {
        columnDropPosition[column] = value;
    }

    public int getMostLeftValidColumn() {
        for(int j = 0; j < gameBoardWidth; j++) {
            if(columnDropPosition[j] >= 0) {
                return j;
            }
        }
        return -1;
    }
}
