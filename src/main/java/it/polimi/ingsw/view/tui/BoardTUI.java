package it.polimi.ingsw.view.tui;

public class BoardTUI {
    private static final int DIM = 9;
    private static final char EMPTY = '-';
    private static final char[][] test = {
            {' ',' ',' ',EMPTY,'c',' ',' ',' ',' '},
            {' ',' ',' ',EMPTY,'c',EMPTY,' ',' ',' '},
            {' ',' ','c','c','c',EMPTY,EMPTY,' ',' '},
            {' ',EMPTY,'c','c','c','c',EMPTY,EMPTY,EMPTY},
            {'c','c','c','c','c','c','c','c','c'},
            {'c','c','c',EMPTY,EMPTY,'c',EMPTY,EMPTY,' '},
            {' ',' ',EMPTY,EMPTY,EMPTY,'c',EMPTY,' ',' '},
            {' ',' ',' ',EMPTY,'c',EMPTY,' ',' ',' '},
            {' ',' ',' ',' ','c','c',' ',' ',' '}};

    public static void main( String[] args ) {
       // printInSquare();
        printFitBoard();
    }

    private static void printInSquare() {
        System.out.println("╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");

        for( int i = 0; i < DIM; ++i ){
            for( int j = 0; j < DIM; ++j){
                if( j == 0 )
                    System.out.print( "║ " + test[i][j] + " ║ ");
                else if( j < DIM - 1 )
                    System.out.print( test[i][j] + " ║ " );
                else
                    System.out.println( test[i][j] + " ║ " );
            }

            if( i < DIM - 1 )
                System.out.println("╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
        }

        System.out.println("╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝");
    }

    private static void printFitBoard() {
        int r = 0;

        System.out.println( "     1   2   3   4   5   6   7   8   9  " );
        System.out.println( "   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐" );
        printLine( r );
        System.out.println( "   ├───┼───┼───╠═══╬═══╬═══╗───┼───┼───┤" );
        printLine( ++r );
        System.out.println( "   ├───┼───╔═══╬═══╬═══╬═══╬═══╗───┼───┤" );
        printLine(  ++r );
        System.out.println( "   ├───╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗" );
        printLine(  ++r );
        System.out.println( "   ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣" );
        printLine( ++r );
        System.out.println( "   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝" );
        printLine( ++r );
        System.out.println( "   ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝───┤" );
        printLine( ++r );
        System.out.println( "   ├───┼───╚═══╬═══╬═══╬═══╬═══╝───┼───┤" );
        printLine( ++r );
        System.out.println( "   ├───┼───┼───╚═══╬═══╬═══╣───┼───┼───┤" );
        printLine( ++r );
        System.out.println( "   └───┴───┴───┴───╚═══╩═══╝───┴───┴───┘" );
    }
    /*
     * r -> row
     * */
    private static void printLine( int r ){
        int n; //number of print
        int c; //starting column
        int i;

        System.out.print( (char)(r + 'A' ) + "  " );

        switch (r) {
            case 0, 1, 7 -> {
                System.out.print("│   │   │   ");
                c = 3;
            }

            case 2, 6 -> {
                System.out.print("│   │   ");
                c = 2;
            }

            case 3 -> {
                System.out.print("│   ");
                c = 1;
            }
            case 8 -> {
                System.out.print("│   │   │   │   ");
                c = 4;
            }

            default -> {
                System.out.print("");
                c = 0;
            }
        }

        switch (r) {
            case 0, 8 -> n = 2;
            case 1, 7 -> n = 3;
            case 2, 6 -> n = 5;
            case 3, 5 -> n = 8;
            default -> n = 9;
        }

        System.out.print( "║ " );

        for( i = 0; i < n; ++i ){
            System.out.print( test[r][c + i] );

            if( i < n - 1 )
                System.out.print( " ║ ");
        }

        System.out.print( " ║" );

        for( i += c ; i < DIM; ++i ){
                System.out.print( "   │" );
        }

        System.out.println();
    }
}
