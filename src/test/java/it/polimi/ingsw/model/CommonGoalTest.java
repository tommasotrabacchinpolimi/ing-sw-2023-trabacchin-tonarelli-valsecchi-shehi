package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    void getAvailableScore() {
        int numberPlayer = 2;
        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(8);
        CommonGoal goal = new StairCommonGoal(5, "Description");
        goal.initScoringTokens(numberPlayer);
        assertEquals(8, goal.getAvailableScore());
    }

    @Test
    void setAvailableScore() {
        CommonGoal goal = new StairCommonGoal(5, "Description");
        goal.setAvailableScore(6);
        assertEquals(6, goal.getScoringTokens());
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