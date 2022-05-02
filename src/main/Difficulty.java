package main;

/**
 * Different difficulties of the game
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
public enum Difficulty {
    /**
     * Easy difficulty
     */
    EASY,
    /**
     * Normal difficulty
     * DEFAULT
     */
    NORMAL,
    /**
     * Hard difficulty
     */
    HARD;

    /**
     * Gets the difficulty multiplier to influence game events
     * 
     * @param difficulty game difficulty
     * @return multiplier value should be scaled by
     */
    public static float getDifficultyMultiplier(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return 0.9f;
            case NORMAL:
                return 1.0f;
            case HARD:
                return 1.1f;
            default:
                return 1.0f;
        }
    }
}
