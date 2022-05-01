package monsters;

import java.util.ArrayList;
import java.util.Random;

import main.Trigger;
import main.BattleEvent;
import main.Team;

/**
 * Jynx Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
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
                "-1 Health to a random ENEMY",
                MonsterConstants.JYNXBASESPEED); // Base Speed
        this.setTrigger(Trigger.ONFAINT); // TODO decide trigger
    }

    @Override
    public BattleEvent ability(Team allyTeam, Team enemyTeam) {
        // TODO: new ability is on GAME-MANAGER Branch

        ArrayList<Monster> possibleMembers = enemyTeam.getAliveMonsters();
        if (possibleMembers.size() > 0) {
            Monster monsterToAdjust = possibleMembers.get(rng.nextInt(possibleMembers.size()));
            return null;
        }

        return null;
    }

}