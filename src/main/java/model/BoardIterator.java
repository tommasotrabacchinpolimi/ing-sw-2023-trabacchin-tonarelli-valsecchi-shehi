package model;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Iterator;


public class BoardIterator implements Iterator<BoardSquare>, Serializable {
    private static final long serialVersionUID = 18052001L;

    private enum Direction implements Serializable{
        LEFT,
        RIGHT;

        private static final long serialVersionUID = 784386346274L;
    }

    final private Board board;
    private BoardSquare last;
    private BoardSquare middle;
    private Direction direction;
    private int numberOfIteratedSquares;

    public BoardIterator( Board board ){
        this.board = board;
        this.last = null;
        this.middle = board.getLivingRoomBoard();
        this.direction = Direction.LEFT;
        this.numberOfIteratedSquares = 0;
    }

    @Override
    public boolean hasNext() {
        //check if the number of iterated square are the number of the square on the board
        return this.numberOfIteratedSquares != this.board.getNumberOfBoardSquares();
    }

    @Override
    public BoardSquare next() {
        if( last == null ) {
            this.last = this.board.getLivingRoomBoard();
            this.numberOfIteratedSquares++;
            return this.last;
        }
        switch( this.direction  ){
            case LEFT -> {
                this.last = this.last.getLeft();
                if( this.last == null ) {
                    this.last = this.middle.getRight();
                    this.direction = Direction.RIGHT;
                    searchDown();
                }
            }
            case RIGHT -> {
                this.last = this.last.getRight();
                searchDown();
            }
        }
        this.numberOfIteratedSquares++;
        return this.last;
    }

    private void searchDown() {
        if( this.last == null ){
            this.last = this.middle.getBottom();
            this.middle = this.last;
            this.direction = Direction.LEFT;
            if( this.last == null ){
                throw new NoSuchElementException( "No more BoardSquare in the Board" );
            }
        }
    }

}
