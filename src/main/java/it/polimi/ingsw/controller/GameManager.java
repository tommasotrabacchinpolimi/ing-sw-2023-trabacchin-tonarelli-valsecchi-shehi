package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.net.User;

public class GameManager {
    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Controller controller;
    /*private void verifyCommonGoal(User user){
        Player player;
        CommonGoal commonGoal1, commonGoal2;
        TileType[][] bookShelf = player.getBookShelf();
        commonGoal1 = getController().getState().getCommonGoal1();
        commonGoal2 = getController().getState().getCommonGoal2();

        if(commonGoal1.rule(bookShelf)){
            commonGoal1.setAvailableScore();
        }

    }
    private void verifyEndGame(User user){

        Player player;

        if(player.getBookShelf().isFull()) {
            player.setEndGameCard(true);
        }

    }*/

}
