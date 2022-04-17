package monsters.tests;

import monsters.*;

import main.Team;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing for Lucifer Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class LuciferMonsterTest {
    Monster monster;

    /**
     * Set up a monster to test methods on before each test
     * 
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new LuciferMonster();
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
        assertEquals(MonsterConstants.LUCIFERBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.LUCIFERBASEHEALTH, monster.getBaseHealth());
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
     * Valid: stats are swapped
     */
    @Test
    public void abilityTest() {
        monster.setCurrentAttackDamage(2);
        monster.setCurrentHealth(1);
        int startAttackDamage = monster.getCurrentAttackDamage();
        int startHealth = monster.getCurrentHealth();
        Team allyTeam = new Team(monster);
        Team enemyTeam = new Team(new ClinkMonster()); // Arbitrary
        monster.ability(allyTeam, enemyTeam);

        assertEquals(startHealth, monster.getCurrentAttackDamage());
        assertEquals(startAttackDamage, monster.getCurrentHealth());

        // Check base stats are unchanged
        assertEquals(MonsterConstants.LUCIFERBASEHEALTH, monster.getBaseHealth());
        assertEquals(MonsterConstants.LUCIFERBASEATTACKDAMAGE, monster.getBaseAttackDamage());
    }
}
