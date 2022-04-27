package items;

import main.Rarity;
import monsters.Monster;
import monsters.MonsterConstants;

import java.util.Random;

/**
 * An item that increases a random stat of a {@link monsters.Monster}.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
public class RandomStatBoost extends Item {
    Random rng;

    /**
     * Constructor for RandomStatBoost item.
     *
     * @param newRarity {@link main.Rarity} of the item.
     */
    public RandomStatBoost(Rarity newRarity) {
        super("Random Stat Boost",
                ItemConstants.RANDOMBOOSTDESC + Item.getStatBoostAmount(newRarity),
                newRarity);
        rng = new Random(); // Seed it? or have a global rng?
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
        int newStatValue;
        // Adjust probability that each stat will increase
        switch (rng.nextInt(MonsterConstants.NUMBEROFSTATS)) {
            case 0:
                statChanged = "HEALTH";
                monster.increaseBaseHealth(getStatBoostAmount());
                newStatValue = monster.getBaseHealth();
                break;
            case 1:
                statChanged = "ATTACK";
                monster.increaseBaseAttackDamage(getStatBoostAmount());
                newStatValue = monster.getBaseAttackDamage();
                break;
            case 2:
                statChanged = "SPEED";
                monster.increaseSpeed(getStatBoostAmount());
                newStatValue = monster.getSpeed();
                break;
            default:
                statChanged = "NULL";
                newStatValue = -1;
        }

        return String.format(ItemConstants.RANDOMBOOSTFEEDBACK, monster.getName(), statChanged, getStatBoostAmount(),
                newStatValue);
    }
}
