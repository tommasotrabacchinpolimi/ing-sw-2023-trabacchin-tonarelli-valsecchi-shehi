package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 08/03/2023
 */

public enum BoardSquareType implements Serializable  {
    NO_DOTS,
    THREE_DOTS,
    FOUR_DOTS;

    private static final long serialVersionUID = 983967534L;
}
