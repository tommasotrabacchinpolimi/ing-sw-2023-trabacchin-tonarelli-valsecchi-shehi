package it.polimi.ingsw.view;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.ConnectionBuilder;
import it.polimi.ingsw.net.RmiConnectionManager;
import it.polimi.ingsw.net.SocketConnectionManager;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;
//import it.polimi.ingsw.view.gui.MyShelfieApplicationAdapter;
import it.polimi.ingsw.view.tui.TUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Client implements ClientInterface, LogicInterface {
    private final UI ui;
    private ServerInterface server;

    private ViewData viewData;

    public Client(UI ui, ViewData model) {
        this.ui = ui;
        viewData = model;
    }

    public static void main(String[] args) throws IOException, NotBoundException, ClassNotFoundException {
        String UIChoice;
        Client client = null;
        UI ui = null;
        ViewData viewData = new ViewData(9, 5, 6);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Now choose your desired UI:");
        System.out.println("1) TUI");
        System.out.println("2) GUI");
        UIChoice = bufferedReader.readLine();

        if (UIChoice.equals("1")) {
            ui = new TUI();
        } else if (UIChoice.equals("2")) {
            //ui = new MyShelfieApplicationAdapter();
        }

        ui.setModel(viewData);

        client = new Client(ui, viewData);
        ui.setLogicController(client);
        viewData.setUserInterface(ui);
        ui.launchUI();
    }

    private ServerInterface getSocketConnection(String host, int port) throws IOException {
        TypeToken<ServerInterface> typeToken = new TypeToken<>() {
        };
        SocketConnectionManager<ClientInterface, ServerInterface> socketConnectionManager = ConnectionBuilder.buildSocketConnection(host, port, this, typeToken);
        return socketConnectionManager.getRemoteTarget();
    }

    private ServerInterface getRmiConnection(String host, int port) throws NotBoundException, IOException, ClassNotFoundException {
        TypeToken<ClientInterface> typeToken1 = new TypeToken<>() {
        };
        TypeToken<ServerInterface> typeToken2 = new TypeToken<>() {
        };
        RmiConnectionManager<ClientInterface, ServerInterface> rmiConnectionManager = ConnectionBuilder.buildRMIConnection(host, port, typeToken2, typeToken1, this);//port = 2148
        return rmiConnectionManager.getRemoteTarget();
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
    public void onBoardUpdated(TileSubject[][] tileSubjects) {
        for (int i = 0; i < tileSubjects.length; i++) {
            for (int j = 0; j < tileSubjects[0].length; j++) {
                viewData.getBoard()[i][j] = tileSubjects[i][j];
            }
        }
    }

    @Override
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        viewData.getBookShelves().computeIfAbsent(nickname, k -> new TileSubject[6][5]);
        for (int i = 0; i < bookShelf.length; i++) {
            for (int j = 0; j < bookShelf[0].length; j++) {
                viewData.getBookShelves().get(nickname)[i][j] = bookShelf[i][j];
            }
        }
    }

    @Override
    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        viewData.getAvailableScores().put(numberOfCommonGoal - 1, score);
    }

    @Override
    public void onCurrentPlayerChangedListener(String nickname) {
        viewData.setCurrentPlayer(nickname);
    }

    @Override
    public void onException(Exception e) {
        viewData.setException(e.getMessage());
    }

    @Override
    public void onLastPlayerUpdated(String nicknameLastPlayer) {

    }

    @Override
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        Triple<String, List<String>, String> triple = new Triple<>(nicknameSender, nicknameReceivers, text);
        viewData.addMessage(triple);
    }

    @Override
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        List<Triple<String, List<String>, String>> messages = new ArrayList<>();
        for (int i = 0; i < senderNicknames.size(); i++) {
            messages.add(new Triple<>(senderNicknames.get(i), receiverNicknames.get(i), texts.get(i)));
        }
        viewData.setMessages(messages);
    }

    @Override
    public void onPlayerStateChanged(String nickname, PlayerState playerState) {
        viewData.getPlayersState().put(nickname, playerState.toString());
    }

    @Override
    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        viewData.getPlayersPoints().computeIfAbsent(nickName, k -> Arrays.asList(0, 0, 0, 0, 0));
        viewData.getPlayersPointsByNickname(nickName).set(0, scoreAdjacentGoal);
        viewData.getPlayersPointsByNickname(nickName).set(1, scoreCommonGoal1);
        viewData.getPlayersPointsByNickname(nickName).set(2, scoreCommonGoal2);
        viewData.getPlayersPointsByNickname(nickName).set(3, scoreEndGame);
        viewData.getPlayersPointsByNickname(nickName).set(4, scorePersonalGoal);
    }

    @Override
    public void onStateChanged(GameState gameState) {
        viewData.setGameState(gameState.toString());
    }

    @Override
    public void nop() throws RemoteException {

    }

    @Override
    public void joinGame(String nickname) {
        viewData.setThisPlayer(nickname);
        server.joinGame(nickname);
    }

    @Override
    public void createGame(String nickname, int numberOfPlayer) {
        viewData.setThisPlayer(nickname);
        server.createGame(nickname, numberOfPlayer);
    }

    @Override
    public void quitGame() {
        viewData = new ViewData(9, 5, 6);
        viewData.setUserInterface(ui);
        ui.setModel(viewData);
        server.quitGame();
    }

    @Override
    public void sentMessage(String text, String[] receiversNickname) {
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
    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        // modificare la view?
        server.dragTilesToBookShelf(chosenTiles, chosenColumn);
    }

    @Override
    public void onWinnerChanged(String nickname) {
        viewData.setWinnerPlayer(nickname);
    }

    @Override
    public void chosenSocket(int port, String host) throws IOException {
        ServerInterface serverInterface = this.getSocketConnection(host, port);
        this.setServer(serverInterface);
    }

    @Override
    public void chosenRMI(int port, String host) throws NotBoundException, IOException, ClassNotFoundException {
        ServerInterface serverInterface = this.getRmiConnection(host, port);
        this.setServer(serverInterface);
    }

    @Override
    public void onPlayersListChanged(List<String> players) {
        viewData.setPlayers(players);
    }
}


