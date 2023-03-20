package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StairCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 142749503L;

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        int numRows = bookShelf.length, numColumns = bookShelf[0].length;
        int diff = Math.abs(numRows - numColumns);
        List<EntryPatternGoal> result = new ArrayList<>();

        //System.out.println("Starting rule method...");
        for (int i = numRows-1; i >= numRows-1-diff; i--){
            for (int j = 0; j <= diff; j++) {
                //System.out.println("Verifying from (" + i + ","+ j + ")");
                result = this.verifyNew(i, j, bookShelf);
                if (result != null) {
                    //System.out.println("RESULT NOT NULL");
                    return result;
                }
            }
            for (int j = numColumns-1; j >= numColumns-1-diff; j--){
                //System.out.println("Verifying from (" + i + ","+ j + ")");
                result = this.verifyNew(i, j, bookShelf);
                if (result != null) {
                    //System.out.println("RESULT NOT NULL");
                    return result;
                }
            }
        }

        return null;
    }


    private List<EntryPatternGoal> verifyNew(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        EntryPatternGoal element = new EntryPatternGoal();
        int numRows = matrix.length, numColumns = matrix[0].length;
        int flag = 0, add = 0;
        int index = Math.min(numRows, numColumns);
        int diff = Math.abs(numRows - numColumns);
        int counter = startIndexColumn;

        if (startIndexColumn <= diff ){
            add = 1;
            if (counter > 0) {
                for (int i = 0; i < counter; i++) {
                    if (matrix[numRows - 1][i] != null)
                        flag = -1;
                }
            }
        }

        if (numColumns - startIndexColumn <= diff) {
            add = -1;
            if (counter < numColumns -1) {
                for (int i = numColumns - 1; i > counter; i++) {
                    if (matrix[numRows - 1][i] != null)
                        flag = -1;
                }
            }
        }

        for (int i = startIndexRow; i >= Math.abs(startIndexRow-index+1) && flag != -1; i--) {
            //System.out.println("i="+i);
            //System.out.println("...checking matrix["+i+","+counter+"] tile");
            if (matrix[i][counter] != null) {
                if ( i > 0 ){ //new
                    if (matrix[i-1][counter] != null) {
                        //System.out.println("..flag = 1, not highest tile");
                        flag = -1;
                    }
                }

                for (int j = numRows-1; j <= i && j >= 0 && flag != -1; j--){
                    //System.out.println("...Adding element (j=" + j + ",counter=" + counter + ")");
                    element = new EntryPatternGoal(counter, j, matrix[j][counter]);
                    result.add(element);
                }

                counter += add;
            }
            else {
                //System.out.println("...flag=-1");
                flag = -1;
            }
        }

        //System.out.println("counter="+counter+" flag="+flag);
        if (flag != -1 && ( counter == numColumns-1 || counter == 0 || counter == -1 || counter == numColumns))
            return result;

        //System.out.println("...returning null");
        return null;
    }
}
