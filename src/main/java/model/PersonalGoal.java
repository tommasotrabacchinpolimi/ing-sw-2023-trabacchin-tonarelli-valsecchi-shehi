package model;

public class PersonalGoal {
    private int[][] referenceMatrix;

    //costruttore
    public PersonalGoal(int[][] matrix){
        referenceMatrix = matrix;
    }

    public int[][] getReferenceMatrix() {
        return referenceMatrix;
    }

}
