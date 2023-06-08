package it.polimi.ingsw.view.gui.bookshelf;

import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieTriangleButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BookshelfCommandController extends MyShelfieController {

    @FXML
    private GridPane bookshelfCommandRoot;

    @FXML
    private MyShelfieTriangleButton leftTriangle;

    @FXML
    private MyShelfieTriangleButton midLeftTriangle;

    @FXML
    private MyShelfieTriangleButton midTriangle;

    @FXML
    private MyShelfieTriangleButton midRightTriangle;

    @FXML
    private MyShelfieTriangleButton rightTriangle;

    @FXML
    private StackPane commandBookshelfView;

    @FXML
    private BookshelfController commandBookshelfViewController;

    private int selectedColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedColumn = -1;
    }

    public void insertTilesInBookshelf(@NotNull List<TileSubjectView> tiles, @NotNull List<Coordinate> coordinates) {
        if (tiles.size() != coordinates.size()) {
            //display Error
            return;
        }

        tiles.forEach(tile -> tile.performAction(getTriangleButton(), commandBookshelfViewController.getTileCellAt(coordinates.get(tiles.indexOf(tile)))));
    }

    public void insertTilesInBookshelf(@NotNull List<TileSubjectView> tiles, @NotNull Coordinate... coordinates) {
        insertTilesInBookshelf(tiles, Arrays.asList(coordinates));
    }

    @FXML
    private void selectedColumn(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(leftTriangle) && leftTriangle.isActive()) {
            updateSelectedColumn(0);
        } else if (mouseEvent.getSource().equals(midLeftTriangle) && midLeftTriangle.isActive()) {
            updateSelectedColumn(1);
        } else if (mouseEvent.getSource().equals(midTriangle) && midTriangle.isActive()) {
            updateSelectedColumn(2);
        } else if (mouseEvent.getSource().equals(midRightTriangle) && midRightTriangle.isActive()) {
            updateSelectedColumn(3);
        } else if (mouseEvent.getSource().equals(rightTriangle) && rightTriangle.isActive()) {
            updateSelectedColumn(4);
        }
    }

    private void updateSelectedColumn(int chosen) {
        if (selectedColumn == -1) {
            selectedColumn = chosen;
            commandBookshelfViewController.highlightSelectedColum(selectedColumn);
            disableButtons(chosen);
        } else if (selectedColumn == chosen) {
            selectedColumn = -1;
            commandBookshelfViewController.resetSelectedColum(chosen);
            enableButtons(chosen);
        }
    }

    private void disableButtons(int chosen) {
        List<MyShelfieTriangleButton> buttons = new ArrayList<>(Arrays.asList(leftTriangle, midLeftTriangle, midTriangle, midRightTriangle, rightTriangle));

        for (MyShelfieTriangleButton button : buttons) {
            if (buttons.indexOf(button) != chosen) {
                button.setInactive();
            }
        }
    }

    private void enableButtons(int chosen) {
        List<MyShelfieTriangleButton> buttons = new ArrayList<>(Arrays.asList(leftTriangle, midLeftTriangle, midTriangle, midRightTriangle, rightTriangle));

        for (MyShelfieTriangleButton button : buttons) {
            if (buttons.indexOf(button) != chosen) {
                button.setActive();
            }
        }
    }

    public void disableAllButtons() {
        if (selectedColumn == -1)
            Arrays.asList(leftTriangle, midLeftTriangle, midTriangle, midRightTriangle, rightTriangle)
                    .forEach(MyShelfieTriangleButton::setInactive);
    }

    public void enableAllButtons() {
        if (selectedColumn == -1)
            Arrays.asList(leftTriangle, midLeftTriangle, midTriangle, midRightTriangle, rightTriangle)
                    .forEach(MyShelfieTriangleButton::setActive);
    }

    public void deselectAnyColumn() {
        if (selectedColumn == -1)
            return;

        commandBookshelfViewController.resetSelectedColum(selectedColumn);

        selectedColumn = -1;

        disableAllButtons();

        Arrays.asList(leftTriangle, midLeftTriangle, midTriangle, midRightTriangle, rightTriangle)
                .forEach(MyShelfieTriangleButton::reverseState);
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

    private MyShelfieTriangleButton getTriangleButton(){
        switch (selectedColumn) {
            case 0 -> {
                return leftTriangle;
            }
            case 1 -> {
                return midLeftTriangle;
            }
            case 2 -> {
                return midTriangle;
            }
            case 3 -> {
                return midRightTriangle;
            }
            case 4 -> {
                return rightTriangle;
            }

            default -> {
                return null;
            }
        }
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }
}
