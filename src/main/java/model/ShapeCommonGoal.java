package model;

import java.io.Serializable;
import java.util.List;

public class ShapeCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 746524795L;
    private int tileNumber;
    private List<Integer> incrementRuleShape;

    public int getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public List<Integer> getIncrementRuleShape() {
        return incrementRuleShape;
    }

    public void setIncrementRuleShape(List<Integer> incrementRuleShape) {
        this.incrementRuleShape = incrementRuleShape;
    }

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        return null;
    }
}
