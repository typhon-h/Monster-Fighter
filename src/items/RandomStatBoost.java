package items;

import main.Rarity;
import monsters.Monster;
import java.util.Random;

/**
 * An item that increases a random stat of a Monster.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class RandomStatBoost extends Item {
    /**
     * Constructor for RandomStatBoost.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     * @param newRarity      rarity of the description.
     */
    public RandomStatBoost(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);
    }

    /**
     * Randomly boosts either the base health or base attack damage of a monster.
     * The amount boosted is based on the rarity of the item.
     *
     * @param monster The moster to boost the stat of.
     */
    public void use(Monster monster) {
        // Generates a new random object with a random seed.
        // TODO: Test if this actually does something different on seperate method calls.
        Random random = new Random();

        // TODO: Turn this into an enum?
        // change that random 3 into a constant inside generic monster?
        // Adjust probability that the heal/damage will increase
        if (random.nextInt(2) == 0) {
            monster.increaseBaseHealth(getStatBoostAmount());
        } else {
            monster.increaseBaseAttackDamage(getStatBoostAmount());
        }
    }
}
