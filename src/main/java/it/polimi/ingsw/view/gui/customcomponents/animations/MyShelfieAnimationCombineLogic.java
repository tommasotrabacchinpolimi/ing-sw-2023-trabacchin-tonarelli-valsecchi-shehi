package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Node;

/**
 * Defines all the possible combine logic for a chain of
 * animation built with the {@link MyShelfieAnimation}
 * class
 */
public enum MyShelfieAnimationCombineLogic {
    /**
     * <p>Represent a parallel logic execution.</p>
     * <p>Using this will result is a unique transition
     * where each transition specified in the chain
     * will be executed at the same time of the others</p>
     */
    PARALLEL_ANIMATION {
        /**
         * {@inheritDoc}
         * <p>All {@code animations} are executed
         * simultaneously</p>
         *
         * @param node the graphical component on
         *             which apply the transition's
         *             chain
         * @param animations all the transition to
         *                   apply at the node
         * @return a {@linkplain ParallelTransition
         * transition} that will execute every
         * animation simultaneously
         */
        @Override
        ParallelTransition getCombinedTransition(Node node, Animation... animations) {
            return new ParallelTransition(node, animations);
        }
    },

    /**
     * <p>Represent a sequential logic execution.</p>
     * <p>Using this will result is a unique transition
     * where each transition specified in the chain
     * will be executed in the order that was assigned
     * at the "animated graphical component"</p>
     *
     * @see MyShelfieAnimation.Builder#addAnimation(Transition transition)
     */
    SEQUENTIAL_ANIMATION {
        /**
         * {@inheritDoc}
         * <p>All {@code animations} are executed
         * once the previous one has reached its end</p>
         *
         * @param node the graphical component on
         *             which apply the transition's
         *             chain
         * @param animations all the transition to
         *                   apply at the node
         * @return a {@linkplain SequentialTransition
         * transition} that will execute every
         * animation once the previous is finished
         */
        @Override
        SequentialTransition getCombinedTransition(Node node, Animation... animations) {
            return new SequentialTransition(node, animations);
        }
    };

    /**
     * Retrieves the final transition that will
     * execute all specified {@code animations}
     *
     * @param node the graphical component on
     *             which apply the transition's
     *             chain
     * @param animations all the transition to
     *                   apply at the node
     */
    abstract Transition getCombinedTransition(Node node, Animation... animations);
}
