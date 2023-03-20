import model.*;

import java.util.ArrayList;
import java.util.List;

import static model.BoardSquareType.*;
import static model.BoardSquareType.THREE_DOTS;

public class CommonGoalClassTest {
    TileType[][] matrix;
    CommonGoal commonGoal;

    public static void main(String[] args){
        List<EntryPatternGoal> list = new ArrayList<EntryPatternGoal>();
        CommonGoalClassTest commonGoalClassTest = new CommonGoalClassTest();
        commonGoalClassTest.createStairDesign();
        commonGoalClassTest.createStairCommonGoal();
        list = commonGoalClassTest.getCommonGoal().rule(commonGoalClassTest.getMatrix());
        if (list != null)
            System.out.println("Stair Common Goal verified!");
        else System.out.println("Stair Common Goal not verified!");
    }

    public TileType[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(TileType[][] matrix) {
        this.matrix = matrix;
    }

    public CommonGoal getCommonGoal() {
        return commonGoal;
    }

    public void setCommonGoal(CommonGoal commonGoal) {
        this.commonGoal = commonGoal;
    }

    private void createStairDesign(){
        /*matrix = new TileType[][]{
                {null,  null, null, null, null},
                {TileType.CAT, null, null, null, null},
                {TileType.CAT, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

         /*matrix = new TileType[][]{
                {null,  null, null, null, null},
                {null, null, null, null, TileType.CAT},
                {null, null, null, TileType.CAT, TileType.CAT},
                {null, null, TileType.CAT, TileType.CAT, TileType.CAT},
                {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

        /*matrix = new TileType[][]{
                {null,  null, null, null, TileType.CAT},
                {null, null, null, TileType.CAT, TileType.CAT},
                {null, null, TileType.CAT, TileType.CAT, TileType.CAT},
                {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

        matrix = new TileType[][]{
                {TileType.CAT,  null, null, null, null},
                {TileType.CAT, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };

       /* matrix = new TileType[][]{
                {null,  null, null, null, null},
                {null, TileType.CAT, null, null, null},
                {null, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

       /*matrix = new TileType[][]{
                {TileType.CAT,  null, null, null, null},
                {TileType.CAT, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

    }

    private void createStairCommonGoal(){
        this.commonGoal = new StairCommonGoal();
    }

}
