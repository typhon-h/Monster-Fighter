package monsters;

import java.util.ArrayList;

import main.Trigger;
import main.GameEnvironment;
import main.Team;

/**
 * Jynx Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
 */
public class JynxMonster extends Monster {
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
                "-1 Health to a random ENEMY",
                MonsterConstants.JYNXBASESPEED); // Base Speed
        this.setTrigger(Trigger.ONFAINT); // TODO decide trigger
    }

    public Monster ability(Team allyTeam, Team enemyTeam) {
        // -1 Health to a random ENEMY

        ArrayList<Monster> possibleMembers = enemyTeam.getAliveMonsters();
        if (possibleMembers.size() > 0) {
            Monster monsterToAdjust = possibleMembers.get(GameEnvironment.rng.nextInt(possibleMembers.size()));
            return monsterToAdjust.takeDamage(1);
        }

        return null;
    }

}