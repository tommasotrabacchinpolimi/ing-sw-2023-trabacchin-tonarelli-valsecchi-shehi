package it.polimi.ingsw.view.gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileSubjectView extends Pane {

    private static final String ITEM_TILE_PATH_FOLDER = "/graphical.resources/item.tiles/";

    private StackPane parent;

    /**
     *
     * @param parent
     * @param fileName no extension, just name
     */
    public TileSubjectView(StackPane parent, String fileName) {
        this.parent = parent;

        parent.getChildren().add(this);
        StackPane.setAlignment(this, Pos.CENTER);

        setStyle("-fx-padding: 1.9em;" +
                "-fx-background-image: url('" + getClass().getResource(ITEM_TILE_PATH_FOLDER + fileName + ".png") + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: cover;");

        parent.setStyle("-fx-padding: 0.1em");

        setDefaultDropShadow();

        makeRoundBorder();

        addEventFilter(MouseEvent.ANY, new TileSubjectMouseEvent(this));
    }

    public TileSubjectView(StackPane parent) {
        this(parent, "Gatti1.1");
    }

    public void changeImage(String fileName) {
        setStyle("-fx-background-image: url('" + getClass().getResource(ITEM_TILE_PATH_FOLDER + fileName + ".png") + "');");
    }

    public void removeImage() {
        setBackground(null);
    }

    private void makeRoundBorder() {
        Rectangle clipper = new Rectangle(this.parent.getWidth() * 0.9, this.parent.getHeight() * 0.9);
        clipper.setArcHeight(this.parent.getHeight() * 0.1);
        clipper.setArcWidth(this.parent.getWidth() * 0.1);

        setShape(clipper);
    }

    protected void setDefaultDropShadow() {
        setEffect(new DropShadow(1.0, Color.BLACK));
    }

    protected void resetToDefaultEffect() {
        setEffect(null);
        setDefaultDropShadow();
    }

    protected void setOnMouseEnteredEffect() {
        setEffect(null);
        setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(245, 177, 61, 0.93), 10.0, 0.0, 0.0, 0.0));
        setEffect(new InnerShadow(10.0, Color.rgb(245, 177, 61, 0.93)));
    }

    protected void setOnMouseClickedEffect() {
        setEffect(null);
        setEffect(new Glow(0.2));
    }

    protected void setDeactivatedEffect() {
        setEffect(new InnerShadow(BlurType.ONE_PASS_BOX, Color.rgb(16, 27, 31, 0.53), 127.0, 1.0, 0.0, 0.0));
    }

    private static class TileSubjectMouseEvent implements EventHandler<MouseEvent> {

        private final TileSubjectView tileSubjectTarget;

        public TileSubjectMouseEvent(TileSubjectView tileSubjectView) {
            this.tileSubjectTarget = tileSubjectView;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
                tileSubjectTarget.setOnMouseEnteredEffect();

            } else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED ||
                    mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                tileSubjectTarget.resetToDefaultEffect();

            } else if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                tileSubjectTarget.setOnMouseClickedEffect();
                onMouseClickedAction();
            }
        }

        private void onMouseClickedAction() {
            //Perform an action
        }
    }
}
