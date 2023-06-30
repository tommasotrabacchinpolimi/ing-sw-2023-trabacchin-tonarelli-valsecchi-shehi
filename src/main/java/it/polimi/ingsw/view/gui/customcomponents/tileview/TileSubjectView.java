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

    /**
     * Item Tile Folder path
     */
    private static final String ITEM_TILE_PATH_FOLDER = "/it.polimi.ingsw/graphical.resources/item.tiles/";

    /**
     * Error item file path
     */
    private static final String ERROR_ITEM_TILE_PATH = "/it.polimi.ingsw/graphical.resources/tile.type/error_tile.png";

    /**
     * Initial Padding
     */
    private static final double INITIAL_PADDING = 2.8;

    /**
     * Parent of the Tile
     */
    private Pane parent;

    /**
     * Represent a Tile Subject that will be show
     */
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

    /**
     * Constructs a new `TileSubjectView` instance with the specified `TileSubject`.
     *
     * @param tileSubject The tile subject associated with this view.
     */
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

    /**
     * Method to set Parent
     * @param parent the parent element of TileSubject
     */
    private void setParent(@NotNull Pane parent){
        this.parent = parent;
        parent.getChildren().add(this);
    }

    /**
     * Sets the CSS properties of the component with the specified padding and background image.
     *
     * @param padding       The padding value to be set.
     */
    private void setCSS(double padding) {
        setStyle("-fx-padding: " + padding + "em;" +
                "-fx-background-image: url('" + getMyShelfieResource(ITEM_TILE_PATH_FOLDER + tileSubject.toString().toLowerCase() + ".png",
                ERROR_ITEM_TILE_PATH, "Can't load item tile image") + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;");
    }

    /**
     * Updates the CSS properties of the component with the specified padding and resets the scale properties.
     */
    protected void updatedCSS() {
        setCSS(currentState.getPadding());
        resetScaleProperties();
    }


    /**
     * Resets the CSS properties of the component to their initial values and resets the scale properties.
     */
    protected void resetCSS() {
        setCSS(currentState.getPadding());
        resetScaleProperties();
    }

    /**
     * Resets the scale properties of the component to their default values.
     */
    private void resetScaleProperties() {
        setScaleProperties(1.0, 1.0);
    }

    /**
     * Sets the scale properties of the component to the specified values.
     *
     * @param x The scale factor in the x-axis.
     * @param y The scale factor in the y-axis.
     */
    private void setScaleProperties(double x, double y) {
        setScaleX(x);
        setScaleY(y);
    }

    /**
     * Applies highlight effect decorations to the component.
     */
    private void highlightEffect() {
        applyDecorationFromZero(new MyShelfieLightShadow(), new MyShelfieInnerShadow(), new MyShelfieRustBorder());
    }


    /**
     * Event handler triggered when the mouse enters the component.
     * If the component is not clicked or disabled, it applies the highlight effect.
     *
     * @param mouseEvent The MouseEvent triggering the event.
     */
    private void onMouseEnteredHandler(MouseEvent mouseEvent) {
        if(!clicked && !disabled){
            highlightEffect();
        }
    }

    /**
     * Event handler triggered when the mouse exits the component.
     * If the component is not clicked or disabled, it resets to default decorations.
     *
     * @param mouseEvent The MouseEvent triggering the event.
     */
    private void onMouseExitedHandler(MouseEvent mouseEvent) {
        if(!clicked && !disabled){
            resetToDefaultDecorations();
        }
    }

    /**
     * Event handler triggered when the mouse is pressed on the component.
     * If the component is not disabled, it applies the appropriate decoration based on the clicked state.
     * It also toggles the clicked state.
     *
     * @param mouseEvent The MouseEvent triggering the event.
     */
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

    /**
     * Sets the clicked state of the component.
     *
     * @param clicked The clicked state to set.
     */
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

    /**
     * Method to get the parent
     * @return The parent of the Tile Component
     */
    protected Pane getParentPane() {
        return parent;
    }

    /**
     * Method to Enable the Component
     */
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

    /**
     * Changes the parent of the component to the specified newParent.
     * Removes the component from the current parent, resets the interaction state,
     * sets the new parent, and resets the decorations to default.
     *
     * @param newParent The new parent pane to set.
     */
    private void changeParent(Pane newParent) {
        this.parent.getChildren().remove(this);

        resetInteractionState();

        setParent(newParent);

        resetToDefaultDecorations();
    }

    /**
     * Changes the parent of the component to the specified newParent, and sets the translation coordinates.
     * If the component is not disabled and not clicked, it calls the changeParent method,
     * sets the translation coordinates, and updates the visual position of the component.
     *
     * @param newParent The new parent pane to set.
     * @param x         The x-coordinate translation.
     * @param y         The y-coordinate translation.
     */
    public void changeParent(Pane newParent, double x, double y) {
        if(!disabled && !clicked){
            changeParent(newParent);

            setTranslateX(x);
            setTranslateY(y);
        }
    }

    /**
     * Sets the current state of the TileSubjectView to the specified currentState.
     *
     * @param currentState The new current state to set.
     */
    protected void setCurrentState(TileSubjectViewState currentState) {
        this.currentState = currentState;
    }

    /**
     * Performs the action associated with the current state of the TileSubjectView.
     * If the component is not disabled, it sets the clicked state to false,
     * calls the tileStateAction method of the current state passing the component and the specified panes,
     * and resets the decorations to default.
     *
     * @param panes The panes to pass to the tileStateAction method.
     */
    public void performAction(Pane... panes) {
        if(!disabled){
            clicked = false;

            currentState.tileStateAction(this, panes);

            resetToDefaultDecorations();
        }
    }

    /**
     * Reverses the action associated with the current state of the TileSubjectView.
     * If the component is not disabled, it sets the clicked state to false
     * and calls the reverseStateAction method of the current state passing the component.
     */

    public void reverseAction() {
        if(!disabled){
            clicked = false;

            currentState.reverseStateAction(this);
        }
    }

    /**
     * Moves the TileSubjectView to the opponent's bookshelf by performing the action associated with the opponent's state.
     * Sets the component as disabled and clicked to false, updates the current state to TileViewInOpponent,
     * calls the tileStateAction method of the current state passing the component and the specified panes,
     * and resets the decorations to default.
     *
     * @param panes The panes to pass to the tileStateAction method.
     */
    public void toOpponentBookShelf(Pane... panes){
        disableClick();
        clicked = false;

        currentState = new TileViewInOpponent();

        currentState.tileStateAction(this, panes);

        resetToDefaultDecorations();
    }

    /**
     * Disables the TileSubjectView by setting the disabled flag to true and applying the MyShelfieObscured decoration.
     * If the component is not already disabled, it disables the component and applies the decoration.
     */
    public void disable() {
        if(!disabled){
            disableClick();

            applyDecoration(new MyShelfieObscured());
        }
    }

    /**
     * method to disable click
     */
    public void disableClick() {
        disabled = true;
    }

    /**
     * Sets the TileSubjectView as clickable by resetting the interaction state and decorations.
     * If the component is disabled, it resets the interaction state and decorations to make it clickable again.
     */
    public void setClickable() {
        if(disabled) {
            resetInteractionState();
            resetToDefaultDecorations();
        }
    }

    /**
     * Checks if the TileSubjectView is enabled or disabled.
     *
     * @return true if the component is enabled, false if it is disabled.
     */
    public boolean isEnabled() {
        return !disabled;
    }

    /**
     * Creates a transition animation to scale down the TileSubjectView and move it along a path to the specified destination panes.
     *
     * @param destinationPanes The destination panes to move the component towards.
     * @return The transition animation.
     */
    protected Transition createToSmallerPathTransition(Pane... destinationPanes) {
        return MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(this, destinationPanes))
                .addAnimation(new MyShelfieScaleTransition(1.0, 1.0, 0.71, 0.71))
                .setCombineLogic(MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION)
                .buildAnimation(this);
    }

    /**
     * Creates a transition animation to scale up the TileSubjectView and move it along a path to the specified destination panes.
     *
     * @param destinationPanes The destination panes to move the component towards.
     * @return The transition animation.
     */
    protected Transition createToBiggerPathTransition(Pane... destinationPanes) {
        return MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(this, destinationPanes))
                .addAnimation(new MyShelfieScaleTransition(1.0, 1.0, 1.4, 1.4))
                .setCombineLogic(MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION)
                .buildAnimation(this);
    }

    /**
     * Creates a transition animation to move the TileSubjectView along a path to the specified destination panes.
     *
     * @param destinationPanes The destination panes to move the component towards.
     * @return The transition animation.
     */
    protected Transition createPathTransition(Pane... destinationPanes) {
        return MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(this, destinationPanes))
                .setCombineLogic(MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION)
                .buildAnimation(this);
    }

    /**
     * Method to get Tile Subject of the Tile
     * @return The Tile Subject
     */
    public TileSubject getTileSubject() {
        return tileSubject;
    }

    /**
     * Checks if the TileSubjectView has the same TileSubject as the specified otherTileSubject.
     *
     * @param otherTileSubject The other TileSubject to compare.
     * @return true if the TileSubjectView has the same TileSubject, false otherwise.
     */
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
