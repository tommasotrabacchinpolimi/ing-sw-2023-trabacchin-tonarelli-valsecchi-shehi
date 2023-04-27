package it.polimi.ingsw.utils;

import java.io.Serial;
import java.io.Serializable;

public class Coordinate implements Serializable {
    @Serial
    private static final long serialVersionUID = 27112003L;
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "("+x+","+y+")";
    }

}
