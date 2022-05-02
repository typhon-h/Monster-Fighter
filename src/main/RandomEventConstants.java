package main;

/**
 * Class storing constants for probabilities of {@link main.RandomEvent Random
 * Events}
 */
public class RandomEventConstants {

    /**
     * Base probability of a {@link main.RandomEvent#randomBoost Random Stat Boost}
     * event
     */
    public static final double STATBOOSTPROBABILITY = 0.25;

    /**
     * Base probability of a {@link main.RandomEvent#randomMonsterLeave Random
     * Monster Leave} event
     */
    public static final double MONSTERLEAVEPROBABILITY = 0;

    /**
     * Base probability of a {@link main.RandomEvent#randomMonsterJoin Random
     * Monster Join} event
     */
    public static final double MONSTERJOINPROBABILITY = 0.05;

    /**
     * Amount the influencing factors change the base probability by
     */
    public static final double MODIFIERMULTIPLIER = 0.05;

}
