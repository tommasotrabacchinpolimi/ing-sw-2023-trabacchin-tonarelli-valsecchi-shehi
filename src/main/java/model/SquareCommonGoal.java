package model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 *SquareCommonGoal is a class that represents a generic CommonGoal which is satisfied iff is possible to find in the BookShelf a given number
 * of square-shaped groups of a given dimension. The tiles of each group must be of the same type.
 */

public class SquareCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 625473943L;
    /**
     * The number of square-shaped groups that needs to be found in order to complete the Goal
     */
    private int groupsNumber;
    /**
     * The dimension required for the groups, that is the groups must be squareDim x squareDim squares
     */
    private int squareDim;

    /**
     * The method used for creating the SquareCommonGoal
     * @param groupsNumber the number of square-shaped groups that needs to be found in order to complete the Goal
     * @param squareDim the dimension required for the groups, that is the groups must be squareDim x squareDim squares
     * @param description Textual description of the Common Goal
     */
    public SquareCommonGoal(int groupsNumber, int squareDim, String description){
        super(description);
        this.groupsNumber = groupsNumber;
        this.squareDim = squareDim;
    }


    /**
     * Gets the groups number
     * @return the groups number
     */
    public int getGroupsNumber() {
        return groupsNumber;
    }

    /**
     * Sets the groups number
     * @param groupsNumber the number to which the groupsNumber attribute is to be set
     */
    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    /**
     * Gets the dimension of the squares
     * @return the dimension of the squares
     */
    public int getSquareDim() {
        return squareDim;
    }

    /**
     * Sets the groups dimension
     * @param squareDim the number to which the squareDim attribute is to be set
     */
    public void setSquareDim(int squareDim) {
        this.squareDim = squareDim;
    }


    /**
     * The method returns a list of the EntryPatternGoals that satisfy the SquareCommonGoal for the BookShelf passed as argument.*
     * If the SquareCommonGoal is not satisfied for the BookShelf passed as arguments it returns null
     * @param bookShelf the BookShelf to check for the SquareCommonGoal
     * @return  list of the EntryPatternGoals that satisfy the SquareCommonGoal, null otherwise
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        for(TileType tileType : TileType.values()){
            TileType[][] copied_bookshelf =  Arrays.stream(bookShelf).map(TileType[]::clone).toArray(TileType[][]::new);
            Set<Set<EntryPatternGoal>> result = findNSquareGroup(copied_bookshelf,groupsNumber,squareDim,tileType);
            if(!result.isEmpty()){
                return result.stream().flatMap(Collection::stream).collect(Collectors.toList());
            }
        }
        return null;
    }

    /**
     * The method searches bookshelf for groupsToFind dim x dim square-shaped groups of tileType type.
     * It returns the set of the found groups, or null, if such groups cannot be found.
     * @param bookshelf the BookShelf to check
     * @param groupsToFind the number of square-shaped groups to find
     * @param dim the required dimension for the groups
     * @param tileType the TileType of the groups
     * @return the set of the found groups, or null, if such groups cannot be found
     */
    private Set<Set<EntryPatternGoal>> findNSquareGroup(TileType[][] bookshelf, int groupsToFind, int dim, TileType tileType){
        Set<Set<EntryPatternGoal>> result = new HashSet<>();
        for(int i = 0;i<bookshelf.length;i++){
            for(int j = 0;j<bookshelf[0].length;j++){
                Set<EntryPatternGoal> group = getSquareGroupFromUpperLeft(i,j,dim,bookshelf,tileType);
                if(!group.isEmpty()){
                    if(groupsToFind!=1){
                        Set<Set<EntryPatternGoal>> others = findNSquareGroup(bookshelf,groupsToFind-1,dim,tileType);
                        restoreBookShelf(bookshelf,group);
                        if(!others.isEmpty()){
                            result.add(group);
                            result.addAll(others);
                            return result;
                        }
                    }
                    else{
                        restoreBookShelf(bookshelf,group);
                        result.add(group);
                        return result;
                    }
                }
            }
        }
        return new HashSet<>();
    }

    /**
     * The method returns, if it exists, the dim x dim square group , whose upper-left tile is located at (row,column) in the bookshelf and whose type is tileType.
     * If such group can be found then the corresponding tiles in the bookshelf are set to null.
     * If no such group can be found the method returns null, leaving the bookshelf unchanged.
     * @param row   the row of the upper-left tile
     * @param column    the column of the upper-left tile
     * @param dim   the desired dimension of the group
     * @param bookShelf     the bookshelf to search for the group
     * @param tileType  the type of the tile of the group
     *
     * @return if it exists, the dim x dim square group , whose upper-left tile is located at (row,column) in the bookshelf and whose type is tileType. If no such group can be found the method returns null.
     */
    private Set<EntryPatternGoal> getSquareGroupFromUpperLeft(int row, int column, int dim, TileType[][] bookShelf, TileType tileType){
        Set<EntryPatternGoal> group = new HashSet<>();
        if(row+dim>bookShelf.length||column+dim>bookShelf[0].length){
            return group;
        }
        for(int i = row;i<row+dim;i++){
            for(int j = column;j<column+dim;j++){
                if(bookShelf[i][j]!=tileType){
                    return new HashSet<>();
                }
                group.add(new EntryPatternGoal(j,i,bookShelf[i][j]));
            }
        }
        for(EntryPatternGoal e : group){
            bookShelf[e.getRow()][e.getColumn()] = null;
        }
        return group;
    }

    /**
     * The method sets the bookshelf so that it matches the EntryPatternGoals contained in group
     * @param bookShelf the bookshelf to restore
     * @param group the group the bookshelf must match after calling this method
     */
    private void restoreBookShelf(TileType[][] bookShelf, Set<EntryPatternGoal> group){
        for(EntryPatternGoal e : group){
            bookShelf[e.getRow()][e.getColumn()] = e.getTileType();
        }
    }




































    /**
     * The method takes in input a group (along with the rows number and columns number of the BookShelf) and return an optional of its dimension if it is a square,
     * an empty optional otherwise
     * @param group the group to check
     * @param numRows the number of rows in the BookShelf containing the group
     * @param numCols the number of columns in the BookShelf containing the group
     * @return the optional of the square dimension if the group is a square, an empty optional otherwise
     */

    private Optional<Integer> isSquare(Set<EntryPatternGoal> group, int numRows, int numCols){
        int max_row,min_row,max_col,min_col;
        Comparator<EntryPatternGoal> rowComparator = Comparator.comparingInt(EntryPatternGoal::getRow);
        Comparator<EntryPatternGoal> colComparator = Comparator.comparingInt(EntryPatternGoal::getColumn);
        max_row = Collections.max(group,rowComparator).getRow();
        min_row = Collections.min(group,rowComparator).getRow();
        max_col = Collections.max(group,colComparator).getColumn();
        min_col = Collections.min(group,colComparator).getColumn();
        boolean[][] matrix = new boolean[numRows][numCols];
        for(EntryPatternGoal e : group){
            matrix[e.getRow()][e.getColumn()] = true;
        }
        for(int i = min_row;i<=max_row;i++){
            for(int j = min_col;j<=max_col;j++){
                if(!matrix[i][j]){
                    return Optional.empty();
                }
            }
        }
        if(max_row-min_row!=max_col-min_col){
            return Optional.empty();
        }
        else{
            return Optional.of(max_row-min_row + 1);
        }
    }

    /**
     * The method returns the Set of all groups in the BookShelf. Each group is represented as the Set containing the EntryPatternGoals that form the group.
     * A group is defined as a set of adjacent tiles with the same type.
     * @param bookShelf the BookShelf to check
     * @return the Set of all groups in the BookShelf
     */
    private Set<Set<EntryPatternGoal>> findGroups(TileType[][] bookShelf, int maxGroupSize){
        boolean[][] alreadyTaken = new boolean[bookShelf.length][bookShelf[0].length];//initialized to false
        Set<Set<EntryPatternGoal>> result = new HashSet<Set<EntryPatternGoal>>();
        for(int i = 0;i<bookShelf.length;i++){
            for(int j = 0;j<bookShelf[0].length;j++){
                findSingleGroup(i,j,bookShelf,alreadyTaken,bookShelf[i][j]).ifPresent(result::add);
            }
        }
        return result;
    }

    /**
     * The method finds the largest possible group (possibly an empty one), assuming the tile at (i,j) is part of the group. Group's tile should be of tileType type, and should not be already part
     * of another group.
     * @param i the row coordinate of the tile that is assumed to be part of the group to find
     * @param j the column coordinate of the tile that is assumed to be part of the group to find
     * @param bookShelf the BookShelf to check for the group
     * @param alreadyTaken a matrix representing the tiles already part of another group
     * @param tileType the type that group's tiles must be of
     * @return the largest possible group
     */
    private Optional<Set<EntryPatternGoal>> findSingleGroup(int i, int j, TileType[][] bookShelf, boolean[][] alreadyTaken, TileType tileType){
        if(tileType==null){
            return Optional.empty();
        }
        if (i<0||i>=bookShelf.length||j<0||j>=bookShelf[0].length){// nothing is to be returned if arguments are illegal
            return Optional.empty();
        }
        if (alreadyTaken[i][j]){ //if this bookShelf is already part of another group then it should not be considered for another group
            return Optional.empty();
        }

        Set<EntryPatternGoal> result = new HashSet<>();// Java documentation recommends using HashSet, unless otherwise required
        if (bookShelf[i][j]!=tileType){//we want only entries whose type is tileType
            return Optional.empty();
        }
        else{
            result.add(new EntryPatternGoal(j,i,tileType));//if the type is correct then the (i,j)-entry can be added to the group
            alreadyTaken[i][j] = true;
        }
        findSingleGroup(i-1,j,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        findSingleGroup(i+1,j,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        findSingleGroup(i,j-1,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        findSingleGroup(i,j+1,bookShelf,alreadyTaken,tileType).ifPresent(result::addAll);
        return Optional.of(result);
    }
}
