package com.company;

import java.io.Serializable;

public class Board implements Serializable {

    public int dim;
    public char uncappedChar;
    public char uncoveredChar;
    public char board[][];
    public boolean state = true;
    public boolean availableTurn;

    public Board(int dim, char uncappedChar, char uncoveredChar) {

        this.dim = dim;
        this.uncappedChar = uncappedChar;
        this.uncoveredChar = uncoveredChar;

        board = new char[dim][dim];

        for (int i = 1; i < dim; i++)
            for (int j = 1; j < dim; j++)
                board[i][j] = uncappedChar;

        for (int i = 1; i < dim; i++)
            board[i][0] = String.valueOf(i).charAt(0);

        for (int j = 1; j < dim; j++)
            board[0][j] = String.valueOf(j).charAt(0);
    }

    public void printBoard() {

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    void updateBoard(int x, int y) {
        board[x][y] = uncoveredChar;
    }

    void addShipP1(int x, int y){
        board[x][y] = 'x';
    }
    void addShipP2(int x, int y){
        board[x][y] = 'x';
    }

    public void matchState(boolean state) {
        this.state = state;
    }
    public boolean gameState() {
        return state;
    }
}
