package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>This class is used to define some default path and commands allowed
 * by every class that has to deal with the graphical user interface</p>
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
    private static final String FXML_FILE_PATH = "/it.polimi.ingsw/gui/layout/";

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
    private static final Screen SCREEN_DEVICE = Screen.getPrimary();

    /**
     * the screen width
     */
    private static final double SCREEN_WIDTH = SCREEN_DEVICE.getVisualBounds().getWidth();

    /**
     * The screen height
     */
    private static final double SCREEN_HEIGHT = SCREEN_DEVICE.getVisualBounds().getHeight();

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
    protected MyShelfieController fxController;

    /**
     * Stage window of the whole application
     */
    private Stage stage;

    /**
     * Change listener size that preserves the aspect ratio of the window while resizing
     *
     * @see WindowSizeChangeListener
     */
    private WindowSizeChangeListener windowSizeChangeListener;

    /**
     * This method loads the font in the graphical user interface
     */
    private static void loadMyShelfieFont() {
        Font.loadFont(MyShelfieApplication.class.getResourceAsStream(FONT_PATH), Font.getDefault().getSize());
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
    @NotNull
    @Contract(pure = true)
    public static String getFXMLFile(String fileName) {
        return getFXMLFilePath() + fileName;
    }

    /**
     * <p>Set up a scene with no preferred width or height</p>
     * <p>The automatic size of the scene will be set to fit up the content</p>
     *
     * @param FXMLFileName the layout file reference
     * @return the scene personalized as described
     */
    public Scene setupScene(final String FXMLFileName) {
        return setupScene(FXMLFileName, 0.0, 0.0);
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
    public Scene setupScene(final String FXMLFileName, final double percentWidth, final double percentHeight) {
        return setupScene(FXMLFileName, percentWidth, percentHeight, null);
    }

    /**
     * <p>Construct a {@link Scene scene} from a specific file containing a static layout
     * ({@code .fxml})</p>
     *
     * @param FXMLFileName      the layout file reference
     * @param percentWidth      the percent width of the scene based on screen size
     * @param percentHeight     the percent height of the scene based on screen size
     * @param rootPaneContainer the main pane container for every graphical component in the layout file
     * @return the scene personalized as described
     * @apiNote <ul>
     *     <li>if {@code percentWidth} and/or {@code percentHeight} are equals to {@code 0.0} (or negative),
     *     the scene will be set to fit its content.</li>
     *
     *     <li>if {@code rootPaneContainer} is null, tha main container for the scene will be
     *     the pane in the static layout file passed as parameter</li>
     * </ul>
     */
    public Scene setupScene(final String FXMLFileName, final double percentWidth, final double percentHeight, Pane rootPaneContainer) {
        Platform.setImplicitExit(false);

        loadMyShelfieFont();

        return setScene(FXMLFileName, percentWidth, percentHeight, rootPaneContainer);
    }

    public Scene setupSceneWithPane(final String FXMLFileName) {
        return setupSceneWithPane(FXMLFileName, 0.0, 0.0);
    }

    public Scene setupSceneWithPane(final String FXMLFileName, final double percentWidth, final double percentHeight) {
        Pane rootPane = new Pane();

        rootPane.setId("rootPaneContainer");

        return setupScene(FXMLFileName, percentWidth, percentHeight, rootPane);
    }

    public Scene setupMaximizedScene(final String FXMLFileName) {
        return setupScene(FXMLFileName, 100.0, 100.0, null);
    }

    /**
     * <p>Set up a scene with no preferred width or height</p>
     * <p>The automatic size of the scene will be set to fit up the content</p>
     *
     * @param FXMLFileName the layout file reference
     * @return the scene personalized as described
     */
    private Scene setScene(final String FXMLFileName) {
        return setScene(FXMLFileName, 0.0, 0.0);
    }

    private Scene setScene(final String FXMLFileName, final double percentWidth, final double percentHeight) {
        return setScene(FXMLFileName, percentWidth, percentHeight, null);
    }

    /**
     *
     * @param FXMLFileName
     * @param percentWidth
     * @param percentHeight
     * @param rootPaneContainer
     * @return
     */
    private Scene setScene(final String FXMLFileName, double percentWidth, double percentHeight, Pane rootPaneContainer) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MyShelfieApplication.getFXMLFile(FXMLFileName)));

        try {
            if (rootPaneContainer == null) {
                rootPaneContainer = fxmlLoader.load();
            } else {
                rootPaneContainer.getChildren().add(fxmlLoader.load());
            }
        } catch (IOException e) {
            errorInLoadingMyShelfieGame();
        }

        setupFxController(fxmlLoader.getController());

        if (percentWidth > 0.0 && percentHeight > 0.0) {
            percentHeight = Math.min(percentHeight, 100.0) / 100.0;
            percentWidth = Math.min(percentWidth, 100.0) / 100.0;
            scene = new Scene(rootPaneContainer, (SCREEN_WIDTH * percentWidth), (SCREEN_HEIGHT * percentHeight));
        }else
            scene = new Scene(rootPaneContainer);

        setRootPane(rootPaneContainer);

        Platform.runLater(() -> {
            setDynamicFontSize(scene);
        });

        return scene;
    }

    public void setupStage(final Stage stage) {

        setStage(stage, false, false);

        this.stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

        //set icon in taskbar
        this.stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(GAME_ICON_PATH))));
    }

    public void setupFullScreenStage(final Stage stage) {
        setStage(stage, true, false);
    }

    public void setupMaximizedStage(final Stage stage) {
        setStage(stage, false, true);
    }

    private void setStage(final Stage stage, final boolean fullScreen, final boolean maximized) {
        this.stage = stage;

        this.stage.setScene(scene);

        if(fullScreen){
            this.stage.setFullScreen(true);
            this.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            this.stage.setResizable(false);
        }else if(maximized) {
            this.stage.setMaximized(true);
            this.stage.setFullScreen(false);
            this.stage.setResizable(false);
        }

        stage.setOnShown(value -> {
            setFocusOnBackground();
            setPreserveRatio();
        });

        this.stage.centerOnScreen();
    }

    public void changeScene(Scene newScene) {

        stage.close();

        stage.widthProperty().removeListener(windowSizeChangeListener);
        stage.heightProperty().removeListener(windowSizeChangeListener);

        this.scene = newScene;

        stage.setScene(scene);

        stage.setOnShown(value -> {
            setFocusOnBackground();
            setPreserveRatio();
        });

        //center stage in screen
        stage.centerOnScreen();

        stage.show();
    }

    public void changeScene(final String FXMLFileName) {
        changeScene(setScene(FXMLFileName));
    }

    public void changeToFullScreenStage(final String FXMLFileName) {
        changeScene(setScene(FXMLFileName));

        setupFullScreenStage(stage);
    }

    /**
     * This method allows the user to resize the content in the page and font will change automatically according to it
     *
     * @param scene the scene on which set the font dynamically
     */
    public void setDynamicFontSize(@NotNull Scene scene) {
        Pane rootPane = (Pane) scene.getRoot();

        TextSizeChangeListener textSizeChangeListener = new TextSizeChangeListener(rootPane, SCREEN_WIDTH, SCREEN_HEIGHT);

        rootPane.widthProperty().addListener(textSizeChangeListener);
        rootPane.heightProperty().addListener(textSizeChangeListener);
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    private void setupFxController(MyShelfieController fxController) {
        if(fxController == null) {
            errorInLoadingMyShelfieGame();
        }

        setFxController(fxController);
        assert fxController != null;
        fxController.setMyShelfieApplicationLauncher(this);
    }

    private void setFxController(MyShelfieController fxController) {
        this.fxController = fxController;
    }

    public void setFocusOnBackground() {
        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();
    }

    public void errorInLoadingMyShelfieGame() {
        MyShelfieAlertCreator.displayErrorAlert(
                "The graphical interface can't be loaded correctly, the application will be terminated",
                "Cannot load My Shelfie game"
        );

        closeWindowEvent(1);
    }

    private void closeWindowEvent(int exitCode) {
        closeWindowEvent(null, exitCode);
    }

    private void closeWindowEvent(WindowEvent event) {
        closeWindowEvent(event, 0);
    }

    private void closeWindowEvent(WindowEvent event, int exitCode) {
        Platform.exit();
        System.exit(exitCode);
    }

    private void setPreserveRatio() {
        stage.sizeToScene();

        windowSizeChangeListener = new WindowSizeChangeListener(stage);

        stage.widthProperty().addListener(windowSizeChangeListener);
        stage.heightProperty().addListener(windowSizeChangeListener);
    }
}
