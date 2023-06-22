package it.polimi.ingsw.view.gui.customcomponents.messageView;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieColor.DARK_LAVA;

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

    public void addMessage(SingleMessageViewType messageViewType, String senderName, String messageContent){
        getChildren().add(new SingleMessageViewContainer(messageViewType, senderName, messageContent));
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
