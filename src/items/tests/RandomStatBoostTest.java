package items.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import items.ItemConstants;
import main.Rarity;
import monsters.ClinkMonster;
import monsters.Monster;
import monsters.MonsterConstants;
import items.RandomStatBoost;

/**
 * Tests for the class {@link items.RandomStatBoost}
 * 
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
class RandomStatBoostTest {
	Monster testMonster;

	@BeforeEach
	void setUp() throws Exception {
		testMonster = new ClinkMonster();
	}

	/**
	 * Test cases to check
	 * 
	 * @return A stream of arguments as the test case
	 */
	private static Stream<Arguments> rarityAndBoost() {
		return Stream.of(
				Arguments.arguments(Rarity.COMMON, ItemConstants.COMMONSTATBOOST),
				Arguments.arguments(Rarity.RARE, ItemConstants.RARESTATBOOST),
				Arguments.arguments(Rarity.LEGENDARY, ItemConstants.LEGENDARYSTATBOOST));
	}

	/**
	 * Test that at least either the base health or base attack damage of the
	 * monster
	 * is increased and the other one remains constant.
	 * 
	 * @param rarity
	 * @param boostAmount
	 */
	@ParameterizedTest
	@MethodSource("rarityAndBoost")
	void useItemTest(Rarity rarity, int boostAmount) {
		RandomStatBoost statBoostItem = new RandomStatBoost(rarity);
		int prevMonsterHealth = testMonster.getBaseHealth();
		int prevMonsterDamage = testMonster.getBaseAttackDamage();
		int prevMonsterSpeed = testMonster.getSpeed();
		String message = statBoostItem.use(testMonster);

		// Test that one stat has increased by the correct amount and the others
		// have stayed the same.
		int numChanged = 0;
		int numSame = 0;

		if (testMonster.getBaseHealth() == prevMonsterHealth + boostAmount) {
			numChanged += 1;
			assertEquals(String.format(ItemConstants.RANDOMBOOSTFEEDBACK, testMonster.getName(), "HEALTH",
					statBoostItem.getStatBoostAmount(), testMonster.getBaseHealth()), message);
		} else {
			numSame += 1;
		}

		if (testMonster.getBaseAttackDamage() == prevMonsterDamage + boostAmount) {
			numChanged += 1;
			assertEquals(String.format(ItemConstants.RANDOMBOOSTFEEDBACK, testMonster.getName(), "ATTACK",
					statBoostItem.getStatBoostAmount(), testMonster.getBaseAttackDamage()), message);
		} else {
			numSame += 1;
		}

		if (testMonster.getSpeed() == prevMonsterSpeed + boostAmount) {
			numChanged += 1;
			assertEquals(String.format(ItemConstants.RANDOMBOOSTFEEDBACK, testMonster.getName(), "SPEED",
					statBoostItem.getStatBoostAmount(), testMonster.getSpeed()), message);
		} else {
			numSame += 1;
		}

		assertEquals(numSame, MonsterConstants.NUMBEROFSTATS - 1);
		assertEquals(numChanged, 1);
	}

	// TODO: Add test to check if all the different stats are reached.
}
