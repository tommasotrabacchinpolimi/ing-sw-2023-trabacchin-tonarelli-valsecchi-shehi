package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.net_alternative.servermessages.*;
import it.polimi.ingsw.utils.Coordinate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketImpl implements ServerInterface, Runnable {


    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final ClientDispatcherInterface clientDispatcher;

    private final OnClientConnectionLostListener clientConnectionLostListener;

    private boolean OPEN = true;

    public ServerSocketImpl(Socket socket, ClientDispatcherInterface clientDispatcher, OnClientConnectionLostListener clientConnectionLostListener) throws IOException {
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.clientDispatcher = clientDispatcher;
        this.clientConnectionLostListener = clientConnectionLostListener;
    }
    @Override
    public synchronized void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage = new DragTilesToBookShelfNetMessage(chosenTiles, chosenColumn);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(dragTilesToBookShelfNetMessage);
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void joinGame(String nickname) {
        JoinGameNetMessage joinGameNetMessage = new JoinGameNetMessage(nickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(joinGameNetMessage);
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void createGame(String nickname, int numberOfPlayer) {
        CreateGameNetMessage createGameNetMessage = new CreateGameNetMessage(nickname, numberOfPlayer);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(createGameNetMessage);
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void quitGame() {
        QuitGameNetMessage quitGameNetMessage = new QuitGameNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(quitGameNetMessage);
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void sentMessage(String text, String[] receiversNickname) {
        SentMessageNetMessage sentMessageNetMessage = new SentMessageNetMessage(text, receiversNickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(sentMessageNetMessage);
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void nop() {
        NopNetMessage nopNetMessage = new NopNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(nopNetMessage);
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        while (true) {
            try {
                ClientMessage message = (ClientMessage) ois.readObject();
                executorService.submit(() -> message.dispatch(clientDispatcher));
            } catch (Exception e) {
                synchronized (this) {
                    OPEN = false;
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
