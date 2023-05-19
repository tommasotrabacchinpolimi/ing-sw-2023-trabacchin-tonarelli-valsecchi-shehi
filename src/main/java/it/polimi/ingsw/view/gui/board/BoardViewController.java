package it.polimi.ingsw.view.gui.board;

import it.polimi.ingsw.view.gui.TileSubjectView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardViewController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private GridPane livingRoomGridPane;

    @FXML
    private AnchorPane livingRoomAnchor;

    @FXML
    private StackPane itemTileBox03;

    @FXML
    private StackPane itemTileBox04;

    @FXML
    private StackPane itemTileBox13;

    @FXML
    private StackPane itemTileBox14;

    @FXML
    private StackPane itemTileBox15;

    @FXML
    private StackPane itemTileBox22;

    @FXML
    private StackPane itemTileBox23;

    @FXML
    private StackPane itemTileBox24;

    @FXML
    private StackPane itemTileBox25;

    @FXML
    private StackPane itemTileBox26;

    @FXML
    private StackPane itemTileBox31;

    @FXML
    private StackPane itemTileBox32;

    @FXML
    private StackPane itemTileBox33;

    @FXML
    private StackPane itemTileBox34;

    @FXML
    private StackPane itemTileBox35;

    @FXML
    private StackPane itemTileBox36;

    @FXML
    private StackPane itemTileBox37;

    @FXML
    private StackPane itemTileBox38;

    @FXML
    private StackPane itemTileBox40;

    @FXML
    private StackPane itemTileBox41;

    @FXML
    private StackPane itemTileBox42;

    @FXML
    private StackPane itemTileBox43;

    @FXML
    private StackPane itemTileBox44;

    @FXML
    private StackPane itemTileBox45;

    @FXML
    private StackPane itemTileBox46;

    @FXML
    private StackPane itemTileBox47;

    @FXML
    private StackPane itemTileBox48;

    @FXML
    private StackPane itemTileBox50;

    @FXML
    private StackPane itemTileBox51;

    @FXML
    private StackPane itemTileBox52;

    @FXML
    private StackPane itemTileBox53;

    @FXML
    private StackPane itemTileBox54;

    @FXML
    private StackPane itemTileBox55;

    @FXML
    private StackPane itemTileBox56;

    @FXML
    private StackPane itemTileBox57;

    @FXML
    private StackPane itemTileBox62;

    @FXML
    private StackPane itemTileBox63;

    @FXML
    private StackPane itemTileBox64;

    @FXML
    private StackPane itemTileBox65;

    @FXML
    private StackPane itemTileBox66;

    @FXML
    private StackPane itemTileBox73;

    @FXML
    private StackPane itemTileBox74;

    @FXML
    private StackPane itemTileBox75;

    @FXML
    private StackPane itemTileBox84;

    @FXML
    private StackPane itemTileBox85;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::fillUpBoard);
    }

    private void fillUpBoard() {
        new TileSubjectView(itemTileBox03, "cat_1");
        new TileSubjectView(itemTileBox04, "cat_2");
        new TileSubjectView(itemTileBox13, "cat_3");
        new TileSubjectView(itemTileBox14, "book_1");
        new TileSubjectView(itemTileBox15, "book_2");
        new TileSubjectView(itemTileBox22, "book_3");
        new TileSubjectView(itemTileBox23, "frame_1");
        new TileSubjectView(itemTileBox24, "frame_2");
        new TileSubjectView(itemTileBox25, "frame_3");
        new TileSubjectView(itemTileBox26, "plant_1");
        new TileSubjectView(itemTileBox31, "plant_2");
        new TileSubjectView(itemTileBox32, "plant_3");
        new TileSubjectView(itemTileBox33, "game_1");
        new TileSubjectView(itemTileBox34, "game_2");
        new TileSubjectView(itemTileBox35, "game_3");
        new TileSubjectView(itemTileBox36, "trophy_1");
        new TileSubjectView(itemTileBox37, "trophy_2");
        new TileSubjectView(itemTileBox38, "trophy_3");
    }
}
