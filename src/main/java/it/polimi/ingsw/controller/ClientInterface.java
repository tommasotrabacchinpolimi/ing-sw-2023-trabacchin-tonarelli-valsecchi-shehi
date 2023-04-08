package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.listeners.*;
import it.polimi.ingsw.net.RemoteInterface;

public interface ClientInterface extends RemoteInterface, OnAchievedCommonGoalListener, OnBoardRefilledListener, OnBoardUpdatedListener, OnBookShelfUpdatedListener, OnCurrentPlayerChangedListener, OnLastPlayerUpdatedListener, OnMessageSentListener, OnPlayerDisconnectedListener, OnPointsUpdatedListener, OnStateChangedListener {

}