package items;

import main.Rarity;
import monsters.Monster;

// TODO: Add tests for this

/**
 * An item that increases the speed of a {@link monsters.Monster} by
 * a set amount based on the {@link main.Rarity} of the item.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class SpeedBoost extends Item {
    /**
     * Constructor for SpeedBoost.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     * @param newRarity      {@link main.Rarity} of the item.
     */
    public SpeedBoost(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);
    }

    /**
     * Increases the speed of a given {@link monsters.Monster} by an amount
     * based on the {@link main.Rarity} of the item.
     *
     * @param monster The {@link monsters.Monster} to use the item on.
     */
    public void use(Monster monster) {
        monster.increaseSpeed(getStatBoostAmount());
    }
}
