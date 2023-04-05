package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.RemoteInterface;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonGoalDeserializer<R extends RemoteInterface>{

    private Controller controller;

    private static final String COMMON_GOAL_CONFIGURATION = "./src/main/resources/CommonGoalConfiguration/";

    public void initScoringTokens(){
        Stack<Integer> scoringTokens = new Stack<>();

        int numberOfPlayers = controller.getState().getPlayersNumber();

        if(numberOfPlayers == 4)
            scoringTokens.push(2);

        scoringTokens.push(4);
        if (numberOfPlayers >= 3)
            scoringTokens.push(6);

        scoringTokens.push(8);
    }

    /**
     * Retrieves all possible {@linkplain CommonGoal common goals} inside every "*.json" configuration file
     *
     * @return a set containing all possible common goals configuration
     * @see CommonGoal
     * @see LineCommonGoal
     * @see ShapeCommonGoal
     * @see StairCommonGoal
     * @see TupleCommonGoal
     */
    public Set<CommonGoal> getCommonGoalsDeck(){
        return getCommonGoalClasses().stream()
                .map(this::getCommonGoalConfig)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * <p>Retrieves all common goals in a macro-group</p>
     * <p>Common goals macro-groups are
     * <ul>
     *     <li>{@linkplain LineCommonGoal Line common goal}</li>
     *     <li>{@linkplain ShapeCommonGoal Shape common goal}</li>
     *     <li>{@linkplain StairCommonGoal Stair common goal}</li>
     *     <li>{@linkplain TupleCommonGoal Tuple common goal}</li>
     * </ul>
     * </p>
     *
     * @param c the class representing the common goal
     * @return a set containing all common goals configuration for a group
     *
     * @see CommonGoal
     * @see LineCommonGoal
     * @see ShapeCommonGoal
     * @see StairCommonGoal
     * @see TupleCommonGoal
     */
    private Set<CommonGoal> getCommonGoalConfig(Class<? extends CommonGoal> c) {
        Set<CommonGoal> res = new HashSet<>();
        Gson gson = new Gson();
        JsonReader reader;

        for(String fullFilePath : getFullCommonGoalConfigPath(c)) {
            try {
                reader = new JsonReader(new FileReader(fullFilePath));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            res.add(gson.fromJson(reader, c));
        }

        return res;
    }

    /**
     * Return the literal representing the file path of each "*.json" configuration file
     *
     * @param c the class to which map the common goal
     * @return path to the configuration file
     *
     * @deprecated <p>Since version 1.0, replaced by {@link #getFullCommonGoalConfigPath(Class commonGoal)}</p>
     * <p>This method was deprecated due to performance, security and robustness of the code</p>
     *
     * @see CommonGoal
     * @see LineCommonGoal
     * @see ShapeCommonGoal
     * @see StairCommonGoal
     * @see TupleCommonGoal
     */
    @Deprecated
    private String getCommonGoalConfigurationPath(Class<? extends CommonGoal> c){
        StringBuilder path = new StringBuilder();

        path.append(COMMON_GOAL_CONFIGURATION)
                .append(c.getSimpleName())
                .append("/")
                .append(1)
                .append(".json");

        return path.toString();
    }

    /**
     * Use this method to get all the classes that represent a {@linkplain CommonGoal common goal}
     *
     * @return classes that are a common goal
     *
     * @see CommonGoal
     * @see LineCommonGoal
     * @see ShapeCommonGoal
     * @see StairCommonGoal
     * @see TupleCommonGoal
     */
    private Set<Class<? extends CommonGoal>> getCommonGoalClasses(){
        Set<Class<? extends CommonGoal>> commonGoalClasses = new HashSet<Class<? extends CommonGoal>>();

        //From String to Classes
        getCommonGoalClassesName().forEach(n -> {
            try {
                commonGoalClasses.add(Class.forName("it.polimi.ingsw.model." + n).asSubclass(CommonGoal.class));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return commonGoalClasses;
    }

    /**
     * Retrieves all name of classes that represent a common goal
     *
     * @return names of classes representing a common goal
     */
    private List<String> getCommonGoalClassesName(){
        List<String> commonGoalClassesName = new ArrayList<String>();

        //Walking through each Directory contained in "COMMON_GOAL_CONFIGURATION" directory to search for "main directory"
        //representing a common goal class
        try (Stream<Path> path = Files.walk(Paths.get(COMMON_GOAL_CONFIGURATION))) {

            path.filter(Files::isDirectory)
                    .filter(e -> e.getNameCount() - Paths.get(COMMON_GOAL_CONFIGURATION).getNameCount() >= 1) //exclude starting folder
                    .forEach(p -> commonGoalClassesName.add(
                            p.toString().substring(COMMON_GOAL_CONFIGURATION.length())
                    ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return commonGoalClassesName;
    }

    /**
     * Return the literal representing the full file path of each "*.json" configuration file
     *
     * @param c the class to which map the common goal
     * @return full path to the configuration file
     */
    private List<String> getFullCommonGoalConfigPath(Class<? extends CommonGoal> c){
        List<String> fullPathConfigFile = new ArrayList<>();

        //Walking through each Directory contained in "COMMON_GOAL_CONFIGURATION"  to search for "file.json"
        //representing a common goal class
        try (Stream<Path> path = Files.walk(Paths.get(COMMON_GOAL_CONFIGURATION + c.getSimpleName() + "/"))) {

            path.filter(Files::isRegularFile).forEach(p -> fullPathConfigFile.add(p.toString()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fullPathConfigFile;
    }
}
