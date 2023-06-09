package it.polimi.ingsw.view.gui.gameinterface;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.board.BoardViewController;
import it.polimi.ingsw.view.gui.bookshelf.BookshelfCommandController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameInterfaceController extends MyShelfieController {
    public GridPane gameRootPane;
    public StackPane gameBoardView;
    public GridPane gridActionContainer;
    public HBox selectedTilesBox;
    public GridPane gameBookshelfCommandView;
    public MyShelfieButton myShelfieButton;
    public MyShelfieButton confirmButton;
    public MyShelfieButton reverseButton;

    @FXML
    private BoardViewController gameBoardViewController;

    @FXML
    private BookshelfCommandController gameBookshelfCommandViewController;

    private final List<TileSubjectView> tilesOnBoard = new ArrayList<>();

    private TileBoxChildListener tileBoxChildListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameBookshelfCommandViewController.disableAllButtons();

        tileBoxChildListener = new TileBoxChildListener(selectedTilesBox, gameBookshelfCommandViewController, tilesOnBoard);

        selectedTilesBox.getChildren().addListener(tileBoxChildListener);
    }

    public void fillBoard(MouseEvent mouseEvent) {
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(0, 3), TileSubject.BOOK_COMIC));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(0, 4), TileSubject.BOOK_DICTIONARY));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(1, 3), TileSubject.BOOK_NOTE));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(1, 4), TileSubject.CAT_BLACK));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(1, 5), TileSubject.CAT_GRAY));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(2, 2), TileSubject.CAT_ORANGE));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(2, 3), TileSubject.FRAME_LOVE));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(2, 4), TileSubject.FRAME_DEGREE));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(2, 5), TileSubject.FRAME_MEMORIES));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(2, 6), TileSubject.GAME_CHESS));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(3, 1), TileSubject.GAME_MONOPOLY));
        tilesOnBoard.add(new TileSubjectView(gameBoardViewController.getItemTileBox(3, 2), TileSubject.GAME_RISIKO));
    }

    @FXML
    private void handleTileAction(MouseEvent mouseEvent) {
        List<TileSubjectView> boardSelected = getTilesFromBoard();
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
                        "You have already " + selectedTilesBox.getChildren().size() + "tiles in your box, you can't",
                        "To many tiles in box");
                boardSelected.forEach(TileSubjectView::resetClick);
            } else {
                tilesOnBoard.removeAll(boardSelected);
                boardSelected.forEach(tile -> tile.performAction(selectedTilesBox));
                boardSelected.forEach(TileSubjectView::resetClick);
            }
        } else if (boxSelected.size() > 0) {
            if (boxSelected.size() != tileBoxChildListener.getOrderedSelectedTiles().size()) {
                MyShelfieAlertCreator.displayWarningAlert(
                        "You have to select all tiles to fill in your bookshelf",
                        "Select all tiles from box");
            } else {
                if(gameBookshelfCommandViewController.getSelectedColumn() != -1) {

                    gameBookshelfCommandViewController.insertTilesInBookshelf(tileBoxChildListener.getOrderedTilesFromBox(),
                            new Coordinate(5, gameBookshelfCommandViewController.getSelectedColumn()),
                            new Coordinate(4, gameBookshelfCommandViewController.getSelectedColumn()),
                            new Coordinate(3, gameBookshelfCommandViewController.getSelectedColumn()));

                    gameBookshelfCommandViewController.deselectAnyColumn();

                }else {
                    MyShelfieAlertCreator.displayInformationAlert(
                            "Before putting the tiles in your bookshelf, choose a column",
                            "Select a Column");
                }
            }
        }
    }

    private List<TileSubjectView> getTilesFromBoard() {
        return tilesOnBoard.stream()
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
    private void handleReverseAction(MouseEvent mouseEvent) {
        selectedTilesBox.getChildren()
                .stream()
                .map(node -> (TileSubjectView) node)
                .filter(TileSubjectView::isClicked)
                .forEach(tile -> {
                    tile.reverseAction();
                    tilesOnBoard.add(tile);
                });

        gameBookshelfCommandViewController.deselectAnyColumn();
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }


}
