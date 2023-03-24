package model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getNickName() {
        PersonalGoal goal = new PersonalGoal();
        Player player = new Player("Melanie", goal);
        assertEquals("Melanie", player.getNickName());
    }

    @Test
    void getPersonalGoal() {
        PersonalGoal goal = new PersonalGoal();
        Player player = new Player("Tommaso", goal);
        assertEquals(goal.toString(), player.getPersonalGoal().toString());
    }

    @Test
    void getScore() {
        PersonalGoal goal = new PersonalGoal();
        Player player = new Player("Emanuele", goal);
        assertEquals(0, player.getScore());
    }

    @Test
    void setScore() {
        PersonalGoal goal = new PersonalGoal();
        Random rnd = new Random();
        int score = rnd.nextInt();
        Player player = new Player("Adem", goal);
        player.setScore(score);
        assertEquals(score, player.getScore());
    }
}