package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class ClientRmiAdapter implements ServerInterface, Closeable{

    private final RmiServerInterface rmiServer;

    private final OnClientConnectionLostListener onClientConnectionLostListener;

    private boolean OPEN;

    public ClientRmiAdapter(RmiServerInterface rmiServer, OnClientConnectionLostListener onClientConnectionLostListener) {
        this.rmiServer = rmiServer;
        this.onClientConnectionLostListener = onClientConnectionLostListener;
        this.OPEN = true;
    }
    @Override
    public synchronized void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        try{
            if(!OPEN) {
                return;
            }
            rmiServer.dragTilesToBookShelf(chosenTiles, chosenColumn);
        } catch(RemoteException e) {
            OPEN = false;
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public synchronized void joinGame(String nickname) {
        try {
            if(!OPEN) {
                return;
            }
            rmiServer.joinGame(nickname);
        } catch (RemoteException e) {
            OPEN = false;
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public synchronized void createGame(String nickname, int numberOfPlayer) {
        try {
            if(!OPEN) {
                return;
            }
            rmiServer.createGame(nickname, numberOfPlayer);
        } catch(RemoteException e) {
            OPEN = false;
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public synchronized void quitGame() {
        try {
            if(!OPEN) {
                return;
            }
            rmiServer.quitGame();
        } catch(RemoteException e) {
            OPEN = false;
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public synchronized void sentMessage(String text, String[] receiversNickname) {
        try {
            if(!OPEN) {
                return;
            }
            rmiServer.sentMessage(text, receiversNickname);
        } catch (RemoteException e) {
            OPEN = false;
            onClientConnectionLostListener.onConnectionLost();
        }

    }

    @Override
    public synchronized void nop() {
        try {
            if(!OPEN) {
                return;
            }
            rmiServer.nop();
        } catch(RemoteException e) {
            OPEN = false;
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public void close() {
        if(OPEN) {
            OPEN = false;
            this.onClientConnectionLostListener.onConnectionLost();
        }
    }
}
