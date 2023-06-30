package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.JSONExclusionStrategy;
import it.polimi.ingsw.model.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides utility methods for deserializing common goals from JSON configuration files.
 *
 * <p>This class allows retrieving common goals from "*.json" configuration files and provides methods
 * to obtain different sets of common goals based on their macro-groups. It also includes methods to adjust
 * scoring tokens based on the number of players in the game.</p>
 *
 * <p>This class is immutable and cannot be instantiated.</p>
 *
 * <p><strong>Note:</strong> This class assumes the presence of "*.json" configuration files in the specified
 * common goal configuration directories.</p>
 *
 * <p><strong>Version:</strong> 2.0</p>
 * <p><strong>Since:</strong> 10/04/2023</p>
 *
 * @see CommonGoal
 * @see LineCommonGoal
 * @see ShapeCommonGoal
 * @see StairCommonGoal
 * @see TupleCommonGoal
 * @see JSONExclusionStrategy
 * @see Gson
 * @see GsonBuilder
 * @see JsonReader
 * @see Controller
 * @see Path
 * @see Files
 * @see Paths
 * @see Set
 * @see List
 * @see IOException
 * @see RuntimeException
 * @see FileNotFoundException
 * @see Contract
 * @see NotNull
 * @since 10/04/2023
 */
public final class CommonGoalDeserializer {
    /**
     * Constant representing the common goal file path.
     */
    private static final String COMMON_GOAL_CONFIGURATION = "/it.polimi.ingsw/common.goal.configuration/";

    /**
     * Added a private constructor to prevent the instance creation for this class.
     */
    private CommonGoalDeserializer() {
    }

    /**
     * Retrieves all possible {@linkplain CommonGoal common goals} inside every "*.json" configuration file.
     *
     * @return a set containing all possible common goals configuration
     */
    public static Set<CommonGoal> getCommonGoalsDeck() {
        Set<CommonGoal> commonGoalDeck = new HashSet<>();

        for (int i = 1; i <= 4; i++) {
            commonGoalDeck.add(new GsonBuilder()
                    .setExclusionStrategies(new JSONExclusionStrategy())
                    .create()
                    .fromJson(
                            new BufferedReader(new InputStreamReader(Objects.requireNonNull(CommonGoalDeserializer.class.getResourceAsStream(COMMON_GOAL_CONFIGURATION + "LineCommonGoal/" + i + ".json")))),
                            LineCommonGoal.class));
        }

        for (int i = 1; i <= 3; i++) {
            commonGoalDeck.add(new GsonBuilder()
                    .setExclusionStrategies(new JSONExclusionStrategy())
                    .create()
                    .fromJson(
                            new BufferedReader(new InputStreamReader(Objects.requireNonNull(CommonGoalDeserializer.class.getResourceAsStream(COMMON_GOAL_CONFIGURATION + "ShapeCommonGoal/" + i + ".json")))),
                            ShapeCommonGoal.class));
        }

        for (int i = 1; i <= 1; i++) {
            commonGoalDeck.add(new GsonBuilder()
                    .setExclusionStrategies(new JSONExclusionStrategy())
                    .create()
                    .fromJson(
                            new BufferedReader(new InputStreamReader(Objects.requireNonNull(CommonGoalDeserializer.class.getResourceAsStream(COMMON_GOAL_CONFIGURATION + "StairCommonGoal/" + i + ".json")))),
                            StairCommonGoal.class));
        }

        for (int i = 1; i <= 4; i++) {
            commonGoalDeck.add(new GsonBuilder()
                    .setExclusionStrategies(new JSONExclusionStrategy())
                    .create()
                    .fromJson(
                            new BufferedReader(new InputStreamReader(Objects.requireNonNull(CommonGoalDeserializer.class.getResourceAsStream(COMMON_GOAL_CONFIGURATION + "TupleCommonGoal/" + i + ".json")))),
                            TupleCommonGoal.class));
        }
        return commonGoalDeck;

    }

    /**
     * Retrieves all the classes that represent a {@linkplain CommonGoal common goal}.
     *
     * @return classes that are a common goal
     */
    @NotNull
    private static Set<Class<? extends CommonGoal>> getCommonGoalClasses() {
        Set<Class<? extends CommonGoal>> commonGoalClasses = new HashSet<>();

        // From String to Classes
        getCommonGoalClassesName().forEach(name -> {
            try {
                commonGoalClasses.add(Class.forName("it.polimi.ingsw.model." + name).asSubclass(CommonGoal.class));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return commonGoalClasses;
    }

    /**
     * Retrieves all names of classes that represent a common goal.
     *
     * @return names of classes representing a common goal
     */
    @NotNull
    private static List<String> getCommonGoalClassesName() {
        List<String> commonGoalClassesName = new ArrayList<>();

        // Walking through each Directory contained in "COMMON_GOAL_CONFIGURATION" directory to search for "main directory"
        // representing a common goal class
        try (Stream<Path> path = Files.walk(Paths.get(COMMON_GOAL_CONFIGURATION))) {

            path.filter(Files::isDirectory)
                    .filter(e -> e.getNameCount() - Paths.get(COMMON_GOAL_CONFIGURATION).getNameCount() >= 1) // exclude starting folder
                    .forEach(p -> commonGoalClassesName.add(
                            p.toString().substring(COMMON_GOAL_CONFIGURATION.length())
                    ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return commonGoalClassesName;
    }

    /**
     * Retrieves all common goals in a macro-group.
     *
     * <p>Common goals macro-groups are:</p>
     * <ul>
     *   <li>{@linkplain LineCommonGoal Line common goal}</li>
     *   <li>{@linkplain ShapeCommonGoal Shape common goal}</li>
     *   <li>{@linkplain StairCommonGoal Stair common goal}</li>
     *   <li>{@linkplain TupleCommonGoal Tuple common goal}</li>
     * </ul>
     *
     * @param c the class representing the common goal
     * @return a set containing all common goals configuration for a group
     */
    @NotNull
    private static Set<CommonGoal> getCommonGoalConfig(Class<? extends CommonGoal> c) {
        Set<CommonGoal> res = new HashSet<>();
        Gson gson = new GsonBuilder().setExclusionStrategies(new JSONExclusionStrategy()).create();
        JsonReader reader;

        for (String fullFilePath : getFullCommonGoalConfigPath(c)) {
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
     * Returns the literal representing the full file path of each "*.json" configuration file.
     *
     * @param c the class that will be mapped to the common goal
     * @return full path to the configuration file
     */
    @NotNull
    private static List<String> getFullCommonGoalConfigPath(@NotNull Class<? extends CommonGoal> c) {
        List<String> fullPathConfigFile = new ArrayList<>();

        // Walking through each Directory contained in "COMMON_GOAL_CONFIGURATION" to search for "*.json" file
        // representing a common goal class
        try (Stream<Path> path = Files.walk(Paths.get(COMMON_GOAL_CONFIGURATION + c.getSimpleName() + "/"))) {

            path.filter(Files::isRegularFile).forEach(p -> fullPathConfigFile.add(p.toString()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fullPathConfigFile;
    }

    /**
     * This method takes a {@linkplain CommonGoal common goal} and
     * modifies its scoring tokens, according to the number of
     * players in the game.
     *
     * @param controller   the game controller
     * @param commonGoal   the common goal that will have modified scoring tokens
     * @return the common goal with the stack modified
     */
    @Contract("_, _ -> param2")
    private static CommonGoal adjustScoringTokens(Controller controller, CommonGoal commonGoal) {
        int numberOfPlayers = controller.getState().getPlayersNumber();

        if (numberOfPlayers < 4)
            commonGoal.getScoringTokens().removeElementAt(
                    commonGoal.getScoringTokens().search(2)
            );

        if (numberOfPlayers < 3)
            commonGoal.getScoringTokens().removeElementAt(
                    commonGoal.getScoringTokens().search(6)
            );

        return commonGoal;
    }
}
