package model;

import java.io.Serializable;
import java.util.List;

public abstract class CommonGoal implements Serializable {
    private static final long serialVersionUID = 285236373L;
    //attributo per memorizzare il punteggio assegnato dal goal
    private int availableScore;
    //descrizione della common goal
    private String description;

    public int getAvailableScore() {
        return availableScore;
    }

    public void setAvailableScore( int availableScore ) {
        this.availableScore = availableScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public abstract List<EntryPatternGoal> rule(TileType[][] bookShelf);

}
