package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

/**
 * The AskPort class represents an input prompt for entering a port in order to create a connection to the server.
 * It extends the {@link Input} class.
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskPort extends Input {
    /**
     * String that contains the chosen protocol (1 for socket, 2 for RMI).
     */
    private final String protocolChoice;
    /**
     * String that contains the chosen server address.
     */
    private final String address;

    /**
     * Constructs an AskPort object with the specified {@link TUI}, {@link PrintStream}, 
     * {@link #protocolChoice protocol choice}, and {@link #address address}.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param out the {@link PrintStream} to print messages
     * @param protocolChoice the protocol choice (1 for socket, 2 for RMI)
     * @param address the server address to connect to
     *                
     * @see TUI
     */
    public AskPort(TUI tui, PrintStream out, String protocolChoice, String address) {
        super(tui, out);
        this.protocolChoice = protocolChoice;
        this.address = address;
        setLocked(false);
    }

    /**
     * Prints the prompt message asking the user to insert a port number.
     * @see Input#printPrompt()  
     */
    @Override
    public void printPrompt() {
        getOut().println("Now insert a port number");
    }

    /**
     * Reads the user's input and processes it accordingly.
     * @param line the user's input
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        if(isLocked()) {
            return;
        }
        int portNumber;
        try {
            portNumber = Integer.parseInt(line);
        } catch(Exception e) {
            getOut().println("Invalid port, lease try again...");
            getTUI().setCurrentInput(new AskPort(getTUI(), getOut(), protocolChoice, address));
            return;
        }

        if(portNumber<0 || portNumber>65536) {
            getOut().println("Invalid port, please try again...");
            getTUI().setCurrentInput(new AskPort(getTUI(), getOut(), protocolChoice, address));
        }
        else {
            getTUI().setInputInProgress(false);
            setLocked(true);
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
