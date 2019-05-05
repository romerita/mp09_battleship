package com.company;

import java.io.Serializable;

public class Ship implements Serializable {

    int shipsP1 = 0;
    int shipsP2 = 0;
    Board board1;
    Board board2;
    public boolean defeat = true;

    public void printShip(Board boardJ1, Board boardJ2) {

        board1 = boardJ1;

        while (shipsP1 < 3) {

            int posX = (int) (Math.random() * 5);
            int posY = (int) (Math.random() * 5);

            if (posX < 5 && posX >= 1 && posY < 5 && posY >= 1) {
                if(boardJ1.board[posX][posY] != 'b') {
                    board1.addShipP1(posX, posY);
                    shipsP1++;
                }
            }
        }

        board2 = boardJ2;

        while (shipsP2 < 3) {

            int posX = (int) (Math.random() * 5);
            int posY = (int) (Math.random() * 5);

            if (posX < 5 && posX >= 1 && posY < 5 && posY >= 1) {
                if(boardJ2.board[posX][posY] != 'b') {
                    board2.addShipP2(posX, posY);
                    shipsP2++;
                }
            }
        }
    }

    public void shoot(Board boardP1, int x, int y){

        board1 = boardP1;

        if (x < 5 && x >= 1 || y < 5 && y >= 1) {
            if (boardP1.board[x][y] == 'b') {
                boardP1.board[x][y] = 'x';
                shipsP1--;
                board1.matchState(defeat);
                System.out.println("Has hundido un barco.");

                if (shipsP1 == 0){
                    defeat = false;
                    System.out.println("VICTORIA !");
                    board1.matchState(defeat);
                }
            } else {
                board1.updateBoard(x, y);
                System.out.println("Has fallado");
                board1.matchState(defeat);
            }
        } else {
            System.out.println("Valor no válido");
        }
        System.out.println(shipsP1);
    }

}
