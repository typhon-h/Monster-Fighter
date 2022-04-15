package monsters;

import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import main.Rarity;
import main.Trigger;
import main.Team;

/**
 * Jynx Monster
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class JynxMonster extends Monster {
    /**
     * Random number generator
     */
    Random rng = new Random();

    /**
     * Creates a new Monster with specified base stats
     */
    public JynxMonster() {
        super(
                "Jynx", // Name
                "Highly trained assassin. *CLASSIFIED*", // Description
                0, // Buy Price
                0, // Sell Price
                Rarity.LEGENDARY, // Rarity
                1, // Base AttackDamage
                3, // Base Health
                "-1 Health to a random ENEMY");
        this.setTrigger(Trigger.ONFAINT); // TODO decide trigger
    }

    @Override
    public Queue<Monster> ability(Team allyTeam, Team enemyTeam) {
        // -1 Health to a random ENEMY
        Queue<Monster> triggeredAbilities = new LinkedList<Monster>();

        ArrayList<Monster> possibleMembers = enemyTeam.getAliveMonsters();
        Monster monsterToAdjust = possibleMembers.get(rng.nextInt() % possibleMembers.size());
        triggeredAbilities.addAll(monsterToAdjust.takeDamage(1));

        return triggeredAbilities;
    }

}