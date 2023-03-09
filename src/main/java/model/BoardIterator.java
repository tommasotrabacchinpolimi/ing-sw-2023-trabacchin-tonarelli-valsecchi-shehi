package model;

import java.util.NoSuchElementException;
import java.util.Iterator;


public class BoardIterator implements Iterator<BoardSquare> {
    private enum Direction{
        LEFT,
        RIGHT
    }
    final private Board board;
    private BoardSquare last;
    private BoardSquare middle;

    private Direction direction;

    private int number_of_iterated_squares;
    public BoardIterator(Board board){
        this.board = board;
        this.last = null;
        this.middle = board.getLivingRoomBoard();
        this.direction = Direction.LEFT;
        this.number_of_iterated_squares = 0;
    }
    @Override
    public boolean hasNext() {
        return this.number_of_iterated_squares != this.board.getNumberOfBoardSquares();
    }

    @Override
    public BoardSquare next() {
        if(last==null){
            this.last = this.board.getLivingRoomBoard();
        }
        switch(this.direction){
            case LEFT -> {
                this.last = this.last.getLeft();
                if(this.last==null){
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
        this.number_of_iterated_squares++;
        return this.last;
    }

    private void searchDown() {
        if(this.last==null){
            this.last = this.middle.getBottom();
            this.middle = this.last;
            this.direction = Direction.LEFT;
            if(this.last==null){
                throw new NoSuchElementException("No more BoardSquare in the Board");
            }
        }
    }
}
