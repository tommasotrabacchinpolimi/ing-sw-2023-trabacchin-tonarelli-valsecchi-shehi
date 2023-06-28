package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.clientmessage.*;

/**
 * The `ClientDispatcher` class implements the `ClientDispatcherInterface` interface and is responsible for dispatching
 * messages received from the server to the corresponding client.
 * Each `dispatch` method receives a specific type of message and forwards it to the client by calling the corresponding
 * methods in the `ClientInterface`.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ClientDispatcher implements ClientDispatcherInterface {

    private final ClientInterface client;


    /**
     * Constructs a `ClientDispatcher` object with the specified client.
     *
     * @param client            the client interface
     */
    public ClientDispatcher(ClientInterface client) {
        this.client = client;
    }

    /**
     * Dispatches an `AchievedCommonGoalNetMessage` to the client.
     *
     * @param achievedCommonGoalNetMessage the achieved common goal message
     */
    @Override
    public void dispatch(AchievedCommonGoalNetMessage achievedCommonGoalNetMessage) {
        client.onAchievedCommonGoal(achievedCommonGoalNetMessage.getNicknamePlayer(), achievedCommonGoalNetMessage.getTiles(), achievedCommonGoalNetMessage.getNumberCommonGoal());
    }

    /**
     * Dispatches an `AchievedPersonalGoalNetMessage` to the client.
     *
     * @param achievedPersonalGoalNetMessage the achieved personal goal message
     */
    @Override
    public void dispatch(AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage) {
        client.onAchievedPersonalGoal(achievedPersonalGoalNetMessage.getNickname(), achievedPersonalGoalNetMessage.getTiles());
    }

    /**
     * Dispatches an `AdjacentTilesUpdatedNetMessage` to the client.
     *
     * @param adjacentTilesUpdatedNetMessage the adjacent tiles updated message
     */
    @Override
    public void dispatch(AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage) {
        client.onAdjacentTilesUpdated(adjacentTilesUpdatedNetMessage.getNickname(), adjacentTilesUpdatedNetMessage.getTiles());
    }

    /**
     * Dispatches an `AssignedCommonGoalNetMessage` to the client.
     *
     * @param assignedCommonGoalNetMessage the assigned common goal message
     */
    @Override
    public void dispatch(AssignedCommonGoalNetMessage assignedCommonGoalNetMessage) {
        client.onAssignedCommonGoal(assignedCommonGoalNetMessage.getDescription(), assignedCommonGoalNetMessage.getN(), assignedCommonGoalNetMessage.getId());
    }

    /**
     * Dispatches an `AssignedPersonalGoalNetMessage` to the client.
     *
     * @param assignedPersonalGoalNetMessage the assigned personal goal message
     */
    @Override
    public void dispatch(AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage) {
        client.onAssignedPersonalGoal(assignedPersonalGoalNetMessage.getNickname(), assignedPersonalGoalNetMessage.getGoalPattern(), assignedPersonalGoalNetMessage.getScoreMap());
    }

    /**
     * Dispatches a `BoardRefilledNetMessage` to the client.
     *
     * @param boardRefilledNetMessage the board refilled message
     */
    @Override
    public void dispatch(BoardRefilledNetMessage boardRefilledNetMessage) {
        client.onBoardRefilled();
    }

    /**
     * Dispatches a `BoardUpdatedNetMessage` to the client.
     *
     * @param boardUpdatedNetMessage the board updated message
     */
    @Override
    public void dispatch(BoardUpdatedNetMessage boardUpdatedNetMessage) {
        client.onBoardUpdated(boardUpdatedNetMessage.getTileSubjects());
    }

    /**
     * Dispatches a `BookShelfUpdatedNetMessage` to the client.
     *
     * @param bookShelfUpdatedNetMessage the bookshelf updated message
     */
    @Override
    public void dispatch(BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage) {
        client.onBookShelfUpdated(bookShelfUpdatedNetMessage.getNickname(), bookShelfUpdatedNetMessage.getBookShelf());
    }

    /**
     * Dispatches a `ChangedCommonGoalAvailableScoreNetMessage` to the client.
     *
     * @param changedCommonGoalAvailableScoreNetMessage the changed common goal available score message
     */
    @Override
    public void dispatch(ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage) {
        client.onChangedCommonGoalAvailableScore(changedCommonGoalAvailableScoreNetMessage.getScore(), changedCommonGoalAvailableScoreNetMessage.getNumberOfCommonGoal());
    }

    /**
     * Dispatches a `CurrentPlayerChangedListenerNetMessage` to the client.
     *
     * @param currentPlayerChangedListener the current player changed listener message
     */
    @Override
    public void dispatch(CurrentPlayerChangedListenerNetMessage currentPlayerChangedListener) {
        client.onCurrentPlayerChangedListener(currentPlayerChangedListener.getNickname());
    }

    /**
     * Dispatches an `ExceptionNetMessage` to the client.
     *
     * @param exceptionNetMessage the exception message
     */
    @Override
    public void dispatch(ExceptionNetMessage exceptionNetMessage) {
        client.onException(exceptionNetMessage.getPlayerCause(), exceptionNetMessage.getException());
    }

    /**
     * Dispatches a `LastPlayerUpdatedNetMessage` to the client.
     *
     * @param lastPlayerUpdatedNetMessage the last player updated message
     */
    @Override
    public void dispatch(LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage) {
        client.onLastPlayerUpdated(lastPlayerUpdatedNetMessage.getNicknameLastPlayer());
    }

    /**
     * Dispatches a `MessageSentNetMessage` to the client.
     *
     * @param messageSentNetMessage the message sent message
     */
    @Override
    public void dispatch(MessageSentNetMessage messageSentNetMessage) {
        client.onMessageSent(messageSentNetMessage.getNickname(), messageSentNetMessage.getNicknameReceivers(), messageSentNetMessage.getText());
    }

    /**
     * Dispatches a `MessagesSentNetMessage` to the client.
     *
     * @param messagesSentNetMessage the messages sent message
     */
    @Override
    public void dispatch(MessagesSentNetMessage messagesSentNetMessage) {
        client.onMessagesSentUpdate(messagesSentNetMessage.getSenderNicknames(), messagesSentNetMessage.getReceiverNicknames(), messagesSentNetMessage.getTexts());
    }

    /**
     * Dispatches a `PlayersListChangedNetMessage` to the client.
     *
     * @param playersListChangedNetMessage the players list changed message
     */
    @Override
    public void dispatch(PlayersListChangedNetMessage playersListChangedNetMessage) {
        client.onPlayersListChanged(playersListChangedNetMessage.getPlayers());
    }

    /**
     * Dispatches a `PlayerStateChangedNetMessage` to the client.
     *
     * @param playerStateChangedNetMessage the player state changed message
     */
    @Override
    public void dispatch(PlayerStateChangedNetMessage playerStateChangedNetMessage) {
        client.onPlayerStateChanged(playerStateChangedNetMessage.getNickname(), playerStateChangedNetMessage.getPlayerState());
    }

    /**
     * Dispatches a `PointsUpdatedNetMessage` to the client.
     *
     * @param pointsUpdatedNetMessage the points updated message
     */
    @Override
    public void dispatch(PointsUpdatedNetMessage pointsUpdatedNetMessage) {
        client.onPointsUpdated(pointsUpdatedNetMessage.getNickname(), pointsUpdatedNetMessage.getScoreAdjacentGoal(), pointsUpdatedNetMessage.getScoreCommonGoal1(), pointsUpdatedNetMessage.getScoreCommonGoal2(), pointsUpdatedNetMessage.getScoreEndGame(), pointsUpdatedNetMessage.getScorePersonalGoal());
    }

    /**
     * Dispatches a `StateChangedNetMessage` to the client.
     *
     * @param stateChangedNetMessage the state changed message
     */
    @Override
    public void dispatch(StateChangedNetMessage stateChangedNetMessage) {
        client.onStateChanged(stateChangedNetMessage.getGameState());
    }

    /**
     * Dispatches a `WinnerChangedNetMessage` to the client.
     *
     * @param winnerChangedNetMessage the winner changed message
     */
    @Override
    public void dispatch(WinnerChangedNetMessage winnerChangedNetMessage) {
        client.onWinnerChanged(winnerChangedNetMessage.getNickname());
    }

    /**
     * Dispatches a `NopNetMessage` to the client.
     *
     * @param nopNetMessage the NOP message
     */
    @Override
    public void dispatch(NopNetMessage nopNetMessage) {
        client.nop();
    }
}
