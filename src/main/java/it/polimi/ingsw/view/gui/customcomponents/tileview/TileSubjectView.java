package it.polimi.ingsw.view.gui.customcomponents.tileview;

import it.polimi.ingsw.view.gui.customcomponents.decorations.*;
import it.polimi.ingsw.view.gui.customcomponents.uitoolkit.MyShelfieShadowType;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 3.0
 * @since 08/06/2023
 */
public class TileSubjectView extends Pane implements MyShelfieComponent {

    private static final String ITEM_TILE_PATH_FOLDER = "/it.polimi.ingsw/graphical.resources/item.tiles/";

    private Pane parent;

    private final String fileName;

    /**
     * Used to verify if the tile is clicked ({@code true}) or not ({@code false})
     */
    private boolean clicked;

    /**
     * Used to verify if the component is disabled ({@code true}) or not ({@code false})
     */
    private boolean disabled;

    /**
     * The current state of the tile item
     *
     * @see TileSubjectViewState
     */
    private TileSubjectViewState currentState;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     *
     * @param parent
     * @param fileName no extension, just name
     */
    public TileSubjectView(Pane parent, String fileName) {
        setParent(parent);

        this.fileName = fileName;

        this.currentState = new TileViewInBoard();

        this.clicked = false;
        this.disabled = false;

        setCSS();

        applyDecorationAsDefault(new MyShelfieDarkShadow(MyShelfieShadowType.SHORT), new MyShelfieRoundEdge(0.27));

        //?????!!!!
        parent.setStyle("-fx-padding: 0.1em;");

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        setOnMouseEntered(this::onMouseEnteredHandler);
        setOnMouseExited(this::onMouseExitedHandler);
        setOnMousePressed(this::onMousePressedHandler);
    }

    public TileSubjectView(StackPane parent) {
        this(parent, "error");
    }

    private void setParent(@NotNull Pane parent){
        this.parent = parent;
        parent.getChildren().add(this);
    }

    private void setCSS() {
        setStyle("-fx-padding: 1.9em;" +
                "-fx-background-image: url('" + getImageFile(fileName) + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;");
    }

    private URL getImageFile(String fileName) {

        URL fileURL = getClass().getResource(ITEM_TILE_PATH_FOLDER + fileName + ".png");

        if(fileURL == null) {
            System.err.println("Image source not found");
            fileURL = getClass().getResource("/it.polimi.ingsw/graphical.resources/tile.type/error_tile.png");
        }

        return fileURL;
    }

    private void onMouseEnteredHandler(MouseEvent mouseEvent) {
        if(!clicked && !disabled){
            applyDecorationFromZero(new MyShelfieLightShadow(), new MyShelfieInnerShadow());
        }
    }

    private void onMouseExitedHandler(MouseEvent mouseEvent) {
        if(!clicked && !disabled){
            resetToDefaultDecorations();
        }
    }

    protected void onMousePressedHandler(MouseEvent mouseEvent) {
        if(!disabled){

            if(clicked) {
                resetToDefaultDecorations();
            } else {
                applyDecoration(new MyShelfieGlow());
            }

            clicked = !clicked;
        }
    }

    protected void setOnMouseClickedEffect() {
        setEffect(null);
        setEffect(new Glow(0.2));
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    /**
     * if the tile was clicked and not disabled, set the tile view to be clickable and reset to default effect
     */
    public void resetClick() {
        if(clicked && !disabled) {
            clicked = false;
            resetToDefaultDecorations();
        }
    }

    /**
     * @return true if the tile is clicked, false otherwise
     */
    public boolean isClicked() {
        return clicked;
    }

    protected Pane getParentPane() {
        return parent;
    }

    private void enable() {
        disabled = false;
    }

    /**
     * Set the button to be clickable and enabled
     */
    private void resetInteractionState() {
        enable();
        resetClick();
    }

    private void changeParent(Pane newParent) {
        this.parent.getChildren().remove(this);

        resetInteractionState();

        setParent(newParent);

        resetToDefaultDecorations();
    }

    protected void changeParent(Pane newParent, double x, double y) {
        if(!disabled && !clicked){
            changeParent(newParent);

            setTranslateX(x);
            setTranslateY(y);
        }
    }

    protected void setCurrentState(TileSubjectViewState currentState) {
        this.currentState = currentState;
    }

    public void performAction(Pane... panes) {
        if(!disabled){
            clicked = false;

            currentState.tileStateAction(this, panes);

            resetToDefaultDecorations();
        }
    }

    public void reverseAction() {
        if(!disabled){
            clicked = false;

            currentState.reverseStateAction(this);
        }
    }

    public void disable() {
        disabled = true;

        applyDecoration(new MyShelfieObscured());
    }

    public void setClickable() {
        if(disabled) {
            resetInteractionState();
            resetToDefaultDecorations();
        }
    }

    protected PathTransition createPathTransition(Pane... destinationPanes) {
        Bounds boundsInScene = localToScene(getBoundsInLocal());

        List<Bounds> destinationBounds =
                Arrays.stream(destinationPanes)
                        .map(destinationPane -> destinationPane.localToScene(destinationPane.getBoundsInLocal()))
                        .toList();

        MoveTo start = new MoveTo((boundsInScene.getWidth() / 2 - getPadding().getLeft() / 2), (boundsInScene.getHeight() / 2 - getPadding().getTop() / 2));

        List<PathElement> lineTos = new ArrayList<>(List.of(start));

        destinationBounds.forEach( destinationBound -> lineTos.add(createCenterLineTo(boundsInScene, destinationBound)));

        Path transitionPath = new Path(lineTos);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(this);
        pathTransition.setDuration(Duration.seconds(1));
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.setPath(transitionPath);

        return pathTransition;
    }

    private LineTo createCenterLineTo(Bounds startingBounds, Bounds endingBounds) {
        double endX = endingBounds.getMinX() - startingBounds.getMinX() + (endingBounds.getWidth() / 2.0 - (getPadding().getLeft() / 2));
        double endY = endingBounds.getMinY() - startingBounds.getMinY() + (endingBounds.getHeight() / 2.0 - (getPadding().getTop() / 2));

        return new LineTo(endX, endY);
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
     * Retrieves the set of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the set
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return this.baseDecorations;
    }
}
