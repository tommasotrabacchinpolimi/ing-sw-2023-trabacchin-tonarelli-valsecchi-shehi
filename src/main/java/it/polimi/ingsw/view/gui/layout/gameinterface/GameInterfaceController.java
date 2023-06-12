package it.polimi.ingsw.view.gui.layout.gameinterface;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.layout.board.BoardViewController;
import it.polimi.ingsw.view.gui.layout.bookshelf.PersonalBookshelfController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.model.TileSubject.*;

public class GameInterfaceController extends MyShelfieController {

    @FXML
    private GridPane gameRootPane;

    @FXML
    private StackPane gameBoardView;

    @FXML
    private GridPane gridActionContainer;

    @FXML
    private HBox selectedTilesBox;

    @FXML
    private GridPane gamePersonalBookshelf;

    @FXML
    private MyShelfieButton myShelfieButton;

    @FXML
    private MyShelfieButton confirmButton;

    @FXML
    private MyShelfieButton reverseButton;

    @FXML
    private BoardViewController gameBoardViewController;

    @FXML
    private PersonalBookshelfController gamePersonalBookshelfController;

    private TileBoxChildListener tileBoxChildListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePersonalBookshelfController.disableAllButtons();

        tileBoxChildListener = new TileBoxChildListener(selectedTilesBox, gamePersonalBookshelfController, gameBoardViewController);

        selectedTilesBox.getChildren().addListener(tileBoxChildListener);
    }

    public void fillBoard(MouseEvent mouseEvent) {

        TileSubject[][] tilesOnBoard = new TileSubject[][]{
                {null, null, null, BOOK_COMIC, BOOK_DICTIONARY, null, null, null, null},
                {null, null, null, BOOK_NOTE, CAT_BLACK, CAT_GRAY, null, null, null},
                {null, null, CAT_ORANGE, FRAME_DEGREE, FRAME_LOVE, FRAME_MEMORIES, GAME_CHESS, null, null},
                {null, GAME_MONOPOLY, GAME_RISIKO, PLANT_BASIL, PLANT_GREEN, PLANT_MONSTERA, TROPHY_CHAMPION, TROPHY_GYM, TROPHY_MUSIC},
                {BOOK_COMIC, BOOK_DICTIONARY, BOOK_NOTE, CAT_BLACK, CAT_GRAY, CAT_ORANGE, FRAME_DEGREE, FRAME_LOVE, FRAME_MEMORIES},
                {GAME_CHESS, GAME_MONOPOLY, GAME_RISIKO, PLANT_BASIL, PLANT_GREEN, PLANT_MONSTERA, TROPHY_CHAMPION, TROPHY_GYM, null},
                {null, null, TROPHY_GYM, TROPHY_MUSIC, BOOK_COMIC, BOOK_DICTIONARY, BOOK_NOTE, null, null},
                {null, null, null, CAT_BLACK, CAT_GRAY, CAT_ORANGE, null, null, null},
                {null, null, null, null, FRAME_DEGREE, FRAME_LOVE, null, null, null}
        };

        gameBoardViewController.fillUpBoard(tilesOnBoard);

        myShelfieButton.setDisable(true);
        myShelfieButton.setVisible(false);
    }

    private List<TileSubjectView> getClickedTilesFromBoard() {
        return gameBoardViewController.getTilesOnBoard().stream()
                .filter(TileSubjectView::isClicked)
                .toList();
    }

    private List<TileSubjectView> getTilesFromBox() {
        return selectedTilesBox.getChildren()
                .stream()
                .map(node -> (TileSubjectView) node)
                .filter(TileSubjectView::isClicked)
                .toList();
    }

    @FXML
    private void handleTileAction(MouseEvent mouseEvent) {
        List<TileSubjectView> boardSelected = getClickedTilesFromBoard();
        List<TileSubjectView> boxSelected = getTilesFromBox();

        if (boardSelected.size() != 0 && boxSelected.size() != 0) {
            MyShelfieAlertCreator.displayWarningAlert("Can't select at the same time from board and box",
                    "Insertion in bookshelf failed");

        } else if (boardSelected.size() > 0) {

            if (boardSelected.size() > 3) {
                MyShelfieAlertCreator.displayInformationAlert("You can't select more than 3 tiles from board",
                        "To many tiles selected");
            } else if (selectedTilesBox.getChildren().size() + boardSelected.size() > 3) {
                MyShelfieAlertCreator.displayInformationAlert(
                        "You have already " + selectedTilesBox.getChildren().size() + " tiles in your box, you can't",
                        "To many tiles in box");
                boardSelected.forEach(TileSubjectView::resetClick);
            } else {
                gameBoardViewController.getTilesOnBoard().removeAll(boardSelected);
                boardSelected.forEach(tile -> tile.performAction(selectedTilesBox));
                boardSelected.forEach(TileSubjectView::resetClick);
            }

        } else if (boxSelected.size() > 0) {
            if (boxSelected.size() != tileBoxChildListener.getOrderedSelectedTiles().size()) {
                MyShelfieAlertCreator.displayWarningAlert(
                        "You have to select all tiles to fill in your bookshelf",
                        "Select all tiles from box");
            } else {
                if (gamePersonalBookshelfController.getSelectedColumn() != -1) {

                    gamePersonalBookshelfController.insertTilesInBookshelf(tileBoxChildListener.getOrderedTilesFromBox(),
                            gamePersonalBookshelfController.getSelectedColumn());

                    gamePersonalBookshelfController.deselectAnyColumn();
                } else {
                    MyShelfieAlertCreator.displayInformationAlert(
                            "Before putting the tiles in your bookshelf, choose a column",
                            "Select a Column");
                }
            }
        }
    }

    @FXML
    private void handleReverseAction(MouseEvent mouseEvent) {
        List<TileSubjectView> boardSelected = getClickedTilesFromBoard();
        List<TileSubjectView> boxSelected = getTilesFromBox();

        if(boardSelected.size() != 0 && boxSelected.size() != 0) {
            MyShelfieAlertCreator.displayWarningAlert("Can't select at the same time from board and box",
                    "Insertion in bookshelf failed");
        }else if(boardSelected.size() > 0) {
            boardSelected.forEach(TileSubjectView::resetClick);
        }else if(boxSelected.size() > 0) {
            if (selectedTilesBox.getChildren().size() != boxSelected.size()) {
                MyShelfieAlertCreator.displayWarningAlert(
                        "In order to re-insert tiles on board you need to select all tiles in box",
                        "Select all tiles in box");
            } else {
                boxSelected.forEach( tile -> {
                    tile.reverseAction();
                    gameBoardViewController.getTilesOnBoard().add(tile);
                });
            }

            gamePersonalBookshelfController.deselectAnyColumn();
        }
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }


}
