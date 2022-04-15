package main;

import java.util.ArrayList;
import exceptions.InsufficientFundsException;
import items.Item;

/**
 * A class for defining a player. Used to track the players information
 * including
 * their inventory and score and to create opponents for the player to face
 * against.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public class Player {

	/**
	 * Constant defining the maximum size of the inventory for all players
	 */
	final private static int inventoryLimit = 6;

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
	 * The team of monsters which the player has
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
	 * Constructor to create a player with a team
	 * 
	 * @param newTeam The team to give to the player
	 */
	public Player(Team newTeam) {
		setTeam(newTeam);
	}

	/**
	 * Constructor to create a player with a name and team
	 *
	 * @param newName The name of the player
	 * @param newTeam The team to give to the player
	 */
	public Player(String newName, Team newTeam) {
		setName(newName);
		setTeam(newTeam);
	}

	/**
	 * Constructor to create a player with a name, team, and starting gold
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
	 */
	public void addGold(int goldAmount) {
		gold += goldAmount;
	}

	/**
	 * Decreases the amount of gold the player has
	 *
	 * @param goldAmount The amount of gold to remove from the player
	 * @throws InsufficientFundsException not enough gold to remove amount
	 */
	public void removeGold(int goldAmount) throws InsufficientFundsException {
		if (gold - goldAmount < 0) {
			throw new InsufficientFundsException((goldAmount - gold) + " more gold is required");
		} else {
			gold -= goldAmount;
		}
	}

	/**
	 * Gets the players team
	 *
	 * @return Team object which contains the player's team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Sets the player's team to a team object
	 *
	 * @param newTeam The team to give to the player
	 */
	public void setTeam(Team newTeam) {
		team = newTeam;
	}

	/**
	 * Gets the player's inventory
	 *
	 * @return An array of the items which the player has
	 */
	public ArrayList<Item> getInventory() {
		return inventory;
	}

	/**
	 * Adds an item to the player's inventory
	 * If the inventory is full, returns false otherwise true
	 *
	 * @param newItem The item to give to the player
	 * @return True if the item is successfully added to the inventory
	 */
	public boolean addItem(Item newItem) {
		if (inventory.size() < inventoryLimit) {
			inventory.add(newItem);
			return true;
		}
		return false;
	}

	/**
	 * Removes the given item from the player's inventory
	 * If the item is not in the inventory, then false is returned
	 *
	 * @param removeItem item to remove from the player's inventory
	 * @return true if the item is successfully removed from the players inventory
	 *         otherwise false
	 */
	public boolean removeItem(Item removeItem) {
		return inventory.remove(removeItem);
	}

	/**
	 * Gets the number of free slots available in the inventory
	 *
	 * @return The number of free slots left in the inventory
	 */
	public int getNumFreeSlots() {
		return inventoryLimit - inventory.size();
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
	 */
	public void incrementScore(int incrementAmount) {
		score += incrementAmount;
	}

}
