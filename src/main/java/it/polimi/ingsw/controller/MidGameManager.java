package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoMoreTileSubjectsLeftInTheBag;
import it.polimi.ingsw.model.exceptions.NoTileTakenException;
import it.polimi.ingsw.model.exceptions.NotEnoughSpaceInBookShelfException;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.InputCheck;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * The {@code MidGameManager} class is responsible for managing the game flow during the mid phase of the game.
 * <br>
 *
 * It extends the {@code GameManager} class and provides specific behavior for the mid game phase.
 *
 * <p>
 * This class overrides methods from the {@code GameManager} class to provide custom behavior for the mid game phase.
 * <br>
 *
 * It includes methods for dragging tiles to the bookshelf, registering players during reconnection, verifying game conditions,
 * and setting the next current player.
 *
 * </p>
 *
 * @see GameManager
 *
 * @see ClientInterface
 *
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 6.0
 * @since 27/04/2023
 */
public class MidGameManager extends GameManager {

    /**
     Constructs a new {@code MidGameManager} with the specified controller.
     @param controller the controller for the game
     */
    public MidGameManager(Controller controller){
        super(controller);
    }

    /**
     *
     *   {@inheritDoc}
     *
     * This method allows the user to drag tiles to the bookshelf.
     * It verifies the validity of the chosen tiles and updates the game state accordingly.
     *
     * @param user the client interface of the user
     * @param chosenTiles the list of coordinates representing the chosen tiles
     * @param chosenColumn the chosen column in the bookshelf
     */
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
            BookShelf bookShelf = player.getBookShelf();
            InputCheck.checkActiveTilesInBoard(chosenTiles, bookShelf.getTileSubjectTaken(),board.getBoard());
            bookShelf.addTileSubjectTaken(tiles, chosenColumn);
            board.removeSelectedTileSubject(chosenTiles);
           // bookShelf.addTileSubjectTaken(tiles, chosenColumn);

            verifyFinalGame(user);
            if (verifyRefillBoard() && getController().getState().getGameState()!=GameState.END) {
                getController().getState().getBoard().refillBoard(getController().getState().getPlayersNumber());
            }
            verifyAdjacentTiles(player);
            verifyPersonalGoal(player);
            verifyCommonGoal(user);
            //verifyAllDisconnectedPlayer();
            setNextCurrentPlayer();
        }
        catch (NotEnoughSpaceInBookShelfException | NoTileTakenException | WrongChosenTilesFromBoardException e){
            System.err.println(e.getMessage());
            getController().getState().setLastException(e);
        }
        catch(NoMoreTileSubjectsLeftInTheBag e) {
            System.err.println(e.getMessage());
            getController().getState().setLastException(e);
            getController().getState().setGameState(GameState.END);
        }
    }

    //se registerPlayer viene chiamata in fase di MID o FINAL della partita allora vuol dire che il giocatore
    // si era disconnesso e ora sta cercando di ri-connettersi, quindi controllo che effettivamente ciò è vero e
    // nel caso ri-setto la view del player corrispondente

    /**

     {@inheritDoc}
     This method is called when a player tries to register during reconnection.
     If the player is successfully reconnected, their virtual view is updated and their state is changed to connected.
     @param user the client interface of the user
     @param nickname the nickname of the player
     */
    @Override
    public synchronized void registerPlayer(ClientInterface user, String nickname) {
        Player player = getController().getState().getPlayerFromNick(nickname);
        if(player != null && player.getPlayerState() == PlayerState.DISCONNECTED){
            ClientInterface oldView = getController().getState().getPlayerFromNick(nickname).getVirtualView();
            getController().getState().removeOnPlayersListChangedListener(oldView);
            getController().getState().setOnPlayersListChangedListener(user);
            registerListeners(user, nickname);
            player.setVirtualView(user);
            player.setPlayerState(PlayerState.CONNECTED);
        }
    }

    //metodo che dice se tutti i player tranne quello passato per parametro sono disconnessi
    /*private synchronized boolean verifyAllDisconnectedPlayer(){

        Player player = getController().getState().getCurrentPlayer();
        for(Player p: getController().getState().getPlayers()){
            if(p != player && p.getPlayerState() != PlayerState.DISCONNECTED){
                return false;
            }
        }
        GameState gameState = getController().getState().getGameState();
        getController().getState().setGameState(GameState.SUSPENDED);
        getController().setGameManager(new SuspendedGameManager(getController(), gameState));
        return true;
    }*/

    private void verifyCommonGoal(ClientInterface user){
        Player player = getController().getState().getPlayerFromView(user);
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
     * {@inheritDoc}
     * Sets the next player who will play based on the current game state and player states.
     * @see Controller
     * @see State
     * @see Player
     */
    @Override
    protected synchronized void setNextCurrentPlayer() {

        if(getController().getState().getGameState() == GameState.END) return;

        int n = getController().getState().getPlayersNumber();
        int index;
        Player oldCurrentPlayer = getController().getState().getCurrentPlayer();

        if (oldCurrentPlayer == null) {
            getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(0));
        } else {
            if (getController().getState().getGameState() == GameState.FINAL) { //se sono nella fase FINAL del gioco e il prossimo giocatore è il lastPlayer, allora rendo il gioco END
                if (oldCurrentPlayer.equals(getController().getState().getLastPlayer())) {
                    getController().getState().setGameState(GameState.END);
                    Optional<Player> winner = getController().getState().getPlayers().stream().max(Comparator.comparing(p -> p.getPointPlayer().getTotalScore()));
                    winner.ifPresent(player -> getController().getState().setWinner(player));
                }
            }

            index = (getController().getState().getPlayers().indexOf(oldCurrentPlayer) + 1) % n;

            if (getController().getState().getPlayers().get(index).getPlayerState() == PlayerState.QUITTED) {
                if(getController().getState().getGameState() == GameState.FINAL) {
                    getController().getState().setGameState(GameState.END);
                    Optional<Player> winner = getController().getState().getPlayers().stream().max(Comparator.comparing(p -> p.getPointPlayer().getTotalScore()));
                    winner.ifPresent(player -> getController().getState().setWinner(player));
                }
                index = (index + 1) % n;
                while(true) {
                    if(getController().getState().getPlayers().get(index).getPlayerState() != PlayerState.QUITTED) {
                        getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));
                        break;
                    }
                    index = (index + 1) % n;
                }
            } else if (getController().getState().getPlayers().get(index).getPlayerState() == PlayerState.DISCONNECTED) {
                getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));

            } else {
                getController().getState().setCurrentPlayer(getController().getState().getPlayers().get(index));
            }
        }
    }

}
