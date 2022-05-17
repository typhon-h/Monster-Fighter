package main;

import org.junit.jupiter.api.*;

import exceptions.*;
import monsters.ClinkMonster;
import monsters.GilMonster;
import monsters.Monster;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link main.GameEnvironment GameEnvironment} Class
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class GameEnvironmentTest {

    /**
     * {@link main.GameEnvironment GameEnvironment} to be tested
     */
    GameEnvironment game;

    /**
     * Set up a {@link main.GameEnvironment GameEnvironment} for Testing
     *
     * @throws TeamSizeException         if too many members added to
     *                                   {@link main.Team team}
     * @throws DuplicateMonsterException if {@link monsters.Monster monster} already
     *                                   in {@link main.Team team}
     */
    @BeforeEach
    public void setUp() throws TeamSizeException, DuplicateMonsterException {
        Team defaultTeam = new Team(new ClinkMonster(), new GilMonster());
        Player player = new Player("MyPlayer", defaultTeam, 5);
        game = new GameEnvironment(player, 15, Difficulty.NORMAL);
    }

    /**
     * Tests {@link main.GameEnvironment GameEnvironment} can be set up with a
     * distinguished seed
     * Seed: 0
     * Generates: -1155484576, -723955400, 1033096058, -1690734402, -1557280266
     */
    @Test
    public void gameEnvironmentSeedTest() {
        new GameEnvironment(this.game.getPlayer(), 1, Difficulty.NORMAL, 0);
        assertEquals(GameEnvironment.rng.nextInt(), -1155484576);
        assertEquals(GameEnvironment.rng.nextInt(), -723955400);
        assertEquals(GameEnvironment.rng.nextInt(), 1033096058);
        assertEquals(GameEnvironment.rng.nextInt(), -1690734402);
        assertEquals(GameEnvironment.rng.nextInt(), -1557280266);
    }

    /**
     * Checks sleeping advances the day until the last day and then ends the game
     */
    @Test
    public void sleepDayAdvanceTest() {
        int i = 1;
        for (; i <= game.getTotalDays(); i++) {
            assertEquals(i, game.getCurrentDay());
            assertFalse(game.isGameOver());
            game.sleep();
        }
        assertEquals(i - 1, game.getTotalDays());
        assertTrue(game.isGameOver());
    }

    /**
     * Checks sleeping updates the {@link shop.BuyShop BuyShop}
     */
    @Test
    public void sleepShopUpdateTest() {
        ArrayList<Entity> prevContent = new ArrayList<Entity>(game.getBuyShop().getContent());
        game.sleep();
        assertNotEquals(prevContent, game.getBuyShop().getContent());
    }

    /**
     * Checks sleeping refreshes the {@link main.BattleManager battle state} by
     * {@link main.BattleManager#generateOpponents generating new opponents}
     */
    // @Test
    // public void sleepGenerateOpponentsTest() {
    // ArrayList<Player> prevOpponents = game.getBattleState().getOpponents();
    // game.sleep();
    // assertNotEquals(prevOpponents, game.getBattleState().getOpponents());
    // }

    /**
     * Checks sleeping {@link monsters.Monster#restore restores} all
     * {@link monsters.Monster monsters} in {@link main.Team team}
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
     * Check {@link main.RandomEvent random events} do not occur with given setup
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
     * Check {@link main.RandomEvent random events} do occur with given setup
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

    /**
     * Test that the {@link battle.BattleManager battle state} is refreshed
     */
    @Test
    public void sleepBattleRefreshTest() {
        ArrayList<Player> prevOpponents = new ArrayList<Player>(game.getBattleState().getOpponents());
        game.sleep();
        assertNotEquals(prevOpponents, game.getBattleState().getOpponents());
    }
}
