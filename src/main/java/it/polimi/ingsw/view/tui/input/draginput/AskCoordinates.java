package it.polimi.ingsw.view.tui.input.draginput;

import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

/**
 * The AskCoordinates class represents an input prompt for asking the {@link Coordinate coordinates} of tiles to be taken from the board.
 * It extends the {@link Input} class.
 *
 * @see Input
 * @see TUI
 * @see Coordinate
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskCoordinates extends Input {
    /**
     * Boolean set to true if and only if it's the player's turn to play.
     */
    private final boolean hint;
    /**
     * List of {@link Coordinate coordinates} of the chosen tiles.
     */
    private final List<Coordinate> previousCoordinates;

    /**
     * Constructs a new AskCoordinates instance with the specified {@link TUI}, {@link PrintStream}, {@link #hint}, and {@link #previousCoordinates previous coordinates}.
     *
     * @param tui                 The {@link TUI} object.
     * @param out                 The {@link PrintStream} for output.
     * @param hint                A boolean indicating whether it's the player's turn to play or not.
     * @param previousCoordinates The list of selected {@link Coordinate coordinates}.
     */
    public AskCoordinates(TUI tui, PrintStream out, boolean hint, List<Coordinate> previousCoordinates) {
        super(tui, out);
        this.hint = hint;
        this.previousCoordinates = previousCoordinates;
        getTUI().setInputInProgress(true);
    }

    /**
     * Prints the prompt for entering the {@link Coordinate coordinates} of tiles.
     * If the {@link #hint} is true, it displays a message indicating it's the player's turn.
     *
     * @see Input#printPrompt()
     */
    @Override
    public void printPrompt() {
        if(hint) {
            getOut().println("It's your turn to play! Please enter the coordinate of the tiles you want to take from the board, then type " + colorize("end", MyShelfieAttribute.BOLD()));
        }
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
        try{
            Coordinate tile;
            if(!line.equals("end")) {
                tile = new Coordinate(line.charAt(0) - 'A', Integer.parseInt(String.valueOf(line.charAt(1)))-1 );
                previousCoordinates.add(tile);
                if(previousCoordinates.size() < 3) {
                    getTUI().setCurrentInput(new AskCoordinates(getTUI(), getOut(), false, previousCoordinates));
                }
                else {
                    getTUI().setCurrentInput(new AskColumn(getTUI(), getOut(), previousCoordinates));
                }
            }
            else {
                getTUI().setCurrentInput(new AskColumn(getTUI(), getOut(), previousCoordinates));
            }
        } catch(Exception ex) {
            getOut().println("Your last choice is invalid, please try again...");
            getTUI().setCurrentInput(new AskCoordinates(getTUI(), getOut(), false, previousCoordinates));
        }
    }
}
