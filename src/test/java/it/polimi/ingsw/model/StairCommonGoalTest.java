package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StairCommonGoalTest {

    @Test
    void getNumberOfColumns() {
        Random rnd = new Random();
        int numberOfColumns = rnd.nextInt();
        StairCommonGoal goal = new StairCommonGoal(numberOfColumns);
        assertEquals(numberOfColumns, goal.getNumberOfColumns());
    }

    @Test
    void setNumberOfColumns() {
        Random rnd = new Random();
        int numberOfColumns = rnd.nextInt();
        StairCommonGoal goal = new StairCommonGoal(1);
        goal.setNumberOfColumns(numberOfColumns);
        assertEquals(numberOfColumns, goal.getNumberOfColumns());
    }

    @Test
    void rule() {
        int numberOfColumns, index;
        numberOfColumns = 7;
        StairCommonGoal goal = new StairCommonGoal(numberOfColumns);
        assertNull(goal.rule(createStairDesign(1))); //testing illegal parameter

        numberOfColumns = 6;
        goal = new StairCommonGoal(numberOfColumns);
        assertNull(goal.rule(createStairDesign(1))); //testing illegal parameter

       for(index = 1; index <= 6; index++){
            for(numberOfColumns = 2; numberOfColumns <= 5; numberOfColumns++){
                if ( (index == 4 && numberOfColumns !=2) || index == 5 || (index == 6 && numberOfColumns == 5)) {
                    goal = new StairCommonGoal(numberOfColumns);
                    assertNull(getExpectedResult(index,numberOfColumns));
                } else {
                    for (int i = 0; i < Objects.requireNonNull(getExpectedResult(index, numberOfColumns)).size(); i++) {
                        goal = new StairCommonGoal(numberOfColumns);
                        assertEquals(Objects.requireNonNull(getExpectedResult(index, numberOfColumns)).get(i).toString(),
                                goal.rule(createStairDesign(index)).get(i).toString());
                    }
                }
            }
        }

       assertNull(goal.rule(null));
    }

    private TileType[][] createStairDesign(int number){
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
                        {TileType.CAT, TileType.CAT, TileType.CAT, null, null},
                        {TileType.CAT, TileType.CAT, TileType.CAT, null, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, null, TileType.CAT}
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

    private List<EntryPatternGoal> getExpectedResult(int number, int numberOfColumns){
        List<EntryPatternGoal> result = new ArrayList<>();

        switch(number){
            case 1 -> {
                switch (numberOfColumns){
                    case 5 ->{
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(2,1,TileType.CAT));
                        result.add(new EntryPatternGoal(5,0,TileType.CAT));
                        result.add(new EntryPatternGoal(4,0,TileType.CAT));
                        result.add(new EntryPatternGoal(3,0,TileType.CAT));
                        result.add(new EntryPatternGoal(2,0,TileType.CAT));
                        result.add(new EntryPatternGoal(1,0,TileType.CAT));
                        return result;
                    }

                    case 4 -> {
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(2,1,TileType.CAT));
                        return result;
                    }

                    case 3 -> {
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        return result;
                    }

                    case 2 -> {
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        return result;
                    }

                    default ->{
                        return null;
                    }
                }
            }

            case 2 ->{
                switch (numberOfColumns){
                    case 5 ->{
                        result.add(new EntryPatternGoal(5,0,TileType.CAT));
                        result.add(new EntryPatternGoal(4,0,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        result.add(new EntryPatternGoal(2,3,TileType.CAT));
                        result.add(new EntryPatternGoal(1,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(3,4,TileType.CAT));
                        result.add(new EntryPatternGoal(2,4,TileType.CAT));
                        result.add(new EntryPatternGoal(1,4,TileType.CAT));
                        result.add(new EntryPatternGoal(0,4,TileType.CAT));
                        return result;
                    }

                    case 4 ->{
                        result.add(new EntryPatternGoal(5,0,TileType.CAT));
                        result.add(new EntryPatternGoal(4,0,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        result.add(new EntryPatternGoal(2,3,TileType.CAT));
                        result.add(new EntryPatternGoal(1,3,TileType.CAT));
                        return result;
                    }

                    case 3 ->{
                        result.add(new EntryPatternGoal(5,0,TileType.CAT));
                        result.add(new EntryPatternGoal(4,0,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        return result;
                    }

                    case 2 ->{
                        result.add(new EntryPatternGoal(5,0,TileType.CAT));
                        result.add(new EntryPatternGoal(4,0,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        return result;
                    }

                    default -> {
                        return null;
                    }
                }
            }

            case 3 -> {
                switch (numberOfColumns){
                    case 5 ->{
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(2,1,TileType.CAT));
                        result.add(new EntryPatternGoal(1,1,TileType.CAT));
                        result.add(new EntryPatternGoal(5,0,TileType.CAT));
                        result.add(new EntryPatternGoal(4,0,TileType.CAT));
                        result.add(new EntryPatternGoal(3,0,TileType.CAT));
                        result.add(new EntryPatternGoal(2,0,TileType.CAT));
                        result.add(new EntryPatternGoal(1,0,TileType.CAT));
                        result.add(new EntryPatternGoal(0,0,TileType.CAT));
                        return result;
                    }

                    case 4 ->{
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(2,1,TileType.CAT));
                        result.add(new EntryPatternGoal(1,1,TileType.CAT));
                        return result;
                    }

                    case 3->{
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        return result;
                    }

                    case 2 ->{
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        return result;
                    }

                    default -> {
                        return null;
                    }
                }
            }

            case 4 ->{
                if (numberOfColumns == 2) {
                    result.add(new EntryPatternGoal(5, 2, TileType.CAT));
                    result.add(new EntryPatternGoal(4, 2, TileType.CAT));
                    result.add(new EntryPatternGoal(3, 2, TileType.CAT));
                    result.add(new EntryPatternGoal(5, 3, TileType.CAT));
                    result.add(new EntryPatternGoal(4, 3, TileType.CAT));
                    result.add(new EntryPatternGoal(3, 3, TileType.CAT));
                    result.add(new EntryPatternGoal(2, 3, TileType.CAT));
                    return result;
                }
                return null;
            }

            case 6 ->{
                switch (numberOfColumns){
                    case 4 -> {
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        result.add(new EntryPatternGoal(5,1,TileType.CAT));
                        result.add(new EntryPatternGoal(4,1,TileType.CAT));
                        result.add(new EntryPatternGoal(3,1,TileType.CAT));
                        result.add(new EntryPatternGoal(2,1,TileType.CAT));
                        result.add(new EntryPatternGoal(1,1,TileType.CAT));
                        return result;
                    }

                    case 3 -> {
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        result.add(new EntryPatternGoal(5,2,TileType.CAT));
                        result.add(new EntryPatternGoal(4,2,TileType.CAT));
                        result.add(new EntryPatternGoal(3,2,TileType.CAT));
                        result.add(new EntryPatternGoal(2,2,TileType.CAT));
                        return result;
                    }

                    case 2 -> {
                        result.add(new EntryPatternGoal(5,4,TileType.CAT));
                        result.add(new EntryPatternGoal(4,4,TileType.CAT));
                        result.add(new EntryPatternGoal(5,3,TileType.CAT));
                        result.add(new EntryPatternGoal(4,3,TileType.CAT));
                        result.add(new EntryPatternGoal(3,3,TileType.CAT));
                        return result;
                    }

                    // case 5 is mapped to default
                    default -> {
                        return null;
                    }
                }
            }

            // 5 (all null) are mapped to default
            default -> {
                return null;
            }
        }
    }
}