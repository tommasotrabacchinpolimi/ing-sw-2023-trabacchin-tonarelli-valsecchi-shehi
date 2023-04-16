package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoTileTakenException;
import it.polimi.ingsw.model.exceptions.NotEnoughSpaceInBookShelfException;

import java.util.*;
import java.util.function.Function;

public class MidGameManager<R extends ClientInterface> extends GameManager<R> {

    public MidGameManager(Controller<R> controller){
        super(controller);
        //controller.getState().getBoard().refillBoard(controller.getState().getPlayersNumber());
    }

    @Override
    public synchronized void dragTilesToBookShelf(R user, int[] chosenTiles, int chosenColumn){
        try {
            Player<R> player = getController().getState().getPlayerFromView(user);
            if (!player.equals(getController().getPlayerPlaying())) {
                return;
            }
            Board<R> board = getController().getState().getBoard();
            List<TileSubject> tiles = new ArrayList<>();
            for (Integer tile : chosenTiles) {
                tiles.add(board.fromIntToBoardSquare(tile).getTileSubject());
            }
            BookShelf<R> bookShelf = player.getBookShelf();
            bookShelf.addTileSubjectTaken(tiles, chosenColumn);
            verifyEndGame(user);
            if (verifyRefillBoard() && getController().getState().getGameState()!=GameState.END) {
                getController().getState().getBoard().refillBoard(getController().getState().getPlayersNumber());
            }
            verifyAdjacentTiles(player);
            verifyPersonalGoal(player);
            verifyCommonGoal(user);
            setNextCurrentPlayer();
            verifyAllDisconnectedPlayer();
        }
        catch (NotEnoughSpaceInBookShelfException | NoTileTakenException e){
            System.err.println(e.getMessage());
        }
    }

    //se registerPlayer viene chiamata in fase di MID o FINAL della partita allora vuol dire che il giocatore
    // si era disconnesso e ora sta cercando di ri-connettersi, quindi controllo che effettivamente ciò è vero e
    // nel caso ri-setto la view del player corrispondente
    @Override
    public synchronized void registerPlayer(R user, String nickname) {
        Player<R> player = getController().getState().getPlayerFromNick(nickname);
        if(player != null && player.getPlayerState() == PlayerState.DISCONNECTED){
            player.setVirtualView(user);
            player.setPlayerState(PlayerState.CONNECTED);
        }
    }


    //metodo che dice se tutti i player tranne quello passato per parametro sono disconnessi
    private synchronized void verifyAllDisconnectedPlayer(){
        Player<R> player = getController().getPlayerPlaying();
        for(Player<R> p: getController().getState().getPlayers()){
            if(p != player && p.getPlayerState() != PlayerState.DISCONNECTED){
                return;
            }
        }
        getController().getState().setGameState(GameState.SUSPENDED);
        getController().setGameManager(new SuspendedGameManager<>(getController()));
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

    private void verifyPersonalGoal(Player<R> player) {
        getController().getState().checkPersonalGoal(player);
    }
    private void verifyAdjacentTiles(Player<R> player) {
        getController().getState().checkAdjacentTiles(player);
    }

    private void verifyEndGame(R user){
        Player<R> player = getController().getState().getPlayerFromView(user);

        if(player.getBookShelf().isFull()) {
            player.assignScoreEndGame(1);
            getController().getState().setGameState(GameState.FINAL);
            getController().getState().setLastPlayer(getController().getState().getPlayers().get(getController().getState().getPlayersNumber()-1)); //è l'ultimo giocatore della lista
        }
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
            if(getController().getState().getPlayers().get(index).getPlayerState()==PlayerState.DISCONNECTED ||
                    getController().getState().getPlayers().get(index).getPlayerState()==PlayerState.QUITTED){
                index = (index + 1) % n;
            } else {
                getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));
                return;
            }
        }
    }
}
