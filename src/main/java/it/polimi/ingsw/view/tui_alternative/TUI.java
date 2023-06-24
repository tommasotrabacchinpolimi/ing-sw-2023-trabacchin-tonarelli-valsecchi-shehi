package it.polimi.ingsw.view.tui_alternative;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;
import it.polimi.ingsw.view.tui_alternative.input.Input;
import it.polimi.ingsw.view.tui_alternative.input.InputSelector;
import it.polimi.ingsw.view.tui_alternative.input.setup.AskProtocol;
import it.polimi.ingsw.view.tui_alternative.page.HomePage;
import it.polimi.ingsw.view.tui_alternative.page.Page;
import it.polimi.ingsw.view.tui_alternative.page.WinnerPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class TUI extends UI {
    private Page page;

    private Input currentInput;

    private final PrintStream printStream;

    private boolean inputInProgress;



    public TUI() {
        this.page = null;
        printStream = System.out;
        setModel(new ViewData(9,5,6));
        getModel().setUserInterface(this);
        inputInProgress = false;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
    public void setPage(Page page) {
        this.currentInput = new InputSelector(this, printStream);
        this.page = page;
        page.show();
    }

    public Page getPage() {
        return page;
    }


    public Input getCurrentInput() {
        return currentInput;
    }

    public void setCurrentInput(Input currentInput) {
        this.currentInput = currentInput;
        currentInput.printPrompt();

    }
    @Override
    public void launchUI() {
        AsyncReader asyncReader = new AsyncReader(this, new BufferedReader(new InputStreamReader(System.in)));
        new Thread(asyncReader).start();
        this.setCurrentInput(new AskProtocol(this, getPrintStream()));
    }

    @Override
    public synchronized void onNewMessage(String sender) {
        try{
            while(inputInProgress) {
                this.wait();
            }
        }catch(InterruptedException e) {
            e.printStackTrace();
        }

        this.currentInput = new InputSelector(this, printStream);
        if(!sender.equals(getModel().getThisPlayer())) {
            if(page!=null) {
                page.onNewMessage();
            }
            else {
                getPrintStream().println("There is a new message from " + sender);
            }
        }


    }

    @Override
    public synchronized void onCurrentPlayerChanged(String newCurrentPlayer) {
        try{
            while(inputInProgress) {
                this.wait();
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        this.currentInput = new InputSelector(this, printStream);
        if(page == null) {
            page = new HomePage(this);
        }
        page.onCurrentPlayerChanged();
    }

    @Override
    public synchronized void showWinner() {

        //this.page = new WinnerPage(getModel(), this);
    }

    @Override
    public synchronized void onException() throws IOException {
        currentInput.onException();
    }

    @Override
    public synchronized void onGameStateChanged()  {
        try{
            while(inputInProgress) {
                this.wait();
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        if(getModel().getGameState().equals("END")) {
            this.page = new WinnerPage(this);
        }
    }

    public synchronized void readLine(String line) {
        getCurrentInput().readLine(line);
    }

    public void refresh() {
        if(page != null) {
            page.show();
        }
    }

    public synchronized void setInputInProgress(boolean progress) {
        this.inputInProgress = progress;
        this.notifyAll();

    }
}
