package it.polimi.ingsw.view;

import it.polimi.ingsw.view.gui.GUILauncher;
import it.polimi.ingsw.view.tui.TUI;
import javafx.application.Application;

import java.io.IOException;
import java.rmi.NotBoundException;

public class AppClient {
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final int DIMROW_BOOKSHELF = 6;

    public static void main(String[] args) throws NotBoundException, IOException, ClassNotFoundException {
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "10000");
        if(args.length == 2) {
            System.setProperty("java.rmi.server.hostname", args[1]);
        }
        if(args[0].equals("tui"))
            TUISetup();
        else if(args[0].equals("gui"))
            GUISetup(args);
    }

    public static void TUISetup() throws IOException, NotBoundException, ClassNotFoundException {
        TUI tui = new TUI();
        tui.launchUI();
    }

    public static void GUISetup(String[] args) {
        Application.launch(GUILauncher.class, args);
    }
}
