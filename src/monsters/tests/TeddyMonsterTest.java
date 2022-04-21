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

import java.util.stream.Stream;

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
