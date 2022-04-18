package items.itemsTests;

import static org.junit.jupiter.api.Assertions.*;

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
	 * Tests that the item gives the monster a new random ability. The ability cannot
	 * be NOABILITY nor can it be the ability that the monster already has.
	 */
	@Test
	void test() {
		RandomTrigger testItem = new RandomTrigger("Random Trigger",
												   "Gives random trigger");
		
		Trigger prevTrigger = testMonster.getTrigger();
		testItem.use(testMonster);
		
//		assertTrue(testMonster.getTrigger() != prevTrigger &&
//				   testMonster.getTrigger() != Trigger.NOABILITY);
		
		prevTrigger = testMonster.getTrigger();
		testItem.use(testMonster);
		
		assertTrue(testMonster.getTrigger() != prevTrigger &&
				   testMonster.getTrigger() != Trigger.NOABILITY);
	}

}
