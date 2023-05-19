package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.loginpage.LoginPage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * <p>This class is used to define the default path and commands allowed by every class that has to deal with the graphics
 * interface</p>
 *
 * @see Application
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 19/05/2023
 */
public abstract class MyShelfieApplication extends Application{

    /**
     * <p>Default FXML file path folder where every layout is located</p>
     */
    private static final String FXML_FILE_PATH = "/it.polimi.ingsw/layout/";

    /**
     * <p>Path to the font used</p>
     */
    private static final String FONT_PATH = "/it.polimi.ingsw/graphical.resources/font/SpecialElite-Regular.ttf";

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
     *
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
     * <p>This method can be used to construct a {@link Scene scene} from a
     *    specific file containing the layout (.fxml)</p>
     * <p>More precisely the scene retrieved by this method will have {@code height} and {@code width}
     *    based on screen size and a percent passed as parameter</p>
     *
     * @param FXMLFileName the layout file reference
     * @param percentWidth the percent width of the scene based on screen size
     * @param percentHeight the percent height of the scene based on screen size
     *
     * @return the scene personalized as described
     */
    public static Scene setUpScene(final String FXMLFileName, final double percentWidth, final double percentHeight) {
        loadMyShelfieFont();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource(MyShelfieApplication.getFXMLFile(FXMLFileName)));

        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load(), (SCREEN_WIDTH * percentWidth / 100.00),
                    (SCREEN_HEIGHT * percentHeight / 100.00));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return scene;
    }

    public static Scene setUpScene(final String FXMLFileName) {
        loadMyShelfieFont();

        FXMLLoader fxmlLoader = new FXMLLoader(MyShelfieApplication.class.getResource(MyShelfieApplication.getFXMLFile(FXMLFileName)));

        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return scene;
    }

    public static Scene setUpSceneWithPane(final String FXMLFileName) {
        loadMyShelfieFont();

        FXMLLoader fxmlLoader = new FXMLLoader(MyShelfieApplication.class.getResource(MyShelfieApplication.getFXMLFile(FXMLFileName)));

        Pane rootPane = new Pane();
        rootPane.setId("rootPaneContainer");

        try {
            rootPane.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Scene(rootPane);
    }

    public static void setUpStage(final Stage stage, final Scene scene) {
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);

        stage.sizeToScene();

        //center stage in screen
        stage.centerOnScreen();
    }

    /**
     * <p>This method can be used to construct a {@link Scene scene} from a
     *    specific file containing the layout (.fxml)</p>
     * <p>More precisely the scene retrieved by this method will have {@code height} and {@code width}
     *    based on screen size (full screen)</p>
     *
     * @param FXMLFileName the layout file reference
     *
     * @return the scene personalized as described
     */
    public static Scene setUpFullScreenScene(final String FXMLFileName) {
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

    /**
     * This method allows the user to resize the content in the page and font will change automatically according to it
     *
     * @param scene the scene on which set the font dynamically
     */
    public static void setDynamicFontSize(Scene scene) {
        Pane rootPane = (Pane) scene.getRoot();

        SizeChangeListener sizeChangeListener = new SizeChangeListener(rootPane);

        rootPane.widthProperty().addListener(sizeChangeListener);
        rootPane.heightProperty().addListener(sizeChangeListener);
    }
}
