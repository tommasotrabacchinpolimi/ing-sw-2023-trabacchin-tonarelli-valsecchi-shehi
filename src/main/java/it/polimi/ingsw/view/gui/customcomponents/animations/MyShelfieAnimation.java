package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieAnimation.MyShelfieAnimationCombineLogic.PARALLEL_ANIMATION;

public class MyShelfieAnimation {

    private final List<Transition> myShelfieAnimation = new ArrayList<>();

    /**
     * @apiNote Private constructor to enforce the use of the builder
     */
    private MyShelfieAnimation() {
    }

    @NotNull
    private Animation[] getArrayAnimations() {
        return myShelfieAnimation.toArray(new Animation[0]);
    }

    @NotNull
    @Contract(" -> new")
    public static MyShelfieAnimation.Builder build(){
        return new MyShelfieAnimation.Builder();
    }

    public enum MyShelfieAnimationCombineLogic {
        PARALLEL_ANIMATION {
            @Override
            ParallelTransition getLogic(Node node, Animation... animations) {
                return new ParallelTransition(node, animations);
            }
        },

        SEQUENTIAL_ANIMATION {
            @Override
            SequentialTransition getLogic(Node node, Animation... animations) {
                return new SequentialTransition(node, animations);
            }
        };

        abstract Transition getLogic(Node node, Animation... animations);
    }

    public static class Builder {
        private final MyShelfieAnimation instance;

        private MyShelfieAnimationCombineLogic combineLogic;

        private Builder() {
            instance = new MyShelfieAnimation();
        }

        public MyShelfieAnimation.Builder addAnimation(Transition transition) {
            instance.myShelfieAnimation.add(transition);
            return this;
        }

        public MyShelfieAnimation.Builder setCombineLogic(String combineLogic) {
            try {
                setCombineLogic(MyShelfieAnimationCombineLogic.valueOf(combineLogic));
            } catch (IllegalArgumentException e) {
                setCombineLogic(PARALLEL_ANIMATION);
            }

            return this;
        }

        public MyShelfieAnimation.Builder setCombineLogic(MyShelfieAnimationCombineLogic combineLogic) {
            this.combineLogic = combineLogic;
            return this;
        }

        public Transition buildAnimation(Node node) throws NullPointerException {
            if (instance.myShelfieAnimation.size() == 0) {
                throw new NullPointerException();
            } else {
                return Objects.requireNonNullElse(combineLogic, PARALLEL_ANIMATION).getLogic(node, instance.getArrayAnimations());
            }
        }
    }
}
