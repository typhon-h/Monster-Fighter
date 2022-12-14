package monsters;

import battle.BattleEvent;
import main.Team;

/**
 * Clink Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
 */
public class ClinkMonster extends Monster {
    /**
     * Creates a new Monster with specified base stats
     */
    public ClinkMonster() {
        super(
                "Clink", // Name
                "Not very friendly until you get to know him", // Description
                MonsterConstants.CLINKRARITY, // Rarity
                MonsterConstants.CLINKBASEATTACKDAMAGE, // Base AttackDamage
                MonsterConstants.CLINKBASEHEALTH, // Base Health
                "-1 Attack and +1 Health (min 1 Attack)",
                MonsterConstants.CLINKBASESPEED); // Base Speed
        this.setTrigger(Trigger.AFTERATTACK);
    }

    // Runs the ability of the monster - javadoc inherited from super
    @Override
    public BattleEvent ability(boolean inPlayerTeam, Team allyTeam, Team enemyTeam) {
        // -1 Attack and +1 Health (min 1 Attack)
        if (this.getCurrentAttackDamage() > 1) { // Attack can't go lower than 1
            this.setCurrentAttackDamage(this.getCurrentAttackDamage() - 1);
            this.setCurrentHealth(this.getCurrentHealth() + 1);
            Team player = (inPlayerTeam) ? allyTeam : enemyTeam;
            Team opponent = (inPlayerTeam) ? enemyTeam : allyTeam;
            return new BattleEvent(player, opponent, this.getName() + "'s " + this.getTrigger().name()
                    + " ability triggered. Lost 1 ATK and gained 1 HP.");

        }
        return null;
    }

}