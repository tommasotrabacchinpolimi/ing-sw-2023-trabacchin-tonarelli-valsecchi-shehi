package it.polimi.ingsw.view.tui;

import java.io.PrintStream;

public class BoardTUI {
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final char EMPTY = '-';
    private final PrintStream out = System.out;
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
       print();
       //printFitBoard();
        //logo();


    }

    private static void print(){
        System.out.println("              Living Room Board:                                                            Your Personal Goal:");
        System.out.println( "     1   2   3   4   5   6   7   8   9  " );
        System.out.println(getDividerBoard(0) + "               " + getDividerBookShelf(0) + "               " + getDividerBookShelf(0));

        for( int i = 0; i < DIM_BOARD; ++i ){
            System.out.print(getLineBoard(i, test));

            System.out.print("               ");
            if(i < 6){
                getLineBookShelf(i);
                System.out.print("               ");
                getLineBookShelf(i);
            } else if (i==6){
                System.out.print("                                     ");
                System.out.print("Personal Goal Points:");
            } else if (i==7) {
                System.out.print("p = PLANT\t b = BOOK \t \t \t \t p 1 | 2 | 4 | 6 | 9 | 12");
            } else {
                System.out.print("t = TROPHY\t c = CAT \t \t \t \t p = points achieved with relative #");
            }
            System.out.println();

            if( i < DIM_BOARD - 1){
                if(i < 6)
                    System.out.println(getDividerBoard(i+1) + "               "+ getDividerBookShelf(i+1) +"               "+ getDividerBookShelf(i+1) );
                else if (i==6) {
                    System.out.println(getDividerBoard(i+1) + "               " + "Legend:         \t \t \t \t \t \t # 1 | 2 | 3 | 4 | 5 | 6");
                } else  {
                    System.out.println(getDividerBoard(i+1) + "               " +"f = FRAME\t g = GAME \t \t \t \t # = numbers of matched tiles");
                }
            }
        }

        System.out.println(getDividerBoard(DIM_BOARD));
    }

    private static void getLineBookShelf(int row) {
        for(int j = 0; j < DIMCOL_BOOKSHELF; j++){
            if( j == 0 )
                System.out.print( "║ " + test[row][j] + " ║ ");
            else if( j < DIMCOL_BOOKSHELF - 1 )
                System.out.print( test[row][j] + " ║ " );
            else
                System.out.print( test[row][j] + " ║" );
        }
    }

    private static String getDividerBookShelf(int row){
        switch (row){
            case 0 -> { return "╔═══╦═══╦═══╦═══╦═══╗"; }
            case 1, 2, 3, 4, 5 -> { return "╠═══╬═══╬═══╬═══╬═══╣"; }
            case 6 -> { return "╚═══╩═══╩═══╩═══╩═══╝"; }
            default -> { return ""; }
        }
    }

    private static void printInSquare() {
        System.out.println("╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");

        for( int i = 0; i < DIM_BOARD; ++i ){
            for( int j = 0; j < DIM_BOARD; ++j){
                if( j == 0 )
                    System.out.print( "║ " + test[i][j] + " ║ ");
                else if( j < DIM_BOARD - 1 )
                    System.out.print( test[i][j] + " ║ " );
                else
                    System.out.println( test[i][j] + " ║ " );
            }

            if( i < DIM_BOARD - 1 )
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

        for( i += c ; i < DIM_BOARD; ++i ){
                System.out.print( "   │" );
        }

        System.out.println();
    }

    private static String getLineBoard(int r, char[][] board){
        int n; //number of print
        int c; //starting column
        int i;
        StringBuilder result = new StringBuilder("");

        result.append( (char)(r + 'A' ) + "  " );

        switch (r) {
            case 0, 1, 7 -> {
                result.append("│   │   │   ");
                c = 3;
            }

            case 2, 6 -> {
                result.append("│   │   ");
                c = 2;
            }

            case 3 -> {
                result.append("│   ");
                c = 1;
            }
            case 8 -> {
                result.append("│   │   │   │   ");
                c = 4;
            }

            default -> {
                result.append("");
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

        result.append( "║ " );

        for( i = 0; i < n; ++i ){
            result.append( board[r][c + i] );

            if( i < n - 1 )
                result.append( " ║ ");
        }

        result.append( " ║" );

        for( i += c ; i < DIM_BOARD; ++i ){
            result.append( "   │" );
        }

        return result.toString();
        //System.out.println();
    }

    private static String getDividerBoard(int row) {
        switch(row) {
            case 0 -> {
                return "   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐";
            }
            case 1 -> {
                return "   ├───┼───┼───╠═══╬═══╬═══╗───┼───┼───┤";
            }
            case 2 -> {
                return "   ├───┼───╔═══╬═══╬═══╬═══╬═══╗───┼───┤";
            }
            case 3 -> {
                return "   ├───╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗";
            }
            case 4 -> {
                return "   ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣";
            }
            case 5 -> {
                return "   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝";
            }
            case 6 -> {
                return "   ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝───┤";
            }
            case 7 -> {
                return "   ├───┼───╚═══╬═══╬═══╬═══╬═══╝───┼───┤";
            }
            case 8 -> {
                return "   ├───┼───┼───╚═══╬═══╬═══╣───┼───┼───┤";
            }
            case 9 -> {
                return "   └───┴───┴───┴───╚═══╩═══╝───┴───┴───┘";
            }
            default -> {
                return null;
            }
        }
    }

}
