package it.polimi.ingsw.view;

import it.polimi.ingsw.view.gui.GUILauncher;
import it.polimi.ingsw.view.tui.TUIStateMachine;
import javafx.application.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;

public class AppClient {
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final int DIMROW_BOOKSHELF = 6;

    public static void main(String[] args) throws NotBoundException, IOException, ClassNotFoundException {
        if(args[0].equals("tui"))
            TUISetup();
        else if(args[0].equals("gui"))
            GUISetup(args);
    }

    public static void TUISetup() throws IOException, NotBoundException, ClassNotFoundException {
        TUIStateMachine tuiStateMachine = new TUIStateMachine();
        ViewData viewData = new ViewData(DIM_BOARD, DIMCOL_BOOKSHELF, DIMROW_BOOKSHELF);
        Client client = new Client(tuiStateMachine, viewData);
        System.out.println("Please, choose a connection method: 1) socket; 2) rmi.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String protocolChoice = bufferedReader.readLine();
        while(!protocolChoice.equals("1") && !protocolChoice.equals("2")){
            System.out.println("You typed the wrong number. Please, choose again a connection method: 1) socket; 2) rmi.");
            protocolChoice = bufferedReader.readLine();
        }
        System.out.println("Now insert server address:");
        String serverAddress = bufferedReader.readLine();
        System.out.println("Now insert server port:");
        String serverPort = bufferedReader.readLine();
        int port = Integer.parseInt(serverPort);

        if (protocolChoice.equals("1")) {
            client.chosenSocket(port, serverAddress);
        } else {
            client.chosenRMI(port, serverAddress);
        }

        tuiStateMachine.setLogicController(client);
        viewData.setUserInterface(tuiStateMachine);
        tuiStateMachine.setModel(viewData);
        tuiStateMachine.setup();
    }

    public static void GUISetup(String[] args) {
        Application.launch(GUILauncher.class, args);
    }
}
