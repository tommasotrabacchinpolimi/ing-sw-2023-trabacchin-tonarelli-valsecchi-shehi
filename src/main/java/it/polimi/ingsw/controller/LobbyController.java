package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.State;
import it.polimi.ingsw.net.OnConnectionLostListener;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;
import it.polimi.ingsw.net.UserAccepter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class LobbyController<R extends RemoteInterface> implements UserAccepter<R>, OnConnectionLostListener<R> {
    private Map<Controller<R>, List<User<R>>> controllerUserMap;
    private List<User<R>> waitingUsers; //user che non sono ancora stati assegnati a nessuna partita

    @Override
    public boolean acceptUser(User<R> user) {
        return true;
    }

    public Map<Controller<R>, List<User<R>>> getControllerUserMap() {
        return controllerUserMap;
    }

    public void setControllerUserMap(Map<Controller<R>, List<User<R>>> controllerUserMap) {
        this.controllerUserMap = controllerUserMap;
    }

    public List<User<R>> getWaitingUsers() {
        return waitingUsers;
    }

    public void setWaitingUsers(List<User<R>> waitingUsers) {
        this.waitingUsers = waitingUsers;
    }

    public void joinGame(User<R> user, String nickname){
        user.setNickname(nickname);
        if(controllerUserMap.keySet().size() == 0){ //se non ci sono partite avviate
            waitingUsers.add(user);
        } else { //se ci sono già partite avviate
            List<String> listNickname = new ArrayList<>();
            for (Controller<R> controller: controllerUserMap.keySet()) {
                listNickname.addAll(controllerUserMap.get(controller).stream().map(User::getNickname).toList());
            }

            if(listNickname.contains(nickname)){ //controllo se il giocatore è già associato a una partita (ossia si è disconnesso e si sta riconnettendo)
                for (Controller<R> controller: controllerUserMap.keySet()){
                    for(User<R>  u: controllerUserMap.get(controller)){
                        if(u.getNickname().equals(nickname)){
                            controllerUserMap.get(controller).remove(u);
                            controllerUserMap.get(controller).add(user);
                            controller.registerPlayer(user, nickname);
                            return;
                        }
                    }
                }
            } else {// se il giocatore non era già presente in altre partite, lo aggiungo alla prima partita disponibile
                for(Controller<R> controller: controllerUserMap.keySet()){
                    if(controllerUserMap.get(controller).size() < controller.getState().getPlayersNumber()){
                        controllerUserMap.get(controller).add(user);
                        controller.registerPlayer(user, user.getNickname());
                        return;
                    }
                }
            }
            //se non ci sono partite disponibili, il giocatore viene inserito in lista d'attesa
            waitingUsers.add(user);
        }

    }

    public void createGame(User<R> user, String nickname, int numberOfPlayer){
        Controller<R> controller = new Controller<>();
        List<User<R>> list = new ArrayList<>();
        user.setNickname(nickname);
        controller.registerPlayer(user, user.getNickname());
        controller.setNumberPlayers(numberOfPlayer);
        list.add(user);
        //se ci sono dei giocatori in attesa li aggiungo alla partita
        if(waitingUsers.size()!=0){
            for(int i = 1; i < numberOfPlayer; i++){
                if(waitingUsers.size()!=0){
                    User<R> u = waitingUsers.remove(0);
                    list.add(u);
                    controller.registerPlayer(u, u.getNickname());
                }
            }
        }
        controllerUserMap.put(controller, list);
    }

    public void onEndGame(Controller<R> controller){
        if(controllerUserMap.containsKey(controller)){
            if(controller.getState().getGameState() == GameState.END){
                controllerUserMap.remove(controller, controllerUserMap.get(controller));
            }
        }
    }

    @Override
    public void onConnectionLost(User<R> user) {
        //toglio lo user dalla queue se presente
        waitingUsers.removeIf(u -> u.equals(user));
    }


}
