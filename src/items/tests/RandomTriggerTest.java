package items.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import monsters.ClinkMonster;
import monsters.Monster;
import items.RandomTrigger;
import main.Trigger;

class RandomTriggerTest {
	Monster testMonster;

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
		testItem.use(testMonster);

		// assertTrue(testMonster.getTrigger() != prevTrigger &&
		// testMonster.getTrigger() != Trigger.NOABILITY);

		prevTrigger = testMonster.getTrigger();
		testItem.use(testMonster);

		assertTrue(testMonster.getTrigger() != prevTrigger &&
				testMonster.getTrigger() != Trigger.NOABILITY);
	}

	/**
	 * Tests that the all possible triggers are applied to the monster at least
	 * once.
	 */
	@Test
	void itemAppliesAllTriggers() {
		RandomTrigger testItem = new RandomTrigger();

		HashSet<Trigger> foundTriggers = new HashSet<Trigger>();
		int i = 0;
		while (foundTriggers.size() < Trigger.numTriggers - 1 && i < 1000) {
			testItem.use(testMonster);
			foundTriggers.add(testMonster.getTrigger());
			i++;
		}

		assertEquals(foundTriggers.size(), Trigger.numTriggers - 1);
	}
}
