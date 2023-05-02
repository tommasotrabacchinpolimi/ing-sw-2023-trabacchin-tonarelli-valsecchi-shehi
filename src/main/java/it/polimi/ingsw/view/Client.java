package it.polimi.ingsw.view;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net.ConnectionBuilder;
import it.polimi.ingsw.net.RmiConnectionManager;
import it.polimi.ingsw.net.SocketConnectionManager;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.tui.TUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class Client implements ClientInterface, LogicInterface {
    private final UI ui;
    private ServerInterface server;

    public Client(UI ui) {
        this.ui = ui;
    }
    public static void main(String[] args) throws IOException, NotBoundException, ClassNotFoundException {
        String protocolChoice, UIChoice;
        Client client = null;
        UI ui = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ServerInterface server = null;
        System.out.println("Choose one of the following protocols:");
        System.out.println("1) Socket");
        System.out.println("2) Remote Methode Invocation");
        protocolChoice = bufferedReader.readLine();
        System.out.println("Now choose your desired UI:");
        System.out.println("1) TUI");
        System.out.println("2) GUI");
        UIChoice = bufferedReader.readLine();
        if(UIChoice.equals("1")) {
            ui = new TUI();
        }
        client = new Client(ui);
        ui.setLogicController(client);
        if(protocolChoice.equals("1")) {
            server = getSocketConnection(client);
        }
        else if(protocolChoice.equals("2")) {
            server = getRmiConnection(client);
        }
        client.setServer(server);
        ui.launch();
    }

    private static ServerInterface getSocketConnection(Client client) throws IOException {
        TypeToken<ServerInterface> typeToken = new TypeToken<>(){};
        SocketConnectionManager<ClientInterface, ServerInterface>  socketConnectionManager = ConnectionBuilder.buildSocketConnection("localhost",1234,client,typeToken);
        return socketConnectionManager.getRemoteTarget();
    }

    private static ServerInterface getRmiConnection(Client client) throws NotBoundException, IOException, ClassNotFoundException {
        TypeToken<ClientInterface> typeToken1 = new TypeToken<>() {};
        TypeToken<ServerInterface> typeToken2 = new TypeToken<>() {};
        RmiConnectionManager<ClientInterface, ServerInterface> rmiConnectionManager = ConnectionBuilder.buildRMIConnection("localhost", 2148, typeToken2, typeToken1, client);
        return rmiConnectionManager.getRemoteTarget();
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {

    }

    @Override
    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {

    }

    @Override
    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {

    }

    @Override
    public void onAssignedCommonGoal(String description, int n) {

    }

    @Override
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {

    }

    @Override
    public void onBoardRefilled() {

    }

    @Override
    public void onBoardUpdated(TileSubject[][] tileSubjects) {

    }

    @Override
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {

    }

    @Override
    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {

    }

    @Override
    public void onCurrentPlayerChangedListener(String nickname) {

    }

    @Override
    public void onException(Exception e) {

    }

    @Override
    public void onLastPlayerUpdated(String nicknameLastPlayer) {

    }

    @Override
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {

    }

    @Override
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {

    }

    @Override
    public void onPlayerStateChanged(String nickname, PlayerState playerState) {

    }

    @Override
    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {

    }

    @Override
    public void onStateChanged(GameState gameState) {

    }

    @Override
    public void nop() throws RemoteException {

    }

    @Override
    public void joinGame(String nickname) {

    }

    @Override
    public void createGame(String nickname, int numberOfPlayer) {

    }

    @Override
    public void quitGame() {

    }

    @Override
    public void sentMessage(String text, String[] receiversNickname) {

    }

    @Override
    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {

    }
}
