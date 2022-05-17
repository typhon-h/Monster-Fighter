package monsters;

import battle.BattleEvent;
import main.Team;

/**
 * Ditta Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
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
                "COPY the stats of the enemy in front",
                MonsterConstants.DITTABASESPEED); // Base Speed
        this.setTrigger(Trigger.STARTOFBATTLE);
    }

    @Override
    public BattleEvent ability(boolean inPlayerTeam, Team allyTeam, Team enemyTeam) {
        // COPY the stats of the enemy in front

        Monster monsterToCopy = enemyTeam.getFirstAliveMonster();
        if (monsterToCopy != null && monsterToCopy.getClass() != DittaMonster.class) {
            this.setCurrentAttackDamage(monsterToCopy.getCurrentAttackDamage());
            this.setCurrentHealth(monsterToCopy.getCurrentHealth());
            Team player = (inPlayerTeam) ? allyTeam : enemyTeam;
            Team opponent = (inPlayerTeam) ? enemyTeam : allyTeam;
            return new BattleEvent(player, opponent, this.getName() + "'s " + this.getTrigger().name()
                    + " ability triggered. " + this.getName() + " copied " + monsterToCopy.getName() + "'s stats.");

        }

        return null; // No Trigger
    }

}