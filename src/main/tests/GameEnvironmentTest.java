package main.tests;

import org.junit.jupiter.api.*;

import exceptions.*;
import main.*;
import monsters.ClinkMonster;
import monsters.Monster;

import java.util.ArrayList;

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
        Team defaultTeam = new Team(new ClinkMonster());
        Player player = new Player("MyPlayer", defaultTeam, 5);
        game = new GameEnvironment(player, 15, Difficulty.NORMAL);
    }

    /**
     * Checks sleeping advances the day until the last day and then ends the game
     */
    @Test
    public void sleepDayAdvanceTest() {
        int i = 1;
        for (; i < game.getMaxDays(); i++) {
            assertEquals(i, game.getCurrentDay());
            assertFalse(game.isGameOver());
            game.sleep();
        }
        assertEquals(i, game.getMaxDays());
        assertTrue(game.isGameOver());
    }

    /**
     * Checks sleeping updates the sell shop
     */
    @Test
    public void sleepShopUpdateTest() {
        ArrayList<Entity> prevContent = game.getSellShop().getContent();
        game.sleep();
        assertNotEquals(prevContent, game.getSellShop().getContent());
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

    // TODO: Random Events testing???
}
