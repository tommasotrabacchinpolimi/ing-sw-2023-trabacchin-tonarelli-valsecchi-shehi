package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class State implements Serializable {
    private static final long serialVersionUID = 26202152145454545L;
    private Board board;
    private CommonGoal commonGoal1, commonGoal2;
    private List<Player> players;
    transient List<ChatMessage> messages; //excluded from serialization

    public State(){
        board = new Board();
        players = new ArrayList<>();
        messages = new LinkedList<>();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public CommonGoal getCommonGoal1() {
        return commonGoal1;
    }

    public void setCommonGoal1(CommonGoal commonGoal1) {
        this.commonGoal1 = commonGoal1;
    }

    public CommonGoal getCommonGoal2() {
        return commonGoal2;
    }

    public void setCommonGoal2(CommonGoal commonGoal2) {
        this.commonGoal2 = commonGoal2;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    // eventualmente da eliminare
    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(ChatMessage message){
        this.messages.add(message);
    }
}
