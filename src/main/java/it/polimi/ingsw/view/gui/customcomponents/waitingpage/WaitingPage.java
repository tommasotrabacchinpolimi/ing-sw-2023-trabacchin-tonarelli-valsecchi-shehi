package it.polimi.ingsw.view.gui.customcomponents.waitingpage;

import javafx.scene.Scene;

public class WaitingPage extends BlockingPage{

    private static final String LOADING_GIF_PATH = "/it.polimi.ingsw/graphical.resources/misc/waiting_spinner.gif";


    public WaitingPage(Scene scene, String text) {
        super(scene);

       addToBlockingPage(new MyShelfieLabel(text, LOADING_GIF_PATH));
    }
}
