package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

public abstract class GameManager {
    private Controller controller;

    public GameManager(Controller controller){
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public abstract void dragTilesToBookShelf(ClientInterface view, int[] chosenTiles, int chosenColumn);

    public abstract void registerPlayer(ClientInterface view, String nickname);

    public synchronized void quitGame(ClientInterface view) {
        getController().getState().getPlayerFromView(view).setPlayerState(PlayerState.QUITTED);
        getController().getLobbyController().onQuitGame(view);
        if(getController().getState().getPlayers().stream().noneMatch(p->p.getPlayerState()!=PlayerState.QUITTED)) {
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
        getController().getState().getPlayerFromNick(nickname).getBookShelf().removeOnBookShelfUpdated(oldView);
        getController().getState().getPlayerFromNick(nickname).getBookShelf().setOnBookShelfUpdated(view);
        getController().getState().getCommonGoal1().removeOnChangedCommonGoalAvailableScoreListener(oldView);
        getController().getState().getCommonGoal1().setOnChangedCommonGoalAvailableScoreListener(view);
        getController().getState().getCommonGoal2().removeOnChangedCommonGoalAvailableScoreListener(oldView);
        getController().getState().getCommonGoal2().setOnChangedCommonGoalAvailableScoreListener(view);
        getController().getState().removeOnCurrentPlayerChangedListener(oldView);
        getController().getState().setOnCurrentPlayerChangedListener(view);
        getController().getState().removeLastPlayerUpdatedListeners(oldView);
        getController().getState().setLastPlayerUpdatedListener(view);
        getController().getState().removeMessageSentListener(oldView);
        getController().getState().setMessageSentListener(view);
        getController().getState().getPlayerFromNick(nickname).removeOnPlayerStateChangedListener(oldView);
        getController().getState().getPlayerFromNick(nickname).setOnPlayerStateChangedListener(view);
        getController().getState().getPlayerFromNick(nickname).getPointPlayer().removeOnPointsUpdatedListener(oldView);
        getController().getState().getPlayerFromNick(nickname).getPointPlayer().setOnPointsUpdatedListener(view);
        getController().getState().removeStateChangedListener(oldView);
        getController().getState().setStateChangedListener(view);
    }
}
