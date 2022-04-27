package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
     * Player playing the game
     */
    private Player player;

    /**
     * Maximum number of days the game will last
     */
    private int maxDays;

    /**
     * Current day of the game
     * 
     * @default 1
     */
    private int currentDay = 1;;

    /**
     * Difficulty of the game
     */
    private Difficulty difficulty;

    /**
     * Represents whether the game should end
     * 
     * @default false
     */
    private boolean gameOverStatus = false;

    /**
     * Game sell shop where players can sell their entities
     */
    private SellShop sellShop;

    /**
     * Game buy shop where players can buy new entities
     */
    private BuyShop buyShop;

    /**
     * Battle Manager to control battle events in game
     */
    private BattleManager battleState;

    /**
     * Global random variable used for randomness of game
     */
    public static Random rng = new Random();

    /**
     * Constructor for GameEnvironment
     * 
     * @param player     active player
     * @param maxDays    days game will last for
     * @param difficulty difficulty of game
     */
    public GameEnvironment(Player player, int maxDays, Difficulty difficulty) {
        this.player = player;
        this.maxDays = maxDays;
        this.difficulty = difficulty;
        sellShop = new SellShop(player);
        buyShop = new BuyShop(player);
        battleState = new BattleManager(player);
    }

    /**
     * Generates a list of all possible monster objects
     * 
     * @return list of new monster objects
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
     * Advances the day, updates shops, restores monsters, and runs random events
     * 
     * @return ArrayList of strings describing all events that happened
     */
    public ArrayList<String> sleep() {
        ArrayList<String> events = new ArrayList<String>();
        // Advance day and check end game
        if (++currentDay > maxDays) {
            gameOverStatus = true;
            events.add("Game has ended");
            return events;
        }

        // Update shops
        sellShop.setContent();

        // Update battles
        battleState.generateOpponents(currentDay, maxDays, difficulty);

        // Heal monsters
        for (Monster monster : player.getTeam().getMonsters()) {
            monster.restore();
        }

        // Random boost (level up)
        events.addAll(RandomEvent.randomBoost(player.getTeam(), currentDay));

        // Random leave
        events.addAll(RandomEvent.randomMonsterLeave(player.getTeam()));

        // Random join
        String monsterJoinDescription = RandomEvent.randomMonsterJoin(player.getTeam(), currentDay);
        if (monsterJoinDescription != null) {
            events.add(monsterJoinDescription);
        }

        return events;
    }

    /**
     * Gets the active player
     * 
     * @return the active player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets maximum days the game will last for
     * 
     * @return maximum number of days
     */
    public int getMaxDays() {
        return maxDays;
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
     * Gets the difficulty of the game
     * 
     * @return the difficulty of the game
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the sell shop of the game
     * 
     * @return the sell shop state
     */
    public SellShop getSellShop() {
        return sellShop;
    }

    /**
     * Gets the buy shop of the game
     * 
     * @return the buy shop state
     */
    public BuyShop getBuyShop() {
        buyShop.setContent();
        return buyShop;
    }

    /**
     * Gets the battle state of the game
     * 
     * @return the battle state of the game
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
}
