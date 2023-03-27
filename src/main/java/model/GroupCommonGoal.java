package model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class GroupCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 98478359933L;
    private int groupsNumber;
    private int adjacentTiles;


    /*public static void main(String[] args){
        TileType[][] bookShelf = new TileType[][]{
                {TileType.PLANT,TileType.PLANT,TileType.PLANT,null,null},
                {TileType.BOOK,TileType.PLANT,TileType.PLANT,TileType.CAT,null},
                {TileType.FRAME,TileType.BOOK,TileType.FRAME,TileType.BOOK,null},
                {TileType.TROPHY,TileType.GAME,TileType.TROPHY,TileType.GAME,null},
                {TileType.TROPHY,TileType.TROPHY,TileType.CAT,TileType.CAT,TileType.CAT},
                {TileType.TROPHY,TileType.TROPHY,TileType.TROPHY,TileType.CAT,TileType.CAT}};
        GroupCommonGoal g = new GroupCommonGoal(6,2);
        g.findGroups(bookShelf).forEach(System.out::println);
        }*/

    public GroupCommonGoal(int groupsNumber, int adjacentTiles, String description){
        super(description);
        this.groupsNumber = groupsNumber;
        this.adjacentTiles = adjacentTiles;
    }

    public int getAdjacentTiles() {
        return adjacentTiles;
    }

    public void setAdjacentTiles(int adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }

    public int getGroupsNumber() {
        return groupsNumber;
    }

    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf){
        Set<Set<EntryPatternGoal>> groups = findGroups(bookShelf);
        Set<Set<EntryPatternGoal>> candidateGroups = groups.stream().filter(g -> g.size()==adjacentTiles).collect(Collectors.toSet());
        if(candidateGroups.size()==groupsNumber)
        {
            return candidateGroups.stream().flatMap(Collection::stream).collect(Collectors.toList());
        }
        else{
            return null;
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


