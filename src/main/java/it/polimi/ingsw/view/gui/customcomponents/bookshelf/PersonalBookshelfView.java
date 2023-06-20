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

public class PersonalBookshelfView extends BookshelfView{

    private static final String TILE_TYPE_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/tile.type/";

    private static final String ERROR_TILE_TYPE_IMAGE = "/it.polimi.ingsw/graphical.resources/personal.goal.cards/error_personal_goal.png";

    private static final double ALPHA = 0.57;

    private final Map<Coordinate, String> precedentStyleCells = new HashMap<>();

    public PersonalBookshelfView() {
        super(BookshelfViewType.PERSONAL);
    }

    @Deprecated
    public void colorTargetCells(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {
            bookshelfCells.get(coordinate).setStyle("-fx-background-color: " + getTileTypeColor(tileType) + ";" +
                    "-fx-padding: 2em;");
        });
    }

    @Deprecated
    public void resetTargetCellsColor(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {
            bookshelfCells.get(coordinate).setStyle("-fx-background-color: transparent;" +
                    "-fx-padding: 2em;");
        });
    }

    @Deprecated
    private String getTileTypeColor(@NotNull TileType tileType) {

        switch(tileType){
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

    public void addTileTypeHint(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {

            if(bookshelfCells.get(coordinate).getChildren().size() == 0) {

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

    public void hideTileTypeHint(@NotNull Map<Coordinate, TileType> personalGoalConfiguration) {
        personalGoalConfiguration.forEach((coordinate, tileType) -> {
            if(bookshelfCells.get(coordinate).getChildren().size() == 0) {
                if(precedentStyleCells.get(coordinate).contains("-fx-background-image: null;"))
                    bookshelfCells.get(coordinate).setStyle(precedentStyleCells.get(coordinate));
                else
                    bookshelfCells.get(coordinate).setStyle(precedentStyleCells.get(coordinate) + "-fx-background-image: null;");

                bookshelfCells.get(coordinate).setOpacity(1.0);
            }
        });
    }

    private String getTileTypeImagePath(@NotNull TileType tileType) {
        String[] filePath = new String[1];

        MyShelfieComponent
                .getOptionalMyShelfieResource(TILE_TYPE_IMAGE_PATH + tileType.name().toLowerCase() + ".png")
                .ifPresentOrElse(url -> filePath[0] = url.toString(), () -> filePath[0] = ERROR_TILE_TYPE_IMAGE);

        return filePath[0];
    }
}
