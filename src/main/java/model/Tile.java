package model;

public class Tile {
    private TileSubject tileSubject;
    private int subType;

    public Tile(){
        tileSubject = null;
        subType = 0;
    }

    public Tile(Tile t){
        this.tileSubject = t.getTileSubject();
        this.subType = t.getSubType();
    }

    public Tile(TileSubject tileSubject, int subType) {
        this.tileSubject = tileSubject;
        this.subType = subType;
    }

    public void setTileSubject(TileSubject tileSubject ){
        this.tileSubject = tileSubject;
    }

    public TileSubject getTileSubject() {
        return tileSubject;
    }

    public void setSubType(int subType ){
        this.subType = subType;
    }

    public int getSubType() {
        return subType;
    }

}
