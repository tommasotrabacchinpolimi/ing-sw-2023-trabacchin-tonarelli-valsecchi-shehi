package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.listeners.OnBoardRefilledListener;
import it.polimi.ingsw.controller.listeners.OnBoardUpdatedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;
import it.polimi.ingsw.utils.Coordinate;

import java.util.*;

import static it.polimi.ingsw.model.BoardSquareType.*;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;

public class BoardNew implements OnUpdateNeededListener {
    private static final int DIM = 9;
    private static final int NUMBER_OF_BOARDSQUARE = 45;

    private final List<OnBoardRefilledListener> onBoardRefilledListeners;
    private final List<OnBoardUpdatedListener> onBoardUpdatedListeners;
    final private List<TileSubject> bag;
    private final TileSubject[][] board;
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



    public BoardNew() {
        this.onBoardRefilledListeners = new LinkedList<>();
        this.onBoardUpdatedListeners = new LinkedList<>();
        this.bag = new ArrayList<>();
        this.createBag();
        Collections.shuffle(bag);
        this.board = new TileSubject[DIM][DIM];
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

    public int getNumberOfBoardSquares() {
        return NUMBER_OF_BOARDSQUARE;
    }

    // method for extracting a casual TileSubject from bag
    public TileSubject getRandomTileSubject() {
        Collections.shuffle(this.bag);
        return this.bag.remove(0);
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

    public TileSubject getTileSubject(int i, int j) {
        return board[i][j];
    }

    private void shuffleArray(int[] occurrence) {
        Random rnd = new Random();
        for (int i = occurrence.length - 1; i > 0; i--) {
            //Simple swap with random position
            swapArrayElement(occurrence, rnd.nextInt(i + 1), i);
        }
    }

    private void swapArrayElement(int[] array, int i, int j) {
        int a = array[i];
        array[i] = array[j];
        array[j] = a;
    }

    private boolean isOkay(int i, int j, int numPlayers){
        Coordinate coordinate = new Coordinate(3,4);

        return (init_matrix[i][j]==NO_DOTS || (init_matrix[i][j]==THREE_DOTS && numPlayers >=3) || (init_matrix[i][j]==FOUR_DOTS && numPlayers==4));
    }

    /*public List<TileSubject> removeSelectedTileSubject(int[] taken) {
        Coordinate coordinate = new Coordinate(3,4);
    }*/

    /*public void refillBoard(int numberOfPlayers){


        notifyOnBoardUpdated();
        notifyOnBoardRefilled();
    }*/

    public void printBoard(int numPlayer){
        for(int i = 0; i < board[0].length; i++){
            for(int j = 0; j < board.length; j++){
                if(init_matrix[i][j]==null) System.out.print("---\t\t\t");
                else{
                    if(isOkay(i,j,numPlayer)){
                        if(board[i][j]==null) System.out.print("***\t\t\t");
                        else System.out.print(board[i][j].toString()+"\t\t");
                    } else {
                        System.out.print("xxx\t\t\t");
                    }
                }
            }
            System.out.println();
        }
    }

    public void notifyOnBoardUpdated() {
        TileSubject[][] boardCopy = Arrays.stream(board).map(TileSubject[]::clone).toArray(TileSubject[][]::new);
        for(OnBoardUpdatedListener onBoardUpdatedListener : onBoardUpdatedListeners) {
            onBoardUpdatedListener.onBoardUpdated(boardCopy);
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
    public void onUpdateNeededListener(Player player) {
        onBoardUpdatedListeners.stream().filter(v->player.getVirtualView() == v)
                .findAny()
                .ifPresentOrElse(v->v.onBoardUpdated(Arrays.stream(board).map(TileSubject[]::clone).toArray(TileSubject[][]::new)),()->System.err.println("no one to update about board refilled"));
    }
}
