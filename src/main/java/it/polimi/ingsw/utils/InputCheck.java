package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class InputCheck {
    
    public static void checkActiveTilesInBoard(List<Coordinate> chosenTiles, TileSubject[][] bookShelf, TileSubject[][] board){
        if(chosenTiles.size() == 0 || chosenTiles.size() > 3 || chosenTiles.size() > getMaxDimensionChosenTiles(bookShelf)){
            throw new RuntimeException("You have chosen too many tiles from the board");
        }

        for (Coordinate chosenTile : chosenTiles) {
            if (!findIndexActiveTilesInBoard(board).contains(chosenTile)) {
                throw new RuntimeException("You cannot chose these tiles!");
            }
        }

        if(!checkRightPattern(chosenTiles)){
            throw new RuntimeException("Chosen tiles are not adjacent!");
        }

    }

    public static List<Coordinate> findIndexActiveTilesInBoard(TileSubject[][] board) {
        List<Coordinate> result = new ArrayList<>();

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j]!=null){
                    if(i-1 < 0 || i+1 > board.length || j-1 < 0 || j+1 > board[0].length){
                        result.add(new Coordinate(i,j));
                    } else {
                        if(board[i-1][j]==null || board[i+1][j]==null || board[i][j-1]==null || board[i][j+1]==null){
                            result.add(new Coordinate(i,j));
                        }
                    }
                }
            }
        }

        return result;
    }

    private static boolean checkRightPattern(List<Coordinate> list){
        return (checkIfSameY(list) && checkIfAdjacent(orderedXCoordinate(list))) ||(checkIfSameX(list) && checkIfAdjacent(orderedYCoordinate(list)));
    }

    private static List<Integer> orderedXCoordinate(List<Coordinate> coordinates){
        List<Integer> result = new ArrayList<>();
        if(coordinates==null) return null;

        result = coordinates.stream().map(Coordinate::getX).sorted().toList();

        return result;
    }

    private static List<Integer> orderedYCoordinate(List<Coordinate> coordinates){
        List<Integer> result = new ArrayList<>();
        if(coordinates==null) return null;

        result = coordinates.stream().map(Coordinate::getY).sorted().toList();

        return result;
    }

    private static boolean checkIfSameX(List<Coordinate> coordinates){
        if(coordinates==null) return false;
        int x = coordinates.get(0).getX();
        for(Coordinate c: coordinates){
            if(c.getX()!=x)
                return false;
        }
        return true;
    }

    private static boolean checkIfSameY(List<Coordinate> coordinates){
        if(coordinates==null) return false;
        int y = coordinates.get(0).getY();
        for(Coordinate c: coordinates){
            if(c.getY()!=y)
                return false;
        }
        return true;
    }

    private static boolean checkIfAdjacent(List<Integer> index){
        for(int i = 1; i < index.size(); i++){
            if(index.get(i)!=index.get(i-1)+1){
                return false;
            }
        }
        return true;
    }

    private static int getMaxDimensionChosenTiles(TileSubject[][] bookShelf){
        if(bookShelf == null) return -1;

        for(int i = bookShelf.length-1; i >= 0; i--){
            for(int j = 0; j < bookShelf[0].length; j++){
                if(bookShelf[i][j] == null){
                    return i+1;
                }
            }
        }

        return 0;
    }

}
