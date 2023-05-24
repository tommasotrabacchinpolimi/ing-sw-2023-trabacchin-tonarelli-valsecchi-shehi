package it.polimi.ingsw.view.gui;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;


public class MyShelfieButton extends Button {

    /**
     * <p>Constant that holds the relative path which correspond to the background of a button</p>
     */
    private static final String BACKGROUND_IMAGE = "/it.polimi.ingsw/graphical.resources/misc/page_base_darken.jpg";

    /**
     * <p>Constant that specifies the default radius border for the personalized button</p>
     */
    private static final double DEFAULT_RADIUS = 37.0;

    /**
     * <p>Constant that represent the default drop-shadow for personalized button style</p>
     */
    private static final DropShadow DEFAULT_DROPSHADOW = new DropShadow(BlurType.GAUSSIAN, Color.rgb(71, 40, 17, 0.97), 10.0, 0.0, 0.0, 0.0);

    /**
     * <p>Constant that holds the default path to set an icon on the button</p>
     *
     * @see #MyShelfieButton(String buttonText, String iconFile)
     */
    private static final String ICON_PATH = "/it.polimi.ingsw/graphical.resources/icons/";

    /**
     * <p>Holds the entire relative path to the icon file</p>
     *
     * @see #MyShelfieButton(String buttonText, String iconFile)
     */
    private String fullIconPath;

    /**
     * <p>Value that correspond to the actual button's radius border set at the button</p>
     *
     * @apiNote The value should be ranged between 0.0 and 100.0
     * <p>The value is a percentage of the minimum size between width and height of the button</p>
     *
     * @apiNote default value is {@value DEFAULT_RADIUS}
     */
    private final double radius;

    /**
     * <p>Construct a "MyShelfieButton" with personalization set as specified by parameters</p>
     *
     * @apiNote if the {@code radius} is set to a value grater than 100.0
     * it will be automatically set at 100.0
     *
     * @param text    the text to show in the button
     * @param rounded boolean value that determinate if the button
     *                has to be rounded ({@code true}) or not ({@code false})
     * @param radius  the radius value to which the button must be set
     *                if the {@code rounded} parameter is set to {@code true}
     * @see #radius
     * @see #roundedShape()
     * @see #myShelfieStyle()
     */
    public MyShelfieButton(@NamedArg("text") String text,
                           @NamedArg("rounded") boolean rounded,
                           @NamedArg("radius") double radius) {
        super(text);

        this.radius = (radius > 100.0) ? 100.0 : radius;

        if (rounded)
            roundedShape();

        setWrapText(true);
        setTextAlignment(TextAlignment.CENTER);
        setCache(true);

        myShelfieStyle();

        setOnMouseEntered(this::handleMouseEnter);
        setOnMouseExited(this::handleMouseExited);
        setOnMousePressed(this::handleMousePressed);
        setOnMouseReleased(this::handleMouseReleased);
        setOnMouseClicked(this::handleMouseClicked);
    }

    /**
     * <p>Construct a "MyShelfieButton" with personalization set as specified by parameters</p>
     *
     * @param text    the text to show in the button
     * @param rounded boolean value that determinate if the button
     *                has to be rounded ({@code true}) or not ({@code false})
     * @apiNote this constructor automatically set the radius to
     * the default value ({@value DEFAULT_RADIUS}).
     * <p>In order to set a different value, the
     * {@link #MyShelfieButton(String text, boolean rounded, double radius)}
     * constructor must be called.</p>
     * <p>In addition, if the {@code rounded} ({@code boolean}) value is set to
     * {@code false} no border radius will be applied</p>
     */
    public MyShelfieButton(@NamedArg("text") String text, @NamedArg("rounded") boolean rounded) {
        this(text, rounded, DEFAULT_RADIUS);
    }

    /**
     * <p>Construct a "MyShelfieButton" with personalization set as specified by parameters</p>
     *
     * @param text the text to show in the button
     * @apiNote this constructor automatically set the button to be rounded
     * and with the default value ({@value DEFAULT_RADIUS})
     * <p>In order to set a different radius value, the
     * {@link #MyShelfieButton(String text, boolean rounded, double radius)}
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
     * @param iconFile the name of the file that corresponds
     *                 to the icon that the button should have,
     *                 followed by the extension of the file itself
     * @apiNote this constructor automatically set the button to be rounded
     * and with the default value ({@value DEFAULT_RADIUS})
     */
    public MyShelfieButton(@NamedArg("text") String text, @NamedArg("iconFile") String iconFile) {
        this(text, true);

        this.fullIconPath = ICON_PATH + iconFile;

        Pane icon = new Pane();
        icon.setStyle("-fx-background-image: url('" + getClass().getResource(fullIconPath) + "');" +
                "-fx-background-size: contain;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-padding: 0.5em;");

        setContentDisplay(ContentDisplay.RIGHT);
        setGraphic(icon);
    }

    /**
     * <p>Construct a {@linkplain Rectangle rectangle}
     * with a {@linkplain #radius} border</p>
     */
    private void roundedShape() {
        Rectangle clipper = new Rectangle();

        layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            clipper.setHeight(newValue.getHeight());
            clipper.setWidth(newValue.getWidth());

            double radiusSize = Math.min(newValue.getHeight(), newValue.getWidth());

            clipper.setArcWidth(radiusSize * radius / 100);
            clipper.setArcHeight(radiusSize * radius / 100);
        });

        setShape(clipper);
    }

    /**
     * <p>Applies the default style for the button including effects and CSS</p>
     * @apiNote this function is the combination of {@link #myShelfieStyleSheet()}
     * and {@link #setDefaultEffects()}
     */
    private void myShelfieStyle() {
        myShelfieStyleSheet();
        setDefaultEffects();
    }

    /**
     * Applies a series of CSS rules to the button to set the default style
     *
     * @see #myShelfieStyle()
     */
    private void myShelfieStyleSheet() {
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
     * Applies the default effect to the button
     *
     * @see #DEFAULT_DROPSHADOW
     */
    private void setDefaultEffects() {
        DEFAULT_DROPSHADOW.setInput(getEffect());
        setEffect(DEFAULT_DROPSHADOW);
    }

    /**
     * Removes all effect applied by user interactions and apply {@linkplain #myShelfieStyle() default style}
     *
     * @see #myShelfieStyle()
     */
    private void resetToDefaultEffects() {
        setEffect(null);
        myShelfieStyle();
    }

    /**
     * <p>Applies the effects at the personalized button when the mouse enter it</p>
     *
     * @param mouseEvent the mouse action performed by the user
     */
    private void handleMouseEnter(MouseEvent mouseEvent) {
        Bloom bloom = new Bloom();
        bloom.setInput(getEffect());
        setEffect(bloom);
    }

    /**
     * <p>Applies the effects at the personalized button when the mouse exited it</p>
     *
     * @param mouseEvent the mouse action performed by the user
     *
     * @see #resetToDefaultEffects()
     */
    private void handleMouseExited(MouseEvent mouseEvent) {
        resetToDefaultEffects();
    }

    /**
     * <p>Applies the effects at the personalized button when the button is pressed</p>
     *
     * @apiNote the mouse event "pressed" is different from clicked
     *
     * @param mouseEvent the mouse action performed by the user
     */
    private void handleMousePressed(MouseEvent mouseEvent) {
        Glow glow = new Glow(0.2);
        glow.setInput(getEffect());
        setEffect(glow);
    }

    /**
     * <p>Applies the effects at the personalized button when the mouse is released</p>
     *
     * @apiNote the mouse event release always happens after the pressed evenet
     *
     * @param mouseEvent the mouse action performed by the user
     *
     * @see #resetToDefaultEffects()
     */
    private void handleMouseReleased(MouseEvent mouseEvent) {
        resetToDefaultEffects();
    }

    /**
     * <p>Applies the effects at the personalized button when the button is clicked</p>
     *
     * @param mouseEvent the mouse action performed by the user
     *
     * @see #resetToDefaultEffects()
     */
    private void handleMouseClicked(MouseEvent mouseEvent) {
        resetToDefaultEffects();
    }
}
