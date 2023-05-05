package it.polimi.ingsw.net;

/**
 * <p>This interface used to manage and notice if a potential user can play</p>
 * <p>More precisely actions managed are:
 * <ul>
 *     <li>
 *         {@linkplain #acceptUser(User) accept users}
 *     </li>
 *     <li>
 *         {@linkplain #registerConnectionDownListener(User) to notice a loss connection}
 *     </li>
 * </ul>
 * </p>
 *
 *
 * @param <R> the type of the remote object
 *
 * @see RemoteInterface
 * @see User
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0, 27/04/2023
 */
public interface UserAccepter<R extends RemoteInterface> {

    /**
     * <p>This method is used to judge if a {@linkplain User user} is accepted to play</p>
     * @param user the player to be judged
     * @return True if the user can play, False otherwise
     */
    boolean acceptUser(User<R> user);

    /**
     * <p>This method is used to notice a loss connection of a {@linkplain User user}</p>
     * @param user the player to be checked
     */
    void registerConnectionDownListener(User<R> user);
}
