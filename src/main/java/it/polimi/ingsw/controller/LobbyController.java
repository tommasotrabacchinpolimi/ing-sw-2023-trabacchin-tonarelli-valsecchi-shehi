package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.AlreadyInGameException;
import it.polimi.ingsw.controller.exceptions.AlreadyTakenNicknameException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.State;
import it.polimi.ingsw.net.*;
import it.polimi.ingsw.net_alternative.OnServerConnectionLostListener;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>This class is used to assign every user that wants to play MyShelfie game inside a different match.</p>
 * <p>More specifically, it handles different situation:
 * <ul>
 *     <li>The user wants to {@linkplain #joinGame(ClientInterface, String) join an existing match}</li>
 *     <li>The user tries to {@linkplain #createGame(ClientInterface, String, int) create a new game}</li>
 *     <li>The user {@linkplain #onConnectionLost(ClientInterface) has been disconnected}</li>
 *     <li>After connection lost a user is trying to {@linkplain #reconnectPlayer(ClientInterface, String)
 *     reconnecting to a game}</li>
 *     <li>Verify if a user {@linkplain #nop() is still connected}</li>
 *     <li>A match has finished so players can join or create new match</li>
 * </ul>
 * </p>
 *
 * @see ClientInterface
 * @see UserAccepter
 * @see OnConnectionLostListener
 * @see LobbyController
 * @see Controller
 * @see Dispatcher
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 4.0
 * @since 26/04/2023
 */
public class LobbyController
        implements OnServerConnectionLostListener, LobbyControllerInterface{
    /**
     * <p>Holds a match between different controllers and all the users that are assigned to that controller.</p>
     * <p>This fields is used to retrieve all the {@linkplain ClientInterface users} at once that are assigned to a specific
     * {@linkplain Controller controller} (that is used as a "key")</p>
     */
    private final Map<Controller, List<ClientInterface>> controllerViewMap = new ConcurrentHashMap<>();

    /**
     * <p>Holds a match between a {@linkplain ClientInterface user} and the corresponding controller that is handling
     * the game in which he is assigned</p>
     * <p>This fields is used to retrieve faster the {@linkplain Controller controller} given a specific
     * {@linkplain ClientInterface user} (that is used as "key")</p>
     */
    private final Map<ClientInterface, Controller> viewControllerMap = new ConcurrentHashMap<>();

    /**
     * Holds all {@linkplain ClientInterface users} that are not assigned to a game match
     */
    private final List<ClientInterface> waitingUsers = new ArrayList<>();

    /**
     * <p>Holds a match between a {@linkplain ClientInterface user} and it's nickname.</p>
     * <p>This field is useful when a new {@linkplain ClientInterface client} tries to join or create a new game match
     * but the nickname that is using is already taken by someone else</p>
     * <p>The {@linkplain ClientInterface client} associated to a particular nickname is obtained using this last as a
     * unique ("key") value</p>
     *
     * @see Player
     */
    private final Map<ClientInterface, String> viewToNicknameMap = new ConcurrentHashMap<>();

    /**
     * <p>Holds a match between a {@linkplain ClientInterface user} and it's nickname</p>
     * <p>This field is useful when a new {@linkplain ClientInterface client} tries to join or create a new game match
     * but the nickname that is using is already taken by someone else</p>
     * <p>The nickname associated to a specific {@linkplain ClientInterface client} is obtained using this last as a
     * unique ("key") value</p>
     *
     * @see Player
     */
    private final Map<String, ClientInterface> nicknameToViewMap = new ConcurrentHashMap<>();

    /**
     * <p>Holds all nicknames of players that are disconnected (probably due to connection problems) from the game but
     * the game match in which they were assigned is not ended yet</p>
     */
    private final List<String> disconnectedButInGame = new ArrayList<>();

    /**
     * Reference to the {@linkplain Dispatcher dispatcher} that manage the requests coming from the network and
     * {@linkplain Dispatcher#invoke(Object, Method, Object[]) calls} the appropriate method
     *
     * @see Dispatcher
     * @see Dispatcher#invoke(Object proxy, Method method, Object[] args)
     */
    private ControllerDispatcher dispatcher;

    /**
     * Binds a {@linkplain Dispatcher dispatcher} instance to the calling object
     *
     * @param dispatcher the dispatcher to be bound
     */
    public void setDispatcher(ControllerDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }






    /**
     * {@inheritDoc}
     * <p>More precisely, it uses a "{@linkplain ClientInterface user interface}" and a {@linkplain Player nickname}
     * to check if the nickname was already taken by someone; based on this two values:
     * <ul>
     *     <li>If the nickname has never been used by a client the user is added to the game without problems</li>
     *     <li>If the nickname was already taken by someone:
     *          <ul>
     *              <li>The player with that nickname is reconnected to the game if and only if the player was disconnected</li>
     *              <li>An error occurs if the nickname was already taken by someone and the player is
     *                  still connected or the player is not assigned to a game but the name was already chosen by someone</li>
     *          </ul>
     *     </li>
     * </ul>
     * </p>
     *
     * @param user {@inheritDoc}
     * @param nickname {@inheritDoc}
     * @throws AlreadyTakenNicknameException if someone already took the nickname
     * @throws AlreadyInGameException if a player with the same nickname is assigned to a match that is still running
     *
     * @see Player
     */
    @Override
    public synchronized void joinGame(ClientInterface user, String nickname)
            throws AlreadyTakenNicknameException, AlreadyInGameException {

        if(!viewToNicknameMap.containsValue(nickname)){
            addPlayerToGame(user, nickname);
        } else if (user != nicknameToViewMap.get(nickname)) {
            if(disconnectedButInGame.contains(nickname)){
                reconnectPlayer(user, nickname);
            } else {
                //notify view
                AlreadyTakenNicknameException e = new AlreadyTakenNicknameException();
                user.onException(e);
                throw e;
            }
        } else {
            if(viewControllerMap.containsKey(user)){
                AlreadyInGameException e = new AlreadyInGameException();
                user.onException(e);
                throw e;
            } else{
                addPlayerToGame(user, nickname);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>More precisely, it uses a "{@linkplain ClientInterface user interface}", a {@linkplain Player nickname}
     * and a {@code integer} value to check if the nickname was already taken by someone or the max number of player for
     * that game is already reached. Based on this three values:
     * <ul>
     *     <li>If the nickname has never been used by a client the client can
     *         {@linkplain #createNewGame(ClientInterface, String, int) create a new game}</li>
     *     <li>If the nickname was already taken by someone the client can't create a game if the match in which it was
     *         assigned is still running</li>
     * </ul>
     * </p>
     *
     * @param user {@inheritDoc}
     * @param nickname {@inheritDoc}
     * @param numberOfPlayer {@inheritDoc}
     *
     * @throws AlreadyTakenNicknameException if someone already took the nickname
     * @throws AlreadyInGameException if a player with the same nickname is assigned to a match that is still running
     * @throws FileNotFoundException if a file that is requested to build the game is not found
     */
    @Override
    public synchronized void createGame(ClientInterface user, String nickname, int numberOfPlayer)
            throws AlreadyTakenNicknameException, AlreadyInGameException, FileNotFoundException {

        if(!viewToNicknameMap.containsValue(nickname)){
            createNewGame(user, nickname, numberOfPlayer);
        } else if (user != nicknameToViewMap.get(nickname)) {
            if(disconnectedButInGame.contains(nickname)){
                AlreadyInGameException e = new AlreadyInGameException();
                user.onException(e);
                throw e;
            } else {
                //metodo nella textclient interface che dice al client che il nome è gia stato preso
                //notify to view
                AlreadyTakenNicknameException e = new AlreadyTakenNicknameException();
                user.onException(e);
                throw e;
            }
        } else {
            if(viewControllerMap.containsKey(user)){
                AlreadyInGameException e = new AlreadyInGameException();
                user.onException(e);
                throw e;
            } else{
                createNewGame(user, nickname, numberOfPlayer);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws RemoteException {@inheritDoc}
     */
    @Override
    public void nop() {

    }

    /**
     * <p>This method is used to clean every bind between
     *      <ul>
     *          <li>a {@linkplain ClientInterface client} and a {@linkplain Controller controller}</li>
     *          <li>{@linkplain #dispatcher dispatcher} and a {@linkplain  Controller controller}</li>
     *      </ul>
     * </p>
     *
     * @param controller the controller of the game that is finished
     */
    public synchronized void onEndGame(Controller controller) {
        if(controllerViewMap.containsKey(controller)) {
            if(controller.getState().getGameState() == GameState.END) {
                List<ClientInterface> list =  controllerViewMap.get(controller);
                controllerViewMap.remove(controller);

                for(ClientInterface user: list){
                    viewControllerMap.remove(user);
                    dispatcher.removeController(user, controller);
                }
            }
        }
    }

    /**
     * <p>This method retrieve the first {@linkplain Controller controller} that is bound to a {@linkplain State game}
     * with available spaces for player</p>
     *
     * @return <ul><li>first {@linkplain Controller controller instance} that has a game with available spaces for player.</li>
     *     <li>{@code null} if there is no game with available player "position"</li>
     * </ul>
     */
    private Controller firstGameAvailable(){
        for(Controller c : controllerViewMap.keySet()){
            if(c.getState().getPlayers().size() < c.getState().getPlayersNumber()){
                return c;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     * <p>In that case if the player was assigned to a game that is still running the player state is set to
     * "{@linkplain PlayerState#DISCONNECTED disconnected}" and the client is added to a
     * "{@linkplain #disconnectedButInGame disconnected player list}" until he connects again.</p>
     * <p>In addition if the player was in the "{@linkplain #waitingUsers waiting list}" to play in a match is removed
     * from this one</p>
     *
     * @param user {@inheritDoc}
     *
     * @see Controller#onConnectionLost(ClientInterface client)
     * @see PlayerState
     * @see Player#setPlayerState(PlayerState playerState)
     * @see OnConnectionLostListener#onConnectionLost(RemoteInterface)
     */
    @Override
    public synchronized void onConnectionLost(ClientInterface user) {
        System.out.println("connection lost");

        if(viewControllerMap.get(user) != null) {
            viewControllerMap.get(user).onConnectionLost(user);
            disconnectedButInGame.add(viewToNicknameMap.get(user));
        }

        waitingUsers.removeIf(u -> u.equals(user));
    }

    /**
     * <p>This method removes the binding between each {@linkplain ClientInterface user} and the corresponding
     * {@link Controller controller} of the game in which the client was assigned.</p>
     *
     * @param user the {@linkplain ClientInterface client} to be removed from the correspondences
     *
     * @see #viewToNicknameMap
     * @see #viewControllerMap
     * @see #nicknameToViewMap
     * @see #controllerViewMap
     * @see #dispatcher
     */
    public synchronized void onQuitGame(ClientInterface user){
        Controller c = viewControllerMap.get(user);
        if(c != null) {
            c.quitGame(user);
            controllerViewMap.get(c).remove(user);
            dispatcher.removeController(user, c);
        }
        viewControllerMap.remove(user);
        viewToNicknameMap.remove(user);

    }

    /**
     * <p>This method adds a new {@linkplain Player player} to an existing game.</p>
     * <p>More specifically if:
     * <ul>
     *     <li>there aren't match in which the player can be assigned, the user is insert in
     *         a {@linkplain #waitingUsers waiting list}.</li>
     *     <li>there is an available match, the player is assigned to that and the correct correspondences are created</li>
     * </ul>
     * </p>
     *
     * @apiNote <p>Please note that this method doesn't handle errors due to unique nicknames; the only reason
     * that a player is not added to a match calling this method are:
     * <ul>
     *     <li>There aren't available games at all</li>
     *     <li>There are instance of games match, but these have already all the maximum player that can hold</li>
     * </ul>
     * </p>
     *
     * @param user the {@linkplain ClientInterface client} that needs to be added
     * @param nickname the {@linkplain Player player nickname} with which the client wants to register in a match
     *
     * @see #viewToNicknameMap
     * @see #nicknameToViewMap
     * @see #controllerViewMap
     * @see #viewControllerMap
     * @see #dispatcher
     * @see State
     * @see Player
     */
    private void addPlayerToGame(ClientInterface user, String nickname) {
        Controller c = firstGameAvailable();

        if(c == null){ //se non ci sono partite disponibili o sono gia tutte piene metto lo user nella waiting
            waitingUsers.add(user);
            viewToNicknameMap.put(user, nickname);
            nicknameToViewMap.put(nickname,user);
        } else {
            viewToNicknameMap.put(user, nickname);
            nicknameToViewMap.put(nickname,user);
            controllerViewMap.get(c).add(user);
            viewControllerMap.put(user, c);
            dispatcher.setController(user, c);
            System.out.println(nickname + " joining " + c);
            c.registerPlayer(user, nickname);
        }
    }

    /**
     * <p>This method handles the reconnection of a player to an existing game match.</p>
     *
     * @apiNote <p>Inside this method all correspondences are managed correctly</p>
     *
     * @param user the {@linkplain ClientInterface client} that reconnect to a match
     * @param nickname the {@linkplain Player player nickname} with which the client wants to re-join in a match
     */
    private void reconnectPlayer(ClientInterface user, String nickname) {
        Controller c = viewControllerMap.get(nicknameToViewMap.get(nickname));

        for (Player p : c.getState().getPlayers()) {
            if (p.getVirtualView() == nicknameToViewMap.get(nickname) && p.getPlayerState() == PlayerState.DISCONNECTED) {
                viewControllerMap.remove(nicknameToViewMap.get(nickname)); //remove old ClientInterface associated to that nickname
                viewControllerMap.put(user, c); //added new ClientInterface instance to the map

                controllerViewMap.get(c).remove(nicknameToViewMap.get(nickname));
                controllerViewMap.get(c).add(user);

                viewToNicknameMap.remove(nicknameToViewMap.get(nickname));
                viewToNicknameMap.put(user, nickname);

                nicknameToViewMap.remove(nickname);
                nicknameToViewMap.put(nickname, user);

                disconnectedButInGame.remove(nickname);

                dispatcher.setController(user, c);

                c.registerPlayer(user, nickname);
            }
        }
    }

    /**
     * <p>This method is used to create a new {@linkplain State game match} and create bindings between:
     * <ul>
     *     <li>{@linkplain ClientInterface the client} that is creating a new game match</li>
     *     <li>{@linkplain Controller the controller} that will manage the game match</li>
     *     <li>{@linkplain #waitingUsers waiting users}that will be assigned to tne new game match</li>
     * </ul>
     * </p>
     *
     * @apiNote users inside the {@linkplain #waitingUsers waiting list} are added chronologically to new games match
     * (first player inserted in the waiting list will be assigned to the first available game match).
     *
     * @param user the {@linkplain ClientInterface client} that is creating a new game match
     * @param nickname the {@linkplain Player player nickname} that is creating the match
     * @param numberOfPlayer the max {@linkplain it.polimi.ingsw.model.State number of player} that can join the game
     *
     * @throws FileNotFoundException if a file that is requested to build the game is not found
     *
     * @see Controller
     * @see Controller#registerPlayer(ClientInterface user, String nickname)
     * @see Controller#setNumberPlayers(int numberOfPlayer)
     */
    private void createNewGame(ClientInterface user, String nickname, int numberOfPlayer) throws FileNotFoundException {
        State state = new State();
        Controller controller = new Controller(state, this);
        List<ClientInterface> list = new ArrayList<>();

        viewToNicknameMap.put(user, nickname);
        nicknameToViewMap.put(nickname, user);
        dispatcher.setController(user, controller);

        System.out.println(nickname + " joining " + controller);
        controller.setNumberPlayers(numberOfPlayer);
        controller.registerPlayer(user, nickname);

        list.add(user);

        //If there are players inside the waitingUsers list, they are added to the new game match created
        if(waitingUsers.size() != 0) {
            for(int i = 1; i < numberOfPlayer; i++){
                if(waitingUsers.size() != 0){
                    ClientInterface u = waitingUsers.remove(0);
                    list.add(u);
                    dispatcher.setController(user, controller);
                    System.out.println(nickname + " joining " + controller);
                    controller.registerPlayer(u, viewToNicknameMap.get(u));
                }
            }
        }

        controllerViewMap.put(controller, list);

        for(ClientInterface u : list){
            viewControllerMap.put(u, controller);
        }
    }

}