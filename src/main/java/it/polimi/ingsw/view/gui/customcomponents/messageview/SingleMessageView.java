package it.polimi.ingsw.view.gui.customcomponents.messageview;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.beans.NamedArg;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieColor.CHARLESTON;
import static it.polimi.ingsw.utils.color.MyShelfieColor.DARK_LAVA;

/**
 * Represents a custom component for displaying a single message in the graphical user interface.
 * This class extends the JavaFX `VBox` layout and implements the `MyShelfieComponent` interface.
 * It provides methods for adding a header label and message content to the message view.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 */
class SingleMessageView extends VBox implements MyShelfieComponent {

    /**
     * Represents the maximum number of characters allowed on a single line in the message content.
     */
    private final static int MAX_MESSAGE_CHAR_ON_LINE = 40;


    /**
     * Represents the background image path for the message view.
     */

    private final static String MESSAGE_BACKGROUND = "/it.polimi.ingsw/graphical.resources/misc/white_paper_texture.png";

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * The sender of the message.
     */
    private final String sender;

    /**
     * The content of the message
     */
    private final String messageContent;

    /**
     * The header label
     */
    private Label headerLabel;

    /**
     * The content of the message
     */
    private TextFlow message;

    /**
     * Constructs a new instance of the {@code SingleMessageView} class with the given sender name and message content.
     * This constructor sets the header text to {@code null}.
     *
     * @param senderName The name or identifier of the message sender.
     * @param messageContent The content of the message.
     */
    public SingleMessageView(@NamedArg("senderName") String senderName, @NamedArg("messageContent") String messageContent) {
        this(senderName, null, messageContent);
    }

    /**
     * Constructs a new `SingleMessageView` with the given sender name, header text, and message content.
     *
     * @param senderName    The name of the message sender.
     * @param headerText    The text to display in the header label.
     * @param messageContent The content of the message.
     */
    public SingleMessageView(String senderName, String headerText, String messageContent) {
        super();

        this.sender = senderName;
        this.messageContent = messageContent;

        addHeaderText(headerText);

        addMessageContentTextFlow();

        setCSS();

        applyDecorationAsDefault(new MyShelfieDarkShadow(), new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL));

        getChildren().addAll(headerLabel, message);

        layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            headerLabel.setMaxWidth(newValue.getWidth());
        });

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    /**
     * Sets the CSS styles for the `SingleMessageView` component.
     * This method applies background image, border color, border width, padding, and text wrapping styles.
     */
    private void setCSS() {
        setStyle("-fx-background-image: url('" + getMyShelfieResource(MESSAGE_BACKGROUND) + "');" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-border-color:"+ DARK_LAVA.getDarkenRGBAStyleSheet(0.97) + " ;" +
                "-fx-border-width: 0.2em;" +
                "-fx-padding: 1em;" +
                "-fx-wrap-text: true;");
    }

    /**
     * Adds the header text to the `SingleMessageView` component.
     * If the `headerText` parameter is not null, the header label is created with the sender name concatenated with the header text.
     * Otherwise, the header label is created with only the sender name.
     *
     * @param headerText The text to display in the header label.
     */
    private void addHeaderText(String headerText) {

        if(headerText != null)
            headerLabel = new Label(sender + headerText);
        else
            headerLabel = new Label(sender);

        headerLabel.setStyle("-fx-text-alignment: center;" +
                "-fx-text-fill: " + CHARLESTON.getDarkenRGBAStyleSheet(0.97) + ";" +
                "-fx-wrap-text: true;" +
                "-fx-font-family: 'Special Elite Regular';" +
                "-fx-border-width: 0em 0em 0.1em 0em;" +
                "-fx-border-color: " + CHARLESTON.getRGBAStyleSheet(0.93) + ";" +
                "-fx-label-padding: 0.2em;");
    }

    /**
     * Adds the message content as a `TextFlow` to the `SingleMessageView` component.
     * The message content is split into lines using the `getMessageLines` method,
     * and the resulting lines are passed as arguments to create a new `TextFlow`.
     * The method sets various CSS styles for the message content, including padding,
     * font family, and text wrapping. It also sets the text alignment to justify.
     */
    private void addMessageContentTextFlow() {
        message = new TextFlow(getMessageLines(messageContent));

        message.setStyle("-fx-padding: 0.3em;" +
                "-fx-font-family: 'Special Elite Regular';" +
                "-fx-wrap-text: true;");

        message.setTextAlignment(TextAlignment.JUSTIFY);

    }

    /**
     * Retrieves the message lines from the given message content and returns an array of `Text` objects.
     * The method splits the message content into lines using the `getSplitMessage` method.
     * It then creates a `Text` object for each line and adds it to a list.
     * Finally, it converts the list of `Text` objects to an array and returns it.
     *
     * @param messageContent The content of the message.
     * @return An array of `Text` objects representing the message lines.
     */
    @NotNull
    private Text[] getMessageLines(String messageContent) {
        List<Text> messageTexts = new ArrayList<>();

        Arrays.stream(getSplitMessage(messageContent)).forEach(line ->{
            messageTexts.add(new Text(line));
        });

        return messageTexts.toArray(new Text[0]);
    }


    /**
     * Splits the given text into individual words and returns an array of strings.
     * The method scans the text character by character and identifies word boundaries based on spaces.
     * It adds each word to a list and finally converts the list of words to an array and returns it.
     *
     * @param text The text to split into words.
     * @return An array of strings representing the individual words.
     * @throws IllegalArgumentException if the input text is null.
     */
    @NotNull
    private static String[] getSplitWordInMessage(String text) throws IllegalArgumentException {
        if (text == null)
            throw new IllegalArgumentException();

        List<String> words = new ArrayList<>();

        for (int i = 0, index = 0; i < text.length(); ++i) {
            if (text.charAt(i) == ' ') {
                words.add(text.substring(index, i));
                index = i + 1;
            }
            if (i == text.length() - 1) {
                words.add(text.substring(index, i + 1));
            }
        }

        return words.toArray(new String[0]);
    }

    /**
     * Splits the given text into multiple lines based on the maximum character limit per line.
     * The method first splits the text into individual words using the {@link #getSplitWordInMessage(String)} method.
     * It then processes the words and constructs lines by adding words until the maximum character limit is reached.
     * If a word exceeds the limit, it is placed on a new line.
     * The resulting lines are stored in a list and converted to an array, which is returned.
     *
     * @param text The text to split into lines.
     * @return An array of strings representing the lines of text.
     */
    @NotNull
    private static String[] getSplitMessage(String text) {
        String[] wordsInMessage = getSplitWordInMessage(text);

        List<String> messageLines = new ArrayList<>();

        StringBuilder messageLine = new StringBuilder();

        int sum = 0;

        for (int i = 0; i < wordsInMessage.length; ++i) {
            if (messageLine.isEmpty() && wordsInMessage[i].length() <= MAX_MESSAGE_CHAR_ON_LINE) { //quando inserisco la prima parola nella riga
                if (wordsInMessage[i].length() == MAX_MESSAGE_CHAR_ON_LINE) {
                    messageLines.add(messageLine.append(wordsInMessage[i]).toString());
                    messageLine = new StringBuilder();
                    sum = 0;
                } else {
                    messageLine.append(wordsInMessage[i]);
                    sum += wordsInMessage[i].length();
                }
            } else { // quando inserisco le parole successive nella riga
                sum += wordsInMessage[i].length() + 1;

                if (sum < MAX_MESSAGE_CHAR_ON_LINE) {
                    messageLine.append(" ").append(wordsInMessage[i]);
                } else if (sum == MAX_MESSAGE_CHAR_ON_LINE) {

                    if(i == wordsInMessage.length - 1) {
                        messageLines.add(messageLine.append(" ").append(wordsInMessage[i]).toString());
                    } else {
                        messageLines.add(messageLine.append(" ").append(wordsInMessage[i]).append("\n").toString());
                    }
                    sum = 0;
                    messageLine = new StringBuilder();
                } else {
                    messageLines.add(messageLine.append("\n").toString());
                    sum = wordsInMessage[i].length();
                    messageLine = new StringBuilder(wordsInMessage[i]);
                }
            }
        }
        if (!messageLine.isEmpty()) {
            messageLines.add(messageLine.toString());
        }
        return messageLines.toArray(new String[0]);
    }

    /**
     * Returns the customized Node.
     *
     * @return The customized Node.
     */
    @Override
    public Node getCustomizedNode() {
        return this;
    }


    /**
     * Returns the list of base decorations applied to the component.
     *
     * @return The list of base decorations.
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
