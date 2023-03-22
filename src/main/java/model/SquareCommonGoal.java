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
    private int groupsNumber;
    private int squareDim;

    public SquareCommonGoal(int groupsNumber, int squareDim){
        this.groupsNumber = groupsNumber;
        this.squareDim = squareDim;
    }



    public int getGroupsNumber() {
        return groupsNumber;
    }

    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    public int getSquareDim() {
        return squareDim;
    }

    public void setSquareDim(int squareDim) {
        this.squareDim = squareDim;
    }

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        Set<Set<EntryPatternGoal>> groups = findGroups(bookShelf);
        Set<Set<EntryPatternGoal>> candidateGroups = groups.stream().filter(g -> isSquare(g, bookShelf.length, bookShelf[0].length)==squareDim).collect(Collectors.toSet());
        if(candidateGroups.size()==groupsNumber){
            return candidateGroups.stream().flatMap(Collection::stream).collect(Collectors.toList());
        }
        else{
            return null;
        }
    }

    private int isSquare(Set<EntryPatternGoal> group, int numRows, int numCols){
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
                    return -1;
                }
            }
        }
        if(max_row-min_row!=max_col-min_col){
            return -1;
        }
        else{
            return max_row-min_row + 1;
        }
    }
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
