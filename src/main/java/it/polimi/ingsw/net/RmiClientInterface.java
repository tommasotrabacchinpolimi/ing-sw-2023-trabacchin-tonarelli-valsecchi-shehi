package it.polimi.ingsw.net;

import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * This interface defines the methods that can be invoked by clients through RMI.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface RmiClientInterface extends Remote {

    /**
     * Method called to perform an empty operation.
     *
     * @throws RemoteException if a remote connection error occurs.
     */
    public void nop() throws RemoteException;

    /**
     * Method called when a common goal is achieved.
     *
     * @param nicknamePlayer   the nickname of the player who achieved the goal.
     * @param tiles            a list of Coordinates representing the tiles involved in the goal.
     * @param numberCommonGoal the number of the completed common goal.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) throws RemoteException;

    /**
     * Method called when a personal goal is achieved.
     *
     * @param nickname the nickname of the player who achieved the goal.
     * @param tiles    a list of Coordinates representing the tiles involved in the goal.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) throws RemoteException;

    /**
     * Method called when adjacent tiles are updated for a player.
     *
     * @param nickname the nickname of the player whose adjacent tiles have been updated.
     * @param tiles    a list of Coordinates representing the updated adjacent tiles.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) throws RemoteException;

    /**
     * Method called when a common goal is assigned.
     *
     * @param description the description of the assigned common goal.
     * @param n           the number of the assigned common goal.
     * @param id the common goal id.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onAssignedCommonGoal(String description, int n, String id) throws RemoteException;

    /**
     * Method called when a personal goal is assigned.
     *
     * @param nickname     the nickname of the player to whom the personal goal is assigned.
     * @param goalPattern  a list of EntryPatternGoal representing the pattern of the personal goal.
     * @param scoreMap     a map associating the number of the common goal with the obtained score.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) throws RemoteException;

    /**
     * Method called when the board is refilled with tiles.
     *
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onBoardRefilled() throws RemoteException;

    /**
     * Method called when the board is updated.
     *
     * @param tileSubjects a matrix of TileSubject representing the updated board.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onBoardUpdated(TileSubject[][] tileSubjects) throws RemoteException;

    /**
     * Method called when the bookshelf is updated for a player.
     *
     * @param nickname   the nickname of the player whose bookshelf has been updated.
     * @param bookShelf  a matrix of TileSubject representing the updated bookshelf.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) throws RemoteException;

    /**
     * Method called when the available score for common goals changes.
     *
     * @param score               the available score for common goals.
     * @param numberOfCommonGoal  the number of available common goals.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) throws RemoteException;

    /**
     * Method called when the current player changes.
     *
     * @param nickname the nickname of the current player.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onCurrentPlayerChangedListener(String nickname) throws RemoteException;

    /**
     * Method called when an exception occurs.
     *
     * @param playerCause the player that caused the exception
     * @param e the exception that occurred.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onException(String playerCause, Exception e) throws RemoteException;

    /**
     * Method called when the last player is updated.
     *
     * @param nicknameLastPlayer the nickname of the last player.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onLastPlayerUpdated(String nicknameLastPlayer) throws RemoteException;

    /**
     * Method called when a message is sent.
     *
     * @param nicknameSender    the nickname of the message sender.
     * @param nicknameReceivers a list of nicknames of the message receivers.
     * @param text              the text of the message.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) throws RemoteException;

    /**
     * Method called when multiple messages are sent.
     *
     * @param senderNicknames    a list of nicknames of the message senders.
     * @param receiverNicknames  a list of lists of nicknames of the message receivers.
     * @param texts              a list of texts of the messages.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) throws RemoteException;

    /**
     * Method called when the state of a player changes.
     *
     * @param nickname     the nickname of the player.
     * @param playerState  the state of the player.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onPlayerStateChanged(String nickname, PlayerState playerState) throws RemoteException;

    /**
     * Method called when the players list changes.
     *
     * @param players a list of nicknames of the players.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onPlayersListChanged(List<String> players) throws RemoteException;

    /**
     * Method called when the scores of a player are updated.
     *
     * @param nickName           the nickname of the player.
     * @param scoreAdjacentGoal  the score of the adjacent goals.
     * @param scoreCommonGoal1   the score of the first common goal.
     * @param scoreCommonGoal2   the score of the second common goal.
     * @param scoreEndGame       the score of the end game.
     * @param scorePersonalGoal  the score of the personal goal.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) throws RemoteException;

    /**
     * Method called when the game state changes.
     *
     * @param gameState the game state.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onStateChanged(GameState gameState) throws RemoteException;

    /**
     * Method called when the winner of the game changes.
     *
     * @param nickname the nickname of the new game winner.
     * @throws RemoteException if a remote connection error occurs.
     */
    public void onWinnerChanged(String nickname) throws RemoteException;
}
