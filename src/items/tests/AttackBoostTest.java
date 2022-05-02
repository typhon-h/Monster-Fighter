package items.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import items.AttackBoost;
import main.Rarity;
import monsters.Monster;
import monsters.ClinkMonster;
import items.ItemConstants;

/**
 * Tests for the class {@link items.AttackBoost}
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

    /**
     * Sets up the arguments for each test
     * 
     * @return A stream of arguments to be passed into the test
     */
    private static Stream<Arguments> rarityAndBoost() {
        return Stream.of(
                Arguments.arguments(Rarity.COMMON, ItemConstants.COMMONSTATBOOST),
                Arguments.arguments(Rarity.RARE, ItemConstants.RARESTATBOOST),
                Arguments.arguments(Rarity.LEGENDARY, ItemConstants.LEGENDARYSTATBOOST));
    }

    @ParameterizedTest
    @MethodSource("rarityAndBoost")
    void itemBoostAmountTest(Rarity rarity, int boost) {
        AttackBoost attackBoostItem = new AttackBoost(rarity);

        // Checks that the item has the correct boost amount to apply to the monster
        assertEquals(attackBoostItem.getStatBoostAmount(), boost);
    }

    /**
     * Tests that the use method increases the base attack of a monster by the
     * define damount
     */
    @ParameterizedTest
    @MethodSource("rarityAndBoost")
    void useItemTest(Rarity rarity, int boost) {
        AttackBoost attackBoostItem = new AttackBoost(rarity);

        int monsterPrevAttack = testMonster.getBaseAttackDamage();

        String message = attackBoostItem.use(testMonster);

        assertEquals(testMonster.getBaseAttackDamage(), monsterPrevAttack + boost);
        assertEquals(String.format(ItemConstants.ATTACKBOOSTFEEDBACK, testMonster.getName(),
                attackBoostItem.getStatBoostAmount(), testMonster.getBaseAttackDamage()), message);
    }

}
