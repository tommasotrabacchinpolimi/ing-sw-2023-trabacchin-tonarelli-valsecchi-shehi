package it.polimi.ingsw.view.gui.customcomponents.waitingpage;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

import static it.polimi.ingsw.utils.color.MyShelfieColor.BONE;

class PointPlayerBox extends VBox implements MyShelfieComponent {

    private static final String POINT_PLAYER_BACKGROUND = "/it.polimi.ingsw/graphical.resources/misc/page_base_darken.jpg";

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    private final Integer maxPoint;

    /**
     * Creates a {@code VBox} layout with {@code spacing = 0} and alignment at {@code TOP_LEFT}.
     */
    public PointPlayerBox(Map<String, Integer> playersTotalPoint) {

        this.maxPoint = getMaxPoint(playersTotalPoint.values().stream().toList());
        System.out.println(maxPoint);

        playersTotalPoint.forEach(this::addProgressIndicatorPlayer);

        setCSS();

        applyDecorationAsDefault(new MyShelfieDarkShadow(), new MyShelfieRoundEdge(MyShelfieRoundEdgeType.MINIMUM));
    }

    private Integer getMaxPoint(List<Integer> values) {
        return values.stream().mapToInt(value -> (int) value).sorted().toArray()[values.size()-1];
    }

    private void addProgressIndicatorPlayer(String nickName, Integer totalPoint) {
        HBox playerPointContainer = new HBox();
        playerPointContainer.setId("winningPagePlayerPointContainer");
        playerPointContainer.prefWidthProperty().bind(prefWidthProperty());

        Label playerName = new Label(nickName);
        playerName.setId("winningPagePlayerName");
        playerName.setLabelFor(playerPointContainer);

        ProgressBar pointProgressBar = new ProgressBar();
        pointProgressBar.setId("winningPagePointProgressBar");
        pointProgressBar.setProgress(getProgressValue(totalPoint));

        Label playerPointValue = new Label(totalPoint.toString());
        playerPointValue.setId("winningPagePlayerPointValue");
        playerName.setLabelFor(playerPointContainer);

        playerPointContainer.getChildren().addAll(playerName, pointProgressBar, playerPointValue);

        getChildren().add(playerPointContainer);
    }

    private double getProgressValue(Integer playerTotalPoint) {
        if(playerTotalPoint.equals(maxPoint))
            return 1.0;
        else
            return (playerTotalPoint * 100.0 / maxPoint) / 100.0;
    }

    private void setCSS() {
        setStyle("-fx-background-image: url('" + getMyShelfieResource(POINT_PLAYER_BACKGROUND) + "');" +
                "-fx-background-size: cover;" +
                "-fx-background-position: center;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-padding: 2em;" +
                "-fx-alignment: center;");
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
