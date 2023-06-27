package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Defines the game phase
 *
 * <p>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * </p>
 * @version 1.0
 * @since 04/04/2023
 */

public enum GameState implements Serializable {

    /**
     * <p>The match is starting.</p>
     * <p>More precisely: the match has been created by a {@linkplain Player player} and the game is waiting other
     * players to be connected.</p>
     */
    INIT,

    /**
     * <p>The match is proceeding regularly.</p>
     * <p>Each player can perform the action specified in the
     *      <a href="https://www.craniocreations.it/storage/media/product_downloads/48/538/MyShelfie_Ruleboo_ENG_lowres_new.pdf">
     *          Rulebook</a>.
     * </p>
     *
     * @apiNote There are at list 2 players in the game.
     */
    MID,

    /**
     * <p>The match is going to end.</p>
     * <p>More precisely each player is going to do his last turn before the game effectively ends
     * (refer to the
     * <a href="https://www.craniocreations.it/storage/media/product_downloads/48/538/MyShelfie_Ruleboo_ENG_lowres_new.pdf">
     *     Rulebook</a> for more information)</p>.
     */
    FINAL,

    /**
     * <p>The game doesn't proceed and it is "frozen".</p>
     * <p>More precisely in this state there is only one player connected to the match and all other players are
     * disconnected from the server.</p>
     * @apiNote This state is not set if all players have quit the game, but only if they are disconnected for
     * connection issues.
     */
    SUSPENDED,

    /**
     * <p>The match is terminated.</p>
     * <p>Every player connected to the server can now create or join another match.</p>
     */
    END;

    private static final long serialVersionUID = 9582348944623L;
}
