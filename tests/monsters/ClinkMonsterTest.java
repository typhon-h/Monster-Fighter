package monsters;

import main.Rarity;
import main.Team;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import battle.BattleEvent;
import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

/**
 * Testing for Clink Monster class.
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class ClinkMonsterTest {
    Monster monster;
    Team allyTeam;
    Team enemyTeam;

    /**
     * Set up a monster to test methods on before each test
     *
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new ClinkMonster();
        allyTeam = new Team(monster);
        enemyTeam = new Team(new ClinkMonster());
    }

    /**
     * Test cases to check
     *
     * @return A stream of arguments as the test case
     */
    private static Stream<Arguments> rarityAndPrice() {
        return Stream.of(
                Arguments.arguments(Rarity.COMMON, MonsterConstants.COMMONBUYPRICE, MonsterConstants.COMMONSELLPRICE),
                Arguments.arguments(Rarity.RARE, MonsterConstants.RAREBUYPRICE, MonsterConstants.RARESELLPRICE),
                Arguments.arguments(Rarity.LEGENDARY, MonsterConstants.LEGENDARYBUYPRICE,
                        MonsterConstants.LEGENDARYSELLPRICE));
    }

    /**
     * Checks stats are set correctly according to constants
     * Covers: Constructor
     * BuyPrice, SellPrice, Rarity not NULL
     * AttackDamage set to BaseAttackDamage
     * Health set to BaseHealth
     * BuyPrice and SellPrice set based on Rarity
     *
     * @param rarity    rarity to set the monster
     * @param buyPrice  expected buy price
     * @param sellPrice expected sell price
     */
    @ParameterizedTest
    @MethodSource("rarityAndPrice")
    public void statsTest(Rarity rarity, int buyPrice, int sellPrice) {
        monster.setRarity(rarity);
        // Check base stats are set correctly
        assertEquals(MonsterConstants.CLINKBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.CLINKBASEHEALTH, monster.getBaseHealth());
        // Check buy/sell prices are set correctly
        assertEquals(buyPrice, monster.getBuyPrice());
        assertEquals(sellPrice, monster.getSellPrice());
        assertEquals(MonsterConstants.CLINKBASESPEED, monster.getSpeed());
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: effect occurs. Base stats not affected
     * Invalid: Attack is at minimum (1)
     *
     * @throws DuplicateMonsterException if same monster is added more than once
     * @throws TeamSizeException         if more team members than max allowed
     */
    @Test
    public void abilityTest() throws TeamSizeException, DuplicateMonsterException {
        int startAttackDamage = monster.getCurrentAttackDamage();
        int startHealth = monster.getCurrentHealth();

        monster.ability(true, allyTeam, enemyTeam);

        // Attack damage is reduced
        assertEquals(startAttackDamage - 1, monster.getCurrentAttackDamage());
        // Base attack was not changed
        assertEquals(MonsterConstants.CLINKBASEATTACKDAMAGE, monster.getBaseAttackDamage());

        // Health was increased
        assertEquals(startHealth + 1, monster.getCurrentHealth());
        // Base health was not changed
        assertEquals(MonsterConstants.CLINKBASEHEALTH, monster.getBaseHealth());

        // Ability does nothing
        monster.restore();
        startAttackDamage = 1;
        monster.setCurrentAttackDamage(startAttackDamage); // Minimum attack
        monster.ability(true, allyTeam, enemyTeam);
        assertEquals(startAttackDamage, monster.getCurrentAttackDamage());
        assertEquals(startHealth, monster.getCurrentHealth());

    }

    /**
     * Checks returns valid {@link battle.BattleEvent battle event}
     */
    @Test
    public void abilityReturnTest() {
        BattleEvent ability = monster.ability(true, allyTeam, enemyTeam);
        assertEquals(monster.getName() + "'s " + monster.getTrigger().name()
                + " ability triggered. Lost 1 ATK and gained 1 HP", ability.getDescription());
    }
}
