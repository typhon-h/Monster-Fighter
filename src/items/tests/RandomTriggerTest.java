package items.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import monsters.ClinkMonster;
import monsters.Monster;

import items.ItemConstants;
import items.RandomTrigger;

import main.GameEnvironment;
import main.Trigger;

class RandomTriggerTest {
    /**
     * Monster for item to be tested on
     */
    Monster testMonster;

    /**
     * Creates new monster for each test to run on
     */
    @BeforeEach
    void setUp() throws Exception {
        testMonster = new ClinkMonster();
    }

    /**
     * Tests that the item gives the monster a new random ability. The ability
     * cannot
     * be NOABILITY nor can it be the ability that the monster already has.
     */
    @Test
    void useItemTest() {
        RandomTrigger testItem = new RandomTrigger();

        Trigger prevTrigger = testMonster.getTrigger();
        String message = testItem.use(testMonster);
        assertEquals(String.format(ItemConstants.RANDOMTRIGGERFEEDBACK, testMonster.getName(),
                testMonster.getTrigger().name()), message);

        prevTrigger = testMonster.getTrigger();
        message = testItem.use(testMonster);
        assertEquals(String.format(ItemConstants.RANDOMTRIGGERFEEDBACK, testMonster.getName(),
                testMonster.getTrigger().name()), message);

        assertTrue(testMonster.getTrigger() != prevTrigger);
        assertTrue(testMonster.getTrigger() != Trigger.NOABILITY);
    }

    /**
     * Tests that the all possible triggers are applied to the monster at least
     * once.
     * Seed: 14
     * Generates: 0, 4, 2, 1, 3
     */
    @Test
    void itemAppliesAllTriggers() {
        GameEnvironment.setSeed(14);
        RandomTrigger testItem = new RandomTrigger();

        ArrayList<Trigger> foundTriggers = new ArrayList<Trigger>();

        for (int i = 0; i < Trigger.numTriggers - 1; i++) {
            testItem.use(testMonster);
            if (!foundTriggers.contains(testMonster.getTrigger())) {
                foundTriggers.add(testMonster.getTrigger());
            }
        }

        assertEquals(foundTriggers.size(), Trigger.numTriggers - 1);
    }
}
