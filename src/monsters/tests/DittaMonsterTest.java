package monsters.tests;

import monsters.*;
import main.Rarity;
import main.Team;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

/**
 * Testing for Ditta Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class DittaMonsterTest {
    Monster monster;

    /**
     * Set up a monster to test methods on before each test
     * 
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new DittaMonster();
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
        assertEquals(MonsterConstants.DITTABASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.DITTABASEHEALTH, monster.getBaseHealth());
        // Check buy/sell prices are set correctly
        assertEquals(buyPrice, monster.getBuyPrice());
        assertEquals(sellPrice, monster.getSellPrice());
        assertEquals(MonsterConstants.DITTABASESPEED, monster.getSpeed());
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: effect occurs. Stats match first enemy
     * Invalid: Whole opponent team has fainted
     * 
     * @throws DuplicateMonsterException if same monster is added more than once
     * @throws TeamSizeException         if more team members than max allowed
     * 
     */
    @Test
    public void abilityTest() throws TeamSizeException, DuplicateMonsterException {
        Team allyTeam = new Team(monster);
        Team enemyTeam = new Team(new ClinkMonster());
        Monster triggeredAbility = monster.ability(allyTeam, enemyTeam);
        Monster firstEnemy = enemyTeam.getFirstAliveMonster();

        // Check stats are copied
        assertNull(triggeredAbility);
        assertEquals(firstEnemy.getCurrentHealth(), monster.getCurrentHealth());
        assertEquals(firstEnemy.getCurrentAttackDamage(), monster.getCurrentAttackDamage());

        // Copies modified stats
        monster.restore();
        firstEnemy.setCurrentAttackDamage(monster.getCurrentAttackDamage() + 1);
        firstEnemy.setCurrentHealth(monster.getCurrentHealth() + 1);
        monster.ability(allyTeam, enemyTeam);
        assertEquals(firstEnemy.getCurrentHealth(), monster.getCurrentHealth());
        assertEquals(firstEnemy.getCurrentAttackDamage(), monster.getCurrentAttackDamage());

        // Nothing happens when whole team fainted (no available enemy)
        monster.restore();
        firstEnemy.takeDamage(firstEnemy.getCurrentHealth() + 1);
        assertEquals(0, enemyTeam.getAliveMonsters().size()); // All enemies fainted
        monster.ability(allyTeam, enemyTeam);
        assertEquals(monster.getBaseHealth(), monster.getCurrentHealth());
        assertEquals(monster.getBaseAttackDamage(), monster.getCurrentAttackDamage());
    }
}
