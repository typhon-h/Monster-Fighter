package monsters;

import battle.BattleEvent;
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
                "Only as strong as...the strongest member???", // Description
                MonsterConstants.JYNXRARITY, // Rarity
                MonsterConstants.JYNXBASEATTACKDAMAGE, // Base AttackDamage
                MonsterConstants.JYNXBASEHEALTH, // Base Health
                "Copy HEALTH of healthiest ally",
                MonsterConstants.JYNXBASESPEED); // Base Speed
        this.setTrigger(Trigger.STARTOFBATTLE);
    }

    // Runs the ability of the monster - javadoc inherited from super
    public BattleEvent ability(boolean inPlayerTeam, Team allyTeam, Team enemyTeam) {
        // Copy HEALTH of healthiest ally

        int mostHealth = this.getCurrentHealth();
        for (Monster m : allyTeam.getAliveMonsters()) {
            if (m.getCurrentHealth() > mostHealth) {
                mostHealth = m.getCurrentHealth();
            }
        }

        this.setCurrentHealth(mostHealth);

        Team player = (inPlayerTeam) ? allyTeam : enemyTeam;
        Team opponent = (inPlayerTeam) ? enemyTeam : allyTeam;
        return new BattleEvent(player, opponent, this.getName() + "'s " + this.getTrigger().name()
                + " ability triggered. " + this.getName() + "'s new HP is " + this.getCurrentHealth() + ".");
    }

}