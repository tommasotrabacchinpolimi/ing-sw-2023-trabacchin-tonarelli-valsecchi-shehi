package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 6.0
 * @since 03/05/2023
 */
public abstract class GameManager {
    private final Controller controller;

    public GameManager(Controller controller){
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public abstract void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn);

    public abstract void registerPlayer(ClientInterface view, String nickname);

    protected abstract void setNextCurrentPlayer();

    public synchronized void quitGame(ClientInterface view) {
        getController().getState().getPlayerFromView(view).setPlayerState(PlayerState.QUITTED);
        getController().getLobbyController().onQuitGame(view);
        if(getController().getState().getPlayers().stream().filter(p->p.getPlayerState()!=PlayerState.QUITTED).count() <= 1) {
            getController().getState().setGameState(GameState.END);
            getController().getLobbyController().onEndGame(getController());
        }
    }

    public void registerListeners(ClientInterface view, String nickname){
        ClientInterface oldView = getController().getState().getPlayerFromNick(nickname).getVirtualView();
        getController().getState().removeAchievedCommonGoalListener(oldView);
        getController().getState().setAchievedCommonGoalListener(view);
        getController().getState().removeOnAchievedPersonalGoalListener(oldView);
        getController().getState().setOnAchievedPersonalGoalListener(view);
        getController().getState().removeOnAdjacentTilesUpdatedListener(oldView);
        getController().getState().setOnAdjacentTilesUpdatedListener(view);
        getController().getState().removeOnAssignedCommonGoalListener(oldView);
        getController().getState().setOnAssignedCommonGoalListener(view);
        getController().getState().getPlayerFromNick(nickname).removeOnAssignedPersonalGoalListener(oldView);
        getController().getState().getPlayerFromNick(nickname).setOnAssignedPersonalGoalListener(view);
        getController().getState().getBoard().removeOnBoardRefilledListener(oldView);
        getController().getState().getBoard().setOnBoardRefilledListener(view);
        getController().getState().getBoard().removeOnBoardUpdatedListener(oldView);
        getController().getState().getBoard().setOnBoardUpdatedListener(view);
        getController().getState().removeOnChangedCommonGoalAvailableScoreListener(oldView);
        getController().getState().setOnChangedCommonGoalAvailableScoreListener(view);
        getController().getState().removeOnCurrentPlayerChangedListener(oldView);
        getController().getState().setOnCurrentPlayerChangedListener(view);
        getController().getState().removeLastPlayerUpdatedListeners(oldView);
        getController().getState().setLastPlayerUpdatedListener(view);
        getController().getState().removeMessageSentListener(oldView);
        getController().getState().setMessageSentListener(view);
        getController().getState().removeStateChangedListener(oldView);
        getController().getState().setStateChangedListener(view);
        getController().getState().removeOnExceptionsListener(oldView);
        getController().getState().setOnExceptionsListener(view);
        getController().getState().removeOnWinnerChangedListener(oldView);
        getController().getState().setOnWinnerChangedListener(view);

        for(Player p : getController().getState().getPlayers()){
            p.getBookShelf().removeOnBookShelfUpdated(oldView);
            p.getBookShelf().setOnBookShelfUpdated(view);
        }
        for(Player p : getController().getState().getPlayers()){
            System.out.println("...Register Listeners on "+p.getNickName() + " of "+ getController().getState().getPlayerFromView(oldView).getNickName());
            p.removeOnPlayerStateChangedListener(oldView);
            p.setOnPlayerStateChangedListener(view);
        }
        for(Player p : getController().getState().getPlayers()){
            p.getPointPlayer().removeOnPointsUpdatedListener(oldView);
            p.getPointPlayer().setOnPointsUpdatedListener(view);
        }
        //getController().getState().getPlayers().forEach(p -> p.getBookShelf().removeOnBookShelfUpdated(oldView));
        //getController().getState().getPlayers().forEach(p-> p.getBookShelf().setOnBookShelfUpdated(view));
       // getController().getState().getPlayers().forEach(p->p.removeOnPlayerStateChangedListener(oldView));
        //getController().getState().getPlayers().forEach(p->p.setOnPlayerStateChangedListener(view));
        //getController().getState().getPlayers().forEach(p->p.getPointPlayer().removeOnPointsUpdatedListener(oldView));
        //getController().getState().getPlayers().forEach(p->p.getPointPlayer().setOnPointsUpdatedListener(view));
    }
}
