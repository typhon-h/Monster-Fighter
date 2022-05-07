package main.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;
import main.*;
import monsters.*;

/**
 * Testing {@link main.BattleManager BattleManager} class
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
class BattleManagerTests {
    /**
     * {@link main.Player Player} to test with
     */
    Player player;
    /**
     * {@link main.Team team} of the {@link main.Player player} to be tested
     */
    Team playerTeam;
    /**
     * {@link main.BattleMangager BattleManager} to be tested
     */
    BattleManager battleManager;

    /**
     * Set up {@link main.BattleMangager BattleManager} before each test
     *
     * @throws Exception general case if creating {@link main.Tean tean} or
     *                   {@link main.BattleMangager BattleManager} causes an
     *                   exception
     */
    @BeforeEach
    void setUp() throws Exception {
        playerTeam = new Team(new ClinkMonster());
        player = new Player(playerTeam, 0);
        player.addGold(100);

        battleManager = new BattleManager(player, Difficulty.NORMAL, 15);
    }

    /**
     * Test cases containing the current day and the number of
     * {@link monsters.Monster monsters}
     * that should be generated for that day
     *
     * @return A stream of day, monster number arguments
     */
    private static Stream<Arguments> dayAndMonsterNum() {
        return Stream.of(
                Arguments.arguments(1, 1),
                Arguments.arguments(3, 1),
                Arguments.arguments(6, 2),
                Arguments.arguments(8, 3),
                Arguments.arguments(10, 3),
                Arguments.arguments(11, 3),
                Arguments.arguments(12, 4),
                Arguments.arguments(13, 4),
                Arguments.arguments(15, 4));
    }

    /**
     * Checks that the correct amount of {@link monsters.Monster monsters} are
     * generated
     * for each day
     *
     * @param day         The day to check
     * @param numMonsters The correct amount of {@link monsters.Monster monsters}
     *                    for the given day
     */
    @ParameterizedTest
    @MethodSource("dayAndMonsterNum")
    public void generateTeamSizeTest(int day, int numMonsters) {
        Team team = battleManager.generateTeam(day, 15, Difficulty.HARD);

        assertEquals(team.getTeamSize(), numMonsters);
    }

    /**
     * Checks that {@link items.Item items} are used on the {@link monsters.Monster
     * monsters} in the {@link main.Team team} to
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

    /**
     * Test for generating opponents
     */
    @Test
    public void generateOpponentsTest() {
        battleManager.generateOpponents(1, 15, Difficulty.NORMAL);
        ArrayList<Player> opponents1 = battleManager.getOpponents();
        Player opponent1 = opponents1.get(0);

        // Check that there are the correct amount of opponents
        assertEquals(opponents1.size(), BattleConstants.NUMOPPONENTS);
        // Check that the gold amount from day 1 is not 0
        assertNotEquals(opponent1.getGold(), 0);

        // Check that the gold increases every day
        battleManager.generateOpponents(2, 15, Difficulty.NORMAL);
        ArrayList<Player> opponents2 = battleManager.getOpponents();
        Player opponent2 = opponents2.get(0);

        // Check that the the gold of the opponent on the second day is more
        // than the gold of the opponent on the first day
        assertTrue(opponent1.getGold() < opponent2.getGold(),
                "Day one gold: " + opponents1.get(0).getGold() +
                        " day two gold: " + opponents2.get(0).getGold());

        // Testing points is not 0
        assertNotEquals(opponent1.getScore(), 0);
        // Testing 2nd day points is more than 1st day points
        assertTrue(opponent1.getScore() < opponent2.getScore());

        // Testing that gold and points is affected by difficulty
        battleManager.generateOpponents(1, 15, Difficulty.HARD);
        opponent2 = battleManager.getOpponents().get(0);

        assertTrue(opponent1.getGold() > opponent2.getGold());
        assertTrue(opponent1.getScore() < opponent2.getScore());

        battleManager.generateOpponents(1, 15, Difficulty.EASY);
        opponent2 = battleManager.getOpponents().get(0);

        assertTrue(opponent1.getGold() < opponent2.getGold());
        assertTrue(opponent1.getScore() > opponent2.getScore());
    }

    /* TODO: test that if the first monster KOs the enemy monster then the 2nd monster in the
             enemy team fights
       TODO: test that the fight ends when all the monsters on one team are fainted
       TODO: test that the event log gives the correct output*/

    /**
     * Check that both monsters at the front of each team take damage and none of
     * the monsters faint. The method is tested through the information obtained by
     * the event log (BattleEvent class).
     * Covers: fight method (excluding ability) and simulateBattle method.
     */
    @Test
    public void bothTeamsTakeDamage() throws TeamSizeException, DuplicateMonsterException {
        int allyMonsterDmg = 3;
        int oppoMonsterDmg = 6;
        int monsterHealth  = 10;

        assertTrue(allyMonsterDmg < monsterHealth && oppoMonsterDmg < monsterHealth,
                "Damage of both monsters must be less than the hp the opposing moster.");

        Monster allyMonster = battleManager.getPlayer().getTeam().getFirstAliveMonster();
        allyMonster.setName("Monster 1");
        allyMonster.setTrigger(Trigger.NOABILITY);
        allyMonster.setCurrentHealth(10);
        allyMonster.setCurrentAttackDamage(allyMonsterDmg);

        // Create opponent
        Monster opponentMonster = new ClinkMonster();
        opponentMonster.setName("Monster 2");
        opponentMonster.setTrigger(Trigger.NOABILITY);
        opponentMonster.setCurrentHealth(10);
        opponentMonster.setCurrentAttackDamage(oppoMonsterDmg);
        Team opponentTeam;

        opponentTeam = new Team(opponentMonster);

        Player opponent = new Player(opponentTeam, 100);

        battleManager.setOpponent(opponent);
        battleManager.simulateBattle();

        BattleEvent event1 = battleManager.nextEvent();
        assertEquals("Monster 1 dealt " + allyMonsterDmg + " damage to Monster 2",
                event1.getDescription());
        assertEquals(monsterHealth - allyMonsterDmg,
                event1.getOpponentTeam().getFirstAliveMonster().getCurrentHealth());


        BattleEvent event2 = battleManager.nextEvent();
        assertEquals("Monster 2 dealt " + oppoMonsterDmg + " damage to Monster 1",
                event2.getDescription());
        assertEquals(monsterHealth - oppoMonsterDmg,
                event2.getAllyTeam().getFirstAliveMonster().getCurrentHealth());
    }

    // TODO: Refactor this code to test FIGHT method. Stolen from Monster.takeDamage
    /**
    // * Check ONHURT event is triggered
    // * Covers: takeDamage
    // * Valid: receives damage and has ONHURT
    // * Invalid: receives damage and has ONFAINT
    // * Invalid: receives damage and faints and has ONHURT
    // */
    // @Test
    // public void onHurtTriggerTest() {
    //     monster.setTrigger(Trigger.ONHURT);
    //     Monster triggeredAbility = monster.takeDamage(monster.getBaseHealth() - 1);
    //     // Non lethal
    //     // Hurt ability was triggered
    //     assertEquals(monster, triggeredAbility);

    //     monster.restore();
    //     monster.setTrigger(Trigger.ONFAINT);
    //     triggeredAbility = monster.takeDamage(monster.getBaseHealth() - 1); // Non
    //     lethal
    //     // Hurt ability was not triggered
    //     assertNull(triggeredAbility);

    //     monster.restore();
    //     monster.setTrigger(Trigger.ONHURT);
    //     triggeredAbility = monster.takeDamage(monster.getBaseHealth() + 1); // Lethal
    //     assertNull(triggeredAbility);
    // }

    // /**
    // * Check ONFAINT event is triggered
    // * Covers: takeDamage
    // * Valid: receives damage and faints and has ONFAINT
    // * Invalid: receives damage and has ONFAINT
    // * Invalid: receives damage and has ONHURT
    // */
    // @Test
    // public void onFaintTriggerTest() {
    // monster.setTrigger(Trigger.ONFAINT);
    // Monster triggeredAbility = monster.takeDamage(monster.getCurrentHealth());
    // // Faint ability was triggered
    // assertEquals(monster, triggeredAbility);
    // assertFalse(monster.getStatus()); // Check fainted

    // monster.restore();
    // triggeredAbility = monster.takeDamage(monster.getBaseHealth() - 1); // Non
    // lethal
    // // Faint ability was not triggered
    // assertNull(triggeredAbility);
    // assertTrue(monster.getStatus()); // Check fainted

    // monster.restore();
    // monster.setTrigger(Trigger.ONHURT);
    // triggeredAbility = monster.takeDamage(monster.getCurrentHealth());
    // // Hurt ability was not triggered
    // assertNull(triggeredAbility);
    // assertFalse(monster.getStatus()); // Check fainted

    // }
}
