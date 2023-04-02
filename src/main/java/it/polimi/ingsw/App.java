package it.polimi.ingsw;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardSquare;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Board board = new Board();
        int index = 2;

        System.out.println("REFILLING THE BOARD");
        board.refillBoardIta(index);
        board.printBoard(index);

        System.out.println("Removing tiles form board...");
        board.removeSelectedTileSubject(new int[]{2,3,4});
        board.printBoard(index);

        System.out.println( "Il mio git" );
    }


}
