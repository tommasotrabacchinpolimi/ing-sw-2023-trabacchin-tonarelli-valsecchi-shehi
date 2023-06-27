package it.polimi.ingsw.view.gui.customcomponents.messageview;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a container for a single message view in the graphical user interface.
 * This class extends the JavaFX `StackPane` layout and implements the `MyShelfieComponent` interface.
 * It provides methods for adding a single message view component and applying decorations.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 */

class SingleMessageViewContainer extends StackPane implements MyShelfieComponent {
    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Constructs a new `SingleMessageViewContainer` with the specified message view type,
     * header text, sender name, and message content.
     *
     * @param messageViewType The type of the single message view.
     * @param headerText      The text to display in the header label of the single message view.
     * @param senderName      The name of the message sender.
     * @param messageContent  The content of the message.
     */
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
