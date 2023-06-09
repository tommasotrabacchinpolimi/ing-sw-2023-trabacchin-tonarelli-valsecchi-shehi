package it.polimi.ingsw.view.gui.board;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BoardViewController extends MyShelfieController {

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
                        if (change.wasAdded())
                            box.setStyle("-fx-padding: 0.1em;");
                        if (change.wasRemoved())
                            box.setStyle("-fx-padding: 2em;");
                    }
                }));
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

    protected Map<Coordinate, StackPane> getItemTileBoxes() {
        return itemTileBoxes;
    }

    public void fillUpBoard() {
        for (Coordinate coordinate : itemTileBoxes.keySet()) {
            new TileSubjectView(itemTileBoxes.get(coordinate), TileSubject.CAT_BLACK);
        }
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

    public StackPane getItemTileBox(int x, int y) {
        List<Coordinate> itemTileBoxCoordinate = itemTileBoxes.keySet()
                .stream()
                .filter(c -> c.equalsToCoordinates(x, y)).toList();
        if (itemTileBoxCoordinate.size() == 1)
            return getItemTileBox(itemTileBoxCoordinate.get(0));
        else
            return null;
    }

    private TileSubject[][] toTileSubjectMatrix() {
        TileSubject[][] boardMatrix = new TileSubject[getMaxBoardRow()][getMaxBoardColumn()];

        itemTileBoxes.forEach( (coordinate, pane) -> {
            if(pane.getChildren().size() > 0 && pane.getChildren().size() == 1){
                try{
                    boardMatrix[coordinate.getX()][coordinate.getY()] = ((TileSubjectView) pane.getChildren().get(0)).getTileSubject();
                }catch(ClassCastException e) {
                    MyShelfieAlertCreator.displayErrorAlert(e);
                }
            }
        });

        return boardMatrix;
    }

    private int getMaxBoardRow() {
        int maxRow = 0;

        for(Coordinate coordinate : itemTileBoxes.keySet()) {
            if(coordinate.hasGraterRow(maxRow)){
                maxRow = coordinate.getX();
            }
        }

        return maxRow;
    }

    private int getMaxBoardColumn() {
        int maxColumn = 0;

        for(Coordinate coordinate : itemTileBoxes.keySet()) {
            if(coordinate.hasGraterColumn(maxColumn)){
                maxColumn = coordinate.getY();
            }
        }

        return maxColumn;
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }
}
