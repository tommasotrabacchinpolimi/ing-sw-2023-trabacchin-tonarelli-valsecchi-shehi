package it.polimi.ingsw.view.gui.maininterface;

import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainInterfaceController extends MyShelfieController {
    public AnchorPane mainRootPane;
    public ScrollPane mainScrollPane;
    public VBox scrollContentBox;

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
