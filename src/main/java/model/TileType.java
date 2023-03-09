package model;

import java.io.Serializable;

public enum TileType implements Serializable {
    CAT, BOOK, GAME, FRAME, TROPHY, PLANT;

    private static final long serialVersionUID = 863462839735L;

    @Override
    public String toString() {
        return this.name();
    }
}
