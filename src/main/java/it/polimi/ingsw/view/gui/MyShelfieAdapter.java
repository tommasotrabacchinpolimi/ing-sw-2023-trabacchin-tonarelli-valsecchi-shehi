package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.LogicInterface;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;
import it.polimi.ingsw.view.gui.loginpage.LoginPage;
import javafx.application.Application;

/**
 *
 * @version 1.0
 * @since 23-05-2023
 */
public class MyShelfieAdapter extends UI {

    private MyShelfieController myShelfieController;

    @Override
    public void launchUI() {
        LoginPage.setUI(this);
        Application.launch(LoginPage.class);
    }

    @Override
    public void onNewMessage(String sender) {

    }

    @Override
    public void onCurrentPlayerChanged(String newCurrentPlayer) {

    }

    @Override
    public void showWinner() {

    }

    @Override
    public void onException() {

    }

    @Override
    public void onGameStateChanged() {
        myShelfieController.onGameStateChangedNotified();
    }

    public MyShelfieController getMyShelfieController() {
        return myShelfieController;
    }

    public void setMyShelfieController(MyShelfieController myShelfieController) {
        this.myShelfieController = myShelfieController;
    }
}
