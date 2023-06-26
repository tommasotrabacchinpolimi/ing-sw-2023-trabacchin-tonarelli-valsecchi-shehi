package it.polimi.ingsw.view.gui.customcomponents.waitingpage;

import it.polimi.ingsw.utils.color.MyShelfieColor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.utils.color.MyShelfieColor.RIFLE_GREEN;

public class WaitingPage {

    private static final String LOADING_GIF_PATH = "/it.polimi.ingsw/graphical.resources/misc/waiting_spinner.gif";

    private final Parent sceneRoot;

    private StackPane waitingPageRoot;

    private MyShelfieColor backgroundColor;


    public WaitingPage(Scene scene, String text) {
        setAllSceneMouseTransparent(scene, true);

        this.sceneRoot = scene.getRoot();

        initWaitingPageRoot();

        waitingPageRoot.getChildren().add(new MyShelfieLabel(text, LOADING_GIF_PATH));
    }

    private void initWaitingPageRoot() {
        waitingPageRoot = new StackPane();

        try{
            waitingPageRoot.setMinSize(((Pane) sceneRoot).getWidth(), ((Pane) sceneRoot).getHeight());
        }catch(ClassCastException e) {

        }

        sceneRoot.layoutBoundsProperty().addListener((observableBounds, oldBounds, newBounds) -> {
            waitingPageRoot.setMinSize(newBounds.getWidth(), newBounds.getHeight());
        });

        waitingPageRoot.setStyle("-fx-background-color: " + RIFLE_GREEN.getRGBAStyleSheet(0.67) + " ;");

        try {
            ((Pane) sceneRoot).getChildren().add(waitingPageRoot);
            waitingPageRoot.toFront();
        } catch (ClassCastException e) {
        }
    }

    public void hideWaiting() {
        setAllSceneMouseTransparent(getScene(), false);

        try{
            ((Pane) sceneRoot).getChildren().remove(waitingPageRoot);
        }catch(ClassCastException e) {

        }
    }

    public static void setAllSceneMouseTransparent(final Scene scene, boolean mouseTransparent) {
        if (scene == null)
            return;

        setMouseTransparentRecursive(scene.getRoot(), mouseTransparent);

        scene.getRoot().requestFocus();
    }

    private static void setMouseTransparentRecursive(Parent node, boolean mouseTransparent) {

        if (node == null)
            return;

        if (node.getChildrenUnmodifiable().size() > 0) {
            node.getChildrenUnmodifiable().forEach(childNode -> {
                try {
                    WaitingPage.setMouseTransparentRecursive((Parent) childNode, mouseTransparent);
                } catch (ClassCastException e) {
                    childNode.setMouseTransparent(mouseTransparent);
                    childNode.setFocusTraversable(!mouseTransparent);
                }
            });
        }

        node.setMouseTransparent(mouseTransparent);
        node.setFocusTraversable(!mouseTransparent);
    }

    private Parent getSceneRoot() {
        return sceneRoot;
    }

    private Scene getScene() {
        return sceneRoot.getScene();
    }
}
