package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.gui.loginpage.LoginPage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <p>This class is used to define the default path and commands allowed by every class that has to deal with the graphics
 * interface</p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @see Application
 * @since 19/05/2023
 */
public abstract class MyShelfieApplication extends Application {

    /**
     * <p>Default FXML file path folder where every layout is located</p>
     */
    private static final String FXML_FILE_PATH = "/it.polimi.ingsw/layout/";

    /**
     * <p>Path to the font used</p>
     */
    private static final String FONT_PATH = "/it.polimi.ingsw/graphical.resources/font/SpecialElite-Regular.ttf";

    /**
     * <p>Path to the font used</p>
     */
    private static final String GAME_ICON_PATH = "/it.polimi.ingsw/graphical.resources/publisher.material/icon_50x50px.png";

    /**
     * The screen size
     */
    private static final GraphicsDevice SCREEN_DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    /**
     * the screen width
     */
    private static final int SCREEN_WIDTH = SCREEN_DEVICE.getDisplayMode().getWidth();

    /**
     * The screen height
     */
    private static final int SCREEN_HEIGHT = SCREEN_DEVICE.getDisplayMode().getHeight();

    /**
     * <p>Width stage change listener</p>
     * <p>This listener is used to preserve the aspect ratio of the stage according to the scene that is shown</p>
     */
    private ChangeListener<? super Number> widthChangeListener;

    /**
     * <p>Height stage change listener</p>
     * <p>This listener is used to preserve the aspect ratio of the stage according to the scene that is shown</p>
     */
    private ChangeListener<? super Number> heightChangeListener;

    /**
     * The scene relative to the interface shown
     */
    private Scene scene;

    /**
     * A (type of) Pane that contains every graphical component of the scene
     */
    private Pane rootPane;

    /**
     * The controller for the layout graphic
     *
     * @see MyShelfieController
     */
    private MyShelfieController fxController;

    /**
     * Stage window of the whole application
     */
    private Stage stage;

    private static MyShelfieAdapter ui;

    /**
     * This method loads the font in the graphical user interface
     */
    private static void loadMyShelfieFont() {
        Font.loadFont(LoginPage.class.getResourceAsStream(FONT_PATH), Font.getDefault().getSize());
    }

    /**
     * Retrieves the {@linkplain #FXML_FILE_PATH FXML layout folder}
     *
     * @return the FXML layout folder path as text
     */
    public static String getFXMLFilePath() {
        return FXML_FILE_PATH;
    }

    /**
     * Given a "fxml" file name that contains a layout for a page of the game, this method retrieves the complete
     * file-path to the layout file
     *
     * @param fileName the name of the layout
     * @return the complete file-path to a file containing a layout page structure
     */
    public static String getFXMLFile(String fileName) {
        return getFXMLFilePath() + fileName;
    }

    /**
     * @return the screen width
     */
    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    /**
     * @return the screen height
     */
    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    /**
     * <p>Construct a {@link Scene scene} from a specific file containing a static layout (.fxml)</p>
     *
     * @param FXMLFileName      the layout file reference
     * @param percentWidth      the percent width of the scene based on screen size
     * @param percentHeight     the percent height of the scene based on screen size
     * @param rootPaneContainer the main pane container for every graphical component in the layout file
     * @return the scene personalized as described
     * @apiNote <ul>
     * <li>if {@code percentWidth} and/or {@code percentHeight} are equals to {@code 0.0} (or negative),
     * the scene will be set to fit its content.</li>
     * <li>if {@code rootPaneContainer} is null, tha main container for the scene will be
     * the pane in the static layout file passed as parameter</li>
     * </ul>
     */
    public Scene setUpScene(final String FXMLFileName, final double percentWidth, final double percentHeight, Pane rootPaneContainer) {
        Platform.setImplicitExit(false);

        loadMyShelfieFont();

        return setScene(FXMLFileName, percentWidth, percentHeight, rootPaneContainer);
    }

    /**
     * <p>Construct a {@link Scene scene} from a specific file containing a static layout (.fxml)</p>
     * <p>More precisely the scene retrieved by this method will have {@code height} and {@code width}
     * based on screen size and a percent passed as parameter</p>
     *
     * @param FXMLFileName  the layout file reference
     * @param percentWidth  the percent width of the scene based on screen size
     * @param percentHeight the percent height of the scene based on screen size
     * @return the scene personalized as described
     * @apiNote if {@code percentWidth} and/or {@code percentHeight} are equals to {@code 0.0} (or negative),
     * the scene will be set to fit its content.
     */
    public Scene setUpScene(final String FXMLFileName, final double percentWidth, final double percentHeight) {
        return setUpScene(FXMLFileName, percentWidth, percentHeight, null);
    }

    /**
     * <p>Set up a scene with no preferred width or height</p>
     * <p>The automatic size of the scene will be set to fit up the content</p>
     *
     * @param FXMLFileName the layout file reference
     * @return the scene personalized as described
     */
    public Scene setUpScene(final String FXMLFileName) {
        return setUpScene(FXMLFileName, 0.0, 0.0);
    }

    public Scene setUpSceneWithPane(final String FXMLFileName, final double percentWidth, final double percentHeight) {
        Pane rootPane = new Pane();
        rootPane.setId("rootPaneContainer");

        return setUpScene(FXMLFileName, percentWidth, percentHeight, rootPane);
    }

    public Scene setUpSceneWithPane(final String FXMLFileName) {
        return setUpSceneWithPane(FXMLFileName, 0.0, 0.0);
    }

    public Scene setScene(final String FXMLFileName, final double percentWidth, final double percentHeight, Pane rootPaneContainer) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource(MyShelfieApplication.getFXMLFile(FXMLFileName)));

        try {
            if (rootPaneContainer == null) {
                rootPaneContainer = fxmlLoader.load();
            } else {
                rootPaneContainer.getChildren().add(fxmlLoader.load());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setFxController(fxmlLoader.getController());

        fxController.setMyShelfieApplicationLauncher(this);

        if (ui != null)
            ui.bindShelfieControllerAndAdapter(fxController);

        if (percentWidth > 0.0 && percentHeight > 0.0)
            scene = new Scene(rootPaneContainer, (SCREEN_WIDTH * percentWidth / 100.00), (SCREEN_HEIGHT * percentHeight / 100.00));
        else
            scene = new Scene(rootPaneContainer);

        setRootPane(rootPaneContainer);

        Platform.runLater(() -> {
            setDynamicFontSize(scene);
        });

        return scene;
    }

    public Scene setScene(final String FXMLFileName, final double percentWidth, final double percentHeight) {
        return setScene(FXMLFileName, percentWidth, percentHeight, null);
    }

    /**
     * <p>Set up a scene with no preferred width or height</p>
     * <p>The automatic size of the scene will be set to fit up the content</p>
     *
     * @param FXMLFileName the layout file reference
     * @return the scene personalized as described
     */
    public Scene setScene(final String FXMLFileName) {
        return setScene(FXMLFileName, 0.0, 0.0);
    }

    public void setUpStage(final Stage stage) {

        this.stage = stage;

        this.stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);

        setPreserveRatio();
        setMinStageSize();

        //center stage in screen
        this.stage.centerOnScreen();

        this.stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

        //set icon in taskbar
        this.stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(GAME_ICON_PATH))));
    }

    /**
     * <p>This method can be used to construct a {@link Scene scene} from a
     * specific file containing the layout (.fxml)</p>
     * <p>More precisely the scene retrieved by this method will have {@code height} and {@code width}
     * based on screen size (full screen)</p>
     *
     * @param FXMLFileName the layout file reference
     * @return the scene personalized as described
     */
    public Scene setUpFullScreenScene(final String FXMLFileName) {
        loadMyShelfieFont();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource(MyShelfieApplication.getFXMLFile(FXMLFileName)));

        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return scene;
    }

    public void changeScene(Scene newScene) {

        stage.close();

        this.scene = newScene;

        stage.setScene(scene);

        //center stage in screen
        stage.centerOnScreen();

        stage.show();
    }

    /**
     * This method allows the user to resize the content in the page and font will change automatically according to it
     *
     * @param scene the scene on which set the font dynamically
     */
    public void setDynamicFontSize(Scene scene) {
        Pane rootPane = (Pane) scene.getRoot();

        SizeChangeListener sizeChangeListener = new SizeChangeListener(rootPane, SCREEN_WIDTH, SCREEN_HEIGHT);

        rootPane.widthProperty().addListener(sizeChangeListener);
        rootPane.heightProperty().addListener(sizeChangeListener);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public MyShelfieController getFxController() {
        return fxController;
    }

    public void setFxController(MyShelfieController fxController) {
        this.fxController = fxController;
    }

    private void closeWindowEvent(WindowEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public static UI getUi() {
        return ui;
    }

    public static void setUI(MyShelfieAdapter ui) {
        MyShelfieApplication.ui = ui;
    }

    private void setPreserveRatio() {
        stage.sizeToScene();

        widthChangeListener = (observable, oldValue, newValue) -> {
            stage.heightProperty().removeListener(heightChangeListener);
            stage.setHeight(newValue.doubleValue() / 2.0);
            stage.heightProperty().addListener(heightChangeListener);
        };

        heightChangeListener = (observable, oldValue, newValue) -> {
            stage.widthProperty().removeListener(widthChangeListener);
            stage.setWidth(newValue.doubleValue() * 2.0);
            stage.widthProperty().addListener(widthChangeListener);
        };

        stage.widthProperty().addListener(widthChangeListener);
        stage.heightProperty().addListener(heightChangeListener);

        stage.maximizedProperty().addListener((observableValue, newValue, oldValue) -> {
            if (observableValue.getValue().booleanValue()) {
                stage.heightProperty().removeListener(heightChangeListener);
                stage.widthProperty().removeListener(widthChangeListener);
            } else if (newValue) {
                stage.widthProperty().addListener(widthChangeListener);
                stage.heightProperty().addListener(heightChangeListener);
            }
        });
    }

    private void setMinStageSize() {
        stage.setOnShown( value -> {
            stage.setMinHeight(stage.getHeight() * 0.67);
            stage.setMinWidth(stage.getWidth() * 0.67);
        });
    }
}
