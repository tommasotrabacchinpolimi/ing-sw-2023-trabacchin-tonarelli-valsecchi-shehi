package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.State;
import it.polimi.ingsw.net.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyController<R extends RemoteInterface> implements UserAccepter<R>, OnConnectionLostListener<R> {
    private final Map<Controller<R>, List<R>> controllerViewMap = new ConcurrentHashMap<>();
    private final Map<R, Controller<R>> viewControllerMap = new ConcurrentHashMap<>();

    private final List<R> waitingUsers = new ArrayList<>(); //user che non sono ancora stati assegnati a nessuna partita
    private final Map<R,String> viewToNicknameMap = new ConcurrentHashMap<>();
    private final Map<String,R> nicknameToViewMap = new ConcurrentHashMap<>();

    private final List<String> disconnectedButInGame = new ArrayList<>();

    @Override
    public boolean acceptUser(User<R> user) {
        return true;
    }


    public void joinGame(R user, String nickname){
        //controllo se c'è un'altra view con lo stesso nickname -> se c'è ed è in una partita, per ora si ignora
        if(viewToNicknameMap.containsValue(nickname)){
            if(disconnectedButInGame.contains(nickname)){
                Controller<R> c = viewControllerMap.get(nicknameToViewMap.get(nickname));
                for(Player p: c.getState().getPlayers()) {
                    if (p.getVirtualView() == nicknameToViewMap.get(nickname)) {
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
            }
        } else {
            Controller<R> c = firstGameAvailable();
            if(c == null){ //se non ci sono partite disponibili o sono gia tutte piene metto lo user nella waiting
                waitingUsers.add(user);
                viewToNicknameMap.put(user, nickname);
                nicknameToViewMap.put(nickname,user);
            } else { //altrimenti lo inserisco nella prima partita disponibile

                viewToNicknameMap.put(user, nickname);
                nicknameToViewMap.put(nickname,user);
                controllerViewMap.get(c).add(user);
                viewControllerMap.put(user, c);
                c.registerPlayer(user, nickname);
            }
        }
    }

    public void createGame(R user, String nickname, int numberOfPlayer){
        //se arriva uno user che si era disconnesso, lo ignoro (da fare)
        Controller<R> controller = new Controller<>();
        State state = new State();
        List<R> list = new ArrayList<>();
        controller.setState(state);

        viewToNicknameMap.put(user,nickname);
        nicknameToViewMap.put(nickname,user);

        controller.registerPlayer(user,nickname);
        controller.setNumberPlayers(numberOfPlayer);
        list.add(user);
        //se ci sono dei giocatori in attesa li aggiungo alla partita
        if(waitingUsers.size()!=0){
            for(int i = 1; i < numberOfPlayer; i++){
                if(waitingUsers.size()!=0){
                    R u = waitingUsers.remove(0);
                    list.add(u);
                    controller.registerPlayer(u, nickname);
                }
            }
        }
        controllerViewMap.put(controller, list);
        for(R u: list){
            viewControllerMap.put(u,controller);
        }
    }

    public void onEndGame(Controller<R> controller){
        if(controllerViewMap.containsKey(controller)){
            if(controller.getState().getGameState() == GameState.END){
                List<R> list =  controllerViewMap.get(controller);
                controllerViewMap.remove(controller);
                for(R user: list){
                    viewControllerMap.remove(user);
                }
                //e rimuovo anche da viewControllerMap
            }
        }
    }

    private Controller<R> firstGameAvailable(){
        for(Controller<R> c: controllerViewMap.keySet()){
            if(c.getState().getPlayers().size() < c.getState().getPlayersNumber()){
                return c;
            }
        }
        return null;
    }

    @Override
    public void onConnectionLost(R user) {
        disconnectedButInGame.add(viewToNicknameMap.get(user));
        waitingUsers.removeIf(u -> u.equals(user));
    }

    public void onQuitGame(R user){
        //bisogna rimuovere le associazioni controller - user
    }
}
