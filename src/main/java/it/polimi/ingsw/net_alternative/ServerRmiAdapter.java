package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ServerRmiAdapter implements ClientInterface {

    private final RmiClientInterface rmiClient;

    private final OnServerConnectionLostListener serverConnectionLostListener;




    public ServerRmiAdapter(RmiClientInterface rmiClient, OnServerConnectionLostListener serverConnectionLostListener) {
        this.rmiClient = rmiClient;
        this.serverConnectionLostListener = serverConnectionLostListener;
    }
    @Override
    public void nop() {
        try{
            rmiClient.nop();
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }

    }

    @Override
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        try{
            rmiClient.onAchievedCommonGoal(nicknamePlayer, tiles, numberCommonGoal);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {
        try{
            rmiClient.onAchievedPersonalGoal(nickname, tiles);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {
        try {
            rmiClient.onAdjacentTilesUpdated(nickname, tiles);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }

    }

    @Override
    public void onAssignedCommonGoal(String description, int n) {
        try {
            rmiClient.onAssignedCommonGoal(description, n);
        }catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }

    }

    @Override
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        try{
            rmiClient.onAssignedPersonalGoal(nickname, goalPattern, scoreMap);
        }catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onBoardRefilled() {
        try{
            rmiClient.onBoardRefilled();
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onBoardUpdated(TileSubject[][] tileSubjects) {
        try {
            rmiClient.onBoardUpdated(tileSubjects);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        try{
            rmiClient.onBookShelfUpdated(nickname, bookShelf);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        try {
            rmiClient.onChangedCommonGoalAvailableScore(score, numberOfCommonGoal);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onCurrentPlayerChangedListener(String nickname) {
        try{
            rmiClient.onCurrentPlayerChangedListener(nickname);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onException(Exception e) {
        try {
            rmiClient.onException(e);
        } catch(RemoteException e1) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onLastPlayerUpdated(String nicknameLastPlayer) {
        try {
            rmiClient.onLastPlayerUpdated(nicknameLastPlayer);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        try {
            rmiClient.onMessageSent(nicknameSender, nicknameReceivers, text);
        }catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        try {
            rmiClient.onMessagesSentUpdate(senderNicknames, receiverNicknames, texts);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onPlayerStateChanged(String nickname, PlayerState playerState) {
        try{
            rmiClient.onPlayerStateChanged(nickname, playerState);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onPlayersListChanged(List<String> players) {
        try {
            rmiClient.onPlayersListChanged(players);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        try {
            rmiClient.onPointsUpdated(nickName, scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onStateChanged(GameState gameState) {
        try {
            rmiClient.onStateChanged(gameState);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }

    @Override
    public void onWinnerChanged(String nickname) {
        try {
            rmiClient.onWinnerChanged(nickname);
        } catch(RemoteException e) {
            serverConnectionLostListener.onConnectionLost(this);
        }
    }
}
