package monsters;

import main.Trigger;
import main.Team;

import exceptions.TeamStatusException;

/**
 * Ditta Monster
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class DittaMonster extends Monster {
    /**
     * Creates a new Monster with specified base stats
     */
    public DittaMonster() {
        super(
                "Ditta", // Name
                "It looks familiar, just can't put my finger on it", // Description
                MonsterConstants.DITTARARITY, // Rarity
                MonsterConstants.DITTABASEATTACKDAMAGE, // Base AttackDamage
                MonsterConstants.DITTABASEHEALTH, // Base Health
                "COPY the stats of the enemy in front");
        this.setTrigger(Trigger.STARTOFBATTLE); // TODO decide trigger
    }

    @Override
    public Monster ability(Team allyTeam, Team enemyTeam) {
        // COPY the stats of the enemy in front

        try {
            Monster monsterToCopy = enemyTeam.getFirstAliveMonster();
            this.setCurrentAttackDamage(monsterToCopy.getCurrentAttackDamage());
            this.setCurrentHealth(monsterToCopy.getCurrentHealth());
        } catch (TeamStatusException e) {
            // Do Nothing
        }

        return null; // No Trigger
    }

}