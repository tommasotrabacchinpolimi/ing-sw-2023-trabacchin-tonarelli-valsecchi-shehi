package it.polimi.ingsw.net_alternative;


import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.clientmessage.*;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The RmiClientImpl class is the implementation of the RmiClientInterface interface.
 * It handles remote calls from the server to the client.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class RmiClientImpl extends UnicastRemoteObject implements RmiClientInterface{

    private final ClientDispatcherInterface clientDispatcherInterface;
    private final ExecutorService executorService;
    /**
     * Constructs a new RmiClientImpl object with the specified clientDispatcherInterface.
     *
     * @param clientDispatcherInterface the client dispatcher interface used for message dispatching
     * @throws RemoteException if a remote error occurs
     */
    public RmiClientImpl(ClientDispatcherInterface clientDispatcherInterface) throws RemoteException{
        super();
        this.clientDispatcherInterface = clientDispatcherInterface;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Performs a no-operation (nop) on the client.
     *
     * @throws RemoteException if a remote error occurs
     */
    public void nop() throws RemoteException{
        NopNetMessage nopNetMessage = new NopNetMessage();
        executorService.submit(()->nopNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of achieving a common goal by a player.
     *
     * @param nicknamePlayer   the nickname of the player who achieved the goal
     * @param tiles            the coordinates of the tiles involved in achieving the goal
     * @param numberCommonGoal the number of the common goal achieved
     * @throws RemoteException if a remote error occurs
     */
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) throws RemoteException{
        AchievedCommonGoalNetMessage achievedCommonGoalNetMessage = new AchievedCommonGoalNetMessage(nicknamePlayer, tiles, numberCommonGoal);
        executorService.submit(()->achievedCommonGoalNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of achieving a personal goal by a player.
     *
     * @param nickname the nickname of the player who achieved the goal
     * @param tiles    the coordinates of the tiles involved in achieving the goal
     * @throws RemoteException if a remote error occurs
     */
    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) throws RemoteException{
        AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage = new AchievedPersonalGoalNetMessage(nickname, tiles);
        executorService.submit(()->clientDispatcherInterface.dispatch(achievedPersonalGoalNetMessage));
    }

    /**
     * Handles the event of updating adjacent tiles for a player.
     *
     * @param nickname the nickname of the player
     * @param tiles    the coordinates of the updated adjacent tiles
     * @throws RemoteException if a remote error occurs
     */
    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) throws RemoteException{
        AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage = new AdjacentTilesUpdatedNetMessage(nickname, tiles);
        executorService.submit(()->adjacentTilesUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of assigning a common goal to the players.
     *
     * @param description the description of the assigned common goal
     * @param n           the number of the common goal assigned
     * @param id the common goal id
     * @throws RemoteException if a remote error occurs
     */
    public void onAssignedCommonGoal(String description, int n, String id) throws RemoteException{
        AssignedCommonGoalNetMessage assignedCommonGoalNetMessage = new AssignedCommonGoalNetMessage(description, n, id);
        executorService.submit(()->assignedCommonGoalNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of assigning a personal goal to a player.
     *
     * @param nickname    the nickname of the player
     * @param goalPattern the entry pattern goal assigned to the player
     * @param scoreMap    the score map of the assigned personal goal
     * @throws RemoteException if a remote error occurs
     */
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) throws RemoteException{
        AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage = new AssignedPersonalGoalNetMessage(nickname, goalPattern, scoreMap);
        executorService.submit(()->assignedPersonalGoalNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of refilling the game board.
     *
     * @throws RemoteException if a remote error occurs
     */
    public void onBoardRefilled() throws RemoteException{
        BoardRefilledNetMessage boardRefilledNetMessage = new BoardRefilledNetMessage();
        executorService.submit(()->boardRefilledNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of updating the game board.
     *
     * @param tileSubjects the updated tile subjects of the game board
     * @throws RemoteException if a remote error occurs
     */
    public void onBoardUpdated(TileSubject[][] tileSubjects) throws RemoteException{
        BoardUpdatedNetMessage boardUpdatedNetMessage = new BoardUpdatedNetMessage(tileSubjects);
        executorService.submit(()->boardUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of updating the bookshelf for a player.
     *
     * @param nickname   the nickname of the player
     * @param bookShelf  the updated bookshelf
     * @throws RemoteException if a remote error occurs
     */
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) throws RemoteException{
        BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage = new BookShelfUpdatedNetMessage(nickname, bookShelf);
        executorService.submit(()->bookShelfUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of changing the available score for common goals.
     *
     * @param score             the updated score
     * @param numberOfCommonGoal the number of common goals available
     * @throws RemoteException if a remote error occurs
     */
    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) throws RemoteException{
        ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage = new ChangedCommonGoalAvailableScoreNetMessage(score, numberOfCommonGoal);
        executorService.submit(()->changedCommonGoalAvailableScoreNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of changing the current player.
     *
     * @param nickname the nickname of the new current player
     * @throws RemoteException if a remote error occurs
     */
    public void onCurrentPlayerChangedListener(String nickname) throws RemoteException{
        CurrentPlayerChangedListenerNetMessage changedListenerNetMessage = new CurrentPlayerChangedListenerNetMessage(nickname);
        executorService.submit(()->changedListenerNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of an exception occurring on the server.
     *
     * @param e the exception that occurred
     * @throws RemoteException if a remote error occurs
     */
    public void onException(Exception e) throws RemoteException{
        ExceptionNetMessage exceptionNetMessage = new ExceptionNetMessage(e);
        executorService.submit(()->exceptionNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of updating the last player.
     *
     * @param nicknameLastPlayer the nickname of the last player
     * @throws RemoteException if a remote error occurs
     */
    public void onLastPlayerUpdated(String nicknameLastPlayer) throws RemoteException{
        LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage = new LastPlayerUpdatedNetMessage(nicknameLastPlayer);
        executorService.submit(()->lastPlayerUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of a message being sent from a player to other players.
     *
     * @param nicknameSender     the nickname of the sender
     * @param nicknameReceivers  the nicknames of the receivers
     * @param text               the text of the message
     * @throws RemoteException if a remote error occurs
     */
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) throws RemoteException{
        MessageSentNetMessage messageSentNetMessage = new MessageSentNetMessage(nicknameSender, nicknameReceivers, text);
        executorService.submit(()->messageSentNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of multiple messages being sent from players to other players.
     *
     * @param senderNicknames   the nicknames of the senders
     * @param receiverNicknames the nicknames of the receivers
     * @param texts             the texts of the messages
     * @throws RemoteException if a remote error occurs
     */
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) throws RemoteException{
        MessagesSentNetMessage messagesSentNetMessage = new MessagesSentNetMessage(senderNicknames, receiverNicknames, texts);
        executorService.submit(()->messagesSentNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of a player's state changing.
     *
     * @param nickname     the nickname of the player
     * @param playerState  the new state of the player
     * @throws RemoteException if a remote error occurs
     */
    public void onPlayerStateChanged(String nickname, PlayerState playerState) throws RemoteException{
        PlayerStateChangedNetMessage playerStateChangedNetMessage = new PlayerStateChangedNetMessage(nickname, playerState);
        executorService.submit(()->playerStateChangedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of the players list changing.
     *
     * @param players the updated list of players
     * @throws RemoteException if a remote error occurs
     */
    public void onPlayersListChanged(List<String> players) throws RemoteException{
        PlayersListChangedNetMessage playersListChangedNetMessage = new PlayersListChangedNetMessage(players);
        executorService.submit(()->playersListChangedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of a player's points being updated.
     *
     * @param nickName            the nickname of the player
     * @param scoreAdjacentGoal   the score for adjacent goals
     * @param scoreCommonGoal1    the score for common goal 1
     * @param scoreCommonGoal2    the score for common goal 2
     * @param scoreEndGame        the score for the end game
     * @param scorePersonalGoal   the score for personal goals
     * @throws RemoteException if a remote error occurs
     */
    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) throws RemoteException{
        PointsUpdatedNetMessage pointsUpdatedNetMessage = new PointsUpdatedNetMessage(nickName, scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        executorService.submit(()->pointsUpdatedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of the game state changing.
     *
     * @param gameState the new game state
     * @throws RemoteException if a remote error occurs
     */
    public void onStateChanged(GameState gameState) throws RemoteException{
        StateChangedNetMessage stateChangedNetMessage = new StateChangedNetMessage(gameState);
        executorService.submit(()->stateChangedNetMessage.dispatch(clientDispatcherInterface));
    }

    /**
     * Handles the event of the winner changed
     *
     * @param nickname the nickname of the winner.
     * @throws RemoteException if a remote error occurs
     */
    public void onWinnerChanged(String nickname) throws RemoteException{
        WinnerChangedNetMessage winnerChangedNetMessage = new WinnerChangedNetMessage(nickname);
        executorService.submit(()->winnerChangedNetMessage.dispatch(clientDispatcherInterface));
    }
}
