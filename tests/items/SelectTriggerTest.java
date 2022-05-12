package items;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import exceptions.UnusableItemException;

import monsters.ClinkMonster;
import monsters.Monster;
import main.Trigger;

class SelectTriggerTest {
    /**
     * {@link monsters.Monster Monster} to test {@link items.AttackBoost Attack
     * Boost} on
     */
    Monster testMonster;

    /**
     * Refreshes the test {@link monsters.Monster}
     * 
     */
    @BeforeEach
    void setUp() {
        testMonster = new ClinkMonster();
    }

    /**
     * Test that the item has the correct {@link main.Trigger trigger} that is given
     * to it
     * 
     * @param trigger {@link main.Trigger trigger} to be checked
     */
    @ParameterizedTest
    @EnumSource(Trigger.class)
    void itemHasCorrectTriggerTest(Trigger trigger) {
        SelectTrigger testTrigger = new SelectTrigger(trigger);
        assertEquals(testTrigger.getTrigger(), trigger);

        testTrigger.setTrigger(Trigger.NOABILITY);
        assertEquals(testTrigger.getTrigger(), Trigger.NOABILITY);
    }

    /**
     * Test that the item applies a given {@link main.Trigger trigger} effect onto
     * the provided {@link monsters.Monster monster}
     * and that a {@link exceptions.UnusableItemException exception} is thrown at an
     * appropriate time.
     * 
     * @param trigger The {@link main.Trigger} enum value to be passed into the
     *                test.
     */
    @ParameterizedTest
    @EnumSource(Trigger.class)
    void useItemTest(Trigger trigger) {
        SelectTrigger testTrigger = new SelectTrigger(trigger);
        if (testMonster.getTrigger() == trigger) {
            assertThrows(UnusableItemException.class, () -> testTrigger.use(testMonster));
        } else {
            try {
                String message = testTrigger.use(testMonster);
                assertEquals(String.format(ItemConstants.SELECTTRIGGERFEEDBACK, testMonster.getName(),
                        testMonster.getTrigger().name()), message);
            } catch (UnusableItemException e) {
            }

            assertEquals(testMonster.getTrigger(), trigger);
        }

    }
}
