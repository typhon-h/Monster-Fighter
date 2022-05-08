package monsters;

import main.BattleEvent;
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
 * Testing for Lucifer Monster class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class LuciferMonsterTest {
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
        monster = new LuciferMonster();
        allyTeam = new Team(monster);
        enemyTeam = new Team(new ClinkMonster()); // Arbitrary
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
        assertEquals(MonsterConstants.LUCIFERBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.LUCIFERBASEHEALTH, monster.getBaseHealth());
        // Check buy/sell prices are set correctly
        assertEquals(buyPrice, monster.getBuyPrice());
        assertEquals(sellPrice, monster.getSellPrice());
        assertEquals(MonsterConstants.LUCIFERBASESPEED, monster.getSpeed());
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: stats are swapped
     * 
     * @throws DuplicateMonsterException if same monster is added more than once
     * @throws TeamSizeException         if more team members than max allowed
     */
    @Test
    public void abilityTest() throws TeamSizeException, DuplicateMonsterException {
        monster.setCurrentAttackDamage(2);
        monster.setCurrentHealth(1);
        int startAttackDamage = monster.getCurrentAttackDamage();
        int startHealth = monster.getCurrentHealth();
        monster.ability(allyTeam, enemyTeam);

        assertEquals(startHealth, monster.getCurrentAttackDamage());
        assertEquals(startAttackDamage, monster.getCurrentHealth());

        // Check base stats are unchanged
        assertEquals(MonsterConstants.LUCIFERBASEHEALTH, monster.getBaseHealth());
        assertEquals(MonsterConstants.LUCIFERBASEATTACKDAMAGE, monster.getBaseAttackDamage());
    }

    /**
     * Checks returns valid {@link main.BattleEvent battle event}
     */
    @Test
    public void abilityReturnTest() {
        BattleEvent ability = monster.ability(allyTeam, enemyTeam);
        assertEquals(monster.getName() + "'s " + monster.getTrigger().name()
                + " ability triggered. ATK and HP have been swapped", ability.getDescription());
    }
}
