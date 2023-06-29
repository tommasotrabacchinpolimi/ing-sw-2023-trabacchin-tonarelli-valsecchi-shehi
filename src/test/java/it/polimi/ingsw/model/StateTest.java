package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.listeners.*;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.*;

class StateTest {

    private final String prefix = "/it.polimi.ingsw/personal.goal.configuration/";

    public State createStateWithPlayers(){
        State state = new State();
        Player p1, p2, p3, p4;
        p1 = new Player("Melanie");
        p2 = new Player("Tommaso");
        p3 = new Player("Emanuele");
        p4 = new Player("Adem");
        state.setPlayersNumber(4);
        state.setPlayers(Arrays.stream(new Player[]{p1, p2, p3, p4}).toList());
        return state;
    }

    @Test
    void getAndSetPlayersNumber() {
        State state = createStateWithPlayers();
        int num = 4;
        state.setPlayersNumber(num);
        assertEquals(num, state.getPlayersNumber());
    }

    @Test
    void getAndSetBoard() {
        Board board = new Board();
        State state = createStateWithPlayers();
        state.setBoard(board);
        assertEquals(board, state.getBoard());
    }

    @Test
    void getAndSetAndAddPlayers() {
        OnPlayersListChangedListener listener1 = new OnPlayersListChangedListener() {
            @Override
            public void onPlayersListChanged(List<String> players) {

            }
        };
        State state = new State();
        state.setOnPlayersListChangedListener(listener1);
        Player p1, p2, p3;
        state.setPlayersNumber(3);
        p1 = new Player("Melanie");
        p2 = new Player("Tommaso");
        p3 = new Player("Adem");
        Player[] p = new Player[]{p1, p2};
        state.setPlayers(Arrays.stream(p).toList());
        assertEquals(p1, state.getPlayers().get(0));
        assertEquals(p2, state.getPlayers().get(1));
        state.removeOnPlayersListChangedListener(listener1);

        final State s = new State();
        s.setOnPlayersListChangedListener(listener1);
        s.setPlayersNumber(2);
        p = new Player[]{p1, p2, p3};
        List<Player> players = Arrays.stream(p).toList();
        Exception e = assertThrows(RuntimeException.class, () -> {s.setPlayers(players);});
        assertTrue(s.getPlayers().isEmpty());
        assertEquals("Too many players inserted", e.getMessage());
        s.addPlayer(p1);
        assertEquals(p1, s.getPlayers().get(0));
        assertFalse(s.addPlayer(p1));
        assertEquals(1, s.getPlayers().size());
        s.addPlayer(p2);
        assertEquals(p2, s.getPlayers().get(1));
        assertFalse(s.addPlayer(p3));
        assertEquals(2, s.getPlayers().size());
        s.removeOnPlayersListChangedListener(listener1);
    }

    @Test
    void getAndSetCurrentPlayer() {
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(0);
        OnCurrentPlayerChangedListener listener = new OnCurrentPlayerChangedListener() {
            @Override
            public void onCurrentPlayerChangedListener(String nickname) {
                assertEquals(p.getNickName(), nickname);
            }
        };
        state.setOnCurrentPlayerChangedListener(listener);
        state.setCurrentPlayer(p);
        assertEquals(p, state.getCurrentPlayer());
        state.removeOnCurrentPlayerChangedListener(listener);
    }

    @Test
    void getAndSetAndAddMessages() {
        OnMessageSentListener listener = new OnMessageSentListener() {
            @Override
            public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {

            }

            @Override
            public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {

            }
        };
        State state = createStateWithPlayers();
        state.setMessageSentListener(listener);
        List<Player> p = state.getPlayers();
        ChatMessage m1 = new ChatMessage(p.get(0), p, "Hello World!");
        ChatMessage m2 = new ChatMessage(p.get(1), p, "Hello World 2!");
        ChatMessage m3 = new ChatMessage(p.get(2), p, "Hello World 3!");
        ChatMessage m4 = new ChatMessage(p.get(3), p, "Hello World 4!");
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(m1);
        chatMessages.add(m2);
        chatMessages.add(m3);
        state.setMessages(chatMessages);
        assertEquals(3, state.getMessages().size());
        for(int i = 0; i < 3; i++){
            assertEquals(chatMessages.get(i), state.getMessages().get(i));
        }
        state.addMessage(m4);
        assertEquals(4, state.getMessages().size());
        assertEquals(chatMessages.get(3), state.getMessages().get(3));
        state.removeMessageSentListener(listener);
    }

    @Test
    void getPlayerFromNick() {
        State state = createStateWithPlayers();
        Player p = state.getPlayerFromNick("Melanie");
        Player p1 = state.getPlayers().get(0);
        assertEquals(p1, p);
    }

    @Test
    void getAndSetLastPlayer(){
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(1);
        OnLastPlayerUpdatedListener listener = new OnLastPlayerUpdatedListener() {
            @Override
            public void onLastPlayerUpdated(String nicknameLastPlayer) {
                assertEquals(p.getNickName(), nicknameLastPlayer);
            }
        };
        state.setLastPlayerUpdatedListener(listener);
        state.setLastPlayer(p);
        assertEquals(p, state.getLastPlayer());
        state.removeLastPlayerUpdatedListeners(listener);
    }

    @Test
    void getAndSetWinnerPlayer(){
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(2);
        OnWinnerChangedListener listener = new OnWinnerChangedListener() {
            @Override
            public void onWinnerChanged(String nickname) {
                assertEquals(p.getNickName(), nickname);
            }
        };
        state.setOnWinnerChangedListener(listener);
        state.setWinner(p);
        assertEquals(p, state.getWinner());
        state.removeOnWinnerChangedListener(listener);
    }

    @Test
    void getAndSetGameState(){
        OnStateChangedListener listener = new OnStateChangedListener() {
            @Override
            public void onStateChanged(GameState gameState) {
                assertEquals(GameState.FINAL, gameState);
            }
        };
        State state = createStateWithPlayers();
        state.setStateChangedListener(listener);
        state.setGameState(GameState.FINAL);
        assertEquals("FINAL", state.getGameState().toString());
        state.removeStateChangedListener(listener);
        state.setGameState(GameState.INIT);
        assertEquals("INIT", state.getGameState().toString());
        state.setGameState(GameState.MID);
        assertEquals("MID", state.getGameState().toString());
        state.setGameState(GameState.SUSPENDED);
        assertEquals("SUSPENDED", state.getGameState().toString());
        state.setGameState(GameState.END);
        assertEquals("END", state.getGameState().toString());
    }

    @Test
    void checkPersonalGoal() throws FileNotFoundException {
        //TO DO
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(0);
        BookShelf b = new BookShelf();
        p.setBookShelf(b);
        b.setPlayer(p);
        TileSubject[][] m = new TileSubject[6][5];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                m[j][i] = null;
            }
        }
        p.setPointPlayer(new PointPlayer());
        PersonalGoal goal = new PersonalGoal(prefix + "pattern_1.json");
        p.setPersonalGoal(goal);
        OnAchievedPersonalGoalListener listener = (nickname, tiles) -> assertEquals(p.getNickName(), nickname);
        state.setOnAchievedPersonalGoalListener(listener);
        b.setTileSubjectTaken(m);
        state.checkPersonalGoal(p);
        assertEquals(0, p.getPointPlayer().getScorePersonalGoal());
        m = new TileSubject[][]{
                {TileSubject.PLANT_BASIL, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };
        p.getBookShelf().setTileSubjectTaken(m);
        state.checkPersonalGoal(p);
        assertEquals(1, p.getPointPlayer().getScorePersonalGoal());
        m = new TileSubject[][]{
                {TileSubject.PLANT_BASIL, null, TileSubject.FRAME_DEGREE, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };
        p.getBookShelf().setTileSubjectTaken(m);
        state.checkPersonalGoal(p);
        assertEquals(2, p.getPointPlayer().getScorePersonalGoal());
        m = new TileSubject[][]{
                {TileSubject.PLANT_BASIL, null, TileSubject.FRAME_DEGREE, null, null},
                {null, null, null, null, TileSubject.CAT_BLACK},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };
        p.getBookShelf().setTileSubjectTaken(m);
        state.checkPersonalGoal(p);
        assertEquals(4, p.getPointPlayer().getScorePersonalGoal());
        m = new TileSubject[][]{
                {TileSubject.PLANT_BASIL, null, TileSubject.FRAME_DEGREE, null, null},
                {null, null, null, null, TileSubject.CAT_BLACK},
                {null, null, null, TileSubject.BOOK_COMIC, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };
        p.getBookShelf().setTileSubjectTaken(m);
        state.checkPersonalGoal(p);
        assertEquals(6, p.getPointPlayer().getScorePersonalGoal());
        m = new TileSubject[][]{
                {TileSubject.PLANT_BASIL, null, TileSubject.FRAME_DEGREE, null, null},
                {null, null, null, null, TileSubject.CAT_BLACK},
                {null, null, null, TileSubject.BOOK_COMIC, null},
                {null, TileSubject.GAME_RISIKO, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };
        p.getBookShelf().setTileSubjectTaken(m);
        state.checkPersonalGoal(p);
        assertEquals(9, p.getPointPlayer().getScorePersonalGoal());
        m = new TileSubject[][]{
                {TileSubject.PLANT_BASIL, null, TileSubject.FRAME_DEGREE, null, null},
                {null, null, null, null, TileSubject.CAT_BLACK},
                {null, null, null, TileSubject.BOOK_COMIC, null},
                {null, TileSubject.GAME_RISIKO, null, null, null},
                {null, null, null, null, null},
                {null, null, TileSubject.TROPHY_CHAMPION, null, null}
        };
        p.getBookShelf().setTileSubjectTaken(m);
        state.checkPersonalGoal(p);
        assertEquals(12, p.getPointPlayer().getScorePersonalGoal());
        state.removeOnAchievedPersonalGoalListener(listener);
    }

    @Test
    void checkAdjacentTiles(){
        TileSubject[][] bookShelf = new TileSubject[][]{
                {TileSubject.GAME_RISIKO, null, null, null, null},
                {TileSubject.GAME_RISIKO, TileSubject.BOOK_COMIC, null, null, null},
                {TileSubject.GAME_RISIKO, TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, null, null},
                {TileSubject.GAME_RISIKO, TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.CAT_GRAY, null},
                {TileSubject.GAME_RISIKO, TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.CAT_GRAY, null},
                {TileSubject.GAME_RISIKO, TileSubject.BOOK_COMIC, TileSubject.PLANT_BASIL, TileSubject.CAT_GRAY, TileSubject.FRAME_DEGREE},
        };
        BookShelf b = new BookShelf();
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(0);
        p.setPointPlayer(new PointPlayer());
        b.setPlayer(p);
        p.setBookShelf(b);
        OnAdjacentTilesUpdatedListener listener = new OnAdjacentTilesUpdatedListener() {
            @Override
            public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {
                assertEquals(p.getNickName(), nickname);
            }
        };
        state.setOnAdjacentTilesUpdatedListener(listener);
        p.getBookShelf().setTileSubjectTaken(bookShelf);
        state.checkAdjacentTiles(p);
        assertEquals(8+5+3+2, p.getPointPlayer().getScoreAdjacentGoal());
        state.removeOnAdjacentTilesUpdatedListener(listener);
    }

    @Test
    void getAndSetLastException(){
        Exception e1 = new Exception("Exception.");
        State state = createStateWithPlayers();
        OnExceptionsListener listener = new OnExceptionsListener() {
            @Override
            public void onException(String cause, Exception e) {
                assertEquals(e1.getMessage(), e.getMessage());
            }
        };
        state.setOnExceptionsListener(listener);
        state.setLastException("player",e1);
        assertEquals(e1, state.getLastException());
        state.removeOnExceptionsListener(listener);
    }

    @Test
    void handleCommonGoal1(){
        State state = createStateWithPlayers();
        state.setPlayersNumber(4);
        BookShelf bookShelf = new BookShelf();
        CommonGoal goal1 = new StairCommonGoal(new Stack<>(), "Common Goal 1", "", 5);
        CommonGoal goal2 = new LineCommonGoal(new Stack<>(), "Common Goal 2" , "", 1,0,4,5, new int[]{1});
        goal1.getScoringTokens().push(2);
        goal1.getScoringTokens().push(4);
        goal1.getScoringTokens().push(6);
        goal1.getScoringTokens().push(8);
        goal2.getScoringTokens().push(2);
        goal2.getScoringTokens().push(4);
        goal2.getScoringTokens().push(6);
        goal2.getScoringTokens().push(8);
        TileSubject[][] bookshelf = new TileSubject[][]{
                {null,  null, null, null, null},
                {TileSubject.CAT_BLACK, null, null, null, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, null, null, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, null, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK}
        };
        Player p = state.getPlayers().get(0);
        p.setPointPlayer(new PointPlayer());
        String nickname = p.getNickName();
        OnAchievedCommonGoalListener listener = new OnAchievedCommonGoalListener() {
            @Override
            public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
                assertEquals(nickname, nicknamePlayer);
                assertEquals(1, numberCommonGoal);
                List<Coordinate> result = new ArrayList<>();
                result.add(new Coordinate(5,4));
                result.add(new Coordinate(5,3));
                result.add(new Coordinate(4,3));
                result.add(new Coordinate(5,2));
                result.add(new Coordinate(4,2));
                result.add(new Coordinate(3,2));
                result.add(new Coordinate(5,1));
                result.add(new Coordinate(4,1));
                result.add(new Coordinate(3,1));
                result.add(new Coordinate(2,1));
                result.add(new Coordinate(5,0));
                result.add(new Coordinate(4,0));
                result.add(new Coordinate(3,0));
                result.add(new Coordinate(2,0));
                result.add(new Coordinate(1,0));
                assertEquals(result.size(), tiles.size());
                for(Coordinate c : tiles){
                    assertTrue(containsElement(result, c));
                }
            }
        };
        OnChangedCommonGoalAvailableScoreListener listener1 = new OnChangedCommonGoalAvailableScoreListener() {
            @Override
            public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
                assertEquals(6, score);
                assertEquals(1, numberOfCommonGoal);
            }
        };
        OnAssignedCommonGoalListener listener2 = new OnAssignedCommonGoalListener() {
            @Override
            public void onAssignedCommonGoal(String description, int n, String id) {
                if(n == 1) {
                    assertEquals("Common Goal 1", description);
                    assertEquals(1, n);
                } else if (n==2){
                    assertEquals("Common Goal 2", description);
                    assertEquals(2, n);
                }
            }
        };
        state.setOnAssignedCommonGoalListener(listener2);
        state.setCommonGoal1(goal1);
        state.setCommonGoal2(goal2);
        state.removeOnAssignedCommonGoalListener(listener2);
        state.setAchievedCommonGoalListener(listener);
        state.setOnChangedCommonGoalAvailableScoreListener(listener1);
        p.setBookShelf(bookShelf);
        bookShelf.setPlayer(p);
        p.getBookShelf().setTileSubjectTaken(bookshelf);
        state.checkCommonGoal(p);
        state.removeAchievedCommonGoalListener(listener);
        state.removeOnChangedCommonGoalAvailableScoreListener(listener1);
        assertEquals(8, p.getPointPlayer().getScoreCommonGoal1());
        assertEquals(6, goal1.getScoringTokens().peek());
        assertEquals(8, goal2.getAvailableScore());
        assertEquals(0, p.getPointPlayer().getScoreCommonGoal2());
    }

    @Test
    void handleCommonGoal2(){
        State state = createStateWithPlayers();
        state.setPlayersNumber(4);
        BookShelf bookShelf = new BookShelf();
        CommonGoal goal2 = new StairCommonGoal(5);
        CommonGoal goal1 = new LineCommonGoal(1,0,4,5, new int[]{1});
        goal1.getScoringTokens().push(2);
        goal1.getScoringTokens().push(4);
        goal1.getScoringTokens().push(6);
        goal1.getScoringTokens().push(8);
        goal2.getScoringTokens().push(2);
        goal2.getScoringTokens().push(4);
        goal2.getScoringTokens().push(6);
        goal2.getScoringTokens().push(8);
        state.setCommonGoal1(goal1);
        state.setCommonGoal2(goal2);
        TileSubject[][] bookshelf = new TileSubject[][]{
                {null,  null, null, null, null},
                {TileSubject.CAT_BLACK, null, null, null, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, null, null, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, null, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, null},
                {TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK, TileSubject.CAT_BLACK}
        };
        Player p = state.getPlayers().get(0);
        p.setPointPlayer(new PointPlayer());
        String nickname = p.getNickName();
        OnAchievedCommonGoalListener listener = new OnAchievedCommonGoalListener() {
            @Override
            public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
                assertEquals(nickname, nicknamePlayer);
                assertEquals(2, numberCommonGoal);
                List<Coordinate> result = new ArrayList<>();
                result.add(new Coordinate(5,4));
                result.add(new Coordinate(5,3));
                result.add(new Coordinate(4,3));
                result.add(new Coordinate(5,2));
                result.add(new Coordinate(4,2));
                result.add(new Coordinate(3,2));
                result.add(new Coordinate(5,1));
                result.add(new Coordinate(4,1));
                result.add(new Coordinate(3,1));
                result.add(new Coordinate(2,1));
                result.add(new Coordinate(5,0));
                result.add(new Coordinate(4,0));
                result.add(new Coordinate(3,0));
                result.add(new Coordinate(2,0));
                result.add(new Coordinate(1,0));
                assertEquals(result.size(), tiles.size());
                for(Coordinate c : tiles){
                    assertTrue(containsElement(result, c));
                }
            }
        };
        OnChangedCommonGoalAvailableScoreListener listener1 = new OnChangedCommonGoalAvailableScoreListener() {
            @Override
            public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
                assertEquals(6, score);
                assertEquals(2, numberOfCommonGoal);
            }
        };
        state.setAchievedCommonGoalListener(listener);
        state.setOnChangedCommonGoalAvailableScoreListener(listener1);
        p.setBookShelf(bookShelf);
        bookShelf.setPlayer(p);
        p.getBookShelf().setTileSubjectTaken(bookshelf);
        state.checkCommonGoal(p);
        state.removeAchievedCommonGoalListener(listener);
        state.removeOnChangedCommonGoalAvailableScoreListener(listener1);
        assertEquals(8, p.getPointPlayer().getScoreCommonGoal2());
        assertEquals(6, state.getCommonGoal2().getScoringTokens().peek());
        assertEquals(8, state.getCommonGoal1().getAvailableScore());
        assertEquals(0, p.getPointPlayer().getScoreCommonGoal1());
    }

    private boolean containsElement(List<Coordinate> l, Coordinate e){
        for(Coordinate entry : l){
            if(entry.equals(e))
                return true;
        }
        return false;
    }

}