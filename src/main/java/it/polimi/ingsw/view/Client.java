package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net_alternative.ClientDispatcher;
import it.polimi.ingsw.net_alternative.ConnectionBuilder;
import it.polimi.ingsw.net_alternative.OnClientConnectionLostListener;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.*;

import static it.polimi.ingsw.view.Client.Choice.RMI;
import static it.polimi.ingsw.view.Client.Choice.SOCKET;

/**
 * The Client class represents a client in the game.
 * It implements the ClientInterface and LogicInterface interfaces,
 * which define the methods for interacting with the server and handling game logic.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 */
public class Client implements ClientInterface, LogicInterface, OnClientConnectionLostListener {

    /**
     * The UI object associated with the client.
     */
    private final UI ui;

    /**
     * The server interface for communication with the server.
     */
    private ServerInterface server;

    /**
     * The view data containing the game state and information for the client's UI.
     */
    private ViewData viewData;

    /**
     * The host address of the server.
     */
    private String host;

    /**
     * The port number for the server connection.
     */
    private int port;

    /**
     * Enumeration representing the client's connection choice (Socket or RMI).
     */
    enum Choice {
        SOCKET,
        RMI
    }

    /**
     * The client's connection choice.
     */
    private Choice choice;

    /**
     * Constructs a Client object with the specified UI and view data.
     *
     * @param ui    The UI object associated with the client.
     * @param model The view data containing the game state and information for the client's UI.
     */
    public Client(UI ui, ViewData model) {
        this.ui = ui;
        viewData = model;
    }

    /**
     * Establishes a socket connection with the server.
     *
     * @param host The host address of the server.
     * @param port The port number for the server connection.
     * @return The server interface for communication with the server.
     * @throws IOException if an I/O error occurs during the socket connection.
     */
    private ServerInterface getSocketConnection(String host, int port) throws IOException {
        return ConnectionBuilder.buildSocketConnection(port, host, new ClientDispatcher(this), this);
    }

    /**
     * Establishes an RMI connection with the server.
     *
     * @param host The host address of the server.
     * @param port The port number for the server connection.
     * @return The server interface for communication with the server.
     * @throws NotBoundException if the RMI registry binding is not found.
     * @throws IOException       if an I/O error occurs during the RMI connection.
     */
    private ServerInterface getRmiConnection(String host, int port) throws NotBoundException, IOException {
        return ConnectionBuilder.buildRmiConnection(port, host, new ClientDispatcher(this), this);
    }

    /**
     * Sets the server interface for communication with the server.
     *
     * @param server The server interface.
     */
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    /**
     * Callback method invoked when a common goal is achieved by a player.
     *
     * @param nicknamePlayer    The nickname of the player who achieved the common goal.
     * @param tiles             The list of coordinates of the tiles involved in the common goal.
     * @param numberCommonGoal  The number of the common goal.
     */
    @Override
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        //TODO
    }

    /**
     * Callback method invoked when a personal goal is achieved by a player.
     *
     * @param nickname The nickname of the player who achieved the personal goal.
     * @param tiles    The list of coordinates of the tiles involved in the personal goal.
     */
    @Override
    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {
        //TODO
    }

    /**
     * Callback method invoked when the adjacent tiles of a player are updated.
     *
     * @param nickname The nickname of the player whose adjacent tiles are updated.
     * @param tiles    The list of coordinates of the updated adjacent tiles.
     */
    @Override
    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {
        //TODO
    }

    /**
     * Callback method invoked when a common goal is assigned.
     *
     * @param description The description of the assigned common goal.
     * @param n           The number of the assigned common goal.
     */
    @Override
    public void onAssignedCommonGoal(String description, int n, String id) {
        viewData.getCommonGoals()[n - 1] = description;
        viewData.getIdCommonGoals()[n - 1] = id;
    }

    /**
     * Callback method invoked when a personal goal is assigned to a player.
     *
     * @param nickname     The nickname of the player to whom the personal goal is assigned.
     * @param goalPattern  The list of entry pattern goals assigned to the player.
     * @param scoreMap     The map containing the scores for the assigned goals.
     */
    @Override
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        goalPattern.forEach(e -> viewData.getPersonalGoal()[e.getRow()][e.getColumn()] = e.getTileType());
    }

    @Override
    public void onBoardRefilled() {
        //TODO
    }


    @Override
    public synchronized void onBoardUpdated(TileSubject[][] tileSubjects) {
        for (int i = 0; i < tileSubjects.length; i++) {
            for (int j = 0; j < tileSubjects[0].length; j++) {
                viewData.getBoard()[i][j] = tileSubjects[i][j];
            }
        }
    }

    /**
     * Callback method invoked when a player's bookshelf is updated.
     *
     * @param nickname    The nickname of the player whose bookshelf is updated.
     * @param bookShelf   The updated tile subjects representing the player's bookshelf.
     */
    @Override
    public synchronized void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        viewData.getBookShelves().computeIfAbsent(nickname, k -> new TileSubject[6][5]);
        for (int i = 0; i < bookShelf.length; i++) {
            for (int j = 0; j < bookShelf[0].length; j++) {
                viewData.getBookShelves().get(nickname)[i][j] = bookShelf[i][j];
            }
        }
    }

    /**
     * Callback method invoked when the available score for a common goal is changed.
     *
     * @param score             The new available score for the common goal.
     * @param numberOfCommonGoal The number of the common goal.
     */
    @Override
    public synchronized void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        viewData.getAvailableScores().put(numberOfCommonGoal - 1, score);
    }

    /**
     * Callback method invoked when the current player in the game changes.
     *
     * @param nickname The nickname of the new current player.
     */
    @Override
    public synchronized void onCurrentPlayerChangedListener(String nickname) {
        viewData.setCurrentPlayer(nickname);
    }

    /**
     * Callback method invoked when an exception occurs during the game.
     *
     * @param e The exception that occurred.
     */
    @Override
    public synchronized void onException(String cause, Exception e) {
        try{
            if(cause.equals(viewData.getThisPlayer())) {
                viewData.setException(e.getMessage());
            }
        }catch(IOException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Callback method invoked when the last player in the game is updated.
     *
     * @param nicknameLastPlayer The nickname of the last player.
     */
    @Override
    public synchronized void onLastPlayerUpdated(String nicknameLastPlayer) {

    }


    /**
     * Callback method invoked when a message is sent from a player.
     *
     * @param nicknameSender     The nickname of the player sending the message.
     * @param nicknameReceivers  The nicknames of the players receiving the message.
     * @param text               The content of the message.
     */
    @Override
    public synchronized void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        Triple<String, List<String>, String> triple = new Triple<>(nicknameSender, nicknameReceivers, text);
        viewData.addMessage(triple);
    }

    /**
     * Callback method invoked when multiple messages are sent from players.
     *
     * @param senderNicknames    The nicknames of the players sending the messages.
     * @param receiverNicknames  The nicknames of the players receiving the messages.
     * @param texts              The contents of the messages.
     */
    @Override
    public synchronized void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        List<Triple<String, List<String>, String>> messages = new ArrayList<>();
        for (int i = 0; i < senderNicknames.size(); i++) {
            messages.add(new Triple<>(senderNicknames.get(i), receiverNicknames.get(i), texts.get(i)));
        }
        viewData.setMessages(messages);
    }

    /**
     * Callback method invoked when the state of a player changes.
     *
     * @param nickname     The nickname of the player whose state changed.
     * @param playerState  The new state of the player.
     */
    @Override
    public synchronized void onPlayerStateChanged(String nickname, PlayerState playerState) {
        viewData.getPlayersState().put(nickname, playerState.toString());
    }


    /**
     * Callback method invoked when the points of a player are updated.
     *
     * @param nickName             The nickname of the player.
     * @param scoreAdjacentGoal    The score for the adjacent goal.
     * @param scoreCommonGoal1     The score for the first common goal.
     * @param scoreCommonGoal2     The score for the second common goal.
     * @param scoreEndGame         The score for the end game goal.
     * @param scorePersonalGoal    The score for the personal goal.
     */
    @Override
    public synchronized void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        viewData.getPlayersPoints().computeIfAbsent(nickName, k -> Arrays.asList(0, 0, 0, 0, 0));
        viewData.getPlayersPointsByNickname(nickName).set(0, scoreAdjacentGoal);
        viewData.getPlayersPointsByNickname(nickName).set(1, scoreCommonGoal1);
        viewData.getPlayersPointsByNickname(nickName).set(2, scoreCommonGoal2);
        viewData.getPlayersPointsByNickname(nickName).set(3, scoreEndGame);
        viewData.getPlayersPointsByNickname(nickName).set(4, scorePersonalGoal);
    }

    /**
     * Called when the state of the game changes. Updates the game state in the view.
     *
     * @param gameState The new game state.
     */
    @Override
    public synchronized void onStateChanged(GameState gameState) {
        try {
            viewData.setGameState(gameState.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * No operation. Does nothing.
     */
    @Override
    public synchronized void nop() {
        // No operation
    }

    /**
     * Called when the connection to the server is lost. Notifies the UI about the connection loss.
     */
    @Override
    public synchronized void onConnectionLost() {
        ui.onConnectionLost();
    }

    /**
     * Joins the game with the specified nickname. Sets the current player's nickname in the view and informs the server to join the game.
     *
     * @param nickname The nickname of the player.
     */
    @Override
    public synchronized void joinGame(String nickname) {
        viewData.setThisPlayer(nickname);
        server.joinGame(nickname);
    }

    /**
     * Creates a new game with the specified nickname and number of players. Sets the current player's nickname in the view and informs the server to create the game.
     *
     * @param nickname       The nickname of the player.
     * @param numberOfPlayer The number of players in the game.
     */
    @Override
    public synchronized void createGame(String nickname, int numberOfPlayer) {
        viewData.setThisPlayer(nickname);
        server.createGame(nickname, numberOfPlayer);
    }

    /**
     * Quits the current game. Resets the view data, updates the UI model, and informs the server to quit the game.
     */
    @Override
    public synchronized void quitGame() {
        viewData = new ViewData(9, 5, 6);
        viewData.setUserInterface(ui);
        ui.setModel(viewData);
        server.quitGame();
    }

    /**
     * Sends a message with the specified text to the given receivers' nicknames. Informs the server to send the message.
     *
     * @param text              The text of the message.
     * @param receiversNickname The nicknames of the message receivers.
     */
    @Override
    public synchronized void sentMessage(String text, String[] receiversNickname) {
        List<String> receivers = new ArrayList<>();
        for (String nick : receiversNickname) {
            if (nick != null) {
                receivers.add(nick);
            }
        }
        String[] r = new String[receivers.size()];
        int i = 0;
        for (String nick : receivers) {
            r[i] = nick;
            i++;
        }

        server.sentMessage(text, r);
    }

    /**
     * Drags the chosen tiles to the specified column in the bookshelf. Informs the server about the tile drag action.
     *
     * @param chosenTiles   The list of chosen tiles to be dragged.
     * @param chosenColumn  The target column in the bookshelf.
     */
    @Override
    public synchronized void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        server.dragTilesToBookShelf(chosenTiles, chosenColumn);
    }

    /**
     * Called when the winner of the game is changed. Updates the winner player in the view.
     *
     * @param nickname The nickname of the winner player.
     */
    @Override
    public synchronized void onWinnerChanged(String nickname) {
        viewData.setWinnerPlayer(nickname);
    }

    /**
     * Chooses the socket connection with the specified port and host. Establishes a socket connection with the server.
     *
     * @param port The port for the socket connection.
     * @param host The host address for the socket connection.
     * @throws IOException If an I/O error occurs while creating the socket connection.
     */
    @Override
    public synchronized void chosenSocket(int port, String host) throws IOException {
        ServerInterface serverInterface = this.getSocketConnection(host, port);
        this.setServer(serverInterface);
        this.port = port;
        this.host = host;
        this.choice = SOCKET;
    }

    /**
     * Chooses the RMI connection with the specified port and host. Establishes an RMI connection with the server.
     *
     * @param port The port for the RMI connection.
     * @param host The host address for the RMI connection.
     * @throws NotBoundException If the remote object is not bound to the specified name.
     * @throws IOException       If an I/O error occurs while creating the RMI connection.
     */
    @Override
    public synchronized void chosenRMI(int port, String host) throws NotBoundException, IOException {
        ServerInterface serverInterface = this.getRmiConnection(host, port);
        this.setServer(serverInterface);
        this.port = port;
        this.host = host;
        this.choice = RMI;
    }

    /**
     * Reconnects to the server using the previously chosen connection type, port, and host.
     */
    @Override
    public synchronized void reConnect() {
        while (true) {
            try {
                if (this.choice == SOCKET) {
                    chosenSocket(this.port, this.host);
                } else {
                    chosenRMI(this.port, this.host);
                }
                break;
            } catch (IOException | NotBoundException e) {
                continue;
            }
        }
    }

    /**
     * Called when the list of players in the game is changed. Updates the player list in the view.
     *
     * @param players The updated list of players.
     */
    @Override
    public synchronized void onPlayersListChanged(List<String> players) {
        viewData.setPlayers(players);
    }
}
