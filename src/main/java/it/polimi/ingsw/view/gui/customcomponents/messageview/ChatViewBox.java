package it.polimi.ingsw.view.gui.customcomponents.messageview;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a custom component for displaying a chat box in the graphical user interface.
 * This class extends the JavaFX `VBox` layout and implements the `MyShelfieComponent` interface.
 * It provides methods for adding messages to the chat box.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 */

public class ChatViewBox extends VBox implements MyShelfieComponent {

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Creates a {@code VBox} layout with {@code spacing = 2em} and alignment at {@code TOP_LEFT}.
     */
    public ChatViewBox() {
        super();

        setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 0.7em;" +
                "-fx-spacing: 1em;");

        //applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.MINIMUM), new MyShelfieDarkShadow());
    }

    /**
     * Adds a message to the chat box with the specified parameters.
     *
     * @param messageViewType       The type of the message view.
     * @param messageContent        The content of the message.
     * @param receiver              The name of the message receiver.
     */
    public void addMessage(SingleMessageViewType messageViewType, String receiver, String messageContent){
        addMessage(messageViewType, SingleMessageViewPrivacyType.PRIVATE, "You", messageContent, receiver);
    }

    /**
     * Adds a message to the chat box with the specified parameters.
     * The privacy type of the message is specified.
     *
     * @param messageViewType       The type of the message view.
     * @param messageViewPrivacyType The privacy type of the message view.
     * @param messageContent        The content of the message.
     */
    public void addMessage(SingleMessageViewType messageViewType, SingleMessageViewPrivacyType messageViewPrivacyType,
                           String messageContent){
        addMessage(messageViewType, messageViewPrivacyType, "You", messageContent, "");
    }

    /**
     * Adds a message to the chat box with the specified parameters.
     * The sender name and content of the message are specified.
     *
     * @param messageViewType       The type of the message view.
     * @param messageViewPrivacyType The privacy type of the message view.
     * @param senderName            The name of the message sender.
     * @param messageContent        The content of the message.
     */
    public void addMessage(SingleMessageViewType messageViewType, SingleMessageViewPrivacyType messageViewPrivacyType,
                           String senderName, String messageContent){
        addMessage(messageViewType, messageViewPrivacyType, senderName, messageContent, "");
    }

    /**
     * Adds a message to the chat box with the specified parameters.
     * The sender name, content of the message, and receiver name are specified.
     *
     * @param messageViewType       The type of the message view.
     * @param messageViewPrivacyType The privacy type of the message view.
     * @param senderName            The name of the message sender.
     * @param messageContent        The content of the message.
     * @param receiver              The name of the message receiver.
     */
    public void addMessage(SingleMessageViewType messageViewType, SingleMessageViewPrivacyType messageViewPrivacyType,
                           String senderName, String messageContent, String receiver){
        getChildren().add(
                new SingleMessageViewContainer(messageViewType, messageViewPrivacyType.getHeaderMessageViewText() + receiver,
                        senderName, messageContent));
    }

    /**
     * Retrieve the element that has to be customized
     *
     * @return the element that needs to be customized
     */
    @Override
    public Node getCustomizedNode() {
        return this;
    }

    /**
     * Retrieves the list of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the list of default decorations
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
