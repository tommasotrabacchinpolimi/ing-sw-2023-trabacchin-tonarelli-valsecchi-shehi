package it.polimi.ingsw.view.gui.connectioninterface;

import it.polimi.ingsw.view.gui.MyShelfieButton;
import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionPageController extends MyShelfieController {

    @FXML
    private MyShelfieButton submitButton;

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
    private ChoiceBox<String> protocolBox;

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

    private String chosenConnection = null;
    private String serverAddress = null;
    private String portNumber = null;

    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProtocolBoxErrorPseudoClass();
        setServerAddressErrorPseudoClass();
        setPortNumberErrorPseudoClass();
    }

    @FXML
    private void submitButtonClicked(MouseEvent mouseEvent) {
        if(chosenConnection == null) {
            protocolBox.setValue("Missing connection protocol!");
            protocolBox.pseudoClassStateChanged(errorClass, true);
        }

        serverAddress = serverAddressField.getText();

        if(serverAddress == null || serverAddress.equals("")) {
            serverAddressField.pseudoClassStateChanged(errorClass, true);
            serverAddressField.setPromptText("Missing Server Address!");
        }

        portNumber = portNumberField.getText();

        if(portNumber == null || portNumber.equals("")) {
            portNumberField.pseudoClassStateChanged(errorClass, true);
            portNumberField.setPromptText("Missing Port Number!");
        }
    }

    private void setProtocolBoxErrorPseudoClass() {
        protocolBox.getSelectionModel().selectedItemProperty().addListener(
                (selected, oldString, newString) -> {
                    chosenConnection = newString;
                    protocolBox.pseudoClassStateChanged(errorClass, false);
                }
        );
    }

    private void setServerAddressErrorPseudoClass() {
        serverAddressField.textProperty().addListener(event -> {
            serverAddressField.pseudoClassStateChanged(errorClass, serverAddressField.getText().isEmpty());
        });
    }

    private void setPortNumberErrorPseudoClass() {
        portNumberField.textProperty().addListener(event -> {
            portNumberField.pseudoClassStateChanged(errorClass, portNumberField.getText().isEmpty());
        });
    }
}
