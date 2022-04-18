package items;

import main.Rarity;
import monsters.Monster;


/**
 * An item that increases the base health of a {@link monsters.Monster} by
 * a set amount based on the {@link main.Rarity} of the item.
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
     * @param newRarity      {@link main.Rarity} of the item.
     */
    public HealthBoost(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);
    }

    /**
     * Increases the base health of a given {@link monsters.Monster} by an amount
     * based on the {@link main.Rarity} of the item.
     *
     * @param monster The {@link monsters.Monster} to use the item on.
     */
    public void use(Monster monster) {
        monster.increaseBaseHealth(getStatBoostAmount());
    }
}