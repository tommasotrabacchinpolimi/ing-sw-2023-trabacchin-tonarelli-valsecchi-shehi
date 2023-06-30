package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.ViewData;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.layout.loginpage.LoginPageController;
import it.polimi.ingsw.view.gui.layout.maininterface.MainInterfaceController;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
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
 * <p>
 * The GameEngine class represents the core engine of a game. It manages the game logic and facilitates
 * communication between different components of the game. This class handles various operations such as
 * player management, game state tracking, and game flow control. It provides methods to start the game,
 * handle player actions, and manage the overall gameplay. The GameEngine class serves as the central
 * component that drives the execution of the game and ensures its smooth functioning.
 * </p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
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

    /**
     * Indicates whether the first player's seat has been assigned.
     */
    private boolean isFirstPlayerSeatAssigned;

    /**
     * Indicates whether the end game token has been assigned.
     */
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

    /**
     * Shows a waiting view indicating that the connection is lost and the system is trying to reconnect.
     */
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

                if (value == ButtonType.OK) {
                    getGUI().getLogicController().quitGame();

                    PauseTransition pauseTransition = new PauseTransition();
                    pauseTransition.setDuration(Duration.seconds(1));
                    pauseTransition.playFromStart();

                    pauseTransition.setOnFinished(onFinishedEvent -> {
                        Platform.exit();
                        System.exit(0);
                    });
                }

                event.consume();
            });
        });

        changeToFullScreenStage(MAIN_INTERFACE_LAYOUT);

        try {
            ((MainInterfaceController) fxController).settingUpInterfaceControllers();
        } catch (ClassCastException e) {
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

        if (isInWaiting())
            hideWaitingView();

        try {
            ((LoginPageController) fxController).restoreNickNameInputGUI();
        } catch (ClassCastException ignored) {}
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

    /**
     * Retrieves the updates of opponents' bookshelves.
     *
     * @return A map containing the updates of opponents' bookshelves. The keys represent the nicknames of the opponents,
     *         and the values are two-dimensional arrays of TileSubject objects representing their bookshelves.
     */
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

    /**
     * Checks if it is the turn of the current player.
     *
     * @return {@code true} if it is the turn of the current player, {@code false} otherwise.
     */
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


    /**
     * Handles the display of information text in the main interface.
     */
    public void handleInfoTextDisplay() {
        getMainInterfaceController().showUpdatesInDisplayContentOperation(getGUIModel().getCurrentPlayer());
    }

    /**
     * Handles the assignment of the first player seat.
     * If the seat has not been assigned yet, it adds the first player seat operation.
     *
     * @see MainInterfaceController#addFirstPlayerSeatOperation(String)
     */
    public void handleFirstPlayerSeatAssignment() {
        if (!isFirstPlayerSeatAssigned) {
            getMainInterfaceController().addFirstPlayerSeatOperation(getGUIModel().getPlayers().get(0));
            isFirstPlayerSeatAssigned = true;
        }
    }

    /**
     * Handles the assignment of the common goals.
     * It calls the respective methods to handle the assignment of common goal 1 and common goal 2.
     *
     * @see #handleAssignedCommonGoal1()
     * @see #handleAssignedCommonGoal2()
     */
    public void handleAssignedCommonGoals() {
        handleAssignedCommonGoal1();
        handleAssignedCommonGoal2();
    }

    /**
     * Handles the assignment of common goal 1.
     * If the available score of common goal 1 is not equal to the displayed common goal score at index 0,
     * it retrieves the player who achieved the common goal and calls the assignScoringToken1 operation in the main interface controller.
     *
     * @see MainInterfaceController#assignScoringToken1(String)
     */
    private void handleAssignedCommonGoal1() {
        if (!getGUIModel().getAvailableScores().get(0).equals(getMainInterfaceController().requestDisplayedCommonGoalScore(0))) {
            getPlayerAchievedCommonGoal(1).ifPresent(nickName ->
                    getMainInterfaceController().assignScoringToken1(nickName));
        }
    }


    /**
     * Handles the assignment of common goal 2.
     * If the available score of common goal 2 is not equal to the displayed common goal score at index 1,
     * it retrieves the player who achieved the common goal and calls the assignScoringToken2 operation in the main interface controller.
     *
     * @see MainInterfaceController#assignScoringToken2(String)
     */
    private void handleAssignedCommonGoal2() {
        if (!getGUIModel().getAvailableScores().get(1).equals(getMainInterfaceController().requestDisplayedCommonGoalScore(1))) {
            getPlayerAchievedCommonGoal(2).ifPresent(nickName ->
                    getMainInterfaceController().assignScoringToken2(nickName));
        }
    }

    /**
     * Retrieves the player who achieved the specified common goal.
     *
     * @param commonGoalNumber The common goal number (1 or 2).
     * @return The nickname of the player who achieved the common goal, or empty if no player achieved it.
     */
    private Optional<String> getPlayerAchievedCommonGoal(int commonGoalNumber) {

        return getGUIModel().getPlayersPoints()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().get(commonGoalNumber).equals(getMainInterfaceController().requestDisplayedCommonGoalScore((commonGoalNumber - 1))))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    /**
     * Handles the assignment of the end game token.
     * If the end game token has not been assigned yet, it retrieves the player who achieved the end game condition
     * and calls the assignEndGameToken operation in the main interface controller.
     *
     * @see MainInterfaceController#assignEndGameToken(String)
     */
    public void handleAssignedEndToken() {
        if (!isEndGameTokenAssigned) {
            getPlayerAchievedEndGame().ifPresent(nickName -> {
                getMainInterfaceController().assignEndGameToken(nickName);
                isEndGameTokenAssigned = true;
            });
        }
    }

    /**
     * Retrieves the player who achieved the end game condition.
     *
     * @return The nickname of the player who achieved the end game condition, or empty if no player achieved it.
     */
    @NotNull
    private Optional<String> getPlayerAchievedEndGame() {

        return getGUIModel().getPlayersPoints()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().get(3).equals(1))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    /**
     * Shows the winning page in the main interface with the winner player, players' point map,
     * and whether the current player is winning.
     */
    public void showWinningPageOperation() {
        showWinningPage(getGUIModel().getWinnerPlayer(), getPlayersPointMap(), isThisPlayerWinning());
    }

    /**
     * Checks if the current player is winning.
     *
     * @return {@code true} if the current player is winning, {@code false} otherwise.
     */
    public boolean isThisPlayerWinning() {
        return isThisPlayer(getGUIModel().getThisPlayer());
    }

    /**
     * Checks if the given player nickname is the current player.
     *
     * @param playerNick The nickname of the player to check.
     * @return {@code true} if the given player nickname is the current player, {@code false} otherwise.
     */
    public boolean isThisPlayer(String playerNick) {
        return getGUIModel().getThisPlayer().equals(playerNick);
    }

    /**
     * Retrieves the players' point map containing each player's nickname and their total points.
     *
     * @return The map of players' nicknames and their corresponding total points.
     */
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