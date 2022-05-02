package main;

import java.util.Map;
import static java.util.Map.entry;

import monsters.*;

/**
 * Class defining the constants of the optimal {@link main.Trigger triggers} for
 * each {@link monsters.Monster monster} in the game.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
public final class BattleConstants {
    // TODO: Tweak the triggers for each monster
    /*
     * When triggers for a monster are changed, also make the same changes to
     * the test file.
     */

    /**
     * Constant for number of opponents generated each day for the
     * {@link main.Player player}
     * to face against.
     */
    public static final int NUMOPPONENTS = 3;

    /**
     * A map mapping each subclass of the {@link monsters.Monster monster} class to
     * an array of {@link main.Trigger triggers}.
     */
    private static final Map<Class<?>, Trigger[][]> triggers = Map.ofEntries(
            // Optimal Triggers for Clink Monster.
            entry(ClinkMonster.class, new Trigger[][] {
                    { Trigger.ONFAINT }, // Easy Triggers
                    { Trigger.STARTOFBATTLE }, // Normal Triggers
                    { Trigger.ONHURT } }), // Hard Triggers
            // Optimal Triggers for Ditta Monster.
            entry(DittaMonster.class, new Trigger[][] {
                    { Trigger.ONFAINT, Trigger.STARTOFBATTLE, Trigger.ONHURT },
                    {},
                    { Trigger.AFTERATTACK } }),
            // Optimal Triggers for Gil Monster.
            entry(GilMonster.class, new Trigger[][] {
                    { Trigger.ONFAINT, Trigger.STARTOFBATTLE },
                    {},
                    {} }),
            // Optimal Triggers for Jynx Monster.
            entry(JynxMonster.class, new Trigger[][] {
                    { Trigger.ONFAINT, Trigger.STARTOFBATTLE },
                    {},
                    {} }),
            // Optimal Triggers for Lucifer Monster.
            entry(LuciferMonster.class, new Trigger[][] {
                    { Trigger.ONFAINT, Trigger.STARTOFBATTLE },
                    {},
                    {} }),
            // Optimal Triggers for Teddy Monster.
            entry(TeddyMonster.class, new Trigger[][] {
                    { Trigger.ONFAINT, Trigger.STARTOFBATTLE },
                    {},
                    {} }));

    /**
     * Concatenates two {@link main.Trigger trigger} arrays together.
     *
     * @param array1 First array to concatenate to.
     * @param array2 Second array to concatenate.
     * @return Concatenated arrays.
     */
    private static Trigger[] appendArray(Trigger[] array1, Trigger[] array2) {
        Trigger[] finalArray = new Trigger[array1.length + array2.length];

        for (int i = 0; i < array1.length; i++) {
            finalArray[i] = array1[i];
        }

        for (int i = 0; i < array2.length; i++) {
            finalArray[array1.length + i] = array2[i];
        }

        return finalArray;
    }

    /**
     * Returns all the {@link main.Trigger triggers} as a one-dimensional array
     * based on the
     * {@link main.Difficulty difficulty} of the game in order from easy to hard.
     *
     * @param allTriggers A two-dimensional array of all the triggers.
     * @param difficulty  The difficulty of the game.
     * @return A one-dimensional array of all the triggers based on diffculty.
     */
    private static Trigger[] getTriggerDifficulties(Trigger[][] allTriggers, Difficulty difficulty) {
        Trigger[] finalTriggers = new Trigger[0];
        switch (difficulty) {
            // No break statements here because HARD includes both NORMAL and EASY triggers
            case HARD:
                finalTriggers = appendArray(allTriggers[2], finalTriggers);
            case NORMAL:
                finalTriggers = appendArray(allTriggers[1], finalTriggers);
            case EASY:
                finalTriggers = appendArray(allTriggers[0], finalTriggers);
        }
        return finalTriggers;
    }

    /**
     * Gets the optimal {@link main.Trigger triggers} for a given
     * {@link monsters.Monster monster}.
     *
     * @param monster    The {@link monsters.Monster monster} to get the
     *                   {@link main.Trigger triggers} for.
     * @param difficulty The {@link main.Difficulty difficulty} of the game
     * @return An array of {@link main.Trigger trigger enum} values.
     */
    public static final Trigger[] getTriggers(Monster monster, Difficulty difficulty) {
        Trigger[][] allTriggers = triggers.get(monster.getClass());
        return getTriggerDifficulties(allTriggers, difficulty);
    }

    // TODO: can we remove the method above this and always pass the class using
    // monster.getClass()
    /**
     * Gets the optimal {@link main.Trigger triggers} for a given
     * {@link monsters.Monster monster}.
     *
     * @param monsterClass The class of the {@link monsters.Monster monster} to get
     *                     the {@link main.Trigger triggers} for/
     * @param difficulty   The {@link main.Difficulty difficulty} of the game
     * 
     * @return An array of {@link main.Trigger trigger enum} values.
     * @throws IllegalArgumentException if the given class is not a subclass of
     *                                  {@link monsters.Monster monster class}.
     */
    public static final Trigger[] getTriggers(Class<?> monsterClass, Difficulty difficulty)
            throws IllegalArgumentException {
        if (Monster.class.isAssignableFrom(monsterClass) && monsterClass != Monster.class) {
            Trigger[][] allTriggers = triggers.get(monsterClass);
            return getTriggerDifficulties(allTriggers, difficulty);
        } else {
            throw new IllegalArgumentException("The given class is not a subclass of Monster class");
        }
    }
}
