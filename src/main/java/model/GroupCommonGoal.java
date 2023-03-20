package model;

import java.io.Serializable;
import java.util.List;

public class GroupCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 98478359933L;
    private int groupsNumber;
    private int adjacentTiles;

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
    public List<EntryPatternGoal> rule(){

        return null;
    }
}
