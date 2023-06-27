package it.polimi.ingsw.view.gui.customcomponents.pointpane;

import javafx.beans.NamedArg;
/**
 * The `HPointPane` class represents a horizontal point pane, a specialized type of `PointPane` with a single row.
 * It is used to display a row of point cells horizontally.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class HPointPane extends PointPane {

    /**
     * Creates an instance of `HPointPane` with a default point cell number of 4.
     */
    public HPointPane() {
        this(4);
    }

    /**
     * Creates an instance of `HPointPane` with the specified point cell number.
     *
     * @param pointCellNumber The number of point cells in the pane.
     */
    protected HPointPane(@NamedArg("point cell number") int pointCellNumber) {
        super(1, pointCellNumber);
    }
}
