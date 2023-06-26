package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;
import it.polimi.ingsw.controller.listeners.*;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;
import it.polimi.ingsw.utils.Coordinate;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * State is a class that contains all the references necessary to modify and update the 'state' (hence the name of the class)
 * of the game.
 *
 * @apiNote The game is divided in turns that take place in a clockwise order starting from the first player. In fact, during each round of the game,
 * the player to the left of the previous currentPlayer becomes the new currentPlayer.
 *
 * @see PersonalGoal
 * @see CommonGoal
 * @see Board
 * @see Player
 * @see ChatMessage
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 22/04/2023
 */
public class State implements Serializable, OnUpdateNeededListener {
    @Serial
    @ExcludedFromJSON
    private static final long serialVersionUID = 26202152145454545L;

    /**
     * The living room {@link Board} in which the game is played.
     *
     * @see Board
     */
    @ExcludedFromJSON
    private Board board;

    /**
     * Identify the phase of the game
     *
     * @see GameState
     */
    @ExcludedFromJSON
    private GameState gameState;

    /**
     * The {@link CommonGoal common goals} that the players can achieve in order to earn more points.
     *
     * @see CommonGoal
     */
    @ExcludedFromJSON
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
    @ExcludedFromJSON
    private List<Player> players;

    /**
     * The {@link Player} who will play in the current round of the Game.
     *
     * @apiNote The Player {@code currentPlayer} must be contained in the list {@link State#players}.
     * @see Player
     */
    @ExcludedFromJSON
    private Player currentPlayer;

    /**
     * The number of player selected by the player that created the game.
     */
    @ExcludedFromJSON
    private int playersNumber;

    /**
     * The {@link Player} that wins the game, which is the player with the highest score at the end of the game.
     */
    @ExcludedFromJSON
    private Player winner;

    /**
     * List of {@link ChatMessage messages} sent between {@link Player players}.
     *
     * @apiNote The list is excluded from serialization
     * @see ChatMessage
     * @see Player
     */
    private transient List<ChatMessage> messages;

    /**
     * The {@link Player} that will play the last round in the game; after his turn, the game will end.
     */
    @ExcludedFromJSON
    private Player lastPlayer;

    /**
     * The most recent {@link Exception} thrown during the game.
     */
    private Exception lastException;

    @ExcludedFromJSON
    private final List<OnAchievedCommonGoalListener> achievedCommonGoalListeners;

    @ExcludedFromJSON
    private final List<OnStateChangedListener> stateChangedListeners;

    @ExcludedFromJSON
    private final List<OnLastPlayerUpdatedListener> lastPlayerUpdatedListeners;

    @ExcludedFromJSON
    private final List<OnMessageSentListener> messageSentListeners;

    @ExcludedFromJSON
    private final List<OnCurrentPlayerChangedListener> onCurrentPlayerChangedListeners;

    @ExcludedFromJSON
    private final List<OnAssignedCommonGoalListener> onAssignedCommonGoalListeners;

    @ExcludedFromJSON
    private final List<OnAchievedPersonalGoalListener> onAchievedPersonalGoalListeners;

    @ExcludedFromJSON
    private final List<OnAdjacentTilesUpdatedListener> onAdjacentTilesUpdatedListeners;

    @ExcludedFromJSON
    private final List<OnChangedCommonGoalAvailableScoreListener> onChangedCommonGoalAvailableScoreListenerListeners;

    @ExcludedFromJSON
    private final List<OnExceptionsListener> exceptionsListeners;

    @ExcludedFromJSON
    private final List<OnWinnerChangedListener> onWinnerChangedListeners;

    @ExcludedFromJSON
    private final List<OnPlayersListChangedListener> onPlayersListChangedListeners;

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
        lastPlayer = null;
        lastException = null;
        achievedCommonGoalListeners = new LinkedList<>();
        stateChangedListeners = new LinkedList<>();
        lastPlayerUpdatedListeners = new LinkedList<>();
        messageSentListeners = new LinkedList<>();
        onCurrentPlayerChangedListeners = new LinkedList<>();
        onAssignedCommonGoalListeners = new LinkedList<>();
        onAchievedPersonalGoalListeners = new LinkedList<>();
        onAdjacentTilesUpdatedListeners = new LinkedList<>();
        onChangedCommonGoalAvailableScoreListenerListeners = new LinkedList<>();
        exceptionsListeners = new LinkedList<>();
        onWinnerChangedListeners = new LinkedList<>();
        onPlayersListChangedListeners = new LinkedList<>();
        System.err.println("a state is being created");
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

    public void setOnAdjacentTilesUpdatedListener(OnAdjacentTilesUpdatedListener onAdjacentTilesUpdatedListener) {
        onAdjacentTilesUpdatedListeners.add(onAdjacentTilesUpdatedListener);
    }

    public void removeOnAdjacentTilesUpdatedListener(OnAdjacentTilesUpdatedListener onAdjacentTilesUpdatedListener) {
        onAdjacentTilesUpdatedListeners.remove(onAdjacentTilesUpdatedListener);
    }

    public void setMessageSentListener(OnMessageSentListener listener){
        messageSentListeners.add(listener);
    }

    public void removeMessageSentListener(OnMessageSentListener listener){
        messageSentListeners.remove(listener);
    }

    public void setOnCurrentPlayerChangedListener(OnCurrentPlayerChangedListener onCurrentPlayerChangedListener) {
        this.onCurrentPlayerChangedListeners.add(onCurrentPlayerChangedListener);
    }

    public void removeOnCurrentPlayerChangedListener(OnCurrentPlayerChangedListener onCurrentPlayerChangedListener) {
        this.onCurrentPlayerChangedListeners.remove(onCurrentPlayerChangedListener);
    }

    public void setOnAssignedCommonGoalListener(OnAssignedCommonGoalListener onAssignedCommonGoalListener) {
        this.onAssignedCommonGoalListeners.add(onAssignedCommonGoalListener);
    }

    public void removeOnAssignedCommonGoalListener(OnAssignedCommonGoalListener onAssignedCommonGoalListener) {
        this.onAssignedCommonGoalListeners.remove(onAssignedCommonGoalListener);
    }

    public void setOnAchievedPersonalGoalListener(OnAchievedPersonalGoalListener onAchievedPersonalGoalListener) {
        onAchievedPersonalGoalListeners.add(onAchievedPersonalGoalListener);
    }

    public void removeOnAchievedPersonalGoalListener(OnAchievedPersonalGoalListener onAchievedPersonalGoalListener) {
        onAchievedPersonalGoalListeners.remove(onAchievedPersonalGoalListener);
    }

    public void setOnChangedCommonGoalAvailableScoreListener(OnChangedCommonGoalAvailableScoreListener listener) {
        this.onChangedCommonGoalAvailableScoreListenerListeners.add(listener);
    }

    public void removeOnChangedCommonGoalAvailableScoreListener(OnChangedCommonGoalAvailableScoreListener listener) {
        this.onChangedCommonGoalAvailableScoreListenerListeners.remove(listener);
    }

    public void setOnExceptionsListener(OnExceptionsListener listener){
        exceptionsListeners.add(listener);
    }

    public void removeOnExceptionsListener(OnExceptionsListener listener){
        exceptionsListeners.remove(listener);
    }

    public void setOnPlayersListChangedListener(OnPlayersListChangedListener onPlayersListChangedListener) {
        this.onPlayersListChangedListeners.add(onPlayersListChangedListener);
    }

    public void removeOnPlayersListChangedListener(OnPlayersListChangedListener onPlayersListChangedListener) {
        this.onPlayersListChangedListeners.remove(onPlayersListChangedListener);
    }

    public void setOnWinnerChangedListener(OnWinnerChangedListener onWinnerChangedListener) {
        this.onWinnerChangedListeners.add(onWinnerChangedListener);
    }

    public void removeOnWinnerChangedListener(OnWinnerChangedListener onWinnerChangedListener) {
        this.onWinnerChangedListeners.remove(onWinnerChangedListener);
    }

    /**
     * Method that gets the {@link #lastPlayer}.
     * @return the {@link Player} set as {@link #lastPlayer} in the game.
     */
    public Player getLastPlayer() {
        return lastPlayer;
    }

    /**
     * Method that sets the {@link #lastPlayer}.
     * param lastPlayer the {@link Player} that needs to be set as {@link #lastPlayer} in the game.
     */
    public void setLastPlayer(Player lastPlayer) {
        this.lastPlayer = lastPlayer;
        notifyLastPlayerUpdated();
    }

    /**
     * Method that gets the {@link #gameState state} of the game.
     * @return the {@link GameState} associated to this game.
     * @see GameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Method that sets the {@link #gameState} of the game.
     * @param gameState the {@link GameState} that needs to be set to the game.
     * @see GameState
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyStateChanged();
        if(gameState.equals(GameState.INIT)){

        }
    }

    /**
     * Method that gets the {@link #winner} of the game
     * @param winner the {@link Player} that has won the game.
     */
    public void setWinner(Player winner) {
        this.winner = winner;
        notifyOnWinnerChanged();
    }

    /**
     * Method that sets the {@link #winner} of the game
     * @return the {@link Player} that has won the game.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Method that gets the {@link #playersNumber}.
     * @return the value of {@link #playersNumber}.
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * Method that sets the {@link #playersNumber}.
     * @param playersNumber the value of {@link #playersNumber}.
     */
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
        notifyAssignedCommonGoal();
        notifyChangedCommonGoalAvailableScore(commonGoal1.getAvailableScore(), 1);
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
        notifyAssignedCommonGoal();
        notifyChangedCommonGoalAvailableScore(commonGoal1.getAvailableScore(), 2);
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
        if(players.size() > playersNumber){
            throw new RuntimeException("Too many players inserted");
            // alzare eccezione eventualmente
        }
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
    public boolean addPlayer(Player player){
        System.out.println("adding player");
        if(players.stream().anyMatch(p-> p.getNickName().equals(player.getNickName())) || players.size() == playersNumber)
        {
            System.out.println("player not added");
            return false;
        }

        this.players.add(player);
        player.getPointPlayer().setPlayer(player);
        player.getBookShelf().setPlayer(player);
        notifyOnPlayersListChanged();
        return true;
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
        notifyCurrentPlayerChanged();
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
        notifyMessageSent();
    }

    /**
     * Method that gets {@link #lastException}
     * @return the {@link #lastException} thrown in the game.
     */
    public Exception getLastException() {
        return lastException;
    }

    /**
     * Method that sets {@link #lastException}
     * @param lastException  the {@link #lastException} thrown in the game.
     */
    public void setLastException(Exception lastException) {
        this.lastException = lastException;
        notifyOnExceptionsListener(lastException);
    }

    /**
     * Retrieve the Player that has the {@code nickName} chosen from the total players in a game
     *
     * @apiNote This method assume that each player has unique nickname inside a game
     * @param nickName the nickname of the player
     * @return the player that has the {@code nickName} passed as parameter
     */
    public Player getPlayerFromNick(String nickName){
        return players.stream()
                .filter( player -> nickName.equals(player.getNickName()))
                .findAny().orElse(null);
    }

    public Player getPlayerFromView(ClientInterface user){
        return players.stream().filter(player -> user == player.getVirtualView()).toList().get(0);
    }

    /**
     * Method that checks if the given {@link Player} has achieved the first e/o second {@link CommonGoal} and, if so, will assign the points to the player.
     * @param player the {@link Player} whose {@link BookShelf} is being tested in case of achievement of one or both common goals.
     */
    public void checkCommonGoal(Player player){
       List<EntryPatternGoal> result = new ArrayList<>();
        int newScore;

       result = commonGoal1.rule(player.getBookShelf().toTileTypeMatrix());
       if(result != null && player.getPointPlayer().getScoreCommonGoal1() == 0){
           newScore = commonGoal1.removeAvailableScore();
           if(newScore != 0) {
               player.getPointPlayer().setScoreCommonGoal1(newScore);
               notifyOnAchievedCommonGoal(result, player, 1);
               notifyChangedCommonGoalAvailableScore(commonGoal1.getScoringTokens().peek(), 1);
           }
       }

       result = commonGoal2.rule(player.getBookShelf().toTileTypeMatrix());
       if(result != null && player.getPointPlayer().getScoreCommonGoal2() == 0){
           newScore = commonGoal2.removeAvailableScore();
           if(newScore != 0) {
               player.getPointPlayer().setScoreCommonGoal2(newScore);
               notifyOnAchievedCommonGoal(result, player, 2);
               notifyChangedCommonGoalAvailableScore(commonGoal2.getScoringTokens().peek(), 2);
           }
       }
    }

    /**
     * Method that checks if the given {@link Player} has achieved his {@link PersonalGoal} and, if so, will assign the points to the player.
     * @param player the {@link Player} whose {@link BookShelf} is being tested in case of achievement of his personal goal.
     */
    public void checkPersonalGoal(Player player) {
        PersonalGoal personalGoal = player.getPersonalGoal();
        BookShelf bookShelf = player.getBookShelf();
        int personalGoalMatches = 0;
        List<EntryPatternGoal> personalGoalTiles = new LinkedList<>();
        for(EntryPatternGoal e : personalGoal.getGoalPattern()) {
            if(bookShelf.toTileTypeMatrix()[e.getRow()][e.getColumn()] == e.getTileType()) {
                personalGoalMatches++;
                personalGoalTiles.add(new EntryPatternGoal(e.getRow(), e.getColumn(), e.getTileType()));
            }
        }
        int oldScore = player.getPointPlayer().getScorePersonalGoal();
        if(personalGoal.getScoreMap().get(personalGoalMatches)==null) {
            throw new NullPointerException();
        }
        int newScore = personalGoal.getScoreMap().get(personalGoalMatches);

        player.getPointPlayer().setScorePersonalGoal(personalGoal.getScoreMap().get(personalGoalMatches));
        if (newScore != oldScore) {
            notifyOnAchievedPersonalGoal(personalGoalTiles, player);
        }
    }

    /**
     * Method that checks if the given {@link Player} has groups of adjacent item tiles of the same type on his bookshelf and, if so, will assign the points to the player.
     * @param player the {@link Player} whose {@link BookShelf} is being tested in case of groups of adjacent item tiles of the same type.
     */
    public void checkAdjacentTiles(Player player) {
        int scoreAdjacentGoal = 0;
        BookShelf bookShelf = player.getBookShelf();
        Set<Set<EntryPatternGoal>> groups = findGroups(bookShelf.toTileTypeMatrix());
        for(Set<EntryPatternGoal> group : groups){
            scoreAdjacentGoal += adjacentGroupsScore(group.size());
        }
        if(scoreAdjacentGoal != player.getPointPlayer().getScoreAdjacentGoal()) {
            player.getPointPlayer().setScoreAdjacentGoal(scoreAdjacentGoal);
            notifyAdjacentTilesUpdated(groups.stream().flatMap(Collection::stream).toList(), player);
        }
    }

    private void notifyAdjacentTilesUpdated(List<EntryPatternGoal> tiles, Player player) {
        for(OnAdjacentTilesUpdatedListener onAdjacentTilesUpdatedListener : onAdjacentTilesUpdatedListeners) {
            List<Coordinate> list = new ArrayList<>();
            for(EntryPatternGoal tile : tiles){
                list.add(new Coordinate(tile.getRow(), tile.getColumn()));
            }
            onAdjacentTilesUpdatedListener.onAdjacentTilesUpdated(player.getNickName(), list);
        }
    }

    /**
     * Method that returns the number of points assigned when there is a group of {@code size} adjacent tiles of the same type
     * @param size number of adjacent tiles of the same type.
     * @return the points granted depending on how many tiles are connected
     */
    private int adjacentGroupsScore(int size) {
        if(size >= 6) {
            return 8;
        }
        else if(size == 5) {
            return 5;
        }
        else if(size == 4) {
            return 3;
        }
        else if(size == 3) {
            return 2;
        }
        else {
            return 0;
        }
    }

    private void notifyOnAchievedPersonalGoal(List<EntryPatternGoal> tiles, Player player) {
        for(OnAchievedPersonalGoalListener onAchievedPersonalGoalListener : onAchievedPersonalGoalListeners) {
            List<Coordinate> list = new ArrayList<>();
            for(EntryPatternGoal tile : tiles){
                list.add(new Coordinate(tile.getRow(), tile.getColumn()));
            }
            onAchievedPersonalGoalListener.onAchievedPersonalGoal(player.getNickName(), list);
        }
    }

    private void notifyOnAchievedCommonGoal(List<EntryPatternGoal> tiles, Player player, int numberCommonGoal){
        List<EntryPatternGoal> copy_result = new ArrayList<>();
        for (EntryPatternGoal entry : tiles) {
            copy_result.add(new EntryPatternGoal(entry.getRow(), entry.getColumn(), entry.getTileType()));
        }
        for(OnAchievedCommonGoalListener listener : achievedCommonGoalListeners){
            listener.onAchievedCommonGoal(player.getNickName(), copy_result.stream().map(e -> new Coordinate(e.getRow(), e.getColumn())).toList(), numberCommonGoal);
        }
    }

    private void notifyStateChanged(){
        for(OnStateChangedListener stateChangedListener : stateChangedListeners){
            System.out.println("game state changed notified");
            stateChangedListener.onStateChanged(this.getGameState());
        }
    }

    private void notifyLastPlayerUpdated(){
        if(lastPlayer != null) {
            for (OnLastPlayerUpdatedListener lastPlayerUpdatedListener : lastPlayerUpdatedListeners) {
                lastPlayerUpdatedListener.onLastPlayerUpdated(lastPlayer.getNickName());
            }
        }
    }

    private void notifyMessageSent() {
        System.out.println("try messages sent");
        ChatMessage message = messages.get(messages.size()-1);
        List<String> nicknameReceivers = message.getReceivers().stream().map(Player::getNickName).toList();
        for(OnMessageSentListener listener : messageSentListeners){
            listener.onMessageSent(message.getSender().getNickName(), nicknameReceivers, message.getText());
        }
        System.out.println("messages sent");
    }

    private void notifyAssignedCommonGoal() {
        for(OnAssignedCommonGoalListener onAssignedCommonGoalListener : onAssignedCommonGoalListeners) {
            if(this.getCommonGoal1() != null) {
                onAssignedCommonGoalListener.onAssignedCommonGoal(this.getCommonGoal1().getDescription(), 1);
            }
            if(this.getCommonGoal2() != null) {
                onAssignedCommonGoalListener.onAssignedCommonGoal(this.getCommonGoal2().getDescription(), 2);
            }

        }
    }

    private void notifyCurrentPlayerChanged() {
        for(OnCurrentPlayerChangedListener onCurrentPlayerChangedListener :onCurrentPlayerChangedListeners) {
            onCurrentPlayerChangedListener.onCurrentPlayerChangedListener(currentPlayer.getNickName());
        }
    }

    private void notifyChangedCommonGoalAvailableScore(int newScore, int numberOfCommonGoal){
        for(OnChangedCommonGoalAvailableScoreListener listener: onChangedCommonGoalAvailableScoreListenerListeners){
            listener.onChangedCommonGoalAvailableScore(newScore, numberOfCommonGoal);
        }
    }

    private void notifyOnExceptionsListener(Exception e){
        for(OnExceptionsListener listener: exceptionsListeners){
            listener.onException(e);
        }
    }

    private void notifyOnWinnerChanged() {
        for(OnWinnerChangedListener onWinnerChangedListener : onWinnerChangedListeners) {
            onWinnerChangedListener.onWinnerChanged(getWinner().getNickName());
        }
    }

    private void notifyOnPlayersListChanged() {
        for(OnPlayersListChangedListener onPlayersListChangedListener : onPlayersListChangedListeners) {
            onPlayersListChangedListener.onPlayersListChanged(players.stream().map(Player::getNickName).toList());
            System.out.println("players list notified");
        }
    }

    @Override
    public void onUpdateNeededListener(Player player) {
        notifyOnPlayersListChanged();
        //onPlayersListChangedListeners
                        //.forEach(v -> v.onPlayersListChanged(players.stream().map(Player::getNickName).toList()));

        stateChangedListeners
                .forEach(v -> v.onStateChanged(gameState));

        if(currentPlayer != null) {
            onCurrentPlayerChangedListeners
                    .forEach(v -> v.onCurrentPlayerChangedListener(currentPlayer.getNickName()));
        }

        if(lastPlayer != null) {
            lastPlayerUpdatedListeners
                    .forEach(v -> v.onLastPlayerUpdated(lastPlayer.getNickName()));

        }

        if(this.getCommonGoal2() != null && this.getCommonGoal1() != null) {
            onAssignedCommonGoalListeners
                    .forEach(v -> {v.onAssignedCommonGoal(this.getCommonGoal1().getDescription(),1); v.onAssignedCommonGoal(this.getCommonGoal2().getDescription(), 2);});
            onChangedCommonGoalAvailableScoreListenerListeners.forEach(v -> {v.onChangedCommonGoalAvailableScore(this.getCommonGoal1().getAvailableScore(),1); v.onChangedCommonGoalAvailableScore(this.getCommonGoal2().getAvailableScore(), 2);});
        }


        List<String> senderNicknames = messages.stream().map(m -> m.getSender().getNickName()).toList();
        List<List<String>> receiverNicknames = messages.stream().map(m -> m.getReceivers().stream().map(Player::getNickName).toList()).toList();
        List<String> texts = messages.stream().map(ChatMessage::getText).toList();
        messageSentListeners
                .forEach(v -> v.onMessagesSentUpdate(senderNicknames, receiverNicknames, texts));
    }

    public static Set<Set<EntryPatternGoal>> findGroups(TileType[][] bookShelf){
        boolean[][] alreadyTaken = new boolean[bookShelf.length][bookShelf[0].length];//initialized to false
        Set<Set<EntryPatternGoal>> result = new HashSet<Set<EntryPatternGoal>>();
        for(int i = 0; i < bookShelf.length; i++){
            for(int j = 0; j < bookShelf[0].length; j++){
                findSingleGroup(i, j, bookShelf, alreadyTaken, bookShelf[i][j]).ifPresent(result::add);
            }
        }
        return result;
    }

    private static Optional<Set<EntryPatternGoal>> findSingleGroup(int i, int j, TileType[][] bookShelf, boolean[][] alreadyTaken, TileType tileType){
        if(tileType == null){
            return Optional.empty();
        }
        if (i < 0 || i >= bookShelf.length || j < 0 || j >= bookShelf[0].length){// nothing is to be returned if arguments are illegal
            return Optional.empty();
        }
        if (alreadyTaken[i][j]){ //if this bookShelf is already part of another group then it should not be considered for another group
            return Optional.empty();
        }
        Set<EntryPatternGoal> result = new HashSet<>();// Java documentation recommends using HashSet, unless otherwise required
        if (bookShelf[i][j] != tileType){//we want only entries whose type is tileType
            return Optional.empty();
        }
        else{
            result.add(new EntryPatternGoal(j, i, tileType));//if the type is correct then the (i,j)-entry can be added to the group
            alreadyTaken[i][j] = true;
        }

        findSingleGroup(i-1, j, bookShelf, alreadyTaken, tileType).ifPresent(result::addAll);
        findSingleGroup(i+1, j, bookShelf, alreadyTaken, tileType).ifPresent(result::addAll);
        findSingleGroup(i, j-1, bookShelf, alreadyTaken, tileType).ifPresent(result::addAll);
        findSingleGroup(i, j+1, bookShelf, alreadyTaken, tileType).ifPresent(result::addAll);
        return Optional.of(result);
    }

}
