package monsters;

import java.util.Queue;
import java.util.LinkedList;

import main.Trigger;
import main.Team;

/**
 * Gil Monster
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class GilMonster extends Monster {
    /**
     * Creates a new Monster with specified base stats
     */
    public GilMonster() {
        super(
                "Gil", // Name
                "Full of encouraging words but that's about it", // Description
                MonsterConstants.GILRARITY, // Rarity
                MonsterConstants.GILBASEATTACKDAMAGE, // Base AttackDamage
                MonsterConstants.GILBASEHEALTH, // Base Health
                "Boosts attack of ALLY in front by 50% of my ATTACK");
        this.setTrigger(Trigger.STARTOFBATTLE); // TODO decide trigger
    }

    @Override
    public Queue<Monster> ability(Team allyTeam, Team enemyTeam) {
        // Boosts attack of ALLY in front by 50% of my ATTACK
        Queue<Monster> triggeredAbilities = new LinkedList<Monster>();

        int myPosition = allyTeam.getAliveMonsters().indexOf(this);
        Monster monsterToAdjust;
        if (myPosition > 0) {
            monsterToAdjust = allyTeam.getAliveMonsters().get(myPosition - 1);
            monsterToAdjust.setCurrentAttackDamage(
                    monsterToAdjust.getCurrentAttackDamage() + (this.getCurrentAttackDamage() / 2));
        }

        return triggeredAbilities; // Empty
    }

}