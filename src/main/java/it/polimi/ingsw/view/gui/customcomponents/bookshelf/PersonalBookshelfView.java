package it.polimi.ingsw.view.gui.customcomponents.bookshelf;

import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieGraphicIcon;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import javafx.scene.layout.Background;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.model.TileType.CAT;
import static it.polimi.ingsw.utils.color.MyShelfieColor.*;

public class PersonalBookshelfView extends BookshelfView {

    /**
     * Path of tileType image
     */
    private static final String TILE_TYPE_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/tile.type/";
    /**
     * Error Tile Type Image
     */
    private static final String ERROR_TILE_TYPE_IMAGE = "/it.polimi.ingsw/graphical.resources/personal.goal.cards/error_personal_goal.png";
    /**
     * Is the Opacity used for images below in the code.
     */
    private static final double ALPHA = 0.57;

    /**
     * A map that stores the precedent style (CSS) of each cell in the bookshelf.
     * This map is used to restore the original style when hiding the tile type hint.
     * The key is the cell coordinate, and the value is the CSS style string.
     */
    private final Map<Coordinate, String> precedentStyleCells = new HashMap<>();

    /**
     * Creates a new instance of {@code PersonalBookshelfView} with the bookshelf view type set to personal.
     */
    public PersonalBookshelfView() {
        super(BookshelfViewType.PERSONAL);
    }

    /**
     * Colors the target cells of the personal bookshelf with the corresponding color based on the provided personal goal configuration.
     *
     * @param personalGoalConfiguration the personal goal configuration containing the coordinates and tile types.
     */
    @Deprecated
    public void colorTargetCells(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {
            bookshelfCells.get(coordinate).setStyle("-fx-background-color: " + getTileTypeColor(tileType) + ";" +
                    "-fx-padding: 2em;");
        });
    }

    /**
     * Resets the color of the target cells of the personal bookshelf based on the provided personal goal configuration.
     *
     * @param personalGoalConfiguration the personal goal configuration containing the coordinates and tile types.
     */
    @Deprecated
    public void resetTargetCellsColor(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {
            bookshelfCells.get(coordinate).setStyle("-fx-background-color: transparent;" +
                    "-fx-padding: 2em;");
        });
    }

    /**
     *
     * method used to return the color of the TileType.
     *
     * @param tileType Is the tile type being considered.
     * @return the color of the tile type.
     */
    @Deprecated
    private String getTileTypeColor(@NotNull TileType tileType) {
        switch (tileType) {
            case CAT -> {
                return APPLE_GREEN.getDarkenRGBAStyleSheet(ALPHA);
            }
            case BOOK -> {
                return BONE.getRGBAStyleSheet(ALPHA);
            }
            case GAME -> {
                return GAMBOGE.getRGBAStyleSheet(ALPHA);
            }
            case FRAME -> {
                return SAPPHIRE.getRGBAStyleSheet(ALPHA);
            }
            case TROPHY -> {
                return VERDIGRIS.getRGBAStyleSheet(ALPHA);
            }
            case PLANT -> {
                return MAGENTA.getRGBAStyleSheet(ALPHA);
            }
            default -> {
                return RED_RUBY.getRGBAStyleSheet(1.0);
            }
        }
    }

    /**
     * Adds the tile type hint to the cells of the personal bookshelf based on the provided personal goal configuration.
     *
     * @param personalGoalConfiguration the personal goal configuration containing the coordinates and tile types.
     */
    public void addTileTypeHint(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {
            if (bookshelfCells.get(coordinate).getChildren().size() == 0) {
                precedentStyleCells.put(coordinate, bookshelfCells.get(coordinate).getStyle());
                bookshelfCells.get(coordinate).setStyle("-fx-background-image: url('" + getTileTypeImagePath(tileType) + "');" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-size: cover;" +
                        "-fx-padding: 2em;");
                bookshelfCells.get(coordinate).setOpacity(ALPHA);
            }
        });
    }

    /**
     * Hides the tile type hint from the cells of the personal bookshelf based on the provided personal goal configuration.
     *
     * @param personalGoalConfiguration the personal goal configuration containing the coordinates and tile types.
     */
    public void hideTileTypeHint(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {
            if (bookshelfCells.get(coordinate).getChildren().size() == 0) {
                if (precedentStyleCells.get(coordinate).contains("-fx-background-image: null;"))
                    bookshelfCells.get(coordinate).setStyle(precedentStyleCells.get(coordinate));
                else
                    bookshelfCells.get(coordinate).setStyle(precedentStyleCells.get(coordinate) + "-fx-background-image: null;");
                bookshelfCells.get(coordinate).setOpacity(1.0);
            }
        });
    }

    /**
     *
     * Method used to get the path of a tile type.
     *
     * @param tileType Tile Type which path is needed.
     * @return the correct path of the tile type inside the project.
     */
    private String getTileTypeImagePath(@NotNull TileType tileType) {
        String[] filePath = new String[1];
        MyShelfieComponent
                .getOptionalMyShelfieResource(TILE_TYPE_IMAGE_PATH + tileType.name().toLowerCase() + ".png")
                .ifPresentOrElse(url -> filePath[0] = url.toString(), () -> filePath[0] = ERROR_TILE_TYPE_IMAGE);
        return filePath[0];
    }
}
