package main;

import javax.swing.ImageIcon;

import gui.Previewable;

/**
 * Superclass for game objects.
 * An Entity is able to be possessed by a {@link main.Player player} and traded
 * in {@link shop.Shop shops}
 *
 * @author Harrison Tyson
 * @version 1.3, Apr 2022.
 */
public class Entity implements Cloneable, Previewable {
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
     * The {@link main.Rarity rarity} of the entity
     */
    private Rarity rarity;

    /**
     * GUI icon for the entity
     */
    private ImageIcon image;

    /**
     * Create new Entity
     *
     * @param newName        Name of the entity
     * @param newDescription Description of the entity
     * @param newRarity      Rarity of the entity
     */
    public Entity(String newName, String newDescription, Rarity newRarity) {
        setName(newName);
        setDescription(newDescription);
        setRarity(newRarity);
        setImage();
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
     * Increases sell price of the entity by a given amount
     *
     * @param amount Amount to increase sell price by
     */
    public void increaseSellPrice(int amount) {
        sellPrice += amount;
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

    /**
     * Creates a clone of the entity
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Sets image of the entity
     */
    public void setImage() {
        String imgUrl = "/img/";

        String entityClass = this.getClass().toString();
        imgUrl += entityClass.substring(entityClass.lastIndexOf('.') + 1);
        imgUrl += ".png";
        image = new ImageIcon(Entity.class.getResource(imgUrl));
    }

    /**
     * Gets image of the entity
     */
    public ImageIcon getImage() {
        return image;
    }
}