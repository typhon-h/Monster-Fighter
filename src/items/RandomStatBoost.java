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
     */
    public void use(Monster monster) {
        // Adjust probability that each stat will increase
        switch (rng.nextInt(MonsterConstants.NUMBEROFSTATS)) {
            case 0:
                monster.increaseBaseHealth(getStatBoostAmount());
                break;
            case 1:
                monster.increaseBaseAttackDamage(getStatBoostAmount());
                break;
            case 2:
                monster.increaseSpeed(getStatBoostAmount());
        }
    }
}
