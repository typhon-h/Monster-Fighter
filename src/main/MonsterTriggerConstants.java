package main;

import java.util.Map;
import static java.util.Map.entry;

import monsters.*;

/**
 * Class definting the constants of the optimal triggers for
 * each monster in the game.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public final class MonsterTriggerConstants {
    // TODO: Tweak the triggers for each monster
    /**
     * A map mapping each subclass of the monster class to an array of triggers.
     */
    private static final Map<Class<?>, Trigger[]> triggers = Map.ofEntries(
        // Optimal Triggers for Clink Monster.
        entry(ClinkMonster.class,   new Trigger[] {Trigger.ONFAINT,
                                                   Trigger.STARTOFBATTLE}),
        // Optimal Triggers for Ditta Monster.
        entry(DittaMonster.class,   new Trigger[] {Trigger.ONFAINT,
                                                   Trigger.STARTOFBATTLE,
                                                   Trigger.ONHURT}),
        // Optimal Triggers for Gil Monster.
        entry(GilMonster.class,     new Trigger[] {Trigger.ONFAINT,
                                                   Trigger.STARTOFBATTLE}),
        // Optimal Triggers for Jynx Monster.
        entry(JynxMonster.class,    new Trigger[] {Trigger.ONFAINT,
                                                   Trigger.STARTOFBATTLE}),
        // Optimal Triggers for Lucifer Monster.
        entry(LuciferMonster.class, new Trigger[] {Trigger.ONFAINT,
                                                   Trigger.STARTOFBATTLE}),
        // Optimal Triggers for Teddy Monster.
        entry(TeddyMonster.class,   new Trigger[] {Trigger.ONFAINT,
                                                   Trigger.STARTOFBATTLE})
    );

    /**
     * Gets the optimal triggers for a given monster.
     *
     * @param monster The monster to get the triggers for.
     * @return        An array of {@link main.Trigger trigger enum} values.
     */
    public static final Trigger[] getTriggers(Monster monster) {
        return triggers.get(monster.getClass());
    }

    /**
     * Gets the optimal triggers for a given monster
     *
     * @param monsterClass              The class of the monster to get the triggers for/
     * @return                          An array of {@link main.Trigger trigger enum} values.
     * @throws IllegalArgumentException if the given class is not a subclass of {@link monsters.Monster monster class}.
     */
    public static final Trigger[] getTriggers(Class<?> monsterClass) throws IllegalArgumentException {
        if (Monster.class.isAssignableFrom(monsterClass) && monsterClass != Monster.class) {
            return triggers.get(monsterClass);
        } else {
            throw new IllegalArgumentException("The given class is not a subclass of Monster class");
        }
    }
}
