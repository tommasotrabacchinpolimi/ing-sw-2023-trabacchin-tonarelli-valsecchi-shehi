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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RmiClientImpl extends UnicastRemoteObject implements RmiClientInterface{

    private final ClientDispatcherInterface clientDispatcherInterface;
    private final ExecutorService executorService;
    public RmiClientImpl(ClientDispatcherInterface clientDispatcherInterface) throws RemoteException{
        super();
        this.clientDispatcherInterface = clientDispatcherInterface;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void nop() throws RemoteException{
        NopNetMessage nopNetMessage = new NopNetMessage();
        executorService.submit(()->nopNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) throws RemoteException{
        AchievedCommonGoalNetMessage achievedCommonGoalNetMessage = new AchievedCommonGoalNetMessage(nicknamePlayer, tiles, numberCommonGoal);
        executorService.submit(()->achievedCommonGoalNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) throws RemoteException{
        AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage = new AchievedPersonalGoalNetMessage(nickname, tiles);
        executorService.submit(()->clientDispatcherInterface.dispatch(achievedPersonalGoalNetMessage));
    }

    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) throws RemoteException{
        AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage = new AdjacentTilesUpdatedNetMessage(nickname, tiles);
        executorService.submit(()->adjacentTilesUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onAssignedCommonGoal(String description, int n) throws RemoteException{
        AssignedCommonGoalNetMessage assignedCommonGoalNetMessage = new AssignedCommonGoalNetMessage(description, n);
        executorService.submit(()->assignedCommonGoalNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) throws RemoteException{
        AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage = new AssignedPersonalGoalNetMessage(nickname, goalPattern, scoreMap);
        executorService.submit(()->assignedPersonalGoalNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onBoardRefilled() throws RemoteException{
        BoardRefilledNetMessage boardRefilledNetMessage = new BoardRefilledNetMessage();
        executorService.submit(()->boardRefilledNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onBoardUpdated(TileSubject[][] tileSubjects) throws RemoteException{
        BoardUpdatedNetMessage boardUpdatedNetMessage = new BoardUpdatedNetMessage(tileSubjects);
        executorService.submit(()->boardUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) throws RemoteException{
        BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage = new BookShelfUpdatedNetMessage(nickname, bookShelf);
        executorService.submit(()->bookShelfUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) throws RemoteException{
        ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage = new ChangedCommonGoalAvailableScoreNetMessage(score, numberOfCommonGoal);
        executorService.submit(()->changedCommonGoalAvailableScoreNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onCurrentPlayerChangedListener(String nickname) throws RemoteException{
        CurrentPlayerChangedListenerNetMessage changedListenerNetMessage = new CurrentPlayerChangedListenerNetMessage(nickname);
        executorService.submit(()->changedListenerNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onException(Exception e) throws RemoteException{
        ExceptionNetMessage exceptionNetMessage = new ExceptionNetMessage(e);
        executorService.submit(()->exceptionNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onLastPlayerUpdated(String nicknameLastPlayer) throws RemoteException{
        LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage = new LastPlayerUpdatedNetMessage(nicknameLastPlayer);
        executorService.submit(()->lastPlayerUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) throws RemoteException{
        MessageSentNetMessage messageSentNetMessage = new MessageSentNetMessage(nicknameSender, nicknameReceivers, text);
        executorService.submit(()->messageSentNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) throws RemoteException{
        MessagesSentNetMessage messagesSentNetMessage = new MessagesSentNetMessage(senderNicknames, receiverNicknames, texts);
        executorService.submit(()->messagesSentNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onPlayerStateChanged(String nickname, PlayerState playerState) throws RemoteException{
        PlayerStateChangedNetMessage playerStateChangedNetMessage = new PlayerStateChangedNetMessage(nickname, playerState);
        executorService.submit(()->playerStateChangedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onPlayersListChanged(List<String> players) throws RemoteException{
        PlayersListChangedNetMessage playersListChangedNetMessage = new PlayersListChangedNetMessage(players);
        executorService.submit(()->playersListChangedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) throws RemoteException{
        PointsUpdatedNetMessage pointsUpdatedNetMessage = new PointsUpdatedNetMessage(nickName, scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        executorService.submit(()->pointsUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onStateChanged(GameState gameState) throws RemoteException{
        StateChangedNetMessage stateChangedNetMessage = new StateChangedNetMessage(gameState);
        executorService.submit(()->stateChangedNetMessage.dispatch(clientDispatcherInterface));
    }

    public void onWinnerChanged(String nickname) throws RemoteException{
        WinnerChangedNetMessage winnerChangedNetMessage = new WinnerChangedNetMessage(nickname);
        executorService.submit(()->winnerChangedNetMessage.dispatch(clientDispatcherInterface));
    }
}
