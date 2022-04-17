package monsters.tests;

import monsters.*;

import main.Team;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;

/**
 * Testing for Gil Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class GilMonsterTest {
    Monster monster;

    /**
     * Set up a monster to test methods on before each test
     * 
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new GilMonster();
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
        assertEquals(MonsterConstants.GILBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.GILBASEHEALTH, monster.getBaseHealth());
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
     * Valid: Monster in front of Gil AttackDamage: 1
     * Valid: Monster in fron of Gil AttackDamage: > 1
     * Invalid: Gil is front of the team
     * 
     * @throws TeamSizeException   if team has too many monsters
     * @throws TeamStatusException if whole team is fainted
     */
    @Test
    public void abilityTest() throws TeamSizeException, TeamStatusException {
        // Initialize variables
        Team enemyTeam = new Team(new ClinkMonster()); // Arbitrary
        Monster ally = new ClinkMonster();
        Team allyTeam = new Team(ally, monster);

        // Check Gil is in team
        int indexOfGil = allyTeam.getAliveMonsters().indexOf(monster);
        assertNotEquals(-1, indexOfGil); // monster exists in alive monsters

        // Check the ally is in front of Gil
        Monster monsterInFront = allyTeam.getAliveMonsters().get(indexOfGil - 1);
        assertEquals(ally, monsterInFront); // Check positions are correct

        // Monster in front of Gil AttackDamage: 1
        monster.setCurrentAttackDamage(1);
        int startAttackDamage = monsterInFront.getCurrentAttackDamage();
        // Checks no additional abilities are triggered
        Monster triggeredAbility = monster.ability(allyTeam, enemyTeam);
        assertNull(triggeredAbility);
        assertEquals(startAttackDamage, monsterInFront.getCurrentAttackDamage()); // 50% of 1 is 0 (rounded down)

        // Monster in front of Gil AttackDamage: 2
        monster.setCurrentAttackDamage(2);
        monsterInFront.restore(); // Reset stats
        startAttackDamage = monsterInFront.getCurrentAttackDamage();
        monster.ability(allyTeam, enemyTeam);
        assertEquals(startAttackDamage + (monster.getCurrentAttackDamage() / 2), // 50% of 2 is 1 (rounded down)
                monsterInFront.getCurrentAttackDamage());

        // Gil in front of team
        monster.setCurrentAttackDamage(2);
        allyTeam = new Team(monster);
        allyTeam.addMonster(ally);
        // Check Gil in front
        assertEquals(monster, allyTeam.getFirstAliveMonster());
        int startAttackDamageGil = monster.getCurrentAttackDamage();
        int startAttackDamageMonsterBehind = ally.getCurrentAttackDamage();
        monster.ability(allyTeam, enemyTeam);
        assertEquals(startAttackDamageGil, monster.getCurrentAttackDamage()); // Gil attack isn't changed
        assertEquals(startAttackDamageMonsterBehind, ally.getCurrentAttackDamage()); // Behind attack isn't changed'

    }
}
