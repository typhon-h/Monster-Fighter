package items.itemsTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import monsters.ClinkMonster;
import monsters.Monster;
import items.HealthBoost;
import items.ItemConstants;
import main.Rarity;

class HealthBoostTest {
    Monster testMonster;

    @BeforeEach
    void setUp() throws Exception {
        testMonster = new ClinkMonster();
    }

    private static Stream<Arguments> rarityAndBoost() {
        return Stream.of(
                Arguments.arguments(Rarity.COMMON, ItemConstants.COMMONSTATBOOST),
                Arguments.arguments(Rarity.RARE, ItemConstants.RARESTATBOOST),
                Arguments.arguments(Rarity.LEGENDARY, ItemConstants.LEGENDARYSTATBOOST));
    }

    /**
     * Tests that the item boosts the health of a given monster
     */
    @ParameterizedTest
    @MethodSource("rarityAndBoost")
    void useHealthBoostTest(Rarity rarity, int boost) {
        HealthBoost testHealthBoost = new HealthBoost("Health Boost",
                                                      "Description",
                                                      rarity);

        // TODO: Change this to use getBaseHealth
        int prevMonsterBaseHealth = testMonster.getBaseHealth();

        testHealthBoost.use(testMonster);

        assertEquals(testMonster.getBaseHealth(),
                     prevMonsterBaseHealth + boost);
    }

}
