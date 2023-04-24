package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InitGameManager extends GameManager {

    private static final String PERSONAL_GOAL_CONFIGURATION = "./src/main/resources/it.polimi.ingsw/personal.goal.configuration/";
    private List<PersonalGoal> personalGoalsDeck;
    private List<CommonGoal> commonGoalsDeck;
    public InitGameManager(Controller controller) throws FileNotFoundException {
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
        commonGoalsDeck = new LinkedList<>(commonGoalDeserializer.getCommonGoalsDeck());
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
    public synchronized void dragTilesToBookShelf(ClientInterface view, int[] chosenTiles, int chosenColumn) {
        System.err.println("dragTilesToBookShelf called in INIT state");
    }

    @Override
    public synchronized void registerPlayer(ClientInterface view, String nickname) {
        Player player = getController().getState().getPlayerFromNick(nickname);
        if(player!=null && player.getPlayerState()== PlayerState.DISCONNECTED) {
            registerListeners(view, nickname);
            player.setVirtualView(view);
            player.setPlayerState(PlayerState.CONNECTED);
        }
        else {
            Player newPlayer = new Player(nickname,view);
            getController().getState().addPlayer(newPlayer);
            registerListeners(view, nickname);
            registerInternalListener(newPlayer);
            listenersSetUp(newPlayer);
            newPlayer.setPlayerState(PlayerState.CONNECTED);
        }
        if(getController().getState().getPlayers().size() == getController().getState().getPlayersNumber()) {
            getController().getState().setCommonGoal1(commonGoalsDeck.remove(0));
            getController().getState().setCommonGoal2(commonGoalsDeck.remove(0));
            getController().setGameManager(new MidGameManager<>(getController()));
            getController().getState().getBoard().refillBoard(getController().getState().getPlayersNumber());
            getController().getState().setGameState(GameState.MID);
            getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(0));
            for(Player rPlayer : getController().getState().getPlayers()) {
                rPlayer.setPersonalGoal(personalGoalsDeck.remove(0));
            }

        }

    }

    private void registerInternalListener(Player player) {
        player.setOnUpdateNeededListener(getController().getState());
        player.setOnUpdateNeededListener(getController().getState().getBoard());
        player.setOnUpdateNeededListener(player.getBookShelf());
        player.setOnUpdateNeededListener(player.getPointPlayer());
        getController().getState().getPlayers().forEach(player::setOnUpdateNeededListener);
    }

    private void listenersSetUp(Player player) {
        List<ClientInterface> views = getController().getState().getPlayers().stream().filter(p->!player.getNickName().equals(p.getNickName())).map(Player::getVirtualView).toList();
        views.forEach(player::setOnPlayerStateChangedListener);
        views.forEach(v->player.getBookShelf().setOnBookShelfUpdated(v));
    }

}
