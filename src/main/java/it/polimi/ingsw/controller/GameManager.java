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

public class GameManager<R extends RemoteInterface> {
    private Controller<R> controller;
    private Function<Integer,Integer> fromGroupSizeToScore;
    private static final String COMMON_GOAL_CONFIGURATION = "./src/main/CommonGoalConfiguration/";

    public Controller<R> getController() {
        return controller;
    }

    public void dragTilesToBookShelf(R user, int[] chosenTiles, int chosenColumn){
        if(controller.getState().getGameState() == GameState.MID || controller.getState().getGameState() == GameState.FINAL) {
            Player<R> player = controller.getState().getPlayerFromView(user);
            if (!player.equals(getController().getPlayerPlaying())) {
                return;
            }
            Board board = getController().getState().getBoard();
            List<TileSubject> tiles = new ArrayList<>();
            for (Integer tile : chosenTiles) {
                tiles.add(board.fromIntToBoardSquare(tile).getTileSubject());
            }
            BookShelf bookShelf = player.getBookShelf();
            bookShelf.addTileSubjectTaken(tiles, chosenColumn);
            verifyEndGame(user);
            if (verifyRefillBoard() && controller.getState().getGameState()!=GameState.END) {
                getController().getState().getBoard().refillBoard(controller.getState().getPlayersNumber());
            }
            evaluateFinalScore(player);
            verifyCommonGoal(user);
            setNextCurrentPlayer();
        }
    }

    public Function<Integer, Integer> getFromGroupSizeToScore() {
        return fromGroupSizeToScore;
    }

    private void setFromGroupSizeToScore(Function<Integer, Integer> fromGroupSizeToScore) {
        this.fromGroupSizeToScore = fromGroupSizeToScore;
    }

    private void verifyCommonGoal(R user){
        Player<R> player = controller.getState().getPlayerFromView(user);
        CommonGoal commonGoal1, commonGoal2;
        BookShelf bookShelf = player.getBookShelf();
        commonGoal1 = getController().getActiveCommonGoal1();
        commonGoal2 = getController().getActiveCommonGoal2();

        if(commonGoal1.rule(bookShelf.toTileTypeMatrix()) != null){
            player.getPointPlayer().setScoreCommonGoal1(commonGoal1.getAvailableScore());
        }

        if(commonGoal2.rule(bookShelf.toTileTypeMatrix()) != null){
            player.getPointPlayer().setScoreCommonGoal2(commonGoal2.getAvailableScore());
        }
    }

    private void verifyEndGame(R user){
        Player<R> player = controller.getState().getPlayerFromView(user);

        if(player.getBookShelf().isFull()) {
            player.assignScoreEndGame(1);
            controller.getState().setGameState(GameState.FINAL);
            controller.getState().setLastPlayer(controller.getState().getPlayers().get(controller.getState().getPlayersNumber()-1)); //è l'ultimo giocatore della lista
        }
    }

    private void evaluateFinalScore(Player<R> player){
        int scoreAdjacentGoal = 0;
        int personalGoalMatches = 0;
        int scorePersonalGoal = 0;
        TileType[][] bookShelf = player.getBookShelf().toTileTypeMatrix();
        Set<Set<EntryPatternGoal>> groups = findGroups(bookShelf);
        for(Set<EntryPatternGoal> group : groups){
            scoreAdjacentGoal += fromGroupSizeToScore.apply(group.size());
        }
        for(EntryPatternGoal e : player.getPersonalGoal().getGoalPattern()){
            if(player.getBookShelf().toTileTypeMatrix()[e.getRow()][e.getColumn()]==e.getTileType()){
                personalGoalMatches++;
            }
        }
        scorePersonalGoal = player.getPersonalGoal().getScoreMap().get(personalGoalMatches);
        player.getPointPlayer().setScoreAdjacentGoal(scoreAdjacentGoal);
        player.getPointPlayer().setScorePersonalGoal(scorePersonalGoal);
    }

    /**
     * Method that returns true if and only if the Board needs to be refilled with tiles.
     * @return true if and only if the Board needs to be refilled with tiles.
     */
    private boolean verifyRefillBoard(){
        int numberOfPlayers = controller.getState().getPlayers().size();
        for(BoardSquare b : controller.getState().getBoard()){
            if(b.getBottom().getTileSubject()!=null || b.getRight().getTileSubject()!=null ||
                    b.getLeft().getTileSubject()!=null || b.getTop().getTileSubject()!=null) return false;
        }
        return true;
    }

    /**
     * Method that sets the next player who will play.
     *
     * @see Controller
     * @see State
     * @see Player
     */
    private void setNextCurrentPlayer(){
        Player<R> oldCurrentPlayer = controller.getState().getCurrentPlayer();
        if(controller.getState().getGameState() == GameState.FINAL){ //se sono nella fase FINAL del gioco e il prossimo giocatore è il lastPlayer, allora rendo il gioco END
            if(oldCurrentPlayer.equals(controller.getState().getLastPlayer())){
                controller.getState().setGameState(GameState.END);
            }
        }
        int index = (controller.getState().getPlayers().indexOf(oldCurrentPlayer) + 1) % 4;
        controller.getState().setCurrentPlayer(controller.getState().getPlayers().get(index));
    }

    private Set<Set<EntryPatternGoal>> findGroups(TileType[][] bookShelf){
        boolean[][] alreadyTaken = new boolean[bookShelf.length][bookShelf[0].length];//initialized to false
        Set<Set<EntryPatternGoal>> result = new HashSet<Set<EntryPatternGoal>>();
        for(int i = 0;i<bookShelf.length;i++){
            for(int j = 0;j<bookShelf[0].length;j++){
                findSingleGroup(i,j,bookShelf,alreadyTaken,bookShelf[i][j]).ifPresent(result::add);
            }
        }
        return result;
    }

    private Optional<Set<EntryPatternGoal>> findSingleGroup(int i, int j, TileType[][] bookShelf, boolean[][] alreadyTaken, TileType tileType){
        if(tileType==null){
            return Optional.empty();
        }
        if (i<0||i>=bookShelf.length||j<0||j>=bookShelf[0].length){// nothing is to be returned if arguments are illegal
            return Optional.empty();
        }
        if (alreadyTaken[i][j]){ //if this bookShelf is already part of another group then it should not be considered for another group
            return Optional.empty();
        }
        Set<EntryPatternGoal> result = new HashSet<>();// Java documentation recommends using HashSet, unless otherwise required
        if (bookShelf[i][j]!=tileType){//we want only entries whose type is tileType
            return Optional.empty();
        }
        else{
            result.add(new EntryPatternGoal(j,i,tileType));//if the type is correct then the (i,j)-entry can be added to the group
            alreadyTaken[i][j] = true;
        }

        findSingleGroup(i-1,j,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        findSingleGroup(i+1,j,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        findSingleGroup(i,j-1,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        findSingleGroup(i,j+1,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        return Optional.of(result);
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

    public void registerPlayer(R user, String nickname) {
        Player<R> player = controller.getState().getPlayerFromNick(nickname);
        if(player != null){ //Se il giocatore era già presente nella partita, allora si era disconnesso. Quindi aggiorno la view e cambio lo stato del player
            player.setVirtualView(user);
            player.setPlayerState(PlayerState.CONNECTED);
        } else { //se il nickname non è già presente, allora aggiungo il player e gli setto la view
            player = new Player<R>(nickname);
            player.setVirtualView(user);
            controller.getState().addPlayer(player);
        }
    }

}
