package it.polimi.ingsw.view.gui.customcomponents.waitingpage;

import it.polimi.ingsw.view.gui.customcomponents.MyShelfieGraphicIcon;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Map;

public class WinningPage extends BlockingPage{

    private static final String WINNING_ICON_PATH = "/it.polimi.ingsw/graphical.resources/misc/winner_icon.png";

    private static final String WINNING_PAGE_STYLE = "/it.polimi.ingsw/gui/layout/WinningPage.css";

    private static final double WINNING_ICON_PADDING = 9.7;

    private VBox graphicalElementsContainer;

    private MyShelfieLabel winnerLabel;

    private MyShelfieGraphicIcon winnerIcon;

    private PointPlayerBox playerPoints;

    public WinningPage(Scene scene, String winnerText, Map<String, Integer> playersTotalPoint) {
        super(scene);

        setStyleSheet(WINNING_PAGE_STYLE);

        addGraphicalElementsContainer(winnerText, playersTotalPoint);

        addToBlockingPage(graphicalElementsContainer);
    }

    private void addGraphicalElementsContainer(String winnerText, Map<String, Integer> playersTotalPoint) {
        graphicalElementsContainer = new VBox();
        graphicalElementsContainer.setId("winningPageGraphicalElementsContainer");

        addWinnerIcon();

        addWinnerLabel(winnerText);

        playerPoints = new PointPlayerBox(playersTotalPoint);
        graphicalElementsContainer.getChildren().add(playerPoints);

        graphicalElementsContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    private void addWinnerLabel(String winnerText) {
        if(winnerText != null) {
            winnerLabel = new MyShelfieLabel(winnerText);
            winnerLabel.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            graphicalElementsContainer.getChildren().add(winnerLabel);
        }
    }

    private void addWinnerIcon() {
        winnerIcon = new MyShelfieGraphicIcon(WINNING_ICON_PATH, WINNING_ICON_PADDING);
        winnerIcon.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        graphicalElementsContainer.getChildren().add(winnerIcon);
    }
}
