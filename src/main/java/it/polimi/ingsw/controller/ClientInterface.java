package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.listeners.*;
import it.polimi.ingsw.controller.listeners.OnWinnerChangedListener;
import it.polimi.ingsw.net.RemoteInterface;

public interface ClientInterface extends RemoteInterface, OnAchievedCommonGoalListener, OnBoardRefilledListener, OnBoardUpdatedListener, OnBookShelfUpdatedListener, OnCurrentPlayerChangedListener, OnLastPlayerUpdatedListener, OnMessageSentListener, OnPlayerStateChangedListener, OnPointsUpdatedListener, OnStateChangedListener, OnAssignedPersonalGoalListener, OnAssignedCommonGoalListener, OnExceptionsListener, OnAchievedPersonalGoalListener, OnAdjacentTilesUpdatedListener, OnChangedCommonGoalAvailableScoreListener, OnWinnerChangedListener {

}
