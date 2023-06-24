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

class SingleMessageView extends VBox implements MyShelfieComponent {

    private final static int MAX_MESSAGE_CHAR_ON_LINE = 40;

    private final static String MESSAGE_BACKGROUND = "/it.polimi.ingsw/graphical.resources/misc/white_paper_texture.png";

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();
    private final String sender;

    private final String messageContent;

    private Label headerLabel;

    private TextFlow message;

    public SingleMessageView(@NamedArg("senderName") String senderName, @NamedArg("messageContent") String messageContent) {
        this(senderName, null, messageContent);
    }

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

    private void addMessageContentTextFlow() {
        message = new TextFlow(getMessageLines(messageContent));

        message.setStyle("-fx-padding: 0.3em;" +
                "-fx-font-family: 'Special Elite Regular';" +
                "-fx-wrap-text: true;");

        message.setTextAlignment(TextAlignment.JUSTIFY);

    }

    @NotNull
    private Text[] getMessageLines(String messageContent) {
        List<Text> messageTexts = new ArrayList<>();

        Arrays.stream(getSplitMessage(messageContent)).forEach(line ->{
            messageTexts.add(new Text(line));
        });

        return messageTexts.toArray(new Text[0]);
    }


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

    @Override
    public Node getCustomizedNode() {
        return this;
    }

    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
