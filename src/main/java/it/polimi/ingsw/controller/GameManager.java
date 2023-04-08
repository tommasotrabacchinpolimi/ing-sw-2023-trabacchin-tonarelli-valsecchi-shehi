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

    public abstract void quitGame(R view);

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

}
