package it.polimi.ingsw.view.gui.customcomponents.waitingpage;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static it.polimi.ingsw.utils.color.MyShelfieColor.RIFLE_GREEN;

abstract class BlockingPage {

    protected StackPane blockingPageRoot;

    protected final Parent sceneRoot;

    public BlockingPage(@NotNull Scene scene) {
        this.sceneRoot = scene.getRoot();

        BlockingPage.setAllSceneMouseTransparent(scene, true);

        initBlockingPageRoot();
    }

    private void initBlockingPageRoot() {
        blockingPageRoot = new StackPane();

        try{
            blockingPageRoot.setMinSize(((Pane) sceneRoot).getWidth(), ((Pane) sceneRoot).getHeight());
        }catch(ClassCastException ignored) {
            sceneRoot.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
                if(newValue.getWidth() != oldValue.getWidth()) {
                    blockingPageRoot.setMinWidth(newValue.getWidth());
                }else if(newValue.getHeight() != oldValue.getHeight()) {
                    blockingPageRoot.setMinHeight(newValue.getHeight());
                }
            });
        }

        sceneRoot.layoutBoundsProperty().addListener((observableBounds, oldBounds, newBounds) -> {
            blockingPageRoot.setMinSize(newBounds.getWidth(), newBounds.getHeight());
        });

        blockingPageRoot.setStyle("-fx-background-color: " + RIFLE_GREEN.getRGBAStyleSheet(0.67) + " ;");

        try {
            ((Pane) sceneRoot).getChildren().add(blockingPageRoot);
            blockingPageRoot.toFront();
        } catch (ClassCastException ignored) {
            sceneRoot.getScene().setRoot(blockingPageRoot);
        }
    }

    static void setAllSceneMouseTransparent(final Scene scene, boolean mouseTransparent) {
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
                    BlockingPage.setMouseTransparentRecursive((Parent) childNode, mouseTransparent);
                } catch (ClassCastException e) {
                    childNode.setMouseTransparent(mouseTransparent);
                    childNode.setFocusTraversable(!mouseTransparent);
                }
            });
        }

        node.setMouseTransparent(mouseTransparent);
        node.setFocusTraversable(!mouseTransparent);
    }

    public void hidePage() {
        BlockingPage.setAllSceneMouseTransparent(getScene(), false);

        setMouseTransparentRecursive(blockingPageRoot, true);

        try{
            ((Pane) sceneRoot).getChildren().remove(blockingPageRoot);
        }catch(ClassCastException ignored) {
        }
    }

    protected void addToBlockingPage(Node... node) {
        blockingPageRoot.getChildren().addAll(node);
    }

    private Scene getScene() {
        return sceneRoot.getScene();
    }

    protected void setStyleSheet(final String filePath) {
        blockingPageRoot.getStylesheets().add(Objects.requireNonNull(getClass().getResource(filePath)).toExternalForm());
    }
}
