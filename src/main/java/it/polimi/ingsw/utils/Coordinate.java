package it.polimi.ingsw.utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Represent the {@linkplain #x row} and {@linkplain #y column} index for a tile item in the
 * {@linkplain it.polimi.ingsw.model.Board board} or in the {@linkplain it.polimi.ingsw.model.BookShelf bookshelf}</p>
 *
 * @see it.polimi.ingsw.model.TileSubject
 * @see it.polimi.ingsw.model.Board
 * @see it.polimi.ingsw.model.BookShelf
 * @see InputCheck
 * @see it.polimi.ingsw.model.EntryPatternGoal
 * @see it.polimi.ingsw.controller.MidGameManager
 */
public class Coordinate implements Serializable {
    @Serial
    private static final long serialVersionUID = 27112003L;

    /**
     * <p>Row index for a tile in the board or in the bookshelf</p>
     */
    private int x;

    /**
     * <p>Column index fot a tile in the board or in the bookshelf</p>
     */
    private int y;

    /**
     * <p>Constructor that sets the {@linkplain #x row} and the {@linkplain #y column} for a tile
     * in the board or in the bookshelf as specified</p>
     *
     * @param x row index of the tile
     * @param y column index of the tile
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieve the tile's row index in the bookshelf or in the board
     *
     * @return the tile's column index
     */
    public int getX() {
        return x;
    }

    /**
     * Sets tile's row index in the bookshelf or in the board as specified
     *
     * @param x the tile's row index to be set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retrieve the tile's column index in the bookshelf or in the board
     *
     * @return the tile's column index
     */
    public int getY() {
        return y;
    }

    /**
     * Sets tile's column index in the bookshelf or in the board as specified
     *
     * @param y the tile's column index to be set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * <p>Returns a string representation of the object.</p>
     * @apiNote More precisely the representation of
     * {@linkplain Coordinate this object} is a tuple of two values
     * that respectively represent the tile's
     * {@linkplain #x row} and {@linkplain #y column}
     * inside a bookshelf or in the board
     *
     * @return a tuple with the following meaning:
     * <blockquote><pre>({@linkplain #x row}, {@linkplain #y column})</pre></blockquote>
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Verify if the given row and column index given are equals
     * to the ones stored in the instance calling
     *
     * @param x the row index to be verified
     * @param y the column index to be verified
     *
     * @return {@code true} if the given row and column index
     * matches the ones stored in the instance, {@code false} otherwise
     */
    public boolean equalsToCoordinates(int x, int y) {
        return this.getX() == x && this.getY() == y;
    }

    /**
     * <p>Indicates whether some other object ({@code o}) is "logically equal to" the caller.</p>
     *
     * @apiNote Two {@linkplain Coordinate coordinate} object are logically equals if
     * both have the same {@linkplain #x row} and {@linkplain #y column} index
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code o} argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    /**
     * <p>Returns a hash code value representing the object
     * that is unique during the same execution of a Java application</p>
     *
     * @return hash code value unique for an instance of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
