package it.polimi.ingsw.view;

import java.io.IOException;

public interface UiInterface {
    LogicInterface getLogicController() ;

    ViewData getModel();

    void setModel(ViewData model) ;

    void onNewMessage(String sender);

    void onCurrentPlayerChanged(String newCurrentPlayer);

    void showWinner();

    void onException() throws IOException;

    void onGameStateChanged() throws IOException;
}
