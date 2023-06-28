package it.polimi.ingsw.view;

import it.polimi.ingsw.view.gui.GUILauncher;
import it.polimi.ingsw.view.tui.TUI;
import javafx.application.Application;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * The main class for the client application.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AppClient {
    /**
     * Size of the Board
     */
    private static final int DIM_BOARD = 9;
    /**
     * Dimension of the Column of bookshelf
     */
    private static final int DIMCOL_BOOKSHELF = 5;
    /**
     * Dimension of the Column of bookshelf
     */
    private static final int DIMROW_BOOKSHELF = 6;

    /**
     * The entry point of the client application.
     *
     * @param args The command-line arguments.
     * @throws NotBoundException      if the remote object is not bound.
     * @throws IOException           if an I/O error occurs.
     * @throws ClassNotFoundException if a class cannot be found.
     */
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

    /**
     * Sets up the Text User Interface (TUI).
     *
     * @throws IOException           if an I/O error occurs.
     * @throws NotBoundException      if the remote object is not bound.
     * @throws ClassNotFoundException if a class cannot be found.
     */
    public static void TUISetup() throws IOException, NotBoundException, ClassNotFoundException {
        TUI tui = new TUI();
        tui.launchUI();
    }

    /**
     * Sets up the Graphical User Interface (GUI).
     *
     * @param args The command-line arguments.
     */
    public static void GUISetup(String[] args) {
        Application.launch(GUILauncher.class, args);
    }
}
