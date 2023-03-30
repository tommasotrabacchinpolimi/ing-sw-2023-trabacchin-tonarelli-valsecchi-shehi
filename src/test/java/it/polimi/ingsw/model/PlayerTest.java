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
}