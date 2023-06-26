package it.polimi.ingsw.view.tui.page;

import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

public class WinnerPage extends Page{
    private final PrintStream out;
    public WinnerPage(TUI tui) {
        super(tui);
        this.out = tui.getPrintStream();
    }

    @Override
    public void show() {
        System.out.print(colorize("                                                           ", MyShelfieAttribute.GREEN_BACK()));
        System.out.print(colorize("MY SHELFIE: HOME OF " + getModel().getThisPlayer(), MyShelfieAttribute.GREEN_BACK()));
        System.out.println(colorize("                                                           ", MyShelfieAttribute.GREEN_BACK()));
        printGameState(getModel().getGameState());
        if(getModel().getWinnerPlayer()!=null) {
            out.println("The winner is..." + colorize(getModel().getWinnerPlayer(), MyShelfieAttribute.BOLD(), MyShelfieAttribute.BRIGHT_YELLOW_TEXT()) + "!");
        }
        else {
            out.println("There is no winner....");
        }
        out.println("Here are the bookshelves and the point of all players:");

        char[][] bookshelf = fromTileSubjectToChar(getModel().getBookShelfByNickname(getModel().getThisPlayer()), false);
        List<Integer> pointPlayer = getModel().getPlayersPointsByNickname(getModel().getThisPlayer());
        int totalScore = getModel().getTotalPointByNickname(getModel().getThisPlayer());

        List<String> nicknames = getOtherPlayer();
        List<char[][]> othersBookshelves = getOtherBookShelves();
        List<List<Integer>> othersPoints = getOtherPoints();
        List<Integer> othersTotalPoints = getOtherTotalPoints();
        out.println("              Your Bookshelf:              Your Points:");
        out.println("               " + getDividerBookShelf(0));
        for( int i = 0; i < DIMROW_BOOKSHELF; ++i ){
            out.print("               ");
            printLineBookShelf(i,bookshelf);
            out.print("               ");
            if(i < 5 ) {
                printPoint(pointPlayer, i);
            } else {
                out.print(colorize("Total Points", MyShelfieAttribute.BOLD()) + " =   " + totalScore + printPointOrPoints(totalScore));
            }
            out.println();
            out.println("               "+ getDividerBookShelf(i+1));
        }
        out.println();
        printOthersBookShelf(nicknames.get(0), nicknames.get(1), nicknames.get(2), othersBookshelves.get(0), othersBookshelves.get(1), othersBookshelves.get(2));
        printOthersPoint(nicknames.get(0), nicknames.get(1), nicknames.get(2), othersPoints.get(0), othersPoints.get(1), othersPoints.get(2), othersTotalPoints.get(0), othersTotalPoints.get(1), othersTotalPoints.get(2));
        //out.println(colorize("<--- Enter 'quit' to leave this game and start a new one. --->", MyShelfieAttribute.BOLD()));
    }
}
