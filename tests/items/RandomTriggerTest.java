package items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import monsters.ClinkMonster;
import monsters.Monster;
import monsters.Trigger;
import main.GameEnvironment;

/**
 * Tests for the random trigger item
 * @author Jackie Jone
 * @version 1.0, Apr. 2022
 *
 */
public class RandomTriggerTest {
    /**
     * {@link monsters.Monster Monster} for item to be tested on
     */
    Monster testMonster;

    /**
     * Creates new {@link monsters.Monster monster} for each test to run on
     */
    @BeforeEach
    void setUp() {
        testMonster = new ClinkMonster();
    }

    /**
     * Tests that the {@link items.Item item} gives the {@link monsters.Monster
     * monster} a new random {@link monsters.Trigger trigger}. The
     * {@link monsters.Trigger
     * trigger} cannot be {@link monsters.Trigger#NOABILITY} nor can it be the
     * {@link monsters.Trigger trigger} that the {@link monsters.Monster monster}
     * already has.
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
     * Tests that the all possible {@link monsters.Trigger trigger} are applied to
     * the
     * {@link monsters.Monster monster} at least
     * once.
     * Seed: 14
     * Generates: 0, 4, 2, 1, 3
     */
    @Test
    void itemAppliesAllTriggers() {
        GameEnvironment.setSeed(14);
        RandomTrigger testItem = new RandomTrigger();
        testMonster.setTrigger(Trigger.NOABILITY);
        ArrayList<Trigger> foundTriggers = new ArrayList<Trigger>();

        for (int i = 0; i < Trigger.numTriggers; i++) {
            testItem.use(testMonster);
            if (!foundTriggers.contains(testMonster.getTrigger())) {
                foundTriggers.add(testMonster.getTrigger());
            }
        }

        assertEquals(foundTriggers.size(), Trigger.numTriggers - 1);
    }
}
