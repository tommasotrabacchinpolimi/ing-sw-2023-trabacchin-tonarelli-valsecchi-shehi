package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.listeners.*;
import it.polimi.ingsw.controller.listeners.OnWinnerChangedListener;
import it.polimi.ingsw.net.RemoteInterface;

/**
 * This class represents a "physical" user that wants to play the game
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0, 03/05/23
 */
public interface ClientInterface extends RemoteInterface,
        OnAchievedCommonGoalListener,
        OnBoardRefilledListener,
        OnBoardUpdatedListener,
        OnBookShelfUpdatedListener,
        OnCurrentPlayerChangedListener,
        OnLastPlayerUpdatedListener,
        OnMessageSentListener,
        OnPlayerStateChangedListener,
        OnPointsUpdatedListener,
        OnStateChangedListener,
        OnAssignedPersonalGoalListener,
        OnAssignedCommonGoalListener,
        OnExceptionsListener,
        OnAchievedPersonalGoalListener,
        OnAdjacentTilesUpdatedListener,
        OnChangedCommonGoalAvailableScoreListener,
        OnWinnerChangedListener {

}
