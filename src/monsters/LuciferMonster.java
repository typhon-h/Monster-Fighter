package monsters;

import main.Trigger;
import main.Team;

/**
 * Lucifer Monster
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
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
                "SWAP Atack and Health");
        this.setTrigger(Trigger.ONHURT); // TODO decide trigger
    }

    @Override
    public Monster ability(Team allyTeam, Team enemyTeam) {
        // SWAP Atack and Health

        int tempAttack = this.getCurrentAttackDamage();
        int tempHealth = this.getCurrentHealth();
        this.setCurrentAttackDamage(tempHealth);
        this.setCurrentHealth(tempAttack);

        return null; // Empty
    }

}