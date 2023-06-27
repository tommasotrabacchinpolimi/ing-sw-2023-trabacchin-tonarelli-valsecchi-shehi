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

public class Client implements ClientInterface, LogicInterface, OnClientConnectionLostListener {
    private final UI ui;
    private ServerInterface server;

    private ViewData viewData;

    private String host;

    private int port;

    enum Choice {
        SOCKET,
        RMI
    }
    private Choice choice;


    public Client(UI ui, ViewData model) {
        this.ui = ui;
        viewData = model;
    }

    private ServerInterface getSocketConnection(String host, int port) throws IOException {
        return ConnectionBuilder.buildSocketConnection(port, host, new ClientDispatcher(this), this);
    }

    private ServerInterface getRmiConnection(String host, int port) throws NotBoundException, IOException {
        return ConnectionBuilder.buildRmiConnection(port, host, new ClientDispatcher(this), this);
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        //TODO
    }

    @Override
    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {
        //TODO
    }

    @Override
    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {
        //TODO
    }

    @Override
    public void onAssignedCommonGoal(String description, int n) {
        viewData.getCommonGoals()[n - 1] = description;
    }

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

    @Override
    public synchronized void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        viewData.getBookShelves().computeIfAbsent(nickname, k -> new TileSubject[6][5]);
        for (int i = 0; i < bookShelf.length; i++) {
            for (int j = 0; j < bookShelf[0].length; j++) {
                viewData.getBookShelves().get(nickname)[i][j] = bookShelf[i][j];
            }
        }
    }

    @Override
    public synchronized void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        viewData.getAvailableScores().put(numberOfCommonGoal - 1, score);
    }

    @Override
    public synchronized void onCurrentPlayerChangedListener(String nickname) {
        viewData.setCurrentPlayer(nickname);
    }

    @Override
    public synchronized void onException(Exception e) {
        try{
            viewData.setException(e.getMessage());
        }catch(IOException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public synchronized void onLastPlayerUpdated(String nicknameLastPlayer) {

    }

    @Override
    public synchronized void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        Triple<String, List<String>, String> triple = new Triple<>(nicknameSender, nicknameReceivers, text);
        viewData.addMessage(triple);
    }

    @Override
    public synchronized void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        List<Triple<String, List<String>, String>> messages = new ArrayList<>();
        for (int i = 0; i < senderNicknames.size(); i++) {
            messages.add(new Triple<>(senderNicknames.get(i), receiverNicknames.get(i), texts.get(i)));
        }
        viewData.setMessages(messages);
    }

    @Override
    public synchronized void onPlayerStateChanged(String nickname, PlayerState playerState) {
        viewData.getPlayersState().put(nickname, playerState.toString());
    }

    @Override
    public synchronized void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        viewData.getPlayersPoints().computeIfAbsent(nickName, k -> Arrays.asList(0, 0, 0, 0, 0));
        viewData.getPlayersPointsByNickname(nickName).set(0, scoreAdjacentGoal);
        viewData.getPlayersPointsByNickname(nickName).set(1, scoreCommonGoal1);
        viewData.getPlayersPointsByNickname(nickName).set(2, scoreCommonGoal2);
        viewData.getPlayersPointsByNickname(nickName).set(3, scoreEndGame);
        viewData.getPlayersPointsByNickname(nickName).set(4, scorePersonalGoal);
    }

    @Override
    public synchronized void onStateChanged(GameState gameState) {
        try{
            viewData.setGameState(gameState.toString());
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void nop() {

    }
    @Override
    public synchronized void onConnectionLost() {

        ui.onConnectionLost();
    }

    @Override
    public synchronized void joinGame(String nickname) {
        viewData.setThisPlayer(nickname);
        server.joinGame(nickname);
    }

    @Override
    public synchronized void createGame(String nickname, int numberOfPlayer) {
        viewData.setThisPlayer(nickname);
        server.createGame(nickname, numberOfPlayer);
    }

    @Override
    public synchronized void quitGame() {
        viewData = new ViewData(9, 5, 6);
        viewData.setUserInterface(ui);
        ui.setModel(viewData);
        server.quitGame();
    }

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

    @Override
    public synchronized void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        // modificare la view?
        server.dragTilesToBookShelf(chosenTiles, chosenColumn);
    }

    @Override
    public synchronized void onWinnerChanged(String nickname) {
        viewData.setWinnerPlayer(nickname);
    }

    @Override
    public synchronized void chosenSocket(int port, String host) throws IOException {
        ServerInterface serverInterface = this.getSocketConnection(host, port);
        this.setServer(serverInterface);
        this.port = port;
        this.host = host;
        this.choice = SOCKET;
    }

    @Override
    public synchronized void chosenRMI(int port, String host) throws NotBoundException, IOException {
        ServerInterface serverInterface = this.getRmiConnection(host, port);
        this.setServer(serverInterface);
        this.port = port;
        this.host = host;
        this.choice = RMI;
    }

    @Override
    public synchronized void reConnect() {
        while(true) {
            try{
                if(this.choice == SOCKET) {
                    chosenSocket(this.port, this.host);
                }
                else {
                    chosenRMI(this.port, this.host);
                }
                break;
            } catch(IOException | NotBoundException e) {
                continue;
            }

        }

    }

    @Override
    public synchronized void onPlayersListChanged(List<String> players) {
        viewData.setPlayers(players);
    }
}


