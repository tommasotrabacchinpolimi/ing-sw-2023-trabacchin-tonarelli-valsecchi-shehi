import model.*;

import java.util.ArrayList;
import java.util.List;

import static model.BoardSquareType.*;
import static model.BoardSquareType.THREE_DOTS;

public class CommonGoalClassTest {
    TileType[][] matrix;
    CommonGoal commonGoal;

    public static void main(String[] args){
        List<EntryPatternGoal> list = new ArrayList<>();
        CommonGoalClassTest commonGoalClassTest = new CommonGoalClassTest();
        commonGoalClassTest.createLineDesign();
        commonGoalClassTest.createLineCommonGoal();
        list = commonGoalClassTest.getCommonGoal().rule(commonGoalClassTest.getMatrix());
        if (list != null)
            System.out.println("Common Goal verified!");
        else System.out.println("Common Goal not verified!");
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

    private void createLineDesign(){
        /*matrix = new TileType[][]{
                {null,  null, null, null, null},
                {null, TileType.CAT, null, null, null},
                {null, TileType.CAT, TileType.TROPHY, null, null},
                {TileType.TROPHY, TileType.PLANT, TileType.FRAME, TileType.CAT, TileType.GAME},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.GAME, TileType.FRAME, TileType.PLANT, TileType.BOOK, TileType.CAT}
        };*/

        /*matrix = new TileType[][]{
                {null,  null, null, null, null},
                {null, TileType.CAT, null, null, null},
                {null, TileType.CAT, TileType.TROPHY, null, null},
                {TileType.TROPHY, TileType.PLANT, TileType.FRAME, TileType.CAT, TileType.GAME},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.GAME, TileType.GAME, TileType.PLANT, TileType.BOOK, TileType.CAT}
        };*/

        matrix = new TileType[][]{
                {TileType.PLANT,  TileType.PLANT, TileType.GAME, null, null},
                {TileType.TROPHY, TileType.CAT, TileType.CAT, null, null},
                {TileType.BOOK, TileType.FRAME, TileType.TROPHY, null, null},
                {TileType.CAT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                {TileType.FRAME, TileType.CAT, TileType.FRAME, TileType.CAT, TileType.CAT},
                {TileType.GAME, TileType.GAME, TileType.BOOK, TileType.BOOK, TileType.CAT}
        };

    }

    private void createStairCommonGoal(){
        this.commonGoal = new StairCommonGoal();
    }

    private void createGroupCommonGoal(){
        int  groupsNumber = 6;
        int adjacentTiles = 2;
        this.commonGoal = new GroupCommonGoal(groupsNumber, adjacentTiles);
    }

    private void createShapeCommonGoal(){
        int tileNumber = 2;
        List<Integer> incrementRuleShape = new ArrayList<Integer>();
        this.commonGoal = new ShapeCommonGoal(tileNumber, incrementRuleShape);
    }

    private void createSquareCommonGoal(){
        int groupsNumber = 3;
        int squareDim = 2;
        this.commonGoal = new SquareCommonGoal(groupsNumber, squareDim);
    }

    private void createLineCommonGoal(){
        int incRow = 0; // 1 to test rows or 0 to test columns
        int incCol = 1; // 1 to test columns or 0 to test rows
        int linesNumber = 3; //number of column or rows to test
        int[] differentTiles = {6};  //number of different tile types {1,2,3} or {5}/{6}
        this.commonGoal = new LineCommonGoal(incRow, incCol, linesNumber, differentTiles);
    }
}
