package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Board;

/**
 * Exception thrown when there are no more tile subjects left in the {@linkplain Board#getBag() bag}.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 * @see Board
 */
public class NoMoreTileSubjectsLeftInTheBag extends RuntimeException {
    /**
     * Default error message
     */
    private static final String DEF_MESSAGE = "No more tile subject in the bag";

    /**
     * Constructs a NoMoreTileSubjectsLeftInTheBag with a default error message.
     */
    public NoMoreTileSubjectsLeftInTheBag() {
        super(DEF_MESSAGE);
    }
}