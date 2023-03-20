package model;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class PersonalGoal implements Serializable {
    @Serial
    private static final long serialVersionUID = 52353836745724632L;
    public static final int DEF_NUM_TILE_PATTERN = 6; //Hash Table Capacity & number of Tile in the pattern Goal store
    private List<EntryPatternGoal> goalPattern;
    private Map<Integer, Integer> scoreMap;

    // Default Constructor set the goalPattern and scoreMap capacity to 6 (default value)
    // Also fill in the scoreMap with default value
    public PersonalGoal(){
        goalPattern = new ArrayList<>(DEF_NUM_TILE_PATTERN);
        scoreMap = new Hashtable<>(DEF_NUM_TILE_PATTERN);
        defaultSetScoreMap();
    }

    //Constructor set the goalPattern and scoreMap capacity to numTilePattern value
    public PersonalGoal( int numTilePattern ){
        goalPattern = new ArrayList<>(numTilePattern);
        scoreMap = new Hashtable<>(numTilePattern);
    }

    public PersonalGoal(String fileName){
        JSONObject jo;

        // getting and parsing file "*.json"
        try {
            jo = (JSONObject) new JSONParser().parse(new FileReader(fileName));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        this.goalPattern = new ArrayList<>();
        this.scoreMap = new Hashtable<>();

        fillFromJSONGoalPattern(((JSONArray) jo.get("goalPattern")).iterator());

        fillFromJSONScoreMap((Map) jo.get("scoreMap"));
    }

    public List<EntryPatternGoal> getGoalPattern() {
        return goalPattern;
    }

    public void setGoalPattern(List<EntryPatternGoal> goalPattern) {
        this.goalPattern = goalPattern;
    }

    public Map<Integer, Integer> getScoreMap() {
        return scoreMap;
    }

    public void setScoreMap(Map<Integer, Integer> scoreMap) {
        this.scoreMap = scoreMap;
    }

    public void defaultSetScoreMap(){
        scoreMap.put(1, 1);
        scoreMap.put(2, 2);
        scoreMap.put(3, 4);
        scoreMap.put(4, 6);
        scoreMap.put(5, 9);
        scoreMap.put(6, 12);
    }

    private void fillFromJSONGoalPattern(Iterator entryPatternIterator){
        Map entryPatternAttributes;

        while(entryPatternIterator.hasNext()){
            entryPatternAttributes = (Map) entryPatternIterator.next();

            this.goalPattern.add(
                    new EntryPatternGoal(
                            getColumnConfig(entryPatternAttributes),
                            getRowConfig(entryPatternAttributes),
                            getTileTypeConfig(entryPatternAttributes)
                    )
            );
        }
    }

    private String getAttributeConfig(Map map, String attributeName){
        return map.get(attributeName).toString();
    }

    private int getColumnConfig(Map map){
        return Integer.parseInt(getAttributeConfig(map, "column"));
    }

    private int getRowConfig(Map map){
        return Integer.parseInt(getAttributeConfig(map, "row"));
    }

    private String getTileTypeConfig(Map map){
        return getAttributeConfig(map, "tileType");
    }

    private void fillFromJSONScoreMap(Map scoreMapData){
        Iterator<Map.Entry> entryScoreMapAttributes = scoreMapData.entrySet().iterator();

        while(entryScoreMapAttributes.hasNext()){
            Map.Entry pair = entryScoreMapAttributes.next();

            scoreMap.put(
                    Integer.parseInt(pair.getKey().toString()),
                    Integer.parseInt(pair.getValue().toString())
            );
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Pattern:\n");
        for( EntryPatternGoal epg : goalPattern ){
            res.append("\t{ Column: ").append(epg.getColumn()).append("\n")
                    .append("\t  Row: ").append(epg.getRow()).append("\n")
                    .append("\t  TileType: ").append(epg.getTileType().toString()).append("}\n\n");
        }

        res.append("scoreMap:\n")
                .append(scoreMap.toString());

        return res.toString();
    }
}
