package items;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import main.GameEnvironment;
import main.Rarity;
import monsters.*;

/**
 * Tests for the class {@link items.RandomStatBoost}
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
class RandomStatBoostTest {
    /**
     * {@link monsters.Monster Monster} for {@link items.Item item} to be tested on
     */
    Monster testMonster;

    /**
     * Creates new {@link monsters.Monster monster} for each test to run on
     */
    @BeforeEach
    void setUp() {
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
     * Test that at least either the base health, base attack damage, or base speed
     * of the {@link monsters.Monster monster} is increased and the others remain
     * constant.
     *
     * @param rarity      {@link main.Rarity rarity} of the {@link items.Item item}
     * @param boostAmount amount for the {@link items.Item item} to boost a stat
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

    /**
     * Check all stats are boosted
     * Seed: 2
     * Gives: 1, 0, 2
     */
    @Test
    public void useItemCoverageTest() {
        GameEnvironment.setSeed(2);
        RandomStatBoost item = new RandomStatBoost(Rarity.COMMON);

        int prevAttack = testMonster.getBaseAttackDamage();
        int prevHealth = testMonster.getBaseHealth();
        int prevSpeed = testMonster.getSpeed();

        // Changes attack
        item.use(testMonster);
        assertEquals(prevAttack + item.getStatBoostAmount(), testMonster.getBaseAttackDamage());
        assertEquals(prevHealth, testMonster.getBaseHealth());
        assertEquals(prevSpeed, testMonster.getSpeed());
        prevAttack = testMonster.getBaseAttackDamage();

        // Changes health
        item.use(testMonster);
        assertEquals(prevAttack, testMonster.getBaseAttackDamage());
        assertEquals(prevHealth + item.getStatBoostAmount(), testMonster.getBaseHealth());
        assertEquals(prevSpeed, testMonster.getSpeed());
        prevHealth = testMonster.getBaseHealth();

        // Changes speed
        item.use(testMonster);
        assertEquals(prevAttack, testMonster.getBaseAttackDamage());
        assertEquals(prevHealth, testMonster.getBaseHealth());
        assertEquals(prevSpeed + item.getStatBoostAmount(), testMonster.getSpeed());
    }
}
