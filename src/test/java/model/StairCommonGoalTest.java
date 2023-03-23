package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StairCommonGoalTest {

    @Test
    void rule() {
        int numberOfColumns = 5, index;
        Random rnd = new Random();
        StairCommonGoal goal = new StairCommonGoal(numberOfColumns);
        index = rnd.nextInt(1,7);

        if (index >= 4) {
            assertNull(getExpectedResult(index));
        } else {
            for (int i = 0; i < getExpectedResult(index).size(); i++) {
                assertEquals(getExpectedResult(index).get(i).toString(),
                        goal.rule(createStairDesign(index)).get(i).toString());
            }
        }
    }

    private TileType[][] createStairDesign(int number){
        // case 1
        switch(number){
            case 1 -> {
                return new TileType[][]{
                        {null,  null, null, null, null},
                        {TileType.CAT, null, null, null, null},
                        {TileType.CAT, TileType.CAT, null, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}};
            }


            case 2 ->{
                return new TileType[][]{
                        {null,  null, null, null, TileType.CAT},
                        {null, null, null, TileType.CAT, TileType.CAT},
                        {null, null, TileType.CAT, TileType.CAT, TileType.CAT},
                        {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
                };
            }

            case 3 -> {
                return new TileType[][]{
                        {TileType.CAT,  null, null, null, null},
                        {TileType.CAT, TileType.CAT, null, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
                };
            }
            case 4 -> {
                return new TileType[][]{
                        {null,  null, null, null, null},
                        {null, TileType.CAT, null, null, null},
                        {null, TileType.CAT, null, TileType.CAT, TileType.CAT},
                        {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {null, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
                };
            }

            case 5 -> {
                return new TileType[][]{
                        {null,  null, null, null, null},
                        {null, TileType.CAT, null, null, null},
                        {null, TileType.CAT, null, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
                };
            }

            case 6 -> {
                return new TileType[][]{
                        {null,  null, null, null, null},
                        {TileType.CAT, TileType.CAT, null, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT}
                };
            }

            default -> {
                return null;
            }
        }

    }

    private List<EntryPatternGoal> getExpectedResult(int number){
        List<EntryPatternGoal> result = new ArrayList<>();

        switch(number){
            case 1 -> {
                result.add(new EntryPatternGoal(4,5,TileType.CAT));
                result.add(new EntryPatternGoal(3,5,TileType.CAT));
                result.add(new EntryPatternGoal(3,4,TileType.CAT));
                result.add(new EntryPatternGoal(2,5,TileType.CAT));
                result.add(new EntryPatternGoal(2,4,TileType.CAT));
                result.add(new EntryPatternGoal(2,3,TileType.CAT));
                result.add(new EntryPatternGoal(1,5,TileType.CAT));
                result.add(new EntryPatternGoal(1,4,TileType.CAT));
                result.add(new EntryPatternGoal(1,3,TileType.CAT));
                result.add(new EntryPatternGoal(1,2,TileType.CAT));
                result.add(new EntryPatternGoal(0,5,TileType.CAT));
                result.add(new EntryPatternGoal(0,4,TileType.CAT));
                result.add(new EntryPatternGoal(0,3,TileType.CAT));
                result.add(new EntryPatternGoal(0,2,TileType.CAT));
                result.add(new EntryPatternGoal(0,1,TileType.CAT));
                return result;
            }

            case 2 ->{
                result.add(new EntryPatternGoal(0,5,TileType.CAT));
                result.add(new EntryPatternGoal(0,4,TileType.CAT));
                result.add(new EntryPatternGoal(1,5,TileType.CAT));
                result.add(new EntryPatternGoal(1,4,TileType.CAT));
                result.add(new EntryPatternGoal(1,3,TileType.CAT));
                result.add(new EntryPatternGoal(2,5,TileType.CAT));
                result.add(new EntryPatternGoal(2,4,TileType.CAT));
                result.add(new EntryPatternGoal(2,3,TileType.CAT));
                result.add(new EntryPatternGoal(2,2,TileType.CAT));
                result.add(new EntryPatternGoal(3,5,TileType.CAT));
                result.add(new EntryPatternGoal(3,4,TileType.CAT));
                result.add(new EntryPatternGoal(3,3,TileType.CAT));
                result.add(new EntryPatternGoal(3,2,TileType.CAT));
                result.add(new EntryPatternGoal(3,1,TileType.CAT));
                result.add(new EntryPatternGoal(4,5,TileType.CAT));
                result.add(new EntryPatternGoal(4,4,TileType.CAT));
                result.add(new EntryPatternGoal(4,3,TileType.CAT));
                result.add(new EntryPatternGoal(4,2,TileType.CAT));
                result.add(new EntryPatternGoal(4,1,TileType.CAT));
                result.add(new EntryPatternGoal(4,0,TileType.CAT));
                return result;
            }

            case 3 -> {
                result.add(new EntryPatternGoal(4,5,TileType.CAT));
                result.add(new EntryPatternGoal(4,4,TileType.CAT));
                result.add(new EntryPatternGoal(3,5,TileType.CAT));
                result.add(new EntryPatternGoal(3,4,TileType.CAT));
                result.add(new EntryPatternGoal(3,3,TileType.CAT));
                result.add(new EntryPatternGoal(2,5,TileType.CAT));
                result.add(new EntryPatternGoal(2,4,TileType.CAT));
                result.add(new EntryPatternGoal(2,3,TileType.CAT));
                result.add(new EntryPatternGoal(2,2,TileType.CAT));
                result.add(new EntryPatternGoal(1,5,TileType.CAT));
                result.add(new EntryPatternGoal(1,4,TileType.CAT));
                result.add(new EntryPatternGoal(1,3,TileType.CAT));
                result.add(new EntryPatternGoal(1,2,TileType.CAT));
                result.add(new EntryPatternGoal(1,1,TileType.CAT));
                result.add(new EntryPatternGoal(0,5,TileType.CAT));
                result.add(new EntryPatternGoal(0,4,TileType.CAT));
                result.add(new EntryPatternGoal(0,3,TileType.CAT));
                result.add(new EntryPatternGoal(0,2,TileType.CAT));
                result.add(new EntryPatternGoal(0,1,TileType.CAT));
                result.add(new EntryPatternGoal(0,0,TileType.CAT));
                return result;
            }

            // 4, 5 and 6 are mapped to default
            default -> {
                return null;
            }
        }
    }

}