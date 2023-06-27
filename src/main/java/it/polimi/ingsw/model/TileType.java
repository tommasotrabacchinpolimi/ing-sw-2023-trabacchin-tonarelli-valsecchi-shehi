package it.polimi.ingsw.model;

/**
 * This class represent the TileType associated with each Tile.<br>
 * Please refer to
 * <a href="https://www.craniocreations.it/storage/media/product_downloads/48/538/MyShelfie_Ruleboo_ENG_lowres_new.pdf">
 *     Official RuleBook
 * </a> to understand which type of Tile there are in the game.
 *
 * @see TileSubject
 *
 * <p></p>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 08/03/2023
 * </p>
 */
public enum TileType {
    CAT("C",0),
    BOOK("B",1),
    GAME("G",2),
    FRAME("F",3),
    TROPHY("T",4),
    PLANT("P",5);

    /**
     * This String represents the TileType with only one letter, that correspond to the first letter of each Tile type name
     */

    private final String abbreviation;
    /**
     * An arbitrary int value representing the TileType
     */
    private final int value;

    /**
     * Constructor that sets the enum to the correct value
     *
     * @param abbreviation string that represents the single letter for each Tile type
     * @param value int that represents the TileType
     */
    TileType(String abbreviation, int value) {
        this.abbreviation = abbreviation;
        this.value = value;
    }

    /**
     * This method is used to get the first letter of each Tile type name
     *
     * @return first letter of each Tile type name
     *
     * @see #abbreviation
     */
    public String toAbbreviationString(){
        return this.abbreviation;
    }

    /**
     *
     * @return the int representing the Tile Type
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the name of each Tile type
     *
     * @return name of each TileType
     * @see TileType
     */
    @Override
    public String toString() {
        return this.name();
    }
}
