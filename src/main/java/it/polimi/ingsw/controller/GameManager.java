package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class GameManager {
    private Controller controller;
    private static final String COMMON_GOAL_CONFIGURATION = "./src/main/CommonGoalConfiguration/";

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
        getController().getState().getPlayers().forEach(p -> p.getBookShelf().removeOnBookShelfUpdated(oldView));
        getController().getState().getPlayers().forEach(p-> p.getBookShelf().setOnBookShelfUpdated(view));
        //getController().getState().getCommonGoal1().removeOnChangedCommonGoalAvailableScoreListener(oldView);
        //getController().getState().getCommonGoal1().setOnChangedCommonGoalAvailableScoreListener(view);
        //getController().getState().getCommonGoal2().removeOnChangedCommonGoalAvailableScoreListener(oldView);
        //getController().getState().getCommonGoal2().setOnChangedCommonGoalAvailableScoreListener(view);
        getController().getState().removeOnCurrentPlayerChangedListener(oldView);
        getController().getState().setOnCurrentPlayerChangedListener(view);
        getController().getState().removeLastPlayerUpdatedListeners(oldView);
        getController().getState().setLastPlayerUpdatedListener(view);
        getController().getState().removeMessageSentListener(oldView);
        getController().getState().setMessageSentListener(view);
        getController().getState().getPlayers().forEach(p->p.removeOnPlayerStateChangedListener(oldView));
        getController().getState().getPlayers().forEach(p->p.setOnPlayerStateChangedListener(view));
        getController().getState().getPlayers().forEach(p->p.getPointPlayer().removeOnPointsUpdatedListener(oldView));
        getController().getState().getPlayers().forEach(p->p.getPointPlayer().setOnPointsUpdatedListener(view));
        getController().getState().removeStateChangedListener(oldView);
        getController().getState().setStateChangedListener(view);

    }
}
