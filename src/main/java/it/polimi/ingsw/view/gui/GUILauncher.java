package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.layout.maininterface.MainInterfaceController;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <p>This class is used to launch and interact with the Graphical Interface of the Application</p>
 * <p></p>
 */
public class GUILauncher extends MyShelfieApplication {

    private static final String LOGIN_PAGE_LAYOUT = "login/login-page.fxml";

    private static final String CONNECTION_PAGE_LAYOUT = "connection/connection-page.fxml";

    private static final String MAIN_INTERFACE_LAYOUT = "maininterface/main-interface.fxml";

    private GUI gui;

    /**
     * The application initialization method. This method is called immediately
     * after the Application class is loaded and constructed. An application may
     * override this method to perform initialization prior to the actual starting
     * of the application.
     *
     * <p>
     * The implementation of this method provided by the Application class does nothing.
     * </p>
     *
     * <p>
     * NOTE: This method is not called on the JavaFX Application Thread. An
     * application must not construct a Scene or a Stage in this
     * method.
     * An application may construct other JavaFX objects in this method.
     * </p>
     *
     * @throws Exception if something goes wrong
     */
    @Override
    public void init() throws Exception {
        super.init();

        this.gui = new GUI(this);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        setupScene(CONNECTION_PAGE_LAYOUT);

        setupStage(stage);

        stage.show();
    }

    public void goToLoginPage(){
        changeScene(LOGIN_PAGE_LAYOUT);
    }

    public void goToMainInterface() {
        changeToFullScreenStage(MAIN_INTERFACE_LAYOUT);
    }

    public void setGUI(GUI gui){
        this.gui = gui;
    }

    public GUI getGUI() {
        return gui;
    }

    public void handleRemoteException(String exception) {
        MyShelfieAlertCreator.displayErrorAlert(exception);
    }

    public void manageMainInterface() {
        goToMainInterface();
    }

    public void handleNewMessage(@NotNull Triple<String, List<String>, String> lastMessage) {
        try{
            ((MainInterfaceController) fxController).receivedMessageOperation(lastMessage.getFirst(), lastMessage.getThird());
        }catch(ClassCastException e) {
            errorInLoadingMyShelfieGame();
        }
    }
}
