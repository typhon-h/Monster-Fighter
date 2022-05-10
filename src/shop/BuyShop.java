package shop;

import java.util.ArrayList;

import exceptions.*;
import items.*;
import main.*;
import monsters.*;

/**
 * Subclass of shop
 * Used for buying player items/monsters
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class BuyShop extends Shop {
    /**
     * Items the shop can sell
     */
    private ArrayList<Entity> itemStock;
    /**
     * Monsters the shop can sell
     */
    private ArrayList<Entity> monsterStock;

    /**
     * Constructor for BuyShop
     * Assigns trading player, assigns total stock, sets current stock
     *
     * @param player player to trade with
     */
    public BuyShop(Player player) {
        super(player);
        setContent();
    }

    /**
     * Sets stock with all possible entities that can be sold
     */
    private void generateStock() {
        itemStock = new ArrayList<Entity>();
        monsterStock = new ArrayList<Entity>();
        itemStock.add(new RandomTrigger());

        for (Rarity rarity : Rarity.values()) { // Items of all rarities
            itemStock.add(new AttackBoost(rarity));
            itemStock.add(new HealthBoost(rarity));
            itemStock.add(new SpeedBoost(rarity));
            itemStock.add(new RandomStatBoost(rarity));
        }

        for (Trigger trigger : Trigger.values()) { //
            itemStock.add(new SelectTrigger(trigger));
        }

        for (Monster monster : GameEnvironment.generateMonsters()) {
            monsterStock.add(monster);
        }
    }

    /**
     * Filters ArrayList of entities by a rarity
     *
     * @param stock  entities to be filtered
     * @param rarity rarity to filter
     * @return filtered list of entities
     */
    private ArrayList<Entity> filterStock(ArrayList<Entity> stock, Rarity rarity) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity object : stock) {
            if (object.getRarity() == rarity) {
                result.add(object);
            }
        }
        return result;
    }

    /**
     * Randomly populates the shop with an item/monster of each rarity
     */
    public void setContent() {
        generateStock();
        this.content.clear();
        ArrayList<Entity> filteredItems, filteredMonsters;
        for (Rarity rarity : Rarity.values()) {
            filteredItems = filterStock(itemStock, rarity);
            filteredMonsters = filterStock(monsterStock, rarity);
            if (filteredItems.size() > 0) {
                this.content.add(filteredItems.get(GameEnvironment.rng.nextInt(filteredItems.size())));
            }
            if (filteredMonsters.size() > 0) {
                this.content.add(filteredMonsters.get(GameEnvironment.rng.nextInt(filteredMonsters.size())));
            }

        }
    }

    /**
     * Buys an item from the shop using gold and adds it to inventory
     *
     * @param item item to be bought
     * @return message detailing result of purchase
     */
    public String buy(Item item) {
        if (this.content.contains(item)) { // Check in contents
            try {
                this.player.removeGold(item.getBuyPrice());
                if (!this.player.addItem(item)) { // Try to add item
                    return "Sorry, you do not have enough space in your inventory";
                }
                this.content.remove(item);
                return item.getName() + " bought successfully";
            } catch (InsufficientFundsException e) { // Not enough gold
                this.player.removeItem(item);
                return e.getMessage();
            }
        }
        return "Buy Error: item not found";
    }

    /**
     * Buys a monster from the shop using gold and adds it to team
     *
     * @param monster monster to be bought
     * @return message detailing result of purchase
     */
    public String buy(Monster monster) {
        if (this.content.contains(monster)) { // Check in contents
            try {
                this.player.removeGold(monster.getBuyPrice());
                this.player.getTeam().addMonster(monster);
                this.content.remove(monster);
                return monster.getName() + " bought successfully";
            } catch (InsufficientFundsException e) { // Not enough gold
                return e.getMessage();
            } catch (TeamSizeException | DuplicateMonsterException e) { // Too many members in team or
                this.player.addGold(monster.getBuyPrice()); // monster already in team
                return e.getMessage();
            }
        }
        return "Buy Error: monster not found";
    }

}
