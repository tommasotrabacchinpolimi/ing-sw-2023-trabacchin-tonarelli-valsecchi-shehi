package it.polimi.ingsw.view.gui.bookshelf;

import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.customcomponents.uitoolkit.MyShelfieColor.GAMBOGE;
import static it.polimi.ingsw.view.gui.customcomponents.uitoolkit.MyShelfieColor.KOBICHA;

public class BookshelfController extends MyShelfieController {

    @FXML
    private StackPane bookshelfRootPane;

    @FXML
    private GridPane bookshelfGrid;

    @FXML
    private StackPane cell00;

    @FXML
    private StackPane cell01;

    @FXML
    private StackPane cell02;

    @FXML
    private StackPane cell03;

    @FXML
    private StackPane cell04;

    @FXML
    private StackPane cell10;

    @FXML
    private StackPane cell11;

    @FXML
    private StackPane cell12;

    @FXML
    private StackPane cell13;

    @FXML
    private StackPane cell14;

    @FXML
    private StackPane cell20;

    @FXML
    private StackPane cell21;

    @FXML
    private StackPane cell22;

    @FXML
    private StackPane cell23;

    @FXML
    private StackPane cell24;

    @FXML
    private StackPane cell30;

    @FXML
    private StackPane cell31;

    @FXML
    private StackPane cell32;

    @FXML
    private StackPane cell33;

    @FXML
    private StackPane cell34;

    @FXML
    private StackPane cell40;

    @FXML
    private StackPane cell41;

    @FXML
    private StackPane cell42;

    @FXML
    private StackPane cell43;

    @FXML
    private StackPane cell44;

    @FXML
    private StackPane cell50;

    @FXML
    private StackPane cell51;

    @FXML
    private StackPane cell52;

    @FXML
    private StackPane cell53;

    @FXML
    private StackPane cell54;

    @FXML
    private Pane bookshelfImagePane;

    private Map<Coordinate, StackPane> bookshelfCells;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookshelfCells = new HashMap<>();

        setupBookshelfMap(cell00, cell01, cell02, cell03, cell04,
                cell10, cell11, cell12, cell13, cell14,
                cell20, cell21, cell22, cell23, cell24,
                cell30, cell31, cell32, cell33, cell34,
                cell40, cell41, cell42, cell43, cell44,
                cell50, cell51, cell52, cell53, cell54);

        bookshelfCells.forEach((coordinate, cell) -> {
            cell.getChildren().addListener((ListChangeListener<? super Node>) node -> {

                while(node.next()) {
                    if (node.wasRemoved())
                        cell.setStyle("-fx-padding: 2em;" +
                                "-fx-background-color: transparent;");

                    if(node.wasAdded()){
                        cell.setStyle("-fx-padding: 0.1em;"+
                                "-fx-background-color: " + KOBICHA.getRGBAStyleSheet(0.37) + ";");

                        node.getList()
                                .stream()
                                .map(element -> (TileSubjectView) element)
                                .forEach(TileSubjectView::disable);
                    }
                }
            });
        });
    }

    private void setupBookshelfMap(@NotNull StackPane... bookshelfCells) {

        for (StackPane bookshelfCell : bookshelfCells) {
            insertInBookshelfMap(bookshelfCell);
        }
    }

    private void insertInBookshelfMap(StackPane bookshelfCell) {
        bookshelfCells.put(getItemTileBoxCoordinate(bookshelfCell), bookshelfCell);
    }

    @NotNull
    @Contract("_ -> new")
    private Coordinate getItemTileBoxCoordinate(StackPane bookshelfCell) {
        return new Coordinate(GridPane.getRowIndex(bookshelfCell), GridPane.getColumnIndex(bookshelfCell));
    }

    public Pane getTileCellAt(Coordinate c) {
        for (Coordinate coordinate : bookshelfCells.keySet()) {
            if (coordinate.equals(c)) {
                return bookshelfCells.get(coordinate);
            }
        }

        return null;
    }

    protected void highlightSelectedColum(int column) {
        bookshelfCells.keySet()
                .stream()
                .filter(c -> c.getY() == column)
                .forEach(c -> bookshelfCells.get(c).setStyle("-fx-background-color:" + GAMBOGE.getRGBAStyleSheet(0.57) + ";"));
    }

    protected void resetSelectedColum(int column) {
        bookshelfCells.keySet()
                .stream()
                .filter(c -> c.getY() == column)
                .forEach(c -> bookshelfCells.get(c).setStyle("-fx-background-color: transparent;"));
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }
}
