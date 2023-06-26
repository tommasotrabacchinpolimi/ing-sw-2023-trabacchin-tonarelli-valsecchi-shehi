package it.polimi.ingsw.view.tui.input.draginput;

import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;
import it.polimi.ingsw.view.tui.input.InputSelector;

import java.io.PrintStream;
import java.util.List;

/**
 * The AskColumn class represents an input prompt for asking the column number to insert chosen tiles into a bookshelf.
 * It extends the {@link Input} class.
 *
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskColumn extends Input {
    /**
     * List of {@link Coordinate coordinates} that need to be moved into the bookshelf.
     */
    private final List<Coordinate> coordinates;

    /**
     * Constructs a new AskColumn instance with the specified {@link TUI}, {@link PrintStream}, and {@link Coordinate coordinates}.
     *
     * @param tui         The TUI object.
     * @param out         The PrintStream for output.
     * @param coordinates The list of coordinates representing the chosen tiles.
     *
     * @see TUI
     * @see Coordinate
     */
    public AskColumn(TUI tui, PrintStream out, List<Coordinate> coordinates) {
        super(tui, out);
        this.coordinates = coordinates;
        getTUI().setInputInProgress(true);
    }

    /**
     * Prints the prompt for entering the column number of the bookshelf.
     *
     * @see Input#printPrompt()
     */
    @Override
    public void printPrompt() {
        getOut().println("Now enter the number, between 1 and 5, of the column of your bookshelf where to insert the chosen tiles:");
    }

    /**
     * Reads the user's input and performs the corresponding actions.
     *
     * @param line The user's input.
     *
     * @see Input#readLine(String)
     */
    @Override
    public void readLine(String line) {
        if(isLocked()) {
            return;
        }
        if(!(line.equals("1") || line.equals("2") || line.equals("3") || line.equals("4") || line.equals("5")))  {
            getOut().println("The column number is invalid, please try again");
            getTUI().setCurrentInput(new AskColumn(getTUI(), getOut(), coordinates));

        }
        else {
            setLocked(true);
            getTUI().setInputInProgress(false);
            //getTUI().setCurrentInput(new InputSelector(getTUI(), getOut()));
            getTUI().getLogicController().dragTilesToBookShelf(coordinates, Integer.parseInt(line) - 1);

            //getTUI().refresh();
        }
    }

    /**
     * Handles exceptions by printing the exception message and setting the current input to an InputSelector.
     * @see Input#onException()
     */
    @Override
    public void onException() {
        getOut().println(getTUI().getModel().getException());
        getTUI().setCurrentInput(new InputSelector(getTUI(), getOut()));
    }
}
