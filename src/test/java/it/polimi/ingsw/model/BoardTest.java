package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.BoardSquareType.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest<R extends ClientInterface> {

    @Test
    void getNumberOfBoardSquares() {
        Board board = new Board();
        int numberOfBoardSquare = 45;
        assertEquals(numberOfBoardSquare, board.getNumberOfBoardSquares());
    }

    @Test
    void fromIntToBoardSquare() {
        Board board = new Board();
        boolean result = false;
        int count= 0;
        for(BoardSquare b : board){
            result = b == board.fromIntToBoardSquare(count);
            assertTrue(result);
            count++;
        }
    }

    @Test
    void removeSelectedTileSubject() {
        Board board = new Board();
        List<TileSubject> expectedList = new ArrayList<>();
        List<TileSubject> result = new ArrayList<>();
        int[] tileTaken = new int[]{1,2,3};
        int numPlayers = 4;
        board.refillBoard(numPlayers);
        expectedList.add(board.fromIntToBoardSquare(1).getTileSubject());
        expectedList.add(board.fromIntToBoardSquare(2).getTileSubject());
        expectedList.add(board.fromIntToBoardSquare(3).getTileSubject());
        result = board.removeSelectedTileSubject(tileTaken);
        assertEquals(tileTaken.length,result.size());
        for(int i = 0; i < result.size(); i++){
            assertEquals(expectedList.get(i), result.get(i));
            assertNull(board.fromIntToBoardSquare(tileTaken[i]).getTileSubject());
        }

        expectedList.clear();
        tileTaken = new int[]{43,44};
        expectedList.add(board.fromIntToBoardSquare(43).getTileSubject());
        expectedList.add(board.fromIntToBoardSquare(44).getTileSubject());
        result = board.removeSelectedTileSubject(tileTaken);
        assertEquals(tileTaken.length,result.size());
        for(int i = 0; i < result.size(); i++){
            assertEquals(expectedList.get(i), result.get(i));
            assertNull(board.fromIntToBoardSquare(tileTaken[i]).getTileSubject());
        }
    }

    @Test
    void refillBoard() {
        Board board = new Board();
        int numPlayers = 3;
        board.refillBoard(numPlayers);
        for(BoardSquare b : board){
            if(isOkay(b,numPlayers)){
                assertNotNull(b.getTileSubject());
            } else {
                assertNull(b.getTileSubject());
            }
        }
    }

    @Test
    void setOnBoardRefilledListener() {
    }

    @Test
    void setOnBoardUpdatedListener() {
    }

    @Test
    void removeOnBoardRefilledListener() {
    }

    @Test
    void removeOnBoardUpdatedListener() {
    }

    @Test
    void onUpdateNeededListener() {
    }

    private boolean isOkay(BoardSquare boardSquare, int numPlayers) {
        return (boardSquare.getBoardSquareType() == NO_DOTS || (boardSquare.getBoardSquareType() == THREE_DOTS && numPlayers >=3) || (boardSquare.getBoardSquareType() == FOUR_DOTS && numPlayers==4));
    }

}