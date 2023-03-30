package it.polimi.ingsw.model;

public class PointPlayer {
    private int scoreCommonGoal1;
    private int scoreCommonGoal2;
    private int scoreEndGame;
    private int scorePersonalGoal;
    private int scoreAdjacentGoal;

    public PointPlayer(){
        scoreAdjacentGoal = 0;
        scoreCommonGoal1 = 0;
        scoreCommonGoal2 = 0;
        scoreEndGame = 0;
        scorePersonalGoal = 0;
    }

    public int getScoreCommonGoal1() {
        return scoreCommonGoal1;
    }

    public void setScoreCommonGoal1(int scoreCommonGoal1) {
        this.scoreCommonGoal1 = scoreCommonGoal1;
    }

    public int getScoreCommonGoal2() {
        return scoreCommonGoal2;
    }

    public void setScoreCommonGoal2(int getScoreCommonGoal2) {
        this.scoreCommonGoal2 = getScoreCommonGoal2;
    }

    public int getScoreEndGame() {
        return scoreEndGame;
    }

    public void setScoreEndGame(int scoreEndGame) {
        this.scoreEndGame = scoreEndGame;
    }

    public int getScorePersonalGoal() {
        return scorePersonalGoal;
    }

    public void setScorePersonalGoal(int scorePersonalGoal) {
        this.scorePersonalGoal = scorePersonalGoal;
    }

    public int getScoreAdjacentGoal() {
        return scoreAdjacentGoal;
    }

    public void setScoreAdjacentGoal(int scoreAdjacentGoal) {
        this.scoreAdjacentGoal = scoreAdjacentGoal;
    }

    public int getTotalScore(){
        return scoreAdjacentGoal + scoreEndGame + scorePersonalGoal + scoreCommonGoal1 + scoreCommonGoal2;
    }
}
