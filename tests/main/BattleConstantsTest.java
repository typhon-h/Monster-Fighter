package main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import monsters.*;

/**
 * Testing BattleConstants class.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
class BattleConstantsTest {

    /**
     * Sets up the arguments for each test
     *
     * @return A stream of arguments to be passed into the test
     */
    private static Stream<Arguments> monsterAndTriggers() {
        return Stream.of(
                Arguments.arguments(new ClinkMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.ONHURT,
                                        Trigger.BEFOREATTACK, Trigger.AFTERATTACK },
                        Difficulty.HARD),
                Arguments.arguments(new DittaMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.ONHURT, Trigger.BEFOREATTACK },
                        Difficulty.HARD),
                Arguments.arguments(new GilMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.ONFAINT, Trigger.STARTOFBATTLE,
                                        Trigger.ONHURT, Trigger.BEFOREATTACK , Trigger.AFTERATTACK },
                        Difficulty.HARD),
                Arguments.arguments(new JynxMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.BEFOREATTACK },
                        Difficulty.HARD),
                Arguments.arguments(new LuciferMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.ONHURT,
                                        Trigger.AFTERATTACK },
                        Difficulty.HARD),
                Arguments.arguments(new TeddyMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.AFTERATTACK,
                                        Trigger.BEFOREATTACK },
                        Difficulty.HARD));
    }


    /**
     * Tests that {@link BattleConstants#getTriggers(Class) getTriggers(Class)}
     * returns the correct {@link main.Trigger triggers} for each
     * {@link monsters.Monster monster}.
     *
     * @param testMonster  The {@link monsters.Monster monster} to get the
     *                     {@link main.Trigger triggers} for.
     * @param testTriggers The expected {@link main.Trigger triggers} of the
     *                     {@link monsters.Monster monster}.
     * @param difficulty   {@link main.Difficulty difficulty} of {@link main.Trigger
     *                     triggers} we are looking for,
     *                     {@link main.Difficulty#HARD hard} because we want all the
     *                     {@link main.Trigger triggers}.
     */
    @ParameterizedTest
    @MethodSource("monsterAndTriggers")
    public void getTriggersClassTest(Monster testMonster, Trigger[] testTriggers, Difficulty difficulty) {
        Trigger[] monsterTriggersFromMethod = BattleConstants.getTriggers(testMonster.getClass(), difficulty);
        assertArrayEquals(monsterTriggersFromMethod, testTriggers);
    }

    /**
     * Sets up the arguments for each test
     *
     * @return A stream of arguments to be passed into the test
     */
    private static Stream<Arguments> monsterTriggersAndDifficulty() {
        return Stream.of(
                Arguments.arguments(new ClinkMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE },
                        Difficulty.EASY),
                Arguments.arguments(new ClinkMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.ONHURT },
                        Difficulty.NORMAL),
                Arguments.arguments(new ClinkMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.ONHURT,
                                        Trigger.BEFOREATTACK, Trigger.AFTERATTACK },
                        Difficulty.HARD),
                Arguments.arguments(new DittaMonster(),
                        new Trigger[] { Trigger.NOABILITY, Trigger.STARTOFBATTLE, Trigger.ONHURT, Trigger.BEFOREATTACK },
                        Difficulty.HARD));
    }

    /**
     * Testing that the {@link main.Trigger triggers} for a {@link monsters.Monster
     * monster} changes based on {@link main.Difficulty difficulty}.
     *
     * @param testMonster  The {@link monsters.Monster
     *                     monster} to get the {@link main.Trigger triggers} for.
     * @param testTriggers The expected {@link main.Trigger triggers} of the
     *                     {@link monsters.Monster
     *                     monster} at a given {@link main.Difficulty difficulty}.
     * @param difficulty   {@link main.Difficulty difficulty} of {@link main.Trigger
     *                     triggers} we are looking for.
     */
    @ParameterizedTest
    @MethodSource("monsterTriggersAndDifficulty")
    public void getTriggersDifficultyTest(Monster testMonster, Trigger[] testTriggers, Difficulty difficulty) {
        Trigger[] monsterTriggersFromMethod = BattleConstants.getTriggers(testMonster.getClass(), difficulty);
        assertArrayEquals(monsterTriggersFromMethod, testTriggers);
    }

    /**
     * Testing if an {@link IllegalArgumentException exception} is thrown when an
     * illegal class is passed as an argument into the method.
     */
    @Test
    public void getTriggersIllegalArgumentTest() {
        assertThrows(IllegalArgumentException.class,
                () -> BattleConstants.getTriggers(Monster.class, Difficulty.EASY));

        assertThrows(IllegalArgumentException.class,
                () -> BattleConstants.getTriggers(String.class, Difficulty.EASY));
    }
}
