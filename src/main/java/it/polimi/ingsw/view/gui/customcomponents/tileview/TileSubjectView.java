package it.polimi.ingsw.view.gui.customcomponents.tileview;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieAnimation;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieAnimationCombineLogic;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfiePathTransition;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieScaleTransition;
import it.polimi.ingsw.view.gui.customcomponents.decorations.*;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieShadowType;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 /**
 * Represents a graphical view of a tile subject in the game.
 * This class extends the JavaFX `Pane` class and implements the `MyShelfieComponent` interface.
 * It provides methods to handle the visualization and interaction with a tile subject.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 * @version 3.0
 * @since 08/06/2023
 */
public class TileSubjectView extends Pane implements MyShelfieComponent {

    private static final String ITEM_TILE_PATH_FOLDER = "/it.polimi.ingsw/graphical.resources/item.tiles/";

    private static final String ERROR_ITEM_TILE_PATH = "/it.polimi.ingsw/graphical.resources/tile.type/error_tile.png";

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

    /**
     * Constructs a new `TileSubjectView` instance with the specified `TileSubject`.
     *
     * @param tileSubject The tile subject associated with this view.
     */
    public TileSubjectView(TileSubject tileSubject) {
        this(tileSubject, new TileViewInBoard());
    }

    public TileSubjectView(TileSubject tileSubject, TileSubjectViewState tileSubjectViewState) {
        this.currentState = tileSubjectViewState;

        this.tileSubject = tileSubject;

        this.clicked = false;
        this.disabled = false;

        setCSS(currentState.getPadding());

        applyDecorationAsDefault(new MyShelfieDarkShadow(MyShelfieShadowType.SHORT), new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL));

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        setOnMouseEntered(this::onMouseEnteredHandler);
        setOnMouseExited(this::onMouseExitedHandler);
        setOnMousePressed(this::onMousePressedHandler);
    }

    /**
     * Constructs a new `TileSubjectView` instance with the specified `TileSubject` and parent `Pane`.
     *
     * @param parent      The parent `Pane` to attach this view to.
     * @param tileSubject The tile subject associated with this view.
     */
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
                "-fx-background-image: url('" + getMyShelfieResource(ITEM_TILE_PATH_FOLDER + tileSubject.toString().toLowerCase() + ".png",
                ERROR_ITEM_TILE_PATH, "Can't load item tile image") + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;");
    }

    protected void updatedCSS() {
        setCSS(currentState.getPadding());
        resetScaleProperties();
    }

    protected void resetCSS() {
        setCSS(currentState.getPadding());
        resetScaleProperties();
    }

    private void resetScaleProperties() {
        setScaleProperties(1.0, 1.0);
    }

    private void setScaleProperties(double x, double y) {
        setScaleX(x);
        setScaleY(y);
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

    public void toOpponentBookShelf(Pane... panes){
        disableClick();
        clicked = false;

        currentState = new TileViewInOpponent();

        currentState.tileStateAction(this, panes);

        resetToDefaultDecorations();
    }

    public void disable() {
        if(!disabled){
            disableClick();

            applyDecoration(new MyShelfieObscured());
        }
    }

    public void disableClick() {
        disabled = true;
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

    protected Transition createToSmallerPathTransition(Pane... destinationPanes) {
        return MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(this, destinationPanes))
                .addAnimation(new MyShelfieScaleTransition(1.0, 1.0, 0.71, 0.71))
                .setCombineLogic(MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION)
                .buildAnimation(this);
    }

    protected Transition createToBiggerPathTransition(Pane... destinationPanes) {
        return MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(this, destinationPanes))
                .addAnimation(new MyShelfieScaleTransition(1.0, 1.0, 1.4, 1.4))
                .setCombineLogic(MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION)
                .buildAnimation(this);
    }

    protected Transition createPathTransition(Pane... destinationPanes) {
        return MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(this, destinationPanes))
                .setCombineLogic(MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION)
                .buildAnimation(this);
    }

    public TileSubject getTileSubject() {
        return tileSubject;
    }

    public boolean isEqualTileSubject(TileSubject otherTileSubject) {
        return otherTileSubject == this.tileSubject;
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
