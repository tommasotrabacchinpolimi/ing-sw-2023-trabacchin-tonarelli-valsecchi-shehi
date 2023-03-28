package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    void getAvailableScore() {
        CommonGoal goal = new StairCommonGoal(5, "Description");
        assertEquals(8, goal.getAvailableScore());
    }

    @Test
    void setAvailableScore() {
        CommonGoal goal = new StairCommonGoal(5, "Description");
        goal.setAvailableScore(6);
        assertEquals(6, goal.getAvailableScore());
    }

    @Test
    void getDescription() {
        CommonGoal goal = new StairCommonGoal(5, "Description added.");
        goal.setAvailableScore(6);
        assertEquals("Description added.", goal.getDescription());
    }

    @Test
    void setDescription() {
        CommonGoal goal = new StairCommonGoal(5, "Description");
        goal.setDescription("Made-Up Description");
        assertEquals("Made-Up Description", goal.getDescription());
    }
}