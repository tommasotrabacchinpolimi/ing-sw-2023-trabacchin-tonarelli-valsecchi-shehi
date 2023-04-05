package it.polimi.ingsw;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalClassTest {
    TileType[][] matrix;
    CommonGoal commonGoal;

    public static void main(String[] args){
        List<EntryPatternGoal> list = new ArrayList<>();
        CommonGoalClassTest commonGoalClassTest = new CommonGoalClassTest();
        commonGoalClassTest.createLineDesign();
        commonGoalClassTest.createLineCommonGoal();
        list = commonGoalClassTest.getCommonGoal().rule(commonGoalClassTest.getMatrix());
        if (list != null) {
            System.out.println("Common Goal verified!");
            commonGoalClassTest.printResult(list);
        }
        else {
            System.out.println("Common Goal not verified!");
        }
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
        // verified
      /* matrix = new TileType[][]{
                {null,  null, null, null, null},
                {TileType.PLANT, null, null, null, null},
                {TileType.CAT, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                {TileType.CAT, TileType.CAT, TileType.PLANT, TileType.CAT, null},
                {TileType.CAT, TileType.FRAME, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/


        // verified
       /*matrix = new TileType[][]{
                {null,  null, null, null, TileType.CAT},
                {null, null, null, TileType.CAT, TileType.CAT},
                {null, null, TileType.CAT, TileType.CAT, TileType.CAT},
                {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

        //verified, 3
       /* matrix = new TileType[][]{
                {TileType.CAT,  null, null, null, null},
                {TileType.CAT, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

        //4
        /*matrix = new TileType[][]{
                {null,  null, null, null, null},
                {null, TileType.CAT, null, null, null},
                {null, TileType.CAT, null, TileType.CAT, TileType.CAT},
                {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

        // 5
       matrix = new TileType[][]{
                {null,  null, null, null, null},
                {null, TileType.CAT, null, null, null},
                {null, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, /*TileType.CAT*/ null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, /*TileType.CAT*/ null, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, /*TileType.CAT*/ null, TileType.CAT}
        };

       //6
       /*matrix = new TileType[][]{
                {null,  null, null, null, null},
                {TileType.CAT, TileType.CAT, null, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
        };*/

    }

    private void createLineDesign(){
        //number = 1
        matrix = new TileType[][]{
                {TileType.CAT,  TileType.CAT, TileType.CAT, TileType.CAT, TileType.PLANT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.TROPHY, TileType.CAT, TileType.BOOK},
                {TileType.TROPHY, TileType.PLANT, TileType.FRAME, TileType.CAT, TileType.GAME}, //NO
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.BOOK, TileType.CAT},
                {TileType.GAME, TileType.FRAME, TileType.PLANT, TileType.BOOK, TileType.CAT} //NO
        };

        // number = 2
      /*matrix = new TileType[][]{
                {null,TileType.CAT , null, TileType.CAT,TileType.CAT},
                {null, TileType.CAT, null, TileType.CAT ,  TileType.CAT},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.GAME},
                {TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                {TileType.GAME, TileType.GAME, TileType.PLANT, TileType.BOOK, TileType.GAME}
        };*/

        //number = 3
       /*matrix = new TileType[][]{
                {TileType.PLANT,  TileType.PLANT, TileType.GAME, null, null},
                {TileType.TROPHY, TileType.CAT, TileType.CAT, null, null},
                {TileType.BOOK, TileType.FRAME, TileType.TROPHY, null, null},
                {TileType.CAT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                {TileType.FRAME, TileType.CAT, TileType.FRAME, TileType.CAT, TileType.CAT},
                {TileType.GAME, TileType.GAME, TileType.BOOK, TileType.BOOK, TileType.CAT}
        }; */

    }

    private void createShapeDesign(){
        // edges design verified
        matrix = new TileType[][]{
                {TileType.PLANT,  TileType.PLANT, TileType.GAME, null, TileType.PLANT},
                {TileType.TROPHY, TileType.CAT, TileType.CAT, null, TileType.GAME},
                {TileType.BOOK, TileType.FRAME, TileType.TROPHY, null, TileType.FRAME},
                {TileType.CAT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                {TileType.FRAME, TileType.CAT, TileType.FRAME, TileType.CAT, TileType.CAT},
                {TileType.PLANT, TileType.GAME, TileType.BOOK, TileType.BOOK, TileType.PLANT}
        };

        // edges design not verified and diagonal design verified
        /*matrix = new TileType[][]{
                {TileType.PLANT,  TileType.PLANT, TileType.GAME, null, TileType.GAME},
                {TileType.PLANT, TileType.CAT, TileType.CAT, null, TileType.GAME},
                {TileType.BOOK, TileType.PLANT, TileType.TROPHY, null, TileType.FRAME},
                {TileType.CAT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                {TileType.FRAME, TileType.CAT, TileType.FRAME, TileType.PLANT, TileType.CAT},
                {TileType.PLANT, TileType.GAME, TileType.BOOK, TileType.BOOK, TileType.PLANT}
        }; */

        // edges design not verified and x design not verified
        /*matrix = new TileType[][]{
                {null,  TileType.PLANT, TileType.GAME, null, null},
                {TileType.TROPHY, TileType.CAT, TileType.CAT, null, TileType.GAME},
                {TileType.BOOK, TileType.FRAME, TileType.TROPHY, null, TileType.FRAME},
                {TileType.CAT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                {TileType.FRAME, TileType.CAT, TileType.FRAME, TileType.CAT, TileType.CAT},
                {TileType.PLANT, TileType.GAME, TileType.BOOK, TileType.BOOK, TileType.PLANT}
        }; */

        // x design verified and diagonal design not verified
        /*matrix = new TileType[][]{
                {null,  TileType.PLANT, TileType.GAME, null, null},
                {TileType.TROPHY, TileType.CAT, TileType.CAT, null, TileType.GAME},
                {TileType.BOOK, TileType.TROPHY, TileType.TROPHY, null, TileType.FRAME},
                {TileType.CAT, TileType.PLANT, TileType.TROPHY, TileType.CAT, TileType.GAME},
                {TileType.FRAME, TileType.CAT, TileType.FRAME, TileType.TROPHY, TileType.CAT},
                {TileType.CAT, TileType.GAME, TileType.CAT, TileType.BOOK, TileType.PLANT}
        }; */

    }

    private void createGroupDesign(){
        // 6 groups of 2 tiles each
        matrix = new TileType[][]{
                {TileType.PLANT,  TileType.PLANT, TileType.GAME, null, TileType.PLANT},
                {TileType.TROPHY, TileType.CAT, TileType.CAT, null, TileType.PLANT},
                {TileType.PLANT, TileType.FRAME, TileType.TROPHY, null, TileType.FRAME},
                {TileType.PLANT, TileType.BOOK, TileType.PLANT, TileType.CAT, TileType.GAME},
                {TileType.FRAME, TileType.CAT, TileType.PLANT, TileType.CAT, TileType.CAT},
                {TileType.PLANT, TileType.PLANT, TileType.BOOK, TileType.PLANT, TileType.PLANT}
        };

        // 4 groups of 4 tiles each; 2 squares group of dim=2; 8 tiles of one type
        /* matrix = new TileType[][]{
                {TileType.PLANT,  TileType.PLANT, TileType.GAME, null, TileType.PLANT},
                {TileType.PLANT, TileType.CAT, TileType.CAT, TileType.PLANT, TileType.PLANT},
                {TileType.PLANT, TileType.FRAME, TileType.BOOK, TileType.PLANT, TileType.FRAME},
                {TileType.BOOK, TileType.BOOK, TileType.FRAME, TileType.CAT, TileType.GAME},
                {TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.PLANT, TileType.PLANT},
                {TileType.PLANT, TileType.PLANT, TileType.BOOK, TileType.PLANT, TileType.PLANT}
        }; */

        // 8 tiles of one type not verified, 2 squares group of dim=2 not verified,
        // 6 groups of 2 tiles each not verified
        /* matrix = new TileType[][]{
                {null, null , null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.FRAME, TileType.PLANT},
                {TileType.PLANT, TileType.PLANT, TileType.BOOK, TileType.PLANT, TileType.PLANT}
        }; */
    }

    private void createSquareDesign(){
        // 4 groups of 4 tiles each; 2 squares group of dim=2; 8 tiles of one type
        matrix = new TileType[][]{
                {TileType.PLANT,  TileType.PLANT, TileType.GAME, null, TileType.PLANT},
                {TileType.PLANT, TileType.CAT, TileType.CAT, TileType.PLANT, TileType.PLANT},
                {TileType.PLANT, TileType.FRAME, TileType.BOOK, TileType.PLANT, TileType.FRAME},
                {TileType.BOOK, TileType.BOOK, TileType.FRAME, TileType.CAT, TileType.GAME},
                {TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.PLANT, TileType.PLANT},
                {TileType.PLANT, TileType.PLANT, TileType.BOOK, TileType.PLANT, TileType.PLANT}
        };

        // 8 tiles of one type not verified, 2 squares group of dim=2 not verified,
        // 6 groups of 2 tiles each not verified
        /* matrix = new TileType[][]{
                {null, null , null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.FRAME, TileType.PLANT},
                {TileType.PLANT, TileType.PLANT, TileType.BOOK, TileType.PLANT, TileType.PLANT}
        }; */
    }

    private void createStairCommonGoal(){
        int numberOfColumns = 5;
        this.commonGoal = new StairCommonGoal(numberOfColumns);
    }

    private void createGroupCommonGoal(){
        int  groupsNumber = 6;
        int adjacentTiles = 2;
        this.commonGoal = new GroupCommonGoal("Description", groupsNumber, adjacentTiles);
    }

    private void createShapeCommonGoal(){
        List<Integer[]> incrementRuleShape = new ArrayList<Integer[]>();
        this.commonGoal = new ShapeCommonGoal(incrementRuleShape);
    }

    private void createSquareCommonGoal(){
        int groupsNumber = 3;
        int squareDim = 2;
        this.commonGoal = new SquareCommonGoal("Description", groupsNumber, squareDim , true);
    }

    private void createLineCommonGoal(){
        int incRow = 1; // 1 to test rows or 0 to test columns
        int incCol = 1; // 1 to test columns or 0 to test rows
        int linesNumber = 4; //number of column or rows to test
        int numberOfTiles = 5;
        int[] differentTiles = {1};  //number of different tile types {1,2,3} or {5}/{6}
        this.commonGoal = new LineCommonGoal(incRow, incCol, linesNumber, numberOfTiles, differentTiles);
    }


    private void printResult(List<EntryPatternGoal> list){

        System.out.println(list.toString());


        TileType[][] matrix = new TileType[6][5];
        int[][] index = new int[6][5];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 5; j++){
                matrix[i][j] = null;
                index[i][j] = 0;
            }
        }

        for (EntryPatternGoal element : list) {
            matrix[element.getRow()][element.getColumn()] = element.getTileType();
            index[element.getRow()][element.getColumn()] = list.indexOf(element)+1;
        }

        System.out.print("\t");
        for (int i = 0; i < 5; i++){
            System.out.print(i+ "\t\t");
        }
        System.out.println();
        for (int i = 0; i < 6; i++){
            System.out.print(i+ "\t");
            for (int j = 0; j < 5; j++){
                if (matrix[i][j]==null) System.out.print("----\t");
                else {
                    System.out.print( index[i][j] +"."+ matrix[i][j]  + "\t");

                }
            }
            System.out.println();
        }
    }
}
