package it.polimi.ingsw.view.tui.page;

import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

/**
 * The HelpPage class represents a help page in the {@link TUI} user interface.
 * It provides information and instructions on how to play the game. The class extend {@link Page}.
 *
 * @see it.polimi.ingsw.view.tui.page.Page
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class HelpPage extends Page{
    /**
     * Attribute that represents the {@link PrintStream output stream}.
     */
    private PrintStream out;

    /**
     * Constructs a new HelpPage object.
     * @param tui the {@link TUI} object associated with the page.
     */
    public HelpPage(TUI tui) {
        super(tui);
        this.out = tui.getPrintStream();
    }

    /**
     * Displays the help page in the {@link TUI} user interface.
     *
     * @see TUI
     * @see Page#show()
     */
    @Override
    public void show() {
        out.print(colorize("                                                                   ", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        out.print(colorize("MY SHELFIE: LEGEND", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        out.println(colorize("                                                                   ", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        out.println(colorize("Here are the actions you can make during the game:", MyShelfieAttribute.ITALIC()));
        out.println(colorize(" TYPE        DESCRIPTION", MyShelfieAttribute.ITALIC()));
        out.println(" play        use it during your turn in order to move tiles from the board to your bookshelf.");
        out.println(" message     use it to send messages to other players.");
        out.println(" chat        use it to see the chat.");
        out.println(" others      use it to see other players' bookshelves and points.");
        out.println(" exit        use this to leave chat, others visual or help legend.");
        out.println(" quit        use it to leave the game, you will be directed to the welcome page so you can play again.");
        out.println(colorize("<--- Please enter exit to return to the homepage. --->", MyShelfieAttribute.BOLD()));
    }
}
