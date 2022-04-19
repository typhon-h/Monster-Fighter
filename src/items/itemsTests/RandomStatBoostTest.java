package items.itemsTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import items.ItemConstants;
import main.Rarity;
import monsters.ClinkMonster;
import monsters.Monster;
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
	 * Test that at least either the base health or base attack damage of the monster
	 * is increased and the other one remains constant.
	 * 
	 * @param rarity
	 * @param boostAmount
	 */
	@ParameterizedTest
	@MethodSource("rarityAndBoost")
	void useItemTest(Rarity rarity, int boostAmount) {
		RandomStatBoost statBoostItem = new RandomStatBoost("Random stat boost",
															"boosts random stat",
															rarity);
		int prevMonsterHealth = testMonster.getBaseHealth();
		int prevMonsterDamage = testMonster.getBaseAttackDamage();
		statBoostItem.use(testMonster);
		
		// Test that one stat has increased by the correct amount and the other has
		// stayed the same.
		assertTrue((testMonster.getBaseHealth() == prevMonsterHealth + boostAmount &&
				   testMonster.getBaseAttackDamage() == prevMonsterDamage) || 
				   (testMonster.getBaseHealth() == prevMonsterHealth &&
			       testMonster.getBaseAttackDamage() == prevMonsterDamage + boostAmount));
		
	}

}
