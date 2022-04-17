package monsters;

import main.Trigger;
import main.Team;

/**
 * Clink Monster
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
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
                "-1 Attack and +1 Health (min 1 Attack)");
        this.setTrigger(Trigger.AFTERATTACK); // TODO decide trigger
    }

    @Override
    public Monster ability(Team allyTeam, Team enemyTeam) {
        // -1 Attack and +1 Health (min 1 Attack)
        if (this.getCurrentAttackDamage() > 1) { // Attack can't go lower than 1
            this.setCurrentAttackDamage(this.getCurrentAttackDamage() - 1);
            this.setCurrentHealth(this.getCurrentHealth() + 1);
        }
        return null; // Empty
    }

}