package items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import monsters.ClinkMonster;
import monsters.Monster;

import main.Rarity;

class SpeedBoostTest {
    /**
     * {@link monsters.Monster Monster} to test {@link items.AttackBoost Attack
     * Boost} on
     */
    Monster testMonster;

    /**
     * Refreshes the test {@link monsters.Monster}
     * 
     */
    @BeforeEach
    void setUp() {
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

    /**
     * Tests that the item boosts the speed of a given {@link monsters.Monster
     * monster}
     */
    @ParameterizedTest
    @MethodSource("rarityAndBoost")
    void useHealthBoostTest(Rarity rarity, int boost) {
        SpeedBoost testSpeedBoost = new SpeedBoost(rarity);

        int prevMonsterBaseSpeed = testMonster.getSpeed();

        String message = testSpeedBoost.use(testMonster);

        assertEquals(testMonster.getSpeed(),
                prevMonsterBaseSpeed + boost);
        assertEquals(String.format(ItemConstants.SPEEDBOOSTFEEDBACK, testMonster.getName(),
                testSpeedBoost.getStatBoostAmount(), testMonster.getSpeed()), message);
    }

}
