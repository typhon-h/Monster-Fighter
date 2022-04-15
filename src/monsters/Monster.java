package monsters;

import java.util.Queue;
import java.util.LinkedList;

import main.Entity;
import main.Rarity;
import main.Team;
import main.Trigger;

/**
 * Generic Monster
 * 
 * @author Harrison Tyson
 * @version 1.1, Apr 2022.
 */
public abstract class Monster extends Entity {
    /**
     * Current health of the monter
     */
    private int currentHealth;
    /**
     * Base health of the monster
     */
    private int baseHealth;
    /**
     * Current attack damage of the monster
     */
    private int currentAttackDamage;
    /**
     * Base attack damage of the monster
     */
    private int baseAttackDamage;
    /**
     * Number of times the monster has fainted.
     * 
     * @default 0
     */
    private int faintCount = 0;
    /**
     * Event which triggers the Monster's ability
     * 
     * @default Trigger.NOABILITY
     */
    private Trigger abilityTrigger = Trigger.NOABILITY;
    /**
     * Description of what the ability does
     */
    private String abilityDescription;
    /**
     * Status of the monster.
     * true when alive and false when fainted
     * 
     * @default true
     */
    private boolean status = true;

    /**
     * Constructor for the Monster class
     * 
     * @param name               Name of the monster
     * @param description        Description of the monster
     * @param buyPrice           Price to buy the monster from shop
     * @param sellPrice          Price to sell the monster to shop
     * @param rarity             Rarity of the monster
     * @param baseHealth         Base health of the monster
     * @param baseAttackDamage   Base attack damage of the monster
     * @param abilityDescription Description of the monster's ability
     */
    public Monster(String name, String description, int buyPrice, int sellPrice,
            Rarity rarity, int baseAttackDamage, int baseHealth,
            String abilityDescription) {
        super(name, description, buyPrice, sellPrice, rarity); // Entity
        this.baseHealth = this.currentHealth = baseHealth;
        this.baseAttackDamage = this.currentAttackDamage = baseAttackDamage;
        setAbilityDescription(abilityDescription);
    }

    /**
     * Unique ability of the monster that has special effects in battle
     * 
     * @param allyTeam  Friendly team of the monster
     * @param enemyTeam Enemy team of the monster
     * @return a Queue containing all monsters whose abilities triggered
     */
    public abstract Queue<Monster> ability(Team allyTeam, Team enemyTeam);

    /**
     * Deals damage to the monster and triggers relevant events
     * 
     * @param damage amount of damage recieved
     * @return a Queue containing all monsters whose abilities triggered
     */
    public Queue<Monster> takeDamage(int damage) {
        Queue<Monster> triggeredAbilities = new LinkedList<Monster>();
        this.currentHealth -= damage;

        if (getTrigger() == Trigger.ONHURT) { // ONHURT Event
            triggeredAbilities.add(this);
        }

        if (currentHealth <= 0) { // Check for faint
            currentHealth = 0;
            setStatus(false);
            incrementFaintCount();
            if (getTrigger() == Trigger.ONFAINT) { // ONFAINT Event
                triggeredAbilities.add(this);
            }
        }

        return triggeredAbilities;
    };

    /**
     * Gets current health of monster
     * 
     * @return current health of monster
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Sets the current health of monster
     * 
     * @param health value to set current health to
     */
    public void setCurrentHealth(int health) {
        if (health < 0) {
            currentHealth = 0;
        } else {
            currentHealth = health;
        }
    }

    /**
     * Gets the current attack damage of the monster
     * 
     * @return the current attack damage
     */
    public int getCurrentAttackDamage() {
        return this.currentAttackDamage;
    }

    /**
     * Sets the current attack damage of the monster
     * 
     * @param attackDamage the new current attack damage
     */
    public void setCurrentAttackDamage(int attackDamage) {
        currentAttackDamage = attackDamage;
    }

    /**
     * Gets the status of the monster.
     * true when alive and false when fainted
     * 
     * @return the status of the monster
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Sets the status of the monster.
     * 
     * @param status new status of the monster
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Regenerates monster and
     * restores the monster attack and health back to base values
     */
    public void restore() {
        currentHealth = baseHealth;
        currentAttackDamage = baseAttackDamage;
        setStatus(true);
    }

    /**
     * Gets the current ability Trigger
     * 
     * @return current event that triggers the ability
     */
    public Trigger getTrigger() {
        return abilityTrigger;
    }

    /**
     * Sets the ability Trigger
     * 
     * @param newTrigger event to trigger ability
     */
    public void setTrigger(Trigger newTrigger) {
        this.abilityTrigger = newTrigger;
    }

    /**
     * Increases monster base health
     * 
     * @param amount amount to increase by
     */
    public void increaseBaseHealth(int amount) {
        baseHealth += amount;
        currentHealth = baseHealth;
    }

    /**
     * Increases base monster attack damage
     * 
     * @param amount amount to increase by
     */
    public void increaseBaseAttackDamage(int amount) {
        baseAttackDamage += amount;
        currentAttackDamage = baseAttackDamage;
    }

    /**
     * Gets the description of the monster ability
     * 
     * @return the description of the monster ability
     */
    public String getAbilityDescription() {
        return abilityDescription;
    }

    /**
     * Sets the description of the monster ability
     * 
     * @param description the description of the monster ability
     */
    public void setAbilityDescription(String description) {
        this.abilityDescription = description;
    }

    /**
     * Gets the number of times the monster has fainted
     * 
     * @return the number of times the monster has fainted
     */
    public int getFaintCount() {
        return faintCount;
    }

    /**
     * Increases the faintCount by 1
     */
    public void incrementFaintCount() {
        faintCount++;
    }

}
