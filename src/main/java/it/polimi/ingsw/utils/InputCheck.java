package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.exceptions.WrongChosenTilesFromBoardException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class InputCheck {
    /**
     * Checks the validity of the chosen tiles from the board in the game.
     * @param chosenTiles list of {@linkplain Coordinate coordinates} of the chosen tiles.
     * @param bookShelf matrix of {@linkplain TileSubject tile subjects} representing the player's bookshelf.
     * @param board matrix of {@linkplain  TileSubject tile subjects} representing the game board.
     * @throws WrongChosenTilesFromBoardException exception thrown when the chosen tiles aren't valid.
     *
     * @see Coordinate
     * @see TileSubject
     * @see WrongChosenTilesFromBoardException
     */
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
     * Retrieves a list of {@linkplain Coordinate coordinates} that correspond
     * to the {@linkplain TileSubject tile subjects} selectable in the board
     *
     * @param board the board matrix
     *
     * @return {@linkplain Coordinate coordinates} of clickable tiles on board
     *
     * @see Coordinate
     * @see TileSubject
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
     * Retrieves a list of {@linkplain Coordinate coordinates} that correspond
     * to the {@linkplain TileSubject tile subjects} selectable in the board given
     * a selected tile
     *
     * @param board the board matrix
     * @param c1 the {@linkplain Coordinate coordinates} of the selected tiles
     * @param bookShelf the player's bookshelf
     * @return list of {@linkplain Coordinate coordinates} of clickable tiles on board given a selected tile
     * @see Coordinate
     * @see TileSubject
     */
    public static List<Coordinate> findIndexActiveAfterOneChosenTile(TileSubject[][] board, Coordinate c1, TileSubject[][] bookShelf) throws WrongChosenTilesFromBoardException {
        List<Coordinate> coordinates = new ArrayList<>();
        List<Coordinate> active = findIndexAllActiveTilesInBoard(board);
        if(getMaxDimensionChosenTiles(bookShelf) <= 1) return null;
        if(!active.contains(c1)) throw new WrongChosenTilesFromBoardException();

        addSelectableTiles(coordinates, active, new Coordinate(c1.getX() - 1, c1.getY()));
        addSelectableTiles(coordinates, active, new Coordinate(c1.getX()+1, c1.getY()));
        addSelectableTiles(coordinates, active, new Coordinate(c1.getX(), c1.getY()+1));
        addSelectableTiles(coordinates, active, new Coordinate(c1.getX(), c1.getY()-1));
        return coordinates;
    }


    /**
     * Adds the {@linkplain Coordinate coordinate} to the list of selectable tiles if it is present in the list of active {@linkplain Coordinate coordinates} .
     * @param coordinates the list of selectable {@linkplain Coordinate coordinates}.
     * @param active the list of active {@linkplain Coordinate coordinates}.
     * @param c the {@linkplain Coordinate coordinate}  to be added
     *
     * @see Coordinate
     */
    private static void addSelectableTiles(List<Coordinate> coordinates, List<Coordinate> active, Coordinate c) {
        if(active.contains(c)){
            coordinates.add(c);
        }
    }

    /**
     * Retrieves a list of {@linkplain Coordinate coordinates}  that correspond
     * to the TileSubject selectable in the board given
     * two selected tiles
     *
     * @param board the board matrix
     * @param c1 the {@linkplain Coordinate coordinate}  of the first selected tile
     * @param c2 the {@linkplain Coordinate coordinate}  of the second selected tile
     * @param bookShelf the player's bookshelf
     * @return {@linkplain Coordinate coordinates}  of clickable tiles on board given a selected tile
     *
     * @apiNote The order in which the tiles are selected is irrelevant
     * @see Coordinate
     */
    public static List<Coordinate> findIndexActiveAfterTwoChosenTiles(TileSubject[][] board, Coordinate c1, Coordinate c2, TileSubject[][] bookShelf) throws WrongChosenTilesFromBoardException {
        List<Coordinate> active = findIndexAllActiveTilesInBoard(board);
        List<Coordinate> list = new ArrayList<>();
        List<Coordinate> result = new ArrayList<>();
        list.add(c1);
        list.add(c2);

        if(!active.contains(c1) || !active.contains(c2)) throw new WrongChosenTilesFromBoardException();
        if(c1.getX()==c2.getX() && c1.getY()==c2.getY()) return active;
        if((checkIfSameX(list) && !checkIfAdjacent(orderedYCoordinate(list))) || (checkIfSameY(list) && !checkIfAdjacent(orderedXCoordinate(list))))
            throw new WrongChosenTilesFromBoardException("You have selected two non-adjacent tiles!");
        if(getMaxDimensionChosenTiles(bookShelf) <= 2) return null;

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

    /**
     * Checks if the list of {@linkplain Coordinate coordinates} follows the right pattern for selection.
     * @param list the list of {@linkplain Coordinate coordinates} to check
     * @return {@code true} if the {@linkplain Coordinate coordinates} follow the right pattern, {@code false} otherwise.
     * @see Coordinate
     */
    private static boolean checkRightPattern(List<Coordinate> list){
        return (checkIfSameY(list) && checkIfAdjacent(orderedXCoordinate(list))) ||(checkIfSameX(list) && checkIfAdjacent(orderedYCoordinate(list)));
    }

    /**
     * Returns a list of the {@linkplain Coordinate#getX() x-coordinates} from the given list of coordinates in ascending order.
     * @param coordinates the list of {@linkplain Coordinate coordinates}
     * @return the list {@linkplain Coordinate#getX() x-coordinates} in ascending order
     * @see Coordinate
     */
    private static List<Integer> orderedXCoordinate(List<Coordinate> coordinates){
        List<Integer> result = new ArrayList<>();
        if(coordinates==null) return null;

        result = coordinates.stream().map(Coordinate::getX).sorted().toList();

        return result;
    }

    /**
     * Returns a list of the {@linkplain Coordinate#getY() y-coordinates} from the given list of coordinates in ascending order.
     * @param coordinates the list of {@linkplain Coordinate coordinates}
     * @return the list {@linkplain Coordinate#getY() y-coordinates} in ascending order
     * @see Coordinate
     */
    private static List<Integer> orderedYCoordinate(List<Coordinate> coordinates){
        List<Integer> result = new ArrayList<>();
        if(coordinates==null) return null;

        result = coordinates.stream().map(Coordinate::getY).sorted().toList();

        return result;
    }

    /**
     * Checks if all {@linkplain Coordinate coordinates} in the list have the same {@linkplain Coordinate#getX() x-coordinate}.
     * @param coordinates the list of {@linkplain Coordinate coordinates}
     * @return {@code true} if all coordinates have the same {@linkplain Coordinate#getX() x-coordinate}, {@code false} otherwise
     */
    private static boolean checkIfSameX(List<Coordinate> coordinates){
        if(coordinates==null) return false;
        int x = coordinates.get(0).getX();
        for(Coordinate c: coordinates){
            if(c.getX()!=x)
                return false;
        }
        return true;
    }

    /**
     * Checks if all {@linkplain Coordinate coordinates} in the list have the same {@linkplain Coordinate#getY() y-coordinate}.
     * @param coordinates the list of {@linkplain Coordinate coordinates}
     * @return {@code true} if all coordinates have the same {@linkplain Coordinate#getY() y-coordinate}, {@code false} otherwise
     */
    private static boolean checkIfSameY(List<Coordinate> coordinates){
        if(coordinates==null) return false;
        int y = coordinates.get(0).getY();
        for(Coordinate c: coordinates){
            if(c.getY()!=y)
                return false;
        }
        return true;
    }

    /**
     * Checks if the list of indices is adjacent, i.e., each element is one greater than the previous element.
     * @param index the list of indices.
     * @return {@code true} if the indices are adjacent, {@code false} otherwise.
     * @apiNote the list of indices needs to be sorted beforehand.
     */
    private static boolean checkIfAdjacent(List<Integer> index){
        for(int i = 1; i < index.size(); i++){
            if(index.get(i)!=index.get(i-1)+1){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the maximum dimension that the chosen tiles can have in order to fit into the player's bookshelf.
     * @param bookShelf the matrix of {@linkplain TileSubject tile subjects} representing the player's bookshelf
     * @return the maximum dimension of chosen tiles in the bookshelf
     */
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
