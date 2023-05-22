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

    private static final String BACKGROUND_IMAGE = "/it.polimi.ingsw/graphical.resources/misc/page_base_darken.jpg";

    private static final double DEFAULT_RADIUS = 13.0;

    private static final DropShadow DEFAULT_DROPSHADOW = new DropShadow(BlurType.GAUSSIAN, Color.rgb(71, 40, 17, 0.97), 10.0, 0.0, 0.0, 0.0);

    private static final String ICON_PATH = "/it.polimi.ingsw/graphical.resources/icons/";

    private String fullIconPath;

    private final double radius;

    public MyShelfieButton(@NamedArg("buttonText") String buttonText, @NamedArg("rounded") boolean rounded, @NamedArg("radius") double radius) {
        super(buttonText);

        this.radius = radius;

        if (rounded)
            rounded();

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

    public MyShelfieButton(@NamedArg("buttonText") String buttonText, @NamedArg("rounded") boolean rounded) {
        this(buttonText, rounded, DEFAULT_RADIUS);
    }

    public MyShelfieButton(@NamedArg("buttonText") String buttonText) {
        this(buttonText, true);
    }

    public MyShelfieButton(@NamedArg("buttonText") String buttonText, @NamedArg("iconName") String iconName) {
        this(buttonText, true);

        this.fullIconPath = ICON_PATH + iconName + ".png";

        Pane icon = new Pane();
        icon.setStyle("-fx-background-image: url('" + getClass().getResource(fullIconPath) + "');" +
                "-fx-background-size: contain;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-padding: 0.5em;");

        setContentDisplay(ContentDisplay.RIGHT);
        setGraphic(icon);
    }

    private void rounded() {
        Rectangle clipper = new Rectangle();

        layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            clipper.setHeight(newValue.getHeight());
            clipper.setWidth(newValue.getWidth());
            clipper.setArcWidth(radius);
            clipper.setArcHeight(radius);
        });

        setShape(clipper);
    }

    private void myShelfieStyle() {
        myShelfieStyleSheet();
        setDefaultEffects();
    }

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

    private void setDefaultEffects() {
        DEFAULT_DROPSHADOW.setInput(getEffect());
        setEffect(DEFAULT_DROPSHADOW);
    }

    private void resetToDefaultEffects() {
        setEffect(null);
        myShelfieStyle();
    }

    private void handleMouseEnter(MouseEvent mouseEvent) {
        Bloom bloom = new Bloom();
        bloom.setInput(getEffect());
        setEffect(bloom);
    }

    private void handleMouseExited(MouseEvent mouseEvent) {
        resetToDefaultEffects();
    }

    private void handleMousePressed(MouseEvent mouseEvent) {
        Glow glow = new Glow(0.2);
        glow.setInput(getEffect());
        setEffect(glow);
    }

    private void handleMouseReleased(MouseEvent mouseEvent) {
        resetToDefaultEffects();
    }

    private void handleMouseClicked(MouseEvent mouseEvent) {
        resetToDefaultEffects();
    }
}
