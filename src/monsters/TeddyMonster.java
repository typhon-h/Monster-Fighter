package monsters;

import java.util.ArrayList;
import java.util.Random;

import main.Trigger;
import main.BattleEvent;
import main.Team;

/**
 * Teddy Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
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
                MonsterConstants.TEDDYRARITY, // Rarity
                MonsterConstants.TEDDYBASEATTACKDAMAGE, // Base AttackDamage
                MonsterConstants.TEDDYBASEHEALTH, // Base Health
                "+1 Health to a random ALLY",
                MonsterConstants.TEDDYBASESPEED); // Base Speed
        this.setTrigger(Trigger.BEFOREATTACK); // TODO decide trigger
    }

    @Override
    public BattleEvent ability(Team allyTeam, Team enemyTeam) {
        // +1 Health to a random ALLY

        ArrayList<Monster> possibleMembers = allyTeam.getAliveMonsters();
        if (possibleMembers.size() > 0) {
            Monster monsterToAdjust = possibleMembers.get(rng.nextInt(possibleMembers.size()));
            monsterToAdjust.setCurrentHealth(monsterToAdjust.getCurrentHealth() + 1);
            return new BattleEvent((Team) allyTeam.clone(), (Team) enemyTeam.clone(),
                    this.getName() + "'s " + this.getTrigger().name() + " ability triggered. "
                            + monsterToAdjust.getName() + " gained 1 HP");
        }
        return null; // Empty
    }

}