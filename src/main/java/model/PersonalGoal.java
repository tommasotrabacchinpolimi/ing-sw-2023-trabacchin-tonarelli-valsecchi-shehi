package model;

import java.io.Serializable;

public class PersonalGoal implements Serializable {
    private static final long serialVersionUID = 52353836745724632L;
    private final TileType[][] referenceMatrix;

    //costruttore
    public PersonalGoal( TileType[][] matrix ){
        referenceMatrix = matrix;
    }

    public TileType[][] getReferenceMatrix() {
        return referenceMatrix;
    }

}
