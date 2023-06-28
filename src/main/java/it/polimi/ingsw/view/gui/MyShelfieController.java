package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.LogicInterface;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents each controller inside the My Shelfie application.
 * More precisely, this class is used to store every controller created for any JavaFX class.
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

    /**
     * Gets the MyShelfieApplication launcher associated with this controller.
     *
     * @return the MyShelfieApplication launcher
     */
    public MyShelfieApplication getMyShelfieApplicationLauncher() {
        return myShelfieApplicationLauncher;
    }

    /**
     * Sets the MyShelfieApplication launcher for this controller.
     *
     * @param myShelfieApplicationLauncher the MyShelfieApplication launcher to be set
     */
    public void setMyShelfieApplicationLauncher(MyShelfieApplication myShelfieApplicationLauncher) {
        this.myShelfieApplicationLauncher = myShelfieApplicationLauncher;
    }

    /**
     * Gets the GUILauncher associated with this controller.
     *
     * @return the GUILauncher
     */
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

    /**
     * Gets the GUI associated with this controller.
     *
     * @return the GUI
     */
    private GUI getGUI() {
        return getGUILauncher().getGUI();
    }

    /**
     * Gets the LogicInterface controller associated with this controller.
     *
     * @return the LogicInterface controller
     */
    protected LogicInterface getLogicController() {
        return getGUI().getLogicController();
    }

    /**
     * Gets a list of opponent players.
     *
     * @return a list of opponent player names
     */
    protected List<String> getOpponentPlayers() {
        return getGUI().getModel()
                .getPlayers()
                .stream()
                .filter(nickName -> !nickName.equals(getGUI().getModel().getThisPlayer()))
                .toList();
    }
}
