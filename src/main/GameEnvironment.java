package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import battle.BattleManager;
import shop.*;
import monsters.*;

/**
 * Game Environment class that serves as a container for all information related
 * to the active game environment
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class GameEnvironment {
    /**
     * {@link main.Player player} playing the game
     */
    private Player player;

    /**
     * Total number of days the game will last
     */
    private int totalDays;

    /**
     * Current day of the game
     *
     * @default 1
     */
    private int currentDay = 1;;

    /**
     * {@link main.Difficulty difficulty} of the game
     */
    private Difficulty difficulty;

    /**
     * Represents whether the game should end
     *
     * @default false
     */
    private boolean gameOverStatus = false;

    /**
     * Player starting gold constant
     */
    public static final int STARTINGGOLD = 30;

    /**
     * Number of starter monsters to generate
     */
    public static final int NUMSTARTERMONSTERS = 3;

    /**
     * Game {@link shop.SellShop sell shop} where {@link main.Player players} can
     * sell their {@link main.Entity entities}
     */
    private SellShop sellShop;

    /**
     * Game {@link shop.BuyShop buy shop} where {@link main.Player players} can
     * sell their {@link main.Entity entities}
     */
    private BuyShop buyShop;

    /**
     * {@link battle.BattleManager Battle Manager} to control battle events in game
     */
    private BattleManager battleState;

    /**
     * Global random variable used for randomness of game
     */
    public static Random rng = new Random();

    /**
     * Maximum days the game can last for
     */
    public static final int MAXDAYS = 15;

    /**
     * Minimum days the game can last for
     */
    public static final int MINDAYS = 5;

    /**
     * Create new GameEnvironment
     *
     * @param player     active {@link main.Player player}
     * @param totalDays  days game will last for
     * @param difficulty {@link main.Difficulty difficulty} of game
     */
    public GameEnvironment(Player player, int totalDays, Difficulty difficulty) {
        this.player = player;
        this.totalDays = totalDays;
        this.difficulty = difficulty;
        sellShop = new SellShop(player);
        buyShop = new BuyShop(player);
        battleState = new BattleManager(player, difficulty, totalDays);
    }

    /**
     * Create new GameEnvironment with {@link Random Random} seed
     *
     * @param player     active {@link main.Player player}
     * @param totalDays  days game will last for
     * @param difficulty {@link main.Difficulty difficulty} of game
     * @param seed       seed for the {@link Random#Random} generator
     */
    public GameEnvironment(Player player, int totalDays, Difficulty difficulty, long seed) {
        this.player = player;
        this.totalDays = totalDays;
        this.difficulty = difficulty;
        sellShop = new SellShop(player);
        buyShop = new BuyShop(player);
        battleState = new BattleManager(player, difficulty, GameEnvironment.MAXDAYS);
        rng = new Random(seed);
    }

    /**
     * Generates a list of all possible {@link monsters.Monster monster} objects
     *
     * @return list of new {@link monsters.Monster monster} objects
     */
    public static ArrayList<Monster> generateMonsters() {
        return new ArrayList<Monster>(Arrays.asList( // All Monsters
                new ClinkMonster(),
                new DittaMonster(),
                new GilMonster(),
                new JynxMonster(),
                new LuciferMonster(),
                new TeddyMonster()));
    }

    /**
     * Advances the day, {@link shop.BuyShop#setContent updates shops},
     * {@link monsters.Monster#restore restores
     * monsters}, and
     * runs {@link main.RandomEvent random events}
     *
     * @return ArrayList of strings describing all events that happened
     */
    public ArrayList<String> sleep() {
        ArrayList<String> events = new ArrayList<String>();
        // Advance day and check end game
        if (++currentDay > totalDays) {
            // Heal monsters for display at end of game
            for (Monster monster : player.getTeam().getMonsters()) {
                monster.restore();
            }
            
            gameOverStatus = true;
            currentDay = totalDays;
            events.add("Game has ended");
            return events;
        }

        // Random boost (level up)
        events.addAll(RandomEvent.randomBoost(player.getTeam(), currentDay, difficulty));

        // Random leave
        events.addAll(RandomEvent.randomMonsterLeave(player.getTeam(), difficulty));

        // Random join
        String monsterJoinDescription = RandomEvent.randomMonsterJoin(player.getTeam(), difficulty);
        if (monsterJoinDescription != null) {
            events.add(monsterJoinDescription);
        }

        // Update shops
        buyShop.setContent();

        // Update battles
        battleState.generateOpponents(currentDay, GameEnvironment.MAXDAYS, difficulty);

        // Heal monsters
        for (Monster monster : player.getTeam().getMonsters()) {
            monster.restore();
        }

        return events;
    }

    /**
     * Gets the active {@link main.Player player}
     *
     * @return the active {@link main.Player player}
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets maximum days the game will last for
     *
     * @return maximum number of days
     */
    public int getTotalDays() {
        return totalDays;
    }

    /**
     * Gets current day of the game
     *
     * @return the current day of the game
     */
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * Gets the {@link main.Difficulty difficulty} of the game
     *
     * @return the {@link main.Difficulty difficulty} of the game
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the {@link shop.SellShop sell shop} of the game
     *
     * @return the {@link shop.SellShop sell shop} state
     */
    public SellShop getSellShop() {
        sellShop.setContent();
        return sellShop;
    }

    /**
     * Gets the {@link shop.BuyShop buy shop} of the game
     *
     * @return the {@link shop.BuyShop buy shop} state
     */
    public BuyShop getBuyShop() {
        return buyShop;
    }

    /**
     * Gets the {@link battle.BattleManager battle state} of the game
     *
     * @return the {@link battle.BattleManager battle state} of the game
     */
    public BattleManager getBattleState() {
        return battleState;
    }

    /**
     * Gets boolean indicating whether the game should end
     *
     * @return boolean indicating whether the game should end
     */
    public boolean isGameOver() {
        return gameOverStatus;
    }

    /**
     * Sets the {@link main.GameEnvironment#rng rng} seed and refreshes variable
     *
     * @param seed seed for the generator
     */
    public static void setSeed(long seed) {
        GameEnvironment.rng = new Random(seed);
    }
}
