package it.polimi.ingsw.view;

import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.GUILauncher;
import it.polimi.ingsw.view.tui.ConsoleAsynchReader;
import it.polimi.ingsw.view.tui.TUIStateMachine;
import javafx.application.Application;

import java.io.IOException;
import java.rmi.NotBoundException;

public class MainApp {
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final int DIMROW_BOOKSHELF = 6;

    public static void main(String[] args) throws NotBoundException, IOException, ClassNotFoundException {
        //if(args[0].equals("tui"))
            TUISetup("rmi", "localhost", 4321);
        //else if(args[0].equals("gui"))
            //GUISetup(args);
    }

    public static void TUISetup(String choice, String host, int port) throws IOException, NotBoundException, ClassNotFoundException {
        TUIStateMachine tuiStateMachine = new TUIStateMachine();
        ViewData viewData = new ViewData(DIM_BOARD, DIMCOL_BOOKSHELF, DIMROW_BOOKSHELF);
        Client client = new Client(tuiStateMachine, viewData);
        if (choice.equals("rmi")) {
            client.chosenRMI(port, host);
        } else if (choice.equals("socket")) {
            client.chosenSocket(port, host);
        }

        tuiStateMachine.setLogicController(client);
        viewData.setUserInterface(tuiStateMachine);
        tuiStateMachine.setModel(viewData);
        //new Thread(new ConsoleAsynchReader(tuiStateMachine)).start();
        tuiStateMachine.setup();
    }

    public static void GUISetup(String[] args) {
        Application.launch(GUILauncher.class, args);
    }
}
