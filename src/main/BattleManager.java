package main;

import java.util.ArrayList;

import main.Player;

/**
 * A class for managing all the battles and functionality of battles
 * which a player can prticipate in.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public class BattleManager {
    Player allyPlayer;
    ArrayList<Player> opponents;
    Player currentOpponent = null;
    boolean battleResult = false;

    public BattleManager(Player player) {
        allyPlayer = player;
    }

    public void generateOpponents(int currentDay, int maxDays, Difficulty difficulty) {}
}
