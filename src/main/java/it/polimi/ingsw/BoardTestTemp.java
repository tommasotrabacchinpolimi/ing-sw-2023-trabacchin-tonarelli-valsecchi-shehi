package it.polimi.ingsw;

import it.polimi.ingsw.model.BoardOld;
import it.polimi.ingsw.model.BoardSquare;

import static it.polimi.ingsw.model.BoardSquareType.NO_DOTS;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;

@Deprecated
public class BoardTestTemp {
    private static final int DIM = 9;
    public BoardSquare getLineBegin(BoardSquare b){
        while(b.getLeft()!=null){
            b = b.getLeft();
        }
        return b;
    }

    public static void main(String[] args){
        BoardOld b = new BoardOld();
        System.out.println(b.bagToString());
    }

    public boolean test1(BoardSquare root){
        BoardSquare middle = root.getRight();
        while(true){
            if(root.getLeft()!=null)
            {
                if(root.getLeft().getRight()!=root){
                    return false;
                }
            }

            if(root.getRight()!=null)
            {
                if(root.getRight().getLeft()!=root){
                    return false;
                }
            }

            if(root.getBottom()!=null)
            {
                if(root.getBottom().getTop()!=root){
                    return false;
                }
            }
            if(root.getTop()!=null)
            {
                if(root.getTop().getBottom()!=root){
                    return false;
                }
            }
            root = root.getRight();
            if(root==null){
                middle = middle.getBottom();
                if(middle.getBottom()==null){
                    break;
                }
                root = getLineBegin(middle);
            }
        }
        return true;
    }



    private  void printFitBoard(BoardSquare root) {
        BoardSquare middle = root.getRight();
        int r = 0;

        System.out.println( "     1   2   3   4   5   6   7   8   9  " );
        System.out.println( "   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐" );
        printLine( r,getLineBegin(middle) );
        middle = middle.getBottom();
        System.out.println( "   ├───┼───┼───╠═══╬═══╬═══╗───┼───┼───┤" );
        printLine( ++r ,getLineBegin(middle));
        middle = middle.getBottom();
        System.out.println( "   ├───┼───╔═══╬═══╬═══╬═══╬═══╗───┼───┤" );
        printLine(  ++r ,getLineBegin(middle));
        middle = middle.getBottom();
        System.out.println( "   ├───╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗" );
        printLine(  ++r,getLineBegin(middle) );
        middle = middle.getBottom();
        System.out.println( "   ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣" );
        printLine( ++r ,getLineBegin(middle));
        middle = middle.getBottom();
        System.out.println( "   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝" );
        printLine( ++r ,getLineBegin(middle));
        middle = middle.getBottom();
        System.out.println( "   ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝───┤" );
        printLine( ++r,getLineBegin(middle) );
        middle = middle.getBottom();
        System.out.println( "   ├───┼───╚═══╬═══╬═══╬═══╬═══╝───┼───┤" );
        printLine( ++r ,getLineBegin(middle));
        middle = middle.getBottom();
        System.out.println( "   ├───┼───┼───╚═══╬═══╬═══╣───┼───┼───┤" );
        printLine( ++r ,getLineBegin(middle));
        middle = middle.getBottom();
        System.out.println( "   └───┴───┴───┴───╚═══╩═══╝───┴───┴───┘" );
    }
    /*
     * r -> row
     * */
    private  void printLine( int r, BoardSquare b ){
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
            if(b.getBoardSquareType()==NO_DOTS){
                System.out.print("0");
            }
            else if(b.getBoardSquareType()==THREE_DOTS){
                System.out.print("3");
            }
            else {
                System.out.print("4");
            }
            b = b.getRight();
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
