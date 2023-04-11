package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointPlayerTest<R extends ClientInterface> {

    @Test
    void getScoreCommonGoal1() {
        PointPlayer<R> pointPlayer = new PointPlayer<R>();
        assertEquals(0,pointPlayer.getScoreCommonGoal1());
    }

    @Test
    void setScoreCommonGoal1() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        int score = 2;
        pointPlayer.setScoreCommonGoal1(score);
        assertEquals(score, pointPlayer.getScoreCommonGoal1());
    }

    @Test
    void getScoreCommonGoal2() {
        PointPlayer<R> pointPlayer = new PointPlayer<R>();
        assertEquals(0,pointPlayer.getScoreCommonGoal2());
    }

    @Test
    void setScoreCommonGoal2() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        int score = 2;
        pointPlayer.setScoreCommonGoal2(score);
        assertEquals(score, pointPlayer.getScoreCommonGoal2());
    }

    @Test
    void getScoreEndGame() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        assertEquals(0, pointPlayer.getScoreEndGame());
    }

    @Test
    void setScoreEndGame() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        int score = 2;
        pointPlayer.setScoreCommonGoal1(score);
        assertEquals(score, pointPlayer.getScoreEndGame());
    }

    @Test
    void getScorePersonalGoal() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        assertEquals(0, pointPlayer.getScorePersonalGoal());
    }

    @Test
    void setScorePersonalGoal() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        int score = 4;
        pointPlayer.setScorePersonalGoal(score);
        assertEquals(score, pointPlayer.getScorePersonalGoal());
    }

    @Test
    void getScoreAdjacentGoal() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        assertEquals(0, pointPlayer.getScoreAdjacentGoal());
    }

    @Test
    void setScoreAdjacentGoal() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        int score = 5;
        pointPlayer.setScoreAdjacentGoal(score);
        assertEquals(score, pointPlayer.getScoreAdjacentGoal());
    }

    @Test
    void getTotalScore() {
        PointPlayer<R> pointPlayer = new PointPlayer<>();
        int common1score = 2, common2score = 3, endscore = 0, personalscore = 4, adjacentscore = 2;
        int sum = common1score+common2score+endscore+personalscore+adjacentscore;
        pointPlayer.setScoreCommonGoal1(common1score);
        pointPlayer.setScoreCommonGoal2(common2score);
        pointPlayer.setScoreEndGame(endscore);
        pointPlayer.setScorePersonalGoal(personalscore);
        pointPlayer.setScoreAdjacentGoal(adjacentscore);
        assertEquals(sum, pointPlayer.getTotalScore());
    }
}