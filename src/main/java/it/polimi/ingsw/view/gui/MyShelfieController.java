package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.LogicInterface;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    private MyShelfieApplication myShelfieApplicationLauncher;

    public MyShelfieApplication getMyShelfieApplicationLauncher() {
        return myShelfieApplicationLauncher;
    }

    public void setMyShelfieApplicationLauncher(MyShelfieApplication myShelfieApplicationLauncher) {
        this.myShelfieApplicationLauncher = myShelfieApplicationLauncher;
    }

    protected GUILauncher getGUILauncher() {
        try {
            return (GUILauncher) getMyShelfieApplicationLauncher();
        } catch (ClassCastException e) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "The program that starts the application was not the correct one",
                    "cannot run My Shelfie game"
            );

            myShelfieApplicationLauncher.errorInLoadingMyShelfieGame("The application request a critical information");
        }

        return null;
    }

    private GUI getGUI() {
        return getGUILauncher().getGUI();
    }

    protected LogicInterface getLogicController() {
        return getGUI().getLogicController();
    }

    protected List<String> getOpponentPlayers() {
        return getGUI().getModel()
                .getPlayers()
                .stream()
                .filter(nickName -> !nickName.equals(getGUI().getModel().getThisPlayer()))
                .toList();
    }
}
