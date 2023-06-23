package it.polimi.ingsw.view.gui.layout.gameinterface;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.commongoal.CommonGoalView;
import it.polimi.ingsw.view.gui.customcomponents.PersonalGoalView;
import it.polimi.ingsw.view.gui.customcomponents.pointpane.HPointPane;
import it.polimi.ingsw.view.gui.layout.board.BoardViewController;
import it.polimi.ingsw.view.gui.layout.bookshelf.PersonalBookshelfController;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.model.TileSubject.*;
import static it.polimi.ingsw.model.TileType.*;
import static it.polimi.ingsw.model.TileType.TROPHY;

public class GameInterfaceController extends MyShelfieController {

    public HBox testingBox;

    @FXML
    private HPointPane personalPointPane;

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
    private MyShelfieButton confirmButton;

    @FXML
    private MyShelfieButton reverseButton;

    private final List<CommonGoalView> commonGoals = new ArrayList<>();

    private PersonalGoalView personalGoal;

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

        Stack<Integer> scoringTokens = new Stack<>();

        scoringTokens.push(2);
        scoringTokens.push(4);
        scoringTokens.push(6);
        scoringTokens.push(8);

        commonGoals.add(new CommonGoalView(
                "line_common_goal_1",
                "Four lines each formed by 5 tiles of maximum three different types. " +
                        "One line can show the same or a different combination of another line.",
                scoringTokens));

        scoringTokens.remove(3);

        commonGoals.add(new CommonGoalView("error_common_goal", "My personal description", scoringTokens));

        TileType[][] configuration = new TileType[][]{
                {null, null, null, null, TROPHY},
                {null, GAME, null, null, null},
                {BOOK, null, null, null, null},
                {null, null, null, CAT, null},
                {null, FRAME, null, null, null},
                {null, null, null, PLANT, null}
        };

        personalGoal = new PersonalGoalView(configuration);

        gameGridGoalContainer.add(commonGoals.get(0), 0, 0);
        gameGridGoalContainer.add(commonGoals.get(1), 0, 1);
        gameGridGoalContainer.add(personalGoal, 0, 2);

        personalGoal.setOnMouseEntered(value -> {
            gamePersonalBookshelfController.highlightPersonalTargetCells(personalGoal.getPersonalConfiguration());
        });

        personalGoal.setOnMouseExited(value -> {
            gamePersonalBookshelfController.resetPersonalTargetCells(personalGoal.getPersonalConfiguration());
        });
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
                    try {
                        gameBoardViewController.setActiveTilesOnBoardOneSelected(
                                gamePersonalBookshelfController.getTileSubjectBookshelfMatrix(),
                                clickedTiles.get(0));
                    } catch (WrongChosenTilesFromBoardException e) {
                        MyShelfieAlertCreator.displayErrorAlert(
                                "Tiles selected are not adjacent, so you can not select them",
                                "Can't select these tiles"
                        );
                    }

                } else if (clickedTiles.size() == 2) {

                    try {
                        gameBoardViewController.setActiveTilesOnBoardTwoSelected(
                                gamePersonalBookshelfController.getTileSubjectBookshelfMatrix(),
                                clickedTiles.get(0), clickedTiles.get(1));
                    } catch (WrongChosenTilesFromBoardException e) {

                        MyShelfieAlertCreator.displayInformationAlert(
                                "Tiles selected are not adjacent, so you can not select them",
                                "Can't select these tiles"
                        );

                        getClickedTilesFromBoard().forEach(TileSubjectView::resetClick);

                        gameBoardViewController.setActiveTilesOnBoardNoneSelected();
                    }

                } else if (getClickedTilesFromBoard().size() == 3) {
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

    public Map<Coordinate, TileSubjectView> retrieveBoardState() {
        return gameBoardViewController.getBoardState();
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
            //Can never be true but is insert for strong software resistance
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

        } else if (boxSelected.size() == 0 && gamePersonalBookshelfController.getSelectedColumn() != -1) {
            MyShelfieAlertCreator.displayWarningAlert(
                    "You have to select all tiles to fill in your bookshelf",
                    "Select all tiles from box");
        } else if (boxSelected.size() > 0) {
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

    public void removeOpponentTakenTiles(List<TileSubjectView> tilesTakenByOpponent) {
        tilesOnBoard.removeAll(tilesTakenByOpponent);

        tilesTakenByOpponent.forEach(tile -> {
            removeBoardHandlerTilePair(tile);
            tile.resetClick();

            tile.parentProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue != oldValue) {
                    gameBoardViewController.setActiveTilesOnBoardNoneSelected();
                }
            });
        });
    }

    public void startEndGameTokenAnimation(MouseEvent mouseEvent) {
        transferEndGameToken(personalPointPane.getFreePointCell());
    }

    public void assignEndGameTokenToOpponent(Pane destinationPane) {
        transferEndGameToken(destinationPane);
    }

    private void transferEndGameToken(Pane destionationPane) {
        try {
            gameBoardViewController.moveEndGameTokenView(destionationPane);
        } catch (NullPointerException e) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "The player has obtained all possible point, so he can't obtain another point token",
                    "Player point table full"
            );
        }
    }

    public void startTokenAnimation1(MouseEvent mouseEvent) {
        transferToken(commonGoals.get(0), personalPointPane.getFreePointCell());
    }

    public void assignToken1ToOpponent(Pane destinationPane) {
        transferToken(commonGoals.get(0), destinationPane);
    }

    public void startTokenAnimation2(MouseEvent mouseEvent) {
        transferToken(commonGoals.get(1), personalPointPane.getFreePointCell());
    }

    public void assignToken2ToOpponent(Pane destinationPane) {
        transferToken(commonGoals.get(1), destinationPane);
    }

    public void transferToken(CommonGoalView commonGoalView, Pane destinationPane) {
        try {
            commonGoalView.moveScoringTokenView(destinationPane);
        } catch (NullPointerException e) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "The player has obtained all possible point, so he can't obtain another point token",
                    "Player point table full"
            );
        } catch (EmptyStackException e) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "There are no more points that can be obtained from this common goal",
                    "Can't assign common goal point"
            );
        }
    }

    //For testing purpose

    public void fillBoard(@NotNull MouseEvent mouseEvent) {

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

        ((MyShelfieButton) mouseEvent.getSource()).setDisable(true);
        ((MyShelfieButton) mouseEvent.getSource()).setVisible(false);

        ((HBox) ((MyShelfieButton) mouseEvent.getSource()).getParent()).getChildren().remove(((MyShelfieButton) mouseEvent.getSource()));
    }

    public TileSubject[][] getTilesOnBoardMatrix() {
        return gameBoardViewController.toTileSubjectMatrix();
    }
}
