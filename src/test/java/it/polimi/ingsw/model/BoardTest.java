package it.polimi.ingsw.model;

import com.almasb.fxgl.entity.level.tiled.Tile;
import it.polimi.ingsw.utils.Coordinate;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getBoard() {
        Board bor = new Board();
        TileSubject[][] b1 = bor.getBoard();
        assertEquals(bor.getBoard().length, b1.length);
        assertEquals(bor.getBoard()[0].length, b1[0].length);

        for(int i =0; i < bor.getBoard().length; i++)
        {
            for(int j = 0; j < bor.getBoard()[0].length; j++)
            {
                assertEquals(bor.getBoard()[i][j], b1[i][j]);
            }
        }

        assertNull(b1[0][0]);
        assertNull(b1[0][1]);
        assertNull(b1[0][2]);

        assertNull(b1[0][5]);
        assertNull(b1[0][6]);
        assertNull(b1[0][7]);
        assertNull(b1[0][8]);

        assertNull(b1[1][0]);
        assertNull(b1[1][1]);
        assertNull(b1[1][2]);
        assertNull(b1[1][6]);
        assertNull(b1[1][7]);
        assertNull(b1[1][8]);

        assertNull(b1[2][0]);
        assertNull(b1[2][1]);
        assertNull(b1[2][7]);
        assertNull(b1[2][8]);

        assertNull(b1[3][0]);

        assertNull(b1[5][8]);

        assertNull(b1[6][0]);
        assertNull(b1[6][1]);
        assertNull(b1[6][7]);
        assertNull(b1[6][8]);

        assertNull(b1[7][0]);
        assertNull(b1[7][1]);
        assertNull(b1[7][2]);
        assertNull(b1[7][6]);
        assertNull(b1[7][7]);
        assertNull(b1[7][8]);

        assertNull(b1[8][0]);
        assertNull(b1[8][1]);
        assertNull(b1[8][2]);
        assertNull(b1[8][6]);
        assertNull(b1[8][7]);
        assertNull(b1[8][8]);


        //******************

        assertNull(bor.getBoard()[0][0]);
        assertNull(bor.getBoard()[0][1]);
        assertNull(bor.getBoard()[0][2]);

        assertNull(bor.getBoard()[0][5]);
        assertNull(bor.getBoard()[0][6]);
        assertNull(bor.getBoard()[0][7]);
        assertNull(bor.getBoard()[0][8]);

        assertNull(bor.getBoard()[1][0]);
        assertNull(bor.getBoard()[1][1]);
        assertNull(bor.getBoard()[1][2]);
        assertNull(bor.getBoard()[1][6]);
        assertNull(bor.getBoard()[1][7]);
        assertNull(bor.getBoard()[1][8]);

        assertNull(bor.getBoard()[2][0]);
        assertNull(bor.getBoard()[2][1]);
        assertNull(bor.getBoard()[2][7]);
        assertNull(bor.getBoard()[2][8]);

        assertNull(bor.getBoard()[3][0]);

        assertNull(bor.getBoard()[5][8]);

        assertNull(bor.getBoard()[6][0]);
        assertNull(bor.getBoard()[6][1]);
        assertNull(bor.getBoard()[6][7]);
        assertNull(bor.getBoard()[6][8]);

        assertNull(bor.getBoard()[7][0]);
        assertNull(bor.getBoard()[7][1]);
        assertNull(bor.getBoard()[7][2]);
        assertNull(bor.getBoard()[7][6]);
        assertNull(bor.getBoard()[7][7]);
        assertNull(bor.getBoard()[7][8]);

        assertNull(bor.getBoard()[8][0]);
        assertNull(bor.getBoard()[8][1]);
        assertNull(bor.getBoard()[8][2]);
        assertNull(bor.getBoard()[8][6]);
        assertNull(bor.getBoard()[8][7]);
        assertNull(bor.getBoard()[8][8]);
    }

    @Test
    void getNumberOfBoardSquares() {
        Board bor = new Board();
        assertEquals(bor.getNumberOfBoardSquares(), 45);
    }

    @Test
    public void testGetRandomTileSubject() {
        Board board = new Board();
        List<TileSubject> bagg = board.getBag();
        int preLength = bagg.size();
        TileSubject RandomTile = board.getRandomTileSubject();
        assertTrue(bagg.contains(RandomTile));
        assertEquals(preLength-1, bagg.size());
    }



    @Test
    void bagToString() {

    }


    // volendo puoi fare un metodo che assegna casualmetne un tipo di tile e aggiungerlo in una posizione
    // e controllare che la get funzioni correttamente
@Test
    public void GetTileSubjectInBoard() {
        Board board = new Board();
        TileSubject tile = TileSubject.ORANGE_CAT;
        placeTile(tile, 1, 1, board);
        TileSubject expected = TileSubject.ORANGE_CAT;
        TileSubject actual = board.getTileSubjectInBoard(1, 1);
        assertEquals(expected, actual);
    }

    @Test
    void removeSelectedTileSubject() {
        Board board = new Board();
        int r1, r2;
        List<Coordinate> l1 =new ArrayList<Coordinate>();
        board.getBoard()[4][4] = TileSubject.ORANGE_CAT;
        Coordinate coo = new Coordinate(4, 4);
        l1.add(coo);
        r1 = CountNull(board);
        board.removeSelectedTileSubject(l1);
        r2 = CountNull(board);
        assertEquals(r1, r2-1);


    }


    @Test
    void refillBoard() {
        Board bo = new Board();
        int numbersPlayer;
        for(numbersPlayer = 2; numbersPlayer < 5; numbersPlayer++) {
            bo.refillBoard(numbersPlayer);

            assertNull(bo.getBoard()[0][0]);
            assertNull(bo.getBoard()[0][1]);
            assertNull(bo.getBoard()[0][2]);

            assertNull(bo.getBoard()[0][5]);
            assertNull(bo.getBoard()[0][6]);
            assertNull(bo.getBoard()[0][7]);
            assertNull(bo.getBoard()[0][8]);

            assertNull(bo.getBoard()[1][0]);
            assertNull(bo.getBoard()[1][1]);
            assertNull(bo.getBoard()[1][2]);
            assertNull(bo.getBoard()[1][6]);
            assertNull(bo.getBoard()[1][7]);
            assertNull(bo.getBoard()[1][8]);

            assertNull(bo.getBoard()[2][0]);
            assertNull(bo.getBoard()[2][1]);
            assertNull(bo.getBoard()[2][7]);
            assertNull(bo.getBoard()[2][8]);

            assertNull(bo.getBoard()[3][0]);

            assertNull(bo.getBoard()[5][8]);

            assertNull(bo.getBoard()[6][0]);
            assertNull(bo.getBoard()[6][1]);
            assertNull(bo.getBoard()[6][7]);
            assertNull(bo.getBoard()[6][8]);

            assertNull(bo.getBoard()[7][0]);
            assertNull(bo.getBoard()[7][1]);
            assertNull(bo.getBoard()[7][2]);
            assertNull(bo.getBoard()[7][6]);
            assertNull(bo.getBoard()[7][7]);
            assertNull(bo.getBoard()[7][8]);

            assertNull(bo.getBoard()[8][0]);
            assertNull(bo.getBoard()[8][1]);
            assertNull(bo.getBoard()[8][2]);
            assertNull(bo.getBoard()[8][6]);
            assertNull(bo.getBoard()[8][7]);
            assertNull(bo.getBoard()[8][8]);

        }
        numbersPlayer = 4;
        if (numbersPlayer==4)
        {
            bo.refillBoard(numbersPlayer);
            assertNotNull(bo.getBoard()[0][3]);
            assertNotNull(bo.getBoard()[0][4]);
            assertNotNull(bo.getBoard()[1][5]);
            assertNotNull(bo.getBoard()[2][2]);
            assertNotNull(bo.getBoard()[2][6]);
            assertNotNull(bo.getBoard()[3][1]);

            assertNotNull(bo.getBoard()[3][8]);
            assertNotNull(bo.getBoard()[4][0]);
            assertNotNull(bo.getBoard()[4][8]);
            assertNotNull(bo.getBoard()[5][0]);
            assertNotNull(bo.getBoard()[5][7]);
            assertNotNull(bo.getBoard()[6][2]);

            assertNotNull(bo.getBoard()[6][6]);
            assertNotNull(bo.getBoard()[7][3]);
            assertNotNull(bo.getBoard()[8][4]);
            assertNotNull(bo.getBoard()[8][5]);
        }

        numbersPlayer = 3;
        if(numbersPlayer==3)
        {
            assertNotNull(bo.getBoard()[0][3]);
            assertNotNull(bo.getBoard()[2][2]);
            assertNotNull(bo.getBoard()[2][6]);

            assertNotNull(bo.getBoard()[3][8]);
            assertNotNull(bo.getBoard()[5][0]);
            assertNotNull(bo.getBoard()[6][2]);

            assertNotNull(bo.getBoard()[6][6]);
            assertNotNull(bo.getBoard()[8][5]);
        }

    }

    @Test
    void printBoard() {
    }


    private int CountNull(Board bor)
    {
        int result = 0;
        for(int i = 0; i < bor.getBoard().length; i++)
        {
            for(int j = 0; j < bor.getBoard()[0].length; j++)
            {
                if(bor.getBoard()[i][j] == null)
                {
                    result++;
                }
            }
        }
        return  result;
    }

    private void placeTile (TileSubject TileToPlace, int row, int col, Board ReferredBoard) {
        if ((row < 0 || row > ReferredBoard.getBoard().length) && (col <0 || col > ReferredBoard.getBoard()[0].length)){
            throw new IllegalArgumentException("Invalid row or column index");
        }
        else {
            ReferredBoard.getBoard()[row][col] = TileToPlace;
        }
    }
}

