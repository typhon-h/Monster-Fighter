package main.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import items.ItemConstants;
import main.*;
import monsters.*;

/**
 * Testing {@link main.BattleManager BattleManager} class
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
class BattleManagerTests {
    Player player;
    Team playerTeam;
    BattleManager battleManager;

    @BeforeEach
    void setUp() throws Exception {
        playerTeam = new Team(new ClinkMonster());
        player = new Player(playerTeam, 0);
        player.addGold(100);

        battleManager = new BattleManager(player);
    }

    // /**
    //  * Testing that every monster is put in the team at
    //  */
    // @Test
    // public void getRandomMonsterTest() {
    //     HashSet<Class<?>> monsterClasses = new HashSet<Class<?>>();

    //     int i = 0;
    //     while (monsterClasses.size() < Monster.NUMMONSTERS && i < 1000) {
    //         monsterClasses.add(battleManager.getRandomMonster(Difficulty.HARD).getClass());
    //         i++;
    //     }
    //     System.out.println(monsterClasses.toString());
    //     assertEquals(monsterClasses.size(), Monster.NUMMONSTERS);
    // }

    /**
     * Test cases containing the current day and the number of monsters
     * that should be generated for that day
     *
     * @return A stream of day, monster number arguments
     */
    private static Stream<Arguments> dayAndMonsterNum() {
        return Stream.of(
                Arguments.arguments(1, 1),
                Arguments.arguments(3, 2),
                Arguments.arguments(6, 3),
                Arguments.arguments(6, 3),
                Arguments.arguments(8, 4),
                Arguments.arguments(10, 4),
                Arguments.arguments(11, 5),
                Arguments.arguments(12, 5),
                Arguments.arguments(13, 6),
                Arguments.arguments(15, 6));
    }

    /**
     * Checks that the correct amount of monsters are generated
     * for each day
     *
     * @param day         The day to check
     * @param numMonsters The correct amount of monsters for the given day
     */
    @ParameterizedTest
    @MethodSource("dayAndMonsterNum")
    public void generateTeamSizeTest(int day, int numMonsters) {
        Team team = battleManager.generateTeam(day, 15, Difficulty.HARD);

        assertEquals(team.getTeamSize(), numMonsters);
    }

    /**
     * Checks that items are used on the monsters in the team to
     * boost their stats
     */
    @Test
    public void itemsUsedOnTeam() {
        // Generate a team with one monster
        Team team = battleManager.generateTeam(1, 15, Difficulty.NORMAL);

        assertEquals(player, battleManager.getPlayer());

        // Get the one monster in the team
        Monster monster = team.getFirstAliveMonster();

        Monster baseMonster;
        Class<?> monsterClass = monster.getClass();

        if (monsterClass == ClinkMonster.class) {
            baseMonster = new ClinkMonster();
        } else if (monsterClass == DittaMonster.class) {
            baseMonster = new DittaMonster();
        } else if (monsterClass == GilMonster.class) {
            baseMonster = new GilMonster();
        } else if (monsterClass == JynxMonster.class) {
            baseMonster = new JynxMonster();
        } else if (monsterClass == LuciferMonster.class) {
            baseMonster = new LuciferMonster();
        } else if (monsterClass == TeddyMonster.class) {
            baseMonster = new TeddyMonster();
        } else {
            baseMonster = new ClinkMonster();
            fail("Provided monster was not matched.");
        }

        assertTrue(baseMonster.getBaseAttackDamage() < monster.getBaseAttackDamage() ||
                   baseMonster.getBaseHealth() < monster.getBaseHealth() ||
                   baseMonster.getSpeed() < monster.getSpeed());
    }

    // TODO: Test that the difficulty multiplier works.
}
