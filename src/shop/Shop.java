package shop;

import java.util.ArrayList;

import main.Entity;
import main.Player;

/**
 * Superclass for shop objects.
 * A shop controls the transfer of entities to and from a player
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public abstract class Shop {
    /**
     * ArrayList of entities available to the shop.
     */
    protected ArrayList<Entity> content = new ArrayList<Entity>();

    /**
     * Player that is trading with the shop
     */
    protected Player player;

    /**
     * Constructor for shop
     * 
     * @param player player that is trading with the shop
     */
    public Shop(Player player) {
        this.player = player;
    }

    /**
     * Gets ArrayList of current shop contents
     * 
     * @return the current content of the shop
     */
    public ArrayList<Entity> getContent() {
        return this.content;
    }

    /**
     * Sets the contents of the shop
     */
    public abstract void setContent();
}
