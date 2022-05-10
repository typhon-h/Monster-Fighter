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
        this.setTrigger(Trigger.AFTERATTACK); // TODO decide trigger
    }

    @Override
    public BattleEvent ability(Team allyTeam, Team enemyTeam) {
        // SWAP Attack and Health

        int tempAttack = this.getCurrentAttackDamage();
        int tempHealth = this.getCurrentHealth();
        this.setCurrentAttackDamage(tempHealth);
        this.setCurrentHealth(tempAttack);
        return new BattleEvent(allyTeam, enemyTeam,
                this.getName() + "'s " + this.getTrigger().name() + " ability triggered. ATK and HP have been swapped");
    }

}