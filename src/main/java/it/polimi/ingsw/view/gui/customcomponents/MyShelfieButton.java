package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.view.gui.customcomponents.decorations.*;
import it.polimi.ingsw.view.gui.customcomponents.uitoolkit.MyShelfieRoundEdgeType;
import javafx.beans.NamedArg;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 2.0
 * @since 08/06/2023
 */
public class MyShelfieButton extends Button implements MyShelfieComponent {

    /**
     * <p>Constant that holds the relative path which correspond to the background of a button</p>
     */
    private static final String BACKGROUND_IMAGE = "/it.polimi.ingsw/graphical.resources/misc/page_base_darken.jpg";

    /**
     * <p>Constant that holds the default path to set an icon on the button</p>
     *
     * @see #MyShelfieButton(String buttonText, String iconFile)
     */
    private static final String ICON_PATH = "/it.polimi.ingsw/graphical.resources/icons/";

    /**
     * <p>Holds the entire relative path to the icon file</p>
     *
     * @apiNote if the button has no graphic inside it the value is setted to be null
     *
     * @see #MyShelfieButton(String buttonText, String iconFile)
     */
    private final String fullIconPath;

    /**
     * <p>This value is true when the button is focused, and false otherwise</p>
     *
     * @see #handleMouseEnter(MouseEvent)
     * @see #handleMouseExited(MouseEvent)
     */
    private boolean isFocused;

    /**
     * <p>This boolean value is set to {@code true}
     * if and only if the instance of the button is pressed once.</p>
     *
     * @apiNote Please note that after this boolean value
     * has been set to {@code true} it will never be {@code false} again
     * @see #hasBeenPressed()
     * @see #handleMousePressed(MouseEvent)
     */
    private boolean pressedOccurred = false;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * <p>Construct a "MyShelfieButton" with personalization set as specified by parameters</p>
     *
     * @param text     the text to show in the button
     * @param rounded  boolean value that determinate if the button
     *                 has to be rounded ({@code true}) or not ({@code false})
     * @param radius   the radius-edge value chosen between the
     *                 different available values
     * @param iconName the name of the file that corresponds
     *                 to the icon that the button should have,
     *                 followed by the extension of the file itself
     * @see MyShelfieRoundEdge
     * @see MyShelfieRoundEdgeType
     */
    public MyShelfieButton(@NamedArg("text") String text,
                           @NamedArg("rounded") boolean rounded,
                           @NamedArg("radius") MyShelfieRoundEdgeType radius,
                           @NamedArg("iconName") String iconName) {
        super(text);

        isFocused = false;

        if (rounded)
            applyDecorationAsDefault(new MyShelfieRoundEdge(radius));

        setWrapText(true);
        setTextAlignment(TextAlignment.CENTER);
        setCache(true);

        setCSS();

        applyDecorationAsDefault(new MyShelfieDarkShadow());

        if (iconName != null){
            this.fullIconPath = ICON_PATH + iconName;
            setContentDisplay(ContentDisplay.RIGHT);
            setGraphic(new MyShelfieGraphicIcon(fullIconPath, 0.5, false,"contain"));
        }else {
            fullIconPath = null;
        }

        setOnMouseEntered(this::handleMouseEnter);
        setOnMouseExited(this::handleMouseExited);
        setOnMousePressed(this::handleMousePressed);
        setOnMouseReleased(this::handleMouseReleased);
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);

        focusedProperty().addListener(changed -> {
            if (isFocused())
                handleMouseEnter(null);
            else
                handleMouseExited(null);
        });
    }

    /**
     * <p>Construct a "MyShelfieButton" with personalization set as specified by parameters</p>
     *
     * @param text    the text to shown in the button
     * @param rounded boolean value that determinate if the button
     *                has to be rounded ({@code true}) or not ({@code false})
     * @apiNote this constructor automatically set the radius of the corners to
     * the default value ({@link MyShelfieRoundEdgeType#SMALL}).
     * <p>In order to set a different value, the
     * {@link #MyShelfieButton(String, boolean, MyShelfieRoundEdgeType, String)
     * MyShelfieButton(text, boolean, radius, iconName)}
     * constructor must be called.</p>
     * <p>In addition, if the {@code rounded} ({@code boolean}) value is set to
     * {@code false} no border radius will be applied</p>
     */
    public MyShelfieButton(@NamedArg("text") String text, @NamedArg("rounded") boolean rounded) {
        this(text, rounded, MyShelfieRoundEdgeType.SMALL, null);
    }

    /**
     * <p>Construct a "MyShelfieButton" with personalization set as specified by parameters</p>
     *
     * @param text the text to show in the button
     * @apiNote this constructor automatically set the button to be rounded
     * and with the default value ({@link MyShelfieRoundEdgeType#SMALL})
     * <p>In order to set a different radius value, the
     * {@link #MyShelfieButton(String, boolean, MyShelfieRoundEdgeType, String)
     * MyShelfieButton(text, boolean, radius, iconName)}
     * constructor must be called.</p>
     * <p>If the button should not be rounded, the
     * {@link #MyShelfieButton(String text, boolean rounded)}</p> constructor
     * must be called.</p>
     */
    public MyShelfieButton(@NamedArg("text") String text) {
        this(text, true);
    }

    /**
     * <p>Construct a "MyShelfieButton" with personalization set as specified by parameters</p>
     *
     * @param text     the text to show in the button
     * @param iconName the name of the file that corresponds
     *                 to the icon that the button should have,
     *                 followed by the extension of the file itself
     * @apiNote this constructor automatically set the button to be rounded
     * and with the default value ({@link MyShelfieRoundEdgeType#SMALL})
     */
    public MyShelfieButton(@NamedArg("text") String text, @NamedArg("iconFile") String iconName) {
        this(text, true, MyShelfieRoundEdgeType.SMALL, iconName);
    }

    /**
     * Applies a series of CSS rules to the button to set the default style
     */
    private void setCSS() {
        setStyle("-fx-font-family: 'Special Elite Regular';" +
                "-fx-text-fill: rgba(230, 221, 199, 0.97);" +
                "-fx-background-image: url('" + getClass().getResource(BACKGROUND_IMAGE) + "');" +
                "-fx-background-size: contain;" +
                "-fx-background-position: center center;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 1em;" +
                "-fx-border-color: transparent;" +
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;");
    }

    /**
     * <p>Applies the effects at the personalized button when the mouse enter it</p>
     *
     * @param mouseEvent the mouse action performed by the user
     */
    private void handleMouseEnter(MouseEvent mouseEvent) {
        if (!isFocused) {
            applyDecoration(new MyShelfieBloom());

            isFocused = true;
        }
    }

    /**
     * <p>Applies the effects at the personalized button when the mouse exited it</p>
     *
     * @param mouseEvent the mouse action performed by the user
     */
    private void handleMouseExited(MouseEvent mouseEvent) {
        if (isFocused) {
            resetToDefaultDecorations();
            isFocused = false;
        }
    }

    /**
     * <p>Applies the effects at the personalized button when is pressed</p>
     *
     * @param mouseEvent the mouse action performed by the user
     * @apiNote the mouse event "pressed" is different from clicked
     */
    private void handleMousePressed(MouseEvent mouseEvent) {
        this.pressedOccurred = true;
        applyDecoration(new MyShelfieGlow());
    }

    /**
     * <p>Applies the effects at the personalized button when the mouse is released</p>
     *
     * @param mouseEvent the mouse action performed by the user
     * @apiNote the mouse event release always happens after the pressed event
     */
    private void handleMouseReleased(MouseEvent mouseEvent) {
        resetToDefaultDecorations();
    }

    /**
     * <p>Applies the effects at the personalized button when is pressed with the enter key from keyboard</p>
     *
     * @param keyEvent the key pressed
     * @see #handleMousePressed(MouseEvent)
     */
    private void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleMousePressed(null);
        }
    }

    /**
     * <p>Applies the effects at the personalized button when is released after the enter key is been typed</p>
     *
     * @param keyEvent the key pressed
     * @see #handleMouseReleased(MouseEvent)
     */
    private void handleKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleMouseReleased(null);
        }
    }

    public boolean hasBeenPressed() {
        return pressedOccurred;
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
     * Retrieves the set of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the set
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
