package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.AlreadyInGameException;
import it.polimi.ingsw.controller.exceptions.AlreadyTakenNicknameException;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;

/**
 * <p>This interface defines the basic actions that a {@linkplain ClientInterface client} can perform when tries to
 * connect in a match game or create a new game.</p>
 *
 * @see LobbyController
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 23/04/2023
 */
public interface LobbyControllerInterface {

    /**
     * <p>This method handles a user's request to join a match.</p>
     *
     * @param view instance of a {@linkplain ClientInterface client} that wants to join a game
     * @param nickname the {@linkplain it.polimi.ingsw.model.Player nickname} of the client that is trying to connect
     *                 at a match
     * @throws Exception if a specific problem of implementing class occurs
     *
     * @see ClientInterface
     * @see it.polimi.ingsw.model.Player
     * @see AlreadyTakenNicknameException
     * @see AlreadyInGameException
     */
    void joinGame(ClientInterface view, String nickname) throws Exception;

    /**
     * <p>This method handles a user's request to create a game.</p>
     *
     * @param view instance of a {@linkplain ClientInterface client} that wants to create a game
     * @param nickname the {@linkplain it.polimi.ingsw.model.Player nickname} of the client that is trying to create
     *                 a game
     * @param numberOfPlayer the max {@linkplain it.polimi.ingsw.model.State number of player} that can join the game
     *
     * @throws Exception if a specific problem of implementing class occurs
     *
     * @see ClientInterface
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.State
     * @see AlreadyTakenNicknameException
     * @see AlreadyInGameException
     * @see FileNotFoundException
     */
    void createGame(ClientInterface view, String nickname, int numberOfPlayer) throws Exception;

    /**
     * <p>This method is used only to test if a {@linkplain ClientInterface client} is still connected.</p>
     *
     */
    void nop();
}
