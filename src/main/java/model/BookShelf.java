package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class used to represent personal BookShelf for each player.
 * This class is unique per {@link Player} entity.<br>
 * BookShelf is formed using a matrix of {@link TileSubject}.
 * It's standard dimension are: <br>
 * • <code>6</code>: for rows<br>
 * • <code>5</code>: for columns<br>
 * Dimension are set according to {@link BookShelf#STANDARD_ROW}, {@link BookShelf#STANDARD_COLUMN} constant.
 * To declare a BookShelf with other dimension use
 * {@link BookShelf#BookShelf(int row, int column) BookShelf(row, column)} constructor. <br>
 * This class is used by:<br>
 * • {@link CommonGoal} subclass to assign CommonGoal points to the corresponding player;<br>
 * • {@link PersonalGoal} to assign PersonalGoal points referring to the pattern inside the card<br>
 *
 * @author Emanuele Valsecchi
 * @version 1.0, 15/04/23
 * @see Player
 * @see TileSubject
 * @see CommonGoal
 */
public class BookShelf implements Serializable {
    @Serial
    private static final long serialVersionUID = 9828497462L;
    /**
     * Standard number of row for {@link #tileSubjectTaken} matrix
     *
     * @apiNote To write method in this class please remember to use {@link #row} and not {@code STANDARD_ROW}
     * @see BookShelf
     */
    public static final int STANDARD_ROW = 6;
    /**
     * Standard number of column for {@link #tileSubjectTaken} matrix
     *
     * @apiNote To write method in this class please remember to use {@link #row} and not {@code STANDARD_ROW}
     * @see BookShelf
     */
    public static final int STANDARD_COLUMN = 5;
    /**
     * Matrix used to represent the BookShelf for each {@link Player}. Type is set to {@link TileSubject} because
     * is necessary to show not only the type of card taken from the {@link Board} but also the image.
     *
     * @see BookShelf
     * @see TileSubject
     */
    private TileSubject[][] tileSubjectTaken;

    /**
     * This variable is used to set the row dimension for {@link #tileSubjectTaken}
     *
     * @see BookShelf
     */
    private final int row;
    /**
     * This variable is used to set the column dimension for {@link #tileSubjectTaken}
     *
     * @see BookShelf
     */
    private final int column;

    /**
     * Standard constructor will set:
     * <pre><code>{@link #row} = {@link #STANDARD_ROW}<br>{@link #column} = {@link #STANDARD_COLUMN}</code></pre>
     * In the end <code>{@link #tileSubjectTaken}</code> will have:<br>
     * • <code>6</code>: rows<br>
     * • <code>7</code>: columns<br>
     *
     * @see BookShelf
     * @see #initTileSubjectTaken()
     */
    public BookShelf() {
        row = STANDARD_ROW;
        column = STANDARD_COLUMN;
        initTileSubjectTaken();
    }

    /**
     * This constructor will set the number of row and the number of columns for {@link #tileSubjectTaken BookShelf matrix}
     * according to the param of the constructor.
     *
     * @apiNote Default value {@link #STANDARD_ROW}, {@link #STANDARD_COLUMN} are ignored.<br>
     * {@link #row} is set to the value of first parameter; {@link #column} is set to the value of second parameter
     * @param row number of rows for {@link #tileSubjectTaken}
     * @param column number of columns for {@link #tileSubjectTaken}
     * @see BookShelf
     * @see #initTileSubjectTaken()
     */
    public BookShelf(int row, int column) {
        this.row = row;
        this.column = column;
        initTileSubjectTaken();
    }

    /**
     * This function is used inside constructors to set the dimension of {@link #tileSubjectTaken} to the {@link #row}
     * and {@link #column} value without caring about the constructor called.
     * @apiNote Inside the method only {@link #row} and {@link #column} are used
     *
     * @see BookShelf
     * @see #BookShelf()
     * @see #BookShelf(int row, int column) BookShelf(row, column)
     */
    private void initTileSubjectTaken(){
        this.tileSubjectTaken = new TileSubject[row][column];
    }

    /**
     * Used to set {@link #tileSubjectTaken}
     * @param tileSubjectTaken value to be set at the {@link #tileSubjectTaken} field
     * @see BookShelf
     * @see #tileSubjectTaken
     */
    public void setTileSubjectTaken(TileSubject[][] tileSubjectTaken) {
        this.tileSubjectTaken = tileSubjectTaken;
    }

    /**
     * This method return the {@link #tileSubjectTaken BookShelf matrix} contained inside the {@link BookShelf}.
     *
     * @return {@link #tileSubjectTaken} field
     * @apiNote Type of matrix returned is {@link TileSubject} and not {@link TileType},
     * use {@link #toTileTypeMatrix()} method to obtain the same matrix as the one contained inside {@link #tileSubjectTaken}
     * but with {@link TileType} entry.
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
     * @param row the row index in {@link #tileSubjectTaken} to which retrieve the {@link TileType}
     * @param column the column index in {@link #tileSubjectTaken} to which retrieve the {@link TileType}
     * @return {@link TileType} of {@link TileSubject} stored at <code>{@link #tileSubjectTaken}[{@link #row}][{@link #column}]</code>,
     *         {@code null} if the cells doesn't contain an element
     * @apiNote Please note that no {@code Exception} will be thrown if the cell does not contain an element,
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

    public void addTileSubjectTaken(TileSubject[][] taken, int column){
        if(isFull())
            return;

        for(int i = 0; i < taken.length; i++){

        }
    }
}
