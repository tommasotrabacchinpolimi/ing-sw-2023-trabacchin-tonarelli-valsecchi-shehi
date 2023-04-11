package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InitGameManager<R extends ClientInterface> extends GameManager<R>{

    private static final String PERSONAL_GOAL_CONFIGURATION = "./src/main/resources/PersonalGoalConfiguration/";
    private List<PersonalGoal> personalGoalsDeck;
    private List<CommonGoal> commonGoalsDeck;
    public InitGameManager(Controller<R> controller) throws FileNotFoundException {
        super(controller);
        initPersonalGoals();
        initCommonGoals();

    }

    private void initPersonalGoals() throws FileNotFoundException {
        File rootFolder = new File(PERSONAL_GOAL_CONFIGURATION);
        File[] files = rootFolder.listFiles(File::isFile);
        personalGoalsDeck = new ArrayList<>();
        for(File file : files) {
            personalGoalsDeck.add(new PersonalGoal(file.getPath()));
        }
        Collections.shuffle(personalGoalsDeck);
    }

    private void initCommonGoals() {
        CommonGoalDeserializer commonGoalDeserializer = new CommonGoalDeserializer();
        commonGoalsDeck = commonGoalDeserializer.getCommonGoalsDeck().stream().toList();
        for(CommonGoal commonGoal : commonGoalsDeck) {

            if (getController().getState().getPlayersNumber() == 4) {
                commonGoal.getScoringTokens().push(2);
                commonGoal.getScoringTokens().push(4);
                commonGoal.getScoringTokens().push(6);
                commonGoal.getScoringTokens().push(8);
            }
            else if(getController().getState().getPlayersNumber() == 3) {
                commonGoal.getScoringTokens().push(4);
                commonGoal.getScoringTokens().push(6);
                commonGoal.getScoringTokens().push(8);
            }
            else {
                commonGoal.getScoringTokens().push(4);
                commonGoal.getScoringTokens().push(8);
            }
        }
        Collections.shuffle(commonGoalsDeck);
    }
    @Override
    public synchronized void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn) {
        System.err.println("dragTilesToBookShelf called in INIT state");
    }

    @Override
    public synchronized void registerPlayer(R view, String nickname) {
        Player<R> player = getController().getState().getPlayerFromNick(nickname);
        if(player!=null && player.getPlayerState()== PlayerState.DISCONNECTED) {
            player.setVirtualView(view);
            player.setPlayerState(PlayerState.CONNECTED);
        }
        else {
            getController().getState().addPlayer(new Player<R>(nickname,view));
        }
        if(getController().getState().getPlayers().size() == getController().getState().getPlayersNumber()) {
            getController().setGameManager(new MidGameManager<>(getController()));
            getController().getState().getBoard().refillBoard(getController().getState().getPlayersNumber());
            getController().getState().setGameState(GameState.MID);
            for(Player<R> rPlayer : getController().getState().getPlayers()) {
                rPlayer.setPersonalGoal(personalGoalsDeck.remove(0));
            }
            getController().getState().setCommonGoal1(commonGoalsDeck.remove(0));
            getController().getState().setCommonGoal1(commonGoalsDeck.remove(0));
        }
    }
}
