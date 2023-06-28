package it.polimi.ingsw.view.gui.customcomponents;

import javafx.scene.layout.Region;

public class FirstPlayerSeatView extends MyShelfieGraphicIcon{

    /**
     * The path to the directory containing the scoring token images.
     */
    private static final String FIRST_PLAYER_SEAT_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/misc/first_player_token.png";

    /**
     * The default padding for the scoring token view.
     */
    private static final double DEF_PADDING = 2.2;

    public FirstPlayerSeatView() {
        super(FIRST_PLAYER_SEAT_IMAGE_PATH, DEF_PADDING);

        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
