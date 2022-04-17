package monsters.tests;

import monsters.*;

import main.Team;
import main.Trigger;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;

/**
 * Testing for Teddy Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class TeddyMonsterTest {
    Monster monster;

    /**
     * Set up a monster to test methods on before each test
     * 
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new TeddyMonster();
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
        assertEquals(MonsterConstants.TEDDYBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.TEDDYBASEHEALTH, monster.getBaseHealth());
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
     * Valid: random enemy has Health reduced
     * Valid: heals itself if only member
     * Randomness must be checked manually
     * 
     * @throws TeamSizeException   too many members added to team
     * @throws TeamStatusException all team members are dead
     */
    @Test
    public void abilityTest() throws TeamSizeException, TeamStatusException {
        Team allyTeam = new Team(monster, new ClinkMonster());
        Team enemyTeam = new Team(new ClinkMonster());

        // Check boosts health
        int startAllyTeamSumHealth = 0;
        int endAllyTeamSumHealth = 0;
        for (Monster m : allyTeam.getAliveMonsters()) {
            m.setTrigger(Trigger.NOABILITY);
            startAllyTeamSumHealth += m.getCurrentHealth();
        }
        monster.ability(allyTeam, enemyTeam);
        for (Monster m : allyTeam.getAliveMonsters()) {
            endAllyTeamSumHealth += m.getCurrentHealth();
        }
        assertEquals(startAllyTeamSumHealth + 1, endAllyTeamSumHealth);

        monster.restore();
        // Check does nothing with empty team
        allyTeam = new Team(monster);
        // Faint whole team
        monster.ability(allyTeam, enemyTeam);
        assertEquals(monster.getBaseHealth() + 1, monster.getCurrentHealth());
    }
}
