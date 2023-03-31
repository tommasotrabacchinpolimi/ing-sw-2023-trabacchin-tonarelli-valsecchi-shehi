package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.User;

import java.util.List;

import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;

public class GameManager {
    private Controller controller;

    public Controller getController() {

        return controller;
    }

    public void setController(Controller controller) {

        this.controller = controller;
    }

    private void verifyCommonGoal(User user){
        Player player = controller.getPlayerPlaying();
        CommonGoal commonGoal1, commonGoal2;

        BookShelf bookShelf = player.getBookShelf();
        commonGoal1 = controller.getActiveCommonGoal1();
        commonGoal2 = controller.getActiveCommonGoal2();

        TileType[][] matrix = bookShelf.toTileTypeMatrix();

        if(commonGoal1.rule(matrix) != null){
            player.getPointPlayer().setScoreCommonGoal1(commonGoal1.getAvailableScore());
        }

        if(commonGoal2.rule(matrix) != null){
            player.getPointPlayer().setScoreCommonGoal1(commonGoal2.getAvailableScore());
        }

    }
    private void verifyEndGame(User user){
        Player player = controller.getPlayerPlaying();

        if(player.getBookShelf().isFull()) {
            player.assignScoreEndGame(1);
        }
    }




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
}
