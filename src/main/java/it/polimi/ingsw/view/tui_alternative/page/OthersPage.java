package it.polimi.ingsw.view.tui_alternative.page;

import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui_alternative.TUI;

import java.io.PrintStream;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

public class OthersPage extends Page{

    private final PrintStream out;
    public OthersPage(TUI tui) {
        super(tui);
        this.out = tui.getPrintStream();
    }

    @Override
    public void show() {
        System.out.print(colorize("                                    ", MyShelfieAttribute.GREEN_BACK()));
        System.out.print(colorize("MY SHELFIE: OTHER PLAYERS' BOOKSHELVES AND POINTS", MyShelfieAttribute.GREEN_BACK()));
        System.out.println(colorize("                                    ", MyShelfieAttribute.GREEN_BACK()));
        List<String> nicknames = getOtherPlayer();
        List<char[][]> bookshelves = getOtherBookShelves();
        List<List<Integer>> pointPlayers = getOtherPoints();
        List<Integer> totalPoints = getOtherTotalPoints();
        printOthersBookShelf(nicknames.get(0), nicknames.get(1), nicknames.get(2), bookshelves.get(0), bookshelves.get(1), bookshelves.get(2));
        out.println();
        printOthersPoint(nicknames.get(0), nicknames.get(1), nicknames.get(2), pointPlayers.get(0), pointPlayers.get(1), pointPlayers.get(2), totalPoints.get(0), totalPoints.get(1), totalPoints.get(2));
        out.println(colorize("<--If you want to return to the homepage, please type 'exit'.-->", MyShelfieAttribute.BOLD()));
    }








}
