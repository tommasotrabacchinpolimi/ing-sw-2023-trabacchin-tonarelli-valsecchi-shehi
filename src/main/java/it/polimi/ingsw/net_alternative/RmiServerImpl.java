package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.servermessages.*;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RmiServerImpl extends UnicastRemoteObject implements RmiServerInterface{

    private ServerDispatcherInterface serverDispatcherInterface;
    private ClientInterface clientInterface;

    ExecutorService executorService;
    public RmiServerImpl(ServerDispatcherInterface serverDispatcherInterface, ClientInterface client) throws RemoteException{
        super();
        this.serverDispatcherInterface = serverDispatcherInterface;
        this.clientInterface = client;
        this.executorService = Executors.newSingleThreadExecutor();
    }



    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) throws RemoteException{
        DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage = new DragTilesToBookShelfNetMessage(chosenTiles, chosenColumn);
        executorService.submit(()->serverDispatcherInterface.dispatch(dragTilesToBookShelfNetMessage, clientInterface));
    }

    public void joinGame(String nickname) throws RemoteException{
        JoinGameNetMessage joinGameNetMessage = new JoinGameNetMessage(nickname);
        executorService.submit(()->serverDispatcherInterface.dispatch(joinGameNetMessage, clientInterface));

    }

    public void createGame(String nickname, int numberOfPlayer) throws RemoteException{
        CreateGameNetMessage createGameNetMessage = new CreateGameNetMessage(nickname, numberOfPlayer);
        executorService.submit(()->serverDispatcherInterface.dispatch(createGameNetMessage, clientInterface));

    }

    public void quitGame() throws RemoteException{
        QuitGameNetMessage quitGameNetMessage = new QuitGameNetMessage();
        executorService.submit(()->serverDispatcherInterface.dispatch(quitGameNetMessage, clientInterface));

    }

    public void sentMessage(String text, String[] receiversNickname) throws RemoteException{
        SentMessageNetMessage sentMessageNetMessage = new SentMessageNetMessage(text, receiversNickname);
        executorService.submit(()->serverDispatcherInterface.dispatch(sentMessageNetMessage, clientInterface));
    }

    public void nop() throws RemoteException {

    }
}
