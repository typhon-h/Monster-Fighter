package items.itemsTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import items.AttackBoost;
import main.Rarity;
import monsters.Monster;
import monsters.ClinkMonster;
import items.ItemConstants;

/**
 * Tests for the class AttackBoost
 * 
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
class AttackBoostTest {
	Monster testMonster;
	
	@BeforeEach
	void setUp() throws Exception {
		testMonster = new ClinkMonster();
	}

	@Test
	void itemBoostAmountTest() {
		AttackBoost attackBoostItem = new AttackBoost("Attack Boost",
													  "Boosts attack",
													  Rarity.COMMON);
		
		// Checks that the item has the correct boost amount to apply to the monster
		assertEquals(attackBoostItem.getStatBoostAmount(), ItemConstants.COMMONSTATBOOST);
		
		attackBoostItem = new AttackBoost("Attack Boost",
										  "Boosts attack",
										  Rarity.LEGENDARY);
		
		// Checks that the item has the correct boost amount to apply to the monster
		assertEquals(attackBoostItem.getStatBoostAmount(), ItemConstants.LEGENDARYSTATBOOST);
	}
	
	/**
	 * Tests that the use method increases the base attack of a monster by the define damount
	 */
	@Test
	void useItemTest() {
		AttackBoost attackBoostItem = new AttackBoost("Attack Boost",
													  "Boosts attack",
													  Rarity.COMMON);
		
		// TODO: Change this to testmonster.getBaseAttack() method.
		int monsterPrevAttack = testMonster.getCurrentAttackDamage(); 
		
		attackBoostItem.use(testMonster);
		
		// TODO: change monster method to getBaseAttackDamage.
		assertEquals(testMonster.getCurrentAttackDamage(), monsterPrevAttack + ItemConstants.COMMONSTATBOOST);
		
		monsterPrevAttack = testMonster.getCurrentAttackDamage();
		attackBoostItem = new AttackBoost("Attack Boost",
										  "Boosts attack",
										  Rarity.LEGENDARY);
		
		
		monsterPrevAttack = testMonster.getCurrentAttackDamage();
		
		attackBoostItem.use(testMonster);
		
		assertEquals(testMonster.getCurrentAttackDamage(), monsterPrevAttack + ItemConstants.LEGENDARYSTATBOOST);
	}

}
