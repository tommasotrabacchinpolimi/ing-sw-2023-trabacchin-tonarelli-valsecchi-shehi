package it.polimi.ingsw.view.gui;

import javafx.stage.Stage;

public class GUI extends MyShelfieApplication{

    private static final String LOGIN_PAGE_LAYOUT = "login/login-page.fxml";

    private static final String CONNECTION_PAGE_LAYOUT = "connection/connection-page.fxml";

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
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        setUpScene(CONNECTION_PAGE_LAYOUT);

        setUpStage(stage);

        stage.show();
    }

    public String getLoginPageLayout() {
        return LOGIN_PAGE_LAYOUT;
    }

    public String getConnectionPageLayout() {
        return CONNECTION_PAGE_LAYOUT;
    }
}
