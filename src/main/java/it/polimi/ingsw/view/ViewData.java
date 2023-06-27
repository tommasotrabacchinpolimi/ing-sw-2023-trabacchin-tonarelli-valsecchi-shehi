package it.polimi.ingsw.view;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Triple;

import java.io.IOException;
import java.util.*;

/**
 * The ViewData class represents the model, which contains all the datas needed for the view of the game.
 * It contains information about players, game state, board, bookshelves, points, messages, and more.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ViewData {
    /**
     * Nickname of the player whose view his associated with the given {@link UI}.
     */
    private String thisPlayer;
    /**
     * Nickname of the current player.
     */
    private String currentPlayer;
    /**
     * Nickname of the winner.
     */
    private String winnerPlayer;
    /**
     * Map that associates each player in the game with his state.
     */
    private Map<String, String> playersState;
    /**
     * List of all players' nicknames in the game.
     */
    private List<String> players;
    /**
     * String that represents the game state.
     */
    private String gameState;
    /**
     * Matrix that represents the board.
     */
    private TileSubject[][] board;
    /**
     * Map that associates each player with his bookshelf matrix.
     */
    private Map<String, TileSubject[][]> bookShelves;
    /**
     * Matrix that represents the personal goal of this player.
     */
    private TileType[][] personalGoal;
    /**
     * Array of descriptions of the common goals in the game.
     */
    private String[] commonGoals;
    /**
     * Array of ids of the common goals in the game
     */
    private String[] idCommonGoals;
    /**
     * Map that associates each player with his points.
     */
    private Map<String, List<Integer>> playersPoints;
    /**
     * Map that associates each common goal with its available score.
     */
    private Map<Integer, Integer> availableScores;
    /**
     * List of all messages. Each message is represented with a triplet. The triplet is formed, following the given order,
     * by a string representing the sender's nickname,
     * a list of strings representing the recipients' nickname and the text of the message
     */
    private List<Triple<String, List<String>, String>> messages;
    /**
     * The user interface associated.
     */
    private UI userInterface;
    /**
     * Last thrown exception in the game
     */
    private String exception;
    /**
     * Boolean that id true if and only if there are some unread messages.
     */
    private boolean unreadMessages = false;


    /**
     * Constructs a new ViewData object with the specified dimensions for the board and bookshelves.
     *
     * @param BOARD_DIM      The dimension of the game square board.
     * @param BOOKSHELF_COL  The number of columns in the bookshelves.
     * @param BOOKSHELF_ROW  The number of rows in the bookshelves.
     */
    public ViewData(int BOARD_DIM, int BOOKSHELF_COL, int BOOKSHELF_ROW) {
        playersState = new HashMap<>();
        gameState = "";
        board = new TileSubject[BOARD_DIM][BOARD_DIM];
        bookShelves = new HashMap<>();
        personalGoal = new TileType[BOOKSHELF_ROW][BOOKSHELF_COL];
        commonGoals = new String[2];
        playersPoints = new HashMap<>();
        availableScores = new HashMap<>();
        this.userInterface = null;
        exception = "";
        players = new ArrayList<>();
    }

    /**
     * Returns the exception message associated with the view data.
     * @return The exception message.
     */
    public String getException() {
        return exception;
    }

    /**
     * Sets the exception message for the view data and triggers the corresponding event in the user interface.
     *
     * @param exception The exception message.
     * @throws IOException If an I/O error occurs.
     */
    public void setException(String exception) throws IOException {
        this.exception = exception;
        if(this.currentPlayer==null || this.thisPlayer==null || this.currentPlayer.equals(this.thisPlayer)) {
            userInterface.onException();
        }

    }

    /**
     * Sets the list of players in the game.
     * @param players The list of players.
     */
    public void setPlayers(List<String> players) {
        this.players = players;
    }

    /**
     * Returns the list of players in the game.
     * @return The list of players.
     */
    public List<String> getPlayers() {
        return this.players;
    }

    /**
     * Returns the user interface associated with the view data.
     * @return The user interface.
     */
    public UI getUserInterface() {
        return userInterface;
    }

    /**
     * Sets the user interface for the view data.
     * @param userInterface The user interface.
     */
    public void setUserInterface(UI userInterface) {
        this.userInterface = userInterface;
    }

    /**
     * Returns a map that associates each player in the game with his state.
     *
     * @return The map of player states.
     */
    public Map<String, String> getPlayersState() {
        return playersState;
    }

    /**
     * Sets the map that associates each player in the game with his state.
     * @param playersState The map of player states.
     */
    public void setPlayersState(Map<String, String> playersState) {
        this.playersState = playersState;
    }

    /**
     * Returns the current state of the game.
     * @return The game state.
     */
    public String getGameState() {
        return gameState;
    }

    /**
     * Sets the current state of the game and triggers the corresponding event in the user interface.
     * @param gameState The game state.
     * @throws IOException If an I/O error occurs.
     */
    public void setGameState(String gameState) throws IOException {
        this.gameState = gameState;
        userInterface.onGameStateChanged();
    }

    /**
     * Gets the common goal ids.
     * @return an array of common goals ids.
     */
    public String[] getIdCommonGoals() {
        return idCommonGoals;
    }

    /**
     * Sets the common goal ids.
     * @param idCommonGoals the array of common goal ids.
     */
    public void setIdCommonGoals(String[] idCommonGoals) {
        this.idCommonGoals = idCommonGoals;
    }

    /**
     * Returns the game board.
     * @return The game board.
     */
    public TileSubject[][] getBoard() {
        return board;
    }

    /**
     * Sets the game board.
     * @param board The game board.
     */
    public void setBoard(TileSubject[][] board) {
        this.board = board;
    }


    /**
     * Returns the map that associates each player with his bookshelf matrix.
     * @return The map of bookshelves.
     */
    public Map<String, TileSubject[][]> getBookShelves() {
        return bookShelves;
    }

    /**
     * Returns the bookshelf for the specified player.
     * @param nickname The nickname of the player.
     * @return The bookshelf for the player.
     */
    public TileSubject[][] getBookShelfByNickname(String nickname) {
        return bookShelves.get(nickname);
    }

    /**
     * Sets the map that associates each player with his bookshelf matrix.
     * @param bookShelves The map of bookshelves.
     */
    public void setBookShelves(Map<String, TileSubject[][]> bookShelves) {
        this.bookShelves = bookShelves;
    }

    /**
     * Returns the personal goal tiles of this player.
     * @return The personal goal matrix.
     */
    public TileType[][] getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Sets the personal goal tiles of this player.
     * @param personalGoal The personal goal matrix.
     */
    public void setPersonalGoal(TileType[][] personalGoal) {
        this.personalGoal = personalGoal;
    }

    /**
     * Returns the map that associates each player with his points.
     *
     * @return The map of player points.
     */
    public Map<String, List<Integer>> getPlayersPoints() {
        return playersPoints;
    }

    /**
     * Returns the points for the specified player.
     *
     * @param nickname The nickname of the player.
     * @return The points for the player.
     */
    public List<Integer> getPlayersPointsByNickname(String nickname){
        if(nickname == null || nickname.equals("")) return null;
        return playersPoints.get(nickname);
    }

    /**
     * Returns the total points for the specified player.
     * @param nickname The nickname of the player.
     * @return The total points for the player.
     */
    public Integer getTotalPointByNickname(String nickname){
        if(nickname == null || nickname.equals("")) return null;
        if(playersPoints.get(nickname)== null) return null; //????
        return playersPoints.get(nickname).stream().mapToInt(i -> i).sum();
    }

    /**
     * Sets the map that associates each player with his points.
     *
     * @param playersPoints The map of player points.
     */
    public void setPlayersPoints(Map<String, List<Integer>> playersPoints) {
        this.playersPoints = playersPoints;
    }

    /**
     * Returns the array of strings that represent the common goals in the game.
     *
     * @return The array of common goals.
     */
    public String[] getCommonGoals() {
        return commonGoals;
    }

    /**
     * Sets the array of strings that represent the common goals in the game.
     *
     * @param commonGoals The array of common goals.
     */
    public void setCommonGoals(String[] commonGoals) {
        this.commonGoals = commonGoals;
    }

    /**
     * Returns the map that associates each common goal with its available score.
     *
     * @return The map of available scores.
     */
    public Map<Integer, Integer> getAvailableScores() {
        return availableScores;
    }

    /**
     * Sets the map that associates each common goal with its available score.
     *
     * @param availableScores The map of available scores.
     */
    public void setAvailableScores(Map<Integer, Integer> availableScores) {
        this.availableScores = availableScores;
    }

    /**
     * Returns the current player's nickname.
     *
     * @return The current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player's nickname and triggers the corresponding event in the user interface.
     *
     * @param currentPlayer The current player.
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
        System.out.println("I want to notify current player");
        userInterface.onCurrentPlayerChanged(currentPlayer);
        System.out.println("I have notified current player");
    }

    /**
     * Returns the player's nickname associated with the view data.
     *
     * @return The player nickname.
     */
    public String getThisPlayer() {
        return thisPlayer;
    }

    /**
     * Sets the player's nickname associated with the view data.
     *
     * @param thisPlayer The player's nickname.
     */
    public void setThisPlayer(String thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

    /**
     * Returns the list of triples representing each message in the game.
     *
     * @return The list of messages.
     */
    public List<Triple<String, List<String>, String>> getMessages() {
        return messages;
    }

    /**
     * Sets the messages in the game and triggers the corresponding event in the user interface.
     * @param messages The list of messages.
     */
    public void setMessages(List<Triple<String, List<String>, String>> messages) {
        this.messages = messages;
        userInterface.onNewMessages();
    }

    /**
     * Adds a new message to the list of messages and triggers the corresponding event in the user interface.
     *
     * @param message The message to add.
     */
    public void addMessage(Triple<String, List<String>, String> message){
        unreadMessages = true;
        messages.add(message);
        userInterface.onNewMessage(message.getFirst());
    }

    /**
     * Returns the winner player's nickname in the game.
     *
     * @return The winner player.
     */
    public String getWinnerPlayer() {
        return winnerPlayer;
    }

    /**
     * Sets the winner player's nickname and triggers the corresponding event in the user interface.
     *
     * @param winnerPlayer The winner player.
     */
    public void setWinnerPlayer(String winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
        userInterface.showWinner();
    }

    /**
     * Returns the last message in the list of messages.
     * @return The last message.
     */
    public Triple<String, List<String>, String> getLastMessage() {
        return getMessages().get(getMessages().size() - 1);
    }

    /**
     * Sets the status of unread messages.
     * @param read The status of unread messages.
     */
    public void setUnreadMessages(boolean read) {
        this.unreadMessages = read;
    }

    /**
     * Checks if there are unread messages.
     * @return True if there are unread messages, false otherwise.
     */
    public boolean getUnreadMessages() {
        return unreadMessages;
    }
}