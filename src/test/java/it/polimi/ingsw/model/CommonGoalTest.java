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
        stack.push(8);
        stack.push(4);
        Stack<Integer> expStack = new Stack<>();
        expStack.push(8);
        expStack.push(4);

        CommonGoal goal = new StairCommonGoal(stack,"Description",5);

        assertEquals(expStack.pop(), goal.getAvailableScore());
        assertEquals(expStack.pop(), goal.getAvailableScore());
    }

    @Test
    void getDescription() {
        CommonGoal goal = new StairCommonGoal("Description added.", 5);
        assertEquals("Description added.", goal.getDescription());
    }

    @Test
    void testGetScoringTokens() {
        Stack<Integer> stack = new Stack<>();
        stack.push(8);
        stack.push(6);
        stack.push(4);
        stack.push(2);

        Stack<Integer> expStack = new Stack<>();
        expStack.push(8);
        expStack.push(6);
        expStack.push(4);
        expStack.push(2);

        CommonGoal commonGoal = new CommonGoal(stack) {
            @Override
            public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
                return null;
            }
        };

        assertEquals(expStack.size(), commonGoal.getScoringTokens().size());

        for(int i = stack.size(); i > 0; i--) {
            assertEquals(expStack.pop(), commonGoal.getAvailableScore());
        }
    }
}