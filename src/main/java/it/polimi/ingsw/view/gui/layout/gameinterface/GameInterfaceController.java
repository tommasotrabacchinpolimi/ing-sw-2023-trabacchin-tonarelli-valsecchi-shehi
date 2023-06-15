package it.polimi.ingsw.view.gui.layout.gameinterface;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.CommonGoalView;
import it.polimi.ingsw.view.gui.layout.board.BoardViewController;
import it.polimi.ingsw.view.gui.layout.bookshelf.PersonalBookshelfController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.model.TileSubject.*;

public class GameInterfaceController extends MyShelfieController {

    @FXML
    private GridPane gameGridActionContainer;

    @FXML
    private VBox gameButtonActionsContainer;

    @FXML
    private GridPane gameGridGoalContainer;

    @FXML
    private GridPane gameRootPane;

    @FXML
    private StackPane gameBoardView;

    @FXML
    private GridPane gridGameDivider;

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

    private final List<Pair<CommonGoalView, Integer>> commonGoals = new ArrayList<>();

    @FXML
    private BoardViewController gameBoardViewController;

    @FXML
    private PersonalBookshelfController gamePersonalBookshelfController;

    private TileBoxChildManager tileBoxChildManager;

    private List<TileSubjectView> tilesOnBoard;

    private final List<Pair<EventHandler<MouseEvent>, TileSubjectView>> tileBoardHandler = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePersonalBookshelfController.disableAllButtons();

        tileBoxChildManager = new TileBoxChildManager(selectedTilesBox, gamePersonalBookshelfController, gameBoardViewController);

        selectedTilesBox.getChildren().addListener(tileBoxChildManager);

        tilesOnBoard = gameBoardViewController.getTilesOnBoard();

        commonGoals.add(new Pair<>(new CommonGoalView("Four tiles of the same type in the four corners of the bookshelf."), 1));
        commonGoals.add(new Pair<>(new CommonGoalView(""), 2));

        gameGridGoalContainer.add(commonGoals.get(0).getKey(), 0, 0);
        gameGridGoalContainer.add(commonGoals.get(1).getKey(), 0, 1);
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

        this.tilesOnBoard.forEach(this::addTilesOnBoardListener);

        myShelfieButton.setDisable(true);
        myShelfieButton.setVisible(false);
    }

    protected List<TileSubjectView> getClickedTilesFromBoard() {
        return gameBoardViewController.getTilesOnBoard().stream()
                .filter(TileSubjectView::isClicked)
                .toList();
    }

    private List<TileSubjectView> getTilesFromBox() {
        return tileBoxChildManager.getSelectedTilesFromContainer();
    }

    private void addTilesOnBoardListener(TileSubjectView tile) {
        EventHandler<MouseEvent> pressHandler = mouseEvent -> {

            if (!tile.isEnabled())
                return;

            if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                List<TileSubjectView> clickedTiles = getClickedTilesFromBoard();

                if (clickedTiles.size() == 1) {
                    try{
                        gameBoardViewController.setActiveTilesOnBoardOneSelected(
                                gamePersonalBookshelfController.getTileSubjectBookshelfMatrix(),
                                clickedTiles.get(0));
                    }catch(WrongChosenTilesFromBoardException e) {
                        MyShelfieAlertCreator.displayErrorAlert(
                                "Tiles selected are not adjacent, so you can not select them",
                                "Can't select these tiles"
                        );


                    }

                } else if (clickedTiles.size() == 2) {

                    try{
                        gameBoardViewController.setActiveTilesOnBoardTwoSelected(
                                gamePersonalBookshelfController.getTileSubjectBookshelfMatrix(),
                                clickedTiles.get(0), clickedTiles.get(1));
                    }catch(WrongChosenTilesFromBoardException e) {

                        MyShelfieAlertCreator.displayInformationAlert(
                                "Tiles selected are not adjacent, so you can not select them",
                                "Can't select these tiles"
                        );

                        getClickedTilesFromBoard().forEach(TileSubjectView::resetClick);

                        gameBoardViewController.setActiveTilesOnBoardNoneSelected();
                    }

                } else if(getClickedTilesFromBoard().size() == 3) {
                    tilesOnBoard.stream()
                            .filter(otherTile -> !getClickedTilesFromBoard().contains(otherTile))
                            .forEach(TileSubjectView::disable);

                } else if (getClickedTilesFromBoard().size() == 0) {
                    gameBoardViewController.setActiveTilesOnBoardNoneSelected();
                }
            }
        };

        tileBoardHandler.add(new Pair<>(pressHandler, tile));

        tile.addEventHandler(MouseEvent.MOUSE_CLICKED, pressHandler);
    }

    private void removeBoardHandlerTilePair(TileSubjectView tile) {

        Pair<EventHandler<MouseEvent>, TileSubjectView> removed = getHandlerTilePair(tile);

        removed.getValue().removeEventHandler(MouseEvent.MOUSE_CLICKED, removed.getKey());

        tileBoardHandler.remove(getHandlerTilePair(tile));
    }

    private Pair<EventHandler<MouseEvent>, TileSubjectView> getHandlerTilePair(TileSubjectView tile) {
        return tileBoardHandler.stream()
                .filter(pair -> pair.getValue().equals(tile))
                .toList()
                .get(0);
    }

    @FXML
    private void handleSubmitAction(MouseEvent mouseEvent) throws InterruptedException {
        List<TileSubjectView> boardSelected = getClickedTilesFromBoard();
        List<TileSubjectView> boxSelected = getTilesFromBox();

        if (boardSelected.size() != 0 && boxSelected.size() != 0) {
            MyShelfieAlertCreator.displayWarningAlert("Can't select at the same time from board and box",
                    "Insertion in bookshelf failed");

        } else if (boardSelected.size() > 0) {

            if (boardSelected.size() > 3) {
                MyShelfieAlertCreator.displayInformationAlert("You can't select more than 3 tiles from board",
                        "Too many tiles selected");
            } else {
                tilesOnBoard.removeAll(boardSelected);
                boardSelected.forEach(this::fromBoardToBoxOperations);
            }

        } else if( boxSelected.size() == 0 && gamePersonalBookshelfController.getSelectedColumn() != -1) {
            MyShelfieAlertCreator.displayWarningAlert(
                    "You have to select all tiles to fill in your bookshelf",
                    "Select all tiles from box");
        }else if (boxSelected.size() > 0) {
            if (boxSelected.size() != tileBoxChildManager.getAllTilesFromBox().size()) {
                MyShelfieAlertCreator.displayWarningAlert(
                        "You have to select all tiles to fill in your bookshelf",
                        "Select all tiles from box");
            } else {
                if (gamePersonalBookshelfController.getSelectedColumn() != -1) {

                    gamePersonalBookshelfController.insertTilesInBookshelf(tileBoxChildManager.getOrderedTilesFromBox(),
                            gamePersonalBookshelfController.getSelectedColumn());

                    gamePersonalBookshelfController.deselectAnyColumn();
                } else {
                    MyShelfieAlertCreator.displayInformationAlert(
                            "Before putting the tiles in your bookshelf, choose a column",
                            "Select a Column");

                    boardSelected.forEach(TileSubjectView::resetClick);
                    gameBoardViewController.setActiveTilesOnBoardNoneSelected();
                }
            }
        }
    }

    private void fromBoardToBoxOperations(TileSubjectView tile) {
        removeBoardHandlerTilePair(tile);
        tile.performAction(selectedTilesBox);
        tile.resetClick();
    }

    @FXML
    private void handleReverseAction(MouseEvent mouseEvent) {
        List<TileSubjectView> boardSelected = getClickedTilesFromBoard();
        List<TileSubjectView> boxSelected = getTilesFromBox();

        if (boardSelected.size() != 0 && boxSelected.size() != 0) {
            MyShelfieAlertCreator.displayWarningAlert("Can't select at the same time from board and box",
                    "Insertion in bookshelf failed");
        } else if (boardSelected.size() > 0) {
            boardSelected.forEach(TileSubjectView::resetClick);

            gameBoardViewController.setActiveTilesOnBoardNoneSelected();

        } else if (boxSelected.size() == 0) {
            tileBoxChildManager.getAllTilesFromBox().forEach(this::reinsertTilesOnBoard);
            gamePersonalBookshelfController.deselectAnyColumn();
        } else {
            if (selectedTilesBox.getChildren().size() != boxSelected.size()) {
                MyShelfieAlertCreator.displayWarningAlert(
                        "In order to re-insert tiles on board you need to select all tiles in box",
                        "Select all tiles in box");
            } else {
                boxSelected.forEach(this::reinsertTilesOnBoard);
            }

            gamePersonalBookshelfController.deselectAnyColumn();
        }
    }

    private void reinsertTilesOnBoard(@NotNull TileSubjectView tile) {
        tile.reverseAction();
        gameBoardViewController.getTilesOnBoard().add(tile);
        addTilesOnBoardListener(tile);
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }


}
