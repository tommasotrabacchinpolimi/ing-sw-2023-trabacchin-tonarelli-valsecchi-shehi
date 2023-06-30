package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.ViewData;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.layout.maininterface.MainInterfaceController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>This class is used to launch and interact with the Graphical Interface of the Application</p>
 *
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 */
public class GUILauncher extends MyShelfieApplication {
    /**
     * The layout file path for the login page.
     */
    private static final String LOGIN_PAGE_LAYOUT = "login/login-page.fxml";

    /**
     * The layout file path for the connection page.
     */
    private static final String CONNECTION_PAGE_LAYOUT = "connection/connection-page.fxml";

    /**
     * The layout file path for the main interface.
     */
    private static final String MAIN_INTERFACE_LAYOUT = "maininterface/main-interface.fxml";

    private boolean isFirstPlayerSeatAssigned;

    private boolean isEndGameTokenAssigned;

    /**
     * The GUI instance.
     */
    private GUI gui;

    /**
     * The application initialization method, this method is called immediately
     * after the Application class is loaded and constructed. <br>
     * An application may override this method to perform initialization prior to
     * the actual starting of the application.
     *
     * <p>
     * The implementation of this method provided by the Application class does nothing.
     * </p>
     *
     * <p>
     * NOTE: This method is not called on the JavaFX Application Thread.
     * An application must not construct a Scene or a Stage in this method.
     * An application may construct other JavaFX objects in this method.
     * </p>
     *
     * @throws Exception if something goes wrong
     */
    @Override
    public void init() throws Exception {
        super.init();

        this.gui = new GUI(this);
        this.isFirstPlayerSeatAssigned = false;
        this.isEndGameTokenAssigned = false;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        setupScene(CONNECTION_PAGE_LAYOUT);

        setupStage(stage);

        stage.show();
    }

    /**
     * Shows the "Waiting for other players to join" message.
     */
    public void showWaitForPlayers() {
        showWaitingView("Waiting for other players to join");
    }

    public void showWaitingToReconnect() {
        showWaitingView("Connection lost: trying to reconnect");
    }

    /**
     * Navigates to the login page.
     */
    public void goToLoginPage() {
        changeScene(LOGIN_PAGE_LAYOUT);
    }

    /**
     * Navigates to the main interface.
     */
    public void goToMainInterface() {

        onCloseWindowEventAction(event -> {
            MyShelfieAlertCreator.displayConfirmationAlert().ifPresent(value -> {

                if(value == ButtonType.OK){
                    Platform.exit();
                    System.exit(0);
                }

                event.consume();
            });
        });

        changeToFullScreenStage(MAIN_INTERFACE_LAYOUT);

        try{
            ((MainInterfaceController) fxController).settingUpInterfaceControllers();
        }catch(ClassCastException e) {
            errorInLoadingMyShelfieGame("Could not initialize controllers properly");
        }

        initOpponentInterfaceInformation();
    }

    /**
     * Retrieves the MainInterfaceController from the GUI controller.
     *
     * @return the MainInterfaceController instance.
     * @throws ClassCastException if the controller cannot be cast to MainInterfaceController.
     */
    private MainInterfaceController getMainInterfaceController() throws ClassCastException {
        try {
            return ((MainInterfaceController) fxController);
        } catch (ClassCastException e) {
            errorInLoadingMyShelfieGame("The application was not loaded in an appropriate way");
            throw e;
        }
    }

    /**
     * Initializes the opponent interface information in the MainInterfaceController.
     */
    private void initOpponentInterfaceInformation() {
        getMainInterfaceController().handleOpponentInterfaceInformation();
    }

    /**
     * Sets the GUI instance.
     *
     * @param gui The GUI instance.
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     * Retrieves the GUI instance.
     *
     * @return The GUI instance.
     */
    public GUI getGUI() {
        return gui;
    }

    /**
     * Retrieves the ViewData instance from the GUI model.
     *
     * @return The ViewData instance.
     */
    public ViewData getGUIModel() {
        return getGUI().getModel();
    }

    /**
     * Handles a remote exception by displaying an error alert.
     *
     * @param exception The remote exception message.
     */
    public void handleRemoteException(String exception) {
        MyShelfieAlertCreator.displayErrorAlert(exception);

        if(isInWaiting())
            hideWaitingView();
    }

    /**
     * Handles the living room update by updating the board state in the main interface.
     */
    public void handleLivingRoomUpdate() {
        if (getMainInterfaceController().isBoardEmpty() && getMainInterfaceController().areAllBookshelfEmpty()) {
            Platform.runLater(() -> {
                getGUIModel().getMessages().forEach(this::handleNewMessage);
                getMainInterfaceController().firstTimeFillBoard(getGUIModel().getBoard());
                getMainInterfaceController().firstTimeFillBookshelves(getGUIModel().getBookShelves());
                getMainInterfaceController().showGoalsOperation();
                getMainInterfaceController().playersPointGained();
            });
        } else {
            getMainInterfaceController().transferTilesToOpponent(getGUIModel().getBoard(), getOpponentUpdates());

            getMainInterfaceController().checkThisPlayerUpdate(getGUIModel().getBoard(), getThisPlayerUpdates());
        }
    }

    private Map<String, TileSubject[][]> getOpponentUpdates() {
        return getGUIModel().getBookShelves()
                .entrySet()
                .stream()
                .filter(entry -> !isThisPlayer(entry.getKey()))
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    /**
     * @return {@code null} if the bookshelf of the player using the
     * app is not retrieved from the server
     */
    @Nullable
    private Map.Entry<String, TileSubject[][]> getThisPlayerUpdates() {
        return getGUIModel().getBookShelves()
                .entrySet()
                .stream()
                .filter(entry -> isThisPlayer(entry.getKey()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Handles blocking or enabling game controls in the main interface based on the current player.
     */
    public void handleBlockGameControls() {
        if (!isThisPlayerTurn())
            getMainInterfaceController().blockGameControlsOperation();
        else
            getMainInterfaceController().enableGameControlsOperation();
    }

    private boolean isThisPlayerTurn() {
        return isThisPlayer(getGUIModel().getCurrentPlayer());
    }

    /**
     * Manages the main interface by navigating to it.
     */
    public void manageMainInterface() {
        goToMainInterface();
    }

    /**
     * Handles a new message by passing it to the main interface controller.
     *
     * @param lastMessage The last received message.
     */
    public void handleNewMessage(@NotNull Triple<String, List<String>, String> lastMessage) {
        getMainInterfaceController().receivedMessageOperation(lastMessage);
    }

    public void handleInfoTextDisplay() {
        getMainInterfaceController().showUpdatesInDisplayContentOperation(getGUIModel().getCurrentPlayer());
    }

    public void handleFirstPlayerSeatAssignment() {
        if(!isFirstPlayerSeatAssigned) {
            getMainInterfaceController().addFirstPlayerSeatOperation(getGUIModel().getPlayers().get(0));
            isFirstPlayerSeatAssigned = true;
        }
    }

    public void handleAssignedCommonGoals() {
        handleAssignedCommonGoal1();
        handleAssignedCommonGoal2();
    }

    private void handleAssignedCommonGoal1() {
        if(!getGUIModel().getAvailableScores().get(0).equals(getMainInterfaceController().requestDisplayedCommonGoalScore(0))) {
            getPlayerAchievedCommonGoal(1).ifPresent(nickName ->
                    getMainInterfaceController().assignScoringToken1(nickName));
        }
    }

    private void handleAssignedCommonGoal2() {
        if(!getGUIModel().getAvailableScores().get(1).equals(getMainInterfaceController().requestDisplayedCommonGoalScore(1))) {
            getPlayerAchievedCommonGoal(2).ifPresent(nickName ->
                    getMainInterfaceController().assignScoringToken2(nickName));
        }
    }

    private Optional<String> getPlayerAchievedCommonGoal(int commonGoalNumber) {

        return getGUIModel().getPlayersPoints()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().get(commonGoalNumber).equals(getMainInterfaceController().requestDisplayedCommonGoalScore((commonGoalNumber - 1))))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public void handleAssignedEndToken() {
        if(!isEndGameTokenAssigned) {
            getPlayerAchievedEndGame().ifPresent(nickName -> {
                getMainInterfaceController().assignEndGameToken(nickName);
                isEndGameTokenAssigned = true;
            });
        }
    }

    @NotNull
    private Optional<String> getPlayerAchievedEndGame() {

        return getGUIModel().getPlayersPoints()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().get(3).equals(1))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public void showWinningPageOperation() {
        showWinningPage(getGUIModel().getWinnerPlayer(), getPlayersPointMap(), isThisPlayerWinning());
    }

    public boolean isThisPlayerWinning() {
        return isThisPlayer(getGUIModel().getThisPlayer());
    }

    public boolean isThisPlayer(String playerNick) {
        return getGUIModel().getThisPlayer().equals(playerNick);
    }

    @NotNull
    @Contract(pure = true)
    private Map<String, Integer> getPlayersPointMap() {
        Map<String, Integer> playerPointMap = new HashMap<>();

        getGUIModel().getPlayers().forEach(nickName -> {
            playerPointMap.put(nickName, getGUIModel().getTotalPointByNickname(nickName));
        });

        return playerPointMap;
    }
}
