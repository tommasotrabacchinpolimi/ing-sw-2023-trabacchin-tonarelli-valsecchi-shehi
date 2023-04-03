package it.polimi.ingsw.model;

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
    void getBookShelf() {
    }

    @Test
    void setBookShelf() {
    }

    @Test
    void assignScoreEndGame() {
    }

    @Test
    void getPointPlayer() {
    }

    @Test
    void setPointPlayer() {
    }

    @Test
    void testGetNickName() {
    }

    @Test
    void testGetPersonalGoal() {
    }

    @Test
    void testToString() {
        PersonalGoal goal = new PersonalGoal();
        Player player = new Player("Melanie", goal);
        String expected = "Player{nickname=Melanie}";
        assertEquals(expected, player.toString());
    }
}