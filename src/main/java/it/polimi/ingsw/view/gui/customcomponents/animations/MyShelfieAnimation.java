package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION;

/**
 * Use to build an animation with "My Shelfie" style
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieAnimation {

    /**
     * The list of animation that will be applied on
     * a node element
     */
    private final List<Transition> myShelfieAnimation = new ArrayList<>();

    /**
     * <p>Specify the combine logic of different
     * animations on a node.</p>
     *
     * @apiNote The allowed combine logic for the
     * application are specified in
     * {@link MyShelfieAnimationCombineLogic}
     * class.
     */
    private MyShelfieAnimationCombineLogic combineLogic;

    /**
     * Default constructor
     *
     * @apiNote Private constructor to enforce the use
     * of the builder
     */
    private MyShelfieAnimation() {
    }

    /**
     * Transform the list of animation into an
     * array of the right size
     *
     * @return the array of animation that will
     * be applied on a node
     */
    @NotNull
    private Animation[] getArrayAnimations() {
        return myShelfieAnimation.toArray(new Animation[0]);
    }

    /**
     * static method that is used to build the
     * "animation chain" to apply at a node
     *
     * @return {@code new} {@link MyShelfieAnimation.Builder}
     * instance
     */
    @NotNull
    @Contract(" -> new")
    public static MyShelfieAnimation.Builder build() {
        return new MyShelfieAnimation.Builder();
    }

    /**
     * Static enclosing class to construct the
     * chain of animations to apply at a node
     */
    public static class Builder {

        /**
         * Reference to the main class that is holding
         * the chain of animation to apply at a node
         */
        private final MyShelfieAnimation instance;

        /**
         * Private constructor to enforce the use of
         * {@link MyShelfieAnimation#build()} method
         */
        private Builder() {
            instance = new MyShelfieAnimation();
        }

        /**
         * Adds an animation to the chain of
         * transitions that will be applied on a
         * node
         *
         * @param transition the transition that
         *                   will be applied on a
         *                   node
         * @return the instance of
         * {@link MyShelfieAnimation.Builder builder}
         * that is creating the animation
         */
        public MyShelfieAnimation.Builder addAnimation(Transition transition) {
            instance.myShelfieAnimation.add(transition);
            return this;
        }

        /**
         * Adds a "My Shelfie" animation to the chain of transitions
         * that will be applied on a node
         *
         * @param myShelfieTransition the transition that will be
         *                            applied on a node
         * @return the instance of {@link MyShelfieAnimation.Builder
         * builder} that is creating the animation
         */
        public MyShelfieAnimation.Builder addAnimation(@NotNull MyShelfieTransition myShelfieTransition) {
            addAnimation(myShelfieTransition.getTransition());
            return this;
        }

        /**
         * Specify the combine logic used to apply different animations
         * on the same node
         *
         * @param combineLogic the name of the combine logic
         * @return the instance of {@link MyShelfieAnimation.Builder
         * builder} that is creating the animation
         * @apiNote <p>The allowed combine logic for the application are
         * specified in {@link MyShelfieAnimationCombineLogic} class.</p>
         * <p>If a not allowed name is specified as parameter
         * a {@linkplain MyShelfieAnimationCombineLogic#PARALLEL_ANIMATION
         * parallel animation logic}
         * is applied</p>
         */
        public MyShelfieAnimation.Builder setCombineLogic(@NotNull String combineLogic) {
            try {
                setCombineLogic(MyShelfieAnimationCombineLogic.valueOf(combineLogic.toUpperCase()));
            } catch (IllegalArgumentException e) {
                setCombineLogic(PARALLEL_ANIMATION);
            }

            return this;
        }

        /**
         * Specify the combine logic used to apply different animations
         * on the same node
         *
         * @param combineLogic the type of the combine logic
         * @return the instance of {@link MyShelfieAnimation.Builder
         * builder} that is creating the animation
         * @apiNote <p>The allowed combine logic for the application are
         * specified in {@link MyShelfieAnimationCombineLogic} class.</p>
         */
        public MyShelfieAnimation.Builder setCombineLogic(MyShelfieAnimationCombineLogic combineLogic) {
            instance.combineLogic = combineLogic;
            return this;
        }

        /**
         * Construct and retrieve the chain of animations built as
         * specified
         *
         * @param node the node on which the animations will be
         *             applied
         * @return the resulting transition composed with all
         * transitions specified with the
         * {@link #addAnimation(Transition) addAnimation(...)}
         * method, and the logic specified with
         * {@link #setCombineLogic(String) setCombineLogic(...)}
         * @throws NullPointerException if no animation is specified
         *                              for the animation's chain
         * @see #setCombineLogic(String combineLogic)
         * @see #setCombineLogic(MyShelfieAnimationCombineLogic combineLogic)
         */
        public Transition buildAnimation(@NotNull Node node) throws NullPointerException {
            if (instance.myShelfieAnimation.size() == 0) {
                throw new NullPointerException();
            } else {
                return Objects.requireNonNullElse(instance.combineLogic, PARALLEL_ANIMATION).getCombinedTransition(node, instance.getArrayAnimations());
            }
        }

        /**
         * Combine and start the resulting animation obtained
         * from the chain composed with the
         * {@link #addAnimation(Transition) addAnimation(...)}
         * method, and the logic specified with
         * {@link #setCombineLogic(String) setCombineLogic(...)}
         *
         * @param node the node on which the animations will be
         *             applied
         * @throws NullPointerException if no animation is specified for
         * the animation's chain
         */
        public void playMyShelfieAnimation(Node node) throws NullPointerException {
            playMyShelfieAnimation(node, null);
        }

        /**
         * Combine and start the resulting animation obtained from the
         * chain composed with the {@link #addAnimation(Transition)
         * addAnimation(...)} method, and the logic specified with
         * {@link #setCombineLogic(String) setCombineLogic(...)}
         *
         * @param node             the node on which the animations will be
         *                         applied
         * @param onFinishedAction the action to be executed when the
         *                         animation reaches the end
         * @throws NullPointerException if no animation is specified for
         * the animation's chain
         */
        public void playMyShelfieAnimation(Node node, EventHandler<ActionEvent> onFinishedAction) throws NullPointerException {
            Transition composedTransition = buildAnimation(node);

            composedTransition.playFromStart();

            if (onFinishedAction != null)
                composedTransition.setOnFinished(onFinishedAction);
        }
    }
}
