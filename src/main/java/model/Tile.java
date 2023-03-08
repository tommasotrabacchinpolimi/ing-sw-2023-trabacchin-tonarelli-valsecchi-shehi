package model;

public class Tile {
    private TileType type;
    private int subType;

    public Tile(){
        type = null;
        subType = 0;
    }

    public Tile(Tile t){
        this.type = t.getType();
        this.subType = t.getSubType();
    }

    public Tile(TileType type, int subType) {
        this.type = type;
        this.subType = subType;
    }

    public void setType(TileType type ){
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setSubType(int subType ){
        this.subType = subType;
    }

    public int getSubType() {
        return subType;
    }

}
