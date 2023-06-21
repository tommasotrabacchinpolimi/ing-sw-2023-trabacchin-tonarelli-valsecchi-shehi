package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.clientmessage.*;
import it.polimi.ingsw.view.Client;

public class ClientDispatcher implements ClientDispatcherInterface{

    private ClientInterface client;

    public ClientDispatcher(Client client) {
        this.client = client;
    }


    @Override
    public void dispatch(AchievedCommonGoalNetMessage achievedCommonGoalNetMessage) {
        client.onAchievedCommonGoal(achievedCommonGoalNetMessage.getNicknamePlayer(), achievedCommonGoalNetMessage.getTiles(), achievedCommonGoalNetMessage.getNumberCommonGoal());
    }

    @Override
    public void dispatch(AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage) {
        client.onAchievedPersonalGoal(achievedPersonalGoalNetMessage.getNickname(), achievedPersonalGoalNetMessage.getTiles());
    }

    @Override
    public void dispatch(AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage) {
        client.onAdjacentTilesUpdated(adjacentTilesUpdatedNetMessage.getNickname(), adjacentTilesUpdatedNetMessage.getTiles());
    }

    @Override
    public void dispatch(AssignedCommonGoalNetMessage assignedCommonGoalNetMessage) {
        client.onAssignedCommonGoal(assignedCommonGoalNetMessage.getDescription(), assignedCommonGoalNetMessage.getN());
    }

    @Override
    public void dispatch(AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage) {
        client.onAssignedPersonalGoal(assignedPersonalGoalNetMessage.getNickname(), assignedPersonalGoalNetMessage.getGoalPattern(), assignedPersonalGoalNetMessage.getScoreMap());
    }

    @Override
    public void dispatch(BoardRefilledNetMessage boardRefilledNetMessage) {
        client.onBoardRefilled();
    }

    @Override
    public void dispatch(BoardUpdatedNetMessage boardUpdatedNetMessage) {
        client.onBoardUpdated(boardUpdatedNetMessage.getTileSubjects());
    }

    @Override
    public void dispatch(BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage) {
        client.onBookShelfUpdated(bookShelfUpdatedNetMessage.getNickname(), bookShelfUpdatedNetMessage.getBookShelf());
    }

    @Override
    public void dispatch(ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage) {
        client.onChangedCommonGoalAvailableScore(changedCommonGoalAvailableScoreNetMessage.getScore(), changedCommonGoalAvailableScoreNetMessage.getNumberOfCommonGoal());
    }

    @Override
    public void dispatch(CurrentPlayerChangedListenerNetMessage currentPlayerChangedListener) {
        client.onCurrentPlayerChangedListener(currentPlayerChangedListener.getNickname());
    }

    @Override
    public void dispatch(ExceptionNetMessage exceptionNetMessage) {
        client.onException(exceptionNetMessage.getException());
    }

    @Override
    public void dispatch(LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage) {
        client.onLastPlayerUpdated(lastPlayerUpdatedNetMessage.getNicknameLastPlayer());
    }

    @Override
    public void dispatch(MessageSentNetMessage messageSentNetMessage) {
        client.onMessageSent(messageSentNetMessage.getNickname(), messageSentNetMessage.getNicknameReceivers(), messageSentNetMessage.getText());
    }

    @Override
    public void dispatch(MessagesSentNetMessage messagesSentNetMessage) {
        client.onMessagesSentUpdate(messagesSentNetMessage.getSenderNicknames(), messagesSentNetMessage.getReceiverNicknames(), messagesSentNetMessage.getTexts());
    }

    @Override
    public void dispatch(PlayersListChangedNetMessage playersListChangedNetMessage) {
        client.onPlayersListChanged(playersListChangedNetMessage.getPlayers());
    }

    @Override
    public void dispatch(PlayerStateChangedNetMessage playerStateChangedNetMessage) {
        client.onPlayerStateChanged(playerStateChangedNetMessage.getNickname(), playerStateChangedNetMessage.getPlayerState());
    }

    @Override
    public void dispatch(PointsUpdatedNetMessage pointsUpdatedNetMessage) {
        client.onPointsUpdated(pointsUpdatedNetMessage.getNickname(), pointsUpdatedNetMessage.getScoreAdjacentGoal(), pointsUpdatedNetMessage.getScoreCommonGoal1(), pointsUpdatedNetMessage.getScoreCommonGoal2(), pointsUpdatedNetMessage.getScoreEndGame(), pointsUpdatedNetMessage.getScorePersonalGoal());
    }

    @Override
    public void dispatch(StateChangedNetMessage stateChangedNetMessage) {
        client.onStateChanged(stateChangedNetMessage.getGameState());
    }

    @Override
    public void dispatch(WinnerChangedNetMessage winnerChangedNetMessage) {
        client.onWinnerChanged(winnerChangedNetMessage.getNickname());
    }

    @Override
    public void dispatch(NopNetMessage nopNetMessage) {
        client.nop();
    }

}
