package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.*;
import java.util.function.Function;

public class MidGameManager<R extends ClientInterface> extends GameManager<R> {
    private Function<Integer,Integer> fromGroupSizeToScore;

    public void dragTilesToBookShelf(R user, int[] chosenTiles, int chosenColumn){
        Player<R> player = getController().getState().getPlayerFromView(user);
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
        if (verifyRefillBoard() && getController().getState().getGameState()!=GameState.END) {
            getController().getState().getBoard().refillBoard(getController().getState().getPlayersNumber());
        }
        evaluateFinalScore(player);
        verifyCommonGoal(user);
        setNextCurrentPlayer();
    }

    //se registerPlayer viene chiamata in fase di MID o FINAL della partita allora vuol dire che il giocatore
    // si era disconnesso e ora sta cercando di riconettersi, quindi controllo che effettivamente ciò è vero e
    // nel caso risetto la view del player corrispondente
    public void registerPlayer(R user, String nickname) {
        Player<R> player = getController().getState().getPlayerFromNick(nickname);
        if(player != null && player.getPlayerState() == PlayerState.DISCONNECTED){
            player.setVirtualView(user);
            player.setPlayerState(PlayerState.CONNECTED);
        }
    }

    private void verifyCommonGoal(R user){
        Player<R> player = getController().getState().getPlayerFromView(user);
        /*CommonGoal commonGoal1, commonGoal2;
        BookShelf bookShelf = player.getBookShelf();
        commonGoal1 = getController().getActiveCommonGoal1();
        commonGoal2 = getController().getActiveCommonGoal2();

        if(commonGoal1.rule(bookShelf.toTileTypeMatrix()) != null){
            player.getPointPlayer().setScoreCommonGoal1(commonGoal1.getAvailableScore());
        }

        if(commonGoal2.rule(bookShelf.toTileTypeMatrix()) != null){
            player.getPointPlayer().setScoreCommonGoal2(commonGoal2.getAvailableScore());
        }*/
        getController().getState().checkCommonGoal(player);
    }

    private void verifyEndGame(R user){
        Player<R> player = getController().getState().getPlayerFromView(user);

        if(player.getBookShelf().isFull()) {
            player.assignScoreEndGame(1);
            getController().getState().setGameState(GameState.FINAL);
            getController().getState().setLastPlayer(getController().getState().getPlayers().get(getController().getState().getPlayersNumber()-1)); //è l'ultimo giocatore della lista
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
        int numberOfPlayers = getController().getState().getPlayers().size();
        for(BoardSquare b : getController().getState().getBoard()){
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
        int n = getController().getState().getPlayersNumber();
        int index;
        Player<R> oldCurrentPlayer = getController().getState().getCurrentPlayer();

        if(getController().getState().getGameState() == GameState.FINAL){ //se sono nella fase FINAL del gioco e il prossimo giocatore è il lastPlayer, allora rendo il gioco END
            if(oldCurrentPlayer.equals(getController().getState().getLastPlayer())){
                getController().getState().setGameState(GameState.END);
            }
        }

        index = (getController().getState().getPlayers().indexOf(oldCurrentPlayer) + 1) % n;
        for(int i = 0; i < n; i++ ){
            if(getController().getState().getPlayers().get(index).getPlayerState()==PlayerState.DISCONNECTED){
                index = (index + 1) % n;
            } else {
                getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));
                return;
            }
        }
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

    private Function<Integer, Integer> getFromGroupSizeToScore() {
        return fromGroupSizeToScore;
    }

    private void setFromGroupSizeToScore(Function<Integer, Integer> fromGroupSizeToScore) {
        this.fromGroupSizeToScore = fromGroupSizeToScore;
    }

}
