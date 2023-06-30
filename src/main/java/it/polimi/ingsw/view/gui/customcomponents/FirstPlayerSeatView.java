package it.polimi.ingsw.view.gui.customcomponents;

import javafx.scene.layout.Region;

/**
 *
 * The {@code FirstPlayerSeatView} class represents a graphical component that displays the first player seat
 * in a board game. It extends the {@link MyShelfieGraphicIcon} class and provides additional functionalities
 * specific to the first player seat view.
 * <p>
 * The first player seat view is used to visualize the first player token, which indicates the player who
 * will play first in the game. It utilizes an image of the first player token and applies a default padding
 * to the view for spacing purposes.
 * <p>
 * The first player seat view is designed for use in a JavaFX GUI application. It utilizes JavaFX layouts,
 * styles, and CSS properties to render the graphical representation of the first player seat.
 *
 * @since 1.0
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class FirstPlayerSeatView extends MyShelfieGraphicIcon{

    /**
     * The path to the directory containing the scoring token images.
     */
    private static final String FIRST_PLAYER_SEAT_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/misc/first_player_token.png";

    /**
     * The default padding for the scoring token view.
     */
    private static final double DEF_PADDING = 2.2;

    /**
     * Creates a new instance of {@code FirstPlayerSeatView} with the first player seat image and default padding.
     *
     * The maximum size of the view is set to use the preferred size of the region.
     */
    public FirstPlayerSeatView() {
        super(FIRST_PLAYER_SEAT_IMAGE_PATH, DEF_PADDING);

        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
