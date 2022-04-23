package items.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import exceptions.UnusableItemException;
import items.SelectTrigger;
import monsters.ClinkMonster;
import monsters.Monster;
import main.Trigger;

class SelectTriggerTest {
	Monster testMonster;

	/**
	 * Setup before each test is run, creates a new monster to be tested on.
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		testMonster = new ClinkMonster();
	}

	/**
	 * Test that the item has the correct trigger that is given to it
	 * 
	 * @param trigger
	 */
	@ParameterizedTest
	@EnumSource(Trigger.class)
	void itemHasCorrectTriggerTest(Trigger trigger) {
		SelectTrigger testTrigger = new SelectTrigger("Select Trigger",
				"Item description",
				trigger);
		assertEquals(testTrigger.getTrigger(), trigger);

		testTrigger.setTrigger(Trigger.NOABILITY);
		assertEquals(testTrigger.getTrigger(), Trigger.NOABILITY);
	}

	/**
	 * Test that the item applies a given trigger effect onto the provided monster
	 * and that an exception is thrown at an appropriate time.
	 * 
	 * @param trigger The {@link main.Trigger} enum value to be passed into the
	 *                test.
	 */
	@ParameterizedTest
	@EnumSource(Trigger.class)
	void useItemTest(Trigger trigger) {
		SelectTrigger testTrigger = new SelectTrigger("Select Trigger",
				"Item description",
				trigger);
		if (testMonster.getTrigger() == trigger) {
			assertThrows(UnusableItemException.class, () -> testTrigger.use(testMonster));
		} else {
			try {
				testTrigger.use(testMonster);
			} catch (UnusableItemException e) {
			}

			assertEquals(testMonster.getTrigger(), trigger);
		}

	}
}
