package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.net_alternative.clientmessage.CurrentPlayerChangedListenerNetMessage;
import it.polimi.ingsw.net_alternative.servermessages.*;
import it.polimi.ingsw.utils.Coordinate;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This class implements the ServerInterface and Runnable interfaces for server-client communication over a socket.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ServerSocketImpl implements ServerInterface, Runnable {


    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final ClientDispatcherInterface clientDispatcher;

    private final OnClientConnectionLostListener clientConnectionLostListener;

    private boolean OPEN = true;


    /**
     * Constructs a ServerSocketImpl instance.
     *
     * @param socket                      the socket for communication.
     * @param clientDispatcher            the client dispatcher.
     * @param clientConnectionLostListener the listener for lost client connections.
     * @throws IOException if an I/O error occurs when creating the input/output streams.
     */
    public ServerSocketImpl(Socket socket, ClientDispatcherInterface clientDispatcher, OnClientConnectionLostListener clientConnectionLostListener) throws IOException {
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.clientDispatcher = clientDispatcher;
        this.clientConnectionLostListener = clientConnectionLostListener;
    }

    /**
     * Sends a message to the server to drag the chosen tiles to the bookshelf.
     *
     * @param chosenTiles  the list of coordinates representing the chosen tiles.
     * @param chosenColumn the column index on the bookshelf where the tiles are dragged.
     */
    @Override
    public synchronized void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage = new DragTilesToBookShelfNetMessage(chosenTiles, chosenColumn);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(dragTilesToBookShelfNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Joins a game with the specified nickname.
     *
     * @param nickname the nickname of the player joining the game.
     */
    @Override
    public synchronized void joinGame(String nickname) {
        JoinGameNetMessage joinGameNetMessage = new JoinGameNetMessage(nickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(joinGameNetMessage);
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Creates a game with the specified nickname and number of players.
     *
     * @param nickname       the nickname of the player creating the game.
     * @param numberOfPlayer the number of players in the game.
     */

    @Override
    public synchronized void createGame(String nickname, int numberOfPlayer) {
        CreateGameNetMessage createGameNetMessage = new CreateGameNetMessage(nickname, numberOfPlayer);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(createGameNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
        }
    }


    /**
     * Quits the current game.
     */
    @Override
    public synchronized void quitGame() {
        QuitGameNetMessage quitGameNetMessage = new QuitGameNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(quitGameNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Sends a message to other players.
     *
     * @param text              the text message to be sent.
     * @param receiversNickname an array of receiver nicknames.
     */
    @Override
    public synchronized void sentMessage(String text, String[] receiversNickname) {
        SentMessageNetMessage sentMessageNetMessage = new SentMessageNetMessage(text, receiversNickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(sentMessageNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Sends a no-operation message.
     */
    @Override
    public synchronized void nop() {
        NopNetMessage nopNetMessage = new NopNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(nopNetMessage);
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
        }
    }


    /**
     * Starts the thread execution and continuously listens for incoming client messages.
     * Each received message is dispatched to the client dispatcher for processing.
     * If an exception occurs or the connection is lost, the client connection lost listener is notified.
     */
    @Override
    public void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            synchronized (this) {
                if(!OPEN) {
                    break;
                }
            }
            try {
                ClientMessage message = (ClientMessage) ois.readObject();
                executorService.submit(() -> message.dispatch(clientDispatcher));
            } catch (Exception e) {
                //e.printStackTrace();
                synchronized (this) {
                    if(OPEN) {
                        OPEN = false;
                        clientConnectionLostListener.onConnectionLost();
                    }
                }

            }

            }
        }



}
