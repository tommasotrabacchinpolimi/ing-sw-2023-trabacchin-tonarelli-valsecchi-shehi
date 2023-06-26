package it.polimi.ingsw.view.tui.page;

import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

/**
 * The Others Page class represents a page that displays information about other players' bookshelves and points.
 * It extends the {@link Page} class and overrides the {@link Page#show()} method to customize the display of the page.
 *
 * @see it.polimi.ingsw.view.tui.page.Page
 * @see TUI
 * @see it.polimi.ingsw.view.ViewData
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class OthersPage extends Page{
    /**
     * Attribute that represents the {@link PrintStream output stream}.
     */
    private final PrintStream out;

    /**
     * Constructs a new OthersPage object with the specified {@link TUI} (Text-based User Interface) instance.
     * @param tui The {@link TUI} instance associated with this page.
     *
     * @see TUI
     */
    public OthersPage(TUI tui) {
        super(tui);
        this.out = tui.getPrintStream();
    }

    /**
     * Displays the content of the OthersPage.
     * Overrides the {@link Page#show()} method from the superclass.
     * @see Page#show()
     */
    @Override
    public void show() {
        System.out.print(colorize("                                    ", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        System.out.print(colorize("MY SHELFIE: OTHER PLAYERS' BOOKSHELVES AND POINTS", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        System.out.println(colorize("                                    ", MyShelfieAttribute.DARKEN_GREEN_BACK()));
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
