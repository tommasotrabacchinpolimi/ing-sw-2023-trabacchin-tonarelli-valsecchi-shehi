package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.TileType;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class JSONCreationTest {
    private final static int PATTERN_NUMBER = 10; //number of total pattern
    private final static Random RANDOM = new Random();
    private final String inputFileName = "./src/JSONTestFile/PersonalGoalPattern" + RANDOM.nextInt(1,11) + ".json";

    public static void main(String[] args){
        JSONCreationTest jCT = new JSONCreationTest();

        PersonalGoal example = new PersonalGoal( jCT.getInputFileName() );

        //jCT.createPersonalGoalGSON();

        System.out.println("From File:" + jCT.inputFileName.replace("./src/JSONTestFile/", " "));
        System.out.println(example.toString());
    }

    // metodo che converte l'entità PersonalGoal (lista di EntryPatternGoal) in formato Json,
    // nel caso in cui ci sia una corrispondenza biunivoca tra file Json e Personal Goal
    private void createPersonalGoalGSON(){
        Gson gson = new Gson();

        for(int i = 0; i < PATTERN_NUMBER; i++) {
            PrintWriter pw = this.getPrintWriter( i + 1 );

            PersonalGoal personalGoal = new PersonalGoal();
            createGoalPattern(personalGoal.getGoalPattern());

            assert pw != null;
            pw.write(gson.toJson(personalGoal));

            pw.flush();
            pw.close();
        }
    }

    // metodo che ottiene il path dove salvare il file JSON e restituisce un oggetto PrintWriter per scrivere
    // nel file JSON (nb: il file JSON non è ancora salvato)
    private PrintWriter getPrintWriter(int index){
        try {
            return new PrintWriter("./src/JSONTestFile/PersonalGoalPattern" + index + ".json");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // metodo che crea le EntryPatternGoal per il file Json
    private void createGoalPattern(List<EntryPatternGoal> goalPattern) {
        EntryPatternGoal entryPatternGoal = getRandomEntry();
        goalPattern.add(entryPatternGoal);

        for(int i = 1; i < PersonalGoal.DEF_NUM_TILE_PATTERN; i++) {
            entryPatternGoal = getRandomEntry();

            if(!hasSamePosition(goalPattern, entryPatternGoal)) {
                goalPattern.add(entryPatternGoal);
            } else {
                --i;
            }
        }
    }

    // metodo che verifica se ci sono due EntryPatternGoal con la stessa posizione,
    // nel caso in cui ogni PersonalGoal sia in un unico file Json (corrispondenza biunivoca tra file Json e Personal Goal)
    public boolean hasSamePosition(List<EntryPatternGoal> goalPattern, EntryPatternGoal entryPatternGoal){
        for( EntryPatternGoal epg : goalPattern ) {
            if( epg.getColumn() == entryPatternGoal.getColumn() &&
                    epg.getRow() == entryPatternGoal.getRow()) {
                return true;
            }
        }

        return false;
    }

    // metodo che crea una EntryPatternGoal randomica
    private EntryPatternGoal getRandomEntry() {
        return new EntryPatternGoal(
                RANDOM.nextInt(6),
                RANDOM.nextInt(7),
                TileType.values()[RANDOM.nextInt(TileType.values().length)]);
    }

    public String getInputFileName() {
        return inputFileName;
    }
}
