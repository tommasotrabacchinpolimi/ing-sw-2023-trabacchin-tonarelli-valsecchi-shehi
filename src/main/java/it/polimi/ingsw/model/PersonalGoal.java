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
 * <p>The default number of Tile inside a personal goal pattern is {@value DEF_NUM_TILE_PATTERN}.</p>
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
