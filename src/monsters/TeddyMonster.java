package monsters;

import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import main.Rarity;
import main.Trigger;
import main.Team;

/**
 * Teddy Monster
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class TeddyMonster extends Monster {
    /**
     * Random number generator
     */
    Random rng = new Random();

    /**
     * Creates a new Monster with specified base stats
     */
    public TeddyMonster() {
        super(
                "Teddy", // Name
                "Legend says she always puts others before herself", // Description
                0, // Buy Price
                0, // Sell Price
                Rarity.LEGENDARY, // Rarity
                1, // Base AttackDamage
                3, // Base Health
                "+1 Health to a random ALLY");
        this.setTrigger(Trigger.BEFOREATTACK); // TODO decide trigger
    }

    @Override
    public Queue<Monster> ability(Team allyTeam, Team enemyTeam) {
        // +1 Health to a random ALLY
        Queue<Monster> triggeredAbilities = new LinkedList<Monster>();

        ArrayList<Monster> possibleMembers = allyTeam.getAliveMonsters();
        Monster monsterToAdjust = possibleMembers.get(rng.nextInt() % possibleMembers.size());
        monsterToAdjust.setCurrentHealth(monsterToAdjust.getCurrentHealth() + 1);

        return triggeredAbilities; // Empty
    }

}