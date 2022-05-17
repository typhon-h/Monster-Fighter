package monsters;

/**
 * Different possible events that can trigger an {@link monsters.Monster#ability
 * ability} in battle
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public enum Trigger {
    /**
     * When a monster faints
     */
    ONFAINT,
    /**
     * At the very start of the battle
     */
    STARTOFBATTLE,
    /**
     * Before a monster attacks
     */
    BEFOREATTACK,
    /**
     * After a monster has attacked
     */
    AFTERATTACK,
    /**
     * When a monster takes damage
     */
    ONHURT,
    /**
     * The monster's ability will not be triggered
     */
    NOABILITY;

    /**
     * Total number of triggers
     */
    public static final int numTriggers;
    static {
        numTriggers = values().length;
    }
}
