package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.effect.Bloom;
import org.jetbrains.annotations.NotNull;

/**
 * <p>{@code MyShelfieBloom} effect is one of the
 * {@linkplain MyShelfieDecoration decorations} created
 * for custom components of the graphical user interface.</p>
 *
 * <p>A high-level effect that makes brighter portions of the
 * input image appear to glow, based on a configurable threshold</p>
 *
 * <p>The glow can be applied:
 * <ul>
 *     <li>with a standard threshold ({@value THRESHOLD})</li>
 *     <li>with a personalized threshold ({@link #MyShelfieBloom(double threashold)})</li>
 * </ul></p>
 *
 * @see Bloom
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi

 */
public class MyShelfieBloom implements MyShelfieDecoration {
    /**
     * Standard threshold value for "MyShelfie" style
     */
    private static final double THRESHOLD = 0.2;

    /**
     * The bloom effect applied to the desired {@linkplain MyShelfieComponent component}
     */
    private final Bloom bloom;

    /**
     * <p>Creates a new instance of bloom with the specified threshold</p>
     *
     * @param threshold the threshold value for the bloom effect
     */
    public MyShelfieBloom(double threshold) {
        bloom = new Bloom(threshold);
    }

    /**
     * <p>Creates a new instance of bloom with standard {@linkplain #THRESHOLD threshold}</p>
     */
    public MyShelfieBloom() {
        this(THRESHOLD);
    }

    /**
     * Applies a bloom effect on the desired component
     *
     * @param decoratedComponent the {@linkplain MyShelfieComponent component} that needs to be customized
     */
    @Override
    public void customize(@NotNull MyShelfieComponent decoratedComponent) {
        bloom.setInput(null);

        bloom.setInput(decoratedComponent.getCustomizedNode().getEffect());

        decoratedComponent.getCustomizedNode().effectProperty().set(bloom);
    }
}
