package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * State is a class that contains all the references necessary to modify and update the 'state' (hence the name of the class)
 * of the game.
 *
 * @apiNote The game is divided in turns that take place in a clockwise order starting from the first player. In fact, during each round of the game,
 * the player to the left of the previous currentPlayer becomes the new currentPlayer.
 *
 * @author Melanie Tonarelli
 * @version 1.0, 27/03/23
 * @see PersonalGoal
 * @see CommonGoal
 * @see Board
 * @see Player
 * @see ChatMessage
 */
public class State implements Serializable {
    private static final long serialVersionUID = 26202152145454545L;
    /**
     * The living room {@link Board} in which the game is played.
     *
     * @see Board
     */
    private Board board;
    /**
     * The {@link CommonGoal common goals} that the players can achieve in order to earn more points.
     *
     * @see CommonGoal
     */
    private CommonGoal commonGoal1, commonGoal2;
    /**
     * List of {@link Player players} sorted from first logged in to last logged in.
     *
     * @apiNote This list has a size between 2 and 4 inclusive. The players are set as following:
     * <pre> {@code players.get(0) is the first player logged into the game.
     * players.get(players.size()-1) is the last player logged into the game.
     *     }
     * </pre>
     * @see Player
     */
    private List<Player> players;
    /**
     * The {@link Player} who will play in the current round of the Game.
     *
     * @apiNote The Player {@link State#currentPlayer} must be contained in the list {@link State#players}.
     * @see Player
     */
    private Player currentPlayer;
    /**
     * List of {@link ChatMessage messages} sent between {@link Player players}.
     *
     * @apiNote The list is excluded from serialization
     * @see ChatMessage
     * @see Player
     */
    transient List<ChatMessage> messages; //excluded from serialization

    /**
     * Construct of the class that creates the fields of the class.
     *
     * @see Board
     * @see Player
     * @see ChatMessage
     * @see CommonGoal
     */
    public State(){
        board = new Board();
        players = new ArrayList<>();
        currentPlayer = null;
        messages = new LinkedList<>();
    }

    /**
     * Method that gets the living room board of the game.
     * @return The {@link Board} in which the game is played.
     *
     * @see Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Method that sets the living room board of the game.
     * @param board The {@link Board} in which the game will be played.
     *
     * @see Board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Method that gets the first {@link CommonGoal common goal} of the game.
     * @return The first {@link CommonGoal common goal} of the game.
     *
     * @see CommonGoal
     */
    public CommonGoal getCommonGoal1() {
        return commonGoal1;
    }

    /**
     * Method that sets the first {@link CommonGoal common goal} of the game.
     * @param commonGoal1 The {@link CommonGoal common goal} that will be set to {@link State#commonGoal1}.
     *
     * @see CommonGoal
     */
    public void setCommonGoal1(CommonGoal commonGoal1) {
        this.commonGoal1 = commonGoal1;
    }

    /**
     * Method that gets the second {@link CommonGoal common goal} of the game.
     * @return The second {@link CommonGoal common goal} of the game.
     *
     * @see CommonGoal
     */
    public CommonGoal getCommonGoal2() {
        return commonGoal2;
    }

    /**
     *Method that sets the second {@link CommonGoal common goal} of the game.
     * @param commonGoal2 The {@link CommonGoal common goal} that will be set to {@link State#commonGoal2}.
     *
     * @see CommonGoal
     */
    public void setCommonGoal2(CommonGoal commonGoal2) {
        this.commonGoal2 = commonGoal2;
    }

    /**
     * Method that gets the list of the {@link Player players} of the game.
     * @return The list of {@link Player players} of the game.
     *
     * @see Player
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Method that gets the list of players of the game.
     * @param players The list of {@link Player players} that will be set to {@link State#players}.
     *
     * @see Player
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Method that adds a {@link Player player} to the list {@link State#players}.
     * @param player The {@link Player} that will be added to {@link State#players}.
     *
     * @apiNote This list has a size between 2 and 4 inclusive. The players are set as following:
     * <pre> {@code players.get(0) is the first player logged into the game.
     * players.get(players.size()-1) is the last player logged into the game.
     * @see Player
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * Method that sets the current {@link Player player} of the game.
     * @return The {@link State#currentPlayer} of the game.
     *
     * @apiNote The Player {@link State#currentPlayer} must be contained in the list {@link State#players}.
     * @see Player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method that gets the current {@link Player player} of the game.
     * @param currentPlayer The {@link Player player} that will be set to {@link State#currentPlayer}.
     *
     * @apiNote The Player {@link State#currentPlayer} must be contained in the list {@link State#players}.
     * @see Player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Method that gets the list of {@link ChatMessage messages} of the game.
     * @return The list {@link State#messages} of {@link ChatMessage}.
     *
     * @see ChatMessage
     */
    public List<ChatMessage> getMessages() {
        return messages;
    }

    /**
     * Method that sets the list {@link State#messages}.
     * @param messages The list of {@link ChatMessage} that will be set to {@link State#messages}.
     *
     * @see ChatMessage
     */
    public void setMessages(List<ChatMessage> messages) { // eventualmente da eliminare
        this.messages = messages;
    }

    /**
     * Method that adds a {@link ChatMessage message} to the list {@link State#messages}.
     * @param message The {@link ChatMessage} that will be added to the list {@link State#messages}.
     *
     * @see ChatMessage
     */
    public void addMessage(ChatMessage message){
        this.messages.add(message);
    }
}
