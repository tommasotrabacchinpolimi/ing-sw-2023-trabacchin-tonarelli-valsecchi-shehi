package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.listeners.OnBookShelfUpdatedListener;
import it.polimi.ingsw.model.exceptions.NoTileTakenException;
import it.polimi.ingsw.model.exceptions.NotEnoughSpaceInBookShelfException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookShelfTest<R extends ClientInterface> {

    @Test
    void setTileSubjectTaken() {
        TileSubject[][] matrix = new TileSubject[][]{
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };

        BookShelf bookShelf = new BookShelf();

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
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };

        BookShelf bookShelf = new BookShelf();

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
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };

        BookShelf bookShelf = new BookShelf();
        bookShelf.setTileSubjectTaken(matrix);

        assertTrue(bookShelf.isFull());

        matrix = new TileSubject[][]{
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, null, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, null, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };

        bookShelf.setTileSubjectTaken(matrix);

        assertFalse(bookShelf.isFull());
    }

    @Test
    void toTileTypeMatrix() {
        TileSubject[][] matrix = new TileSubject[][]{
                {null, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };

        BookShelf bookShelf = new BookShelf();

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
                {null, TileSubject.GAME_RISIKO, null, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {null, TileSubject.PLANT_BASIL, null, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, null, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };

        BookShelf bookShelf = new BookShelf();

        bookShelf.setTileSubjectTaken(matrix);

        List<TileSubject> taken = new ArrayList<>();
        taken.add(TileSubject.FRAME_LOVE);
        taken.add(TileSubject.BOOK_COMIC);
        taken.add(TileSubject.CAT_ORANGE);

        try {
            bookShelf.addTileSubjectTaken(taken, 0);
        } catch (NotEnoughSpaceInBookShelfException e) {
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

        TileSubject[][] m = new TileSubject[][]{
                {null, TileSubject.GAME_RISIKO, null, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {null, TileSubject.PLANT_BASIL, null, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, null, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };
        bookShelf.setTileSubjectTaken(m);
        List<TileSubject> list = new ArrayList<>();
        list.add(TileSubject.FRAME_LOVE);
        list.add(TileSubject.BOOK_DICTIONARY);
        list.add(TileSubject.GAME_RISIKO);
        assertThrows(NotEnoughSpaceInBookShelfException.class, () -> {
            bookShelf.addTileSubjectTaken(list, 0);
        });
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            bookShelf.addTileSubjectTaken(list, -1);
        });
        assertThrows(NoTileTakenException.class, () -> {
            bookShelf.addTileSubjectTaken(null, 0);
        });

        TileSubject[][] m1 = new TileSubject[][]{
                {TileSubject.GAME_RISIKO, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };
        bookShelf.setTileSubjectTaken(m1);
        assertThrows(NotEnoughSpaceInBookShelfException.class, () -> {
            bookShelf.addTileSubjectTaken(list, 0);
        });
    }

    @Test
    void getPlayer(){
        BookShelf bookShelf = new BookShelf(7,4);
        Player player = new Player("nick");

        bookShelf.setPlayer(player);

        assertNotNull(bookShelf.getPlayer());
        assertEquals(player, bookShelf.getPlayer());
    }

    @Test
    void setPlayer() {
        BookShelf bookShelf = new BookShelf();
        Player player = new Player("nick");

        bookShelf.setPlayer(player);

        assertNotNull(bookShelf.getPlayer());
        assertEquals(player, bookShelf.getPlayer());

        Player player1 = new Player("nick1");
        bookShelf.setPlayer(player1);
        assertNotEquals(player, bookShelf.getPlayer());
    }

    @Test
    void testEquals() {
        BookShelf bookShelf = new BookShelf();
        BookShelf bookShelf1 = new BookShelf(bookShelf);

        assertEquals(bookShelf, bookShelf1);
        assertEquals(bookShelf, bookShelf);
        assertNotNull(bookShelf);
    }

    @Test
    void bookShelfUpdatedListener(){
        BookShelf bookShelf = new BookShelf();
        Player p = new Player("p1");
        bookShelf.setPlayer(p);
        TileSubject[][] matrix = new TileSubject[][]{
                {null, TileSubject.GAME_RISIKO, null, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {null, TileSubject.PLANT_BASIL, null, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, null, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE},
                {TileSubject.BOOK_NOTE, TileSubject.GAME_RISIKO, TileSubject.TROPHY_MUSIC, TileSubject.BOOK_DICTIONARY, TileSubject.PLANT_BASIL},
                {TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.BOOK_DICTIONARY, TileSubject.GAME_CHESS, TileSubject.GAME_MONOPOLY},
                {TileSubject.PLANT_BASIL, TileSubject.CAT_BLACK, TileSubject.TROPHY_CHAMPION, TileSubject.BOOK_COMIC, TileSubject.FRAME_DEGREE}
        };
        OnBookShelfUpdatedListener listener = (nickname, bookShelf1) -> {
            boolean success = (Arrays.deepEquals(bookShelf.getTileSubjectTaken(), matrix));
            assertTrue(success);
        };
        bookShelf.setOnBookShelfUpdated(listener);
        bookShelf.setTileSubjectTaken(matrix);
        bookShelf.onUpdateNeededListener(p);
        bookShelf.removeOnBookShelfUpdated(listener);
    }

    @Test
    void testHashCode() {
        BookShelf bookShelf = new BookShelf();
        BookShelf bookShelf1 = new BookShelf(bookShelf);

        assertEquals(bookShelf.hashCode(), bookShelf1.hashCode());
    }
}