package it.polimi.ingsw.view.gui.layout.board;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.InputCheck;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.EndGameTokenView;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieAnimation;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfiePathTransition;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieRotateTransition;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieScaleTransition;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class BoardViewController extends MyShelfieController {

    @FXML
    private EndGameTokenView endGameTokenView;

    @FXML
    private StackPane endGameBox;

    @FXML
    private StackPane boardEndGameBoxContainer;

    @FXML
    private StackPane boardRootPane;

    @FXML
    private GridPane livingRoomGridPane;

    @FXML
    private AnchorPane livingRoomAnchor;

    @FXML
    private StackPane itemTileBox03;

    @FXML
    private StackPane itemTileBox04;

    @FXML
    private StackPane itemTileBox13;

    @FXML
    private StackPane itemTileBox14;

    @FXML
    private StackPane itemTileBox15;

    @FXML
    private StackPane itemTileBox22;

    @FXML
    private StackPane itemTileBox23;

    @FXML
    private StackPane itemTileBox24;

    @FXML
    private StackPane itemTileBox25;

    @FXML
    private StackPane itemTileBox26;

    @FXML
    private StackPane itemTileBox31;

    @FXML
    private StackPane itemTileBox32;

    @FXML
    private StackPane itemTileBox33;

    @FXML
    private StackPane itemTileBox34;

    @FXML
    private StackPane itemTileBox35;

    @FXML
    private StackPane itemTileBox36;

    @FXML
    private StackPane itemTileBox37;

    @FXML
    private StackPane itemTileBox38;

    @FXML
    private StackPane itemTileBox40;

    @FXML
    private StackPane itemTileBox41;

    @FXML
    private StackPane itemTileBox42;

    @FXML
    private StackPane itemTileBox43;

    @FXML
    private StackPane itemTileBox44;

    @FXML
    private StackPane itemTileBox45;

    @FXML
    private StackPane itemTileBox46;

    @FXML
    private StackPane itemTileBox47;

    @FXML
    private StackPane itemTileBox48;

    @FXML
    private StackPane itemTileBox50;

    @FXML
    private StackPane itemTileBox51;

    @FXML
    private StackPane itemTileBox52;

    @FXML
    private StackPane itemTileBox53;

    @FXML
    private StackPane itemTileBox54;

    @FXML
    private StackPane itemTileBox55;

    @FXML
    private StackPane itemTileBox56;

    @FXML
    private StackPane itemTileBox57;

    @FXML
    private StackPane itemTileBox62;

    @FXML
    private StackPane itemTileBox63;

    @FXML
    private StackPane itemTileBox64;

    @FXML
    private StackPane itemTileBox65;

    @FXML
    private StackPane itemTileBox66;

    @FXML
    private StackPane itemTileBox73;

    @FXML
    private StackPane itemTileBox74;

    @FXML
    private StackPane itemTileBox75;

    @FXML
    private StackPane itemTileBox84;

    @FXML
    private StackPane itemTileBox85;

    private Map<Coordinate, StackPane> itemTileBoxes;

    private final List<TileSubjectView> tilesOnBoard = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemTileBoxes = new HashMap<>();

        setUpBoxesMap(itemTileBox03, itemTileBox04, itemTileBox13, itemTileBox14, itemTileBox15, itemTileBox22,
                itemTileBox23, itemTileBox24, itemTileBox25, itemTileBox26, itemTileBox31, itemTileBox32, itemTileBox33,
                itemTileBox34, itemTileBox35, itemTileBox36, itemTileBox37, itemTileBox38, itemTileBox40, itemTileBox41,
                itemTileBox42, itemTileBox43, itemTileBox44, itemTileBox45, itemTileBox46, itemTileBox47, itemTileBox48,
                itemTileBox50, itemTileBox51, itemTileBox52, itemTileBox53, itemTileBox54, itemTileBox55, itemTileBox56,
                itemTileBox57, itemTileBox62, itemTileBox63, itemTileBox64, itemTileBox65, itemTileBox66, itemTileBox73,
                itemTileBox74, itemTileBox75, itemTileBox84, itemTileBox85);

        itemTileBoxes.values()
                .forEach(box -> box.getChildren().addListener((ListChangeListener<? super Node>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            box.setStyle("-fx-padding: 0.1em;");
                        }
                        if (change.wasRemoved() && box.getChildren().size() == 0) {
                            box.setStyle("-fx-padding: 2.9em;");
                        }

                        setActiveTilesOnBoardNoneSelected();
                    }
                }));

        endGameBox.getChildren().addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    endGameBox.setStyle("-fx-padding: 3.2em;");
                }
            }
        });
    }

    private void setUpBoxesMap(StackPane... itemTileBoxes) {
        for (StackPane itemTileBox : itemTileBoxes) {
            insertInItemTileBoxesMap(itemTileBox);
        }
    }

    private Coordinate getItemTileBoxCoordinate(StackPane itemTileBox) {
        return new Coordinate(GridPane.getRowIndex(itemTileBox), GridPane.getColumnIndex(itemTileBox));
    }

    private void insertInItemTileBoxesMap(StackPane itemTileBox) {
        itemTileBoxes.put(getItemTileBoxCoordinate(itemTileBox), itemTileBox);
    }

    public void fillUpBoard(TileSubject[][] boardMatrix) {
        for (int i = 0; i < boardMatrix.length; ++i) {
            for (int j = 0; j < boardMatrix[i].length; ++j) {
                if (boardMatrix[i][j] != null)
                    tilesOnBoard.add(new TileSubjectView(getItemTileBox(i, j), boardMatrix[i][j]));
            }
        }
    }

    public TileSubjectView getTileSubjectView(Coordinate coordinate) {

        StackPane itemTileBox = getItemTileBox(coordinate);

        if (itemTileBox != null && itemTileBox.getChildren().size() > 0) {
            try {
                return (TileSubjectView) getItemTileBox(coordinate).getChildren().get(0);
            } catch (ClassCastException e) {
                MyShelfieAlertCreator.displayErrorAlert(e);
                return null;
            }
        } else return null;
    }

    public StackPane getItemTileBox(Coordinate coordinate) {
        if (itemTileBoxes.keySet()
                .stream()
                .filter(c -> c.equals(coordinate))
                .toList()
                .size() > 0) {
            return itemTileBoxes.get(coordinate);
        } else {
            return null;
        }
    }

    public StackPane getItemTileBox(int row, int column) {
        List<Coordinate> chosenItemTileBox = itemTileBoxes.keySet()
                .stream()
                .filter(c -> c.equalsToCoordinates(row, column))
                .toList();

        if (chosenItemTileBox.size() == 1) {
            return getItemTileBox(chosenItemTileBox.get(0));
        } else {
            return null;
        }
    }

    public TileSubject[][] toTileSubjectMatrix() {
        TileSubject[][] boardMatrix = new TileSubject[getMaxBoardRow()][getMaxBoardColumn()];

        getBoardState().forEach((coordinate, tile) -> {
            if(tile != null)
                boardMatrix[coordinate.getX()][coordinate.getY()] = tile.getTileSubject();
        });

        /*itemTileBoxes.forEach((coordinate, pane) -> {
            if (pane.getChildren().size() > 0 && pane.getChildren().size() == 1) {
                try {
                    boardMatrix[coordinate.getX()][coordinate.getY()] = ((TileSubjectView) pane.getChildren().get(0)).getTileSubject();
                } catch (ClassCastException e) {
                    MyShelfieAlertCreator.displayErrorAlert(e);
                }
            }
        });*/

        return boardMatrix;
    }

    private int getMaxBoardRow() {
        int maxRow = 0;

        for (Coordinate coordinate : itemTileBoxes.keySet()) {
            if (coordinate.hasGraterRow(maxRow)) {
                maxRow = coordinate.getX();
            }
        }

        return (maxRow + 1);
    }

    private int getMaxBoardColumn() {
        int maxColumn = 0;

        for (Coordinate coordinate : itemTileBoxes.keySet()) {
            if (coordinate.hasGraterColumn(maxColumn)) {
                maxColumn = coordinate.getY();
            }
        }

        return (maxColumn + 1);
    }

    private Optional<Coordinate> getCoordinateFromTile(TileSubjectView tile) {

        for (StackPane box : itemTileBoxes.values()) {
            if (box.getChildren().size() == 1 && box.getChildren().get(0) == tile) {
                return Optional.of(getItemTileBoxCoordinate(box));
            }
        }

        return Optional.empty();
    }

    public void setActiveTilesOnBoardNoneSelected() {
        setActiveTilesOnBoard(InputCheck.findIndexAllActiveTilesInBoard(toTileSubjectMatrix()));
    }

    public void setActiveTilesOnBoardOneSelected(TileSubject[][] bookshelf, TileSubjectView tile) throws WrongChosenTilesFromBoardException {
        setActiveTilesOnBoard(InputCheck.findIndexActiveAfterOneChosenTile(toTileSubjectMatrix(),
                getCoordinateFromTile(tile).orElseThrow(), bookshelf), getCoordinateFromTile(tile).orElse(null));
        tile.setClickable();
    }

    public void setActiveTilesOnBoardTwoSelected(TileSubject[][] bookshelf, TileSubjectView tile1, TileSubjectView tile2) throws WrongChosenTilesFromBoardException {
        setActiveTilesOnBoard(InputCheck.findIndexActiveAfterTwoChosenTiles(toTileSubjectMatrix(),
                        getCoordinateFromTile(tile1).orElseThrow(), getCoordinateFromTile(tile2).orElseThrow(), bookshelf),
                getCoordinateFromTile(tile1).orElse(null), getCoordinateFromTile(tile2).orElse(null));
        tile1.setClickable();
        tile2.setClickable();
    }

    public void setActiveTilesOnBoard(List<Coordinate> activeTileCoordinates, Coordinate... excluded) {

        Set<Coordinate> coordinates;

        if (Arrays.asList(excluded).size() > 0)
            coordinates = itemTileBoxes.keySet()
                    .stream()
                    .filter(c -> !Arrays.asList(excluded).contains(c))
                    .collect(Collectors.toSet());
        else
            coordinates = itemTileBoxes.keySet();

        if(activeTileCoordinates == null) {
            for(Coordinate coordinate : coordinates) {
                if (getTileSubjectView(coordinate) != null)
                    getTileSubjectView(coordinate).disable();
            }

            return;
        }

        for (Coordinate coordinate : coordinates) {
            if (activeTileCoordinates.contains(coordinate)) {
                if (getTileSubjectView(coordinate) != null)
                    getTileSubjectView(coordinate).setClickable();
            } else {
                if (getTileSubjectView(coordinate) != null)
                    getTileSubjectView(coordinate).disable();
            }
        }
    }

    public void moveEndGameTokenView(Pane destinationPane) {
        MyShelfieAnimation.build()
                .addAnimation(new MyShelfieRotateTransition(8.0, 0.0))
                .addAnimation(new MyShelfiePathTransition(endGameTokenView, destinationPane))
                .addAnimation(new MyShelfieScaleTransition(0.7, 0.7))
                .playMyShelfieAnimation(endGameTokenView, value -> {
                    endGameBox.getChildren().remove(endGameTokenView);

                    destinationPane.getChildren().add(endGameTokenView);

                    endGameTokenView.setStyle(endGameTokenView.getStyle() + "-fx-padding: 2.2em;");

                    endGameTokenView.setScaleX(1.0);
                    endGameTokenView.setScaleY(1.0);

                    endGameTokenView.setTranslateX(0.0);
                    endGameTokenView.setTranslateY(0.0);
                });
    }

    public Map<Coordinate, TileSubjectView> getBoardState() {
        Map<Coordinate, TileSubjectView> boardState = new HashMap<>();

        itemTileBoxes.forEach((coordinate, box) -> {
            try {
                TileSubjectView tileInBox = (TileSubjectView) box.getChildren().stream().findFirst().orElse(null);
                boardState.put(coordinate, tileInBox);
            } catch (ClassCastException e) {
                MyShelfieAlertCreator.displayErrorAlert(e);
            }
        });

        return boardState;
    }

    public List<TileSubjectView> getTilesOnBoard() {
        return tilesOnBoard;
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }
}
