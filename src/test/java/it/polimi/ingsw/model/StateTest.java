package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CommonGoalDeserializer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class StateTest {

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
    void getAndSetCommonGoal1() {

    }

    @Test
    void getAndSetCommonGoal2() {
        //TO DO
    }

    @Test
    void getAndSetAndAddPlayers() {
        State state = new State();
        Player p1, p2, p3;
        state.setPlayersNumber(3);
        p1 = new Player("Melanie");
        p2 = new Player("Tommaso");
        p3 = new Player("Adem");
        Player[] p = new Player[]{p1, p2};
        state.setPlayers(Arrays.stream(p).toList());
        assertEquals(p1, state.getPlayers().get(0));
        assertEquals(p2, state.getPlayers().get(1));

        final State s = new State();
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
    }

    @Test
    void getAndSetCurrentPlayer() {
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(0);
        state.setCurrentPlayer(p);
        assertEquals(p, state.getCurrentPlayer());
    }

    @Test
    void getAndSetAndAddMessages() {
        State state = createStateWithPlayers();
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
    }

    @Test
    void getPlayerFromNick() {
        //
    }

    @Test
    void getAndSetLastPlayer(){
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(1);
        state.setLastPlayer(p);
        assertEquals(p, state.getLastPlayer());
    }

    @Test
    void getAndSetWinnerPlayer(){
        State state = createStateWithPlayers();
        Player p = state.getPlayers().get(2);
        state.setWinner(p);
        assertEquals(p, state.getWinner());
    }

    @Test
    void getAndSetGameState(){
        State state = createStateWithPlayers();
        state.setGameState(GameState.FINAL);
        assertEquals("FINAL", state.getGameState().toString());
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
    void checkCommonGoal(){
        // TO DO
    }

    @Test
    void checkPersonalGoal(){
        //TO DO
    }

    @Test
    void checkAdjacentTiles(){
        //TO DO
    }

    @Test
    void getAndSetLastException(){
        Exception e = new Exception("Exception.");
        State state = createStateWithPlayers();
        state.setLastException(e);
        assertEquals(e, state.getLastException());
    }


}