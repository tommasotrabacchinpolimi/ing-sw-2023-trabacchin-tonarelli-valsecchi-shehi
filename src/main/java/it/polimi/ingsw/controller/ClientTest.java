package it.polimi.ingsw.controller;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net.*;

import javax.swing.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientTest implements ClientInterface {
    final GUIInterface guiInterface;
    ServerInterface server;
    public ClientTest(GUIInterface guiInterface) {
        this.guiInterface = guiInterface;
    }
    public static void main(String[] args) throws IOException, NotBoundException, ClassNotFoundException {
        String c;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("write rmi or socket");
        c = bufferedReader.readLine();
        if(c.equals("rmi")) {
            rmiTest();
        }
        else if(c.equals("socket")) {
            socketTest();
        }
    }

    public static void socketTest() throws IOException {
        GUIInterface guiInterface = new GUIInterface();
        ClientTest clientTest = new ClientTest(guiInterface);
        TypeToken<ServerInterface> typeToken = new TypeToken<>(){};
        SocketConnectionManager<ClientInterface, ServerInterface>  socketConnectionManager = ConnectionBuilder.buildSocketConnection("localhost",1234,clientTest,typeToken);
        ServerInterface server = socketConnectionManager.getRemoteTarget();
        clientTest.server = server;
        clientTest.start();
    }

    public static void rmiTest() throws IOException, NotBoundException, ClassNotFoundException {
        GUIInterface guiInterface = new GUIInterface();
        ClientTest clientTest = new ClientTest(guiInterface);
        TypeToken<ClientInterface> typeToken1 = new TypeToken<>() {};
        TypeToken<ServerInterface> typeToken2 = new TypeToken<>() {};
        RmiConnectionManager<ClientInterface, ServerInterface> rmiConnectionManager = ConnectionBuilder.buildRmiConnection("localhost", 2148, typeToken2, typeToken1, clientTest);
        ServerInterface server = rmiConnectionManager.getRemoteTarget();
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
            else if(input.equals("sendMessage")) {
                System.out.println("write your message");
                String mes = bufferedReader.readLine();
                List<String> nickNames = new LinkedList<>();
                System.out.println("write the names of the recipients, terminate with .");
                while(true) {
                    String c = bufferedReader.readLine();
                    if(c.equals(".")) {
                        break;
                    }
                    else {
                        nickNames.add(c);
                    }
                    server.sentMessage(mes, nickNames.toArray(String[]::new));
                }
            }
            System.out.println();
        }

    }

    @Override
    public void onAchievedCommonGoal(String nicknamePlayer, List<EntryPatternGoal> tiles, int numberCommonGoal) {
        guiInterface.write("player " + nicknamePlayer + "has achieved common goal number " + numberCommonGoal + "with the following tiles : " + Arrays.toString(tiles.toArray(EntryPatternGoal[]::new))+ "\n");
    }

    @Override
    public void onBoardRefilled() {
        guiInterface.write("Board refilled\n");
    }

    @Override
    public void onBoardUpdated(TileSubject[] tileSubjects) {
        guiInterface.write("Board updated : " + Arrays.toString(tileSubjects) + "\n");
    }

    @Override
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        synchronized (guiInterface) {
            guiInterface.write(nickname + "'s bookshelf has been updated : \n" );
            for (int i = 0; i < 6; i++){
                guiInterface.write(i+ "\t");
                for (int j = 0; j < 5; j++){
                    if (bookShelf[i][j]==null) guiInterface.write("----\t");
                    else {
                        guiInterface.write( bookShelf[i][j]  + "\t");
                    }
                }
                guiInterface.write("\n");
            }
        }

    }

    @Override
    public void onCurrentPlayerChangedListener(String nickname) {
        guiInterface.write("current player is now " + nickname + "\n");
    }

    @Override
    public void onLastPlayerUpdated(String nicknameLastPlayer) {
        guiInterface.write("last player has been updated " + nicknameLastPlayer + "\n");
    }

    @Override
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        synchronized(guiInterface) {
            guiInterface.write(nicknameSender + " has sent a message to " + Arrays.toString(nicknameReceivers.toArray(String[]::new)) + "\n");
            guiInterface.write("the message is : " + text + "\n");
        }

    }

    @Override
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        synchronized(guiInterface) {
            guiInterface.write("messages update\n");
            for(int i = 0;i < senderNicknames.size();i++) {
                onMessageSent(senderNicknames.get(i), receiverNicknames.get(i), texts.get(i));
            }
        }
    }

    @Override
    public void onPlayerStateChanged(String nickname, PlayerState playerState) {
        guiInterface.write(nickname + " has changed state to " + playerState.toString() + "\n");
    }

    @Override
    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        guiInterface.write(nickName + " has now " + scoreAdjacentGoal + " for Adjacent Goal, " + scoreCommonGoal1 + " for Common Goal 1, " + scoreCommonGoal2 + " for Common Goal 2, " + scoreEndGame + " for End Game Score, " + scorePersonalGoal + " for Personal Goal\n");
    }

    @Override
    public void onStateChanged(GameState gameState) {
        guiInterface.write("The game state has changed to " + gameState + "\n");
    }

    @Override
    public void nop() throws RemoteException {

    }

    @Override
    public void onAssignedCommonGoal(String description, int n) {
        guiInterface.write("Common Goal number " + n + "is : " + description+"\n");
    }

    @Override
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        guiInterface.write(nickname + " has been assigned the pattern goal with following pattern : " + Arrays.toString(goalPattern.toArray(EntryPatternGoal[]::new)) + "\n");
    }

    @Override
    public void onException(Exception e) {
        guiInterface.write("the server has rose the following exception : " + e.getMessage() + "\n");
    }

    @Override
    public void onAchievedPersonalGoal(String nickname, List<EntryPatternGoal> tiles) {
        guiInterface.write(nickname + " has improved over his personal goal : " + Arrays.toString(tiles.toArray(EntryPatternGoal[]::new)) + "\n");
    }

    @Override
    public void onAdjacentTilesUpdated(String nickname, List<EntryPatternGoal> tiles) {
        guiInterface.write(nickname + " has the following adjacent tiles : " + Arrays.toString(tiles.toArray(EntryPatternGoal[]::new)) + "\n");
    }

    @Override
    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        guiInterface.write("common goal available score changed not implemented yet\n");
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

    public synchronized void write(String text) {
        jTextArea.append(text);
    }
}