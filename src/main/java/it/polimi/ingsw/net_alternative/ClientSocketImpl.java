package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.clientmessage.*;
import it.polimi.ingsw.utils.Coordinate;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The {@code ClientSocketImpl} class implements the {@code ClientInterface} and {@code Runnable} interfaces
 * to provide a client-side socket implementation for network communication with the server.
 * It handles sending various network messages to the server and receiving messages from the server.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ClientSocketImpl implements ClientInterface, Runnable {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    private boolean OPEN;

    private final OnServerConnectionLostListener onConnectionLostListener;


    private final ServerDispatcherInterface serverDispatcher;




    /**
     * Constructs a new {@code ClientSocketImpl} instance.
     *
     * @param socket                  The socket connected to the server.
     * @param serverDispatcher        The server dispatcher to handle incoming server messages.
     * @param onConnectionLostListener The listener to handle connection loss with the server.
     * @throws IOException if an I/O error occurs when creating the input and output streams.
     */
    public ClientSocketImpl(Socket socket, ServerDispatcherInterface serverDispatcher, OnServerConnectionLostListener onConnectionLostListener) throws IOException {
        this.oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        this.serverDispatcher = serverDispatcher;
        this.onConnectionLostListener = onConnectionLostListener;
        OPEN = true;
    }

    /**
     * Sends a network message to the server indicating the achievement of a common goal by a player.
     *
     * @param nicknamePlayer  The nickname of the player who achieved the common goal.
     * @param tiles           The coordinates of the tiles involved in achieving the goal.
     * @param numberCommonGoal The number of the common goal achieved.
     */
    @Override
    public synchronized void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        AchievedCommonGoalNetMessage achievedCommonGoalNetMessage = new AchievedCommonGoalNetMessage(nicknamePlayer, tiles, numberCommonGoal);
        try{
            if(!OPEN) {
                return;
            }
            oos.writeObject(achievedCommonGoalNetMessage);
            oos.flush();
            oos.flush();
        }catch(IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }

    }
    /**
     * Sends a network message to the server indicating the achievement of a personal goal by a player.
     *
     * @param nickname The nickname of the player who achieved the personal goal.
     * @param tiles    The coordinates of the tiles involved in achieving the goal.
     */

    @Override
    public synchronized void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {
        AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage = new AchievedPersonalGoalNetMessage(nickname, tiles);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(achievedPersonalGoalNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating the updating of the adjacent tiles.
     *
     * @param nickname nickname of the player who has adjacent tiles updated
     * @param tiles updated tiles
     */


    @Override
    public synchronized void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {
        AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage = new AdjacentTilesUpdatedNetMessage(nickname, tiles);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(adjacentTilesUpdatedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating the assigment of CommonGoal for a player.
     *
     * @param description The description of the CommonGoal being assigned
     * @param n The number of the CommonGoal (every commonGoal has his number)
     * @param id the common goal id
     */
    @Override
    public synchronized void onAssignedCommonGoal(String description, int n, String id) {
        AssignedCommonGoalNetMessage assignedCommonGoalNetMessage = new AssignedCommonGoalNetMessage(description, n, id);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(assignedCommonGoalNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating the Assigment of personal goal to a player.
     *
     * @param nickname nickname of the player who has a PersonalGoal assigned
     * @param goalPattern The PersonalGoal itself
     * @param scoreMap The scoring map of the player
     */
    @Override
    public synchronized void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage = new AssignedPersonalGoalNetMessage(nickname, goalPattern, scoreMap);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(assignedPersonalGoalNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating the refill of the main board.
     */

    @Override
    public synchronized void onBoardRefilled() {
        BoardRefilledNetMessage boardRefilledNetMessage = new BoardRefilledNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(boardRefilledNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating the updating of the board.
     *
     * @param tileSubjects It indicates the board updated
     */
    @Override
    public synchronized void onBoardUpdated(TileSubject[][] tileSubjects) {
        BoardUpdatedNetMessage boardUpdatedNetMessage = new BoardUpdatedNetMessage(tileSubjects);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(boardUpdatedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating the update of the bookshelf of a player.
     *
     * @param nickname Nickname of the player
     * @param bookShelf The object representing the bookshelf of the player
     */
    @Override
    public synchronized void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage = new BookShelfUpdatedNetMessage(nickname, bookShelf);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(bookShelfUpdatedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating a change for the CommonGoalAvailable score.
     *
     * @param score the new available score
     * @param numberOfCommonGoal the number of the CommonGoal to be achieved
     */
    @Override
    public synchronized void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage = new ChangedCommonGoalAvailableScoreNetMessage(score, numberOfCommonGoal);
        try{
            if(!OPEN) {
                return;
            }
            oos.writeObject(changedCommonGoalAvailableScoreNetMessage);
            oos.flush();
        }catch(IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Sends a network message to the server indicating a change of the current player.
     *
     * @param nickname the current player playing
     */
    @Override
    public synchronized void onCurrentPlayerChangedListener(String nickname) {
        CurrentPlayerChangedListenerNetMessage currentPlayerChangedListenerNetMessage = new CurrentPlayerChangedListenerNetMessage(nickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(currentPlayerChangedListenerNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }


    /**
     * Handles the event of exception occurring the server
     *
     * @param e Exception that occurred
     */

    @Override
    public synchronized void onException(String playerCause, Exception e) {
        ExceptionNetMessage exceptionNetMessage = new ExceptionNetMessage(playerCause, e);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(exceptionNetMessage);
            oos.flush();
        } catch (IOException ex) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Handles the event of updating the last player.
     *
     * @param nicknameLastPlayer the nickname of the last player
     */

    @Override
    public synchronized void onLastPlayerUpdated(String nicknameLastPlayer) {
        LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage = new LastPlayerUpdatedNetMessage(nicknameLastPlayer);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(lastPlayerUpdatedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Handles the event of a message being sent from a player to other players.
     *
     * @param nicknameSender     the nickname of the sender
     * @param nicknameReceivers  the nicknames of the receivers
     * @param text               the text of the message
     */
    @Override
    public synchronized void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        MessageSentNetMessage messageSentNetMessage = new MessageSentNetMessage(nicknameSender, nicknameReceivers, text);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(messageSentNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Handles the event of multiple messages being sent from players to other players.
     *
     * @param senderNicknames   the nicknames of the senders
     * @param receiverNicknames the nicknames of the receivers
     * @param texts             the texts of the messages
     */
    @Override
    public synchronized void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        MessagesSentNetMessage messagesSentNetMessage = new MessagesSentNetMessage(senderNicknames, receiverNicknames, texts);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(messagesSentNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Handles the event of a player's state changing.
     *
     * @param nickname     the nickname of the player
     * @param playerState  the new state of the player
     */
    @Override
    public synchronized void onPlayerStateChanged(String nickname, PlayerState playerState) {
        PlayerStateChangedNetMessage playerStateChangedNetMessage = new PlayerStateChangedNetMessage(nickname, playerState);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(playerStateChangedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Handles the event of the players list changing.
     *
     * @param players the updated list of players
     */

    @Override
    public synchronized void onPlayersListChanged(List<String> players) {
        PlayersListChangedNetMessage playersListChangedNetMessage = new PlayersListChangedNetMessage(players);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(playersListChangedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
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
     */

    @Override
    public synchronized void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        PointsUpdatedNetMessage pointsUpdatedNetMessage = new PointsUpdatedNetMessage(nickName, scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(pointsUpdatedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Handles the event of the game state changing.
     *
     * @param gameState the new game state
     */

    @Override
    public synchronized void onStateChanged(GameState gameState) {
        StateChangedNetMessage stateChangedNetMessage = new StateChangedNetMessage(gameState);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(stateChangedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**
     * Handles the event of the winner changed
     *
     * @param nickname the nickname of the winner.
     */
    @Override
    public synchronized void onWinnerChanged(String nickname) {
        WinnerChangedNetMessage winnerChangedNetMessage = new WinnerChangedNetMessage(nickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(winnerChangedNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }
    }

    /**

     * Sends a "nop" message to the server.
     * This method creates a new instance of the "NopNetMessage" class, representing a no-operation message.
     * If the client's connection is open, the message is written to the output stream using the object output stream.
     * The output stream is then flushed to ensure that the message is sent immediately.
     * If an IOException occurs during the write operation, indicating a connection issue,
     * the "OPEN" flag is set to false, the "onConnectionLost" listener is notified,
     * and the exception is printed to the standard error stream.
     */


    @Override
    public void nop() {
        NopNetMessage nopNetMessage = new NopNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(nopNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
        }

    }

    /**

     * Runs the client socket implementation in a separate thread.

     */
    @Override
    public void run() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            try {
                synchronized (this) {
                    if(!OPEN){
                        break;
                    }
                }
                ServerMessage message = (ServerMessage) ois.readObject();
                executorService.submit(()->message.dispatch(serverDispatcher, this));
            } catch (Exception e) {
                synchronized (this) {
                    if(OPEN) {
                        OPEN = false;
                        onConnectionLostListener.onConnectionLost(this);
                    }
                }
            }
        }
    }




}
