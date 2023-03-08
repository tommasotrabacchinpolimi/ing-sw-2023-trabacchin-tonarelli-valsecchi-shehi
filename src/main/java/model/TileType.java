package model;

public enum TileType {
    CAT, BOOK, GAME, FRAME, TROPHY, PLANT;

    @Override
    public String toString() {
        return this.name();
    }
}
