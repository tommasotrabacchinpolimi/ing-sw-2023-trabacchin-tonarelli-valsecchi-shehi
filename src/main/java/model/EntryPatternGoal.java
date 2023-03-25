package model;

import java.io.Serializable;

/**
 * This class represent a single entry to construct the "pattern" for a {@link PersonalGoal PersonalGoal} card.
 *
 * @author Emanuele Valsecchi
 * @version 1.0, 15/04/23
 * @see PersonalGoal
 * @see TileType
 */
public class EntryPatternGoal implements Serializable {
    /**
     * The column in which the entry is inserted
     *
     * @see EntryPatternGoal#getColumn() getColumn()
     */
    private final int column;
    /**
     * The row in which the entry is inserted
     *
     * @see EntryPatternGoal#getRow() getRow()
     */
    private final int row;
    /**
     * The TileType contained in position specified by the value pair: [{@link EntryPatternGoal#row row}, {@link EntryPatternGoal#column column}]
     *
     * @see EntryPatternGoal#getTileType()
     * @see TileType
     */
    private final TileType tileType;

    /**
     * Empty parameter constructor that sets the fields of the class to:
     * <pre>{@code
     * column = 0;
     * row = 0;
     * tileType = null;}
     * </pre>
     *
     * @see EntryPatternGoal#row
     * @see EntryPatternGoal#column
     * @see EntryPatternGoal#tileType
     */
    public EntryPatternGoal(){
        column = 0;
        row = 0;
        tileType = null;
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed
     *
     * @param column the column that would be set to the entry
     * @param row the column that would be set to the entry
     * @param tileType the TileType that would be set to the entry
     *
     * @see EntryPatternGoal#row
     * @see EntryPatternGoal#column
     * @see EntryPatternGoal#tileType
     */
    public EntryPatternGoal(int column, int row, TileType tileType) {
        this.column = column;
        this.row = row;
        this.tileType = tileType;
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed
     *
     * @param column the column that would be set to the entry
     * @param row the column that would be set to the entry
     * @param tileTypeName the TileType name that would be converted to the corresponding {@link TileType TileType}
     * @see EntryPatternGoal#row
     * @see EntryPatternGoal#column
     * @see EntryPatternGoal#tileType
     * @see TileType
     */
    public EntryPatternGoal(int column, int row, String tileTypeName) {
        this.column = column;
        this.row = row;
        this.tileType = fromStringToTileType(tileTypeName);
    }

    /**
     * Returns the column referred to the EntryPatternGoal instance
     *
     * @return column field of the class
     * @see EntryPatternGoal#column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the row referred to the EntryPatternGoal instance
     *
     * @return row field of the class
     * @see EntryPatternGoal#row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the TileType referred to the EntryPatternGoal instance
     *
     * @return tileType field of the class
     * @see EntryPatternGoal#tileType
     * @see TileType
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * Construct and return an array containing value pair: [{@link EntryPatternGoal#row row}, {@link EntryPatternGoal#column column}]
     *
     * @return An array that contains two elements that refers to the position of the entry in the {@link PersonalGoal PersonalGoal} card
     *
     * @apiNote Calling "<code>result</code>" the array that will be returned, it's position are set as follows:
     * <pre>{@code result[0] = row
     * result[1] = column}</pre>
     */
    public int[] getArrayIndexes() {
        return new int[]{row, column};
    }

    /**
     * Overriding toString() default method.
     *
     * @return {@link String} that represent the {@link EntryPatternGoal} class
     *
     * @apiNote Resulting String will be displayed on a single line with as follows:
     * "<code>EntryPatternGoal{column, row, tileType}</code>"
     */
    @Override
    public String toString() {
        return "EntryPatternGoal{" +
                "column=" + column +
                ", row=" + row +
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
}
