package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.listeners.*;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
public class State<R extends ClientInterface> implements Serializable {
    @Serial
    private static final long serialVersionUID = 26202152145454545L;

    /**
     * The living room {@link Board} in which the game is played.
     *
     * @see Board
     */
    private Board board;

    private GameState gameState;

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
    private List<Player<R>> players;
    /**
     * The {@link Player} who will play in the current round of the Game.
     *
     * @apiNote The Player {@code currentPlayer} must be contained in the list {@link State#players}.
     * @see Player
     */
    private Player<R> currentPlayer;
    private int playersNumber;
    /**
     * List of {@link ChatMessage messages} sent between {@link Player players}.
     *
     * @apiNote The list is excluded from serialization
     * @see ChatMessage
     * @see Player
     */
    private transient List<ChatMessage<R>> messages;

    private Player<R> lastPlayer;

    private final List<OnAchievedCommonGoalListener> achievedCommonGoalListeners;
    private final List<OnStateChangedListener> stateChangedListeners;
    private final List<OnLastPlayerUpdatedListener> lastPlayerUpdatedListeners;
    private final List<OnMessageSentListener> messageSentListeners;

    private final List<OnCurrentPlayerChangedListener> onCurrentPlayerChangedListeners;

    /**
     * Construct of the class that creates the fields of the class.
     *
     * @see Board
     * @see Player
     * @see ChatMessage
     * @see CommonGoal
     */
    public State(){
        gameState = GameState.INIT;
        board = new Board();
        players = new ArrayList<>();
        currentPlayer = null;
        messages = new LinkedList<>();
        achievedCommonGoalListeners = new ArrayList<>();
        stateChangedListeners = new ArrayList<>();
        lastPlayer = null;
        lastPlayerUpdatedListeners = new ArrayList<>();
        messageSentListeners = new ArrayList<>();
        onCurrentPlayerChangedListeners = new LinkedList<>();
    }

    public void setAchievedCommonGoalListener(OnAchievedCommonGoalListener listener) {
        achievedCommonGoalListeners.add(listener);
    }

    public void removeAchievedCommonGoalListener(OnAchievedCommonGoalListener listener) {
         achievedCommonGoalListeners.remove(listener);
    }

    public void setStateChangedListener(OnStateChangedListener stateChangedListener){
        this.stateChangedListeners.add(stateChangedListener);
    }

    public void removeStateChangedListener(OnStateChangedListener stateChangedListener){
        this.stateChangedListeners.remove(stateChangedListener);
    }

    public void setLastPlayerUpdatedListener(OnLastPlayerUpdatedListener lastPlayerUpdatedListener){
        this.lastPlayerUpdatedListeners.add(lastPlayerUpdatedListener);
    }

    public void removeLastPlayerUpdatedListeners(OnLastPlayerUpdatedListener lastPlayerUpdatedListener){
        this.lastPlayerUpdatedListeners.remove(lastPlayerUpdatedListener);
    }

    public void setMessageSentListener(OnMessageSentListener listener){
        messageSentListeners.add(listener);
    }

    public void removeMessageSentListener(OnMessageSentListener listener){
        messageSentListeners.remove(listener);
    }

    public Player<R> getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(Player<R> lastPlayer) {
        this.lastPlayer = lastPlayer;
        notifyLastPlayerUpdated();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyStateChanged();
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
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
    public List<Player<R>> getPlayers() {
        return players;
    }

    /**
     * Method that gets the list of players of the game.
     * @param players The list of {@link Player players} that will be set to {@link State#players}.
     *
     * @see Player
     */
    public void setPlayers(List<Player<R>> players) {
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
    public boolean addPlayer(Player<R> player){
        if(players.stream().anyMatch(p-> p.getNickName().equals(player.getNickName())))
            return false;
        this.players.add(player);
        return true;
    }

    /**
     * Method that sets the current {@link Player player} of the game.
     * @return The {@link State#currentPlayer} of the game.
     *
     * @apiNote The Player {@link State#currentPlayer} must be contained in the list {@link State#players}.
     * @see Player
     */
    public Player<R> getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method that gets the current {@link Player player} of the game.
     * @param currentPlayer The {@link Player player} that will be set to {@link State#currentPlayer}.
     *
     * @apiNote The Player {@link State#currentPlayer} must be contained in the list {@link State#players}.
     * @see Player
     */
    public void setCurrentPlayer(Player<R> currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Method that gets the list of {@link ChatMessage messages} of the game.
     * @return The list {@link State#messages} of {@link ChatMessage}.
     *
     * @see ChatMessage
     */
    public List<ChatMessage<R>> getMessages() {
        return messages;
    }

    /**
     * Method that sets the list {@link State#messages}.
     * @param messages The list of {@link ChatMessage} that will be set to {@link State#messages}.
     *
     * @see ChatMessage
     */
    public void setMessages(List<ChatMessage<R>> messages) { // eventualmente da eliminare
        this.messages = messages;
    }

    /**
     * Method that adds a {@link ChatMessage message} to the list {@link State#messages}.
     * @param message The {@link ChatMessage} that will be added to the list {@link State#messages}.
     *
     * @see ChatMessage
     */
    public void addMessage(ChatMessage<R> message){
        this.messages.add(message);
        notifyMessageSent();
    }

    /**
     * Retrieve the Player that has the {@code nickName} chosen from the total players in a game
     *
     * @apiNote This method assume that each player has unique nickname inside a game
     * @param nickName the nickname of the player
     * @return the player that has the {@code nickName} passed as parameter
     */
    public Player<R> getPlayerFromNick(String nickName){
        return players.stream()
                .filter( player -> nickName.equals(player.getNickName()))
                .toList()
                .get(0);
    }

    public Player<R> getPlayerFromView(R user){
        return players.stream().filter(player -> user == player.getVirtualView()).toList().get(0);
    }

    public void checkCommonGoal(Player<R> player){
       List<EntryPatternGoal> result = new ArrayList<>();

       result = commonGoal1.rule(player.getBookShelf().toTileTypeMatrix());
       if(result != null && player.getPointPlayer().getScoreCommonGoal1() == 0){
           player.getPointPlayer().setScoreCommonGoal1(commonGoal1.getAvailableScore());
           notifyOnAchievedCommonGoal(result, player, 1);
       }

       result = commonGoal2.rule(player.getBookShelf().toTileTypeMatrix());
       if(result != null && player.getPointPlayer().getScoreCommonGoal2() == 0){
           player.getPointPlayer().setScoreCommonGoal2(commonGoal2.getAvailableScore());
           notifyOnAchievedCommonGoal(result, player, 2);
       }

    }

    public void notifyOnAchievedCommonGoal(List<EntryPatternGoal> tiles, Player<R> player, int numberCommonGoal){
        List<EntryPatternGoal> copy_result = new ArrayList<>();
        for (EntryPatternGoal entry : tiles) {
            copy_result.add(new EntryPatternGoal(entry.getRow(), entry.getColumn(), entry.getTileType()));
        }
        for(OnAchievedCommonGoalListener listener : achievedCommonGoalListeners){
            listener.onAchievedCommonGoal(player.getNickName(), copy_result, numberCommonGoal);
        }
    }

    public void notifyStateChanged(){
        for(OnStateChangedListener stateChangedListener: stateChangedListeners){
            stateChangedListener.onStateChanged(this.getGameState());
        }
    }

    public void notifyLastPlayerUpdated(){
        if(lastPlayer!=null) {
            for (OnLastPlayerUpdatedListener lastPlayerUpdatedListener : lastPlayerUpdatedListeners) {
                lastPlayerUpdatedListener.onLastPlayerUpdated(lastPlayer.getNickName());
            }
        }
    }

    public void notifyMessageSent(){
        ChatMessage<R> message = messages.get(messages.size()-1);
        List<String> nicknameReceivers = message.getReceivers().stream().map(Player<R>::getNickName).toList();
        for(OnMessageSentListener listener : messageSentListeners){
            listener.onMessageSent(message.getSender().getNickName(), nicknameReceivers, message.getText());
        }
    }

    public void setOnCurrentPlayerChangedListener(OnCurrentPlayerChangedListener onCurrentPlayerChangedListener) {
        this.onCurrentPlayerChangedListeners.add(onCurrentPlayerChangedListener);
    }

    public void removeOnCurrentPlayerChangedListener(OnCurrentPlayerChangedListener onCurrentPlayerChangedListener) {
        this.onCurrentPlayerChangedListeners.remove(onCurrentPlayerChangedListener);
    }

    public void notifyCurrentPlayerChanged() {
        for(OnCurrentPlayerChangedListener onCurrentPlayerChangedListener :onCurrentPlayerChangedListeners) {
            onCurrentPlayerChangedListener.onCurrentPlayerChangedListener(currentPlayer.getNickName());
        }
    }



}
