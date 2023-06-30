package it.polimi.ingsw.view.gui.customcomponents.commongoal;

import it.polimi.ingsw.view.gui.customcomponents.MyShelfieGraphicIcon;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.layout.Region;

import java.net.URL;

/**
 * The `ScoringTokenView` class represents a graphical view component for a scoring token.
 * It extends the `MyShelfieGraphicIcon` class and displays an image representing the scoring token.
 * The scoring value of the token determines the image displayed.
 * This class provides a convenient way to create and customize scoring token views in the graphical user interface.
 * <br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ScoringTokenView extends MyShelfieGraphicIcon {

    /**
     * The path to the directory containing the scoring token images.
     */
    private static final String SCORING_TOKENS_PATH = "/it.polimi.ingsw/graphical.resources/scoring.tokens/";

    /**
     * The prefix for the token icon filenames.
     */
    private static final String TOKEN_ICON_PREFIX = "scoring_";

    /**
     * The filename for the empty token icon image.
     */
    private static final String TOKEN_ICON_EMPTY = TOKEN_ICON_PREFIX + "empty.jpg";

    /**
     * The default padding for the scoring token view.
     */
    private static final double DEF_PADDING = 2.7;

    /**
     * The filename of the token icon image.
     */
    private final String tokenIcon;

    /**
     * The Scoring value in terms of point
     */
    private Integer scoringValue;

    /**
     * Constructs a `ScoringTokenView` object with the specified scoring value.
     * The appropriate token icon image is displayed based on the scoring value.
     *
     * @param scoringValue The value of the scoring token.
     */
    public ScoringTokenView(Integer scoringValue) {
        this(TOKEN_ICON_PREFIX + scoringValue + ".jpg");

        this.scoringValue = scoringValue;
    }

    /**
     * Constructs a `ScoringTokenView` object with the specified token icon.
     *
     * @param tokenIcon The filename of the token icon image.
     */
    private ScoringTokenView(String tokenIcon) {
        super(SCORING_TOKENS_PATH + tokenIcon, SCORING_TOKENS_PATH + TOKEN_ICON_EMPTY, DEF_PADDING);

        this.tokenIcon = tokenIcon;

        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    /**
     * Method to get the scoring Value
     *
     * @return the scoringValue
     */

    public int getTokenPoint() {
        return scoringValue;
    }
}
