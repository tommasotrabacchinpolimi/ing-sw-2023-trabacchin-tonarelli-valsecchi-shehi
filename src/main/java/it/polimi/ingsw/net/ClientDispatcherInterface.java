package it.polimi.ingsw.net;

import it.polimi.ingsw.net.clientmessage.*;



/**
 * The ClientDispatcherInterface interface defines the methods for dispatching server messages to the client.
 * These methods are used to handle different types of messages received from the server.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface ClientDispatcherInterface {

    /**
     * Dispatches an AchievedCommonGoalNetMessage to the client.
     *
     * @param achievedCommonGoalNetMessage The AchievedCommonGoalNetMessage to dispatch.
     */
    void dispatch(AchievedCommonGoalNetMessage achievedCommonGoalNetMessage);

    /**
     * Dispatches an AchievedPersonalGoalNetMessage to the client.
     *
     * @param achievedPersonalGoalNetMessage The AchievedPersonalGoalNetMessage to dispatch.
     */
    void dispatch(AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage);

    /**
     * Dispatches an AdjacentTilesUpdatedNetMessage to the client.
     *
     * @param adjacentTilesUpdatedNetMessage The AdjacentTilesUpdatedNetMessage to dispatch.
     */
    void dispatch(AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage);

    /**
     * Dispatches an AssignedCommonGoalNetMessage to the client.
     *
     * @param assignedCommonGoalNetMessage The AssignedCommonGoalNetMessage to dispatch.
     */
    void dispatch(AssignedCommonGoalNetMessage assignedCommonGoalNetMessage);

    /**
     * Dispatches an AssignedPersonalGoalNetMessage to the client.
     *
     * @param assignedPersonalGoalNetMessage The AssignedPersonalGoalNetMessage to dispatch.
     */
    void dispatch(AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage);

    /**
     * Dispatches a BoardRefilledNetMessage to the client.
     *
     * @param boardRefilledNetMessage The BoardRefilledNetMessage to dispatch.
     */
    void dispatch(BoardRefilledNetMessage boardRefilledNetMessage);

    /**
     * Dispatches a BoardUpdatedNetMessage to the client.
     *
     * @param boardUpdatedNetMessage The BoardUpdatedNetMessage to dispatch.
     */
    void dispatch(BoardUpdatedNetMessage boardUpdatedNetMessage);

    /**
     * Dispatches a BookShelfUpdatedNetMessage to the client.
     *
     * @param bookShelfUpdatedNetMessage The BookShelfUpdatedNetMessage to dispatch.
     */
    void dispatch(BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage);

    /**
     * Dispatches a ChangedCommonGoalAvailableScoreNetMessage to the client.
     *
     * @param changedCommonGoalAvailableScoreNetMessage The ChangedCommonGoalAvailableScoreNetMessage to dispatch.
     */
    void dispatch(ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage);

    /**
     * Dispatches a CurrentPlayerChangedListenerNetMessage to the client.
     *
     * @param currentPlayerChangedListener The CurrentPlayerChangedListenerNetMessage to dispatch.
     */
    void dispatch(CurrentPlayerChangedListenerNetMessage currentPlayerChangedListener);

    /**
     * Dispatches an ExceptionNetMessage to the client.
     *
     * @param exceptionNetMessage The ExceptionNetMessage to dispatch.
     */
    void dispatch(ExceptionNetMessage exceptionNetMessage);

    /**
     * Dispatches a LastPlayerUpdatedNetMessage to the client.
     *
     * @param lastPlayerUpdatedNetMessage The LastPlayerUpdatedNetMessage to dispatch.
     */
    void dispatch(LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage);

    /**
     * Dispatches a MessageSentNetMessage to the client.
     *
     * @param messageSentNetMessage The MessageSentNetMessage to dispatch.
     */
    void dispatch(MessageSentNetMessage messageSentNetMessage);

    /**
     * Dispatches a MessagesSentNetMessage to the client.
     *
     * @param messagesSentNetMessage The MessagesSentNetMessage to dispatch.
     */
    void dispatch(MessagesSentNetMessage messagesSentNetMessage);

    /**
     * Dispatches a PlayersListChangedNetMessage to the client.
     *
     * @param playersListChangedNetMessage The PlayersListChangedNetMessage to dispatch.
     */
    void dispatch(PlayersListChangedNetMessage playersListChangedNetMessage);

    /**
     * Dispatches a PlayerStateChangedNetMessage to the client.
     *
     * @param playerStateChangedNetMessage The PlayerStateChangedNetMessage to dispatch.
     */
    void dispatch(PlayerStateChangedNetMessage playerStateChangedNetMessage);

    /**
     * Dispatches a PointsUpdatedNetMessage to the client.
     *
     * @param pointsUpdatedNetMessage The PointsUpdatedNetMessage to dispatch.
     */
    void dispatch(PointsUpdatedNetMessage pointsUpdatedNetMessage);

    /**
     * Dispatches a StateChangedNetMessage to the client.
     *
     * @param stateChangedNetMessage The StateChangedNetMessage to dispatch.
     */
    void dispatch(StateChangedNetMessage stateChangedNetMessage);

    /**
     * Dispatches a WinnerChangedNetMessage to the client.
     *
     * @param winnerChangedNetMessage The WinnerChangedNetMessage to dispatch.
     */
    void dispatch(WinnerChangedNetMessage winnerChangedNetMessage);

    /**
     * Dispatches a NopNetMessage to the client.
     *
     * @param nopNetMessage The NopNetMessage to dispatch.
     */
    void dispatch(NopNetMessage nopNetMessage);
}
