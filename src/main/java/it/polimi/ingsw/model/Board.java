package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.listeners.OnBoardRefilledListener;
import it.polimi.ingsw.controller.listeners.OnBoardUpdatedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.BoardSquareType.NO_DOTS;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;
import static it.polimi.ingsw.model.BoardSquareType.FOUR_DOTS;

public class Board<R extends ClientInterface> implements Iterable<BoardSquare>, Serializable, OnUpdateNeededListener<R> {
    @Serial
    private static final long serialVersionUID = 27112000L;
    private static final int DIM = 9;
    private static final int NUMBER_OF_BOARDSQUARE = 45;
    final private BoardSquare livingRoomBoard; //root della board

    private final List<OnBoardRefilledListener> onBoardRefilledListeners;
    private final List<OnBoardUpdatedListener> onBoardUpdatedListeners;
    final private List<TileSubject> bag;
    private static final int NUMBER_OF_TILE = 7; //the default number of tile
    private static final int RESERVE_TILE = 8; //the tile that has one object more than others

    private static final BoardSquareType[][] init_matrix = {
            {null, null, null, THREE_DOTS, FOUR_DOTS, null, null, null, null},
            {null, null, null, NO_DOTS, NO_DOTS, FOUR_DOTS, null, null, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS},
            {FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS},
            {THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, null, null, FOUR_DOTS, NO_DOTS, NO_DOTS, null, null, null},
            {null, null, null, null, FOUR_DOTS, THREE_DOTS, null, null, null}
    };

    //costruttore
    public Board() {
        this.onBoardRefilledListeners = new LinkedList<>();
        this.onBoardUpdatedListeners = new LinkedList<>();
        this.bag = new ArrayList<>();
        this.createBag();
        Collections.shuffle(bag);

        BoardSquare[][] temp = new BoardSquare[DIM][DIM];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                if (init_matrix[i][j] != null) {
                    temp[i][j] = new BoardSquare(init_matrix[i][j]);
                }

            }
        }

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                if (temp[i][j] != null) {
                    if (i - 1 >= 0 && temp[i][j] != null) {
                        temp[i][j].setTop(temp[i - 1][j]);
                    }
                    if (j - 1 >= 0 && temp[i][j] != null) {
                        temp[i][j].setLeft(temp[i][j - 1]);
                    }
                    if (i + 1 < DIM && temp[i][j] != null) {
                        temp[i][j].setBottom(temp[i + 1][j]);
                    }
                    if (j + 1 < DIM && temp[i][j] != null) {
                        temp[i][j].setRight(temp[i][j + 1]);
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
        return new BoardIterator(this);
    }

    public int getNumberOfBoardSquares() {
        return NUMBER_OF_BOARDSQUARE;
    }

    // method for extracting a casual TileSubject from bag
    public TileSubject getRandomTileSubject() {
        Collections.shuffle(this.bag);
        return this.bag.remove(0);
    }

    private void createBag() {
        int[] occurence = {NUMBER_OF_TILE, NUMBER_OF_TILE, RESERVE_TILE};
        int k = 0;
        for (TileSubject el : TileSubject.values()) {
            if (k % 3 == 0)
                shuffleArray(occurence);

            for (int i = 0; i < occurence[k % 3]; i++) {
                bag.add(el);
            }
            k++;
        }
    }

    public List<TileSubject> getBag() {
        return bag;
    }

    public String bagToString() {
        StringBuilder result = new StringBuilder("BAG: \n");
        for (TileSubject el : bag) {
            result.append(el.name()).append("\n");
        }
        return result.toString();
    }

    private void shuffleArray(int[] occurrence) {
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
     * @param i     position in the array that will contain the array[j] element
     * @param j     position in the array that will contain the array[i] element
     */
    private void swapArrayElement(int[] array, int i, int j) {
        int a = array[i];
        array[i] = array[j];
        array[j] = a;
    }


    private TileSubject[][] fromBoardToMatrix() {
        TileSubject[][] matrix = new TileSubject[DIM][DIM];
        int counter = 0;

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                matrix[i][j] = null;
            }
        }

        for (int i = 0; i < DIM; i++) {
            for (int j = 4; j >= 0; j--) { //controllo prima a destra del centro
                if (init_matrix[i][j] != null) {
                    matrix[i][j] = fromIntToBoardSquare(counter).getTileSubject();
                    counter++;
                }
            }

            for (int j = 5; j < DIM; j++) {
                if (init_matrix[i][j] != null) {
                    matrix[i][j] = fromIntToBoardSquare(counter).getTileSubject();
                    counter++;
                }
            }
        }

        return matrix;
    }

    //metodo che prende in ingresso un intero che rappresenta l'indice della board square (utilizzando lo schema dell'iterator)
    // e restituisce il riferimento al board square corrispondente, null se la board square è vuota
    public BoardSquare fromIntToBoardSquare(int index) {
        int cont = 0;

        for (BoardSquare b : this) {
            if (cont == index) return b;
            cont++;
        }

        return null;
    }

    private List<BoardSquare> getBoardSquareList() {
        List<BoardSquare> boardSquares = new ArrayList<>();
        for(BoardSquare boardSquare : this) {
            boardSquares.add(boardSquare);
        }
        return boardSquares;
    }

    //aggiungere metodo per rimuovere le tile dalla board quando vengono prese da un giocatore
    // taken deve contenere le posizioni delle tile prese secondo lo schema d'iterator.
    public List<TileSubject> removeSelectedTileSubject(int[] taken) {
        List<TileSubject> result = new ArrayList<>();
        for(int i = 0; i < taken.length; i++){
            result.add(fromIntToBoardSquare(taken[i]).getTileSubject());
            fromIntToBoardSquare(taken[i]).setTileSubject(null);
        }
        notifyOnBoardUpdated();
        return result;
    }

    //metodo che ritorna true se init_matrix[i][j] è usata in funzione del numero di giocatori !!
    private boolean isOkay(int i, int j, int numPlayers){
        return (init_matrix[i][j]==NO_DOTS || (init_matrix[i][j]==THREE_DOTS && numPlayers >=3) || (init_matrix[i][j]==FOUR_DOTS && numPlayers==4));
    }

    private boolean isOkay(BoardSquare boardSquare, int numPlayers) {
        return (boardSquare.getBoardSquareType() == NO_DOTS || (boardSquare.getBoardSquareType() == THREE_DOTS && numPlayers >=3) || (boardSquare.getBoardSquareType() == FOUR_DOTS && numPlayers==4));
    }

    public void refillBoard(int numberOfPlayers){
        int count = 0;
        Random rnd = new Random();
        if(bag.size()!=0){
            for(BoardSquare b : this){ //conto il numero di tile presenti nella board
                if(b.getTileSubject() != null)
                    count++;
            }
            if((numberOfPlayers==2 && count+bag.size()<getNumBoardSquareGivenType(NO_DOTS)) ||
                    (numberOfPlayers==3 && count+bag.size()<getNumBoardSquareGivenType(NO_DOTS)+getNumBoardSquareGivenType(THREE_DOTS))
                    || (numberOfPlayers==4 && count+ bag.size()<NUMBER_OF_BOARDSQUARE)){
                while(bag.size()>0){
                    int index = rnd.nextInt(0,NUMBER_OF_BOARDSQUARE+1);
                    if(fromIntToBoardSquare(index).getTileSubject()==null && isOkay(fromIntToBoardSquare(index),numberOfPlayers)){
                        fromIntToBoardSquare(index).setTileSubject(getRandomTileSubject());
                    }
                }
            }
            else {
                for(BoardSquare b : this){ //se ci sono abbastanza tile per riempire tutta la board
                    if(b.getTileSubject() == null){
                        if(isOkay(b,numberOfPlayers)) {
                            b.setTileSubject(getRandomTileSubject());
                        }
                    }
                }
            }
        }

        notifyOnBoardUpdated();
        notifyOnBoardRefilled();
    }

    public void refillBoard_alternative(int numberOfPlayers){
        Random random = new Random();
        List<BoardSquare> boardSquares = getBoardSquareList();
        while(bag.size() > 0 && boardSquares.size() > 0) {
            int index = random.nextInt(boardSquares.size());
            BoardSquare randomBoardSquare = boardSquares.remove(index);
            if(isOkay(randomBoardSquare, numberOfPlayers) && randomBoardSquare.getTileSubject() != null) {
                randomBoardSquare.setTileSubject(bag.remove(0));
            }
        }

        notifyOnBoardUpdated();
        notifyOnBoardRefilled();
    }

    private int getNumBoardSquareGivenType(BoardSquareType boardSquareType){
        int count = 0;
        for(BoardSquare b : this){
            if(b.getBoardSquareType().equals(boardSquareType)) count++;
        }
        return count;
    }

    public void printBoard(int numPlayer){
        TileSubject[][] matrix = fromBoardToMatrix();
        for(int i = 0; i < matrix[0].length; i++){
            for(int j = 0; j < matrix.length; j++){
                if(init_matrix[i][j]==null) System.out.print("---\t\t\t");
                else{
                    if(isOkay(i,j,numPlayer)){
                        if(matrix[i][j]==null) System.out.print("***\t\t\t");
                        else System.out.print(matrix[i][j].toString()+"\t\t");
                    } else {
                        System.out.print("xxx\t\t\t");
                    }
                }
            }
            System.out.println();
        }
    }

    public void notifyOnBoardUpdated() {
       // TileSubject[] tileSubjects = (TileSubject[]) getBoardSquareList().stream().map(BoardSquare::getTileSubject).toArray();
        TileSubject[] tileSubjects = new TileSubject[NUMBER_OF_BOARDSQUARE];
        int count = 0;
        for(BoardSquare b : this){
            tileSubjects[count] = b.getTileSubject();
            count++;
        }
        for(OnBoardUpdatedListener onBoardUpdatedListener : onBoardUpdatedListeners) {
            if(onBoardUpdatedListener != null) {
                onBoardUpdatedListener.onBoardUpdated(tileSubjects);
            }
        }
    }

    public void notifyOnBoardRefilled() {
        for(OnBoardRefilledListener onBoardRefilledListener : onBoardRefilledListeners) {
            if(onBoardRefilledListener != null) {
                onBoardRefilledListener.onBoardRefilled();
            }
        }
    }

    public void setOnBoardRefilledListener(OnBoardRefilledListener onBoardRefilledListener) {
        this.onBoardRefilledListeners.add(onBoardRefilledListener);
    }

    public void setOnBoardUpdatedListener(OnBoardUpdatedListener onBoardUpdatedListener) {
        this.onBoardUpdatedListeners.add(onBoardUpdatedListener);
    }

    public void removeOnBoardRefilledListener(OnBoardRefilledListener onBoardRefilledListener) {
        this.onBoardRefilledListeners.remove(onBoardRefilledListener);
    }

    public void removeOnBoardUpdatedListener(OnBoardUpdatedListener onBoardUpdatedListener) {
        this.onBoardUpdatedListeners.remove(onBoardUpdatedListener);
    }

    @Override
    public void onUpdateNeededListener(Player<R> player) {
        onBoardUpdatedListeners.stream().filter(v->player.getVirtualView() == v)
                .findAny()
                .ifPresentOrElse(v->v.onBoardUpdated((TileSubject[]) getBoardSquareList().stream().map(BoardSquare::getTileSubject).toArray()),()->System.err.println("no one to update about board refilled"));
    }
}
