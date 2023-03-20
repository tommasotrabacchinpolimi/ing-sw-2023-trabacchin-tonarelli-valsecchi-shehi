package model;

import java.io.Serializable;
import java.util.List;

public class SquareCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 625473943L;
    private int groupsNumber;
    private int squareDim;
    private GroupCommonGoal groupCommonGoal;

    public GroupCommonGoal getGroupCommonGoal() {
        return groupCommonGoal;
    }

    public void setGroupCommonGoal(GroupCommonGoal groupCommonGoal) {
        this.groupCommonGoal = groupCommonGoal;
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
    public List<EntryPatternGoal> rule() {
        return null;
    }
}
