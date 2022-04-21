package monsters.tests;

import monsters.*;
import main.Rarity;
import main.Team;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

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
        assertEquals(MonsterConstants.GILBASEATTACKDAMAGE, monster.getBaseAttackDamage());
        assertEquals(MonsterConstants.GILBASEHEALTH, monster.getBaseHealth());
        // Check buy/sell prices are set correctly
        assertEquals(buyPrice, monster.getBuyPrice());
        assertEquals(sellPrice, monster.getSellPrice());
    }

    /**
     * Checks ability effect activates correctly
     * Covers: ability
     * Valid: Monster in front of Gil AttackDamage: 1
     * Valid: Monster in fron of Gil AttackDamage: > 1
     * Invalid: Gil is front of the team
     * 
     * @throws DuplicateMonsterException if same monster is added more than once
     * @throws TeamSizeException         if more team members than max allowed
     */
    @Test
    public void abilityTest() throws TeamSizeException, DuplicateMonsterException {
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

        // Monster in front with Gil AttackDamage: 1
        monster.setCurrentAttackDamage(1);
        int startAttackDamage = monsterInFront.getCurrentAttackDamage();
        // Checks no additional abilities are triggered
        Monster triggeredAbility = monster.ability(allyTeam, enemyTeam);
        assertNull(triggeredAbility);
        assertEquals(startAttackDamage, monsterInFront.getCurrentAttackDamage()); // 50% of 1 is 0 (rounded down)

        // Monster in front with Gil AttackDamage: 2
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
