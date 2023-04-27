package it.polimi.ingsw;

import it.polimi.ingsw.model.BoardOld;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        BoardOld boardOld = new BoardOld();
        int index = 2;

        System.out.println("REFILLING THE BOARD");
        boardOld.refillBoard(index);
        boardOld.printBoard(index);

        System.out.println("Removing tiles form board...");
        boardOld.removeSelectedTileSubject(new int[]{2,3,4});
        boardOld.printBoard(index);

        System.out.println( "Il mio git" );
    }


}
