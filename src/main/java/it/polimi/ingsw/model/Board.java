package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.BoardSquareType.NO_DOTS;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;
import static it.polimi.ingsw.model.BoardSquareType.FOUR_DOTS;

public class Board implements Iterable<BoardSquare>, Serializable {
    private static final long serialVersionUID = 27112000L;
    private static final int DIM = 9;
    private static final int NUMBER_OF_BOARDSQUARE = 45;
    final private BoardSquare livingRoomBoard; //root della board

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
            if (k % 3 == 0)
                shuffleArray(occurence);

            for (int i = 0; i < occurence[k%3]; i++){
                bag.add(el);
            }
            k++;
        }
    }

    public List<TileSubject> getBag() {
        return bag;
    }

    public String bagToString(){
        StringBuilder result = new StringBuilder("BAG: \n");
        for (TileSubject el: bag){
            result.append(el.name()).append("\n");
        }
        return result.toString();
    }

    private void shuffleArray(int[] occurrence){
        Random rnd = new Random();
        for (int i = occurrence.length - 1; i > 0; i--) {
            //Simple swap with random position
            swapArrayElement(occurrence, rnd.nextInt(i + 1), i);
        }
    }

    /**
     * Simple swap function for array int
     *
     * @param array the array that needs to swap its elements
     * @param i position in the array that will contain the array[j] element
     * @param j position in the array that will contain the array[i] element
     */
    private void swapArrayElement(int[] array, int i, int j){
        int a = array[i];
        array[i] = array[j];
        array[j] = a;
    }

    //aggiungere metodo per rimuovere le tile dalla board quando vengono prese da un giocatore
    // public void removeSelectedTileSubject(int[] taken);
    // taken deve contenere le posizioni delle tile prese secondo lo schema d'iterator.

    //aggiungere metodo per fare il refill della board
    // public void refillBoard()
    // prenderà le tile dalla bag (quindi tra le tile restanti) e in base al numero di giocatori inserisce le tile nella board


    //metodo che prende in ingresso un intero che rappresenta l'indice della board square (utilizzando lo schema dell'iterator)
    // e restituisce il riferimento al board square corrispondente, null se la board square è vuota
    private BoardSquare fromIntToBoardSquare(int index){
        int cont = 0;

        for(BoardSquare b : this){
            if(cont == index) return b;
            cont++;
        }
        return null;
    }


}