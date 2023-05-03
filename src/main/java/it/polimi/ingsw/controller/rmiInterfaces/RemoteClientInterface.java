package it.polimi.ingsw.controller.rmiInterfaces;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.utils.Coordinate;

public interface RemoteClientInterface extends RemoteInterface {
    @Override
    public void nop() throws RemoteException;

    public void onAchievedCommonGoal(java.lang.String p0, java.util.List<Coordinate> p1, int p2) throws RemoteException;

    public void onBoardRefilled() throws RemoteException;

    public void onBoardUpdated(it.polimi.ingsw.model.TileSubject[][] p0) throws RemoteException;

    public void onBookShelfUpdated(java.lang.String p0, it.polimi.ingsw.model.TileSubject[][] p1) throws RemoteException;

    public void onCurrentPlayerChangedListener(java.lang.String p0) throws RemoteException;

    public void onLastPlayerUpdated(java.lang.String p0) throws RemoteException;

    public void onMessageSent(java.lang.String p0, java.util.List<String> p1, java.lang.String p2) throws RemoteException;

    public void onMessagesSentUpdate(java.util.List<String> p0, java.util.List<List<String>> p1, java.util.List<String> p2) throws RemoteException;

    public void onPlayerStateChanged(java.lang.String p0, it.polimi.ingsw.model.PlayerState p1) throws RemoteException;

    public void onPointsUpdated(java.lang.String p0, int p1, int p2, int p3, int p4, int p5) throws RemoteException;

    public void onStateChanged(it.polimi.ingsw.model.GameState p0) throws RemoteException;

    public void onAssignedPersonalGoal(java.lang.String p0, java.util.List p1, java.util.Map p2) throws RemoteException;

    public void onAssignedCommonGoal(java.lang.String p0, int p1) throws RemoteException;

    public void onException(java.lang.Exception p0) throws RemoteException;

    public void onAchievedPersonalGoal(java.lang.String p0, java.util.List p1) throws RemoteException;

    public void onAdjacentTilesUpdated(java.lang.String p0, java.util.List p1) throws RemoteException;

    public void onChangedCommonGoalAvailableScore(int p0, int p1) throws RemoteException;

    public void onWinnerChanged(String nickname) throws RemoteException;
}
