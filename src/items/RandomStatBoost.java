package items;

import main.Rarity;
import monsters.Monster;
import java.util.Random;

/**
 * An item that increases a random stat of a {@link monsters.Monster}.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class RandomStatBoost extends Item {
    Random rng;

    /**
     * Constructor for RandomStatBoost item.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     * @param newRarity      {@link main.Rarity} of the item.
     */
    public RandomStatBoost(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);
        rng = new Random(); // Seed it? or have a global rng?
    }

    /**
     * Randomly boosts either the base health or base attack damage of a monster.
     * The amount boosted is based on the {@link main.Rarity} of the {@link items.Item}.
     *
     * @param monster The {@link monsters.Monster} to boost the stat of.
     */
    public void use(Monster monster) {
        // TODO: Turn this into an enum?
        // change that random 3 into a constant inside generic monster?
        // Adjust probability that the heal/damage will increase
        if (rng.nextInt(2) == 0) {
            monster.increaseBaseHealth(getStatBoostAmount());
        } else {
            monster.increaseBaseAttackDamage(getStatBoostAmount());
        }
    }
}
