package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.BoardSquareType.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardOldTest<R extends ClientInterface> {

    @Test
    void getNumberOfBoardSquares() {
        BoardOld boardOld = new BoardOld();
        int numberOfBoardSquare = 45;
        assertEquals(numberOfBoardSquare, boardOld.getNumberOfBoardSquares());
    }

    @Test
    void fromIntToBoardSquare() {
        BoardOld boardOld = new BoardOld();
        boolean result = false;
        int count= 0;
        for(BoardSquare b : boardOld){
            result = b == boardOld.fromIntToBoardSquare(count);
            assertTrue(result);
            count++;
        }
    }

    @Test
    void removeSelectedTileSubject() {
        BoardOld boardOld = new BoardOld();
        List<TileSubject> expectedList = new ArrayList<>();
        List<TileSubject> result = new ArrayList<>();
        int[] tileTaken = new int[]{1,2,3};
        int numPlayers = 4;
        boardOld.refillBoard(numPlayers);
        expectedList.add(boardOld.fromIntToBoardSquare(1).getTileSubject());
        expectedList.add(boardOld.fromIntToBoardSquare(2).getTileSubject());
        expectedList.add(boardOld.fromIntToBoardSquare(3).getTileSubject());
        result = boardOld.removeSelectedTileSubject(tileTaken);
        assertEquals(tileTaken.length,result.size());
        for(int i = 0; i < result.size(); i++){
            assertEquals(expectedList.get(i), result.get(i));
            assertNull(boardOld.fromIntToBoardSquare(tileTaken[i]).getTileSubject());
        }

        expectedList.clear();
        tileTaken = new int[]{43,44};
        expectedList.add(boardOld.fromIntToBoardSquare(43).getTileSubject());
        expectedList.add(boardOld.fromIntToBoardSquare(44).getTileSubject());
        result = boardOld.removeSelectedTileSubject(tileTaken);
        assertEquals(tileTaken.length,result.size());
        for(int i = 0; i < result.size(); i++){
            assertEquals(expectedList.get(i), result.get(i));
            assertNull(boardOld.fromIntToBoardSquare(tileTaken[i]).getTileSubject());
        }
    }

    @Test
    void refillBoard() {
        BoardOld boardOld = new BoardOld();
        int numPlayers = 3;
        boardOld.refillBoard(numPlayers);
        for(BoardSquare b : boardOld){
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