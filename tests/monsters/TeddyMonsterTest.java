package monsters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import battle.BattleEvent;
import exceptions.*;
import main.*;

/**
 * Testing for Teddy Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class TeddyMonsterTest {
    /**
     * Monster to run tests on
     */
    Monster monster;
    /**
     * Ally team for ability testing
     */
    Team allyTeam;
    /**
     * Enemy team for ability testing
     */
    Team enemyTeam;

    /**
     * Set up a monster to test methods on before each test
     * 
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new TeddyMonster();
        allyTeam = new Team(monster, new ClinkMonster());
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
        assertEquals(MonsterConstants.TEDDYBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.TEDDYBASEHEALTH, monster.getBaseHealth());
        assertEquals(MonsterConstants.TEDDYBASESPEED, monster.getSpeed());
        // Check buy/sell prices are set correctly
        assertEquals(buyPrice, monster.getBuyPrice());
        assertEquals(sellPrice, monster.getSellPrice());
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: random enemy has Health reduced
     * Valid: heals itself if only member
     * Randomness must be checked manually
     * 
     * @throws DuplicateMonsterException if same monster is added more than once
     * @throws TeamSizeException         if more team members than max allowed
     */
    @Test
    public void abilityTest() throws TeamSizeException, DuplicateMonsterException {
        // Check boosts health
        int startAllyTeamSumHealth = 0;
        int startEnemyTeamSumHealth = 0;
        int endAllyTeamSumHealth = 0;
        int endEnemyTeamSumHealth = 0;

        for (Monster m : allyTeam.getAliveMonsters()) {
            m.setTrigger(Trigger.NOABILITY);
            startAllyTeamSumHealth += m.getCurrentHealth();
        }
        for (Monster m : enemyTeam.getAliveMonsters()) {
            startEnemyTeamSumHealth += m.getCurrentHealth();
        }

        monster.ability(true, allyTeam, enemyTeam);

        for (Monster m : allyTeam.getAliveMonsters()) {
            endAllyTeamSumHealth += m.getCurrentHealth();
        }
        for (Monster m : enemyTeam.getAliveMonsters()) {
            endEnemyTeamSumHealth += m.getCurrentHealth();
        }

        assertEquals(startAllyTeamSumHealth + 1, endAllyTeamSumHealth);
        assertEquals(startEnemyTeamSumHealth, endEnemyTeamSumHealth);

        // Check heals itself if only member
        monster.restore();
        allyTeam = new Team(monster);
        monster.ability(true, allyTeam, enemyTeam);
        assertEquals(monster.getBaseHealth() + 1, monster.getCurrentHealth());
    }

    /**
     * Checks targets every member of team
     * Seed: 4119
     * Generates: 0, 1, 2, 3
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many members in team
     */
    @Test
    public void abilityCoverageTest() throws TeamSizeException, DuplicateMonsterException {
        GameEnvironment.setSeed(4119);
        ArrayList<Integer> teamStartHealth = new ArrayList<Integer>();
        // Fill team
        while (allyTeam.getTeamSize() < Team.getMaxTeamSize()) {
            allyTeam.addMonster(new ClinkMonster());
        }

        // Get start
        for (Monster m : allyTeam.getMonsters()) {
            teamStartHealth.add(m.getCurrentHealth());
            monster.ability(true, allyTeam, enemyTeam);
        }

        for (int i = 0; i < allyTeam.getTeamSize(); i++) {
            assertEquals(teamStartHealth.get(i) + 1, allyTeam.getMonsters().get(i).getCurrentHealth());
        }

    }

    /**
     * Checks returns valid {@link battle.BattleEvent battle event}
     */
    @Test
    public void abilityReturnTest() {
        GameEnvironment.setSeed(4119);
        BattleEvent ability = monster.ability(true, allyTeam, enemyTeam);
        assertEquals(monster.getName() + "'s " + monster.getTrigger().name()
                + " ability triggered. " + monster.getName() + " gained 1 HP.", ability.getDescription());
    }

}
