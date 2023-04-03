package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

/**
 *
 */
@Deprecated
public class GroupCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 4656354678637L;
    private int groupsNumber;
    private int adjacentTilesPo2;//Numero di tiles nei gruppi, espresso come potenza di due. Per esempio, se il numero è 4 allora questo attributo deve essere impostato a 2.

    public GroupCommonGoal(String description, int groupsNumber, int adjacentTilesPo2){
        super(description);
        this.groupsNumber = groupsNumber;
        this.adjacentTilesPo2 = adjacentTilesPo2;
    }

    public GroupCommonGoal(Stack<Integer> scoringTokens, String description, int groupsNumber, int adjacentTilesPo2){
        super(scoringTokens, description);
        this.groupsNumber = groupsNumber;
        this.adjacentTilesPo2 = adjacentTilesPo2;
    }

    public int getAdjacentTilesPo2() {
        return adjacentTilesPo2;
    }

    public void setAdjacentTilesPo2(int adjacentTilesPo2) {
        this.adjacentTilesPo2 = adjacentTilesPo2;
    }

    public int getGroupsNumber() {
        return groupsNumber;
    }

    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf){
        List<List<Set<EntryPatternGoal>>> result = new ArrayList<>();
        for(TileType tileType : TileType.values()){
            List<EntryPatternGoal> entries = new ArrayList<>();
            for(int i = 0;i<bookShelf.length;i++){
                for(int j = 0;j<bookShelf[0].length;j++){
                    if(bookShelf[i][j]==tileType){
                        entries.add(new EntryPatternGoal(i,j,tileType));
                    }
                }
            }
            result.add(getGroups(generateGroups(this.adjacentTilesPo2,entries),this.groupsNumber,true));
        }
        List<Set<EntryPatternGoal>> groups = result.stream().flatMap(Collection::stream).toList();
        if(groups.size()>=groupsNumber){
            return groups.stream().limit(groupsNumber).flatMap(Collection::stream).toList();
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
}


