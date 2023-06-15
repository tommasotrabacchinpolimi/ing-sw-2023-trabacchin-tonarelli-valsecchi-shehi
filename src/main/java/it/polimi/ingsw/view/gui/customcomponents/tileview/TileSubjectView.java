package it.polimi.ingsw.view.gui.customcomponents.tileview;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.view.gui.customcomponents.decorations.*;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieShadowType;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransition.DEF_DURATION;

/**
 * @version 3.0
 * @since 08/06/2023
 */
public class TileSubjectView extends Pane implements MyShelfieComponent {

    private static final String ITEM_TILE_PATH_FOLDER = "/it.polimi.ingsw/graphical.resources/item.tiles/";

    private static final double INITIAL_PADDING = 2.8;

    private Pane parent;

    private final TileSubject tileSubject;

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

    public TileSubjectView(TileSubject tileSubject) {
        this.currentState = new TileViewInBoard();

        this.tileSubject = tileSubject;

        this.clicked = false;
        this.disabled = false;

        setCSS(INITIAL_PADDING);

        applyDecorationAsDefault(new MyShelfieDarkShadow(MyShelfieShadowType.SHORT), new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL));

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        setOnMouseEntered(this::onMouseEnteredHandler);
        setOnMouseExited(this::onMouseExitedHandler);
        setOnMousePressed(this::onMousePressedHandler);
    }

    public TileSubjectView(Pane parent, TileSubject tileSubject) {
        this(tileSubject);

        setParent(parent);
    }

    private void setParent(@NotNull Pane parent){
        this.parent = parent;
        parent.getChildren().add(this);
    }

    private void setCSS(double padding) {
        setStyle("-fx-padding: " + padding + "em;" +
                "-fx-background-image: url('" + getImageFile(tileSubject.toString().toLowerCase()) + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;");
    }

    protected void updatedCSS(double padding) {
        setCSS(padding);
        resetScaleProperties();
    }

    protected void resetCSS() {
        setCSS(INITIAL_PADDING);
        resetScaleProperties();
    }

    private void resetScaleProperties() {
        setScaleProperties(1.0, 1.0);
    }

    private void setScaleProperties(double x, double y) {
        setScaleX(x);
        setScaleY(y);
    }

    private URL getImageFile(String fileName) {

        URL fileURL = getClass().getResource(ITEM_TILE_PATH_FOLDER + fileName + ".png");

        if(fileURL == null) {
            System.err.println("Image source not found");
            fileURL = getClass().getResource("/it.polimi.ingsw/graphical.resources/tile.type/error_tile.png");
        }

        return fileURL;
    }

    private void highlightEffect() {
        applyDecorationFromZero(new MyShelfieLightShadow(), new MyShelfieInnerShadow(), new MyShelfieRustBorder());
    }

    private void onMouseEnteredHandler(MouseEvent mouseEvent) {
        if(!clicked && !disabled){
            highlightEffect();
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
                highlightEffect();
            } else {
                applyDecoration(new MyShelfieGlow(0.4));
            }

            clicked = !clicked;
        }
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

    public void changeParent(Pane newParent, double x, double y) {
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
        if(!disabled){
            disabled = true;

            applyDecoration(new MyShelfieObscured());
        }
    }

    public void setClickable() {
        if(disabled) {
            resetInteractionState();
            resetToDefaultDecorations();
        }
    }

    public boolean isEnabled() {
        return !disabled;
    }

    protected ParallelTransition createToSmallerPathTransition(Pane... destinationPanes) {
        return new ParallelTransition(this, createPathTransition(false, destinationPanes), createToLowerBoundsTransition());
    }

    protected ParallelTransition createToBiggerPathTransition(Pane... destinationPanes) {
        return new ParallelTransition(this, createPathTransition(false, destinationPanes), createToBiggerBoundsTransition());
    }

    protected PathTransition createPathTransition(Pane... destinationPanes) {
        return createPathTransition(true, destinationPanes);
    }

    @NotNull
    private PathTransition createPathTransition(boolean apply, Pane... destinationPanes) {
        Bounds boundsInScene = localToScene(getBoundsInLocal());

        List<Bounds> destinationBounds =
                Arrays.stream(destinationPanes)
                        .map(destinationPane -> destinationPane.localToScene(destinationPane.getBoundsInLocal()))
                        .toList();

        MoveTo start = new MoveTo(getWidth() / 2, getHeight() / 2);

        List<PathElement> lineTos = new ArrayList<>(List.of(start));

        destinationBounds.forEach( destinationBound -> lineTos.add(createCenterLineTo(boundsInScene, destinationBound)));

        Path transitionPath = new Path(lineTos);

        PathTransition pathTransition = new PathTransition();

        if(apply)
            pathTransition.setNode(this);

        pathTransition.setDuration(DEF_DURATION.getDuration());
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.setPath(transitionPath);

        return pathTransition;
    }

    protected ScaleTransition createToLowerBoundsTransition() {
        return createScaleTransition(1.0, 1.0, 0.71, 0.71);
    }

    protected ScaleTransition createToBiggerBoundsTransition() {
        return createScaleTransition(1.0, 1.0, 1.4, 1.4);
    }

    @NotNull
    private ScaleTransition createScaleTransition(double fromX, double fromY, double toX, double toY) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(DEF_DURATION.getDuration());
        scaleTransition.setInterpolator(Interpolator.LINEAR);
        scaleTransition.setFromX(fromX);
        scaleTransition.setFromY(fromY);
        scaleTransition.setToX(toX);
        scaleTransition.setToY(toY);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);

        return scaleTransition;
    }

    public TileSubject getTileSubject() {
        return tileSubject;
    }

    @NotNull
    private LineTo createCenterLineTo(@NotNull Bounds startingBounds, @NotNull Bounds endingBounds) {
        double endX = endingBounds.getMinX() - startingBounds.getMinX() - (startingBounds.getWidth() - getWidth()) / 2.0 + (endingBounds.getWidth() / 2.0);
        double endY = endingBounds.getMinY() - startingBounds.getMinY() - (startingBounds.getHeight() - getHeight()) / 2.0 + (endingBounds.getHeight() / 2.0);

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
