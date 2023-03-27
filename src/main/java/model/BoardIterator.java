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
    private BoardSquare middle; //root
    private Direction direction;
    private int numberOfIteratedSquares;

    public BoardIterator( Board board ){
        this.board = board;
        this.last = null;
        this.middle = board.getLivingRoomBoard();
        this.direction = Direction.LEFT;
        this.numberOfIteratedSquares = 0;
    }

    /**
     * Check if the number of iterated square is the number of the square on the board
     *
     * @return True if the number of iterated squares is different according to number of board square.
     *         False otherwise
     */
    @Override
    public boolean hasNext() {
        return this.numberOfIteratedSquares != this.board.getNumberOfBoardSquares();
    }

    /**
     * The method is used to iterate over a Board. It returns the next BoardSquare in the iteration sequence.
     * @return the next BoardSquare in the iteration sequence
     */
    @Override
    public BoardSquare next() {
        if(last == null) {
            this.last = this.board.getLivingRoomBoard();
            this.numberOfIteratedSquares++;
            return this.last;
        }

        switch(this.direction){
            case LEFT -> {
                this.last = this.last.getLeft();
                if(this.last == null) {
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

    /**
     * The method set middle to the middle BoardSquare of the row below the current one.
     */
    private void searchDown() {
        if(this.last == null){
            this.last = this.middle.getBottom();
            this.middle = this.last;
            this.direction = Direction.LEFT;

            if(this.last == null){
                throw new NoSuchElementException( "No more BoardSquare in the Board" );
            }
        }
    }

}
