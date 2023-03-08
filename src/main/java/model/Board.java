package model;

import java.util.Set;

import static model.BoardSquareType.THREE_DOTS;
import static model.BoardSquareType.FOUR_DOTS;
import static model.BoardSquareType.NO_DOTS;
public class Board {


    private static final int DIM = 9;
    final private BoardSquare livingRoomBoard;
    private static final BoardSquareType[][] init_matrix = {
            {null,null,null,THREE_DOTS,FOUR_DOTS,null,null,null,null},
            {null,null,null,NO_DOTS,NO_DOTS,FOUR_DOTS,null,null,null},
            {null,null,THREE_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,THREE_DOTS,null,null},
            {null,FOUR_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,THREE_DOTS},
            {FOUR_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,FOUR_DOTS},
            {THREE_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,FOUR_DOTS,null},
            {null,null,THREE_DOTS,NO_DOTS,NO_DOTS,NO_DOTS,THREE_DOTS,null,null},
            {null,null,null,FOUR_DOTS,NO_DOTS,NO_DOTS,null,null,null},
            {null,null,null,null,FOUR_DOTS,THREE_DOTS,null,null,null}};
    public Board(Set<Tile> bag){

        BoardSquare[][] temp = new BoardSquare[DIM][DIM];
        for(int i = 0;i<temp.length;i++){
            for(int j = 0;j<temp[0].length;j++){
                if(init_matrix[i][j]!=null){
                    temp[i][j] = new BoardSquare(init_matrix[i][j]);
                }

            }
        }

        for(int i = 0;i< temp.length;i++){
            for(int j = 0;j< temp.length;j++){
                if(temp[i][j]!=null){
                    if(i-1>=0&&temp[i][j]!=null){
                        temp[i][j].setTop(temp[i-1][j]);
                    }
                    if(j-1>=0&&temp[i][j]!=null){
                        temp[i][j].setLeft(temp[i][j-1]);
                    }
                    if(i+1<DIM&&temp[i][j]!=null){
                        temp[i][j].setBottom(temp[i+1][j]);
                    }
                    if(j+1<DIM&&temp[i][j]!=null){
                        temp[i][j].setRight(temp[i][j+1]);
                    }
                }
            }
        }
        this.livingRoomBoard = temp[0][3];
    }


    public BoardSquare getLivingRoomBoard() {
        return livingRoomBoard;
    }


}
