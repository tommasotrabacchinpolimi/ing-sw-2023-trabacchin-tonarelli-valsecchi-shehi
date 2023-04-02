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
        assertEquals(stack.pop(), goal.getAvailableScore());
        assertEquals(stack.pop(), goal.getAvailableScore());
    }

    @Test
    void getDescription() {
        CommonGoal goal = new StairCommonGoal(5, "Description added.");
        goal.initScoringTokens(4);
        assertEquals("Description added.", goal.getDescription());
    }

    @Test
    void initScoringTokens() {
        int numberPlayer = 3;
        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(6);
        stack.push(8);
        CommonGoal goal = new StairCommonGoal(5, "Description");
        goal.initScoringTokens(numberPlayer);
        for(int i = 0; i < numberPlayer; i++){
            assertEquals(stack.pop(), goal.getAvailableScore());
        }
        numberPlayer = 4;
        stack.push(2);
        stack.push(4);
        stack.push(6);
        stack.push(8);
        goal.initScoringTokens(numberPlayer);
        for(int i = 0; i < numberPlayer; i++){
            assertEquals(stack.pop(), goal.getAvailableScore());
        }
    }
}