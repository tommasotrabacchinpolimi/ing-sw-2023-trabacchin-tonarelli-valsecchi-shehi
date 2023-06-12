package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InputCheck {
    
    public static void checkActiveTilesInBoard(List<Coordinate> chosenTiles, TileSubject[][] bookShelf, TileSubject[][] board) throws WrongChosenTilesFromBoardException {
        if(chosenTiles.size() == 0){
            throw new WrongChosenTilesFromBoardException("You haven't chosen any tiles from the board!");
        }

        if(chosenTiles.size() > 3 || chosenTiles.size() > getMaxDimensionChosenTiles(bookShelf)){
            throw new WrongChosenTilesFromBoardException("You have chosen too many tiles from the board");
        }

        for (Coordinate chosenTile : chosenTiles) {
            if (!findIndexAllActiveTilesInBoard(board).contains(chosenTile)) {
                throw new WrongChosenTilesFromBoardException("You cannot chose these tiles!");
            }
        }

        if(!checkRightPattern(chosenTiles)){
            throw new WrongChosenTilesFromBoardException("Chosen tiles are not adjacent!");
        }

    }

    /**
     * Retrieves a list of coordinates that correspond
     * to the TileSubject selectable in the board
     *
     * @param board the board matrix
     *
     * @return coordinates of clickable tiles on board
     */
    public static List<Coordinate> findIndexAllActiveTilesInBoard(TileSubject[][] board) {
        List<Coordinate> result = new ArrayList<>();

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j]!=null){
                    if(i-1 < 0 || i+1 >= board.length || j-1 < 0 || j+1 >= board[0].length){
                        result.add(new Coordinate(i,j));
                    } else {
                        if(board[i-1][j] == null || board[i+1][j] == null || board[i][j-1]==null || board[i][j+1]==null){
                            result.add(new Coordinate(i,j));
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Retrieves a list of coordinates that correspond
     * to the TileSubject selectable in the board given
     * a selected tile
     *
     * @param board the board matrix
     * @param c1 the coordinate of the selected tiles
     * @param bookShelf the player's bookshelf
     * @return coordinates of clickable tiles on board
     * given a selected tile
     */
    public static List<Coordinate> findIndexActiveAfterOneChosenTile(TileSubject[][] board, Coordinate c1, TileSubject[][] bookShelf){
        List<Coordinate> coordinates = new ArrayList<>();
        List<Coordinate> active = findIndexAllActiveTilesInBoard(board);
        if(getMaxDimensionChosenTiles(bookShelf) <= 1) return null;
        if(active.contains(c1)){
            coordinates = active.stream()
                    .filter(c -> ( (c.getX()==c1.getX() && (c.getY()+1==c1.getY() || c.getY()-1==c1.getY())) ||
                                    (c.getY()==c1.getY() && (c.getX()+1==c1.getX() || c.getX()-1==c1.getX())) ||
                                    (c.getX()==c1.getX() && (c.getY()+2==c1.getY() || c.getY()-2==c1.getY())) ||
                                    (c.getY()==c1.getY() && (c.getX()+2==c1.getX() || c.getX()-2==c1.getX()))
                            )
                    )
                    .collect(Collectors.toList());
            for(Coordinate c: coordinates){
                if(checkIfIsolated(coordinates, c)){
                    coordinates.remove(c);
                }
            }

        }
        return coordinates;
    }

    /**
     * Retrieves a list of coordinates that correspond
     * to the TileSubject selectable in the board given
     * two selected tiles
     *
     * @param board the board matrix
     * @param c1 the coordinate of the first selected tile
     * @param c2 the coordinate of the second selected tile
     * @param bookShelf the player's bookshelf
     * @return coordinates of clickable tiles on board
     * given a selected tile
     *
     * @apiNote The order in which the tiles are selected
     * is irrelevant
     */
    public static List<Coordinate> findIndexActiveAfterTwoChosenTiles(TileSubject[][] board, Coordinate c1, Coordinate c2, TileSubject[][] bookShelf){
        List<Coordinate> active = findIndexAllActiveTilesInBoard(board);
        if(getMaxDimensionChosenTiles(bookShelf) <= 2) return null;
        if(c1.getX()==c2.getX() && c1.getY()==c2.getY()) return active;
        List<Coordinate> list = new ArrayList<>();
        List<Coordinate> result = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        if(checkIfSameX(list)){
            int i = orderedYCoordinate(list).get(0);
            int j = orderedYCoordinate(list).get(1);
            result = active.stream().filter(c -> (c.getX()==c1.getX() && (i-1==c.getY() || j+1==c.getY()))).toList();
        }
        if(checkIfSameY(list)){
            int i = orderedXCoordinate(list).get(0);
            int j = orderedXCoordinate(list).get(1);
            result = active.stream().filter(c -> ( c.getY()==c1.getY() && (i-1==c.getX() || j+1==c.getX() ))).toList();
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

    private static boolean checkIfIsolated(List<Coordinate> tiles, Coordinate c){
        for(Coordinate c1 : tiles){
            if ((c.getX()==c1.getX() && (c.getY()+1==c1.getY() || c.getY()-1==c1.getY())) ||
                    (c.getY()==c1.getY() && (c.getX()+1==c1.getX() || c.getX()-1==c1.getX())))
                return false;
        }
        return true;
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
