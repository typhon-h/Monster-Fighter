package items;

import main.Rarity;
import main.Trigger;
import monsters.Monster;
import java.util.Random;

/**
 * An item that gives a {@link monsters.Monster} a random {@link main.Trigger}
 * for their Ability Trigger
 */
public class RandomTrigger extends Item{
    Random rng;
    /**
     * Constructor for RandomTrigger Item,
     * the {@link main.Rarity} of this item is always {@link main.Rarity#RARE}.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     */
    public RandomTrigger(String newName, String newDescription) {
        super(newName, newDescription, Rarity.RARE);
        rng = new Random();
    }

    /**
     * Sets the {@link main.Trigger} for a given {@link monsters.Monster} to a random trigger.
     *
     * @param monster The {@link monsters.Monster} to set the {@link main.Trigger} for.
     */
    public void use(Monster monster) {
        // Get get a random {@link main.Trigger} that is not NOTRIGGER.
        Trigger randomTrigger = Trigger.values()[rng.nextInt(Trigger.numTriggers - 1)];
        monster.setTrigger(randomTrigger);
    }
}
