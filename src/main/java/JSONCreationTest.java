import com.google.gson.Gson;
import model.EntryPatternGoal;
import model.PersonalGoal;
import model.TileType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
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

    private void personalGoalGSON( JSONCreationTest jCT ){
        Gson gson = new Gson();

        for(int i = 0; i < PATTERN_NUMBER; i++) {
            PrintWriter pw = jCT.getPrintWriter(i);

            PersonalGoal personalGoal = new PersonalGoal();

            consistentGoalPattern(personalGoal.getGoalPattern());

            assert pw != null;
            pw.write(gson.toJson(personalGoal));

            pw.flush();
            pw.close();
        }
    }

    private void consistentGoalPattern(List<EntryPatternGoal> goalPattern) {
        EntryPatternGoal entryPatternGoal = getRandomEntry();
        goalPattern.add(entryPatternGoal);

        for(int i = 0; i < TILE_NUMBER; i++) {
            entryPatternGoal = getRandomEntry();

            if(!hasSamePosition(goalPattern, entryPatternGoal)) {
                goalPattern.add(entryPatternGoal);
            }else {
                --i;
            }
        }
    }

    public boolean hasSamePosition(List<EntryPatternGoal> goalPattern, EntryPatternGoal entryPatternGoal){
        for( EntryPatternGoal epg : goalPattern ) {
            if( epg.getColumn() == entryPatternGoal.getColumn() &&
                    epg.getRow() == entryPatternGoal.getRow()) {
                return true;
            }
        }

        return false;
    }

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

    private EntryPatternGoal getRandomEntry() {
        return new EntryPatternGoal(
                RANDOM.nextInt(6),
                RANDOM.nextInt(7),
                TileType.values()[RANDOM.nextInt(TileType.values().length)]);
    }

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
            jo.put("patternEntry" + j, ja );
        }

        pw.write(jo.toJSONString()
                /*.replaceAll("}],","}],\n\n")
                .replaceAll("},", "},\n")*/);
        pw.flush();
        pw.close();
    }

    private PrintWriter getPrintWriter(int index){
        try {
            return new PrintWriter("./src/JSONTestFile/PersonalGoalPattern" + index + ".json");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map randomPersonalGoalPatternEntry(){
        return createPersonalGoalPatternEntry(
                RANDOM.nextInt(6),
                RANDOM.nextInt(7),
                TileType.values()[RANDOM.nextInt(TileType.values().length)]
        );
    }

    // creation of LinkedHashMap for EntryPattenGoal attributes
    private Map createPersonalGoalPatternEntry(int column, int row, TileType tileType){
        Map m = new LinkedHashMap(3);

        m.put("column", column);
        m.put("row", row);
        m.put("TileType", tileType.toString());

        return m;
    }
}
