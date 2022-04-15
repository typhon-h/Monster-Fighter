package items;

import main.Entity;
import main.Rarity;
import monsters.Monster;

/**
 * Generic Item.
 * Subclass of Entity and the superclass of specific items.
 * Used to define common traits between all the specific items.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public abstract class Item extends Entity {

    /**
     * Constructor to call the constructor of the superclass, Entity.
     * Sets all instance variables
     *
     * @param newName        Name of the item
     * @param newDescription Description of the item
     * @param newBuyPrice    Price to buy the item
     * @param newSellPrice   Price to sell the item
     * @param newRarity      Rarity of the item
     */
    public Item(String newName,
            String newDescription,
            int newBuyPrice,
            int newSellPrice,
            Rarity newRarity) {
        super(newName, newDescription, newBuyPrice, newSellPrice, newRarity);
    }

    /**
     * Constructor to call the constructor of the superclass, Entity.
     * Sets instance variables
     *
     * @param newName        The name of the player
     * @param newDescription Description of the item
     * @param newRarity      Rarity of the item
     */
    public Item(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);
    }

    /**
     * Applies the unique effect of the item to a given monster
     *
     * @param monster The monster to apply the effect to
     */
    public abstract void use(Monster monster);
}
