package monsters;

import java.util.ArrayList;
import java.util.Random;

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
                MonsterConstants.JYNXRARITY, // Rarity
                MonsterConstants.JYNXBASEATTACKDAMAGE, // Base AttackDamage
                MonsterConstants.JYNXBASEHEALTH, // Base Health
                "-1 Health to a random ENEMY");
        this.setTrigger(Trigger.ONFAINT); // TODO decide trigger
    }

    @Override
    public Monster ability(Team allyTeam, Team enemyTeam) {
        // -1 Health to a random ENEMY

        ArrayList<Monster> possibleMembers = enemyTeam.getAliveMonsters();
        if (possibleMembers.size() > 0) {
            Monster monsterToAdjust = possibleMembers.get(rng.nextInt(possibleMembers.size()));
            return monsterToAdjust.takeDamage(1);
        }

        return null;
    }

}