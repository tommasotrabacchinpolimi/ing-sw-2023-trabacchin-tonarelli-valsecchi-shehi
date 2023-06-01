package it.polimi.ingsw.view.gui;

import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.util.Objects;

/**
 * <p>Represent each controller inside the My Shelfie application.</p>
 * <p>More precisely this class is used to store every controller created for any javafx class</p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @see MyShelfieApplication
 * @see Initializable
 * @since 22/05/2023
 */
public abstract class MyShelfieController implements Initializable {

    private final Duration animationDuration = new Duration(400);

    private MyShelfieApplication myShelfieApplicationLauncher;

    public MyShelfieApplication getMyShelfieApplicationLauncher() {
        return myShelfieApplicationLauncher;
    }

    public void setMyShelfieApplicationLauncher(MyShelfieApplication myShelfieApplicationLauncher) {
        this.myShelfieApplicationLauncher = myShelfieApplicationLauncher;
    }

    public abstract void onGameStateChangedNotified();

    public abstract void onExceptionNotified();

    public Duration getAnimationDuration() {
        return new Duration(animationDuration.toSeconds());
    }
}
