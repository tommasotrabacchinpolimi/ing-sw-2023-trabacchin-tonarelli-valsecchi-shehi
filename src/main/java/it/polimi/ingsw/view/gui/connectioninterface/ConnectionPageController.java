package it.polimi.ingsw.view.gui.connectioninterface;

import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionPageController extends MyShelfieController {

    @FXML
    private StackPane rootPane;

    @FXML
    private StackPane backgroundColorHover;

    @FXML
    private StackPane gridStackContainer;

    @FXML
    private GridPane contentGrid;

    @FXML
    private Label connectionLabel;

    @FXML
    private ChoiceBox protocolBox;

    @FXML
    private String SocketChoice;

    @FXML
    private String RMIChoice;

    @FXML
    private Label serverLabel;

    @FXML
    private TextField serverAddressField;

    @FXML
    private Label portLabel;

    @FXML
    private TextField portNumberField;

    private String chosenConnection;
    private String serverAddress;
    private String portNumber;

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
