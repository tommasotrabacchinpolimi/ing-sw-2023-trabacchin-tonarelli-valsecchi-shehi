package it.polimi.ingsw.view.gui.customcomponents;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.*;

public class PersonalGoalView extends Pane implements MyShelfieComponent {

    private static final String PERSONAL_GOAL_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/personal.goal.cards/";

    private static final String PERSONAL_GOAL_MAP = "/it.polimi.ingsw/personal.goal.configuration/personal_goal_map.json";

    private static final String ERROR_PERSONAL_GOAL_IMAGE = "error_personal_goal.png";

    private String fileImage;

    private TileType[][] personalConfiguration;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Creates a Personal Goal graphical component.
     */
    public PersonalGoalView(TileType[][] personalGoalChosen) {
        super();

        setPersonalGoalImage(personalGoalChosen);

        setCSS();

        applyDecorationAsDefault(new MyShelfieDarkShadow(), new MyShelfieRoundEdge(MyShelfieRoundEdgeType.MINIMUM));
    }

    private void setCSS() {
        setStyle("-fx-background-image: url('" + getMyShelfieResource(PERSONAL_GOAL_IMAGE_PATH + fileImage,
                "Can't load personal goal card") + "');" +
                "-fx-padding: 13.5em 9em 13.5em 9em;" +
                "-fx-background-position: center;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }

    private void setPersonalGoalImage(TileType[][] personalGoalChosen) {
        Gson gson = new Gson();

        try {
            File personalGoalMapFile = new File(Objects.requireNonNull(getClass().getResource(PERSONAL_GOAL_MAP)).toURI());
            JsonReader jsonReader = new JsonReader(new FileReader(personalGoalMapFile));

            //Map<Image, TileType configuration>
            Map<String, TileType[][]> personalGoalMap = fromScratchMapToTileType(gson.fromJson(jsonReader, Map.class));

            fileImage = personalGoalMap.keySet()
                    .stream()
                    .filter(personalGoalJSON -> verifyPersonalGoalChosen(personalGoalChosen,
                            personalGoalMap.get(personalGoalJSON)))
                    .findFirst()
                    .orElseThrow();

            this.personalConfiguration = personalGoalMap.get(fileImage);

            fileImage += ".png";

        } catch (FileNotFoundException | NoSuchElementException | URISyntaxException e) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "The file containing the configuration of personal goals was not found",
                    "Error in loading personal goal image");

            fileImage = ERROR_PERSONAL_GOAL_IMAGE;
        }
    }

    @NotNull
    private Map<String, TileType[][]> fromScratchMapToTileType(@NotNull Map<String, List<List<String>>> scratchMap) {
        Map<String, TileType[][]> personalGoalMap = new HashMap<>();

        scratchMap.forEach((personalGoalFileName, personalGoalConfiguration) -> {
            personalGoalMap.put(personalGoalFileName, toPersonalGoalMatrix(personalGoalConfiguration));
        });

        return personalGoalMap;
    }

    @NotNull
    private TileType[][] toPersonalGoalMatrix(@NotNull List<List<String>> scratchList) {
        TileType[][] personalGoalMatrix = new TileType[scratchList.size()][scratchList.get(0).size()];

        for (int i = 0; i < scratchList.size(); ++i) {
            for (int j = 0; j < scratchList.get(0).size(); ++j) {
                personalGoalMatrix[i][j] = fromStringToTileType(scratchList.get(i).get(j));
            }
        }

        return personalGoalMatrix;
    }

    @Nullable
    private TileType fromStringToTileType(String tileTypeName) {
        return (tileTypeName == null) ? null : TileType.valueOf(tileTypeName);
    }

    private boolean verifyPersonalGoalChosen(@NotNull TileType[][] personalGoalChosen, @NotNull TileType[][] personalGoalJSON) {
        if (personalGoalChosen.length != personalGoalJSON.length)
            return false;

        for (int i = 0; i < personalGoalChosen.length; ++i) {
            for (int j = 0; j < personalGoalChosen[i].length; ++j) {
                if (personalGoalChosen[i][j] != null && !personalGoalChosen[i][j].equals(personalGoalJSON[i][j]))
                    return false;
            }
        }

        return true;
    }

    public Map<Coordinate, TileType> getPersonalConfiguration() {
        Map<Coordinate, TileType> personalGoalMapConfiguration = new HashMap<>();

        for (int i = 0; i < personalConfiguration.length; ++i) {
            for(int j = 0; j < personalConfiguration[i].length; ++j) {
                if(personalConfiguration[i][j] != null)
                    personalGoalMapConfiguration.put(new Coordinate(i, j), personalConfiguration[i][j]);
            }
        }

        return personalGoalMapConfiguration;
    }

    /**
     * Retrieve the element that has to be customized
     *
     * @return the element that needs to be customized
     */
    @Override
    public Node getCustomizedNode() {
        return this;
    }

    /**
     * Retrieves the list of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the list of default decorations
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}