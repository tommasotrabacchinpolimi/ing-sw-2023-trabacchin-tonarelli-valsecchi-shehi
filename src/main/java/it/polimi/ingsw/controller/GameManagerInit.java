package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

public class GameManagerInit<R extends RemoteInterface> extends GameManager<R>{

    public void dragTilesToBookShelf(User<R> user, int[] chosenTiles, int chosenColumn) {
        return;
    }

    @Override
    public void registerPlayer(User<R> user, String nickname) {
        Player player = new Player(nickname, new PersonalGoal("personal goal path"));
        if (super.getController().getState().addPlayer(player)){
            user.setPlayer(player);
        }
    }

    @Override
    public void setPlayersNumber(User<R> user, int playersNumber) {
        super.getController().getState().setPlayersNumber(playersNumber);
    }
}
