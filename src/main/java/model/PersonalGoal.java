package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.io.FileReader;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class PersonalGoal implements Serializable {
    private static final long serialVersionUID = 52353836745724632L;
    private List<EntryPatternGoal> goalPattern;

    public PersonalGoal(){
        createGoalPattern();
    }

    public PersonalGoal(List<EntryPatternGoal> goalPattern) {
        this.goalPattern = goalPattern;
    }

    public List<EntryPatternGoal> getGoalPattern() {
        return goalPattern;
    }

    public void setGoalPattern(List<EntryPatternGoal> goalPattern) {
        this.goalPattern = goalPattern;
    }

    private void createGoalPattern(){
        JSONObject jo = getJSONFile();

        Map map;

        JSONArray entries = (JSONArray) jo.get( "pattern" );

        for(Iterator itr = entries.iterator();
            itr.hasNext(); ){

            for(Iterator itrMap = ((Map) itr.next()).entrySet().iterator();
                itrMap.hasNext(); ){

                Map.Entry pair = (Map.Entry) itrMap.next();

                goalPattern.add( new EntryPatternGoal(
                        (int) pair.getValue(),
                        (int) pair.getValue(),
                        (String) pair.getValue()
                ) );

            }
        }
    }

    private JSONObject getJSONFile() {
        try {
            return (JSONObject) new JSONParser().parse(new FileReader("JSONExample.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.print( "Not handled Exception" );
            return null;
        }
    }
}
