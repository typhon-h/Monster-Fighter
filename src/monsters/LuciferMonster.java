package monsters;

import main.Trigger;
import main.BattleEvent;
import main.Team;

/**
 * Lucifer Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
 */
public class LuciferMonster extends Monster {

    /**
     * Creates a new Monster with specified base stats
     */
    public LuciferMonster() {
        super(
                "Lucifer", // Name
                "Flip a coin, horns or tails, you never know", // Description
                MonsterConstants.LUCIFERRARITY, // Rarity
                MonsterConstants.LUCIFERBASEATTACKDAMAGE, // Base AttackDamage
                MonsterConstants.LUCIFERBASEHEALTH, // Base Health
                "SWAP Attack and Health",
                MonsterConstants.LUCIFERBASESPEED); // Base Speed
        this.setTrigger(Trigger.ONHURT); // TODO decide trigger
    }

    @Override
    public BattleEvent ability(Team allyTeam, Team enemyTeam) {
        // SWAP Atack and Health

        int tempAttack = this.getCurrentAttackDamage();
        int tempHealth = this.getCurrentHealth();
        this.setCurrentAttackDamage(tempHealth);
        this.setCurrentHealth(tempAttack);

        return new BattleEvent((Team) allyTeam.clone(), (Team) enemyTeam.clone(),
                this.getName() + "'s " + this.getTrigger().name() + " ability triggered. ATK and HP have been swapped");
    }

}