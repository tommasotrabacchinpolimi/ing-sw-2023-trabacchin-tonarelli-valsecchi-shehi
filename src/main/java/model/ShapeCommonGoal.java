package model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

public class ShapeCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 746524795L;
    private int tileNumber;
    /**
     * the a list of array elements needed to this class to implement the Shape-CommonGoal
     *
     * @see ShapeCommonGoal#getIncrementRuleShape() getIncrementRuleShape()
     *
     * @apiNote The key of this class is this parameter that contains offsets needed
     * to finds if there is a common-goal. This class makes the check of 3 candidate
     * common-goals (Common-goal 2, 3, 10).
     */
    private List<Integer[]> incrementRuleShape;

    /**
     *
     * @param tileNumber it is the number of the array in the list of arrays.
     * @param ruleShape It is the list of arrays that permit to implement the function rule.
     */

    public ShapeCommonGoal(int tileNumber, List<Integer[]> ruleShape) {
        this.tileNumber = tileNumber;
        this.incrementRuleShape = ruleShape;
    }


    /**
     *
     * @return the number of Tiles,taken from input.
     */
    /*
    public int getTileNumber() {
        return tileNumber;
    }
    */
     */
/*

    /**
     *
     * @param tileNumber Needed to set the corrent value of [{@link ShapeCommonGoal#tileNumber tileNumber}]
     */

    /*
    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }
       /*
        */

    /**
     *
     * @return the list of arrays that contains the offsets that permit to implement
     * for more detailed explanation
     *
     * For more click here: [{@link ShapeCommonGoal#incrementRuleShape incrementRuleShape}]
     *
     */

    public List<Integer[]> getIncrementRuleShape() {
        return incrementRuleShape;
    }

    /**
     *
     * @param incrementRuleShape needed to set the value of [{@link ShapeCommonGoal#incrementRuleShape incrementRuleshape}]
     */
    public void setIncrementRuleShape(List<Integer[]> incrementRuleShape) {
        this.incrementRuleShape = incrementRuleShape;
    }

    /**
     * The method returns null if the ShapeCommonGoal is not satisfied for the bookShelf passed as argument.
     * @param bookShelf the BookShelf to check for the ShapeCommonGoal
     * @apiNote For the implementation of this method, this parameter is considered as a matrix
     * @return null if the ShapeCommonGoal is satisfied, otherwise the list of the EntryPatternGoals representing
     * the tiles in the BookShelf that satisfy the ShapeCommonGoal
     *
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        boolean check = true;
        TileType type;
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>(incrementRuleShape.size()+1);

        for(int i = 0; i < bookShelf.length; i++) {
            for(int j = 0; j < bookShelf[0].length; j++) {

                if(!check)
                    result.clear();

                result.add( new EntryPatternGoal(j, i, bookShelf[j][i]) );

                check = true;

                if (!(verifyInField(bookShelf[0].length, bookShelf.length, j, i))) {
                    break;
                } else {
                    type = bookShelf[i][j];
                    for(Integer[] temp : incrementRuleShape) {
                        int rowProcessed = i + temp[0];
                        int columnProcessed = j + temp[1];

                        if(!(type.equals(bookShelf[rowProcessed][columnProcessed]))) {
                            check = false;
                            break;
                        }
                        result.add(new EntryPatternGoal(columnProcessed, rowProcessed, bookShelf[rowProcessed][columnProcessed]));
                    }

                    if (check && verifySurrounding(result, bookShelf)) {
                        return result;
                    }
                }
            }
        }

        return null;
    }


    /**
     *
     * @param maxColumnDim It is the length of the bookshelf, tells where is the vertical limit to check
     *                     for tails.
     * @param maxRowDim    It is the width of the bookshelf, tells where is the horizontal limit to check
     *                     for tails.
     * @param indexColumn  It is the index of column of the current element being verified
     * @param indexRow It is the index of row of the current element being verified
     * @return If an element with indexes (i,j), added to the correspective array of the list incrementRuleShape
     * @see ShapeCommonGoal#incrementRuleShape
     *
     */
    private boolean verifyInField(int maxColumnDim, int maxRowDim, int indexColumn, int indexRow) {
        for(Integer[] temp : incrementRuleShape) {
            if(!((indexColumn + temp[1] < maxColumnDim) &&
                    (indexRow + temp[0] < maxRowDim)))
                return false;
        }
        return true;
    }

    /**
     *
     * @param candidate Is a list of tails inside the bookshelf that is candidate to be one the 3 shapes
     *                  that permit to score CommonGoal
     * @param bookShelf It is the same bookShelf being passed as parameter to the rule method
     * @return If around the exact shape needed to be done there is another tail of the same type.
     * @apiNote It is very important to verify that because for example a diagonal of 6 elements does not
     *          score commongoal.
     */

    private boolean verifySurrounding(List<EntryPatternGoal> candidate, TileType[][] bookShelf){

        for(EntryPatternGoal e : candidate) {
            int row = e.getRow() - 1;
            int column = e.getColumn() - 1;
            for(int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if(insideBookshelfBound(row + i,column + j, bookShelf.length, bookShelf[0].length) &&
                            notInShape(row + i, column + j, candidate)){
                            if(e.getTileType().equals(bookShelf[row + i][column + j])){
                                return false;
                            }
                    }
                }
            }
        }
        return  true;
    }


    /**
     *
     * @param row
     * @param column
     * @param candidate
     * @return
     */
    private boolean notInShape(int row, int column, List<EntryPatternGoal> candidate) {
        for(EntryPatternGoal e : candidate) {
            if((e.getArrayIndexes()[0] == row) &&
                    (e.getArrayIndexes()[1] == column)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param row it is the row index of the element to check
     * @param column it is the column index of the element to check
     * @param maxHeight It is the maximum column index being allowed (Bookshelf Height)
     * @param maxWidth It is the maximum row index being allowed (Bookshelf Width)
     * @return If an element with index row, column is or not part of the bookshelf
     */

    private boolean insideBookshelfBound(int row, int column, int maxHeight, int maxWidth){
        return ((row > 0 &&  row < maxHeight) && (column > 0  && column < maxWidth));
    }

}


    // per renderlo estendibile, a partire dall'alto, cerco tutte le sequenze di carte che hanno lunghezza maggiore di 5
    // Poi uso un altra funzione d'appoggio per calolare e ritornare, se e esiste la sequenza di esattamente 5 elementi uguali
    //controllo gli elementi della sottomatrice lunghezza-4.




  /*
    public List<EntryPatternGoal> VerifyDiagonal(TileType[][] matrice) {
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();
        int conta=1;
        //firstly let's check north-west to south-east diagonal
        for(int i = 0; i < matrice.length-4; i++) {
            for(int j=0; j< matrice[0].length-4; j++) {
                for(int k=1; k<6; k++) {

                    if(matrice[i][j].equals(matrice[i+k][j+k]) ||
                            ((i + 6) < matrice.length &&
                                    (j + 6) < matrice[0].length &&
                                    !(matrice[i+6][j+6].equals(matrice[i][j])))) {
                        conta++;
                        result.add(new EntryPatternGoal(j,i, matrice[i][j]));
                    } else {
                        conta=0;
                        result.clear();
                        break;
                    }

                    if(conta==5) {
                        return result;
                    }
                }
            }
        }
        ///check of south-east to north-west diagonal
        for(int i= matrice.length; i>3; i--)
        {
            for (int j=0; j<matrice[0].length-3; j++)
            {
                for(int k=1; k<7; k++)
                {
                    if(matrice[i][j].equals(matrice[i-k][j+k]))
                    {
                        conta++;
                        result.add(new EntryPatternGoal(j,i, matrice[i][j]));
                    }
                    else {
                        conta=1;
                        result.clear();
                        break;
                    }
                }
                if(conta==5)
                {
                    return result;
                }
            }
        }
    }




    public List<EntryPatternGoal> VerifyEdges(TileType[][] matrice)
    {
        List<EntryPatternGoal> risultato = new ArrayList<EntryPatternGoal>();
        int vero = 1;
        for (int i=0; i< matrice.length; i++)
        {
            if(matrice[0][i]==null || matrice[matrice.length][i]==null)
            {
                vero = 0;
            }
        }
        if(vero == 1)
        {
            TileType tipo;
            int position1 = matrice.length;
            int position2 = matrice[0].length;
            tipo = matrice[0][0];
            if(matrice[position1][0].equals(tipo) && matrice[0][position2].equals(tipo) && matrice[position1][position2tion2].equals(tipo) )
             {
                risultato.add(new EntryPatternGoal(0,0, matrice[0][0]));
                risultato.add(new EntryPatternGoal(position1, 0, matrice[0][position2]));
                risultato.add(new EntryPatternGoal(0, position1, matrice[position1][0]));
                risultato.add(new EntryPatternGoal(position2, position1, matrice[position1][position2]));
            }
        }
        if(risultato.size()==4)
        {
            return risultato;
        }
   }



   //In Cross dovrei controllare che nella X le 2 coppie di elementi che sono di lato, non abbiano spazio in mezzo
    //scenario impossibile per come inserisco le carte Lo faccio dopo
    public List<EntryPatternGoal> Cross (TileType[][] matrice)
    {
        TileType tipo;
        List<EntryPatternGoal> risultato = new ArrayList<EntryPatternGoal>(CommonGoal);
        for(int i=1; i< matrice.length-1; i++)
        {
            for(int j=1; j< matrice[0].length-1; j++)
            {
                tipo = matrice[i][j];
                if(matrice[i-1][j-1].equals(tipo) && matrice[i+1][j-1].equals(tipo) && matrice[i+1][j+1].equals(tipo) && matrice[i-1][j+1].equals(tipo))
                {
                    risultato.add(new EntryPatternGoal(j,i, matrice[i][j]));
                    risultato.add(new EntryPatternGoal(j-1,i-1, matrice[i-1][j-1]));
                    risultato.add(new EntryPatternGoal(j+1,i+1, matrice[i+1][j+1]));
                    risultato.add(new EntryPatternGoal(j+1, i-1,matrice[i-1][j+1]);
                }
            }
        }

    }





