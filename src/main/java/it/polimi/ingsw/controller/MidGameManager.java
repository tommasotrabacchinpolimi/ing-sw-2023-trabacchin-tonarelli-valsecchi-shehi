package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoTileTakenException;
import it.polimi.ingsw.model.exceptions.NotEnoughSpaceInBookShelfException;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.InputCheck;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @param <R>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 6.0
 * @since 27/04/2023
 */
public class MidGameManager<R extends ClientInterface> extends GameManager {
    Timer timer;
    boolean disconnectedFromTheBeginning;
    TimerTask taskSuspended, taskTurnPlayer;

    public MidGameManager(Controller controller){
        super(controller);
        disconnectedFromTheBeginning = false;
        timer = new Timer();
        setNextCurrentPlayer();
    }

    @Override
    public synchronized void dragTilesToBookShelf(ClientInterface user, List<Coordinate> chosenTiles, int chosenColumn){
        try {
            Player player = getController().getState().getPlayerFromView(user);
            if (!player.equals(getController().getPlayerPlaying())) {
                return;
            }
            Board board = getController().getState().getBoard();
            List<TileSubject> tiles = new ArrayList<>();
            for (Coordinate tile : chosenTiles) {
                tiles.add(board.getTileSubjectInBoard(tile.getX(), tile.getY()));
            }
            board.removeSelectedTileSubject(chosenTiles);
            BookShelf bookShelf = player.getBookShelf();
            InputCheck.checkActiveTilesInBoard(chosenTiles, bookShelf.getTileSubjectTaken(),board.getBoard());
            bookShelf.addTileSubjectTaken(tiles, chosenColumn);
            verifyFinalGame(user);
            if (verifyRefillBoard() && getController().getState().getGameState()!=GameState.END) {
                getController().getState().getBoard().refillBoard(getController().getState().getPlayersNumber());
            }
            verifyAdjacentTiles(player);
            verifyPersonalGoal(player);
            verifyCommonGoal(user);
            verifyAllDisconnectedPlayer();
            setNextCurrentPlayer();
        }
        catch (NotEnoughSpaceInBookShelfException | NoTileTakenException | WrongChosenTilesFromBoardException e){
            System.err.println(e.getMessage());
            getController().getState().setLastException(e);
        }
    }

    //se registerPlayer viene chiamata in fase di MID o FINAL della partita allora vuol dire che il giocatore
    // si era disconnesso e ora sta cercando di ri-connettersi, quindi controllo che effettivamente ciò è vero e
    // nel caso ri-setto la view del player corrispondente
    @Override
    public synchronized void registerPlayer(ClientInterface user, String nickname) {
        Player player = getController().getState().getPlayerFromNick(nickname);
        if(player != null && player.getPlayerState() == PlayerState.DISCONNECTED){
            registerListeners(user, nickname);
            player.setVirtualView(user);
            if(player.equals(getController().getState().getCurrentPlayer()) && disconnectedFromTheBeginning) {
                taskSuspended.cancel();
                taskTurnPlayer = new TimerTask() {
                    @Override
                    public void run() {
                        setNextCurrentPlayer();
                    }
                };
                timer.schedule(taskTurnPlayer, 60000);
            }
            disconnectedFromTheBeginning = false;
            player.setPlayerState(PlayerState.CONNECTED);
        }
    }

    //metodo che dice se tutti i player tranne quello passato per parametro sono disconnessi
    private synchronized void verifyAllDisconnectedPlayer(){
        Player player = getController().getState().getCurrentPlayer();
        for(Player p: getController().getState().getPlayers()){
            if(p != player && p.getPlayerState() != PlayerState.DISCONNECTED){
                return;
            }
        }
        GameState gameState = getController().getState().getGameState();
        getController().getState().setGameState(GameState.SUSPENDED);
        getController().setGameManager(new SuspendedGameManager(getController(), gameState));
    }

    private void verifyCommonGoal(ClientInterface user){
        Player player = getController().getState().getPlayerFromView(user);
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

    private void verifyPersonalGoal(Player player) {
        getController().getState().checkPersonalGoal(player);
    }

    private void verifyAdjacentTiles(Player player) {
        getController().getState().checkAdjacentTiles(player);
    }

    private void verifyFinalGame(ClientInterface user){
        Player player = getController().getState().getPlayerFromView(user);

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
        for(int i = 0; i< Board.DIM; i++) {
            for(int j = 0; j< Board.DIM; j++) {
                if(getController().getState().getBoard().getTileSubjectInBoard(i,j)!=null) {
                    if(i != Board.DIM - 1) {
                        if(getController().getState().getBoard().getTileSubjectInBoard(i + 1,j)!=null) {
                            return false;
                        }
                    }
                    if(i != 0) {
                        if(getController().getState().getBoard().getTileSubjectInBoard(i - 1,j)!=null) {
                            return false;
                        }
                    }

                    if(j != Board.DIM - 1) {
                        if(getController().getState().getBoard().getTileSubjectInBoard(i,j + 1)!=null) {
                            return false;
                        }
                    }
                    if(j != 0) {
                        if(getController().getState().getBoard().getTileSubjectInBoard(i,j - 1)!=null) {
                            return false;
                        }
                    }
                }
            }
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
    private synchronized void setNextCurrentPlayer() {
        if(getController().getState().getGameState() == GameState.END) return;

        int n = getController().getState().getPlayersNumber();
        int index;
        Player oldCurrentPlayer = getController().getState().getCurrentPlayer();

        if (oldCurrentPlayer == null) {
            getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(0));
            taskTurnPlayer = new TimerTask() {
                @Override
                public void run() {
                    setNextCurrentPlayer();
                }
            };
            timer.schedule(taskTurnPlayer, 60000);
        } else {
            disconnectedFromTheBeginning = false;
            taskTurnPlayer.cancel();
            if (getController().getState().getGameState() == GameState.FINAL) { //se sono nella fase FINAL del gioco e il prossimo giocatore è il lastPlayer, allora rendo il gioco END
                if (oldCurrentPlayer.equals(getController().getState().getLastPlayer())) {
                    getController().getState().setGameState(GameState.END);
                    Optional<Player> winner = getController().getState().getPlayers().stream().max(Comparator.comparing(p -> p.getPointPlayer().getTotalScore()));
                    winner.ifPresent(player -> getController().getState().setWinner(player));
                }
            }

            index = (getController().getState().getPlayers().indexOf(oldCurrentPlayer) + 1) % n;

            if (getController().getState().getPlayers().get(index).getPlayerState() == PlayerState.QUITTED) {
                index = (index + 1) % n;
                getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));
                setNextCurrentPlayer();
            } else if (getController().getState().getPlayers().get(index).getPlayerState() == PlayerState.DISCONNECTED) {
                disconnectedFromTheBeginning = true;
                getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));
                taskSuspended = new TimerTask() {
                    @Override
                    public void run() {
                        setNextCurrentPlayer();
                    }
                };
                timer.schedule(taskSuspended, 10000);
            } else {
                getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));
                taskTurnPlayer = new TimerTask() {
                    @Override
                    public void run() {
                        setNextCurrentPlayer();
                    }
                };
                timer.schedule(taskTurnPlayer, 60000);
            }
        }
    }

}
