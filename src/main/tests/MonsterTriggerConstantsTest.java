package main.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import main.MonsterTriggerConstants;
import main.Trigger;
import monsters.*;

class MonsterTriggerConstantsTest {

    /**
     * Sets up the arguments for each test
     * @return A stream of arguments to be passed into the test
     */
    private static Stream<Arguments> monsterAndTriggers() {
        return Stream.of(
                Arguments.arguments(new ClinkMonster(),   new Trigger[] {Trigger.ONFAINT,
                                                                         Trigger.STARTOFBATTLE}),
                Arguments.arguments(new DittaMonster(),   new Trigger[] {Trigger.ONFAINT,
                                                                         Trigger.STARTOFBATTLE,
                                                                         Trigger.ONHURT}),
                Arguments.arguments(new GilMonster(),     new Trigger[] {Trigger.ONFAINT,
                                                                         Trigger.STARTOFBATTLE}),
                Arguments.arguments(new JynxMonster(),    new Trigger[] {Trigger.ONFAINT,
                                                                         Trigger.STARTOFBATTLE}),
                Arguments.arguments(new LuciferMonster(), new Trigger[] {Trigger.ONFAINT,
                                                                         Trigger.STARTOFBATTLE}),
                Arguments.arguments(new TeddyMonster(),   new Trigger[] {Trigger.ONFAINT,
                                                                         Trigger.STARTOFBATTLE}));
    }
    
    /**
     * Tests that {@link MonsterTriggerConstants#getTriggers(Monster) getTriggers(Monster)}
     * returns the correct triggers for each monster.
     * 
     * @param testMonster  The monster to get the triggers for.
     * @param testTriggers The expected triggers of the monster.
     */
    @ParameterizedTest
    @MethodSource("monsterAndTriggers")
    public void getTriggersObjectTest(Monster testMonster, Trigger[] testTriggers) {
        Trigger[] monsterTriggersFromMethod = MonsterTriggerConstants.getTriggers(testMonster);
        assertArrayEquals(monsterTriggersFromMethod, testTriggers);
    }
    
    /**
     * Tests that {@link MonsterTriggerConstants#getTriggers(Class) getTriggers(Class)}
     * returns the correct triggers for each monster.
     * 
     * @param testMonster  The monster to get the triggers for.
     * @param testTriggers The expected triggers of the monster.
     */
    @ParameterizedTest
    @MethodSource("monsterAndTriggers")
    public void getTriggersClassTest(Monster testMonster, Trigger[] testTriggers) {
        Trigger[] monsterTriggersFromMethod = MonsterTriggerConstants.getTriggers(testMonster.getClass());
        assertArrayEquals(monsterTriggersFromMethod, testTriggers);
    }
    
    /**
     * Testing if an exception is thrown when an illegal class is passed
     * as an argument into the method.
     */
    @Test
    public void getTriggersIllegalArgumentTest() {
        assertThrows(IllegalArgumentException.class,
                () -> MonsterTriggerConstants.getTriggers(Monster.class));
        
        assertThrows(IllegalArgumentException.class,
                () -> MonsterTriggerConstants.getTriggers(String.class));
    }
}
