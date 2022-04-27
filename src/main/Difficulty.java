package main;

/**
 * Different difficulties of the game
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public enum Difficulty {
    /**
     * Easy difficulty
     */
    EASY,
    /**
     * Normal difficulty, default difficulty
     */
    NORMAL,
    /**
     * Hard difficulty
     */
    HARD;

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
