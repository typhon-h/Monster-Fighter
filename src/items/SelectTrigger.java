package items;

import main.Rarity;
import monsters.Monster;
import main.Trigger;
import exceptions.UnusableItemException;

/**
 * An item that gives a {@link monsters.Monster} a select {@link main.Trigger}
 * for their Ability Trigger
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
public class SelectTrigger extends Item {

    private Trigger itemTrigger;

    /**
     * Constructor for SelectTrigger Item,
     * the {@link main.Rarity} of this item is always {@link main.Rarity#LEGENDARY}.
     *
     * @param newTrigger {@link main.Trigger} to give the item.
     */
    public SelectTrigger(Trigger newTrigger) {
        super(newTrigger.name() + " Ability Trigger",
                ItemConstants.SELECTTRIGGERDESC + newTrigger.name(),
                Rarity.LEGENDARY);

        setTrigger(newTrigger);
    }

    /**
     * Sets the {@link main.Trigger} of the item to a given trigger.
     *
     * @param newTrigger The {@link main.Trigger} to set the item to.
     * @return string describing item effect
     */
    public void setTrigger(Trigger newTrigger) {
        itemTrigger = newTrigger;
    }

    /**
     * Gets the {@link main.Trigger} of the SelectTrigger {@link items.Item}.
     *
     * @return {@link main.Trigger} of the SelectTrigger {@link items.Item}.
     */
    public Trigger getTrigger() {
        return itemTrigger;
    }

    /**
     * Gives the applies the {@link main.Trigger} of the {@link items.Item} to a
     * {@link monsters.Monster}.
     *
     * @param monster The {@link monsters.Monster} to apply the {@link main.Trigger}
     *                to
     * @throws UnusableItemException The {@link monsters.Monster} which the
     *                               {@link items.Item}
     *                               is used on already has the {@link main.Trigger}
     *                               of the {@link items.Item}.
     */
    public String use(Monster monster) throws UnusableItemException {
        if (monster.getTrigger() == itemTrigger) {
            throw new UnusableItemException(
                    "Monster: " + monster.getName() + " already has trigger + " + monster.getTrigger());
        } else {
            monster.setTrigger(itemTrigger);
            monster.increaseSellPrice(this.getSellPrice());
        }

        return String.format(ItemConstants.SELECTTRIGGERFEEDBACK, monster.getName(), monster.getTrigger().name());
    }

}
