package main;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import exceptions.InsufficientFundsException;
import exceptions.UnusableItemException;
import gui.Previewable;
import items.Item;
import monsters.Monster;

/**
 * A class for defining a player. Used to track the players information
 * including their inventory and score and to create opponents for the
 * player to face against.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
public class Player implements Previewable {

    /**
     * Constant defining the maximum size of the inventory for all players
     */
    final private static int INVENTORYLIMIT = 6;

    /**
     * Name of the player
     */
    private String name;

    /**
     * Array storing all the player's items
     *
     * @default An empty inventory
     */
    private ArrayList<Item> inventory = new ArrayList<Item>();

    /**
     * The {@link main.Team team} of {@link monsters.Monster monsters} which the
     * player has
     *
     */
    private Team team;

    /**
     * Amount of gold the player has
     *
     * @default 0
     */
    private int gold = 0;

    /**
     * Score of the player
     *
     * @default 0
     */
    private int score = 0;

    /**
     * Number of item points the player has used
     */
    private int itemPoints = 0;

    /**
     * GUI image for opponents
     */
    private ImageIcon image = new ImageIcon(Player.class.getResource("/img/Battle.png"));

    /**
     * Constructor to create a player with a name and {@link main.Team team}
     *
     * @param newTeam      The team to give to the player
     * @param startingGold Amount of gold to start off with
     */
    public Player(Team newTeam, int startingGold) {
        setName("Opponent");
        setTeam(newTeam);
        addGold(startingGold);
    }

    /**
     * Constructor to create a player with a name, {@link main.Team team}, and
     * starting gold
     *
     * @param newName      Name of the player
     * @param newTeam      The team to give to the player
     * @param startingGold Amount of gold to start off with
     */
    public Player(String newName, Team newTeam, int startingGold) {
        setName(newName);
        setTeam(newTeam);
        addGold(startingGold);
    }

    /**
     * Set the name of the player
     *
     * @param newName The name of the player
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Get the name of the player
     *
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Get the amount of gold the player owns
     *
     * @return The amount of gold the player has
     */
    public int getGold() {
        return gold;
    }

    /**
     * Increases the amount of gold a player has by a give amount
     *
     * @param goldAmount The amount of gold to give to the player
     * @throws IllegalArgumentException if the amount is negative
     */
    public void addGold(int goldAmount) throws IllegalArgumentException {
        if (goldAmount < 0) {
            throw new IllegalArgumentException("Argument cannot be negative");
        } else {
            gold += goldAmount;
        }
    }

    /**
     * Decreases the amount of gold the player has
     *
     * @param goldAmount The amount of gold to remove from the player
     * @throws InsufficientFundsException not enough gold to remove amount
     * @throws IllegalArgumentException   if the amount is negative
     */
    public void removeGold(int goldAmount) throws IllegalArgumentException, InsufficientFundsException {
        if (goldAmount < 0) {
            throw new IllegalArgumentException("Argument cannot be negative");
        } else if (gold - goldAmount < 0) {
            throw new InsufficientFundsException((goldAmount - gold) + " more gold is required");
        } else {
            gold -= goldAmount;
        }
    }

    /**
     * Gets the players {@link main.Team team}
     *
     * @return The player's {@link main.Team team}
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets the player's {@link main.Team team}
     *
     * @param newTeam The {@link main.Team team} to give to the player
     */
    public void setTeam(Team newTeam) {
        team = newTeam;
    }

    /**
     * Gets the player's inventory
     *
     * @return An array of the {@link items.Item items} which the player has
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Gets {@link items.Item item} from {@link main.Player player} inventory
     *
     * @param index index of {@link items.Item item} in inventory
     * @throws IndexOutOfBoundsException index does not match an inventory
     *                                   {@link items.Item item}
     *
     * @return {@link items.Item item} at the index
     */
    public Item getItem(int index) throws IndexOutOfBoundsException {
        return inventory.get(index);
    }

    /**
     * Adds an {@link items.Item item} to the player's inventory
     * If the inventory is full, returns false otherwise true
     *
     * @param newItem The {@link items.Item item} to give to the player
     * @return True if the {@link items.Item item} is successfully added to the
     *         inventory
     */
    public boolean addItem(Item newItem) {
        if (this.getNumFreeSlots() > 0) {
            inventory.add(newItem);
            return true;
        }
        return false;
    }

    /**
     * Removes the given {@link items.Item item} from the player's inventory
     * If the item is not in the inventory, then false is returned
     *
     * @param removeItem {@link items.Item item} to remove from the player's
     *                   inventory
     * @return true if the {@link items.Item item} is successfully removed from the
     *         players inventory
     *         otherwise false
     */
    public boolean removeItem(Item removeItem) {
        return inventory.remove(removeItem);
    }

    /**
     * Uses {@link items.Item item} from player inventory on {@link monsters.Monster
     * monster}
     *
     * @param itemToUse       {@link items.Item item} to be used
     * @param monsterToEffect {@link monsters.Monster monster} for the
     *                        {@link items.Item item} to be used on
     * @return string describing the result of the action
     */
    public String useItem(Item itemToUse, Monster monsterToEffect) {
        try {
            if (this.getInventory().contains(itemToUse)) {
                String message = itemToUse.use(monsterToEffect);
                this.incrementItemPoints(itemToUse.getStatBoostAmount());
                this.removeItem(itemToUse);
                return message;
            }
            return "Error: player does not possess this item";

        } catch (UnusableItemException e) {
            return e.getMessage();
        }
    }

    /**
     * Gets the number of free slots available in the inventory
     *
     * @return The number of free slots left in the inventory
     */
    public int getNumFreeSlots() {
        return INVENTORYLIMIT - inventory.size();
    }

    /**
     * Gets the players score in the game
     *
     * @return The player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Increases the player's score by a given amount
     *
     * @param incrementAmount The amount to increment the score by
     * @throws IllegalArgumentException if the amount is negative
     */
    public void incrementScore(int incrementAmount) throws IllegalArgumentException {
        if (incrementAmount < 0) {
            throw new IllegalArgumentException("Argument cannot be negative");
        } else {
            score += incrementAmount;
        }

    }

    /**
     * Increments the amount of item points the player has used
     * by a given amount
     *
     * @param amount The amount to increment item points by
     */
    public void incrementItemPoints(int amount) {
        itemPoints += amount;
    }

    /**
     * Gets the amount of item points the player has used
     *
     * @return The amount of item points the player has used
     */
    public int getItemPoints() {
        return itemPoints;
    }

    /**
     * Returns the details of player in a string format
     *
     * @return String format detailing the player
     */
    public String toCLIString() {
        String outputString;

        outputString = this.getName() +
                "\nRewards: " + this.getGold() +
                "G | " + this.getScore() +
                " Points\n-----------------------------\n";

        outputString += this.getTeam().toString();

        return outputString;
    }

    public String toString() {
        String outputString;

        outputString = this.getName() +
                "\nRewards: " + this.getGold() +
                "G | " + this.getScore() +
                " Points\n\nMonsters:\n";

        outputString += this.getTeam().toString();

        return outputString;
    }

    /**
     * Get image for GUI opponents
     */
    public ImageIcon getImage() {
        ArrayList<Monster> monsters = this.getTeam().getMonsters();
        if (monsters == null || monsters.size() == 0) {
            return image;
        } else {
            return monsters.get(0).getImage();
        }
    }
}
