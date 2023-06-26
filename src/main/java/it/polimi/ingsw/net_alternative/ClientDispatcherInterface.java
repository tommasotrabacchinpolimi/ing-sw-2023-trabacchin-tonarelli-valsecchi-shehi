package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.net_alternative.clientmessage.*;

/**
 *
 */
public interface ClientDispatcherInterface {

    void dispatch(AchievedCommonGoalNetMessage achievedCommonGoalNetMessage);

    void dispatch(AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage);

    void dispatch(AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage);

    void dispatch(AssignedCommonGoalNetMessage assignedCommonGoalNetMessage);

    void dispatch(AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage);

    void dispatch(BoardRefilledNetMessage boardRefilledNetMessage);

    void dispatch(BoardUpdatedNetMessage boardUpdatedNetMessage);

    void dispatch(BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage);

    void dispatch(ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage);

    void dispatch(CurrentPlayerChangedListenerNetMessage currentPlayerChangedListener);

    void dispatch(ExceptionNetMessage exceptionNetMessage);

    void dispatch(LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage);

    void dispatch(MessageSentNetMessage messageSentNetMessage);

    void dispatch(MessagesSentNetMessage messagesSentNetMessage);

    void dispatch(PlayersListChangedNetMessage playersListChangedNetMessage);

    void dispatch(PlayerStateChangedNetMessage playerStateChangedNetMessage);

    void dispatch(PointsUpdatedNetMessage pointsUpdatedNetMessage);

    void dispatch(StateChangedNetMessage stateChangedNetMessage);

    void dispatch(WinnerChangedNetMessage winnerChangedNetMessage);

    void dispatch(NopNetMessage nopNetMessage);
}
