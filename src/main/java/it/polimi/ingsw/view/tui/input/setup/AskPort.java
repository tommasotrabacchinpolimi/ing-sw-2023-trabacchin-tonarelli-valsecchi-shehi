package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;


public class AskPort extends Input {
    private final String protocolChoice;

    private final String address;

    public AskPort(TUI tui, PrintStream out, String protocolChoice, String address) {
        super(tui, out);
        this.protocolChoice = protocolChoice;
        this.address = address;
    }

    @Override
    public void printPrompt() {
        getOut().println("Now insert a port number");
    }

    @Override
    public void readLine(String line) {
        int portNumber;
        try {
            portNumber = Integer.parseInt(line);
        } catch(Exception e) {
            getOut().println("Invalid port, lease try again...");
            getTUI().setCurrentInput(new AskPort(getTUI(), getOut(), protocolChoice, address));
            return;
        }

        if(portNumber<0 || portNumber>65536) {
            getOut().println("Invalid port, lease try again...");
            getTUI().setCurrentInput(new AskPort(getTUI(), getOut(), protocolChoice, address));
        }
        else {
            getTUI().setInputInProgress(false);
            if(protocolChoice.equals("1")) {
                try{
                    Client client = new Client(getTUI(), getTUI().getModel());
                    client.chosenSocket(portNumber, address);
                    getTUI().setLogicController(client);
                    getTUI().setCurrentInput(new AskNickname(getTUI(), getOut()));
                } catch(Exception ex) {
                    getOut().println("Network error");
                    System.exit(0);
                }
            }
            else {
                try{
                    Client client = new Client(getTUI(), getTUI().getModel());
                    client.chosenRMI(portNumber, address);
                    getTUI().setLogicController(client);
                    getTUI().setCurrentInput(new AskNickname(getTUI(), getOut()));
                } catch(Exception ex) {
                    getOut().println("Network error");
                    System.exit(0);
                }
            }
        }
    }
}
