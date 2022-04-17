package main;

/**
 * Superclass for game objects.
 * An Entity is able to be possessed by a player and traded in shops
 *
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
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
    public Entity(String newName, String newDescription, int newBuyPrice, int newSellPrice, Rarity newRarity) {
        setName(newName);
        setDescription(newDescription);
        setBuyPrice(newBuyPrice);
        setSellPrice(newSellPrice);
        setRarity(newRarity); // Check if this calls the subclass overriden implementation
    }

    /**
     * Constructor to be invoked from subclasses.
     * Sets name and description instance variables
     *
     * @param newName        The name of the entity
     * @param newDescription Description of the entity
     * @param newRarity      The rarity of the entity
     */
    public Entity(String newName, String newDescription, Rarity newRarity) {
        setName(newName);
        setDescription(newDescription);
        setRarity(newRarity);
    }

    /**
     * Sets the name of the entity
     *
     * @param name New name for the entity
     */
    public void setName(String name) {
        this.name = name;
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
     * @param description New description for the entity
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @param buyPrice New buy price of the entity
     */
    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
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
     * @param sellPrice New sell price of the entity
     */
    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
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
     * @param rarity New rarity of the entity
     */
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
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