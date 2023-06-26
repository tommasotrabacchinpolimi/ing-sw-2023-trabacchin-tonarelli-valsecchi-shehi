package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TupleCommonGoal is a class that represents a generic {@link CommonGoal CommonGoal} which is satisfied if the
 * {@link BookShelf BookShelf} contains a given number of groups of adjacent tiles of the same type on a {@link BookShelf bookshelf}.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @apiNote Valid combination of the parameters values, according to the rules of the game, are the following :
 * <ul>
 *     <li>groupsNumber = 6, adjacentTilesPo2 = 1, square = false, separate = true, sameTypeOnly = false</li>
 *     <li>groupsNumber = 4, adjacentTilesPo2 = 2, square = false, separate = true, sameTypeOnly = false</li>
 *     <li>groupsNumber = 2, adjacentTilesPo2 = 2, square = true, separate = true, sameTypeOnly = true</li>
 *     <li>groupsNumber = 8, adjacentTilesPo2 = 0, square = true, separate = false, sameTypeOnly = true</li>
 * </ul>
 * @since 21/04/2023
 */
public class TupleCommonGoal extends CommonGoal implements Serializable {

    @Serial
    @ExcludedFromJSON
    private static final long serialVersionUID = 1427495031L;

    /**
     * Number of groups searched
     *
     * @apiNote Its values can be 2, 4, 6 or 8
     */
    private int groupsNumber;

    /**
     * Cardinality of a single group as power of 2
     *
     * @apiNote Its values can be 1, 2 and 4
     */
    private int adjacentTiles;

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
    private boolean sameTypeOnly;

    /**
     * Constructor that class the constructor of superclass {@link CommonGoal}.
     *
     * @see CommonGoal#CommonGoal()
     */
    public TupleCommonGoal() {
        super();
    }



    /**
     * Constructor that sets the fields of the class to the parameter passed
     *
     * @param groupsNumber     Number of groups searched.
     * @param adjacentTiles Cardinality of a single group as power of 2
     * @param square           Flag to identify a square-shape of a group
     * @param separated        Flag used to check for separated groups or not
     * @param sameTypeOnly     Flag used to check for tile of the same {@link TileType type}
     * @see CommonGoal#CommonGoal()
     */
    public TupleCommonGoal(int groupsNumber, int adjacentTiles, boolean square, boolean separated, boolean sameTypeOnly) {
        super();
        initTupleGoalProperties(groupsNumber, adjacentTiles, square, separated, sameTypeOnly);
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed
     *
     * @param groupsNumber     Number of groups searched.
     * @param adjacentTiles Cardinality of a single group as power of 2
     * @param square           Flag to identify a square-shape of a group
     * @param separated        Flag used to check for separated groups or not
     * @param sameTypeOnly     Flag used to check for tile of the same {@link TileType type}
     * @param description      explanation of common goal card
     * @see CommonGoal#CommonGoal(String description, String id)
     */
    public TupleCommonGoal(String description, String id, int groupsNumber, int adjacentTiles, boolean square, boolean separated, boolean sameTypeOnly) {
        super(description, id);
        initTupleGoalProperties(groupsNumber, adjacentTiles, square, separated, sameTypeOnly);
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed
     *
     * @param groupsNumber     Number of groups searched.
     * @param adjacentTiles Cardinality of a single group as power of 2
     * @param square           Flag to identify a square-shape of a group
     * @param separated        Flag used to check for separated groups or not
     * @param sameTypeOnly     Flag used to check for tile of the same {@link TileType type}
     * @param scoringTokens    scoring tokens stack
     * @see CommonGoal#CommonGoal(Stack scoringTokens, String description, String id)
     */
    public TupleCommonGoal(Stack<Integer> scoringTokens, String description, String id, int groupsNumber, int adjacentTiles, boolean square, boolean separated, boolean sameTypeOnly) {
        super(scoringTokens, description, id);
        initTupleGoalProperties(groupsNumber, adjacentTiles, square, separated, sameTypeOnly);
    }

    /**
     * Initialize the specific properties for the Line Common Goal
     * class
     *
     * @param groupsNumber     Number of groups searched.
     * @param adjacentTilesPo2 Number of tiles that must be of the same type
     *                         in each group
     * @param square           Flag to identify a square-shape of a group that
     * @param separated        Flag used to check for separated groups or
     *                         not
     * @param sameTypeOnly     Flag used to check for tile of the same
     *                         {@link TileType type}
     */
    private void initTupleGoalProperties(int groupsNumber, int adjacentTilesPo2, boolean square, boolean separated, boolean sameTypeOnly) {
        this.groupsNumber = groupsNumber;
        this.adjacentTiles = adjacentTilesPo2;
        this.square = square;
        this.separated = separated;
        this.sameTypeOnly = sameTypeOnly;
    }

    /**
     * Method that returns {@link #groupsNumber}
     *
     * @return the value of {@link #groupsNumber}
     */
    public int getGroupsNumber() {
        return groupsNumber;
    }

    /**
     * Method that sets {@link #groupsNumber}
     *
     * @param groupsNumber the value that need to be set as {@link #groupsNumber}
     */
    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    /**
     * Method that returns {@link #adjacentTiles}
     *
     * @return the value of {@link #adjacentTiles}
     */
    public int getAdjacentTiles() {
        return adjacentTiles;
    }

    /**
     * Method that sets {@link #adjacentTiles}
     *
     * @param adjacentTiles the value that need to be set as {@link #adjacentTiles}
     */
    public void setAdjacentTiles(int adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }

    /**
     * Method that returns {@link #square}
     *
     * @return the value of {@link #square}
     */
    public boolean isGroupSquare() {
        return square;
    }

    /**
     * Method that sets {@link #square}
     *
     * @param square the value that need to be set as {@link #square}
     */
    public void setSquare(boolean square) {
        this.square = square;
    }

    /**
     * Method that returns {@link #separated}
     *
     * @return the value of {@link #separated}
     */
    public boolean isSeparated() {
        return separated;
    }

    /**
     * Method that sets {@link #separated}
     *
     * @param separated the value that need to be set as {@link #separated}
     */
    public void setSeparated(boolean separated) {
        this.separated = separated;
    }

    /**
     * Method that returns {@link #sameTypeOnly}
     *
     * @return the value of {@link #separated}
     */
    public boolean isSameTypeOnly() {
        return sameTypeOnly;
    }

    /**
     * Method that sets {@link #sameTypeOnly}
     *
     * @param sameTypeOnly the value that need to be set as {@link #sameTypeOnly}
     */
    public void setSameTypeOnly(boolean sameTypeOnly) {
        this.sameTypeOnly = sameTypeOnly;
    }


    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        Set<Set<EntryPatternGoal>> groups;
        if(isSeparated()) {
            Set<EntryPatternGoal> group = new HashSet<>();
            for(int i = 0; i < bookShelf.length; i++) {
                for(int j = 0; j < bookShelf[0].length; j++) {
                    group.add(new EntryPatternGoal(i, j, bookShelf[i][j]));
                }
            }
            groups = new HashSet<>();
            groups.add(group);
        }
        else {
            groups = State.findGroups(bookShelf);
        }


            groups = groups
                    .stream()
                    .filter((g -> !this.square||Integer.valueOf(this.adjacentTiles).equals(isGroupSquare(g, bookShelf.length, bookShelf[0].length))))
                    .collect(Collectors.toSet());

        if (!isSeparated()) {
            groups = groups.stream().filter(g -> verifyMinimumNumberOfDifferentTileType(g, this.adjacentTiles)!=null).collect(Collectors.toSet());
            if(groups.size()>=getGroupsNumber()) {
                return groups.stream().limit(getGroupsNumber()).flatMap(Collection::stream).toList();
            }
        }
        else {
            for(Set<EntryPatternGoal> g : groups) {
                Set<EntryPatternGoal> subG = verifyMinimumNumberOfDifferentTileType(g, getAdjacentTiles());
                if(subG != null) {
                    return new ArrayList<>(subG);
                }
            }
        }



        return null;
    }

    private Set<EntryPatternGoal> verifyMinimumNumberOfDifferentTileType(Set<EntryPatternGoal> group, int min) {
        for(TileType tileType : TileType.values()) {
            Set<EntryPatternGoal> subGroup = new HashSet<>();
            for(EntryPatternGoal e : group) {
                if(e.getTileType().equals(tileType)) {
                    subGroup.add(e);
                }
            }
            if(subGroup.size() >= min) {
                return subGroup;
            }
        }
        return null;
    }
    private Integer isGroupSquare(Set<EntryPatternGoal> group, int numRows, int numCols) {
        int max_row;
        int min_row;
        int max_col;
        int min_col;

        Comparator<EntryPatternGoal> rowComparator = Comparator.comparingInt(EntryPatternGoal::getRow);

        Comparator<EntryPatternGoal> colComparator = Comparator.comparingInt(EntryPatternGoal::getColumn);

        max_row = Collections.max(group, rowComparator).getRow();
        min_row = Collections.min(group, rowComparator).getRow();
        max_col = Collections.max(group, colComparator).getColumn();
        min_col = Collections.min(group, colComparator).getColumn();

        boolean[][] matrix = new boolean[numRows][numCols];
        TileType test = null;
        for (EntryPatternGoal e : group) {
            matrix[e.getRow()][e.getColumn()] = true;
            if(test != null && e.getTileType()!=test) {
                return null;
            }
            else if(test == null){
                test = e.getTileType();
            }
        }

        for (int i = min_row; i <= max_row; i++) {
            for (int j = min_col; j <= max_col; j++) {
                if (!matrix[i][j]) {
                    return null;
                }
            }
        }

        if (max_row - min_row != max_col - min_col) {
            return null;
        } else {
            return max_row - min_row + 1;
        }
    }
}