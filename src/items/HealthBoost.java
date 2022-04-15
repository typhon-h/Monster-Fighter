package items;

import main.Rarity;
import monsters.Monster;


/**
 * An item that increases the base health of a monster by
 * a set amount based on the rarity of the item.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class HealthBoost extends Item {
    /**
     * Constructor for HealthBoost.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     * @param newRarity      rarity of the description.
     */
    public HealthBoost(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);
    }

    /**
     * Increases the base health of a given monster by an amount
     * based on the rarity of the item.
     *
     * @param monster The monster to use the item on.
     */
    public void use(Monster monster) {
        monster.increaseBaseHealth(getStatBoostAmount());
    }
}
