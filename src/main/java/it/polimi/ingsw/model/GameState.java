package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 04/04/2023
 */

public enum GameState implements Serializable {

    INIT,
    MID,
    FINAL,
    SUSPENDED,
    END;

    private static  final long serialVersionUID = 9582348944623L;
}
