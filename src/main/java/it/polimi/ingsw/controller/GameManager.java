package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.BoardSquare;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.net.User;

import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;

public class GameManager {
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

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

    /**
     * Method that returns true if and only if the Board needs to be refilled with tiles.
     * @return true if and only if the Board needs to be refilled with tiles.
     */
    public boolean verifyRefillBoard(){
        int numberOfPlayers = controller.getState().getPlayers().size();
        for(BoardSquare b : controller.getState().getBoard()){
            if(b.getBottom().getTileSubject()!=null || b.getRight().getTileSubject()!=null ||
                    b.getLeft().getTileSubject()!=null || b.getTop().getTileSubject()!=null) return false;
        }
        return true;
    }

    public void setNextCurrentPlayer(){
        Player oldCurrentPlayer = controller.getState().getCurrentPlayer();
        int index = (controller.getState().getPlayers().indexOf(oldCurrentPlayer) + 1) % 4;
        controller.getState().setCurrentPlayer(controller.getState().getPlayers().get(index));
    }
}
