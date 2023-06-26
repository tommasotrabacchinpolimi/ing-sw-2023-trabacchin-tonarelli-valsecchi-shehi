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

public class ServerSocketImpl implements ServerInterface, Runnable, Closeable {


    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final ClientDispatcherInterface clientDispatcher;

    private final OnClientConnectionLostListener clientConnectionLostListener;

    private final Timer timer;

    private TimerTask timerTask;

    private boolean OPEN = true;

    public ServerSocketImpl(Socket socket, ClientDispatcherInterface clientDispatcher, OnClientConnectionLostListener clientConnectionLostListener) throws IOException {
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.clientDispatcher = clientDispatcher;
        this.clientConnectionLostListener = clientConnectionLostListener;
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                close();
            }
        };
    }
    @Override
    public synchronized void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage = new DragTilesToBookShelfNetMessage(chosenTiles, chosenColumn);
        try {
            if(!OPEN) {
                return;
            }
            System.out.println("sending drag message");
            oos.writeObject(dragTilesToBookShelfNetMessage);
            oos.flush();
            System.out.println("drag message sent");
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
            oos.flush();
            oos.reset();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
        } catch (IOException e) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        timer.schedule(timerTask, 5000);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        while (true) {
            synchronized (this) {
                if(!OPEN) {
                    break;
                }
            }
            try {
                ClientMessage message = (ClientMessage) ois.readObject();
                if(message instanceof NopNetMessage) {
                    timerTask.cancel();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            close();
                        }
                    };
                    timer.schedule(timerTask, 5000);
                }
                executorService.submit(() -> message.dispatch(clientDispatcher));
            } catch (Exception e) {
                synchronized (this) {
                    if(OPEN) {
                        clientConnectionLostListener.onConnectionLost();
                    }
                    OPEN = false;
                    //e.printStackTrace();

                }
                return;
            }
        }
    }


    @Override
    public synchronized void close() {
        if(OPEN) {
            OPEN = false;
            clientConnectionLostListener.onConnectionLost();
        }
    }
}
