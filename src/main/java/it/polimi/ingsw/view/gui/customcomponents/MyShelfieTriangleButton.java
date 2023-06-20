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

public class MyShelfieTriangleButton extends StackPane implements MyShelfieComponent {

    private static final String TRIANGLE_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/misc/arrow_bookshelf_button.png";

    private boolean entered;

    private boolean clicked;

    private boolean active;

    private final Pane iconPane;

    private PathTransition pathTransition;

    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Creates a Pane layout.
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

    private void initPathTransition(){
        pathTransition = new PathTransition();
        pathTransition.stop();
    }

    private void setDefaultInteractionsProperty() {
        this.entered = false;
        this.clicked = false;
        this.active = true;
    }

    private void handleMouseExited(MouseEvent mouseEvent) {
        if(active){
            entered = false;

            if(!clicked){
                resetToDefaultDecorations();

                if(!entered)
                    reversePathTransition();
            }
        }
    }

    private void handleMouseEnter(MouseEvent mouseEvent) {
        if(active){
            entered = true;

            if(!clicked){
                applyDecoration(new MyShelfieBloom());

                doPathTransition();
            }
        }
    }

    private void handleMousePressed(MouseEvent mouseEvent) {
        if(active){
            clicked = !clicked;

            if (!clicked)
                applyDecoration(new MyShelfieGlow());
        }
    }

    private void handleMouseReleased(MouseEvent mouseEvent) {
        if(active && !clicked)
            resetToDefaultDecorations();
    }

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

    public void setInactive() {
        if(!active) //if it is already inactive do not set inactive again
            return;

        setDefaultInteractionsProperty();

        this.active = false;

        resetToDefaultDecorations();
        applyDecoration(new MyShelfieOpaque());

        reversePathTransition();
    }

    public void setActive() {
        if(active)
            return;

        setDefaultInteractionsProperty();

        applyDecoration(new MyShelfieOpaque(1.0));
    }

    /**
     *
     * @return {@code true} if it is active {@code false} otherwise
     */
    public boolean isActive() {
        return active;
    }

    public void reverseState() {

        if(getOpacity() < 1.0)
            applyDecoration(new MyShelfieOpaque(1.0));

        setDefaultInteractionsProperty();
        resetToDefaultDecorations();
        reversePathTransition();
    }

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
