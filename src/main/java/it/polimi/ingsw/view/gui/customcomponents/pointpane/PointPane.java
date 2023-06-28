package it.polimi.ingsw.view.gui.customcomponents.pointpane;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

abstract class PointPane extends GridPane implements MyShelfieComponent {

    private static final String BACKGROUND_IMAGE = "/it.polimi.ingsw/graphical.resources/misc/page_base_lighten.png";

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * The list of point cells in the point pane.
     */

    private final List<PointCell> pointCells = new ArrayList<>();

    /**
     * Constructs a `PointPane` with the specified number of rows and columns.
     *
     * @param row    The number of rows in the point pane.
     * @param column The number of columns in the point pane.
     */
    PointPane(int row, int column) {
        super();

        addColumnConstraints(column);

        addRowConstraints(row);

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j){
                pointCells.add(new PointCell());
                add(getLastPointCell(), j, i);
            }
        }

        setCSS();

        setPrefSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        applyDecorationAsDefault(new MyShelfieDarkShadow(), new MyShelfieRoundEdge(MyShelfieRoundEdgeType.MINIMUM));
    }


    /**
     * Returns the last `PointCell` in the `pointCells` list.
     *
     * @return The last `PointCell` in the `pointCells` list.
     */
    private PointCell getLastPointCell() {
        return pointCells.get((pointCells.size() - 1));
    }


    /**
     * Adds column constraints to the grid pane.
     *
     * @param column The number of columns for which constraints are added.
     */
    private void addColumnConstraints(int column) {
        for (int i = 0; i < column; ++i) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0);
            columnConstraints.setHalignment(HPos.CENTER);
            columnConstraints.setFillWidth(true);

            getColumnConstraints().add(columnConstraints);
        }
    }


    /**
     * Adds row constraints to the grid pane.
     *
     * @param row The number of rows for which constraints are added.
     */
    private void addRowConstraints(int row) {
        for (int j = 0; j < row; ++j) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0);
            rowConstraints.setValignment(VPos.CENTER);
            rowConstraints.setFillHeight(true);

            getRowConstraints().add(rowConstraints);
        }
    }

    /**
     * Method to set the CSS style
     */
    private void setCSS() {
        setStyle("-fx-background-image: url('" + getMyShelfieResource(BACKGROUND_IMAGE) + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;" +
                "-fx-hgap: 1em;" +
                "-fx-vgap: 1em;" +
                "-fx-padding: 1em;");
    }

    /**
     * Retrieves a free PointCell from the list of PointCells.
     *
     * @return A free PointCell.
     * @throws NullPointerException If there are no free PointCells available.
     */
    public PointCell getFreePointCell() throws NullPointerException {
        for (PointCell cell : pointCells) {
            if (cell.getChildren().size() == 0) {
                return cell;
            }
        }

        throw new NullPointerException();
    }

    /**
     * Retrieve the element that has to be customized
     *
     * @return the element that needs to be customized
     */
    @Override
    public Node getCustomizedNode() {
        return this;
    }

    /**
     * Retrieves the list of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the list of default decorations
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
