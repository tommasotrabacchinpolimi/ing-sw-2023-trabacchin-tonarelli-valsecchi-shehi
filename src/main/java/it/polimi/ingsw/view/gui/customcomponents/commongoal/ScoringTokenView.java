package it.polimi.ingsw.view.gui.customcomponents.commongoal;

import it.polimi.ingsw.view.gui.customcomponents.MyShelfieGraphicIcon;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.layout.Region;

import java.net.URL;

public class ScoringTokenView extends MyShelfieGraphicIcon {

    private static final String SCORING_TOKENS_PATH = "/it.polimi.ingsw/graphical.resources/scoring.tokens/";

    private static final String TOKEN_ICON_PREFIX = "scoring_";

    private static final String TOKEN_ICON_EMPTY = TOKEN_ICON_PREFIX + "empty.jpg";

    private static final double DEF_PADDING = 2.7;

    private final String tokenIcon;

    public ScoringTokenView(Integer scoringValue) {
        this(TOKEN_ICON_PREFIX + scoringValue + ".jpg");
    }

    private ScoringTokenView(String tokenIcon) {
        super(SCORING_TOKENS_PATH + tokenIcon, SCORING_TOKENS_PATH +TOKEN_ICON_EMPTY, DEF_PADDING);

        this.tokenIcon = tokenIcon;

        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
