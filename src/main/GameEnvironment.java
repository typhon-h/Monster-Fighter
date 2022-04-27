package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import shop.*;
import monsters.*;

public class GameEnvironment {
    private Player player;
    private int maxDays;
    private int currentDay;
    private Difficulty difficulty;
    private boolean gameOverStatus = false;
    private SellShop sellShop;
    private BuyShop buyShop;
    private BattleManager battleState;
    public static Random rng = new Random();

    public GameEnvironment(Player player, int maxDays, Difficulty difficulty) {
        this.player = player;
        this.maxDays = maxDays;
        currentDay = 1;
        this.difficulty = difficulty;
        sellShop = new SellShop(player);
        buyShop = new BuyShop(player);
        battleState = new BattleManager(player);
    }

    public static ArrayList<Monster> generateMonsters() {
        return new ArrayList<Monster>(Arrays.asList( // All Monsters
                new ClinkMonster(),
                new DittaMonster(),
                new GilMonster(),
                new JynxMonster(),
                new LuciferMonster(),
                new TeddyMonster()));
    }

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

    public Player getPlayer() {
        return this.player;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public SellShop getSellShop() {
        return sellShop;
    }

    public BuyShop getBuyShop() {
        buyShop.setContent();
        return buyShop;
    }

    public BattleManager getBattleState() {
        return battleState;
    }

    public boolean isGameOver() {
        return gameOverStatus;
    }
}
