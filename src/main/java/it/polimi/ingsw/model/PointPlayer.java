package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.listeners.OnPointsUpdatedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PointPlayer implements Serializable, OnUpdateNeededListener {
    @Serial
    private static final long serialVersionUID = 973546426438574L;
    private int scoreCommonGoal1;
    private int scoreCommonGoal2;
    private int scoreEndGame;
    private int scorePersonalGoal;
    private int scoreAdjacentGoal;
    private final List<OnPointsUpdatedListener> onPointsUpdatedListeners;
    private Player player;

    public PointPlayer(){
        scoreAdjacentGoal = 0;
        scoreCommonGoal1 = 0;
        scoreCommonGoal2 = 0;
        scoreEndGame = 0;
        scorePersonalGoal = 0;
        onPointsUpdatedListeners = new LinkedList<>();
    }

    public int getScoreCommonGoal1() {
        return scoreCommonGoal1;
    }

    public void setScoreCommonGoal1(int scoreCommonGoal1) {
        this.scoreCommonGoal1 = scoreCommonGoal1;
        notifyOnPointUpdated();
    }

    public int getScoreCommonGoal2() {
        return scoreCommonGoal2;
    }

    public void setScoreCommonGoal2(int getScoreCommonGoal2) {
        this.scoreCommonGoal2 = getScoreCommonGoal2;
        notifyOnPointUpdated();
    }

    public int getScoreEndGame() {
        return scoreEndGame;
    }

    public void setScoreEndGame(int scoreEndGame) {
        this.scoreEndGame = scoreEndGame;
        notifyOnPointUpdated();
    }

    public int getScorePersonalGoal() {
        return scorePersonalGoal;
    }

    public void setScorePersonalGoal(int scorePersonalGoal) {
        this.scorePersonalGoal = scorePersonalGoal;
        notifyOnPointUpdated();
    }

    public int getScoreAdjacentGoal() {
        return scoreAdjacentGoal;
    }

    public void setScoreAdjacentGoal(int scoreAdjacentGoal) {
        this.scoreAdjacentGoal = scoreAdjacentGoal;
        notifyOnPointUpdated();
    }

    public Integer getTotalScore(){
        return Integer.valueOf(scoreAdjacentGoal + scoreEndGame + scorePersonalGoal + scoreCommonGoal1 + scoreCommonGoal2);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void notifyOnPointUpdated() {
        for(OnPointsUpdatedListener onPointsUpdatedListener : onPointsUpdatedListeners) {
            onPointsUpdatedListener.onPointsUpdated(player.getNickName(), scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        }
    }
    public void setOnPointsUpdatedListener(OnPointsUpdatedListener onPointsUpdatedListener) {
        onPointsUpdatedListeners.add(onPointsUpdatedListener);
    }

    public void removeOnPointsUpdatedListener(OnPointsUpdatedListener onPointsUpdatedListener) {
        onPointsUpdatedListeners.remove(onPointsUpdatedListener);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PointPlayer that = (PointPlayer) o;

        return scoreCommonGoal1 == that.scoreCommonGoal1 &&
                scoreCommonGoal2 == that.scoreCommonGoal2 &&
                scoreEndGame == that.scoreEndGame &&
                scorePersonalGoal == that.scorePersonalGoal &&
                scoreAdjacentGoal == that.scoreAdjacentGoal &&
                Objects.equals(onPointsUpdatedListeners, that.onPointsUpdatedListeners) && Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal, scoreAdjacentGoal, onPointsUpdatedListeners, player);
    }

    @Override
    public void onUpdateNeededListener(Player player) {
        onPointsUpdatedListeners.stream().filter(v->player.getVirtualView() == v).findAny()
                .ifPresentOrElse(v->v.onPointsUpdated(this.player.getNickName(), scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal),()->System.err.println("unable to notify about points updated"));
    }
}
