/**
 * Superclass for game objects.
 * An Entity is able to be possessed by a player and traded in shops
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class Entity {
    /**
     * The name of the entity
     */
    private String name;
    /**
     * The description of the entity
     */
    private String description;
    /**
     * The price to buy the entity
     */
    private int buyPrice;
    /**
     * The price to sell the entity
     */
    private int sellPrice;
    /**
     * The rarity of the entity
     */
    private Rarity rarity;

    /**
     * Constructor to be invoked from subclasses
     * Sets instance variables
     * 
     * @param newName        Name of the entity
     * @param newDescription Description of the entity
     * @param newBuyPrice    Price to buy the entity
     * @param newSellPrice   Price to sell the entity
     * @param newRarity      Rarity of the entity
     */
    Entity(String newName, String newDescription, int newBuyPrice, int newSellPrice, Rarity newRarity) {
        setName(newName);
        setDescription(newDescription);
        setBuyPrice(newBuyPrice);
        setSellPrice(newSellPrice);
        setRarity(newRarity);
    }

    /**
     * Sets the name of the entity
     * 
     * @param newName New name for the entity
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Gets the name of the entity
     * 
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description of the entity
     * 
     * @param newDescription New description for the entity
     */
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    /**
     * Gets the description of the entity
     * 
     * @return the description of the entity
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the buy price of the entity
     * 
     * @param newBuyPrice New buy price of the entity
     */
    public void setBuyPrice(int newBuyPrice) {
        buyPrice = newBuyPrice;
    }

    /**
     * Gets the buy price of the entity
     * 
     * @return the buy price of the entity
     */
    public int getBuyPrice() {
        return buyPrice;
    }

    /**
     * Sets the sell price of the entity
     * 
     * @param newSellPrice New sell price of the entity
     */
    public void setSellPrice(int newSellPrice) {
        sellPrice = newSellPrice;
    }

    /**
     * Gets the sell price of the entity
     * 
     * @return the sell price of the entity
     */
    public int getSellPrice() {
        return sellPrice;
    }

    /**
     * Sets the rarity of the entity
     * 
     * @param newRarity New rarity of the entity
     */
    public void setRarity(Rarity newRarity) {
        rarity = newRarity;
    }

    /**
     * Gets the rarity of the entity
     * 
     * @return the rarity of the entity
     */
    public Rarity getRarity() {
        return rarity;
    }
}