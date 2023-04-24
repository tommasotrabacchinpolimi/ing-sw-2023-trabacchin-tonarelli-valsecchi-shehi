package it.polimi.ingsw.view.loginpage;

import it.polimi.ingsw.view.FontChangeListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class LoginPage extends Application {

    private static final String FXML_FILE_PATH = "/it.polimi.ingsw/layout/login/login-page.fxml";

    private static final GraphicsDevice SCREEN_DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static final int SCREEN_WIDTH = SCREEN_DEVICE.getDisplayMode().getWidth();
    private static final int SCREEN_HEIGHT = SCREEN_DEVICE.getDisplayMode().getHeight();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource(FXML_FILE_PATH));
        Scene scene = null;

        /* The window dialog login dimension is set according to the screen size. The height and width of the login page
         * is set to maintain the aspect ratio of the background image. The image set as background is located under
         * "src/main/resources/it.polimi.ingsw/graphical.resources/publisher.material/Display_5.jpg"
         *
         * Display_5.jpg has the following dimension: 1140 x 760
         * Standard display resolution are nowadays: 1920 x 1080
         * Based on these dimensions the initial game dialog should have a width (w) of 40% a0ccording to screen width,
         * in our case: w = (1920 * 40) / 100 = 768
         * This is also the width of the "Display_5.jpg" image. That fact means that the image is scaled by a certain
         * "x" percentage that is determined as follows: x = (864 * 100) / 1140 = 67.37%
         * To maintain the aspect ratio, the height (y) of the image should be set according to x percentage value:
         * y =  (67.37 * 760) / 100 = 512
         * To establish the height (h) percentage at which the window dialog should be set to not stretch
         * the image, the last calculation is: h = (576 * 100) / 1080 = 47.41%
         */
        try {
            scene = new Scene(fxmlLoader.load(), (SCREEN_WIDTH * 40.00 / 100.00), (SCREEN_HEIGHT * 47.41 / 100.00));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //stage.setResizable(false);
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);

        new FontChangeListener(scene, fxmlLoader.<LoginPageController>getController().rootPane, "infoText", 21);
        new FontChangeListener(scene, fxmlLoader.<LoginPageController>getController().rootPane, "nicknameInput", 17);

        stage.setWidth(scene.getWidth());
        stage.setHeight(scene.getHeight());

        //center stage in screen
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);

        stage.show();
    }
}
