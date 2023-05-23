package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.listeners.*;
import it.polimi.ingsw.controller.listeners.OnWinnerChangedListener;
import it.polimi.ingsw.net.RemoteInterface;

/**
 * <p>Represents a "physical" user that wants to play the game.</p>
 * <p>This class is used as a sort of model for remote calls</p>
 *
 * @see RemoteInterface
 * @see OnBoardRefilledListener
 * @see OnBoardUpdatedListener
 * @see OnBookShelfUpdatedListener
 * @see OnCurrentPlayerChangedListener
 * @see OnLastPlayerUpdatedListener
 * @see OnMessageSentListener
 * @see OnPlayerStateChangedListener
 * @see OnPointsUpdatedListener
 * @see OnStateChangedListener
 * @see OnAssignedPersonalGoalListener
 * @see OnAssignedCommonGoalListener
 * @see OnExceptionsListener
 * @see OnAchievedPersonalGoalListener
 * @see OnAchievedCommonGoalListener
 * @see OnAdjacentTilesUpdatedListener
 * @see OnChangedCommonGoalAvailableScoreListener
 * @see OnWinnerChangedListener
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 03/05/23
 */
public interface ClientInterface extends RemoteInterface,
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
        OnAchievedCommonGoalListener,
        OnAdjacentTilesUpdatedListener,
        OnChangedCommonGoalAvailableScoreListener,
        OnWinnerChangedListener,
        OnPlayersListChangedListener
                                    {
                                    }
