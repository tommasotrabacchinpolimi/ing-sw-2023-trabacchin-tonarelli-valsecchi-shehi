package it.polimi.ingsw.model;

/**
 * Enumeration that represents the connection state of a player.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 08/04/2023
 */
public enum PlayerState {
    /**
     * Enum constant that indicates that the player is connected.
     */
    CONNECTED,
    /**
     * Enum constant that indicates that the player has quit the game.
     */
    QUITTED,
    /**
     * Enum constant that indicates that the player is disconnected.
     */
    DISCONNECTED;
}
