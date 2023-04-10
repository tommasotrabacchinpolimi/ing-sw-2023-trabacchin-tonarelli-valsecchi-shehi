package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.listeners.OnPointsUpdatedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PointPlayer<R extends ClientInterface> implements Serializable, OnUpdateNeededListener<R> {
    @Serial
    private static final long serialVersionUID = 973546426438574L;
    private int scoreCommonGoal1;
    private int scoreCommonGoal2;
    private int scoreEndGame;
    private int scorePersonalGoal;
    private int scoreAdjacentGoal;
    private final List<OnPointsUpdatedListener> onPointsUpdatedListeners;
    private Player<R> player;

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

    public int getTotalScore(){
        return scoreAdjacentGoal + scoreEndGame + scorePersonalGoal + scoreCommonGoal1 + scoreCommonGoal2;
    }

    public Player<R> getPlayer() {
        return player;
    }

    public void setPlayer(Player<R> player) {
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
    public void onUpdateNeededListener(Player<R> player) {
        onPointsUpdatedListeners.stream().filter(v->player.getVirtualView() == v).findAny()
                .ifPresentOrElse(v->v.onPointsUpdated(this.player.getNickName(), scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal),()->System.err.println("unable to notify about points updated"));
    }
}
