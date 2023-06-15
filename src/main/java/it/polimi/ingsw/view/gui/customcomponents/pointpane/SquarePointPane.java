package it.polimi.ingsw.view.gui.customcomponents.pointpane;

import javafx.beans.NamedArg;

public class SquarePointPane extends PointPane{

    public SquarePointPane() {
        this(4);
    }

    protected SquarePointPane(@NamedArg("point cell number") int pointCellNumber) {
        super(pointCellNumber / 2, pointCellNumber / 2);
    }
}
