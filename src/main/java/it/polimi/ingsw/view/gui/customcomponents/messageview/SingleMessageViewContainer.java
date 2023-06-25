package it.polimi.ingsw.view.gui.customcomponents.messageview;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class SingleMessageViewContainer extends StackPane implements MyShelfieComponent {
    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    public SingleMessageViewContainer(@NotNull SingleMessageViewType messageViewType, String headerText,
                                      String senderName, String messageContent) {
        super();

        getChildren().add(new SingleMessageView(senderName, headerText, messageContent));

        setAlignment(messageViewType.getAlignment());
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
