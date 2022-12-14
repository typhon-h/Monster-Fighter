package items;

import main.GameEnvironment;
import main.Rarity;
import monsters.Monster;
import monsters.MonsterConstants;

/**
 * An item that increases a random stat of a {@link monsters.Monster}.
 *
 * @author Jackie Jone
 * @version 1.2, Apr 2022
 */
public class RandomStatBoost extends Item {
    /**
     * Constructor for RandomStatBoost item.
     *
     * @param newRarity {@link main.Rarity} of the item.
     */
    public RandomStatBoost(Rarity newRarity) {
        super("Random Stat Boost",
                ItemConstants.RANDOMBOOSTDESC + Item.getStatBoostAmount(newRarity),
                newRarity);
    }

    /**
     * Randomly boosts either the base health or base attack damage of a monster.
     * The amount boosted is based on the {@link main.Rarity} of the
     * {@link items.Item}.
     *
     * @param monster The {@link monsters.Monster} to boost the stat of.
     * @return string describing item effect
     */
    public String use(Monster monster) {
        String statChanged;
        switch (GameEnvironment.rng.nextInt(MonsterConstants.NUMBEROFSTATS)) {
            case 0:
                statChanged = "HEALTH";
                monster.increaseBaseHealth(getStatBoostAmount());
                monster.setCurrentHealth(monster.getBaseHealth());
                monster.setStatus(true);
                break;
            case 1:
                statChanged = "ATTACK";
                monster.increaseBaseAttackDamage(getStatBoostAmount());
                break;
            case 2:
                statChanged = "SPEED";
                monster.increaseSpeed(getStatBoostAmount());
                break;
            default:
                statChanged = "NULL";
        }
        monster.increaseSellPrice(this.getSellPrice());

        return String.format(ItemConstants.RANDOMBOOSTFEEDBACK, monster.getName(), statChanged, getStatBoostAmount());
    }
}
