package main;

import java.util.ArrayList;
import java.util.Arrays;

import shop.*;
import monsters.*;

public class GameEnvironment {
    Player player;
    int maxDays;
    int currentDay;
    static Difficulty difficulty;
    SellShop sellShop;
    BuyShop buyShop;
    BattleManager battleState;
    boolean gameOverStatus = false;

    public GameEnvironment(Player player, int maxDays, Difficulty difficulty) {
        this.player = player;
        this.maxDays = maxDays;
        currentDay = 1;
        GameEnvironment.difficulty = difficulty;
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
        // Random level up
        // Random leave
        // Random join

        return new ArrayList<String>();
    }

}
