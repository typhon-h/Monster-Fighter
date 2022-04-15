/**
 * Subclass of Entity and the superclass of specific items.
 * Used to define common traits between all the specific items.
 * 
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */

public abstract class Item extends Entity{
	
	
    /**
     * Constructor to call the constructor of the superclass Entity
     * Sets instance variables
     * 
     * @param newName        Name of the entity
     * @param newDescription Description of the entity
     * @param newBuyPrice    Price to buy the entity
     * @param newSellPrice   Price to sell the entity
     * @param newRarity      Rarity of the entity
     */
	public Item(String newName, String newDescription, int newBuyPrice, int newSellPrice, Rarity newRarity) {
		super(newName, newDescription, newBuyPrice, newSellPrice, newRarity);
	}
}
