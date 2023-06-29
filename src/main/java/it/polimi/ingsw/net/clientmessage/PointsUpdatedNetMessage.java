package it.polimi.ingsw.net.clientmessage;

import it.polimi.ingsw.net.ClientMessage;
import it.polimi.ingsw.net.ClientDispatcherInterface;

/**
 * Represents a client message indicating that the scoring of a player has changed.
 * <br>
 * Extends {@link ClientMessage} Interface.
 * <br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class PointsUpdatedNetMessage implements ClientMessage {



    /**
     * The nickname associated with the player.
     */
    private final String nickname;

    /**
     * The updated score for the adjacent goal.
     */
    private final int scoreAdjacentGoal;

    /**
     * The updated score for the first common goal.
     */
    private final int scoreCommonGoal1;

    /**
     * The updated score for the second common goal.
     */
    private final int scoreCommonGoal2;

    /**
     * The updated score for the end game.
     */
    private final int scoreEndGame;

    /**
     * The updated score for the personal goal.
     */
    private final int scorePersonalGoal;


    /**
     * Constructs a PointsUpdatedNetMessage object with the specified parameters.
     *
     * @param nickname          The nickname associated with the player.
     * @param scoreAdjacentGoal The updated score for the adjacent goal.
     * @param scoreCommonGoal1  The updated score for the first common goal.
     * @param scoreCommonGoal2  The updated score for the second common goal.
     * @param scoreEndGame      The updated score for the end game.
     * @param scorePersonalGoal The updated score for the personal goal.
     */
    public PointsUpdatedNetMessage(String nickname, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        this.nickname = nickname;
        this.scoreAdjacentGoal = scoreAdjacentGoal;
        this.scoreCommonGoal1 = scoreCommonGoal1;
        this.scoreCommonGoal2 = scoreCommonGoal2;
        this.scoreEndGame = scoreEndGame;
        this.scorePersonalGoal = scorePersonalGoal;
    }


    /**
     * Retrieves the nickname associated with the player.
     *
     * @return The nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Retrieves the updated score for the adjacent goal.
     *
     * @return The score for the adjacent goal.
     */
    public int getScoreAdjacentGoal() {
        return scoreAdjacentGoal;
    }


    /**
     * Retrieves the updated score for the CommonGoal1.
     *
     * @return The score for the adjacent CommonGoal1.
     */
    public int getScoreCommonGoal1() {
        return scoreCommonGoal1;
    }

    /**
     * Retrieves the updated score for the CommonGoal2.
     *
     * @return The score for the adjacent CommonGoal2.
     */
    public int getScoreCommonGoal2() {
        return scoreCommonGoal2;
    }

    /**
     * Retrieves the updated score for the end game.
     *
     * @return The score for the end game.
     */
    public int getScoreEndGame() {
        return scoreEndGame;
    }

    /**
     * Retrieves the updated score for the personal goal.
     *
     * @return The score for the personal goal.
     */
    public int getScorePersonalGoal() {
        return scorePersonalGoal;
    }

    /**
     * It dispatches the message to the client
     * @param clientDispatcherInterface is the handler of the message.
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
