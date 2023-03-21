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
               /* //System.out.println("Verifying from (" + i + ","+ j + ")");
                result = this.verifyNew(i, j, bookShelf);
                if (result != null) {
                    //System.out.println("RESULT NOT NULL");
                    return result;
                }*/
                result = rightStair(i, j, bookShelf);
                if (result != null) return result;
            }
            for (int j = numColumns-1; j >= numColumns-1-diff; j--){
                /*//System.out.println("Verifying from (" + i + ","+ j + ")");
                result = this.verifyNew(i, j, bookShelf);
                if (result != null) {
                    //System.out.println("RESULT NOT NULL");
                    return result;
                }
            }*/
                 result = leftStair(i, j, bookShelf);
                 if (result != null) return result;
            }
        }

        return null;
    }


    private List<EntryPatternGoal> rightStair(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        EntryPatternGoal element = new EntryPatternGoal();
        int numRows = matrix.length, numColumns = matrix[0].length, counter = startIndexRow, i =0;

        for ( i = 0; i < startIndexColumn; i++ ){   //controllo che le colonne precedenti siano vuote
            if(matrix[numRows-1][i]==null) return null;
        }
        // row counter
        // column i

        for ( i = startIndexColumn; i < numColumns ; i++ ){
            if (controlColumn(i, counter, matrix)==null) return null;
            result.addAll(controlColumn(i, counter, matrix));
            counter--;
        }

        if ( i == numColumns ) return result;
        return null;
    }


    private List<EntryPatternGoal> leftStair(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        EntryPatternGoal element = new EntryPatternGoal();
        int numRows = matrix.length, numColumns = matrix[0].length, counter = startIndexRow, i =0;

        for ( i = numColumns-1; i > startIndexColumn; i-- ){   //controllo che le colonne precedenti siano vuote
            if(matrix[numRows-1][i]==null) return null;
        }

        for ( i = startIndexColumn; i >= 0 ; i-- ){
            if ( controlColumn(i, counter, matrix) == null ) return null;
            result.addAll(controlColumn(i, counter, matrix));
            counter--;
        }

        if ( i == -1 ) return result;
        return null;
    }

    private List<EntryPatternGoal> controlColumn(int indexColumn,int indexMaxRow, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        EntryPatternGoal element = new EntryPatternGoal();

        if (matrix[indexMaxRow][indexColumn]==null) return null;
        if (indexMaxRow>0){
            if (matrix[indexMaxRow-1][indexColumn]!=null) return null;
        }
        for (int j=matrix.length-1; j >= indexMaxRow; j--){
            element = new EntryPatternGoal(indexColumn, j, matrix[j][indexColumn]);
            result.add(element);
        }
        return result;
    }

    @Deprecated
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
