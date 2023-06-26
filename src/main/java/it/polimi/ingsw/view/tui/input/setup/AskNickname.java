package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

/**
 * The AskNickname class represents the welcome page of the {@link TUI} and is an input prompt for asking
 * the user to enter a unique nickname. It extends the {@link Input} class.
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskNickname extends Input {
    /**
     * Constructs a new AskNickname instance with the specified {@link TUI} and {@link PrintStream}.
     *
     * @param tui The {@link TUI} object.
     * @param out The {@link PrintStream} for output.
     *            
     * @see TUI
     */
    public AskNickname(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    /**
     * Prints the prompt message and the ASCII art logo for My Shelfie.
     * Also prompts the user to insert their unique nickname.
     * @see Input#printPrompt() 
     */
    @Override
    public void printPrompt() {
        getOut().print("""
                          .         .                                                                                                                                   \s
                         ,8.       ,8.   `8.`8888.      ,8'              d888888o.   8 8888        8 8 8888888888   8 8888         8 8888888888    8 8888 8 8888888888  \s
                        ,888.     ,888.   `8.`8888.    ,8'             .`8888:' `88. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                       .`8888.   .`8888.   `8.`8888.  ,8'              8.`8888.   Y8 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                      ,8.`8888. ,8.`8888.   `8.`8888.,8'               `8.`8888.     8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                     ,8'8.`8888,8^8.`8888.   `8.`88888'                 `8.`8888.    8 8888        8 8 888888888888 8 8888         8 888888888888  8 8888 8 888888888888\s
                    ,8' `8.`8888' `8.`8888.   `8. 8888                   `8.`8888.   8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                   ,8'   `8.`88'   `8.`8888.   `8 8888                    `8.`8888.  8 8888888888888 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                  ,8'     `8.`'     `8.`8888.   8 8888                8b   `8.`8888. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                 ,8'       `8        `8.`8888.  8 8888                `8b.  ;8.`8888 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                ,8'         `         `8.`8888. 8 8888                 `Y8888P ,88P' 8 8888        8 8 888888888888 8 888888888888 8 8888          8 8888 8 888888888888\s
                """);
        getOut().println("Welcome to My Shelfie!");
        getOut().println("Now insert your unique nickname: ");
    }

    /**
     * Reads the user's input and sets the next input as AskCreateOrJoin with the provided nickname.
     * @param line The user's input.
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        getTUI().setCurrentInput(new AskCreateOrJoin(getTUI(), getOut(), line));
    }
}
