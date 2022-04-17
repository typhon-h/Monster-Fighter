package items.itemsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import main.Rarity;
import items.*;

/**
 * Tests for Item class, uses AttackBoost subclass to test as Item
 * is an abstract class and cannot be instantiated.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
class ItemTest {

	/**
	 * Checks that the set sell price of {@link items.Item} is setup
	 * properly in the initialization methods.
	 */
	@Test
	void sellPriceTest() {
		Item testItem = new AttackBoost("An item",
								 "Item Description",
								 Rarity.COMMON);
		
		// Checks that the buy sell is set to COMMONSELLPRICE (Common is also default value)
		assertEquals(testItem.getSellPrice(), ItemConstants.COMMONSELLPRICE);
		
		
		testItem = new AttackBoost("An item",
				 "Item Description",
				 Rarity.LEGENDARY);
		
		// Checks that the buy price is set to LEGENDARYSELLPRICE
		assertEquals(testItem.getSellPrice(), ItemConstants.LEGENDARYSELLPRICE);
	}
	
	/**
	 * Checks that the set set sell price of {@link items.Item} is set up
	 * correctly in the initialization methods.
	 */
	@Test
	void buyPriceTest() {
		Item testItem = new AttackBoost("An item",
								 "Item Description",
								 Rarity.COMMON);
		
		// Checks that the buy price is set to COMMONSELLPRICE (Common is also default value)
		assertEquals(testItem.getBuyPrice(), ItemConstants.COMMONBUYPRICE);

		testItem = new AttackBoost("An item",
				 "Item Description",
				 Rarity.LEGENDARY);
		
		// Checks that the buy price is set to LEGENDARYBUYPRICE
		assertEquals(testItem.getBuyPrice(), ItemConstants.LEGENDARYBUYPRICE);
	}


	/**
	 * Tests that the method {@link items.item#getStatBoostAmount() getStatBoostAmount} functions properly
	 */
	@Test
	void getStatBoostAmountTest() {
		Item testItem = new AttackBoost("An item",
				 "Item Description",
				 Rarity.COMMON);
		assertEquals(testItem.getStatBoostAmount(), ItemConstants.COMMONSTATBOOST);
		
		testItem = new AttackBoost("An item",
				 "Item Description",
				 Rarity.LEGENDARY);
		
		assertEquals(testItem.getStatBoostAmount(), ItemConstants.LEGENDARYSTATBOOST);
	}
	
}
