package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @apiNote Valid combination of the parameters values are the following :
 * <ul>
 *     <li>groupsNumber = 6, adjacentTilesPo2 = 1, square = false, separate = true, sameTypeOnly = false</li>
 *     <li>groupsNumber = 4, adjacentTilesPo2 = 2, square = false, separate = true, sameTypeOnly = false</li>
 *     <li>groupsNumber = 2, adjacentTilesPo2 = 2, square = true, separate = true, sameTypeOnly = true</li>
 *     <li>groupsNumber = 8, adjacentTilesPo2 = 0, square = true, separate = false, sameTypeOnly = true</li>
 * </ul>
 */
public class TupleCommonGoal extends CommonGoal implements Serializable {

    @Serial
    @ExcludedFromJSON
    private static final long serialVersionUID = 1427495031L;

    /**
     * Number of groups searched
     * @apiNote Its values can be 2, 4, 6 or 8
     */
    private int groupsNumber;

    /**
     * Cardinality of a single group as power of 2
     * @apiNote Its values can be 0, 1 and 2
     */
    private int adjacentTilesPo2;

    /**
     * Flag to identify a square-shape of a group
     *
     * @apiNote true value checks for square shape. On the other hand false value does not
     */
    private boolean square;

    /**
     * Flag used to check for separated groups or not
     *
     * @apiNote true value checks for separated. On the other hand false does not
     */
    private boolean separated;

    /**
     * Flag used to check for tile of the same {@link TileType type}
     *
     * @apiNote true value checks for same type only. On the other hand a false value means {@link TileType type} can be different
     */
    private boolean sameTypeOnly;//flag per indicare se i gruppi trovati devono essere tutti dello stesso tipo

    public TupleCommonGoal(int groupsNumber, int adjacentTilesPo2, boolean square, boolean separated, boolean sameTypeOnly) {
        super();
        this.groupsNumber = groupsNumber;
        this.adjacentTilesPo2 = adjacentTilesPo2;
        this.square = square;
        this.separated = separated;
        this.sameTypeOnly = sameTypeOnly;
    }

    public TupleCommonGoal(String description, int groupsNumber, int adjacentTilesPo2, boolean square, boolean separated, boolean sameTypeOnly) {
        super(description);
        this.groupsNumber = groupsNumber;
        this.adjacentTilesPo2 = adjacentTilesPo2;
        this.square = square;
        this.separated = separated;
        this.sameTypeOnly = sameTypeOnly;
    }

    public TupleCommonGoal(Stack<Integer> scoringTokens, int groupsNumber, int adjacentTilesPo2, boolean square, boolean separated, boolean sameTypeOnly) {
        super(scoringTokens);
        this.groupsNumber = groupsNumber;
        this.adjacentTilesPo2 = adjacentTilesPo2;
        this.square = square;
        this.separated = separated;
        this.sameTypeOnly = sameTypeOnly;
    }

    public TupleCommonGoal(Stack<Integer> scoringTokens, String description, int groupsNumber, int adjacentTilesPo2, boolean square, boolean separated, boolean sameTypeOnly) {
        super(scoringTokens, description);
        this.groupsNumber = groupsNumber;
        this.adjacentTilesPo2 = adjacentTilesPo2;
        this.square = square;
        this.separated = separated;
        this.sameTypeOnly = sameTypeOnly;
    }

    public int getGroupsNumber() {
        return groupsNumber;
    }

    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    public int getAdjacentTilesPo2() {
        return adjacentTilesPo2;
    }

    public void setAdjacentTilesPo2(int adjacentTilesPo2) {
        this.adjacentTilesPo2 = adjacentTilesPo2;
    }

    public boolean isSquare() {
        return square;
    }

    public void setSquare(boolean square) {
        this.square = square;
    }

    public boolean isSeparated() {
        return separated;
    }

    public void setSeparated(boolean separated) {
        this.separated = separated;
    }

    public boolean isSameTypeOnly() {
        return sameTypeOnly;
    }

    public void setSameTypeOnly(boolean sameTypeOnly) {
        this.sameTypeOnly = sameTypeOnly;
    }

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        List<List<Set<EntryPatternGoal>>> result = new ArrayList<>();

        for(TileType tileType : TileType.values()){
            List<EntryPatternGoal> entries = new ArrayList<EntryPatternGoal>();

            for(int i = 0; i < bookShelf.length; i++){
                for(int j = 0; j < bookShelf[0].length; j++){
                    if(bookShelf[i][j] == tileType){
                        entries.add(new EntryPatternGoal(i,j,tileType));
                    }
                }
            }

            List<Set<EntryPatternGoal>> sameTypeResult =
                    getGroups(
                        generateGroups(this.adjacentTilesPo2,entries).stream()
                                .filter(g -> !square || isSquare(g, bookShelf.length, bookShelf[0].length).isPresent())
                                .collect(Collectors.toList()),
                        this.groupsNumber, this.separated);

            if(sameTypeResult.size()>=this.groupsNumber&&sameTypeOnly){
                return sameTypeResult.stream().limit(groupsNumber).flatMap(Collection::stream).collect(Collectors.toList());
            }
            result.add(sameTypeResult);
        }
        List<Set<EntryPatternGoal>> groups = result.stream().flatMap(Collection::stream).toList();
        if(groups.size()>=groupsNumber&&!sameTypeOnly){
            return groups.stream().limit(groupsNumber).flatMap(Collection::stream).toList();
        }
        return null;
    }
    private List<Set<EntryPatternGoal>> getGroups(List<Set<EntryPatternGoal>> allGroups, int groupsNumber, boolean noAdj){
        return getGroupsRecursive(allGroups,groupsNumber,0,new ArrayList<>(),0,noAdj);
    }

    private List<Set<EntryPatternGoal>> getGroupsRecursive(List<Set<EntryPatternGoal>> allGroups, int groupsNumber, int startingFromGroup, List<Set<EntryPatternGoal>> alreadyFoundGroups, int currentNumberOfGroups, boolean noAdj) {
        if(startingFromGroup==allGroups.size()||currentNumberOfGroups==groupsNumber) {
            return alreadyFoundGroups;
        }

        if(alreadyFoundGroups.stream().anyMatch(g -> areIncompatible(g, allGroups.get(startingFromGroup), noAdj))) {
            return getGroupsRecursive(allGroups, groupsNumber, startingFromGroup + 1, alreadyFoundGroups, currentNumberOfGroups, noAdj);
        } else {
            List<Set<EntryPatternGoal>> resultGroupNotAdded = getGroupsRecursive(allGroups,groupsNumber,startingFromGroup+1,alreadyFoundGroups,currentNumberOfGroups,noAdj);
            List<Set<EntryPatternGoal>> newAlreadyFoundGroups = new ArrayList<>(alreadyFoundGroups);

            newAlreadyFoundGroups.add(allGroups.get(startingFromGroup));

            List<Set<EntryPatternGoal>> resultGroupAdded = getGroupsRecursive(allGroups,groupsNumber,startingFromGroup+1,newAlreadyFoundGroups,currentNumberOfGroups+1,noAdj);

            if(resultGroupAdded.size() >= resultGroupNotAdded.size()) {
                return resultGroupAdded;
            }

            else{
                return resultGroupNotAdded;
            }
        }
    }

    private List<Set<EntryPatternGoal>> generateGroups(int dim, List<EntryPatternGoal> tiles) {
        List<Set<EntryPatternGoal>> allGroups = new ArrayList<>();
        if(dim == 0) {
            for(EntryPatternGoal tile : tiles) {
                Set<EntryPatternGoal> newSingleElementSet = new HashSet<>();
                newSingleElementSet.add(tile);
                allGroups.add(newSingleElementSet);
            }
        } else {
            List<Set<EntryPatternGoal>> subGroups = generateGroups(dim - 1,tiles);

            for(Set<EntryPatternGoal> group1 : subGroups) {
                for(Set<EntryPatternGoal> group2 : subGroups) {
                    if(areAdjacent(group1, group2)) {
                        Set<EntryPatternGoal> fusedGroup = fuseGroups(group1, group2);
                        if(!allGroups.contains(fusedGroup)) {
                            allGroups.add(fusedGroup);
                        }
                    }
                }
            }
        }

        return allGroups;
    }

    private int getManhattanDistance(EntryPatternGoal e1, EntryPatternGoal e2) {
        return Math.abs(e1.getColumn() - e2.getColumn()) + Math.abs(e1.getRow() - e2.getRow());
    }

    private boolean areIncompatible(Set<EntryPatternGoal> group1, Set<EntryPatternGoal> group2, boolean noAdj) {
        for (EntryPatternGoal e1 : group1) {
            for (EntryPatternGoal e2 : group2) {
                if (e1.getRow() == e2.getRow() && e1.getColumn() == e2.getColumn()) {
                    return true;
                }

                if(getManhattanDistance(e1,e2) == 1 && noAdj){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean areAdjacent(Set<EntryPatternGoal> group1, Set<EntryPatternGoal> group2) {
        boolean areThereAdjacentTiles = false;

        for(EntryPatternGoal e1 : group1) {
            for(EntryPatternGoal e2 : group2) {
                if(e1.getRow() == e2.getRow() && e1.getColumn() == e2.getColumn()) {
                    return false;
                }

                if(getManhattanDistance(e1,e2) == 1) {
                    areThereAdjacentTiles = true;
                }
            }
        }

        return areThereAdjacentTiles;
    }

    private Set<EntryPatternGoal> fuseGroups(Set<EntryPatternGoal> group1, Set<EntryPatternGoal> group2) {
        Set<EntryPatternGoal> fusedGroup = new HashSet<>();

        fusedGroup.addAll(group1);
        fusedGroup.addAll(group2);

        return fusedGroup;
    }

    private Optional<Integer> isSquare(Set<EntryPatternGoal> group, int numRows, int numCols){
        int max_row;
        int min_row;
        int max_col;
        int min_col;

        Comparator<EntryPatternGoal> rowComparator = Comparator.comparingInt(EntryPatternGoal::getRow);

        Comparator<EntryPatternGoal> colComparator = Comparator.comparingInt(EntryPatternGoal::getColumn);

        max_row = Collections.max(group,rowComparator).getRow();
        min_row = Collections.min(group,rowComparator).getRow();
        max_col = Collections.max(group,colComparator).getColumn();
        min_col = Collections.min(group,colComparator).getColumn();

        boolean[][] matrix = new boolean[numRows][numCols];

        for(EntryPatternGoal e : group) {
            matrix[e.getRow()][e.getColumn()] = true;
        }

        for(int i = min_row; i <= max_row; i++) {
            for(int j = min_col ; j <= max_col ; j++) {
                if(!matrix[i][j]){
                    return Optional.empty();
                }
            }
        }

        if(max_row - min_row != max_col - min_col) {
            return Optional.empty();
        } else {
            return Optional.of(max_row - min_row + 1);
        }
    }
}
