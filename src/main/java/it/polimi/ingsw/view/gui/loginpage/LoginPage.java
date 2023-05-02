package it.polimi.ingsw.view.gui.loginpage;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import it.polimi.ingsw.view.gui.FontChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPage extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = createLoginPageWindow();

        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);

        FontChangeListener.setResizableFont(scene, "welcomeText", 21);
        FontChangeListener.setResizableFont(scene, "nicknameInput", 17);
        FontChangeListener.setResizableFont(scene, "joinButton", 12);
        FontChangeListener.setResizableFont(scene, "createButton", 12);

        stage.setWidth(scene.getWidth());
        stage.setHeight(scene.getHeight());

        //center stage in screen
        MyShelfieApplication.centerStageInScreen(stage);

        stage.show();
    }

    /**
     * <p>The window dialog login dimension is set according to the screen size. The height and width of the login page
     * is set to maintain the aspect ratio of the
     * <a href="file: /resources/it/polimi/ingsw/graphical/resources/publisher/material/Display_5.jpg">
     *     background image
     *     </a>.
     * </p>
     * <p>Display_5.jpg has the following dimension: 1140 x 760
     * Standard display resolution are nowadays: 1920 x 1080</p>
     * <p>Based on these dimensions the initial game dialog should have a width (w) of 40% a0ccording to screen width,
     * in our case: w = (1920 * 40) / 100 = 768
     * This is also the width of the "Display_5.jpg" image. That fact means that the image is scaled by a certain
     * "x" percentage that is determined as follows: x = (864 * 100) / 1140 = 67.37%
     * To maintain the aspect ratio, the height (y) of the image should be set according to x percentage value:
     * y =  (67.37 * 760) / 100 = 512
     * To establish the height (h) percentage at which the window dialog should be set to not stretch
     * the image, the last calculation is: h = (576 * 100) / 1080 = 47.41%</p>
     */
    private Scene createLoginPageWindow() {
        return MyShelfieApplication.setUpScene("login-page.fxml", 40, 47.41);
    }
}
