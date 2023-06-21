package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.clientmessage.*;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RmiClientInterface extends Remote {
    public void nop() throws RemoteException;

    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) throws RemoteException;

    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) throws RemoteException;

    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) throws RemoteException;

    public void onAssignedCommonGoal(String description, int n) throws RemoteException;

    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) throws RemoteException;
    public void onBoardRefilled() throws RemoteException;

    public void onBoardUpdated(TileSubject[][] tileSubjects) throws RemoteException;

    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) throws RemoteException;

    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) throws RemoteException;

    public void onCurrentPlayerChangedListener(String nickname) throws RemoteException;

    public void onException(Exception e) throws RemoteException;
    public void onLastPlayerUpdated(String nicknameLastPlayer) throws RemoteException;

    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) throws RemoteException;

    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) throws RemoteException;

    public void onPlayerStateChanged(String nickname, PlayerState playerState) throws RemoteException;

    public void onPlayersListChanged(List<String> players) throws RemoteException;

    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) throws RemoteException;

    public void onStateChanged(GameState gameState) throws RemoteException;

    public void onWinnerChanged(String nickname) throws RemoteException;
}
