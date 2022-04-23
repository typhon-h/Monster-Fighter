package main;

import java.util.ArrayList;

import exceptions.TeamSizeException;
import items.Item;
import monsters.Monster;

/**
 * Subclass of shop
 * Used for selling player items
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class SellShop extends Shop {

    /**
     * Constructor for SellShop
     * Sets content and trading player
     * 
     * @param player player to trade with
     */
    public SellShop(Player player) {
        super(player);
        setContent();
    }

    /**
     * Sets the content of the shop to player inventory and monsters
     */
    public void setContent() {
        this.content = new ArrayList<Entity>();
        this.content.addAll(this.player.getInventory());
        this.content.addAll(this.player.getTeam().getMonsters());
    }

    /**
     * Sells player item to shop in return for gold
     * 
     * @param item item to be sold
     * @return message detailing result of sale
     */
    public String sell(Item item) {
        if (this.content.contains(item)) { // Check item in contents
            this.player.addGold(item.getSellPrice());
            this.player.removeItem(item);
            return item.getName() + " was sold for " + item.getSellPrice();
        }

        return "Sell Error: item was not found";
    }

    /**
     * Sells player monster to shop in return for gold
     * 
     * @param monster monster to be sold
     * @return message detailing result of sale
     */
    public String sell(Monster monster) {
        if (this.content.contains(monster)) {
            try {
                this.player.getTeam().removeMonster(monster);
                this.player.addGold(monster.getSellPrice());
                this.setContent();
                return monster.getName() + " was sold for " + monster.getSellPrice();
            } catch (TeamSizeException e) { // removing monster goes under min team size
                return e.getMessage();
            }
        }

        return "Sell Error: monster was not found";
    }
}
