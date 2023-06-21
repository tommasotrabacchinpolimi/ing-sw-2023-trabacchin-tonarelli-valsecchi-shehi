package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class PointsUpdatedNetMessage implements ClientMessage {

    private String nickname;

    private int scoreAdjacentGoal;

    private int scoreCommonGoal1;

    private int scoreCommonGoal2;

    private int scoreEndGame;

    private int scorePersonalGoal;

    public PointsUpdatedNetMessage(String nickname, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        this.nickname = nickname;
        this.scoreAdjacentGoal = scoreAdjacentGoal;
        this.scoreCommonGoal1 = scoreCommonGoal1;
        this.scoreCommonGoal2 = scoreCommonGoal2;
        this.scoreEndGame = scoreEndGame;
        this.scorePersonalGoal = scorePersonalGoal;
    }


    public String getNickname() {
        return nickname;
    }

    public int getScoreAdjacentGoal() {
        return scoreAdjacentGoal;
    }

    public int getScoreCommonGoal1() {
        return scoreCommonGoal1;
    }

    public int getScoreCommonGoal2() {
        return scoreCommonGoal2;
    }

    public int getScoreEndGame() {
        return scoreEndGame;
    }

    public int getScorePersonalGoal() {
        return scorePersonalGoal;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
