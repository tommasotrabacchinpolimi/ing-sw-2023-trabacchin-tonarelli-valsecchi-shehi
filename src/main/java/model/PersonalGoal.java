package model;

public class PersonalGoal {
    private final TileType[][] referenceMatrix;

    //costruttore
    public PersonalGoal( TileType[][] matrix ){
        referenceMatrix = matrix;
    }

    public TileType[][] getReferenceMatrix() {
        return referenceMatrix;
    }

}
