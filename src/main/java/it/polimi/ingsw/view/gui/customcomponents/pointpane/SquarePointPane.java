package it.polimi.ingsw.view.gui.customcomponents.pointpane;

import javafx.beans.NamedArg;

/**
 * The `SquarePointPane` class represents a square point pane, a specialized type of `PointPane` with an equal number
 * of rows and columns. It is used to display a grid of point cells in a square shape.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class SquarePointPane extends PointPane {

    /**
     * Creates an instance of `SquarePointPane` with a default point cell number of 4.
     */
    public SquarePointPane() {
        this(4);
    }

    /**
     * Creates an instance of `SquarePointPane` with the specified point cell number.
     *
     * @param pointCellNumber The number of point cells in each row and column of the pane.
     */
    protected SquarePointPane(@NamedArg("point cell number") int pointCellNumber) {
        super(pointCellNumber / 2, pointCellNumber / 2);
    }
}
