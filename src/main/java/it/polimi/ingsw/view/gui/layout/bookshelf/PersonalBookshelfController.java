package it.polimi.ingsw.view.gui.layout.bookshelf;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.BookshelfView;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieTriangleButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.utils.color.MyShelfieColor.GAMBOGE;

public class PersonalBookshelfController extends BookshelfController{

    @FXML
    private BookshelfView personalBookshelfView;

    @FXML
    private GridPane personalBookshelfRoot;

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

    private int selectedColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedColumn = -1;
        bookshelfCells = personalBookshelfView.getBookshelfCells();
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
            highlightSelectedColum(selectedColumn);
            disableButtons(chosen);
        } else if (selectedColumn == chosen) {
            selectedColumn = -1;
            resetSelectedColum(chosen);
            enableButtons(chosen);
        }
    }

    /**
     *
     * @param column
     */
    protected void highlightSelectedColum(int column) {
        bookshelfCells.keySet()
                .stream()
                .filter(c -> c.getY() == column)
                .forEach(c ->
                        bookshelfCells.get(c)
                                .setStyle("-fx-background-color:" + GAMBOGE.getRGBAStyleSheet(0.57) + ";"));
    }

    protected void resetSelectedColum(int column) {
        bookshelfCells.keySet()
                .stream()
                .filter(c -> c.getY() == column)
                .forEach(c -> bookshelfCells.get(c).setStyle("-fx-background-color: transparent;"));
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

        resetSelectedColum(selectedColumn);

        selectedColumn = -1;

        disableAllButtons();

        Arrays.asList(leftTriangle, midLeftTriangle, midTriangle, midRightTriangle, rightTriangle)
                .forEach(MyShelfieTriangleButton::reverseState);
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

    protected MyShelfieTriangleButton getTriangleButton(){
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

    public TileSubject[][] getTileSubjectBookshelfMatrix() {
        return personalBookshelfView.toTileSubjectMatrix();
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void insertTilesInBookshelf(@NotNull Map<Coordinate, TileSubjectView> coordinateTiles) {
        coordinateTiles.forEach((coordinate, tile) -> {
            tile.performAction(getTriangleButton(), getBookshelfCellAt(coordinate).orElseThrow());
        });
    }
}
