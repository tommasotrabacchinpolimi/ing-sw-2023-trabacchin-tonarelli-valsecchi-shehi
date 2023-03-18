import com.google.gson.Gson;
import model.EntryPatternGoal;
import model.PersonalGoal;
import model.TileType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    @Deprecated
    // analogo al metodo personalGoalGSON ma nel caso in cui tutti i PersonalGoal sono specificati in un unico file JSON
    private void workingWithGSON( JSONCreationTest jCT ){
        PrintWriter pw = jCT.getPrintWriter(0);

        Gson gson = new Gson();

        pw.write("{\"goalPattern\":[\n");

        for(int i = 0; i < PATTERN_NUMBER; ++i) {
            pw.write("\t" + gson.toJson(getRandomEntry()) );

            if(i < PATTERN_NUMBER - 1)
                pw.write(",\n");
        }

        pw.write("]\n}");

        pw.flush();
        pw.close();
    }

    // metodo che crea una EntryPatternGoal randomica
    private EntryPatternGoal getRandomEntry() {
        return new EntryPatternGoal(
                RANDOM.nextInt(6),
                RANDOM.nextInt(7),
                TileType.values()[RANDOM.nextInt(TileType.values().length)]);
    }

    @Deprecated
    // metodo analogo a workingWithGSON, ma converte gli oggetti EntryPatternGoal in array di elementi
    private void workingWithJsonSimple( JSONCreationTest jCT ){

        // creating JSONObject
        JSONObject jo = new JSONObject();

        PrintWriter pw = jCT.getPrintWriter(2);

        for(int j = 0; j < PATTERN_NUMBER; j++){
            // the pattern is store as JSONArray
            JSONArray ja = new JSONArray();

            for(int i = 0; i < TILE_NUMBER; i++) {
                ja.add(jCT.randomPersonalGoalPatternEntry());
            }
            // putting phoneNumbers to JSONObject
            jo.put("patternEntry" + (j + 1), ja );
        }

        pw.write(jo.toJSONString()
                /*.replaceAll("}],","}],\n\n")
                .replaceAll("},", "},\n")*/);
        pw.flush();
        pw.close();
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

    @Deprecated
    // genera un EntryPatternGoal randomico (serve per il metodo workingWithJsonSimple, non usato)
    private Map randomPersonalGoalPatternEntry(){
        return createPersonalGoalPatternEntry(
                RANDOM.nextInt(6),
                RANDOM.nextInt(7),
                TileType.values()[RANDOM.nextInt(TileType.values().length)]
        );
    }

    @Deprecated
    // creation of LinkedHashMap for EntryPattenGoal attributes
    private Map createPersonalGoalPatternEntry(int column, int row, TileType tileType){
        Map m = new LinkedHashMap(3);

        m.put("column", column);
        m.put("row", row);
        m.put("TileType", tileType.toString());

        return m;
    }
}
