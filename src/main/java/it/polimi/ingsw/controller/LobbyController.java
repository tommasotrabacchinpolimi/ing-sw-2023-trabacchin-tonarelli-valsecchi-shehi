package it.polimi.ingsw.controller;

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

public class LobbyController<R extends RemoteInterface> implements UserAccepter<R>, OnConnectionLostListener<R> {
    private Map<Controller<R>, List<User<R>>> stateUserMap;
    private Queue<User<R>> waitingUsers; //user che non sono ancora stati assegnati a nessuna partita

    @Override
    public boolean acceptUser(User<R> user) {
        return false;
    }

    public Map<Controller<R>, List<User<R>>> getStateUserMap() {
        return stateUserMap;
    }

    public void setStateUserMap(Map<Controller<R>, List<User<R>>> stateUserMap) {
        this.stateUserMap = stateUserMap;
    }

    public void joinGame(String nickname){
        //controllare che lo user non si già associato a un controller nella mappa o nella coda
        // se c'è nella mappa, sostituisco lo user e chiamo sul controller registerplayer
        for(Controller s: stateUserMap.keySet()){
            if(stateUserMap.containsKey(s)){
                if(stateUserMap.get(s).size() < s.getPlayersNumber()){
                    stateUserMap.get(s).add(new User<R>());
                    s.addPlayer(new Player(nickname));
                    return;
                }
            }
        }
    }

    public void createGame(User<R> user, String nickname, int numberOfPlayer){
        State state = new State();
        Controller<R> controller = new Controller<>();
        controller.setState(state);
        user.setNickname(nickname);
        controller.registerPlayer(user, user.getNickname());
        controller.setNumberPlayers(numberOfPlayer);
        List<User<R>> list = new ArrayList<>();
        list.add(user);
        stateUserMap.put(controller, list);
    }

    public void onEndGame(Controller<R> controller){

    }

    @Override
    public void onConnectionLost(User<R> user) {
        //toglio lo user dalla queue se presente
    }

}
