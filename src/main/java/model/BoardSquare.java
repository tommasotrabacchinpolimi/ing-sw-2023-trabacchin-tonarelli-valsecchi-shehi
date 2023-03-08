package model;

public class BoardSquare {
    private BoardSquare top;
    private BoardSquare bottom;
    private BoardSquare right;
    private BoardSquare left;
    private Tile tile;
    final private BoardSquareType boardSquareType;
    public BoardSquare(BoardSquareType boardSquareType){
        this.boardSquareType = boardSquareType;
    }
    public BoardSquareType getBoardSquareType() {
        return boardSquareType;
    }


    public BoardSquare getTop() {
        return top;
    }

    public void setTop(BoardSquare top) {
        this.top = top;
    }

    public BoardSquare getBottom() {
        return bottom;
    }

    public void setBottom(BoardSquare bottom) {
        this.bottom = bottom;
    }

    public BoardSquare getRight() {
        return right;
    }

    public void setRight(BoardSquare right) {
        this.right = right;
    }

    public BoardSquare getLeft() {
        return left;
    }

    public void setLeft(BoardSquare left) {
        this.left = left;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }



}
