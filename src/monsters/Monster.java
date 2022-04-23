package monsters;

import main.Entity;
import main.Rarity;
import main.Team;
import main.Trigger;

/**
 * Generic Monster
 *
 * @author Harrison Tyson
 * @version 1.3, Apr 2022.
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
     * Speed of the monster.
     */
    private int speed;

    /**
     * Constructor for the Monster class
     *
     * @param name               Name of the monster
     * @param description        Description of the monster
     * @param rarity             Rarity of the monster
     * @param baseHealth         Base health of the monster
     * @param baseAttackDamage   Base attack damage of the monster
     * @param abilityDescription Description of the monster's ability
     * @param speed              Speed of the monster
     */
    public Monster(String name, String description, Rarity rarity,
            int baseAttackDamage, int baseHealth, String abilityDescription,
            int speed) {
        super(name, description, rarity); // Entity
        this.baseHealth = this.currentHealth = baseHealth;
        this.baseAttackDamage = this.currentAttackDamage = baseAttackDamage;
        setAbilityDescription(abilityDescription);
        setSpeed(speed);
    }

    /**
     * Unique ability of the monster that has special effects in battle
     *
     * @param allyTeam  Friendly team of the monster
     * @param enemyTeam Enemy team of the monster
     * @return a Monster whose ability gets triggered next
     */
    public abstract Monster ability(Team allyTeam, Team enemyTeam);

    /**
     * Deals damage to the monster and triggers relevant events
     *
     * @param damage amount of damage recieved
     * @throws IllegalArgumentException Argument must be positive
     * @return itself if ability was triggered
     */
    public Monster takeDamage(int damage) throws IllegalArgumentException {
        if (damage <= 0) {
            throw new IllegalArgumentException("Argument must be positive");
        }

        this.currentHealth -= damage;

        if (currentHealth <= 0) { // Check for faint
            currentHealth = 0;
            setStatus(false);
            incrementFaintCount();
            if (getTrigger() == Trigger.ONFAINT) { // ONFAINT Event
                return this;
            }
        } else if (getTrigger() == Trigger.ONHURT) {
            return this;
        }

        return null; // No ability triggered
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
    public void setCurrentAttackDamage(int attackDamage) throws IllegalArgumentException {
        if (attackDamage < 1) {
            currentAttackDamage = 1;
        }
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
     * @throws IllegalArgumentException Argument must be positive
     */
    public void increaseBaseHealth(int amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Argument must be positive");
        }
        baseHealth += amount;
        currentHealth = baseHealth;

    }

    /**
     * Gets the base health of the monster
     *
     * @return base health of the monster
     */
    public int getBaseHealth() {
        return baseHealth;
    }

    /**
     * Gets the base attack damage of the monster
     *
     * @return base attack damage of the monster
     */
    public int getBaseAttackDamage() {
        return baseAttackDamage;
    }

    /**
     * Increases base monster attack damage
     *
     * @param amount amount to increase by
     * @throws IllegalArgumentException Argument must be positive
     */
    public void increaseBaseAttackDamage(int amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Argument must be positive");
        }
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

    @Override
    public void setRarity(Rarity rarity) {
        super.setRarity(rarity);
        switch (this.getRarity()) {
            case COMMON:
                setBuyPrice(MonsterConstants.COMMONBUYPRICE);
                setSellPrice(MonsterConstants.COMMONSELLPRICE);
                break;
            case RARE:
                setBuyPrice(MonsterConstants.RAREBUYPRICE);
                setSellPrice(MonsterConstants.RARESELLPRICE);
                break;
            case LEGENDARY:
                setBuyPrice(MonsterConstants.LEGENDARYBUYPRICE);
                setSellPrice(MonsterConstants.LEGENDARYSELLPRICE);
                break;
            default:
                setBuyPrice(MonsterConstants.COMMONBUYPRICE);
                setSellPrice(MonsterConstants.COMMONSELLPRICE);
                break;
        }
    }

    /**
     * Sets the speed of the monster.
     *
     * @param speed The new speed of the monster.
     */
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    /**
     * Gets the speed of the monster.
     *
     * @return The speed of the monster.
     */
    public int getSpeed() {
        return this.speed;
    }
}
