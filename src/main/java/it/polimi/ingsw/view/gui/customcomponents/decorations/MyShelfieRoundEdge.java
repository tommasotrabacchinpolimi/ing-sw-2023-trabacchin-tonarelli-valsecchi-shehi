package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * <p>{@code MyShelfieRoundEdge} effect is one of the
 * {@linkplain MyShelfieDecoration decorations} created
 * for custom components of the graphical user interface.</p>
 *
 * <p>This effect modifies the shape of the desired
 * {@linkplain MyShelfieComponent component} to have rounder
 * border even if it has an image as background</p>
 *
 * @apiNote This effect is applied only once, and can't
 * be removed, or it will result in a bad user experience
 *
 * @see javafx.scene.shape.Shape
 */
public class MyShelfieRoundEdge implements MyShelfieDecoration {

    /**
     * Default-edge radius for the component
     */
    public static final double RADIUS = 0.57;

    /**
     * The actual radius applied at the corners
     * of the component
     */
    private final double radius;

    /**
     * A boolean value that is used to know if the round
     * on the corner has been applied
     */
    private boolean applied;

    /**
     * <p>Creates a new instance of {@code MyShelfieRoundEdge}
     * with standard {@linkplain #RADIUS radius} edge</p>
     */
    public MyShelfieRoundEdge() {
        this(RADIUS);
    }

    /**
     * <p>Creates a new instance of {@code MyShelfieRoundEdge}
     * with specified {@code radius} edge</p>
     *
     * @param radius the desired radius applied at the edges
     */
    public MyShelfieRoundEdge(double radius) {
        this.radius = radius;
        this.applied = false;
    }

    /**
     * Applies a round edge on the corner of a desired component
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(MyShelfieComponent decoratedComponent) {

        if(applied)
            return;

        Rectangle clipper = new Rectangle();

        decoratedComponent.getCustomizedNode().layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            double height = newValue.getHeight();
            double width = newValue.getWidth();
            double minSize = Math.min(width, height);

            clipper.setHeight(height);
            clipper.setWidth(width);
            clipper.setArcWidth(minSize * radius);
            clipper.setArcHeight(minSize * radius);
        });

        try{
            applied = true;
            ((Region) decoratedComponent.getCustomizedNode()).setShape(clipper);
        }catch (ClassCastException e){
            System.err.println("Component " + decoratedComponent.getCustomizedNode().getId() + "can't have rounded border");
        }
    }
}
