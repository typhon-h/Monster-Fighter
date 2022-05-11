package monsters;

import main.Trigger;
import main.BattleEvent;
import main.Team;

/**
 * Gil Monster
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
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
                "Boosts attack of ALLY behind by 50% of my ATTACK",
                MonsterConstants.GILBASESPEED); // Base Speed
        this.setTrigger(Trigger.ONFAINT);
    }

    @Override
    public BattleEvent ability(boolean inPlayerTeam, Team allyTeam, Team enemyTeam) {
        // Boosts attack of ALLY behind by 50% of my ATTACK

        int myPosition = allyTeam.getAliveMonsters().indexOf(this);
        Monster monsterToAdjust;
        if (myPosition < allyTeam.getAliveMonsters().size() - 1) {
            monsterToAdjust = allyTeam.getAliveMonsters().get(myPosition + 1);
            monsterToAdjust.setCurrentAttackDamage(
                    monsterToAdjust.getCurrentAttackDamage() + (this.getCurrentAttackDamage() / 2));

            Team player = (inPlayerTeam) ? allyTeam : enemyTeam;
            Team opponent = (inPlayerTeam) ? enemyTeam : allyTeam;
            return new BattleEvent(player, opponent,
                    this.getName() + "'s " + this.getTrigger().name() + " ability triggered. "
                            + monsterToAdjust.getName() + "'s attack increased to "
                            + monsterToAdjust.getCurrentAttackDamage());
        }

        return null; // No trigger
    }

}