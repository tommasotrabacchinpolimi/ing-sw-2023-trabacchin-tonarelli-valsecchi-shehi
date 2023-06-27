package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;

import java.io.Closeable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * The `ServerRmiAdapter` class is an implementation of the `ClientInterface` and `Closeable` interfaces.
 * It acts as an adapter between the RMI-based client and the server, allowing communication between them.
 * This class handles RMI-specific operations and delegates the actual handling of messages to the `RmiClientInterface`.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */


public class ServerRmiAdapter implements ClientInterface {

    private final RmiClientInterface rmiClient;

    private final OnServerConnectionLostListener serverConnectionLostListener;

    private boolean OPEN;




    /**
     * Constructor for ServerRmiAdapter.
     * @param rmiClient the RMI client interface.
     * @param serverConnectionLostListener the listener for server connection loss.
     */
    public ServerRmiAdapter(RmiClientInterface rmiClient, OnServerConnectionLostListener serverConnectionLostListener) {
        this.rmiClient = rmiClient;
        this.serverConnectionLostListener = serverConnectionLostListener;
        this.OPEN = true;
    }

    /**
     * Sends a "no-operation" signal to the server.
     */

    @Override
    public synchronized void nop() {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.nop();
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }

        }

    }

    /**
     * Notifies the server that a player has achieved a common goal.
     * @param nicknamePlayer the player's nickname.
     * @param tiles the tiles involved.
     * @param numberCommonGoal the number of the common goal.
     */

    @Override
    public synchronized void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.onAchievedCommonGoal(nicknamePlayer, tiles, numberCommonGoal);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that a player has achieved a personal goal.
     * @param nickname the player's nickname.
     * @param tiles the tiles involved.
     */
    @Override
    public synchronized void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.onAchievedPersonalGoal(nickname, tiles);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that the adjacent tiles of a player have been updated.
     * @param nickname the player's nickname.
     * @param tiles the list of updated tiles.
     */

    @Override
    public synchronized void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onAdjacentTilesUpdated(nickname, tiles);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }

    }

    /**
     * Notifies the server that a common goal has been assigned.
     * @param description the description of the common goal.
     * @param n the number of the common goal.
     */
    @Override
    public synchronized void onAssignedCommonGoal(String description, int n) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onAssignedCommonGoal(description, n);
        }catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }

    }

    /**
     * Notifies the server that a personal goal has been assigned to a player.
     * @param nickname the player's nickname.
     * @param goalPattern the entry pattern goal.
     * @param scoreMap the score map.
     */
    @Override
    public synchronized void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.onAssignedPersonalGoal(nickname, goalPattern, scoreMap);
        }catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }
    /**
     * Notifies the server that the board has been refilled.
     */

    @Override
    public synchronized void onBoardRefilled() {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.onBoardRefilled();
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that the board has been updated.
     * @param tileSubjects the updated tile subjects.
     */
    @Override
    public synchronized void onBoardUpdated(TileSubject[][] tileSubjects) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onBoardUpdated(tileSubjects);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that a player's bookshelf has been updated.
     * @param nickname the player's nickname.
     * @param bookShelf the updated bookshelf.
     */
    @Override
    public synchronized void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.onBookShelfUpdated(nickname, bookShelf);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that the available score for a common goal has changed.
     * @param score the new score.
     * @param numberOfCommonGoal the number of the common goal.
     */
    @Override
    public synchronized void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onChangedCommonGoalAvailableScore(score, numberOfCommonGoal);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that the current player has changed.
     * @param nickname the nickname of the new current player.
     */
    @Override
    public synchronized void onCurrentPlayerChangedListener(String nickname) {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.onCurrentPlayerChangedListener(nickname);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     *  Notifies the server of an exception that occurred on the client side
     *  providing the exception object.
     * @param e Exception occurred
     */

    @Override
    public synchronized void onException(Exception e) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onException(e);
        } catch(RemoteException e1) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }


    /**
     * Notifies the server that the last player has been updated.
     * @param nicknameLastPlayer the nickname of the last player.
     */
    @Override
    public synchronized void onLastPlayerUpdated(String nicknameLastPlayer) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onLastPlayerUpdated(nicknameLastPlayer);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that a message has been sent.
     * @param nicknameSender the nickname of the message sender.
     * @param nicknameReceivers the nicknames of the message receivers.
     * @param text the content of the message.
     */
    @Override
    public synchronized void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onMessageSent(nicknameSender, nicknameReceivers, text);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that multiple messages have been sent.
     * @param senderNicknames the nicknames of the message senders.
     * @param receiverNicknames the nicknames of the message receivers.
     * @param texts the contents of the messages.
     */
    @Override
    public synchronized void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onMessagesSentUpdate(senderNicknames, receiverNicknames, texts);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }


    /**
     * Notifies the server that a player's state has changed.
     * @param nickname the player's nickname.
     * @param playerState the new player state.
     */
    @Override
    public synchronized void onPlayerStateChanged(String nickname, PlayerState playerState) {
        try{
            if(!OPEN) {
                return;
            }
            rmiClient.onPlayerStateChanged(nickname, playerState);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }
    /**
     * Notifies the server that the list of players has changed.
     * @param players the updated list of players.
     */
    @Override
    public synchronized void onPlayersListChanged(List<String> players) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onPlayersListChanged(players);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * Notifies the server that the points have been updated for a player.
     * @param nickName the nickname of the player.
     * @param scoreAdjacentGoal the score for the adjacent goal.
     * @param scoreCommonGoal1 the score for the first common goal.
     * @param scoreCommonGoal2 the score for the second common goal.
     * @param scoreEndGame the score for the end game.
     * @param scorePersonalGoal the score for the personal goal.
     */
    @Override
    public synchronized void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onPointsUpdated(nickName, scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }


    /**
     * Notifies the server that the game state has changed.
     * @param gameState the new game state.
     */

    @Override
    public synchronized void onStateChanged(GameState gameState) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onStateChanged(gameState);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

    /**
     * notifies the server that the winner changed
     *
     * @param nickname Nickname of the winner
     */
    @Override
    public synchronized void onWinnerChanged(String nickname) {
        try {
            if(!OPEN) {
                return;
            }
            rmiClient.onWinnerChanged(nickname);
        } catch(RemoteException e) {
            if(OPEN) {
                OPEN = false;
                serverConnectionLostListener.onConnectionLost(this);
            }
        }
    }

}
