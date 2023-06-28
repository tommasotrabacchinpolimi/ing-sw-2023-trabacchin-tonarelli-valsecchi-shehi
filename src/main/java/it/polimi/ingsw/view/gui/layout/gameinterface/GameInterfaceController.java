package it.polimi.ingsw.view.gui.layout.gameinterface;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieApplication;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.FirstPlayerSeatView;
import it.polimi.ingsw.view.gui.customcomponents.commongoal.CommonGoalView;
import it.polimi.ingsw.view.gui.customcomponents.PersonalGoalView;
import it.polimi.ingsw.view.gui.customcomponents.pointpane.HPointPane;
import it.polimi.ingsw.view.gui.layout.board.BoardViewController;
import it.polimi.ingsw.view.gui.layout.bookshelf.PersonalBookshelfController;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.*;

public class GameInterfaceController extends MyShelfieController {

    @FXML
    private StackPane infoDisplayContainer;

    @FXML
    private Label infoContent;

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

    private final Map<TileSubjectView, Coordinate> tileBoardBoxOldParentCoordinate = new HashMap<>();

    private final List<Pair<EventHandler<MouseEvent>, TileSubjectView>> tileBoardHandler = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePersonalBookshelfController.disableAllButtons();

        tileBoxChildManager = new TileBoxChildManager(selectedTilesBox, gamePersonalBookshelfController, gameBoardViewController);

        selectedTilesBox.getChildren().addListener(tileBoxChildManager);

        tilesOnBoard = gameBoardViewController.getTilesOnBoard();
    }

    public void setupBasicInformation(MyShelfieApplication myShelfieApplicationLauncher) {

        setMyShelfieApplicationLauncher(myShelfieApplicationLauncher);

        gameBoardViewController.setMyShelfieApplicationLauncher(getMyShelfieApplicationLauncher());
        gamePersonalBookshelfController.setMyShelfieApplicationLauncher(getMyShelfieApplicationLauncher());
    }

    public void addGoalsToGameInterface(Integer[] point) {

        if(getGUILauncher().getGUIModel().getIdCommonGoals() != null) {
            commonGoals.add(new CommonGoalView(getGUILauncher().getGUIModel().getIdCommonGoals()[0], getGUILauncher().getGUIModel().getCommonGoals()[0], point[0]));

            commonGoals.add(new CommonGoalView(getGUILauncher().getGUIModel().getIdCommonGoals()[1], getGUILauncher().getGUIModel().getCommonGoals()[1], point[1]));
        } else {
            commonGoals.add(new CommonGoalView(null, getGUILauncher().getGUIModel().getCommonGoals()[0], 0));

            commonGoals.add(new CommonGoalView(null, getGUILauncher().getGUIModel().getCommonGoals()[1], 0));
        }

        personalGoal = new PersonalGoalView(getGUILauncher().getGUIModel().getPersonalGoal());

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

    private List<TileSubjectView> getSelectedTilesFromBox() {
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

    /**
     * If the {@code tile} has a mouse handler associated it is
     * removed
     *
     * @param tile the tile on which the mouse handler has to be
     *             removed
     */
    private void removeBoardHandlerTilePair(TileSubjectView tile) {

        Pair<EventHandler<MouseEvent>, TileSubjectView> removed = getHandlerTilePair(tile);

        if (removed != null) {
            removed.getValue().removeEventHandler(MouseEvent.MOUSE_CLICKED, removed.getKey());

            tileBoardHandler.remove(removed);
        }
    }

    /**
     * Retrieves a map that contains the binding between each
     * coordinate on the board and the tile that is displayed
     * inside it.
     *
     * @return a map that holds the tiles shown on board and
     * their position
     * @apiNote If no tile is at the coordinate specified,
     * a {@code null} value is used to represent the 'empty box'
     */
    public Map<Coordinate, TileSubjectView> getBoardContent() {
        return gameBoardViewController.getBoardState();
    }

    public Map<Coordinate, TileSubjectView> getThisPlayerBookshelfContent() {
        return gamePersonalBookshelfController.getBookshelfState();
    }

    /**
     * @param tile
     * @return {@code null} if the tile has no mouse handler associated
     */
    @Nullable
    private Pair<EventHandler<MouseEvent>, TileSubjectView> getHandlerTilePair(TileSubjectView tile) {

        return tileBoardHandler.stream()
                .filter(pair -> pair.getValue().equals(tile))
                .findFirst()
                .orElse(null);
    }

    @FXML
    private void handleSubmitAction(MouseEvent mouseEvent) throws InterruptedException {
        List<TileSubjectView> boardSelected = getClickedTilesFromBoard();
        List<TileSubjectView> boxSelected = getSelectedTilesFromBox();

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

                    getLogicController().dragTilesToBookShelf( getOrderedCoordinateTiles(tileBoxChildManager.getOrderedTilesFromBox()),
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
        tileBoardBoxOldParentCoordinate.put(tile, gameBoardViewController.getCoordinateFromTile(tile).orElse(null));
        tile.performAction(selectedTilesBox);
        tile.resetClick();
    }

    @FXML
    private void handleReverseAction(MouseEvent mouseEvent) {
        List<TileSubjectView> boardSelected = getClickedTilesFromBoard();
        List<TileSubjectView> boxSelected = getSelectedTilesFromBox();

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

    private void reinsertTilesOnBoard(TileSubjectView tile) {
        if(tile == null)
            return;

        tile.reverseAction();
        tileBoardBoxOldParentCoordinate.clear();
        gameBoardViewController.getTilesOnBoard().add(tile);
        addTilesOnBoardListener(tile);
    }

    public void removeOpponentTakenTiles(List<TileSubjectView> tilesTakenByOpponent) {
        tilesOnBoard.removeAll(tilesTakenByOpponent);

        tilesTakenByOpponent.forEach(tile -> {
            if(tile != null){
                removeBoardHandlerTilePair(tile);
                tile.resetClick();
            }
        });
    }

    public void startEndGameTokenAnimation(MouseEvent mouseEvent) {
        transferEndGameToken(personalPointPane.getEndTokenCell());
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

    public void startTokenAnimation1() {
        transferToken(0, personalPointPane.getFirstScoringTokenCell());
    }

    public void assignToken1ToOpponent(Pane destinationPane) {
        transferToken(0, destinationPane);
    }

    public void startTokenAnimation2() {
        transferToken(1, personalPointPane.getSecondScoringTokenCell());
    }

    public void assignToken2ToOpponent(Pane destinationPane) {
        transferToken(1, destinationPane);
    }

    public void transferToken(Integer commonGoalNumber, Pane destinationPane) {
        try {
            commonGoals.get(commonGoalNumber).moveScoringTokenView(destinationPane);
            commonGoals.get(commonGoalNumber).addTopScoringToken(getGUILauncher().getGUIModel().getAvailableScores().get(commonGoalNumber));
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

    public void fillBoard(TileSubject[][] tilesOnBoard) {

        gameBoardViewController.addTilesOnBoard(tilesOnBoard);

        this.tilesOnBoard.forEach(this::addTilesOnBoardListener);
    }

    public void fillBookshelf(TileSubject[][] newBookshelf) {

        for(int i = 0; i < newBookshelf.length; ++i) {
            for(int j = 0; j < newBookshelf[i].length; ++j) {
                if(newBookshelf[i][j] != null)
                    gamePersonalBookshelfController.forcedInsertionInBookshelf(newBookshelf[i][j], i, j);
            }
        }
    }

    public TileSubject[][] getTilesOnBoardMatrix() {
        return gameBoardViewController.toTileSubjectMatrix();
    }

    public boolean boxHasTiles() {
        return tileBoxChildManager.getAllTilesFromBox().size() > 0;
    }

    public boolean boxHasChild() {
        return selectedTilesBox.getChildren().size() > 0;
    }

    public void updatedBoardBoxContentAt(Coordinate updatedBoxCoordinate, TileSubject updatedSubject) {
        gameBoardViewController.addTileAt(updatedSubject, updatedBoxCoordinate);
    }

    public void reverseClientPlayed() {
        restoreClientPlayed(tileBoxChildManager.getAllTilesFromBox());
    }

    public void restoreClientPlayed(List<TileSubjectView> tilesToRestore) {
        if(tilesToRestore == null)
            return;

        tilesToRestore.forEach(this::reinsertTilesOnBoard);

        if(gamePersonalBookshelfController.getSelectedColumn() != -1)
            gamePersonalBookshelfController.deselectAnyColumn();
    }

    public void disableGameButton() {
        setGameButtonAction(false);
    }

    public void enableGameButton() {
        setGameButtonAction(true);
    }

    private void setGameButtonAction(boolean enableState) {
        confirmButton.setFocusTraversable(enableState);
        reverseButton.setFocusTraversable(enableState);

        confirmButton.setMouseTransparent(!enableState);
        reverseButton.setMouseTransparent(!enableState);
    }

    public void confirmInput() {
        tileBoardBoxOldParentCoordinate.clear();
    }

    public void externalSetActiveTilesOnBoardNoneSelected() {
        gameBoardViewController.setActiveTilesOnBoardNoneSelected();
    }

    public void updateTextInDisplayContent(String text) {
        infoContent.setText(text);
    }

    @NotNull
    private List<Coordinate> getOrderedCoordinateTiles(@NotNull List<TileSubjectView> orderedSelectedTiles) {
        List<Coordinate> orderedCoordinateTiles = new ArrayList<>();

        orderedSelectedTiles.forEach(tile -> {
            orderedCoordinateTiles.add(tileBoardBoxOldParentCoordinate.get(tile));
        });

        return orderedCoordinateTiles;
    }

    public void addFirstPlayerSeat() {
        personalPointPane.addFirstPlayerSeat(new FirstPlayerSeatView());
    }

    /**
     *
     * @param commonGoalNumber
     * @return
     * @throws NullPointerException in case that the common goal has not been initialized
     */
    public int getDisplayedCommonGoalScore(int commonGoalNumber) throws NullPointerException{
        if(commonGoals.size() == 0 || commonGoals.get(commonGoalNumber) == null)
            throw new NullPointerException();

        return commonGoals.get(commonGoalNumber).getTopTokenPoint();
    }
}