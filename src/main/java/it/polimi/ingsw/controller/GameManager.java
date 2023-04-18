package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;

public abstract class GameManager<R extends ClientInterface> {
    private Controller<R> controller;
    private static final String COMMON_GOAL_CONFIGURATION = "./src/main/CommonGoalConfiguration/";

    public GameManager(Controller<R> controller){
        this.controller = controller;
    }

    public Controller<R> getController() {
        return controller;
    }

    public abstract void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn);

    public abstract void registerPlayer(R view, String nickname);

    public synchronized void quitGame(R view) {
        getController().getState().getPlayerFromView(view).setPlayerState(PlayerState.QUITTED);
        getController().getLobbyController().onQuitGame(view);
        if(getController().getState().getPlayers().stream().noneMatch(p->p.getPlayerState()!=PlayerState.QUITTED)) {
            getController().getState().setGameState(GameState.END);
            getController().getLobbyController().onEndGame(getController());
        }
    }

    public void initCommonGoal(Class<? extends CommonGoal> c, int i) {
        Gson gson = new GsonBuilder().setExclusionStrategies(new JSONExclusionStrategy()).create();
        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader(COMMON_GOAL_CONFIGURATION+c.getSimpleName()+i+".json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        CommonGoal commonGoal = gson.fromJson(reader, c);

        System.out.println(commonGoal.toString());
    }

    public void registerListeners(R view, String nickname){
        R oldView = getController().getState().getPlayerFromNick(nickname).getVirtualView();
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
