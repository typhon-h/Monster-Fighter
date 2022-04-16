package items;

import main.Rarity;
import monsters.Monster;
import main.Trigger;
import exceptions.UnusableItemException;

public class SelectTrigger extends Item {

    private Trigger itemTrigger;

    /**
     * Constructor for SelectTrigger Item,
     * the {@link main.Rarity} of this item is always {@link main.Rarity#LEGENDARY}.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     */
    public SelectTrigger(String newName, String newDescription, Trigger newTrigger) {
        super(newName, newDescription, Rarity.LEGENDARY);

        itemTrigger = newTrigger;
    }

    public void use(Monster monster) throws UnusableItemException{
        if (monster.getTrigger() == itemTrigger){
            throw new UnusableItemException("Monster: " + monster.getName() + " already has trigger + " + monster.getTrigger());
        } else {
            monster.setTrigger(itemTrigger);
        }
    }

}
