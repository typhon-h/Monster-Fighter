package items;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import main.Rarity;

/**
 * Tests for Item class, uses {@link items.AttackBoost AttackBoost} subclass
 * totest
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
class ItemTest {

    /**
     * Sets up the arguments for each test with {@link main.Rarity rarity} and
     * associated sell price
     * 
     * @return A stream of arguments to be passed into the test
     */
    private static Stream<Arguments> rarityAndSellPrice() {
        return Stream.of(
                Arguments.arguments(Rarity.COMMON, ItemConstants.COMMONSELLPRICE),
                Arguments.arguments(Rarity.RARE, ItemConstants.RARESELLPRICE),
                Arguments.arguments(Rarity.LEGENDARY, ItemConstants.LEGENDARYSELLPRICE));
    }

    /**
     * Checks that the set sell price of {@link items.Item} is setup
     * properly in the initialization methods.
     */
    @ParameterizedTest
    @MethodSource("rarityAndSellPrice")
    void sellPriceTest(Rarity rarity, int sellPrice) {
        Item testItem = new AttackBoost(rarity);

        // Checks that the buy sell is set to its sell price
        assertEquals(testItem.getSellPrice(), sellPrice);
    }

    /**
     * Sets up the arguments for each test with {@link main.Rarity rarity} and
     * associated buy price
     * 
     * @return A stream of arguments to be passed into the test
     */
    private static Stream<Arguments> rarityAndBuyPrice() {
        return Stream.of(
                Arguments.arguments(Rarity.COMMON, ItemConstants.COMMONBUYPRICE),
                Arguments.arguments(Rarity.RARE, ItemConstants.RAREBUYPRICE),
                Arguments.arguments(Rarity.LEGENDARY, ItemConstants.LEGENDARYBUYPRICE));
    }

    /**
     * Checks that the set set sell price of {@link items.Item} is set up
     * correctly in the initialization methods.
     */
    @ParameterizedTest
    @MethodSource("rarityAndBuyPrice")
    void buyPriceTest(Rarity rarity, int buyPrice) {
        Item testItem = new AttackBoost(rarity);

        // Checks that the buy price is set to the buy price for the rarity
        assertEquals(testItem.getBuyPrice(), buyPrice);
    }

    /**
     * Sets up the arguments for each test with {@link main.Rarity rarity} and
     * associated boost amount
     * 
     * @return A stream of arguments to be passed into the test
     */
    private static Stream<Arguments> rarityAndBoost() {
        return Stream.of(
                Arguments.arguments(Rarity.COMMON, ItemConstants.COMMONSTATBOOST),
                Arguments.arguments(Rarity.RARE, ItemConstants.RARESTATBOOST),
                Arguments.arguments(Rarity.LEGENDARY, ItemConstants.LEGENDARYSTATBOOST));
    }

    /**
     * Tests that the method {@link items.item#getStatBoostAmount()
     * getStatBoostAmount} functions properly
     */
    @ParameterizedTest
    @MethodSource("rarityAndBoost")
    void getStatBoostAmountTest(Rarity rarity, int boostAmount) {
        Item testItem = new AttackBoost(rarity);
        assertEquals(testItem.getStatBoostAmount(), boostAmount);
    }

    /**
     * Tests that changing the rarity of the item also changes the buy and sell
     * price.
     */
    @Test
    void rarityPriceChangeTest() {
        Item testItem = new AttackBoost(Rarity.COMMON);

        // Check that all the values of the test item is common
        assertEquals(testItem.getRarity(), Rarity.COMMON);
        assertEquals(testItem.getBuyPrice(), ItemConstants.COMMONBUYPRICE);
        assertEquals(testItem.getSellPrice(), ItemConstants.COMMONSELLPRICE);

        // Change item to a legendary item
        testItem.setRarity(Rarity.LEGENDARY);

        // Check that all the values of the test item is legendary
        assertEquals(testItem.getRarity(), Rarity.LEGENDARY);
        assertEquals(testItem.getBuyPrice(), ItemConstants.LEGENDARYBUYPRICE);
        assertEquals(testItem.getSellPrice(), ItemConstants.LEGENDARYSELLPRICE);
    }
}
