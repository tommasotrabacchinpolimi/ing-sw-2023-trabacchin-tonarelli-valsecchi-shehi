package it.polimi.ingsw.view.gui.customcomponents;

import javafx.scene.layout.Region;

public class EndGameTokenView extends MyShelfieGraphicIcon{

    private static final String END_GAME_SCORING_ICON = "/it.polimi.ingsw/graphical.resources/scoring.tokens/end_game.jpg";

    private static final String ERROR_SCORING_TOKEN_ICON = "/it.polimi.ingsw/graphical.resources/scoring.tokens/scoring_empty.jpg";

    private static final double DEF_PADDING = 3.1;

    public EndGameTokenView() {
        super(END_GAME_SCORING_ICON, ERROR_SCORING_TOKEN_ICON, DEF_PADDING);

        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
