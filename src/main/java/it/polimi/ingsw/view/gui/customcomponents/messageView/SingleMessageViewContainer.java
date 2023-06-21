package it.polimi.ingsw.view.gui.customcomponents.messageView;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class SingleMessageViewContainer extends StackPane implements MyShelfieComponent {
    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    public SingleMessageViewContainer() {
        super();
    }

    @Override
    public Node getCustomizedNode() {
        return this;
    }

    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
