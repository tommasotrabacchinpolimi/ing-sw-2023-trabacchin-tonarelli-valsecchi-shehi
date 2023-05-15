package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Coordinate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class was design to represent a single {@link TileType} entry in a "pattern" for a {@link PersonalGoal PersonalGoal} card.
 * Its usage was then extended to retrieve cards in a {@link BookShelf bookshelf} that satisfy a {@link CommonGoal}
 *
 * @see PersonalGoal
 * @see TileType
 * @see CommonGoal
 * @see TupleCommonGoal
 * @see ShapeCommonGoal
 * @see StairCommonGoal
 * @see LineCommonGoal
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 02/05/2023
 */
public class EntryPatternGoal implements Serializable {
    @Serial
    private static final long serialVersionUID = 73856129837219L;

    private final Coordinate coordinate;

    /**
     * The TileType contained in position specified by the value pair: []
     *
     * @apiNote Default value {@code null}. Once set can't be overwritten
     * @see #getTileType()
     * @see TileType
     * @see #EntryPatternGoal()
     * @see #EntryPatternGoal(int, int, TileType) EntryPatternGoal(row, column, tileType)
     * @see #EntryPatternGoal(int, int, String) EntryPatternGoal(row, column, tileTypeName)
     */
    private final TileType tileType;

    /**
     * Empty parameter constructor that sets the fields of the class to default value.
     *
     * @see EntryPatternGoal#tileType
     */
    public EntryPatternGoal(){
        coordinate = new Coordinate(0,0);
        tileType = null;
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed
     *
     * @param row       the column that would be set to the entry
     * @param column    the column that would be set to the entry
     * @param tileType  the TileType that would be set to the entry
     * @see EntryPatternGoal#tileType
     */
    public EntryPatternGoal(int row, int column, TileType tileType) {
        coordinate = new Coordinate(row,column);
        this.tileType = tileType;
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed
     *
     * @param row the column that would be set to the entry
     * @param column the column that would be set to the entry
     * @param tileTypeName the TileType name that would be converted to the corresponding {@link TileType TileType}
     * @see EntryPatternGoal#tileType
     * @see TileType
     */
    public EntryPatternGoal(int row, int column, String tileTypeName) {
        coordinate = new Coordinate(row,column);
        this.tileType = fromStringToTileType(tileTypeName);
    }

    /**
     * Returns the column referred to the EntryPatternGoal instance
     *
     * @return column field of the class
     */
    public int getColumn() {
        return coordinate.getY();
    } //

    /**
     * Returns the row referred to the EntryPatternGoal instance
     *
     * @return row field of the class
     */
    public int getRow() {
        return coordinate.getX();
    } //

    /**
     * Returns the TileType referred to the EntryPatternGoal instance
     *
     * @return tileType field of the class
     * @see EntryPatternGoal#tileType
     * @see TileType
     */
    public TileType getTileType() {
        return tileType;
    } //

    /**
     * Construct and return an array containing value pair: []
     *
     * @return An array that contains two elements that refers to the position of the entry in the {@link PersonalGoal PersonalGoal} card
     *
     * @apiNote Calling "<code>result</code>" the array that will be returned, it's position are set as follows:
     * <pre>{@code result[0] = row
     * result[1] = column}</pre>
     */
    public int[] getArrayIndexes() {
        return new int[]{coordinate.getX(), coordinate.getY()};
    } //

    /**
     * Overriding toString() default method.
     * {@inheritDoc}
     *
     * @return {@link String} that represent the {@link EntryPatternGoal} class
     *
     * @apiNote Resulting String will be displayed on a single line with as follows:
     * <code>{column, row, tileType}</code>
     */
    @Override
    public String toString() {
        return "EntryPatternGoal{row=" + coordinate.getX() +
                ", column=" + coordinate.getY() +
                ", tileType=" + tileType +
                '}';
    }

    /**
     * Handle exceptions that can occur calling {@link Enum#valueOf(Class, String)} <br>
     * In case that the param is effectively an enum specified inside {@link TileType}
     * that type is returned
     *
     * @param tileTypeName the name of the enum inside {@link TileType}
     * @return {@link TileType} enum type according to the <code>tileTypeName</code> passed as parameter
     */
    private TileType fromStringToTileType(String tileTypeName){
        try {
            return TileType.valueOf(tileTypeName);
        } catch(IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method checks if the {@code object} passed as parameter is equals to the instance that is calling the method
     * {@inheritDoc}
     *
     * @param object the Object to be compared with the {@link EntryPatternGoal} instance
     * @return {@code true} if:
     *          <ul><li>{@code object} parameter is not {@code null}</li>
     *          <li>{@code object} parameter is {@link EntryPatternGoal} type</li>
     *          <li>{@code object} has fields value equals to {@code this} istance of {@link EntryPatternGoal}</li></ul>
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        EntryPatternGoal that = (EntryPatternGoal) object;

        if(this.tileType == null)
            return (coordinate.getY() == that.getColumn() && coordinate.getX() == that.getRow());

        return (coordinate.getY() == that.getColumn() && coordinate.getX() == that.getRow() && tileType.equals(that.tileType));
    }

    /**
     * {@inheritDoc}
     *
     * @return hash code for this objects
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinate.getY(), coordinate.getX(), tileType);
    }
}
