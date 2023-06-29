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
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import static it.polimi.ingsw.utils.color.MyShelfieColor.CHARLESTON;

/**
 * Represents a view for a common goal in the graphical user interface.
 * This class extends `StackPane` and implements the `MyShelfieComponent` interface.
 *
 * @version 2
 * @since 17/04/2023
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class CommonGoalView extends StackPane implements MyShelfieComponent {

    /**
     * The path to the directory containing common goal images.
     */
    private static final String COMMON_GOAL_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/common.goal.cards/";

    /**
     * The name of the default image to be used if a common goal image is not found.
     */
    private static final String ERROR_COMMON_GOAL_IMAGE = "error_common_goal.jpg";

    /**
     * The path to the background image for the common goal description.
     */
    private static final String DESCRIPTION_BACKGROUND_IMAGE = "/it.polimi.ingsw/graphical.resources/misc/white_paper_texture.png";


    /**
     * The default description to be displayed if no description is available.
     */
    private static final String EMPTY_DESCRIPTION = "No description available";

    /**
     * The default Rotation.
     */
    private static final double SCORING_TOKEN_ROTATION = -8.0;


    /**
     * The ID of the common goal.
     */
    private final String commonGoalID;

    /**
     * A flag indicating whether the common goal description is currently being shown.
     */
    private boolean isShowingDescription;


    /**
     * The description of the common goal.
     */
    private final String description;


    /**
     * The stack of scoring token views associated with the common goal.
     */
    private final Stack<ScoringTokenView> scoringTokenViews = new Stack<>();


    /**
     * The pane that holds the scoring token views.
     */
    private final Pane scoringTokenBox;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Constructs a `CommonGoalView` object with the specified parameters.
     *
     * @param commonGoalID The name of the common goal.
     * @param description    The description of the common goal.
     * @param topTokenValue  The token max value shown.
     */
    public CommonGoalView(String commonGoalID, String description, int topTokenValue) {
        super();

        this.commonGoalID = (commonGoalID == null || commonGoalID.length() == 0) ? ERROR_COMMON_GOAL_IMAGE : commonGoalID;

        this.description = (description == null || description.length() == 0) ? EMPTY_DESCRIPTION : description;

        this.isShowingDescription = false;

        this.scoringTokenBox = new Pane();

        setupScoringTokensBox();

        setCSS();

        addTopScoringToken(topTokenValue);

        applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL), new MyShelfieDarkShadow());

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        setOnMouseClicked(this::displayCommonGoalDescription);
    }

    /**
     * Sets up the scoring tokens box with the necessary styles and listeners.
     */
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

    /**
     * Method used to set a prepared CSS style
     */
    private void setCSS() {
        setStyle("-fx-background-image: url('" + getCommonGoalBackground() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;" +
                "-fx-padding: 3.0em 2.1em 3.8em 10.7em;");
    }

    /**
     * method to get the URL image of a CommonGoal Configuration
     * <br>
     * <p>In case of error it will be shown an error message and an empty tile will be shown</p>
     * @return the URL of the commonGoal chosen
     */
    private URL getCommonGoalBackground() {
        return getMyShelfieResource(
                COMMON_GOAL_IMAGE_PATH + commonGoalID + ".jpg",
                COMMON_GOAL_IMAGE_PATH + ERROR_COMMON_GOAL_IMAGE,
                "The image of the common goal requested doesn't exists, an empty card will be displayed"
        );
    }

    /**
     * Adds a top scoring token with the specified value.
     *
     * @param topTokenValue the value of the top scoring token to add
     */
    public void addTopScoringToken(int topTokenValue) {

        ScoringTokenView oldScoringTokenView;

        try{
            oldScoringTokenView = scoringTokenViews.peek();
        }catch(EmptyStackException e) {
            oldScoringTokenView = null;
        }

        if(topTokenValue != 0)
            scoringTokenViews.push(new ScoringTokenView(topTokenValue));

        showTopToken(oldScoringTokenView);
    }

    /**
     * Adds the scoring tokens to the `CommonGoalView`.
     *
     * @param scoringTokens The scoring tokens to be added.
     */
    @Deprecated
    private void addScoringTokens(final Stack<Integer> scoringTokens) {

        if (scoringTokens != null && scoringTokens.size() > 0) {
            scoringTokens.forEach(this::addTopScoringToken);
        } else {
            MyShelfieAlertCreator.displayWarningAlert(
                    "No scoring tokens can be assigned to the common goal",
                    "Error in loading scoring token"
            );
        }
    }


    /**
     * Displays the top scoring token on the `CommonGoalView`.
     */
    private void showTopToken(ScoringTokenView scoringTokenView) {

        try{
            ScoringTokenView topToken = scoringTokenViews.peek();

            scoringTokenBox.getChildren().add(topToken);

            if (scoringTokenView != null)
                scoringTokenView.toFront();

            topToken.setRotate(SCORING_TOKEN_ROTATION);
        }catch(EmptyStackException ignored) {
            MyShelfieAlertCreator.displayInformationAlert(
                    "No common goal point can be obtained",
                    "All common goal point are peeked up"
            );
        }
    }

    /**
     * Retrieves the top scoring token from the `CommonGoalView`.
     *
     * @return The top scoring token.
     * @throws EmptyStackException If there are no scoring tokens available.
     */
    private ScoringTokenView getTopToken() throws EmptyStackException {
        ScoringTokenView topToken =  scoringTokenViews.pop();

        if(scoringTokenViews.size() > 0)
            showTopToken(null);

        return topToken;
    }

    /**
     * Moves the top scoring token to the specified destination pane.
     *
     * @param destinationPane The destination pane.
     * @throws EmptyStackException if there are no scoring tokens available.
     */
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

    /**
     * Displays the description of the common goal when clicked.
     *
     * @param mouseEvent The mouse event triggering the action.
     */
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
     * Retrieves the point value of the top scoring token in the `CommonGoalView`.
     *
     * @return the point value of the top scoring token
     * @throws EmptyStackException if there are no scoring tokens available
     */
    public int getTopTokenPoint() throws EmptyStackException {
        return scoringTokenViews.peek().getTokenPoint();
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
