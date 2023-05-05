package it.polimi.ingsw.model;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.controller.JSONExclusionStrategy;
import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * <p>This class represent the personal goal card referring to the official
 * <a href="https://www.craniocreations.it/storage/media/product_downloads/48/538/MyShelfie_Ruleboo_ENG_lowres_new.pdf">
 *     RuleBook
 *     </a>.
 * </p>
 * <p>The default number of Tile inside a personal goal pattern is {@value DEF_NUM_TILE_PATTERN}. To set a personal goal
 * with a number of tiles above this limit use  {@link #PersonalGoal(int numberOfTileInPattern)} constructor.</p>
 * <p>The score map referred to each card have a standard configuration:
 * <ul>
 *     <li>1 Tile match = 1 Point</li>
 *     <li>2 Tiles match = 2 Points</li>
 *     <li>3 Tiles match = 4 Points</li>
 *     <li>4 Tiles match = 6 Points</li>
 *     <li>5 Tiles match = 9 Points</li>
 *     <li>6 Tiles match = 12 Points</li>
 * </ul>
 * To create a new type of score map associated with a personal goal
 * see {@link #PersonalGoal(String fileName) PersonalGoal(fileName)} constructor</p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 28/03/2023
 */
public class PersonalGoal implements Serializable {
    @ExcludedFromJSON
    @Serial
    private static final long serialVersionUID = 52353836745724632L;

    /**
     * The complete path to get the Personal Goal configuration from a json file
     */
    @ExcludedFromJSON
    private static final String PERSONAL_GOAL_CONFIG = "./src/main/resources/it.polimi.ingsw/personal.goal.configuration/";

    /**
     * Constant that defines standard number of tiles inside a personal goal
     */
    @ExcludedFromJSON
    public static final int DEF_NUM_TILE_PATTERN = 6;

    /**
     * The effective goal pattern represented in the card
     *
     * @see EntryPatternGoal
     */
    private final List<EntryPatternGoal> goalPattern;

    /**
     * Points associated with the number of tiles in the right position
     */
    private final Map<Integer, Integer> scoreMap;

    /**
     * Default constructor set the pattern of the personal goal and score map capacity to {@link #DEF_NUM_TILE_PATTERN default value}.
     * Pattern created is random and the score map is set to default configuration value.
     *
     * @see PersonalGoal
     */
    public PersonalGoal(){
        goalPattern = new ArrayList<>(DEF_NUM_TILE_PATTERN);
        scoreMap = new Hashtable<>(DEF_NUM_TILE_PATTERN);
        createRandomGoalPattern();
        setDefaultScoreMap();
    }

    /**
     * Constructor set the goalPattern and scoreMap capacity to numTilePattern value
     * Pattern created is random and the score map is set to default configuration value.
     *
     * @param numTilePattern the initial capacity for goalPattern and scoreMap
     * @see PersonalGoal
     */
    public PersonalGoal(int numTilePattern){
        goalPattern = new ArrayList<>(numTilePattern);
        scoreMap = new Hashtable<>(numTilePattern);
        setDefaultScoreMap();
    }

    /**
     * Use this constructor to create a copy of the PersonalGoal passed as parameter
     *
     * @param that Personal goal configuration that needs to be copied in the new istance
     */
    public PersonalGoal(PersonalGoal that) {
        this.goalPattern = that.goalPattern;
        this.scoreMap = that.scoreMap;
    }

    /**
     * <p>This constructor is used to set the pattern for the card and scoreMap to the configuration specified inside the file
     * with name passed as parameter.</p>
     * <p>JSON file needs to be formatted as follows:
     * <pre>{@code {"goalPattern": [{
     *      "row": int value,
     *      "column": int value,
     *      "tileType": String TileTypeName},
     *      ...
     * ],"scoreMap": {
     *      String tilesNumber: int value,
     *      ...
     * }}}</pre></p>
     *
     * @param fileName the name of the file ".json" that contains personal goal card configuration
     * @throws FileNotFoundException in case that the configuration file name passed as parameter is not
     *         found at the right configuration path
     * @apiNote No check will be done on the number of Tiles entities that creates the pattern inside the personal goal card
     *          and the maximum number of tiles that needs to be checked the card
     * @see PersonalGoal
     */
    public PersonalGoal(String fileName) throws FileNotFoundException{
        this((PersonalGoal)  new GsonBuilder()
                .setExclusionStrategies(new JSONExclusionStrategy())
                .create()
                .fromJson(
                        new JsonReader(
                                new FileReader( PERSONAL_GOAL_CONFIG + fileName + ".json")),
                        PersonalGoal.class));
    }

    /**
     * This method was used inside the {@link #PersonalGoal(String fileName)} constructor and read line ny line of the json file
     * configuration to set the fields.
     *
     * @deprecated <p>since version 2.3, replaced by {@link #PersonalGoal(String fileName)} constructor</p>
     *             <p>This method was deprecated due to performance, security and robustness of the code</p>
     *
     * @param fileName complete path to the configuration file
     * @apiNote If the number of Tiles entity that creates the pattern inside the personal goal card is not equals to the
     *          maximum number of tiles that needs to be checked the card is not accepted and random configuration is set for the
     *          personal goal pattern, while default configuration is set for score map.
     * @see PersonalGoal
     * @see #PersonalGoal(String fileName)
     */
    @Deprecated
    private void usingJSONParser(String fileName) {
        JSONObject jo;

        // getting and parsing file "*.json"
        try {
            jo = (JSONObject) new JSONParser().parse(new FileReader(fileName));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        fillPersonalGoalFromJSON(((JSONArray) jo.get("goalPattern")).iterator());

        fillScoreMapFromJSON((Map) jo.get("scoreMap"));

        if(!verifyInput()){
            goalPattern.clear();
            scoreMap.clear();
            createRandomGoalPattern();
            setDefaultScoreMap();
        }
    }

    /**
     * @return goal pattern configuration represented in the personal goal card
     */
    public List<EntryPatternGoal> getGoalPattern() {
        return goalPattern;
    }

    /**
     * @return score map configuration in the personal goal card
     */
    public Map<Integer, Integer> getScoreMap() {
        return scoreMap;
    }

    /**
     * Set score map to the default configuration:
     * <ul>
     *     <li>1 Tile match = 1 Point</li>
     *     <li>2 Tiles match = 2 Points</li>
     *     <li>3 Tiles match = 4 Points</li>
     *     <li>4 Tiles match = 6 Points</li>
     *     <li>5 Tiles match = 9 Points</li>
     *     <li>6 Tiles match = 12 Points</li>
     * </ul>
     *
     * @see PersonalGoal
     */
    private void setDefaultScoreMap(){
        scoreMap.put(1, 1);
        scoreMap.put(2, 2);
        scoreMap.put(3, 4);
        scoreMap.put(4, 6);
        scoreMap.put(5, 9);
        scoreMap.put(6, 12);
    }

    /**
     * This method is used to create personal goal card configuration from a JSON file that respect the correct formatting
     *
     * @deprecated <p>since version 2.3, replaced by {@link #PersonalGoal(String fileName)} constructor</p>
     *             <p>This method was deprecated because with the new {@linkplain #PersonalGoal(String fileName) constructor}
     *             it is no more necessary to call it</p>
     *
     * @param entryPatternIterator iterator used to browse the json file configuration for the entries of the personal goal card
     * @see PersonalGoal
     */
    @Deprecated
    private void fillPersonalGoalFromJSON(Iterator entryPatternIterator){
        Map entryPatternAttributes;

        while(entryPatternIterator.hasNext()){
            entryPatternAttributes = (Map) entryPatternIterator.next();

            this.goalPattern.add(
                    new EntryPatternGoal(
                            getRowConfig(entryPatternAttributes),
                            getColumnConfig(entryPatternAttributes),
                            getTileTypeConfig(entryPatternAttributes)
                    )
            );
        }
    }

    /**
     * This method is used to get the value (as a string) from a map representing a json file
     *
     * @deprecated <p>since version 2.3, replaced by {@link #PersonalGoal(String fileName)} constructor</p>
     *             <p>This method was deprecated because with the new {@linkplain #PersonalGoal(String fileName) constructor}
     *             it is no more necessary to call it</p>
     *
     * @param map reference to the map that contains the value of an attribute in JSON file
     * @param attributeName the attribute name to be searched throw the map
     * @return a string representing the value stored in the json file
     */
    @Deprecated
    private String getAttributeConfig(Map map, String attributeName){
        return map.get(attributeName).toString();
    }

    /**
     * Retrieve the column value from the map passed as parameter
     *
     * @deprecated <p>since version 2.3, replaced by {@link #PersonalGoal(String fileName)} constructor</p>
     *             <p>This method was deprecated because with the new {@linkplain #PersonalGoal(String fileName) constructor}
     *             it is no more necessary to call it</p>
     *
     * @param map the map in which search for the value
     * @return an integer representing the column of a single entry for the pattern goal configuration
     */
    @Deprecated
    private int getColumnConfig(Map map){
        return Integer.parseInt(getAttributeConfig(map, "column"));
    }

    /**
     * Retrieve the row value from the map passed as parameter
     *
     * @deprecated <p>since version 2.3, replaced by {@link #PersonalGoal(String fileName)} constructor</p>
     *             <p>This method was deprecated because with the new {@linkplain #PersonalGoal(String fileName) constructor}
     *             it is no more necessary to call it</p>
     *
     * @param map the map in which search for the value
     * @return an integer representing the row of a single entry for the pattern goal configuration
     */
    @Deprecated
    private int getRowConfig(Map map){
        return Integer.parseInt(getAttributeConfig(map, "row"));
    }

    /**
     * Retrieve the TileType value from the map passed as parameter
     *
     * @deprecated <p>since version 2.3, replaced by {@link #PersonalGoal(String fileName)} constructor</p>
     *             <p>This method was deprecated because with the new {@linkplain #PersonalGoal(String fileName) constructor}
     *             it is no more necessary to call it</p>
     *
     * @param map the map in which search for the value
     * @return a String representing the Tile type for a single entry for the pattern goal configuration
     */
    @Deprecated
    private String getTileTypeConfig(Map map){
        return getAttributeConfig(map, "tileType");
    }

    /**
     * This method is used to create score map configuration from a JSON file that respect the correct formatting
     *
     * @deprecated <p>since version 2.3, replaced by {@link #PersonalGoal(String fileName)} constructor</p>
     *             <p>This method was deprecated because with the new {@linkplain #PersonalGoal(String fileName) constructor}
     *             it is no more necessary to call it</p>
     *
     * @param scoreMapData map used to browse the json file configuration for the entries of the personal goal card
     * @see PersonalGoal
     */
    @Deprecated
    private void fillScoreMapFromJSON(Map scoreMapData){
        Iterator<Map.Entry> entryScoreMapAttributes = scoreMapData.entrySet().iterator();

        while(entryScoreMapAttributes.hasNext()){
            Map.Entry pair = entryScoreMapAttributes.next();

            scoreMap.put(
                    Integer.parseInt(pair.getKey().toString()),
                    Integer.parseInt(pair.getValue().toString())
            );
        }
    }

    /**
     * Overriding toString() default method.
     * {@inheritDoc}
     *
     * @return {@link String} that represent the {@link PersonalGoal} class
     *
     * @apiNote Resulting String will be displayed on multiple lines as follows:
     * <pre>{@code Pattern:{
     *      {column, row, tileType},
     *      {column, row, tileType},
     *      ...
     *      }
     * scoreMap:{}}</pre>
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Pattern:{\n");

        for(int i = 0; i < goalPattern.size(); i++) {
            res.append("\t").append(goalPattern.get(i).toString());

            if(i < goalPattern.size() - 1) {
                res.append(',');
            }

            res.append("\n");
        }

        res.append("}\nscoreMap:")
                .append(scoreMap.toString());

        return res.toString();
    }

    /**
     * Method used to create a random pattern for a personal goal card.
     * The set-up of the pattern is made according to the {@value DEF_NUM_TILE_PATTERN} constant
     *
     * @see PersonalGoal
     */
    private void createRandomGoalPattern() {
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

    /**
     * Method used to retrieve a single random entry that compose the total personal goal card
     *
     * @return random entry pattern goal
     * @see PersonalGoal
     */
    private EntryPatternGoal getRandomEntry() {
        Random random = new Random();

        return new EntryPatternGoal(
                random.nextInt(6),
                random.nextInt(5),
                TileType.values()[random.nextInt(TileType.values().length)]);
    }

    /**
     * Give this method a list of entry that compose a pattern for a personal goal card and a single entry that you want to add
     * at the overall list, and will be determinate if there is already an entry (that compose the pattern),
     * with same position of the new one.
     *
     * @param goalPattern a list of entry that form the personal goal card.
     * @param newEntryPatternGoal new entry for the pattern that needs to be added to the pattern
     * @return {@code true} if in the pattern there is another entry with the same position,
     * {@code false} otherwise
     * @see PersonalGoal
     */
    private boolean hasSamePosition(List<EntryPatternGoal> goalPattern, EntryPatternGoal newEntryPatternGoal){
        for(EntryPatternGoal epg : goalPattern) {
            if(epg.getColumn() == newEntryPatternGoal.getColumn() &&
                    epg.getRow() == newEntryPatternGoal.getRow()) {
                return true; //should throw an exception
            }
        }

        return false;
    }

    /**
     * This method checks if the max {@link #scoreMap score map} value correspond to the number of tiles insert in the
     * {@link #goalPattern pattern}
     *
     * @return {@code true} if the format of the entire personal goal card is set up correctly, {@code false} otherwise
     * @see PersonalGoal
     */
    private boolean verifyInput(){
        if( getMaxScoreMapValue() != goalPattern.size() ) {
            //should throw an exception
            System.out.println("The goal pattern insert does not respect rules");
            return false;
        }

        return true;
    }

    /**
     * Retrieve the max value inside {@link #scoreMap score map} associated with the personal goal card
     *
     * @return max {@link #scoreMap score map} value
     * @see PersonalGoal
     */
    private int getMaxScoreMapValue(){
        Integer max = (Integer) scoreMap.keySet().toArray()[0];

        for(Integer i : scoreMap.keySet()){
                max = ( i > max) ? i : max;
        }

        return max;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        PersonalGoal that = (PersonalGoal) o;

        return Objects.equals(goalPattern, that.goalPattern) &&
                (scoreMap.keySet()
                        .stream()
                        .filter(k -> scoreMap.get(k).equals(that.scoreMap.get(k)))
                        .collect(Collectors.toSet())
                        .size() == scoreMap.size());
    }
}
