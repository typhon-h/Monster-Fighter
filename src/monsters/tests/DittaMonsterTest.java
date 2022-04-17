package monsters.tests;

import monsters.*;

import main.Team;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.TeamStatusException;

import java.util.Queue;

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
        assertEquals(MonsterConstants.DITTABASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.DITTABASEHEALTH, monster.getBaseHealth());
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
     * Valid: effect occurs. Stats match first enemy
     * Invalid: Whole opponent team has fainted
     * 
     * @throws TeamStatusException if there is no first monster in team
     */
    @Test
    public void abilityTest() throws TeamStatusException {
        Team allyTeam = new Team(monster);
        Team enemyTeam = new Team(new ClinkMonster());
        Queue<Monster> triggeredAbilities = monster.ability(allyTeam, enemyTeam);
        Monster firstEnemy = enemyTeam.getFirstAliveMonster();

        // Check stats are copied
        assertEquals(0, triggeredAbilities.size());
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
