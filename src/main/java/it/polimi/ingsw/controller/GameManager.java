package it.polimi.ingsw.controller;

import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

public abstract class GameManager<R extends RemoteInterface> {

    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public  abstract void dragTilesToBookShelf(User<R> user, int[] chosenTiles, int chosenColumn);
    public  abstract void registerPlayer(User<R> user, String nickname);
    public  abstract void setPlayersNumber(User<R> user, int playersNumber);

}
