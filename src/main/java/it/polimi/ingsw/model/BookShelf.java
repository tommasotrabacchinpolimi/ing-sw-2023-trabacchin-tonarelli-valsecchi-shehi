package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.listeners.OnBookShelfUpdatedListener;
import it.polimi.ingsw.controller.listeners.OnExceptionsListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;
import it.polimi.ingsw.model.exceptions.NoTileTakenException;
import it.polimi.ingsw.model.exceptions.NotEnoughSpaceInBookShelfException;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * <p>Class used to represent bookshelf for each player.</p>
 * <p>This class is unique per {@link Player} entity.</p>
 * <p>Standard {@link #tileSubjectTaken bookshelf} dimension are:
 * <ul>
 *     <li>{@value #STANDARD_ROW}: for rows</li>
 *     <li>{@value #STANDARD_COLUMN}: for columns</li>
 * </ul>
 * According to the <a href="https://www.craniocreations.it/storage/media/product_downloads/48/538/MyShelfie_Ruleboo_ENG_lowres_new.pdf">
 *     Rulebook</a>.</p>
 * <p>To declare a BookShelf with other dimension use
 * {@link BookShelf#BookShelf(int row, int column)} constructor.</p>
 * <p>{@link CommonGoal Common goal} and {@link PersonalGoal personal goal}
 * are assigned based on {@link #tileSubjectTaken bookshelf} field of this class</p>
 *
 * @author Emanuele Valsecchi
 * @version 1.0, 15/04/23
 * @see Player
 * @see TileSubject
 * @see CommonGoal
 */
public class BookShelf implements Serializable, OnUpdateNeededListener {
    @Serial
    private static final long serialVersionUID = 9828497462L;

    /**
     * Standard number of row for {@link #tileSubjectTaken bookshelf} matrix
     *
     * @apiNote All logic of the class is based on {@link #row} field and not to this constant
     * @see BookShelf
     * @see #row
     */
    public static final int STANDARD_ROW = 6;

    /**
     * Standard number of column for {@link #tileSubjectTaken bookshelf} matrix
     *
     * @apiNote All logic of the class is based on {@link #row} field and not to this constant
     * @see BookShelf
     * @see #column
     */
    public static final int STANDARD_COLUMN = 5;

    /**
     * Matrix used to represent the BookShelf for each {@link Player}.
     * This field will contain {@link TileSubject tile objects} to show not only the type of card taken
     * from the {@link Board} but also a specific image.
     *
     * @see BookShelf
     * @see TileSubject
     */
    private TileSubject[][] tileSubjectTaken;

    /**
     * Row dimension for {@link #tileSubjectTaken bookshelf}
     *
     * @see BookShelf
     */
    private final int row;

    /**
     * Column dimension for {@link #tileSubjectTaken bookshelf}
     *
     * @see BookShelf
     */
    private final int column;

    /**
     * The {@linkplain Player player} associated with the BookShelf instance
     */
    private Player player;

    /**
     * A List of listener of the bookshelf object.
     */
    private final List<OnBookShelfUpdatedListener> onBookShelfUpdatedListeners;

    private final List<OnExceptionsListener> exceptionsListeners;

    /**
     * Standard constructor will set dimension of the {@link #tileSubjectTaken bookshelf} to default value.
     *
     * @apiNote {@linkplain Player player} is not set and
     * {@linkplain #onBookShelfUpdatedListeners listeners} are allocated but not set
     *
     * @see BookShelf
     * @see #initTileSubjectTaken()
     */
    public BookShelf() {
        row = STANDARD_ROW;
        column = STANDARD_COLUMN;
        player = null;
        onBookShelfUpdatedListeners = new LinkedList<>();
        initTileSubjectTaken();
        exceptionsListeners = new ArrayList<>();
    }

    /**
     * This constructor create a {@link #tileSubjectTaken bookshelf} matrix with the specified
     * number of row and number of columns passed as parameters.
     *
     * @apiNote <p>Default value {@value #STANDARD_ROW}, {@value #STANDARD_COLUMN} are ignored.</p>
     * <p>{@linkplain Player player} is not set and
     * {@linkplain #onBookShelfUpdatedListeners listeners} are allocated but not set</p>
     *
     * @param row    number of rows for {@link #tileSubjectTaken bookshelf}
     * @param column number of columns for {@link #tileSubjectTaken bookshelf}
     * @see BookShelf
     * @see #initTileSubjectTaken()
     */
    public BookShelf(int row, int column) {
        this.row = row;
        this.column = column;
        initTileSubjectTaken();
        this.player = null;
        onBookShelfUpdatedListeners = new LinkedList<>();
        exceptionsListeners = new ArrayList<>();
    }

    /**
     * Use this constructor to create a copy of the {@linkplain BookShelf bookshelf} passed as parameter
     *
     * @param that the {@linkplain BookShelf bookshelf} object to copy
     */
    public BookShelf(BookShelf that) {
        this.row = that.row;
        this.column = that.column;
        initTileSubjectTaken();
        this.player = that.player;

        this.tileSubjectTaken = Arrays.stream(that.tileSubjectTaken)
                .map(TileSubject[]::clone)
                .toArray(TileSubject[][]::new);

        this.exceptionsListeners = that.exceptionsListeners;
        this.onBookShelfUpdatedListeners = that.onBookShelfUpdatedListeners;
    }

    /**
     * Used to create a {@link #tileSubjectTaken bookshelf} matrix to chosen dimension
     *
     * @apiNote Dimensions are set according to {@link #row} and {@link #column} values
     *
     * @see BookShelf
     * @see #BookShelf()
     * @see #BookShelf(int row, int column)
     */
    private void initTileSubjectTaken(){
        this.tileSubjectTaken = new TileSubject[row][column];
    }

    /**
     * Used to set {@link #tileSubjectTaken bookshelf} matrix
     *
     * @param tileSubjectTaken matrix that will be assigned to the {@link #tileSubjectTaken} field
     * @see BookShelf
     * @see #tileSubjectTaken
     */
    public void setTileSubjectTaken(TileSubject[][] tileSubjectTaken) {
        this.tileSubjectTaken = tileSubjectTaken;
    }

    /**
     * This method return the {@link #tileSubjectTaken BookShelf matrix} contained inside the {@link BookShelf}.
     *
     * @apiNote Type of matrix returned is {@link TileSubject} and not {@link TileType},
     * use {@link #toTileTypeMatrix()} method to obtain the same matrix as the one contained inside
     * {@linkplain #tileSubjectTaken bookshelf} but with {@link TileType} entry.
     *
     * @return {@link #tileSubjectTaken bookshelf} matrix
     *
     * @see BookShelf
     * @see TileType
     * @see TileSubject
     * @see #toTileTypeMatrix()
     */
    public TileSubject[][] getTileSubjectTaken() {
        return tileSubjectTaken;
    }

    /**
     * This method goes through each position of {@link #tileSubjectTaken} and determinate if it is full or not
     *
     * @return <pre>{@code true} if the {@link #tileSubjectTaken BookShelf matrix} is full, {@code false} otherwise</pre>
     * @see BookShelf
     * @see #tileSubjectTaken
     */
    public boolean isFull(){
        for(int i = 0; i < row; ++i ){
            for(int j = 0; j < column; ++j ){
                if( tileSubjectTaken[i][j] == null )
                    return false;
            }
        }

        return true;
    }

    /**
     * This method returns {@link TileType} contained at position {@code row} and {@code column} specified as parameter.
     *
     * @param row    the row index in {@link #tileSubjectTaken bookshelf} matrix to which retrieve the {@link TileType}
     * @param column the column index in {@link #tileSubjectTaken bookshelf} matrix to which retrieve the {@link TileType}
     * @return {@link TileType} of {@link TileSubject} stored at the position referred at the parameter passed,
     *         {@code null} if the cells doesn't contain an element
     * @apiNote Please note that no {@code error}, nor {@code Exception} will be thrown if the cell does not contain an element,
     *          but {@code null} value will be returned</pre>
     * @see BookShelf
     * @see TileType
     */
    private TileType getTypeCell(int row, int column) {
        return (this.tileSubjectTaken[row][column] == null)
                ? null
                : this.tileSubjectTaken[row][column].getTileType();
    }

    /**
     * This method convert the {@link #tileSubjectTaken} field from {@link TileSubject TileSubject matrix} to
     * {@link TileType TileType matrix}
     *
     * @return matrix of {@link TileType} according to the {@link TileSubject} entry inside {@link #tileSubjectTaken}
     * @see BookShelf
     * @see #tileSubjectTaken
     */
    public TileType[][] toTileTypeMatrix(){
        TileType[][] matrix = new TileType[row][column];

        for (int i = 0; i < row; i++ ){
            for (int j = 0; j < column; j++) {
                matrix[i][j] = this.getTypeCell(i, j);
            }
        }

        return matrix;
    }

    /**
     * This method is used to put the {@link TileSubject} taken from the {@link Board} inside the {@link BookShelf}.
     *
     * @param taken  is a List of {@link TileSubject} that contains the tiles dragged from the {@link Board board}
     * @param column is the column of {@link #tileSubjectTaken bookshelf} in which insert the tile's taken
     * @throws ArrayIndexOutOfBoundsException if {@code column} parameter is negative or exceed the
     *         maximum {@link #column} dimension
     * @throws NoTileTakenException if the parameter {@code taken} is {@code null} or its size is 0
     * @throws NotEnoughSpaceInBookShelfException if {@link BookShelf} is full and can't contain all element passed
     *         as parameter
     * @throws NotEnoughSpaceInBookShelfException if the number of cells empty in the {@code column} is not enough to
     *         hold all tiles object passed as parameter
     *
     * @see BookShelf
     * @see #isFull()
     * @see Board
     * @see NotEnoughSpaceInBookShelfException
     * @see NoTileTakenException
     */
    public void addTileSubjectTaken(List<TileSubject> taken, int column) {
        if(column < 0 || column >= this.column) {
            RuntimeException e = new ArrayIndexOutOfBoundsException();
            notifyOnExceptionsListener(e);
            throw e;
        }


        if(taken == null || taken.size() == 0){
            RuntimeException e =  new NoTileTakenException(new NullPointerException());
            notifyOnExceptionsListener(e);
            throw e;
        }

        if(isFull()){
            RuntimeException e = new NotEnoughSpaceInBookShelfException("BookShelf is full");
            notifyOnExceptionsListener(e);
            throw e;
        }

        int row = getFirstEmptyRowFromBottom(column);

        if((row + 1) < taken.size())
            throw new NotEnoughSpaceInBookShelfException();

        for(TileSubject t : taken) {
            tileSubjectTaken[row][column] = t;
            --row;
        }

        notifyOnBookShelfUpdated();
    }

    /**
     * <p>Retrieve the first "row cell" in {@code column} inside the {@link #tileSubjectTaken bookshelf matrix}
     * that is empty.</p>
     * <p>The {@code row} is chosen starting from the {@link #column bottom} of the {@link #tileSubjectTaken matrix}.
     * The {@code column} is set by the parameter.</p>
     *
     * @param column the column in which the {@link TileSubject tiles} must be inserted
     *
     * @return number between 0 and {@link #row row dimension} representing first cell in
     * {@link #tileSubjectTaken BookShelf matrix} that is empty.<br>
     *         In case that the {@link #column} is full {@code -1} is returned.
     * @see BookShelf
     * @see TileSubject
     */
    private int getFirstEmptyRowFromBottom(int column) {
        for(int i = this.column - 1; i > 0; i--) {
            if(tileSubjectTaken[i][column] == null)
                return i;
        }

        return -1;
    }

    /**
     * This method returns the player associated with the BookShelf
     * @return the player which belong to BookShelf
     * @see Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This method sets the player associated with the BookShelf
     * @param player the players that has to belong to BookShelf
     * @see Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Notify all {@linkplain #onBookShelfUpdatedListeners BookShelf listeners}
     */
    private void notifyOnBookShelfUpdated() {
        TileSubject[][] tileSubjects = Arrays.stream(this.tileSubjectTaken)
                .map(TileSubject[]::clone)
                .toArray(TileSubject[][]::new);

        for(OnBookShelfUpdatedListener onBookShelfUpdatedListener : onBookShelfUpdatedListeners) {
            onBookShelfUpdatedListener.onBookShelfUpdated(player.getNickName(), tileSubjects);
        }
    }

    /**
     * Method to add a {@linkplain OnBookShelfUpdatedListener bookshelf listener}
     *
     * @param onBookShelfUpdatedListener the {@linkplain OnBookShelfUpdatedListener bookshelf listener} to add
     */
    public void setOnBookShelfUpdated(OnBookShelfUpdatedListener onBookShelfUpdatedListener) {
        onBookShelfUpdatedListeners.add(onBookShelfUpdatedListener);
    }

    /**
     * Method to remove a {@linkplain OnBookShelfUpdatedListener bookshelf listener}
     * @param onBookShelfUpdatedListener the {@linkplain OnBookShelfUpdatedListener bookshelf listener} to remove
     */
    public void removeOnBookShelfUpdated(OnBookShelfUpdatedListener onBookShelfUpdatedListener) {
        onBookShelfUpdatedListeners.remove(onBookShelfUpdatedListener);
    }

    public void setOnExceptionsListener(OnExceptionsListener listener){
        exceptionsListeners.add(listener);
    }

    public void removeOnExceptionsListener(OnExceptionsListener listener){
        exceptionsListeners.remove(listener);
    }

    private void notifyOnExceptionsListener(RuntimeException e){
        for(OnExceptionsListener listener: exceptionsListeners){
            listener.onException(e);
        }
    }

    /**
     * Method to verify if two bookshelf instance are equals
     * {@inheritDoc}
     *
     * @param o the object that needs to be compared with the instance calling the method
     * @return <pre>{@code true} if the two objects are identical, {@code false} otherwise</pre>
     */
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        BookShelf thatBookShelf = (BookShelf) o;

        return row == thatBookShelf.row && column == thatBookShelf.column &&
                Arrays.deepEquals(tileSubjectTaken, thatBookShelf.tileSubjectTaken) &&
                Objects.equals(player, thatBookShelf.player) &&
                Objects.equals(onBookShelfUpdatedListeners, thatBookShelf.onBookShelfUpdatedListeners);
    }

    /**
     * {@inheritDoc}
     *
     * @return BookShelf instance hash code
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(row, column, player, onBookShelfUpdatedListeners);
        result = 31 * result + Arrays.deepHashCode(tileSubjectTaken);
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param player
     */
    @Override
    public void onUpdateNeededListener(Player player) {
        onBookShelfUpdatedListeners.stream()
                .filter(v -> player.getVirtualView() == v)
                .findAny()
                .ifPresentOrElse(v ->
                        v.onBookShelfUpdated(player.getNickName(),
                                Arrays.stream(this.tileSubjectTaken)
                                        .map(TileSubject[]::clone)
                                        .toArray(TileSubject[][]::new)),
                        () -> System.err.println("no one to update about bookshelf refilled"));

    }


}
