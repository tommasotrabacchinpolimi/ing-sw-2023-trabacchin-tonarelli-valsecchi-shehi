package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.view.gui.customcomponents.decorations.*;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType.SHORT_DURATION;

/**
 * A custom triangular button component in the Shelfie GUI.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 */
public class MyShelfieTriangleButton extends StackPane implements MyShelfieComponent {

    /**
     * The file path to the triangle image used for the button.
     */
    private static final String TRIANGLE_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/misc/arrow_bookshelf_button.png";

    /**
     * Flag indicating whether the button is currently in the "entered" state.
     */
    private boolean entered;

    /**
     * Flag indicating whether the button is currently in the "clicked" state.
     */
    private boolean clicked;

    /**
     * Flag indicating whether the button is currently in the "active" state.
     */
    private boolean active;

    /**
     * The pane that contains the icon for the button.
     */
    private final Pane iconPane;

    /**
     * The path transition animation used for the button's icon.
     */
    private PathTransition pathTransition;

    /**
     * The list of base decorations applied to the button as default.
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();


    /**
     * Creates a MyShelfieTriangleButton with default settings.
     * It sets up the required interactions, style, and initializes the icon pane.
     */
    public MyShelfieTriangleButton() {
        setDefaultInteractionsProperty();

        setStyle("-fx-background-color: transparent;");

        iconPane = new MyShelfieGraphicIcon(TRIANGLE_IMAGE_PATH, 1, null, "contain");
        getChildren().add(iconPane);

        applyDecorationAsDefault(new MyShelfieDarkShadow());

        initPathTransition();

        setOnMouseEntered(this::handleMouseEnter);
        setOnMouseExited(this::handleMouseExited);
        setOnMousePressed(this::handleMousePressed);
        setOnMouseReleased(this::handleMouseReleased);

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }


    /**
     * Initializes the PathTransition for the MyShelfieTriangleButton.
     * It creates a new PathTransition instance and stops it.
     */
    private void initPathTransition() {
        pathTransition = new PathTransition();
        pathTransition.stop();
    }

    /**
     * Sets the default interaction properties for the MyShelfieTriangleButton.
     * It sets the entered, clicked, and active flags to their default values.
     */
    private void setDefaultInteractionsProperty() {
        this.entered = false;
        this.clicked = false;
        this.active = true;
    }

    /**
     * Event handler for the mouse exit event.
     * It handles the behavior when the mouse exits the MyShelfieTriangleButton.
     * If the button is active and not clicked, it resets to default decorations and reverses the path transition if not entered.
     *
     * @param mouseEvent The MouseEvent associated with the mouse exit event.
     */
    private void handleMouseExited(MouseEvent mouseEvent) {
        if (active) {
            entered = false;
            if (!clicked) {
                resetToDefaultDecorations();
                if (!entered)
                    reversePathTransition();
            }
        }
    }

    /**
     * Event handler for the mouse enter event.
     * It handles the behavior when the mouse enters the MyShelfieTriangleButton.
     * If the button is active and not clicked, it applies the MyShelfieBloom decoration and initiates the path transition.
     *
     * @param mouseEvent The MouseEvent associated with the mouse enter event.
     */
    private void handleMouseEnter(MouseEvent mouseEvent) {
        if (active) {
            entered = true;
            if (!clicked) {
                applyDecoration(new MyShelfieBloom());
                doPathTransition();
            }
        }
    }

    /**
     * Event handler for the mouse pressed event.
     * It handles the behavior when the mouse is pressed on the MyShelfieTriangleButton.
     * If the button is active, it toggles the clicked flag and applies the MyShelfieGlow decoration if not clicked.
     *
     * @param mouseEvent The MouseEvent associated with the mouse pressed event.
     */
    private void handleMousePressed(MouseEvent mouseEvent) {
        if (active) {
            clicked = !clicked;
            if (!clicked)
                applyDecoration(new MyShelfieGlow());
        }
    }

    /**
     * Event handler for the mouse released event.
     * It handles the behavior when the mouse is released on the MyShelfieTriangleButton.
     * If the button is active and not clicked, it resets to default decorations.
     *
     * @param mouseEvent The MouseEvent associated with the mouse released event.
     */
    private void handleMouseReleased(MouseEvent mouseEvent) {
        if (active && !clicked)
            resetToDefaultDecorations();
    }
    /**
     * Initiates a path transition animation for the MyShelfieTriangleButton.
     * It creates a PathTransition with a specified path and properties,
     * and starts the animation.
     * The animation moves the iconPane from its initial position to a final position.
     */
    private void doPathTransition() {
        MoveTo start = new MoveTo(iconPane.getWidth() / 2, iconPane.getHeight() / 2);
        LineTo lineTo = new LineTo(iconPane.getWidth() / 2, iconPane.getHeight());

        pathTransition = new PathTransition();
        pathTransition.setNode(iconPane);
        pathTransition.setDuration(SHORT_DURATION.getDuration());
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setCycleCount(2);
        pathTransition.setAutoReverse(true);
        pathTransition.setPath(new Path(start, lineTo));

        pathTransition.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (pathTransition.getCurrentTime().equals(pathTransition.getCycleDuration()) && entered) {
                pathTransition.pause();
            }
        });

        pathTransition.play();
    }

    /**
     * Sets the MyShelfieTriangleButton to an inactive state.
     * It updates the interaction properties, sets the active flag to false,
     * resets the decorations to default, applies the MyShelfieOpaque decoration,
     * and reverses the path transition.
     */
    public void setInactive() {
        if (!active) // if it is already inactive, do not set inactive again
            return;

        setDefaultInteractionsProperty();

        this.active = false;

        resetToDefaultDecorations();
        applyDecoration(new MyShelfieOpaque());

        reversePathTransition();
    }

    /**
     * Sets the MyShelfieTriangleButton to an active state.
     * It updates the interaction properties and applies the MyShelfieOpaque decoration.
     */
    public void setActive() {
        if (active)
            return;

        setDefaultInteractionsProperty();

        applyDecoration(new MyShelfieOpaque(1.0));
    }

    /**
     * Checks if the MyShelfieTriangleButton is active.
     * @return {@code true} if the button is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Reverses the state of the MyShelfieTriangleButton.
     * It checks the current opacity and applies the MyShelfieOpaque decoration if necessary,
     * updates the interaction properties, resets the decorations to default,
     * and reverses the path transition.
     */
    public void reverseState() {
        if (getOpacity() < 1.0)
            applyDecoration(new MyShelfieOpaque(1.0));

        setDefaultInteractionsProperty();
        resetToDefaultDecorations();
        reversePathTransition();
    }

    /**
     * Reverses the path transition animation.
     * If the path transition is not stopped, it plays the animation in reverse.
     */
    private void reversePathTransition() {
        if (!pathTransition.getStatus().equals(Animation.Status.STOPPED)) {
            pathTransition.play();
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
     * Retrieves the set of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the set
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
