package monsters;

import main.BattleEvent;
import main.Rarity;
import main.Team;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;

import java.util.stream.Stream;

/**
 * Testing for Jynx Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class JynxMonsterTest {
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
        monster = new JynxMonster();
        enemyTeam = new Team(new ClinkMonster());
        allyTeam = new Team(monster, new ClinkMonster());
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
        assertEquals(MonsterConstants.JYNXBASESPEED, monster.getSpeed());
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: random enemy has Health reduced
     * Invalid: empty enemy team
     * Randomness must be checked manually
     * 
     * @throws DuplicateMonsterException if same monster is added more than once
     * @throws TeamSizeException         if more team members than max allowed
     */
    @Test
    public void abilityTest() throws TeamSizeException, DuplicateMonsterException {

        Monster allyMonster = allyTeam.getMonsters().get(1);

        // Health stays same if Jynx has highest health
        monster.setCurrentHealth(allyMonster.getCurrentHealth() + 1);
        int prevHealth = monster.getCurrentHealth();
        monster.ability(allyTeam, enemyTeam);
        assertEquals(prevHealth, monster.getCurrentHealth());

        // Health switches to highest health (not Jynx)
        allyMonster.setCurrentHealth(monster.getCurrentHealth() + 1);
        prevHealth = monster.getCurrentHealth();
        int expectedHealth = allyMonster.getCurrentHealth();
        monster.ability(allyTeam, enemyTeam);
        assertNotEquals(prevHealth, monster.getCurrentHealth());
        assertEquals(expectedHealth, monster.getCurrentHealth());

        // Health stays same if whole team has same health
        prevHealth = monster.getCurrentHealth();
        monster.ability(allyTeam, enemyTeam);
        assertEquals(prevHealth, monster.getCurrentHealth());
    }

    /**
     * Checks returns valid {@link main.BattleEvent battle event}
     */
    @Test
    public void abilityReturnTest() {
        BattleEvent ability = monster.ability(allyTeam, enemyTeam);
        assertEquals(monster.getName() + "'s " + monster.getTrigger().name()
                + " ability triggered. " + monster.getName() + "'s new HP is " + monster.getCurrentHealth(),
                ability.getDescription());
    }
}
