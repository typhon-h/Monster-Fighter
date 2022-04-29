package main.tests;

import org.junit.jupiter.api.*;

import exceptions.*;
import main.*;
import monsters.ClinkMonster;
import monsters.GilMonster;
import monsters.Monster;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameEnvironmentTest {

    GameEnvironment game;

    /**
     * Set up a Game Environment for Testing
     * 
     * @throws TeamSizeException         if too many members added to team
     * @throws DuplicateMonsterException if monster already in team
     */
    @BeforeEach
    public void setUp() throws TeamSizeException, DuplicateMonsterException {
        Team defaultTeam = new Team(new ClinkMonster(), new GilMonster());
        Player player = new Player("MyPlayer", defaultTeam, 5);
        game = new GameEnvironment(player, 15, Difficulty.NORMAL);
    }

    /**
     * Checks sleeping advances the day until the last day and then ends the game
     */
    @Test
    public void sleepDayAdvanceTest() {
        int i = 1;
        for (; i <= game.getMaxDays(); i++) {
            assertEquals(i, game.getCurrentDay());
            assertFalse(game.isGameOver());
            game.sleep();
        }
        assertEquals(i - 1, game.getMaxDays());
        assertTrue(game.isGameOver());
    }

    /**
     * Checks sleeping updates the buy shop
     */
    @Test
    public void sleepShopUpdateTest() {
        ArrayList<Entity> prevContent = new ArrayList<Entity>(game.getBuyShop().getContent());
        game.sleep();
        assertNotEquals(prevContent, game.getBuyShop().getContent());
    }

    /**
     * Checks sleeping refreshes the battle state by generating new opponents
     */
    // @Test
    // public void sleepGenerateOpponentsTest() {
    // ArrayList<Player> prevOpponents = game.getBattleState().getOpponents();
    // game.sleep();
    // assertNotEquals(prevOpponents, game.getBattleState().getOpponents());
    // }

    /**
     * Checks sleeping restores all monsters in team
     */
    @Test
    public void sleepMonsterRestoreTest() {
        GameEnvironment.setSeed(5947); // forces all event rolls over 0.58
        int expectedStatSum = 0;
        Team team = game.getPlayer().getTeam();
        for (Monster monster : team.getMonsters()) {
            monster.takeDamage(1);
            expectedStatSum += monster.getBaseHealth();
        }

        game.sleep();

        int newStatSum = 0;
        for (Monster monster : team.getMonsters()) {
            newStatSum += monster.getCurrentHealth();
        }

        assertEquals(expectedStatSum, newStatSum);
    }

    /**
     * Check random events do not occur with given setup
     * Seed: 61883 no events occur with base probability
     * Monster Boost: 20%
     * Monster Leave: 0%
     * Monster Join: 5%
     *
     */
    @Test
    public void sleepNoRandomEvents() {
        GameEnvironment.setSeed(61883);

        ArrayList<String> eventOutcome = game.sleep();

        assertEquals(0, eventOutcome.size());
    }

    /**
     * Check random events do occur with given setup
     * Seed: 1860048 all events occur with base probability
     * Monster Boost: 20%
     * Monster Leave with 1 faint: 0% + 5% = 5%
     * Monster Join: 5%
     * 
     */
    @Test
    public void sleepAllRandomEvents() {
        GameEnvironment.setSeed(1860048);

        game.getPlayer().getTeam().getFirstAliveMonster().incrementFaintCount(); // Make monster leaving possible
        ArrayList<String> eventOutcome = game.sleep();

        ArrayList<String> expectedOutcome = new ArrayList<String>(Arrays.asList(
                "During the night Clink's SPEED was increased by 1",
                "During the night Gil's ATTACK was increased by 1",
                "Clink got sick of fainting and ran away",
                game.getPlayer().getTeam().getMonsters().get(1).getName() + " joined your team overnight"));

        assertEquals(expectedOutcome, eventOutcome);
    }
}
