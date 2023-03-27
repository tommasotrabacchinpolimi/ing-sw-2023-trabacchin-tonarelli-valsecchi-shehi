package model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 *SquareCommonGoal is a class that represents a generic CommonGoal which is satisfied iff is possible to find in the BookShelf a given number
 * of square-shaped groups of a given dimension. The tiles of each group must be of the same type.
 */

public class SquareCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 4715442695L;
    /**
     *
     */
    private int groupsNumber;
    /**
     *
     */
    private int squareDimPo2;//Numero di tiles nei gruppi, espresso come potenza di due. Per esempio, se il numero è 1 allora questo attributo deve essere impostato a 0.
    /**
     * flag che indica se i gruppi devono essere separati. In pratica è falso solo nel caso degli 8 gruppi da 1 tile.
     */
    private boolean noAdj;

    public SquareCommonGoal(int groupsNumber, int squareDimPo2, boolean noAdj, String description){
        super(description);
        this.groupsNumber = groupsNumber;
        this.squareDimPo2 = squareDimPo2;
        this.noAdj = noAdj;
    }


    public int getGroupsNumber() {
        return groupsNumber;
    }

    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    public int getSquareDimPo2() {
        return squareDimPo2;
    }

    public void setSquareDimPo2(int squareDimPo2) {
        this.squareDimPo2 = squareDimPo2;
    }

    public void setNoAdj(boolean noAdj){
        this.noAdj = noAdj;
    }

    public boolean getNoAdj(){
        return noAdj;
    }

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        for(TileType tileType : TileType.values()){
            List<EntryPatternGoal> entries = new ArrayList<>();
            for(int i = 0;i<bookShelf.length;i++){
                for(int j = 0;j<bookShelf[0].length;j++){
                    if(bookShelf[i][j]==tileType){
                        entries.add(new EntryPatternGoal(j,i,tileType));
                    }
                }
            }
            List<Set<EntryPatternGoal>> result = getGroups(generateGroups(2*this.squareDimPo2,entries).stream().filter(g->isSquare(g,bookShelf.length,bookShelf[0].length).isPresent()).collect(Collectors.toList()), this.groupsNumber,this.noAdj);
            if(result.size()==this.groupsNumber){
                return result.stream().flatMap(Collection::stream).collect(Collectors.toList());
            }
        }
        return null;
    }

    private List<Set<EntryPatternGoal>> getGroups(List<Set<EntryPatternGoal>> allGroups, int groupsNumber, boolean noAdj){
        return getGroupsRecursive(allGroups,groupsNumber,0,new ArrayList<>(),0,noAdj);
    }

    private List<Set<EntryPatternGoal>> getGroupsRecursive(List<Set<EntryPatternGoal>> allGroups, int groupsNumber, int startingFromGroup, List<Set<EntryPatternGoal>> alreadyFoundGroups, int currentNumberOfGroups, boolean noAdj){
        if(startingFromGroup==allGroups.size()||currentNumberOfGroups==groupsNumber){
            return alreadyFoundGroups;
        }
        if(alreadyFoundGroups.stream().anyMatch(g->areIncompatible(g,allGroups.get(startingFromGroup),noAdj))){
            return getGroupsRecursive(allGroups,groupsNumber,startingFromGroup+1,alreadyFoundGroups,currentNumberOfGroups,noAdj);
        }
        else{
            List<Set<EntryPatternGoal>> resultGroupNotAdded = getGroupsRecursive(allGroups,groupsNumber,startingFromGroup+1,alreadyFoundGroups,currentNumberOfGroups,noAdj);
            List<Set<EntryPatternGoal>> newAlreadyFoundGroups = new ArrayList<>(alreadyFoundGroups);
            newAlreadyFoundGroups.add(allGroups.get(startingFromGroup));
            List<Set<EntryPatternGoal>> resultGroupAdded = getGroupsRecursive(allGroups,groupsNumber,startingFromGroup+1,newAlreadyFoundGroups,currentNumberOfGroups+1,noAdj);
            if(resultGroupAdded.size()>=resultGroupNotAdded.size()){
                return resultGroupAdded;
            }

            else{
                return resultGroupNotAdded;
            }
        }
    }

    private List<Set<EntryPatternGoal>> generateGroups(int dim, List<EntryPatternGoal> tiles){
        List<Set<EntryPatternGoal>> allGroups = new ArrayList<>();
        if(dim==0){
            for(EntryPatternGoal tile : tiles){
                Set<EntryPatternGoal> newSingleElementSet = new HashSet<>();
                newSingleElementSet.add(tile);
                allGroups.add(newSingleElementSet);
            }
        }
        else{
            List<Set<EntryPatternGoal>> subGroups = generateGroups(dim-1,tiles);
            for(Set<EntryPatternGoal> group1 : subGroups){
                for(Set<EntryPatternGoal> group2 : subGroups){
                    if(areAdjacent(group1, group2)){
                        Set<EntryPatternGoal> fusedGroup = fuseGroups(group1,group2);
                        if(!allGroups.contains(fusedGroup))
                        {
                            allGroups.add(fusedGroup);
                        }
                    }
                }
            }
        }
        return allGroups;
    }

    private int getManhattanDistance(EntryPatternGoal e1, EntryPatternGoal e2){
        return Math.abs(e1.getColumn()-e2.getColumn()) + Math.abs(e1.getRow()-e2.getRow());
    }

    private boolean areIncompatible(Set<EntryPatternGoal> group1, Set<EntryPatternGoal> group2, boolean noAdj) {
        for (EntryPatternGoal e1 : group1) {
            for (EntryPatternGoal e2 : group2) {
                if (e1.getRow() == e2.getRow() && e1.getColumn() == e2.getColumn()) {
                    return true;
                }
                if(getManhattanDistance(e1,e2)==1&&noAdj){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean areAdjacent(Set<EntryPatternGoal> group1, Set<EntryPatternGoal> group2){
        boolean areThereAdjacentTiles = false;
        for(EntryPatternGoal e1 : group1){
            for(EntryPatternGoal e2 : group2){
                if(e1.getRow()==e2.getRow()&&e1.getColumn()==e2.getColumn()){
                    return false;
                }
                if(getManhattanDistance(e1,e2)==1){
                    areThereAdjacentTiles = true;
                }
            }
        }
        return areThereAdjacentTiles;
    }

    private Set<EntryPatternGoal> fuseGroups(Set<EntryPatternGoal> group1, Set<EntryPatternGoal> group2){
        Set<EntryPatternGoal> fusedGroup = new HashSet<>();
        fusedGroup.addAll(group1);
        fusedGroup.addAll(group2);
        return fusedGroup;
    }

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
}
