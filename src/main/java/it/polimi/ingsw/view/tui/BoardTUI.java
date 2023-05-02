package it.polimi.ingsw.view.tui;

public class BoardTUI {
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
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
       print();
       // printFitBoard();
        //logo();
    }

    private static void print(){
        System.out.println("              Living Room Board:                           Your BookShelf:                   Your Personal Goal:");
        System.out.println("     ╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");

        for( int i = 0; i < DIM_BOARD; ++i ){
            System.out.print("     ");

            /*for( int j = 0; j < DIM_BOARD; ++j){
                if( j == 0 )
                    System.out.print( "║ " + test[i][j] + " ║ ");
                else if( j < DIM_BOARD - 1 )
                    System.out.print( test[i][j] + " ║ " );
                else
                    System.out.print( test[i][j] + " ║" );
            } */
            printLine(i);

            System.out.print("               ");
            if(i >= 3){
                for(int j = 0; j < DIMCOL_BOOKSHELF; j++){
                    if( j == 0 )
                        System.out.print( "║ " + test[i][j] + " ║ ");
                    else if( j < DIMCOL_BOOKSHELF - 1 )
                        System.out.print( test[i][j] + " ║ " );
                    else
                        System.out.print( test[i][j] + " ║" );
                }
                System.out.print("               ");
                for(int j = 0; j < DIMCOL_BOOKSHELF; j++){
                    if( j == 0 )
                        System.out.print( "║ " + test[i][j] + " ║ ");
                    else if( j < DIMCOL_BOOKSHELF - 1 )
                        System.out.print( test[i][j] + " ║ " );
                    else
                        System.out.print( test[i][j] + " ║" );
                }
            }
            System.out.println();

            if( i < DIM_BOARD - 1 ){
                if(i==2)
                    System.out.println("     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣               ╔═══╦═══╦═══╦═══╦═══╗               ╔═══╦═══╦═══╦═══╦═══╗");
                else if (i < 2) {
                    System.out.println("     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
                }
                else {
                    System.out.println("     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣               ╠═══╬═══╬═══╬═══╬═══╣               ╠═══╬═══╬═══╬═══╬═══╣");
                }
            }

        }

        System.out.println("     ╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝               ╚═══╩═══╩═══╩═══╩═══╝               ╚═══╩═══╩═══╩═══╩═══╝");
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

        //System.out.println();
    }

    public static void logo(){
        System.out.print("""
                          .         .                                                                                                                                   \s
                         ,8.       ,8.   `8.`8888.      ,8'              d888888o.   8 8888        8 8 8888888888   8 8888         8 8888888888    8 8888 8 8888888888  \s
                        ,888.     ,888.   `8.`8888.    ,8'             .`8888:' `88. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                       .`8888.   .`8888.   `8.`8888.  ,8'              8.`8888.   Y8 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                      ,8.`8888. ,8.`8888.   `8.`8888.,8'               `8.`8888.     8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                     ,8'8.`8888,8^8.`8888.   `8.`88888'                 `8.`8888.    8 8888        8 8 888888888888 8 8888         8 888888888888  8 8888 8 888888888888\s
                    ,8' `8.`8888' `8.`8888.   `8. 8888                   `8.`8888.   8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                   ,8'   `8.`88'   `8.`8888.   `8 8888                    `8.`8888.  8 8888888888888 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                  ,8'     `8.`'     `8.`8888.   8 8888                8b   `8.`8888. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                 ,8'       `8        `8.`8888.  8 8888                `8b.  ;8.`8888 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                ,8'         `         `8.`8888. 8 8888                 `Y8888P ,88P' 8 8888        8 8 888888888888 8 888888888888 8 8888          8 8888 8 888888888888\s
                """);
    }


}
