package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class used to represent the living room board square type in the {@link Board board}.
 * <ul>
 *  <li>In a 3-player game, the tiles cannot be placed in a {@link #FOUR_DOTS} board square.</li>
 *  <li>In a 2-player game, the tiles cannot be placed in a {@link #FOUR_DOTS} and {@link #THREE_DOTS} board square.</li>
 * </ul>
 *
 * <p>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * </p>
 * @version 1.0
 * @since 08/03/2023
 */

public enum BoardSquareType implements Serializable  {
    /**
     * Enum constant used to represent a board square with zero dots.
     */
    NO_DOTS,
    /**
     * Enum constant used to represent a board square with three dots.
     */
    THREE_DOTS,
    /**
     * Enum constant used to represent a board square with four dots.
     */
    FOUR_DOTS;

    private static final long serialVersionUID = 983967534L;
}
