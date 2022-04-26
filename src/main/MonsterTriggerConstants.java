package main;

import java.util.Map;
import static java.util.Map.entry;

import monsters.*;

/**
 * Class definting the constants of the optimal triggers for
 * each monster in the game.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
public final class MonsterTriggerConstants {
    // TODO: Tweak the triggers for each monster
    /*
    * When triggers for a monster are changed, also make the same changes to
    * the test file.
    */

    /**
     * A map mapping each subclass of the monster class to an array of triggers.
     */
    private static final Map<Class<?>, Trigger[][]> triggers = Map.ofEntries(
        // Optimal Triggers for Clink Monster.
        entry(ClinkMonster.class,   new Trigger[][] {
            {Trigger.ONFAINT,Trigger.STARTOFBATTLE}, // Easy Triggers
            {}, // Normal Triggers
            {}}), // Hard Triggers
        // Optimal Triggers for Ditta Monster.
        entry(DittaMonster.class,   new Trigger[][] {
            {Trigger.ONFAINT, Trigger.STARTOFBATTLE, Trigger.ONHURT},
            {},
            {}}),
        // Optimal Triggers for Gil Monster.
        entry(GilMonster.class,     new Trigger[][] {
            {Trigger.ONFAINT, Trigger.STARTOFBATTLE},
            {},
            {}}),
        // Optimal Triggers for Jynx Monster.
        entry(JynxMonster.class,    new Trigger[][] {
            {Trigger.ONFAINT, Trigger.STARTOFBATTLE},
            {},
            {}}),
        // Optimal Triggers for Lucifer Monster.
        entry(LuciferMonster.class, new Trigger[][] {
            {Trigger.ONFAINT, Trigger.STARTOFBATTLE},
            {},
            {}}),
        // Optimal Triggers for Teddy Monster.
        entry(TeddyMonster.class,   new Trigger[][] {
            {Trigger.ONFAINT, Trigger.STARTOFBATTLE},
            {},
            {}})
    );

    /**
     * Concatenates two {@link main.Trigger trigger arrays} together.
     *
     * @param array1 First array to concatenate to.
     * @param array2 Second array to concatenate.
     * @return       Concatenated arrays.
     */
    private static Trigger[] appendArray(Trigger[] array1, Trigger[] array2) {
        Trigger[] finalArray = new Trigger[array1.length + array2.length];

        for (int i=0; i<array1.length; i++) {
            finalArray[i] = array1[i];
        }

        for (int i=0; i<array2.length; i++) {
            finalArray[array1.length + i] = array2[i];
        }

        return finalArray;
    }

    private static Trigger[] getTriggerDifficulties(Trigger[][] allTriggers, Difficulty difficulty) {
        Trigger[] finalTriggers = new Trigger[0];
        switch (difficulty) {
            case HARD:
                finalTriggers = appendArray(finalTriggers, allTriggers[2]);
            case NORMAL:
                finalTriggers = appendArray(finalTriggers, allTriggers[1]);
            case EASY:
                finalTriggers = appendArray(finalTriggers, allTriggers[0]);
        }
        return finalTriggers;
    }

    /**
     * Gets the optimal triggers for a given monster.
     *
     * @param monster The monster to get the triggers for.
     * @return        An array of {@link main.Trigger trigger enum} values.
     */
    public static final Trigger[] getTriggers(Monster monster, Difficulty difficulty) {
        Trigger[][] allTriggers = triggers.get(monster.getClass());
        return getTriggerDifficulties(allTriggers, difficulty);
    }

    /**
     * Gets the optimal triggers for a given monster
     *
     * @param monsterClass              The class of the monster to get the triggers for/
     * @return                          An array of {@link main.Trigger trigger enum} values.
     * @throws IllegalArgumentException if the given class is not a subclass of {@link monsters.Monster monster class}.
     */
    public static final Trigger[] getTriggers(Class<?> monsterClass, Difficulty difficulty) throws IllegalArgumentException {
        if (Monster.class.isAssignableFrom(monsterClass) && monsterClass != Monster.class) {
            Trigger[][] allTriggers = triggers.get(monsterClass);
            return getTriggerDifficulties(allTriggers, difficulty);
        } else {
            throw new IllegalArgumentException("The given class is not a subclass of Monster class");
        }
    }
}
