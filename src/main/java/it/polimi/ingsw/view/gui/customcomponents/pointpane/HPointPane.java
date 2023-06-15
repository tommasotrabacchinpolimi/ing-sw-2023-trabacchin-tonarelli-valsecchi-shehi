package it.polimi.ingsw.view.gui.customcomponents.pointpane;

import javafx.beans.NamedArg;

public class HPointPane extends PointPane{

    public HPointPane() {
        this(4);
    }

    protected HPointPane(@NamedArg("point cell number") int pointCellNumber) {
        super(1, pointCellNumber);
    }
}
