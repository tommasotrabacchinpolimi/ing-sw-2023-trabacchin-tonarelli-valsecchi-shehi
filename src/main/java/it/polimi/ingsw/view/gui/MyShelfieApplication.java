package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.loginpage.LoginPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public abstract class MyShelfieApplication extends Application{
    private static final String FXML_FILE_PATH = "/it.polimi.ingsw/layout/login/";
    private static final String FONT_PATH = "/it.polimi.ingsw/graphical.resources/font/SpecialElite-Regular.ttf";
    private static final GraphicsDevice SCREEN_DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static final int SCREEN_WIDTH = SCREEN_DEVICE.getDisplayMode().getWidth();
    private static final int SCREEN_HEIGHT = SCREEN_DEVICE.getDisplayMode().getHeight();

    private static void loadMyShelfieFont() {
        Font.loadFont(LoginPage.class.getResourceAsStream(FONT_PATH), Font.getDefault().getSize());
    }

    public static String getFXMLFilePath() {
        return FXML_FILE_PATH;
    }

    public static String getFXMLFile(String fileName) {
        return getFXMLFilePath() + fileName;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

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

    public static void centerStageInScreen(Stage stage) {
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);
    }
}
