package it.polimi.ingsw.view.gui.customcomponents;

import javafx.scene.layout.Region;

/**
 * Represents the graphical view of an end game token.
 * It extends the {@link MyShelfieGraphicIcon} class.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class EndGameTokenView extends MyShelfieGraphicIcon {
    /**
     * The file path for the end game scoring icon image.
     */
    private static final String END_GAME_SCORING_ICON = "/it.polimi.ingsw/graphical.resources/scoring.tokens/end_game.jpg";

    /**
     * The file path for the error scoring token icon image.
     * This icon is displayed when the scoring token image cannot be loaded.
     */
    private static final String ERROR_SCORING_TOKEN_ICON = "/it.polimi.ingsw/graphical.resources/scoring.tokens/scoring_empty.jpg";

    /**
     * The default padding value for the end game token view.
     */
    private static final double DEF_PADDING = 3.1;


    /**
     * Constructs an instance of the {@code EndGameTokenView} class.
     * It sets the default scoring icon, error scoring icon, and padding.
     * It also sets the maximum size of the view to use preferred size.
     */
    public EndGameTokenView() {
        super(END_GAME_SCORING_ICON, ERROR_SCORING_TOKEN_ICON, DEF_PADDING);

        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
