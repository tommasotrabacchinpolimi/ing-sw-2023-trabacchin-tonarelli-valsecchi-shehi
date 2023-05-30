package it.polimi.ingsw.view.gui;

import javafx.stage.Stage;

/**
 * <p>This class is used to launch and interact with the Graphical Interface of the Application</p>
 * <p></p>
 */
public class GUILauncher extends MyShelfieApplication {

    private static final String LOGIN_PAGE_LAYOUT = "login/login-page.fxml";

    private static final String CONNECTION_PAGE_LAYOUT = "connection/connection-page.fxml";

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

        this.gui = new GUI();
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
        setUpScene(CONNECTION_PAGE_LAYOUT);

        setUpStage(stage);

        stage.show();
    }

    public void goToLoginPage(){
        changeScene(LOGIN_PAGE_LAYOUT);
    }

    public void setGUI(GUI gui){
        this.gui = gui;
    }

    public GUI getGUI() {
        return gui;
    }
}
