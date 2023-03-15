import com.google.gson.Gson;
import model.EntryPatternGoal;
import model.TileType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class JSONCreationTest {
    private final static int TILE_NUMBER = 6; //number of TileType per pattern
    private final static int PATTERN_NUMBER = 10; //number of total pattern
    private final static Random RANDOM = new Random();

    public static void main(String[] args){
        JSONCreationTest jCT = new JSONCreationTest();
        jCT.workingWithGSON( jCT );
    }

    private void workingWithGSON( JSONCreationTest jCT ){
        PrintWriter pw = jCT.getPrintWriter();

        Gson gson = new Gson();

        for(int i = 0; i < PATTERN_NUMBER; ++i) {
            EntryPatternGoal entryPatternGoal = new EntryPatternGoal(
                    RANDOM.nextInt(6),
                    RANDOM.nextInt(7),
                    TileType.values()[RANDOM.nextInt(TileType.values().length)]);
            pw.write(gson.toJson(entryPatternGoal) + "\n");
        }

        pw.flush();
        pw.close();
    }

    private void workingWithJsonSimple( JSONCreationTest jCT ){

        // creating JSONObject
        JSONObject jo = new JSONObject();

        PrintWriter pw = jCT.getPrintWriter();

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

    private PrintWriter getPrintWriter(){
        try {
            return new PrintWriter("JSONPersonalGoalPatternExample.json");
        } catch (FileNotFoundException e) {
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
