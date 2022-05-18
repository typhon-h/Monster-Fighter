package monsters;

import java.util.ArrayList;

import battle.BattleEvent;
import main.GameEnvironment;
import main.Team;

/**
 * Teddy Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
 */
public class TeddyMonster extends Monster {
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
        this.setTrigger(Trigger.BEFOREATTACK);
    }

    @Override
    public BattleEvent ability(boolean inPlayerTeam, Team allyTeam, Team enemyTeam) {
        // +1 Health to a random ALLY

        ArrayList<Monster> possibleMembers = allyTeam.getAliveMonsters();
        if (possibleMembers.size() > 0) {
            Monster monsterToAdjust = possibleMembers.get(GameEnvironment.rng.nextInt(possibleMembers.size()));
            monsterToAdjust.setCurrentHealth(monsterToAdjust.getCurrentHealth() + 1);

            Team player = (inPlayerTeam) ? allyTeam : enemyTeam;
            Team opponent = (inPlayerTeam) ? enemyTeam : allyTeam;
            return new BattleEvent(player, opponent, this.getName() + "'s " + this.getTrigger().name()
                    + " ability triggered. " + monsterToAdjust.getName() + " gained 1 HP");
        }
        return null; // Empty
    }

}