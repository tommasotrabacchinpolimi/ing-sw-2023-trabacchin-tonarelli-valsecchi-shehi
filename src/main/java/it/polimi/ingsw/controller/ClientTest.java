package it.polimi.ingsw.controller;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net.ConnectionBuilder;
import it.polimi.ingsw.net.SocketConnectionManager;

import javax.swing.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientTest implements ClientInterface{
    GUIInterface guiInterface;
    ServerInterface server;
    public ClientTest(GUIInterface guiInterface) {
        this.guiInterface = guiInterface;
    }
    public static void main(String[] args) throws IOException {
        GUIInterface guiInterface = new GUIInterface();
        ClientTest clientTest = new ClientTest(guiInterface);
        ExecutorService executorService = Executors.newCachedThreadPool();
        TypeToken<ServerInterface> typeToken = new TypeToken<>(){};
        SocketConnectionManager<ClientInterface, ServerInterface>  socketConnectionManager = ConnectionBuilder.buildSocketConnection("localhost",1234,clientTest,typeToken,executorService);
        ServerInterface server = socketConnectionManager.getRemoteTarget();
        clientTest.server = server;
        clientTest.start();
    }

    public void start() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String input = bufferedReader.readLine();
            if(input.equals("dragTilesToBookShelf")) {
                System.out.println("insert a column number: ");
                int chosenColumn = Integer.parseInt(bufferedReader.readLine());
                System.out.println("insert a sequence of tiles to drag, terminate with a .");
                List<Integer> chosenTiles = new ArrayList<>();
                while(true) {
                    String c = bufferedReader.readLine();
                    if(c.equals(".")) {
                        break;
                    }
                    int chosenTile = Integer.parseInt(c);
                    chosenTiles.add(chosenTile);
                }
                server.dragTilesToBookShelf(chosenTiles.stream().mapToInt(i->i).toArray(),chosenColumn);
            }
            else if(input.equals("createGame")) {
                System.out.println("write your nickname:");
                String nickName = bufferedReader.readLine();
                System.out.println("now write the number of player in the game");
                int num = Integer.parseInt(bufferedReader.readLine());
                server.createGame(nickName, num);
            }
            else if(input.equals("joinGame")) {
                System.out.println("write your nickname:");
                String nickName = bufferedReader.readLine();
                server.joinGame(nickName);
            }
            else if(input.equals("quitGame")) {
                server.quitGame();
            }
            System.out.println();
        }

    }

    @Override
    public void onAchievedCommonGoal(String nicknamePlayer, List<EntryPatternGoal> tiles, int numberCommonGoal) {

    }

    @Override
    public void onBoardRefilled() {

    }

    @Override
    public void onBoardUpdated(TileSubject[] tileSubjects) {

    }

    @Override
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {

    }

    @Override
    public void onCurrentPlayerChangedListener(String nickname) {

    }

    @Override
    public void onLastPlayerUpdated(String nicknameLastPlayer) {

    }

    @Override
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {

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
    public void onAssignedCommonGoal(String description, int n) {

    }

    @Override
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {

    }

    @Override
    public void onException(Exception e) {

    }
}


class GUIInterface {
    JTextArea jTextArea;
    public GUIInterface() {
        JFrame jFrame = new JFrame();
        jTextArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jFrame.add(jScrollPane);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jTextArea.setEditable(false);
    }

    public void write(String text) {
        jTextArea.append(text);
    }
}