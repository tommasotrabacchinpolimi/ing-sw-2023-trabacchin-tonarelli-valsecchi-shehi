package it.polimi.ingsw.net_alternative;


import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.clientmessage.*;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class RmiClientImpl extends UnicastRemoteObject implements RmiClientInterface{

    private final ClientDispatcherInterface clientDispatcherInterface;

    public RmiClientImpl(ClientDispatcherInterface clientDispatcherInterface) throws RemoteException{
        super();
        this.clientDispatcherInterface = clientDispatcherInterface;
    }

    public void nop() throws RemoteException{
        NopNetMessage nopNetMessage = new NopNetMessage();
        nopNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) throws RemoteException{
        AchievedCommonGoalNetMessage achievedCommonGoalNetMessage = new AchievedCommonGoalNetMessage(nicknamePlayer, tiles, numberCommonGoal);
        achievedCommonGoalNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) throws RemoteException{
        AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage = new AchievedPersonalGoalNetMessage(nickname, tiles);
        clientDispatcherInterface.dispatch(achievedPersonalGoalNetMessage);
    }

    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) throws RemoteException{
        AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage = new AdjacentTilesUpdatedNetMessage(nickname, tiles);
        adjacentTilesUpdatedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onAssignedCommonGoal(String description, int n) throws RemoteException{
        AssignedCommonGoalNetMessage assignedCommonGoalNetMessage = new AssignedCommonGoalNetMessage(description, n);
        assignedCommonGoalNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) throws RemoteException{
        AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage = new AssignedPersonalGoalNetMessage(nickname, goalPattern, scoreMap);
        assignedPersonalGoalNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onBoardRefilled() throws RemoteException{
        BoardRefilledNetMessage boardRefilledNetMessage = new BoardRefilledNetMessage();
        boardRefilledNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onBoardUpdated(TileSubject[][] tileSubjects) throws RemoteException{
        BoardUpdatedNetMessage boardUpdatedNetMessage = new BoardUpdatedNetMessage(tileSubjects);
        boardUpdatedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) throws RemoteException{
        BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage = new BookShelfUpdatedNetMessage(nickname, bookShelf);
        bookShelfUpdatedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) throws RemoteException{
        ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage = new ChangedCommonGoalAvailableScoreNetMessage(score, numberOfCommonGoal);
        changedCommonGoalAvailableScoreNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onCurrentPlayerChangedListener(String nickname) throws RemoteException{
        CurrentPlayerChangedListenerNetMessage changedListenerNetMessage = new CurrentPlayerChangedListenerNetMessage(nickname);
        changedListenerNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onException(Exception e) throws RemoteException{
        ExceptionNetMessage exceptionNetMessage = new ExceptionNetMessage(e);
        exceptionNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onLastPlayerUpdated(String nicknameLastPlayer) throws RemoteException{
        LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage = new LastPlayerUpdatedNetMessage(nicknameLastPlayer);
        lastPlayerUpdatedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) throws RemoteException{
        MessageSentNetMessage messageSentNetMessage = new MessageSentNetMessage(nicknameSender, nicknameReceivers, text);
        messageSentNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) throws RemoteException{
        MessagesSentNetMessage messagesSentNetMessage = new MessagesSentNetMessage(senderNicknames, receiverNicknames, texts);
    }

    public void onPlayerStateChanged(String nickname, PlayerState playerState) throws RemoteException{
        PlayerStateChangedNetMessage playerStateChangedNetMessage = new PlayerStateChangedNetMessage(nickname, playerState);
        playerStateChangedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onPlayersListChanged(List<String> players) throws RemoteException{
        PlayersListChangedNetMessage playersListChangedNetMessage = new PlayersListChangedNetMessage(players);
        playersListChangedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) throws RemoteException{
        PointsUpdatedNetMessage pointsUpdatedNetMessage = new PointsUpdatedNetMessage(nickName, scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        pointsUpdatedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onStateChanged(GameState gameState) throws RemoteException{
        StateChangedNetMessage stateChangedNetMessage = new StateChangedNetMessage(gameState);
        stateChangedNetMessage.dispatch(clientDispatcherInterface);
    }

    public void onWinnerChanged(String nickname) throws RemoteException{
        WinnerChangedNetMessage winnerChangedNetMessage = new WinnerChangedNetMessage(nickname);
        winnerChangedNetMessage.dispatch(clientDispatcherInterface);
    }
}
