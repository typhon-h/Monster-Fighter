package items;

import main.Rarity;
import monsters.Monster;
import monsters.Trigger;
import exceptions.UnusableItemException;

/**
 * An item that gives a {@link monsters.Monster} a select {@link monsters.Trigger}
 * for their {@link monsters.Monster#ability ability}
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
     * @param newTrigger {@link monsters.Trigger} to give the item.
     */
    public SelectTrigger(Trigger newTrigger) {
        super(newTrigger.name() + " Ability Trigger",
                ItemConstants.SELECTTRIGGERDESC + newTrigger.name(),
                Rarity.LEGENDARY);

        setTrigger(newTrigger);
    }

    /**
     * Sets the {@link monsters.Trigger} of the item to a given trigger.
     *
     * @param newTrigger The {@link monsters.Trigger} to set the item to.
     */
    public void setTrigger(Trigger newTrigger) {
        itemTrigger = newTrigger;
    }

    /**
     * Gets the {@link monsters.Trigger} of the SelectTrigger {@link items.Item}.
     *
     * @return {@link monsters.Trigger} of the SelectTrigger {@link items.Item}.
     */
    public Trigger getTrigger() {
        return itemTrigger;
    }

    /**
     * Applies the {@link monsters.Trigger} of the {@link items.Item} to a
     * {@link monsters.Monster}.
     *
     * @param monster The {@link monsters.Monster} to apply the {@link monsters.Trigger}
     *                to
     * @throws UnusableItemException The {@link monsters.Monster} which the
     *                               {@link items.Item}
     *                               is used on already has the {@link monsters.Trigger}
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
