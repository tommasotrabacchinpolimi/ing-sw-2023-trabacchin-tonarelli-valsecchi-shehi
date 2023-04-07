package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    void getAvailableScore() {
        int numberPlayer = 2;

        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(8);

        CommonGoal goal = new StairCommonGoal(5);

        assertEquals(stack.pop(), goal.getAvailableScore());
        assertEquals(stack.pop(), goal.getAvailableScore());
    }

    @Test
    void getDescription() {
        CommonGoal goal = new StairCommonGoal("Description added.", 5);
        assertEquals("Description added.", goal.getDescription());
    }

    @Test
    void testGetScoringTokens() {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        stack.push(4);
        stack.push(6);
        stack.push(8);

        CommonGoal commonGoal = new CommonGoal(stack) {
            @Override
            public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
                return null;
            }
        };

        assertEquals(4, commonGoal.getScoringTokens().size());

        for(Integer i : commonGoal.getScoringTokens()) {
            assertEquals(i, stack.pop());
        }
    }
}