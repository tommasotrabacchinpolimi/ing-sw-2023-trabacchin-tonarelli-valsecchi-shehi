package it.polimi.ingsw.view.tui.input.draginput;

import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

public class AskCoordinates extends Input {

    private final boolean hint;
    private final List<Coordinate> previousCoordinates;
    public AskCoordinates(TUI tui, PrintStream out, boolean hint, List<Coordinate> previousCoordinates) {
        super(tui, out);
        this.hint = hint;
        this.previousCoordinates = previousCoordinates;
        getTUI().setInputInProgress(true);
    }
    @Override
    public void printPrompt() {
        if(hint) {
            getOut().println("It's your turn to play! Please enter the coordinate of the tiles you want to take from the board, then type " + colorize("end", MyShelfieAttribute.BOLD()));
        }
    }

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
