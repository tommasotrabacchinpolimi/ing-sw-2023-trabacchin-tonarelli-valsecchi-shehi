package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.AlreadyTakenNicknameException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.State;
import it.polimi.ingsw.net.*;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyController implements UserAccepter<ClientInterface>, OnConnectionLostListener<ClientInterface>, LobbyControllerInterface{
    private final Map<Controller, List<ClientInterface>> controllerViewMap = new ConcurrentHashMap<>();
    private final Map<ClientInterface, Controller> viewControllerMap = new ConcurrentHashMap<>();

    private final List<ClientInterface> waitingUsers = new ArrayList<>(); //user che non sono ancora stati assegnati a nessuna partita
    private final Map<ClientInterface,String> viewToNicknameMap = new ConcurrentHashMap<>();
    private final Map<String,ClientInterface> nicknameToViewMap = new ConcurrentHashMap<>();

    private final List<String> disconnectedButInGame = new ArrayList<>();

    private Dispatcher dispatcher;


    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public boolean acceptUser(User<ClientInterface> user) {
        return true;
    }

    @Override
    public void registerConnectionDownListener(User<ClientInterface> user) {
        user.getConnectionManager().setOnConnectionLostListener(this);
    }

    public synchronized void joinGame(ClientInterface user, String nickname){
        if(viewToNicknameMap.containsValue(nickname)){
            if(disconnectedButInGame.contains(nickname)){
                Controller c = viewControllerMap.get(nicknameToViewMap.get(nickname));
                for(Player p: c.getState().getPlayers()) {
                    if (p.getVirtualView() == nicknameToViewMap.get(nickname) && p.getPlayerState()== PlayerState.DISCONNECTED) {
                        viewControllerMap.remove(nicknameToViewMap.get(nickname)); //togliamo quella vecchia
                        viewControllerMap.put(user, c); //mettiamo quella nuova
                        controllerViewMap.get(c).remove(nicknameToViewMap.get(nickname));
                        controllerViewMap.get(c).add(user);
                        viewToNicknameMap.remove(nicknameToViewMap.get(nickname));
                        viewToNicknameMap.put(user, nickname);
                        nicknameToViewMap.remove(nickname);
                        nicknameToViewMap.put(nickname, user);
                        disconnectedButInGame.remove(nickname);
                        c.registerPlayer(user, nickname);
                    }
                }
            }
            else {
                //metodo nella textclient interface che dice al client che il nome è gia stato preso
                //notificare alla view
                RuntimeException e = new AlreadyTakenNicknameException();
                user.onException(e);
                throw e;
            }
        } else {
            Controller c = firstGameAvailable();
            if(c == null){ //se non ci sono partite disponibili o sono gia tutte piene metto lo user nella waiting
                waitingUsers.add(user);
                viewToNicknameMap.put(user, nickname);
                nicknameToViewMap.put(nickname,user);
            } else { //altrimenti lo inserisco nella prima partita disponibile
                viewToNicknameMap.put(user, nickname);
                nicknameToViewMap.put(nickname,user);
                controllerViewMap.get(c).add(user);
                viewControllerMap.put(user, c);
                dispatcher.setController(user, c);
                System.out.println(nickname + " joining " + c);
                c.registerPlayer(user, nickname);
            }
        }
    }

    public synchronized void createGame(ClientInterface user, String nickname, int numberOfPlayer) throws FileNotFoundException {
        System.out.println("createGame");
        //se arriva uno user che si era disconnesso, lo ignoro (da fare)
        State state = new State();
        Controller controller = new Controller(state, this);
        List<ClientInterface> list = new ArrayList<>();

        viewToNicknameMap.put(user,nickname);
        nicknameToViewMap.put(nickname,user);
        dispatcher.setController(user, controller);
        System.out.println(nickname + " joining " + controller);
        controller.registerPlayer(user,nickname);
        controller.setNumberPlayers(numberOfPlayer);
        list.add(user);
        //se ci sono dei giocatori in attesa li aggiungo alla partita
        if(waitingUsers.size()!=0){
            for(int i = 1; i < numberOfPlayer; i++){
                if(waitingUsers.size()!=0){
                    ClientInterface u = waitingUsers.remove(0);
                    list.add(u);
                    dispatcher.setController(user, controller);
                    System.out.println(nickname + " joining " + controller);
                    controller.registerPlayer(u, viewToNicknameMap.get(u));
                }
            }
        }
        controllerViewMap.put(controller, list);
        for(ClientInterface u: list){
            viewControllerMap.put(u,controller);
        }
    }

    @Override
    public void nop(ClientInterface view) throws RemoteException {

    }

    public synchronized void onEndGame(Controller controller){
        if(controllerViewMap.containsKey(controller)){
            if(controller.getState().getGameState() == GameState.END){
                List<ClientInterface> list =  controllerViewMap.get(controller);
                controllerViewMap.remove(controller);
                for(ClientInterface user: list){
                    viewControllerMap.remove(user);
                    dispatcher.removeController(user, controller);
                }
            }
        }
    }

    private Controller firstGameAvailable(){
        for(Controller c: controllerViewMap.keySet()){
            if(c.getState().getPlayers().size() < c.getState().getPlayersNumber()){
                return c;
            }
        }
        return null;
    }

    @Override
    public synchronized void onConnectionLost(ClientInterface user) {
        System.out.println("connection lost");
        if(viewControllerMap.get(user)!=null) {
            viewControllerMap.get(user).onConnectionLost(user);
            disconnectedButInGame.add(viewToNicknameMap.get(user));
        }
        waitingUsers.removeIf(u -> u.equals(user));
    }

    public synchronized void onQuitGame(ClientInterface user){
        Controller c = viewControllerMap.get(nicknameToViewMap.get(viewToNicknameMap.get(user)));
        controllerViewMap.get(c).remove(user);
        viewControllerMap.remove(user);
        dispatcher.removeController(user, c);
    }

}
