package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.listeners.OnAssignedPersonalGoalListener;
import it.polimi.ingsw.controller.listeners.OnPlayerStateChangedListener;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest<R extends ClientInterface> {
    TileSubject[][] matrix = new TileSubject[][]{
            {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
            {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
            {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME},
            {TileSubject.NOTE_BOOK, TileSubject.RISIKO_GAME, TileSubject.MUSIC_TROPHY, TileSubject.DICTIONARY_BOOK, TileSubject.BASIL_PLANT},
            {TileSubject.COMIC_BOOK, TileSubject.BASIL_PLANT, TileSubject.DICTIONARY_BOOK, TileSubject.CHESS_GAME, TileSubject.MONOPOLY_GAME},
            {TileSubject.BASIL_PLANT, TileSubject.BLACK_CAT, TileSubject.CHAMPION_TROPHY, TileSubject.COMIC_BOOK, TileSubject.DEGREE_FRAME}
    };

    @Test
    void getNickName() {
        ClientInterface ui = null;
        Player player = new Player("Melanie", ui);
        assertEquals("Melanie", player.getNickName());
    }

    @Test
    void getAndSetPersonalGoal() {
        PersonalGoal goal = new PersonalGoal();
        Player player = new Player("Tommaso");
        player.setPersonalGoal(goal);
        assertEquals(goal.toString(), player.getPersonalGoal().toString());
    }

    @Test
    void getAndSetVirtualView(){
        ClientInterface ui = null;
        Player player = new Player("Melanie");
        player.setVirtualView(ui);
        assertNull(player.getVirtualView());
    }

    @Test
    void getAndSetPlayerState(){
        Player player = new Player("Adem");
        player.setPlayerState(PlayerState.DISCONNECTED);
        assertEquals(player.getPlayerState().toString(), "DISCONNECTED");
    }

    @Test
    void getAndSetBookShelf() {
        Player player = new Player("Emanuele");
        BookShelf bookShelf = new BookShelf();
        bookShelf.setTileSubjectTaken(matrix);
        player.setBookShelf(bookShelf);
        bookShelf.setPlayer(player);
        for(int i = 0; i < bookShelf.getTileSubjectTaken().length; i++){
            for(int j = 0; j < bookShelf.getTileSubjectTaken()[0].length; j++){
                assertEquals(player.getBookShelf().getTileSubjectTaken()[i][j], matrix[i][j]);
            }
        }

    }

    @Test
    void assignScoreEndGame() {
        PointPlayer playerPoint = new PointPlayer();
        Player player = new Player("Adem");
        player.setPointPlayer(playerPoint);
        player.assignScoreEndGame(1);
        assertEquals(1, player.getPointPlayer().getScoreEndGame());
    }

    @Test
    void getAndSetPointPlayer() {
        PointPlayer playerPoint = new PointPlayer();
        Player player = new Player("Adem");
        player.setPointPlayer(playerPoint);
        assertEquals(player.getPointPlayer(), playerPoint);
    }

    @Test
    void onPlayerStateChangedListeners(){
        OnPlayerStateChangedListener listener = (nickname, playerState) -> {
            assertEquals("QUITTED", playerState.toString());
            assertEquals("P1", nickname);
        };
        Player p = new Player("P1");
        p.setOnPlayerStateChangedListener(listener);
        p.setPlayerState(PlayerState.QUITTED);
        p.removeOnPlayerStateChangedListener(listener);
    }

    @Test
    void onAssignedPersonalGoalListeners() throws FileNotFoundException {
        OnAssignedPersonalGoalListener listener = (nickname, goalPattern, scoreMap) -> assertEquals("P", nickname);
        Player p = new Player("P");
        p.setOnAssignedPersonalGoalListener(listener);
        p.setPersonalGoal(new PersonalGoal("pattern_1"));
        p.removeOnAssignedPersonalGoalListener(listener);
    }

    @Test
    void testToString() {
        PersonalGoal goal = new PersonalGoal();
        Player player = new Player("Melanie", goal);
        String expected = "Player{nickname=Melanie}";
        assertEquals(expected, player.toString());
    }

    @Test
    void testEquals(){
        Player p1 = new Player("Melanie");
        Player p2 = new Player("Tommaso");
        assertNotEquals(p1, p2);
        assertEquals(p1, p1);
        assertNotEquals(p1, null);

        Player p3 = new Player("Melanie");
        p1.setPlayerState(PlayerState.CONNECTED);
        p3.setPlayerState(PlayerState.CONNECTED);
        assertEquals(p1, p3);
    }

    @Test
    void testHashCode(){
        Player p = new Player("P1");
        assertEquals(p.hashCode(), p.hashCode());
    }

}