package it.polimi.ingsw.view.gui.customcomponents.commongoal;

import it.polimi.ingsw.view.gui.customcomponents.animations.*;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import static it.polimi.ingsw.utils.color.MyShelfieColor.CHARLESTON;

/**
 * @version 2
 * @since 17/04/2023
 */
public class CommonGoalView extends StackPane implements MyShelfieComponent {

    private static final String COMMON_GOAL_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/common.goal.cards/";

    private static final String ERROR_COMMON_GOAL_IMAGE = "error_common_goal.jpg";

    private static final String DESCRIPTION_BACKGROUND_IMAGE = "/it.polimi.ingsw/graphical.resources/misc/white_paper_texture.png";

    private static final String EMPTY_DESCRIPTION = "No description available";

    private static final double SCORING_TOKEN_ROTATION = -8.0;

    private final String commonGoalID;

    private boolean isShowingDescription;

    private final String description;

    private final Stack<ScoringTokenView> scoringTokenViews = new Stack<>();

    private final Pane scoringTokenBox;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    public CommonGoalView(String commonGoalName, String description, Stack<Integer> scoringTokens) {
        super();

        this.commonGoalID = commonGoalName;

        this.description = (description == null || description.length() == 0) ? EMPTY_DESCRIPTION : description;

        this.isShowingDescription = false;

        this.scoringTokenBox = new Pane();

        setupScoringTokensBox();

        setCSS();

        addScoringTokens(scoringTokens);

        applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL), new MyShelfieDarkShadow());

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        setOnMouseClicked(this::displayCommonGoalDescription);
    }

    private void setupScoringTokensBox() {
        this.scoringTokenBox.setStyle("-fx-padding: 2.7em;");

        this.scoringTokenBox.getChildren().addListener((ListChangeListener<? super Node>) change -> {
            while(change.next()){
                if(change.wasAdded()) {
                    scoringTokenBox.setStyle("-fx-padding: 0em;");
                } else if (change.wasRemoved() && scoringTokenBox.getChildren().size() == 0) {
                    scoringTokenBox.setStyle("-fx-padding: 2.7em;");
                }
            }
        });

        getChildren().add(scoringTokenBox);

        this.scoringTokenBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    public CommonGoalView(String commonGoalName, String description) {
        this(commonGoalName, description, null);
    }

    private void setCSS() {
        setStyle("-fx-background-image: url('" + getCommonGoalBackground() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;" +
                "-fx-padding: 3.0em 2.1em 3.8em 10.7em;");
    }

    private URL getCommonGoalBackground() {
        return getMyShelfieResource(
                COMMON_GOAL_IMAGE_PATH + commonGoalID + ".jpg",
                COMMON_GOAL_IMAGE_PATH + ERROR_COMMON_GOAL_IMAGE,
                "The image of the common goal requested doesn't exists, an empty card will be displayed"
        );
    }

    private void addScoringTokens(final Stack<Integer> scoringTokens) {

        if (scoringTokens != null && scoringTokens.size() > 0) {
            scoringTokens.forEach(token -> {
                scoringTokenViews.push(new ScoringTokenView(token));
            });

            showTopToken();

        } else {
            MyShelfieAlertCreator.displayWarningAlert(
                    "No scoring tokens can be assigned to the common goal",
                    "Error in loading scoring token"
            );
        }
    }

    private void showTopToken() {
        ScoringTokenView topToken = scoringTokenViews.peek();

        scoringTokenBox.getChildren().add(topToken);

        topToken.setRotate(SCORING_TOKEN_ROTATION);
    }

    private ScoringTokenView getTopToken() throws EmptyStackException {
        ScoringTokenView topToken =  scoringTokenViews.pop();

        if(scoringTokenViews.size() > 0)
            showTopToken();

        return topToken;
    }

    public void moveScoringTokenView(Pane destinationPane) throws EmptyStackException{
        ScoringTokenView movingToken = getTopToken();

        MyShelfieAnimation.build()
                .addAnimation(new MyShelfieRotateTransition(SCORING_TOKEN_ROTATION, 0.0))
                .addAnimation(new MyShelfiePathTransition(movingToken, destinationPane))
                .addAnimation(new MyShelfieScaleTransition(0.81, 0.81))
                .playMyShelfieAnimation(movingToken, value -> {
                    getChildren().remove(movingToken);
                    destinationPane.getChildren().add(movingToken);

                    movingToken.setStyle(movingToken.getStyle() + "-fx-padding: 2.2em;");

                    movingToken.setScaleX(1.0);
                    movingToken.setScaleY(1.0);

                    movingToken.setTranslateX(0.0);
                    movingToken.setTranslateY(0.0);
                });
    }

    private void displayCommonGoalDescription(MouseEvent mouseEvent) {
        if (!isShowingDescription) {
            Label descriptionLabel = new Label(description);
            descriptionLabel.setStyle("-fx-font-family: 'Special Elite Regular';" +
                    "-fx-font-size: 1.3em;" +
                    "-fx-text-fill: " + CHARLESTON.getRGBAStyleSheet(0.97) + " ;" +
                    "-fx-wrap-text: true;" +
                    "-fx-label-padding: 1em;");
            descriptionLabel.setMouseTransparent(true);
            descriptionLabel.setMaxWidth(this.getWidth());
            descriptionLabel.setMinWidth(this.getWidth());
            descriptionLabel.setPrefWidth(this.getWidth());

            StackPane descriptionContainer = new StackPane();
            descriptionContainer.setStyle("-fx-background-image: url('" + getMyShelfieResource(DESCRIPTION_BACKGROUND_IMAGE) + "');" +
                    "-fx-background-position: center;" +
                    "-fx-background-repeat: no-repeat;" +
                    "-fx-background-size: cover;");

            descriptionContainer.getChildren().add(descriptionLabel);

            Stage descriptionStage = new Stage();
            descriptionStage.initOwner(this.getScene().getWindow());
            descriptionStage.initStyle(StageStyle.UTILITY);
            descriptionStage.setScene(new Scene(descriptionContainer));
            descriptionStage.sizeToScene();

            Bounds boundsInScene = localToScene(getBoundsInLocal());

            descriptionStage.setX(boundsInScene.getMaxX() - (boundsInScene.getWidth() - getWidth()) - getWidth());
            descriptionStage.setY(boundsInScene.getMaxY() - (boundsInScene.getHeight() - getHeight()));
            descriptionStage.setResizable(false);

            PauseTransition delay = new PauseTransition(MyShelfieTransitionDurationType.EXTREME_LONG_DURATION.getDuration());
            delay.setOnFinished(event -> {
                if (descriptionStage.isShowing()) {
                    descriptionStage.close();
                    isShowingDescription = false;
                }
            });

            delay.play();

            descriptionStage.show();
            isShowingDescription = true;

            descriptionStage.setOnCloseRequest(value -> isShowingDescription = false);
        }
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
