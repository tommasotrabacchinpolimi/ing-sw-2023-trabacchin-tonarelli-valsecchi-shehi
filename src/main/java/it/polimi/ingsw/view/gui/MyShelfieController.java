package it.polimi.ingsw.view.gui;

import javafx.fxml.Initializable;

/**
 * <p>Represent each controller inside the My Shelfie application.</p>
 * <p>More precisely this class is used to store every controller created for any javafx class</p>
 *
 * @see MyShelfieApplication
 * @see Initializable
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 22/05/2023
 */
public abstract class MyShelfieController implements Initializable {
    private MyShelfieApplication myShelfieApplicationLauncher;

    public MyShelfieApplication getMyShelfieApplicationLauncher() {
        return myShelfieApplicationLauncher;
    }

    public void setMyShelfieApplicationLauncher(MyShelfieApplication myShelfieApplicationLauncher) {
        this.myShelfieApplicationLauncher = myShelfieApplicationLauncher;
    }
}
