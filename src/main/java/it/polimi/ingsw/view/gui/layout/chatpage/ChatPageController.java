package it.polimi.ingsw.view.gui.layout.chatpage;


import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatPageController extends MyShelfieController {
    public AnchorPane mainContainer;
    public GridPane pageMainDivider;
    public GridPane chatAndControlsDivider;
    public AnchorPane chatWoodContainer;
    public GridPane chatElementsContainer;
    public GridPane controlsDivider;
    public GridPane playerListContainer;
    public GridPane buttonContainer;
    public ImageView icon2;
    public ImageView icon3;
    public ImageView icon1;
    public StackPane UpperContainer;
    public StackPane scrollAndTextContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //per il server va bene l'operazione che si è fatta l'operazione da il "permesso" di cambiare anche a livello grafico
    @Override
    public void onGameStateChangedNotified() {

    }

    //Questo invece serve per l'opposto se ho eccezione a lato server, qui viene mostrata l'eccezione.
    @Override
    public void onExceptionNotified() {

    }

}