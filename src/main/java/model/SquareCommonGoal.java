package model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 SquareCommonGoal is a class that represents a generic CommonGoal which is satisfied if the BookShelf contains a given number
 of groups each being arranged as a square of a given dimension.
 In the context of this class, with a little abuse of terminology, the circumscribed rectangle of a given group is defined to be the smallest rectangle-shape
 group that contains all the tiles of the given group. (A group is defined to be rectangle-shaped if and only if there are two coordinates (i1,j1) and (i2,j2)
 such that j2>=j1 and i2>=i1 and for all BookShelf's coordinates (i3,j3) then, (i3,j3) belongs to the group iff i1<=i3<=i2 and j1<=j3<=j2. Moreover, a rectangle-shaped group is said to be square-shaped
 iff i2-i1=j2-j1)
 Therefore, a group is defined to be a square if and ond if its circumscribed rectangle is formed only by the group's tiles and is square-shaped.
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
     */
    public SquareCommonGoal(int groupsNumber, int squareDim){
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
     * The method returns null if the SquareCommonGoal is not satisfied for the BookShelf passes as argument.
     * If the SquareCommonGoal is satisfied then the method returns the list of the EntryPatternGoals representing the tiles in the BookShelf that satisfy the SquareCommonGoal
     * @param bookShelf the BookShelf to check for the SquareCommonGoal
     * @return  null if the SquareCommonGoal is satisfied, otherwise the list of the EntryPatternGoals representing the tiles in the BookShelf that satisfy the SquareCommonGoal
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        Set<Set<EntryPatternGoal>> groups = findGroups(bookShelf);
        Set<Set<EntryPatternGoal>> candidateGroups = groups.stream().filter(g-> isSquare(g, bookShelf.length, bookShelf[0].length).filter(d->d==squareDim).isPresent()).collect(Collectors.toSet());
        if(candidateGroups.size()==groupsNumber){
            return candidateGroups.stream().flatMap(Collection::stream).collect(Collectors.toList());
        }
        else{
            return null;
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
    private Set<Set<EntryPatternGoal>> findGroups(TileType[][] bookShelf){
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
