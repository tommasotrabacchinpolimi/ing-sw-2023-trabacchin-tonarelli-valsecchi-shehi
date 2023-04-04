package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;


public class GameManagerMid<R extends RemoteInterface> extends GameManager<R> {
    private Function<Integer,Integer> fromGroupSizeToScore;
    private static final String COMMON_GOAL_CONFIGURATION = "./src/main/CommonGoalConfiguration/";

    public static void main(String[] args) {
        /*GameManager gameManager = new GameManager();

        gameManager.initCommonGoal(StairCommonGoal.class, 1);*/

        try {
            List<Path> commonGoalConfigFileList = Files.walk(Paths.get(COMMON_GOAL_CONFIGURATION)).toList();
            commonGoalConfigFileList.stream().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dragTilesToBookShelf(User user, int[] chosenTiles, int chosenColumn) {
        Player player = getController().getState().getPlayers().stream().filter(p->p.getNickName().equals(user.getPlayer().getNickName())).toList().get(0);
        if(!player.equals(getController().getPlayerPlaying())){
            return;
        }
        Board board = getController().getState().getBoard();
        List<TileSubject> tiles = new ArrayList<>();
        for(Integer tile : chosenTiles){
            tiles.add(board.fromIntToBoardSquare(tile).getTileSubject());
        }
        BookShelf bookShelf = player.getBookShelf();
        bookShelf.addTileSubjectTaken(tiles,chosenColumn);
        verifyEndGame(user);
        if(verifyRefillBoard()){
            getController().getState().getBoard().refillBoard(getController().getState().getPlayersNumber());
        }
        evaluateFinalScore(player);
        verifyCommonGoal(user);
        setNextCurrentPlayer();
    }

    @Override
    public void registerPlayer(User user, String nickname) {
        return;
    }

    @Override
    public void setPlayersNumber(User user, int playersNumber) {
        return;
    }

    public Function<Integer, Integer> getFromGroupSizeToScore() {
        return fromGroupSizeToScore;
    }

    private void setFromGroupSizeToScore(Function<Integer, Integer> fromGroupSizeToScore) {
        this.fromGroupSizeToScore = fromGroupSizeToScore;
    }

    private void verifyCommonGoal(User<R> user){
        Player player = getController().getPlayerPlaying();
        CommonGoal commonGoal1, commonGoal2;
        BookShelf bookShelf = player.getBookShelf();
        commonGoal1 = super.getController().getActiveCommonGoal1();
        commonGoal2 = super.getController().getActiveCommonGoal2();

        if(commonGoal1.rule(bookShelf.toTileTypeMatrix()) != null){
            player.getPointPlayer().setScoreCommonGoal1(commonGoal1.getAvailableScore());
        }

        if(commonGoal2.rule(bookShelf.toTileTypeMatrix()) != null){
            player.getPointPlayer().setScoreCommonGoal2(commonGoal2.getAvailableScore());
        }
    }

    private void verifyEndGame(User<R> user){
        Player player = super.getController().getPlayerPlaying();

        if(player.getBookShelf().isFull()) {
            player.assignScoreEndGame(1);
        }
    }

    private void evaluateFinalScore(Player player){
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
        int numberOfPlayers = super.getController().getState().getPlayers().size();
        for(BoardSquare b : super.getController().getState().getBoard()){
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
        Player oldCurrentPlayer = super.getController().getState().getCurrentPlayer();
        int index = (super.getController().getState().getPlayers().indexOf(oldCurrentPlayer) + 1) % 4;
        super.getController().getState().setCurrentPlayer(super.getController().getState().getPlayers().get(index));
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



    public void initScoringTokens(){
        Stack<Integer> scoringTokens = new Stack<>();

        int numberOfPlayers = super.getController().getState().getPlayersNumber();

        if(numberOfPlayers == 4)
            scoringTokens.push(2);

        scoringTokens.push(4);
        if (numberOfPlayers >= 3)
            scoringTokens.push(6);

        scoringTokens.push(8);
    }

    public void initCommonGoal(Class<? extends CommonGoal> c, int i) {
        Gson gson = new Gson();
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
