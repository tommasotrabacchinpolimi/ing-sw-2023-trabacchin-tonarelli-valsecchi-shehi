package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    void getAvailableScore() {
        CommonGoal goal = new StairCommonGoal(5);
        assertEquals(8, goal.getAvailableScore());
    }

    @Test
    void setAvailableScore() {
        CommonGoal goal = new StairCommonGoal(5);
        goal.setAvailableScore(6);
        assertEquals(6, goal.getAvailableScore());
    }

    @Test
    void getDescription() { //da finire
        CommonGoal goal = new StairCommonGoal(5);
        goal.setAvailableScore(6);
        assertEquals(6, goal.getAvailableScore());
    }

    @Test
    void setDescription() {
        CommonGoal goal = new StairCommonGoal(5);
        goal.setDescription("Made-Up Description");
        assertEquals("Made-Up Description", goal.getDescription());
    }
}