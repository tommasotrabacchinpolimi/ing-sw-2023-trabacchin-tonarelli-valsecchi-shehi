package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.model.exceptions.NotEnoughSpaceInBookShelfException;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookShelfTest<R extends ClientInterface> {

    @Test
    void setTileSubjectTaken() {
        TileSubject[][] matrix = new TileSubject[][]{
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME},
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME}
        };

        BookShelf<R> bookShelf = new BookShelf<>();

        bookShelf.setTileSubjectTaken(matrix);

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                assertEquals(matrix[i][j].toString(), bookShelf.getTileSubjectTaken()[i][j].toString());
            }
        }
    }

    @Test
    void getTileSubjectTaken() {
        TileSubject[][] matrix = new TileSubject[][]{
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME},
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME}
        };

        BookShelf<R> bookShelf = new BookShelf<>();

        bookShelf.setTileSubjectTaken(matrix);

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                assertEquals(matrix[i][j].toString(), bookShelf.getTileSubjectTaken()[i][j].toString());
            }
        }
    }

    @Test
    void isFull() {
        TileSubject[][] matrix = new TileSubject[][]{
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME},
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME}
        };

        BookShelf<R> bookShelf = new BookShelf<>();
        bookShelf.setTileSubjectTaken(matrix);

        assertTrue(bookShelf.isFull());

        matrix = new TileSubject[][]{
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, null, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, null, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME},
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME}
        };

        bookShelf.setTileSubjectTaken(matrix);

        assertFalse(bookShelf.isFull());
    }

    @Test
    void toTileTypeMatrix() {
        TileSubject[][] matrix = new TileSubject[][]{
                {null, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME},
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME}
        };

        BookShelf<R> bookShelf = new BookShelf<>();

        bookShelf.setTileSubjectTaken(matrix);

        TileType[][] typesExpected = new TileType[][]{
                {null, TileType.GAME, TileType.TROPHY, TileType.BOOK, TileType.PLANT},
                {TileType.BOOK, TileType.PLANT, TileType.BOOK, TileType.GAME, TileType.GAME},
                {TileType.PLANT, TileType.CAT, TileType.TROPHY, TileType.BOOK, TileType.FRAME},
                {TileType.BOOK, TileType.GAME, TileType.TROPHY, TileType.BOOK, TileType.PLANT},
                {TileType.BOOK, TileType.PLANT, TileType.BOOK, TileType.GAME, TileType.GAME},
                {TileType.PLANT, TileType.CAT, TileType.TROPHY, TileType.BOOK, TileType.FRAME}
        };

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if (i==0 && j==0) assertNull(bookShelf.toTileTypeMatrix()[i][j]);
                else assertEquals(typesExpected[i][j].toString(), bookShelf.toTileTypeMatrix()[i][j].toString());
            }
        }
    }

    @Test
    void addTileSubjectTaken() {
        TileSubject[][] matrix = new TileSubject[][]{
                {null, TileSubject.RISIKO_GAME, null, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {null, TileSubject.BASIL_PLANT, null, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, null, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME},
                {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
                {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
                {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME}
        };

        BookShelf<R> bookShelf = new BookShelf<>();

        bookShelf.setTileSubjectTaken(matrix);

        List<TileSubject> taken = new ArrayList<>();
        taken.add(TileSubject.LOVE_FRAME);
        taken.add(TileSubject.COMIC_BOOK);
        taken.add(TileSubject.ORANGE_CAT);

        try{
            bookShelf.addTileSubjectTaken(taken, 0);
        }catch(NotEnoughSpaceInBookShelfException e) {
            assertNull(bookShelf.getTileSubjectTaken()[0][0]);
            assertNull(bookShelf.getTileSubjectTaken()[1][0]);
        }

        bookShelf.addTileSubjectTaken(taken, 2);

        assertEquals(taken.get(0).getTileType(), bookShelf.getTileSubjectTaken()[2][2].getTileType());
        assertEquals(taken.get(1).getTileType(), bookShelf.getTileSubjectTaken()[1][2].getTileType());
        assertEquals(taken.get(2).getTileType(), bookShelf.getTileSubjectTaken()[0][2].getTileType());

        taken.remove(2);

        bookShelf.addTileSubjectTaken(taken, 0);

        assertEquals(taken.get(0).getTileType(), bookShelf.getTileSubjectTaken()[1][0].getTileType());
        assertEquals(taken.get(1).getTileType(), bookShelf.getTileSubjectTaken()[0][0].getTileType());
    }

    @Test
    void getPlayer(){
        BookShelf<R> bookShelf = new BookShelf<>(7,4);
        Player<R> player = new Player<>("nick");

        bookShelf.setPlayer(player);

        assertNotNull(bookShelf.getPlayer());
        assertEquals(player, bookShelf.getPlayer());
    }

    @Test
    void setPlayer() {
        BookShelf<R> bookShelf = new BookShelf<>();
        Player<R> player = new Player<>("nick");

        bookShelf.setPlayer(player);

        assertNotNull(bookShelf.getPlayer());
        assertEquals(player, bookShelf.getPlayer());

        Player<R> player1 = new Player<>("nick1");
        bookShelf.setPlayer(player1);
        assertNotEquals(player, bookShelf.getPlayer());
    }

    @Test
    void testEquals() {
        BookShelf<R> bookShelf = new BookShelf<>();
        BookShelf<R> bookShelf1 = new BookShelf<>(bookShelf);

        assertEquals(bookShelf, bookShelf1);
    }


    @Test
    void testHashCode() {
        BookShelf<R> bookShelf = new BookShelf<>();
        BookShelf<R> bookShelf1 = new BookShelf<>(bookShelf);

        assertEquals(bookShelf.hashCode(), bookShelf1.hashCode());
    }
}