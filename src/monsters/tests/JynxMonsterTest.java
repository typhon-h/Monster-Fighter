package monsters.tests;

import monsters.*;
import main.Rarity;
import main.Team;
import main.Trigger;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Testing for Jynx Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class JynxMonsterTest {
    Monster monster;

    /**
     * Set up a monster to test methods on before each test
     * 
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new JynxMonster();
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
        assertEquals(MonsterConstants.JYNXBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.JYNXBASEHEALTH, monster.getBaseHealth());
        // Check buy/sell prices are set correctly
        assertEquals(buyPrice, monster.getBuyPrice());
        assertEquals(sellPrice, monster.getSellPrice());
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: random enemy has Health reduced
     * Invalid: empty enemy team
     * Randomness must be checked manually
     * 
     * @throws TeamSizeException   too many members added to team
     * @throws TeamStatusException all team members are dead
     */
    @Test
    public void abilityTest() throws TeamSizeException, TeamStatusException {
        Team allyTeam = new Team(monster);
        Team enemyTeam = new Team(new ClinkMonster(), new ClinkMonster());

        // Check does damage
        int startEnemyTeamSumHealth = 0;
        int endEnemyTeamSumHealth = 0;
        for (Monster m : enemyTeam.getAliveMonsters()) {
            m.setTrigger(Trigger.NOABILITY);
            startEnemyTeamSumHealth += m.getCurrentHealth();
        }
        monster.ability(allyTeam, enemyTeam);
        for (Monster m : enemyTeam.getAliveMonsters()) {
            endEnemyTeamSumHealth += m.getCurrentHealth();
        }
        assertEquals(startEnemyTeamSumHealth - 1, endEnemyTeamSumHealth);

        // Check does nothing with empty team
        enemyTeam = new Team(new ClinkMonster());
        // Faint whole team
        enemyTeam.getFirstAliveMonster().takeDamage(enemyTeam.getFirstAliveMonster().getCurrentHealth());
        monster.ability(allyTeam, enemyTeam);
        endEnemyTeamSumHealth = 0;
        for (Monster m : enemyTeam.getMonsters()) {
            endEnemyTeamSumHealth += m.getCurrentHealth();
        }
        assertEquals(0, endEnemyTeamSumHealth); // Check damage hasn't been done to fainted enemies
    }

    /**
     * Tests if abilities are able to rescurse and hit a bound
     * Covers: ability
     * Valid: two monsters with ONHURT targetting each other
     * Valid: Monster with no abilty not having their ability trigger
     * 
     * @throws TeamStatusException if whole team has fainted
     */
    @Test
    public void recursiveAbilityTest() throws TeamStatusException {
        // Ability bounces back and forth until one faints
        /*
         * Looks like
         * Monster:2hp Enemy:2hp
         * MONSTER ABILITY TRIGGERS
         * 2hp 1hp enemy triggers
         * 1hp 1hp monster triggers
         * 1hp 0hp END
         */
        // SETUP
        Team allyTeam = new Team(monster);
        Monster enemy = new JynxMonster();
        Team enemyTeam = new Team(enemy);
        allyTeam.getFirstAliveMonster().setTrigger(Trigger.ONHURT);
        enemyTeam.getFirstAliveMonster().setTrigger(Trigger.ONHURT);
        allyTeam.getFirstAliveMonster().setCurrentHealth(2);
        enemyTeam.getFirstAliveMonster().setCurrentHealth(2);
        allyTeam.getFirstAliveMonster().setCurrentAttackDamage(1);
        enemyTeam.getFirstAliveMonster().setCurrentAttackDamage(1);
        ArrayList<Monster> expectedTriggered = new ArrayList<>(Arrays.asList(
                monster,
                enemy,
                monster));
        ArrayList<Monster> actualTriggered = new ArrayList<Monster>();
        Monster triggered = monster;
        // Test
        while (triggered != null) {
            actualTriggered.add(triggered);
            if (allyTeam.getMonsters().contains(triggered)) {
                triggered = triggered.ability(allyTeam, enemyTeam);
            } else {
                triggered = triggered.ability(enemyTeam, allyTeam);
            }

        }
        assertEquals(expectedTriggered, actualTriggered);

        // Does damage to monster without ONHURT doesn't rebound
        enemy.setTrigger(Trigger.NOABILITY);
        monster.restore();
        enemy.restore();
        triggered = monster;
        actualTriggered.clear();
        while (triggered != null) {
            actualTriggered.add(triggered);
            if (allyTeam.getMonsters().contains(triggered)) {
                triggered = triggered.ability(allyTeam, enemyTeam);
            } else {
                triggered = triggered.ability(enemyTeam, allyTeam);
            }
        }
        assertTrue(actualTriggered.size() == 1);
        assertTrue(actualTriggered.contains(monster));
    }
}
