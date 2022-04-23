package items;

import main.Rarity;
import main.Trigger;
import monsters.Monster;
import java.util.Random;

/**
 * An item that gives a {@link monsters.Monster} a random {@link main.Trigger}
 * for their Ability Trigger
 * 
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class RandomTrigger extends Item {
    private Random rng;

    /**
     * Constructor for RandomTrigger Item,
     * the {@link main.Rarity} of this item is always {@link main.Rarity#RARE}.
     *
     */
    public RandomTrigger() {
        super("Random Ability Trigger",
                ItemConstants.RANDOMTRIGGERDESC,
                Rarity.RARE);
        rng = new Random();
    }

    /**
     * Sets the {@link main.Trigger} for a given {@link monsters.Monster} to a
     * random trigger.
     *
     * @param monster The {@link monsters.Monster} to set the {@link main.Trigger}
     *                for.
     */
    public void use(Monster monster) {
        Trigger randomTrigger = monster.getTrigger();

        // Keep generating a new tigger that is not already owned by the monster and is
        // not NOABILITY
        do {
            randomTrigger = Trigger.values()[rng.nextInt(Trigger.numTriggers - 1)];
        } while (randomTrigger.equals(Trigger.NOABILITY) || randomTrigger.equals(monster.getTrigger()));

        monster.setTrigger(randomTrigger);
    }
}
