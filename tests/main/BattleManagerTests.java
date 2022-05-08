package main;

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
     * {@link main.Team Team} of the {@link main.Player player} to be tested
     */
    Team playerTeam;
    /**
     * {@link main.BattleManager BattleManager} to be tested
     */
    BattleManager battleManager;

    /**
     * Set up {@link main.BattleMangager BattleManager} before each test
     *
     * @throws Exception general case if creating {@link main.Team team} or
     *                   {@link main.BattleMangager BattleManager} causes an
     *                   exception
     */
    @BeforeEach
    void setUp() throws Exception {
        // Create a new battle manager
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

    /* --------------------------------------------------------
    Tests for simulate battle, fight, runAbility, and nextEvent */

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

        // Setup teams and simulate the battle
        assertTrue(allyMonsterDmg < monsterHealth && oppoMonsterDmg < monsterHealth,
                "Damage of both monsters must be less than the hp the opposing moster.");

        Monster allyMonster = battleManager.getPlayer().getTeam().getFirstAliveMonster();
        allyMonster.setName("Monster 1");
        allyMonster.setTrigger(Trigger.NOABILITY);
        allyMonster.setCurrentHealth(10);
        allyMonster.setCurrentAttackDamage(allyMonsterDmg);

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

        // Run tests
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

    /**
     * Tests that if a monster faints then on the next attack round, the
     * next alive monster attacks instead of the fainted monster
     */
    @Test
    public void faintedMonsterTest() throws TeamSizeException, DuplicateMonsterException {
        int allyMonsterDmg = 10;
        int oppoMonsterDmg1 = 6;
        int oppoMonsterDmg2 = 4;
        int monsterHealth  = 10;

        // Setup teams and simulate the battle
        Monster allyMonster = battleManager.getPlayer().getTeam().getFirstAliveMonster();
        allyMonster.setName("Ally Monster");
        allyMonster.setTrigger(Trigger.NOABILITY);
        allyMonster.setCurrentHealth(monsterHealth);
        allyMonster.setCurrentAttackDamage(allyMonsterDmg);

        Monster opponentMonster1 = new ClinkMonster();
        opponentMonster1.setName("Opponent Monster 1");
        opponentMonster1.setTrigger(Trigger.NOABILITY);
        opponentMonster1.setCurrentHealth(monsterHealth);
        opponentMonster1.setCurrentAttackDamage(oppoMonsterDmg1);

        Monster opponentMonster2 = new ClinkMonster();
        opponentMonster2.setName("Opponent Monster 2");
        opponentMonster2.setTrigger(Trigger.NOABILITY);
        opponentMonster2.setCurrentHealth(monsterHealth);
        opponentMonster2.setCurrentAttackDamage(oppoMonsterDmg2);

        Team opponentTeam = new Team(opponentMonster1, opponentMonster2);
        Player opponent = new Player(opponentTeam, 100);

        battleManager.setOpponent(opponent);
        battleManager.simulateBattle();

        // Run tests
        BattleEvent currEvent = battleManager.nextEvent();
        assertEquals("Ally Monster dealt 10 damage to Opponent Monster 1. Opponent Monster 1 fainted.",
                    currEvent.getDescription());
        assertEquals(opponentMonster2.getName(),
                currEvent.getOpponentTeam().getFirstAliveMonster().getName());

        currEvent = battleManager.nextEvent();
        assertEquals("Opponent Monster 2 dealt 4 damage to Ally Monster",
                currEvent.getDescription());
        assertEquals(monsterHealth - oppoMonsterDmg2,
                currEvent.getAllyTeam().getFirstAliveMonster().getCurrentHealth());
    }

    /**
     * Tests that the simulation ends after one of the teams have no alive monsters
     * and that all triggers have been processed.
     *
     * @throws TeamSizeException
     * @throws DuplicateMonsterException
     */
    @Test
    public void endAfterTeamWipe() throws TeamSizeException, DuplicateMonsterException {
        int allyMonsterDmg = 10;
        int monsterHealth  = 10;

        // Setup teams and simulate the battle
        Monster allyMonster = battleManager.getPlayer().getTeam().getFirstAliveMonster();
        allyMonster.setName("Monster 1");
        allyMonster.setTrigger(Trigger.AFTERATTACK);
        allyMonster.setCurrentHealth(monsterHealth);
        allyMonster.setCurrentAttackDamage(allyMonsterDmg);

        Monster opponentMonster = new ClinkMonster();
        opponentMonster.setName("Monster 2");
        opponentMonster.setTrigger(Trigger.NOABILITY);
        opponentMonster.setCurrentHealth(monsterHealth);
        Team opponentTeam;

        opponentTeam = new Team(opponentMonster);

        Player opponent = new Player(opponentTeam, 100);

        battleManager.setOpponent(opponent);
        battleManager.simulateBattle();

        BattleEvent battleEvent = battleManager.nextEvent();

        assertEquals("Monster 1 dealt 10 damage to Monster 2. Monster 2 fainted.",
                battleEvent.getDescription());
        assertEquals(monsterHealth, battleEvent.getAllyTeam().getFirstAliveMonster().getCurrentHealth());
        battleEvent = battleManager.nextEvent();
        assertEquals("Monster 1's AFTERATTACK ability triggered. Lost 1 ATK and gained 1 HP",
                battleEvent.getDescription());
        assertNull(battleManager.nextEvent());
    }

    /**
     * Test for checking that all the different triggers are activated at the right
     * time during the battle simulation and the event logs are correct.
     * The monsters a set up in in a way that all the triggers will be activated.
     * The order in which the triggers for each monster are placed also contributes
     * to this fact.
     *
     * @throws TeamSizeException
     * @throws DuplicateMonsterException
     */
    @Test
    public void triggersTests() throws TeamSizeException, DuplicateMonsterException {
        // Set up monsters with one of every trigger
        // STARTOFBATTLE, BEFOREATTACK, AFTERATTACK, ONHURT (monster must tank 1 attack),
        // ONFAINT- No damage fast
        // Enemy NOABILITY - heaps of damage, slow

        // Setup ally team using clink monsters
        Team allyTeam = null;
        Team oppoTeam = null;
        Player opponent;
        ClinkMonster newMonster;

        // Set up ally team
        int monsterNum = 1;
        Trigger[] triggers = new Trigger[] {Trigger.STARTOFBATTLE,
                                            Trigger.BEFOREATTACK,
                                            Trigger.AFTERATTACK,
                                            Trigger.ONHURT};
        for (Trigger trigger : triggers) {
            newMonster = new ClinkMonster();
            newMonster.setCurrentHealth(9);
            newMonster.setSpeed(10);
            newMonster.setCurrentAttackDamage(2);
            newMonster.setTrigger(trigger);
            newMonster.setName("AllyMonster " + monsterNum++);

            // Special case
            if (trigger == Trigger.ONHURT) {
                newMonster.setCurrentHealth(19);
            }

            if (allyTeam == null) {
                allyTeam = new Team(newMonster);
            } else {
                allyTeam.addMonster(newMonster);
            }
        }

        // Set up opponent team and player
        newMonster = new ClinkMonster();
        newMonster.setCurrentHealth(200);
        newMonster.setCurrentAttackDamage(10);
        newMonster.setSpeed(1);
        newMonster.setTrigger(Trigger.NOABILITY);
        newMonster.setName("OppoMonster");
        oppoTeam = new Team(newMonster);
        opponent = new Player(oppoTeam, 100);

        battleManager.getPlayer().setTeam(allyTeam);
        battleManager.setOpponent(opponent);

        battleManager.simulateBattle();

        String[] expectedOutputArray = new String[] {
            "AllyMonster 1's STARTOFBATTLE ability triggered. Lost 1 ATK and gained 1 HP",
            "AllyMonster 1 dealt 1 damage to OppoMonster",
            "OppoMonster dealt 10 damage to AllyMonster 1. AllyMonster 1 fainted.",
            "AllyMonster 2's BEFOREATTACK ability triggered. Lost 1 ATK and gained 1 HP",
            "AllyMonster 2 dealt 1 damage to OppoMonster",
            "OppoMonster dealt 10 damage to AllyMonster 2. AllyMonster 2 fainted.",
            "AllyMonster 3 dealt 2 damage to OppoMonster",
            "AllyMonster 3's AFTERATTACK ability triggered. Lost 1 ATK and gained 1 HP",
            "OppoMonster dealt 10 damage to AllyMonster 3. AllyMonster 3 fainted.",
            "AllyMonster 4 dealt 2 damage to OppoMonster",
            "OppoMonster dealt 10 damage to AllyMonster 4",
            "AllyMonster 4's ONHURT ability triggered. Lost 1 ATK and gained 1 HP",
            "AllyMonster 4 dealt 1 damage to OppoMonster",
            "OppoMonster dealt 10 damage to AllyMonster 4. AllyMonster 4 fainted."
        };

        for (String expectedOutput : expectedOutputArray) {
            assertEquals(expectedOutput, battleManager.nextEvent().getDescription());
        }
        assertNull(battleManager.nextEvent());
    }
}
