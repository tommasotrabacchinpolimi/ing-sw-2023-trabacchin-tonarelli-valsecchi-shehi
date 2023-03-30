package it.polimi.ingsw.model;

/**
 * This class represent the TileType associated with each Tile.<br>
 * Please refer to
 * <a href="https://www.craniocreations.it/storage/media/product_downloads/48/538/MyShelfie_Ruleboo_ENG_lowres_new.pdf">
 *     Official RuleBook
 * </a> to understand which type of Tile there are in the game.
 *
 * @see TileSubject
 */
public enum TileType {
    CAT("C"), BOOK("B"), GAME("G"), FRAME("F"), TROPHY("T"), PLANT("P");

    /**
     * This String represent the TileType with only one letter, that correspond to the first letter of each Tile type name
     */
    private final String abbreviation;

    /**
     * Constructor that sets the enum to the correct value
     *
     * @param abbreviation string that reppresent the single letter for each Tile type
     */
    TileType(String abbreviation) {
        this.abbreviation = abbreviation;
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
     * Get the name of each Tile type
     * {@inheritDoc}
     *
     * @return name of each TileType
     * @see TileType
     */
    @Override
    public String toString() {
        return this.name();
    }
}
