import com.google.gson.Gson;
import model.EntryPatternGoal;
import model.PersonalGoal;
import model.TileType;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class JSONCreationTest {
    private final static int TILE_NUMBER = 6; //number of TileType per pattern
    private final static int PATTERN_NUMBER = 10; //number of total pattern
    private final static Random RANDOM = new Random();

    public static void main(String[] args){
        JSONCreationTest jCT = new JSONCreationTest();
        jCT.personalGoalGSON( jCT );
    }

    // metodo che converte l'entità PersonalGoal (lista di EntryPatternGoal) in formato Json,
    // nel caso in cui ci sia una corrispondenza biunivoca tra file Json e Personal Goal
    private void personalGoalGSON( JSONCreationTest jCT ){
        Gson gson = new Gson();

        for(int i = 0; i < PATTERN_NUMBER; i++) {
            PrintWriter pw = jCT.getPrintWriter( i + 1 );

            PersonalGoal personalGoal = new PersonalGoal();

            consistentGoalPattern(personalGoal.getGoalPattern());

            assert pw != null;
            pw.write(gson.toJson(personalGoal));

            pw.flush();
            pw.close();
        }
    }

    // metodo che controlla che non ci siano due EntryPatternGoal con la stessa posizione nel file Json
    private void consistentGoalPattern(List<EntryPatternGoal> goalPattern) {
        EntryPatternGoal entryPatternGoal = getRandomEntry();
        goalPattern.add(entryPatternGoal);

        for(int i = 1; i < TILE_NUMBER; i++) {
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
}
