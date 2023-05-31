package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.LogicInterface;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

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

    private LogicInterface logicController;

    public MyShelfieApplication getMyShelfieApplicationLauncher() {
        return myShelfieApplicationLauncher;
    }

    public void setMyShelfieApplicationLauncher(MyShelfieApplication myShelfieApplicationLauncher) {
        this.myShelfieApplicationLauncher = myShelfieApplicationLauncher;
    }

    public LogicInterface getLogicController() {
        return logicController;
    }

    public void setLogicController(LogicInterface logicController) {
        this.logicController = logicController;
    }

    public abstract void onGameStateChangedNotified();

    public abstract void onExceptionNotified();

    public void displaySimpleAlert(String contentText, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    public void displaySimpleAlert(String contentText) {
        displaySimpleAlert(contentText, null);
    }

    public void displaySimpleAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.setHeaderText(e.getCause().toString());
        alert.showAndWait();
    }
}
