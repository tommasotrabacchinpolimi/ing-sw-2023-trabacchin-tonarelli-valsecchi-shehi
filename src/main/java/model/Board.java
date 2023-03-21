package model;

import java.io.Serializable;
import java.util.*;

import static model.BoardSquareType.NO_DOTS;
import static model.BoardSquareType.THREE_DOTS;
import static model.BoardSquareType.FOUR_DOTS;

public class Board implements Iterable<BoardSquare>, Serializable {
    private static final long serialVersionUID = 27112000L;
    private static final int DIM = 9;
    private static final int NUMBER_OF_BOARDSQUARE = 45;
    final private BoardSquare livingRoomBoard;

    final private List<TileSubject> bag;
    private static final int NUMBER_OF_TILE = 7; //the default number of tile
    private static final int RESERVE_TILE = 8; //the tile that has one object more than others

    private static final BoardSquareType[][] init_matrix = {
            { null, null, null, THREE_DOTS, FOUR_DOTS, null, null, null, null },
            { null, null, null, NO_DOTS, NO_DOTS, FOUR_DOTS, null, null, null },
            { null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null },
            { null, FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS },
            { FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS },
            { THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS, null },
            { null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null },
            { null, null, null, FOUR_DOTS, NO_DOTS, NO_DOTS, null, null, null },
            { null, null, null, null, FOUR_DOTS, THREE_DOTS, null, null, null }
    };

    //costruttore
    public Board(){
        this.bag = new ArrayList<>();
        this.createBag();
        Collections.shuffle(bag);

        BoardSquare[][] temp = new BoardSquare[DIM][DIM];
        for( int i = 0; i < temp.length ; i++ ){
            for( int j = 0; j < temp[0].length; j++ ){
                if( init_matrix[i][j] != null ){
                    temp[i][j] = new BoardSquare(init_matrix[i][j]);
                }

            }
        }

        for( int i = 0; i < temp.length; i++ ){
            for( int j = 0; j < temp.length; j++ ){
                if( temp[i][j] != null ){
                    if( i - 1 >= 0 && temp[i][j] != null ){
                        temp[i][j].setTop( temp[i-1][j] );
                    }
                    if( j-1 >= 0 && temp[i][j] != null ){
                        temp[i][j].setLeft( temp[i][j-1] );
                    }
                    if( i+1 < DIM && temp[i][j] != null ){
                        temp[i][j].setBottom( temp[i+1][j] );
                    }
                    if( j+1 < DIM && temp[i][j] != null ){
                        temp[i][j].setRight( temp[i][j+1] );
                    }
                }
            }
        }
        this.livingRoomBoard = temp[0][4];
    }


    public BoardSquare getLivingRoomBoard() {
        return livingRoomBoard;
    }


    @Override
    public Iterator<BoardSquare> iterator() {
        return new BoardIterator( this );
    }

    public int getNumberOfBoardSquares(){
        return NUMBER_OF_BOARDSQUARE;
    }

    // method for extracting a casual TileSubject from bag
    public TileSubject getRandomTileSubject() {
        Collections.shuffle( this.bag );
        return this.bag.remove( 0 );
    }

    private void createBag(){
        int[] occurence = {NUMBER_OF_TILE, NUMBER_OF_TILE, RESERVE_TILE};
        int k = 0;
        for (TileSubject el : TileSubject.values()) {
            if (k % 3 == 0) shuffleArray(occurence);
            for (int i = 0; i < occurence[k%3]; i++){
                bag.add(el);
            }
            k++;
        }
    }

    public String bagToString(){
        StringBuilder result = new StringBuilder("BAG: \n");
        for (TileSubject el: bag){
            result.append(el.name()).append("\n");
        }
        return result.toString();
    }

    private void shuffleArray(int[] occurence){
        Random rnd = new Random();
        for (int i = occurence.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = occurence[index];
            occurence[index] = occurence[i];
            occurence[i] = a;
        }
    }


}
