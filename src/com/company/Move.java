package com.company;

import java.io.Serializable;

public class Move implements Serializable {

    public int x;
    public int y;
    public int numPlayer;

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
