package it.polimi.ingsw.view.gui.customcomponents.tileview;

import javafx.scene.layout.Pane;

public interface TileSubjectViewState {
    void tileStateAction(TileSubjectView tileSubjectView, Pane... parent);

    void reverseStateAction(TileSubjectView tileSubjectView);
}
