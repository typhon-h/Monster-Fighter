package items;

import java.util.ArrayList;
import java.util.Arrays;

import main.*;
import monsters.Monster;

/**
 * An item that gives a {@link monsters.Monster} a random {@link main.Trigger}
 * for their {@link monsters.Monster#ability ability}
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
public class RandomTrigger extends Item {

    /**
     * Constructor for RandomTrigger Item,
     * the {@link main.Rarity} of this item is always {@link main.Rarity#RARE}.
     *
     */
    public RandomTrigger() {
        super("Random Ability Trigger",
                ItemConstants.RANDOMTRIGGERDESC,
                Rarity.RARE);
    }

    /**
     * Sets the {@link main.Trigger} for a given {@link monsters.Monster} to a
     * random trigger.
     *
     * @param monster The {@link monsters.Monster} to set the {@link main.Trigger}
     *                for.
     * @return string describing item effect
     */
    public String use(Monster monster) {
        Trigger randomTrigger = monster.getTrigger();

        ArrayList<Trigger> possibleTriggers = new ArrayList<Trigger>(Arrays.asList(Trigger.values()));
        possibleTriggers.remove(Trigger.NOABILITY);
        possibleTriggers.remove(monster.getTrigger());

        randomTrigger = possibleTriggers.get(GameEnvironment.rng.nextInt(possibleTriggers.size()));

        monster.setTrigger(randomTrigger);
        monster.increaseSellPrice(this.getSellPrice());

        // TODO: THIS IS BROKEN!!!!
        // TODO: test random trigger feedback
        // ****************************************
        // |  %s's ability trigger was changed    |
        // |                to Gil                |
        // ****************************************
        return String.format(ItemConstants.RANDOMTRIGGERFEEDBACK, monster.getName(), monster.getTrigger().name());
    }
}
