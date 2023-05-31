package it.polimi.ingsw.view;

import it.polimi.ingsw.view.tui.ConsoleAsynchReader;
import it.polimi.ingsw.view.tui.TUIStateMachine;

import java.io.IOException;
import java.rmi.NotBoundException;

public class MainApp {
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final int DIMROW_BOOKSHELF = 6;
    public static void main(String[] args) throws NotBoundException, IOException, ClassNotFoundException {
        TUISetup("socket", "localhost",1234);

    }

    public static void TUISetup(String choice, String host, int port) throws IOException, NotBoundException, ClassNotFoundException {
        TUIStateMachine tuiStateMachine = new TUIStateMachine();
        ViewData viewData = new ViewData(DIM_BOARD, DIMCOL_BOOKSHELF,DIMROW_BOOKSHELF);
        Client client = new Client(tuiStateMachine, viewData);
        if(choice.equals("rmi")) {
            client.chosenRMI(port, host);
        }
        else if(choice.equals("socket")) {
            client.chosenSocket(port, host);
        }
        tuiStateMachine.setLogicController(client);
        viewData.setUserInterface(tuiStateMachine);
        tuiStateMachine.setModel(viewData);
        //new Thread(new ConsoleAsynchReader(tuiStateMachine)).start();
        tuiStateMachine.setup();

    }
}
