package it.polimi.ingsw.view.gui.layout.connectioninterface;

import it.polimi.ingsw.view.gui.*;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator.*;

public class ConnectionPageController extends MyShelfieController {

    @FXML
    private StackPane connectionRootPane;

    @FXML
    private MyShelfieButton connectionSubmitButton;

    @FXML
    private StackPane connectionBackgroundColorHover;

    @FXML
    private StackPane connectionGridStackContainer;

    @FXML
    private GridPane connectionContentGrid;

    @FXML
    private Label connectionLabel;

    @FXML
    private ChoiceBox<String> connectionProtocolBox;

    @FXML
    private String SocketChoice;

    @FXML
    private String RMIChoice;

    @FXML
    private Label connectionServerLabel;

    @FXML
    private TextField connectionServerAddressField;

    @FXML
    private Label connectionPortLabel;

    @FXML
    private TextField connectionPortNumberField;

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
        setProtocolBoxPseudoClassState();
        setServerAddressPseudoClassState();
        setPortNumberPseudoClassState();
    }

    @FXML
    private void submitButtonClicked(MouseEvent mouseEvent) {
        if(chosenConnection == null) {
            connectionProtocolBox.setValue("Missing connection protocol!");
            connectionProtocolBox.pseudoClassStateChanged(errorClass, true);
        }

        serverAddress = connectionServerAddressField.getText();

        if(serverAddress == null || serverAddress.equals("")) {
            connectionServerAddressField.pseudoClassStateChanged(errorClass, true);
            connectionServerAddressField.setPromptText("Missing Server Address!");
        }

        portNumber = connectionPortNumberField.getText();

        if(portNumber == null || portNumber.equals("")) {
            connectionPortNumberField.pseudoClassStateChanged(errorClass, true);
            connectionPortNumberField.setPromptText("Missing Port Number!");
        }

        if(chosenConnection != null && !serverAddress.equals("") && !portNumber.equals("")) {

            try {
                GUILauncher guiLauncher = getGUILauncher();

                if(chosenConnection.equals("Remote Methode Invocation"))
                    guiLauncher.getGUI().getLogicController().chosenRMI(Integer.parseInt(portNumber), serverAddress);
                else if(chosenConnection.equals("Socket"))
                    guiLauncher.getGUI().getLogicController().chosenSocket(Integer.parseInt(portNumber), serverAddress);

                guiLauncher.goToLoginPage();

            }catch (NotBoundException | IOException | ClassNotFoundException e) {
                displayErrorAlert("Can't establish connection with server", "Connection Error");
            }
        }
    }

    @FXML
    private void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            connectionRootPane.requestFocus();
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if((keyEvent.getSource()).equals(connectionServerAddressField) && connectionPortNumberField.getText().isEmpty()) {
                connectionPortNumberField.requestFocus();
            }

            if((keyEvent.getSource()).equals(portNumber) && connectionServerAddressField.getText().isEmpty()) {
                connectionServerAddressField.requestFocus();
            }

            if(((keyEvent.getSource()).equals(connectionServerAddressField) && !connectionPortNumberField.getText().isEmpty()) ||
                    ((keyEvent.getSource()).equals(portNumber) && !connectionServerAddressField.getText().isEmpty())) {
                connectionSubmitButton.requestFocus();
            }
        }
    }

    private void setProtocolBoxPseudoClassState() {
        connectionProtocolBox.getSelectionModel().selectedItemProperty().addListener(
                (selected, oldString, newString) -> {
                    chosenConnection = newString;
                    connectionProtocolBox.pseudoClassStateChanged(errorClass, false);
                }
        );
    }

    private void setServerAddressPseudoClassState() {
        connectionServerAddressField.textProperty().addListener(event -> {
            connectionServerAddressField.pseudoClassStateChanged(errorClass, connectionServerAddressField.getText().isEmpty());
        });
    }

    private void setPortNumberPseudoClassState() {
        connectionPortNumberField.textProperty().addListener(event -> {
            connectionPortNumberField.pseudoClassStateChanged(errorClass, verifyPortNumber());

            connectionSubmitButton.setDisable(verifyPortNumber());
        });
    }

    public void connectionSubmitted(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
            submitButtonClicked(null);
    }

    private boolean verifyPortNumber() {
        return connectionPortNumberField.getText().isEmpty() || invalidNumberInput();
    }

    private boolean invalidNumberInput() {
        if(connectionPortNumberField.getText().isEmpty())
            return true;

        try {
            Integer.parseInt(connectionPortNumberField.getText());
            return false;
        } catch (NumberFormatException nfe) {
            return true;
        }
    }
}
