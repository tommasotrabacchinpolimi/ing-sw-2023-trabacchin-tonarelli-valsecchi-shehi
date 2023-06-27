package it.polimi.ingsw.model;

import com.almasb.fxgl.entity.level.tiled.Tile;
import it.polimi.ingsw.controller.listeners.OnBoardRefilledListener;
import it.polimi.ingsw.controller.listeners.OnBoardUpdatedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;
import it.polimi.ingsw.model.exceptions.NoMoreTileSubjectsLeftInTheBag;
import it.polimi.ingsw.utils.Coordinate;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.BoardSquareType.*;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;

/**
 * <p>Class used to represent the living room board for each game.</p>
 * <p>Standard {@link #board board} dimension are:
 * <ul>
 *     <li>{@value #DIM}: for rows</li>
 *     <li>{@value #DIM}: for columns</li>
 * </ul>
 * <p></p>However, the {@link #board board} is composed of {@value #NUMBER_OF_BOARDSQUARE} board square or cells. Each board square is associated
 * with a {@link BoardSquareType type}, which depends on the numbers of players in the game.
 * The default version of the board is represented in {@link #INIT_MATRIX init matrix}. </p>
 *
 *
 * @see TileSubject
 * @see BoardSquareType
 *
 * <p>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * </p>
 * @version 5.0
 * @since 14/04/2023
 */
public class Board implements Serializable, OnUpdateNeededListener {
    @Serial
    private static final long serialVersionUID = 27112001L;
    /**
     * Standard number of row and column for {@link #board board} square matrix.
     * @see Board
     */
    public static final int DIM = 9;
    /**
     * Standard number of board squares that are contained in the {@link #board board}.
     * @see Board
     */
    private static final int NUMBER_OF_BOARDSQUARE = 45;
    private static final int NUMBER_OF_TILE = 7; //the default number of tile
    private static final int RESERVE_TILE = 8; //the tile that has one object more than others
    /**
     * Matrix of {@linkplain TileSubject} representing the actual game board.
     */
    private final TileSubject[][] board;
    /**
     * List of {@linkplain TileSubject tiles} not yet inserted in the {@linkplain #board}.
     * @see TileSubject
     */
    final private List<TileSubject> bag;
    /**
     * List of {@linkplain OnBoardRefilledListener}
     */
    private final List<OnBoardRefilledListener> onBoardRefilledListeners;
    /**
     * List of {@linkplain OnBoardUpdatedListener}
     */
    private final List<OnBoardUpdatedListener> onBoardUpdatedListeners;
    /**
     * Default version of the board
     * @see BoardSquareType
     */
    public static final BoardSquareType[][] INIT_MATRIX = {
            {null, null, null, THREE_DOTS, FOUR_DOTS, null, null, null, null},
            {null, null, null, NO_DOTS, NO_DOTS, FOUR_DOTS, null, null, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS},
            {FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS},
            {THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, null, null, FOUR_DOTS, NO_DOTS, NO_DOTS, null, null, null},
            {null, null, null, null, FOUR_DOTS, THREE_DOTS, null, null, null}
    };

    /**
     * Constructor of the class {@link Board}.
     */
    public Board() {
        this.onBoardRefilledListeners = new LinkedList<>();
        this.onBoardUpdatedListeners = new LinkedList<>();
        this.bag = new ArrayList<>();
        this.createBag();
        Collections.shuffle(bag);
        this.board = new TileSubject[DIM][DIM];
    }

    /**
     * Method that return the actual {@link #board board}
     * @return matrix of {@link TileSubject tiles} which represents the living room board.
     *
     * @see TileSubject
     */
    public TileSubject[][] getBoard() {
        return board;
    }

    /**
     * Method that return the maximum number of tiles that the board can contain.
     * @return the maximum number of tiles that the board can contain
     */
    public int getNumberOfBoardSquares() {
        return NUMBER_OF_BOARDSQUARE;
    }

    /**
     * Method that return the game bag containing all the remaining {@link TileSubject tiles} in the game.
     * @return list of {@link TileSubject} contained in {@link #bag bag}.
     *
     * @see TileSubject
     */
    public List<TileSubject> getBag() {
        return bag;
    }

    /**
     * Method that initializes the bag with all the tiles in the game. In particular, for each {@link TileType} there are three
     * types of {@link TileSubject}
     */
    private void createBag() {
        int[] occ = {NUMBER_OF_TILE, NUMBER_OF_TILE, RESERVE_TILE};
        int k = 0;
        for (TileSubject el : TileSubject.values()) {
            if (k % 3 == 0)
                shuffleArray(occ);

            for (int i = 0; i < occ[k % 3]; i++) {
                bag.add(el);
            }
            k++;
        }
    }

    /**
     * Method that extracts a casual {@link TileSubject} from the {@link #bag bag} in order to refill the {@link #board board}.
     * @return the {@link TileSubject tile} extracted from the {@link #bag bag}.
     *
     * @see TileSubject
     */
    public TileSubject getRandomTileSubject() {
        Collections.shuffle(this.getBag());
        try{
            return this.bag.remove(0);
        } catch(IndexOutOfBoundsException e) {
            throw new NoMoreTileSubjectsLeftInTheBag();
        }

    }

    /**
     * String formatting the {@linkplain #bag}
     * @returnString formatting the {@linkplain #bag}
     */
    public String bagToString() {
        StringBuilder result = new StringBuilder("BAG: \n");
        for (TileSubject el : bag) {
            result.append(el.name()).append("\n");
        }
        return result.toString();
    }

    /**
     * Method that return the {@link TileSubject tile} contains in the cell {@code (i,j)} in the {@link #board board}.
     * @param i index of the row of the {@link TileSubject tile}  in the {@link #board board}.
     * @param j index of the row of the {@link TileSubject tile}  in the {@link #board board}.
     * @return the tiles contained in the cell {@code (i,j)} in the {@link #board board}.
     */
    public TileSubject getTileSubjectInBoard(int i, int j) {
        return board[i][j];
    }

    /**
     * Method used to swap random elements in an array.
     * @param occurrence the array that need to be shuffled.
     */
    private void shuffleArray(int[] occurrence) {
        Random rnd = new Random();
        for (int i = occurrence.length - 1; i > 0; i--) {
            //Simple swap with random position
            swapArrayElement(occurrence, rnd.nextInt(i + 1), i);
        }
    }

    /**
     * Method to swap two elements in an array.
     * @param array the specified array where the elements are to be swapped.
     * @param i index of the element to be swapped.
     * @param j index of the other element to be swapped.
     */
    private void swapArrayElement(int[] array, int i, int j) {
        int a = array[i];
        array[i] = array[j];
        array[j] = a;
    }

    /**
     * Method that returns {@code true} if the selected board square in {@link #board board} (which is the cell having index of row {@code i} and column {@code j}) can contain
     * a tile according to the number of players in the game.
     * @param i index of the row in the {@link #board board}.
     * @param j index of the column in the {@link #board board}.
     * @param numPlayers number of players in the game.
     * @return {@code true} if and only if the {@link BoardSquareType board square type} can contain a tile.
     */
    private boolean isOkay(int i, int j, int numPlayers){
        return (INIT_MATRIX[i][j] == NO_DOTS || (INIT_MATRIX[i][j] == THREE_DOTS && numPlayers >= 3) || (INIT_MATRIX[i][j] == FOUR_DOTS && numPlayers >= 4));
    }

    /**
     * Method used to remove some selected tiles form the {@link #board board}.
     * @param taken list of {@link Coordinate coordinates} of the tiles that need to be removed from the {@link #board board}.
     * @return the {@link TileSubject tiles} removed from the {@link #board board}.
     *
     * @see TileSubject
     * @see Coordinate
     */
    public List<TileSubject> removeSelectedTileSubject(List<Coordinate> taken) {
        List<TileSubject> result = new ArrayList<>();
        for(Coordinate c : taken){
            result.add(board[c.getX()][c.getY()]);
            board[c.getX()][c.getY()] = null;
        }
        notifyOnBoardUpdated();
        return result;
    }

    /**
     * <p>Method used to refill the {@link #board board} using tiles from {@link #bag bag} when on the board
     * there are only item tiles without any other adjacent tile, i.e. it is only possible to take single tiles.</p>
     * @param numberOfPlayers number of the player in the game, used to refill the board properly according to the {@link BoardSquareType}.
     */
    public void refillBoard(int numberOfPlayers){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(isOkay(i, j, numberOfPlayers) && board[i][j] == null){
                    board[i][j] = getRandomTileSubject();
                }
            }
        }

        notifyOnBoardUpdated();
        notifyOnBoardRefilled();
    }

    /**
     * Notified all the {@linkplain OnBoardUpdatedListener}
     */
    private void notifyOnBoardUpdated() {
        TileSubject[][] boardCopy = Arrays.stream(board).map(TileSubject[]::clone).toArray(TileSubject[][]::new);
        for(OnBoardUpdatedListener onBoardUpdatedListener : onBoardUpdatedListeners) {
            onBoardUpdatedListener.onBoardUpdated(boardCopy);
        }
    }

    /**
     * Notified all the {@linkplain OnBoardRefilledListener}
     */
    private void notifyOnBoardRefilled() {
        for(OnBoardRefilledListener onBoardRefilledListener : onBoardRefilledListeners) {
            if(onBoardRefilledListener != null) {
                onBoardRefilledListener.onBoardRefilled();
            }
        }
    }

    /**
     * Adds a new {@linkplain OnBoardRefilledListener}
     * @param onBoardRefilledListener the listener to add
     */
    public void setOnBoardRefilledListener(OnBoardRefilledListener onBoardRefilledListener) {
        this.onBoardRefilledListeners.add(onBoardRefilledListener);
    }

    /**
     * Adds a new {@linkplain OnBoardUpdatedListener}
     * @param onBoardUpdatedListener the listener to add
     */
    public void setOnBoardUpdatedListener(OnBoardUpdatedListener onBoardUpdatedListener) {
        this.onBoardUpdatedListeners.add(onBoardUpdatedListener);
    }

    /**
     * Removes a given {@linkplain OnBoardRefilledListener}
     * @param onBoardRefilledListener the listener to remove
     */
    public void removeOnBoardRefilledListener(OnBoardRefilledListener onBoardRefilledListener) {
        this.onBoardRefilledListeners.remove(onBoardRefilledListener);
    }

    /**
     * Removes a given {@linkplain OnBoardRefilledListener}
     * @param onBoardUpdatedListener the listener to remove
     */
    public void removeOnBoardUpdatedListener(OnBoardUpdatedListener onBoardUpdatedListener) {
        this.onBoardUpdatedListeners.remove(onBoardUpdatedListener);
    }


    /**
     * Notifies the listener that an update is needed for the specified player.
     * @param player the player for which an update is needed
     * @see Player
     */
    @Override
    public void onUpdateNeededListener(Player player) {
        onBoardUpdatedListeners.stream().forEach(v->v.onBoardUpdated(Arrays.stream(board).map(TileSubject[]::clone).toArray(TileSubject[][]::new)));
    }

}

