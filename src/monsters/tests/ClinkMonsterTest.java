package monsters.tests;

import monsters.*;

import main.Team;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Queue;

/**
 * Testing for Clink Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class ClinkMonsterTest {
    Monster monster;

    /**
     * Set up a monster to test methods on before each test
     * 
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new ClinkMonster();
    }

    /**
     * Checks stats are set correctly according to constants
     * Covers: Constructor
     * BuyPrice, SellPrice, Rarity not NULL
     * AttackDamage set to BaseAttackDamage
     * Health set to BaseHealth
     * BuyPrice and SellPrice set based on Rarity
     */
    @Test
    public void statsTest() {
        assertNotNull(monster.getBuyPrice());
        assertNotNull(monster.getSellPrice());
        assertNotNull(monster.getRarity());
        // Check base stats are set correctly
        assertEquals(MonsterConstants.CLINKBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.CLINKBASEHEALTH, monster.getBaseHealth());
        // Check buy/sell prices are set correctly
        switch (monster.getRarity()) {
            case COMMON:
                assertEquals(MonsterConstants.COMMONBUYPRICE, monster.getBuyPrice());
                assertEquals(MonsterConstants.COMMONSELLPRICE, monster.getSellPrice());
                break;
            case RARE:
                assertEquals(MonsterConstants.RAREBUYPRICE, monster.getBuyPrice());
                assertEquals(MonsterConstants.RARESELLPRICE, monster.getSellPrice());
                break;
            case LEGENDARY:
                assertEquals(MonsterConstants.LEGENDARYBUYPRICE, monster.getBuyPrice());
                assertEquals(MonsterConstants.LEGENDARYSELLPRICE, monster.getSellPrice());
                break;
        }
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: effect occurs. Base stats not affected
     * Invalid: Attack is at minimum (1)
     */
    @Test
    public void abilityTest() {
        int startAttackDamage = monster.getCurrentAttackDamage();
        int startHealth = monster.getCurrentHealth();
        Team allyTeam = new Team(monster);
        Team enemyTeam = new Team(new ClinkMonster());

        Queue<Monster> triggeredAbilities = monster.ability(allyTeam, enemyTeam);
        // No other abilities were triggered
        assertEquals(0, triggeredAbilities.size());

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
        monster.ability(allyTeam, enemyTeam);
        assertEquals(startAttackDamage, monster.getCurrentAttackDamage());
        assertEquals(startHealth, monster.getCurrentHealth());

    }
}
