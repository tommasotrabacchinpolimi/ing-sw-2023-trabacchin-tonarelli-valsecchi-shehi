package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.listeners.OnPointsUpdatedListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointPlayerTest<R extends ClientInterface> {

    @Test
    void getScoreCommonGoal1() {
        PointPlayer pointPlayer = new PointPlayer();
        assertEquals(0,pointPlayer.getScoreCommonGoal1());
    }

    @Test
    void setScoreCommonGoal1() {
        PointPlayer pointPlayer = new PointPlayer();
        int score = 2;
        pointPlayer.setScoreCommonGoal1(score);
        assertEquals(score, pointPlayer.getScoreCommonGoal1());
    }

    @Test
    void getScoreCommonGoal2() {
        PointPlayer pointPlayer = new PointPlayer();
        assertEquals(0,pointPlayer.getScoreCommonGoal2());
    }

    @Test
    void setScoreCommonGoal2() {
        PointPlayer pointPlayer = new PointPlayer();
        int score = 2;
        pointPlayer.setScoreCommonGoal2(score);
        assertEquals(score, pointPlayer.getScoreCommonGoal2());
    }

    @Test
    void getScoreEndGame() {
        PointPlayer pointPlayer = new PointPlayer();
        assertEquals(0, pointPlayer.getScoreEndGame());
    }

    @Test
    void setScoreEndGame() {
        PointPlayer pointPlayer = new PointPlayer();
        int score = 2;
        pointPlayer.setScoreEndGame(score);
        assertEquals(score, pointPlayer.getScoreEndGame());
    }

    @Test
    void getScorePersonalGoal() {
        PointPlayer pointPlayer = new PointPlayer();
        assertEquals(0, pointPlayer.getScorePersonalGoal());
    }

    @Test
    void setScorePersonalGoal() {
        PointPlayer pointPlayer = new PointPlayer();
        int score = 4;
        pointPlayer.setScorePersonalGoal(score);
        assertEquals(score, pointPlayer.getScorePersonalGoal());
    }

    @Test
    void getScoreAdjacentGoal() {
        PointPlayer pointPlayer = new PointPlayer();
        assertEquals(0, pointPlayer.getScoreAdjacentGoal());
    }

    @Test
    void setScoreAdjacentGoal() {
        PointPlayer pointPlayer = new PointPlayer();
        int score = 5;
        pointPlayer.setScoreAdjacentGoal(score);
        assertEquals(score, pointPlayer.getScoreAdjacentGoal());
    }

    @Test
    void getTotalScore() {
        PointPlayer pointPlayer = new PointPlayer();
        int common1score = 2, common2score = 3, endscore = 0, personalscore = 4, adjacentscore = 2;
        int sum = common1score+common2score+endscore+personalscore+adjacentscore;
        pointPlayer.setScoreCommonGoal1(common1score);
        pointPlayer.setScoreCommonGoal2(common2score);
        pointPlayer.setScoreEndGame(endscore);
        pointPlayer.setScorePersonalGoal(personalscore);
        pointPlayer.setScoreAdjacentGoal(adjacentscore);
        assertEquals(sum, pointPlayer.getTotalScore().intValue());
    }

    @Test
    void getAndSetPlayer(){
        Player player = new Player("Melanie");
        PointPlayer pointPlayer = new PointPlayer();
        pointPlayer.setPlayer(player);
        assertEquals(player, pointPlayer.getPlayer());
    }

    @Test
    void TestEquals(){
        PointPlayer pointPlayer1 = new PointPlayer();
        PointPlayer pointPlayer2 = new PointPlayer();
        Player p1 = new Player("Adem");
        Player p2 = new Player("Tommaso");
        pointPlayer1.setPlayer(p1);
        pointPlayer2.setPlayer(p1);
        assertEquals(pointPlayer1, pointPlayer1);
        assertNotEquals(pointPlayer1, null);
        assertEquals(pointPlayer1, pointPlayer2);
        pointPlayer2.setPlayer(p2);
        assertNotEquals(pointPlayer1, pointPlayer2);
        pointPlayer2.setPlayer(p1);
        pointPlayer2.setScoreEndGame(1);
        assertNotEquals(pointPlayer1, pointPlayer2);
    }

    @Test
    void onPointsUpdatedListener(){
        PointPlayer pointPlayer = new PointPlayer();
        Player p = new Player("P");
        OnPointsUpdatedListener listener = new OnPointsUpdatedListener() {
            @Override
            public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
                assertEquals("P", nickName);
                assertEquals(0, scoreAdjacentGoal);
                assertEquals(0, scoreCommonGoal1);
                assertEquals(0, scoreCommonGoal2);
                assertEquals(1, scoreEndGame);
                assertEquals(0, scorePersonalGoal);
            }
        };
        pointPlayer.setPlayer(p);
        p.setPointPlayer(pointPlayer);
        pointPlayer.setOnPointsUpdatedListener(listener);
        pointPlayer.setScoreEndGame(1);
        pointPlayer.removeOnPointsUpdatedListener(listener);
    }
}