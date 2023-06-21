package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;
import java.util.List;

public class ClientRmiAdapter implements ServerInterface {

    private final RmiServerInterface rmiServer;

    private final OnClientConnectionLostListener onClientConnectionLostListener;

    public ClientRmiAdapter(RmiServerInterface rmiServer, OnClientConnectionLostListener onClientConnectionLostListener) {
        this.rmiServer = rmiServer;
        this.onClientConnectionLostListener = onClientConnectionLostListener;
    }
    @Override
    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        try{
            rmiServer.dragTilesToBookShelf(chosenTiles, chosenColumn);
        } catch(RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public void joinGame(String nickname) {
        try {
            rmiServer.joinGame(nickname);
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public void createGame(String nickname, int numberOfPlayer) {
        try {
            rmiServer.createGame(nickname, numberOfPlayer);
        } catch(RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public void quitGame() {
        try {
            rmiServer.quitGame();
        } catch(RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    @Override
    public void sentMessage(String text, String[] receiversNickname) {
        try {
            rmiServer.sentMessage(text, receiversNickname);
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }

    }

    @Override
    public void nop() {
        try {
            rmiServer.nop();
        } catch(RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

}
