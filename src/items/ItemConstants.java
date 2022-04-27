package items;

/**
 * Class containting constants for items based on {@link main.Rarity} level
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public final class ItemConstants {

    // TODO: tweak these constants to balance game.

    /**
     * Constant defining the amount a common item should boost a stat
     * of a {@link monsters.Monster}.
     */
    public static final int COMMONSTATBOOST = 1;

    /**
     * Constant defining the amount a rare item should boost a stat
     * of a {@link monsters.Monster}.
     */
    public static final int RARESTATBOOST = 3;

    /**
     * Constant defining the amount a legendary item should boost a stat
     * of a {@link monsters.Monster}.
     */
    public static final int LEGENDARYSTATBOOST = 5;

    /**
     * Constant defining the cost of a common item.
     */
    public static final int COMMONBUYPRICE = 20;

    /**
     * Constant defining the cost of a common item.
     */
    public static final int RAREBUYPRICE = 50;

    /**
     * Constant defining the cost of a common item.
     */
    public static final int LEGENDARYBUYPRICE = 100;

    /**
     * Constant defining the cost of a common item.
     */
    public static final int COMMONSELLPRICE = 15;

    /**
     * Constant defining the cost of a common item.
     */
    public static final int RARESELLPRICE = 35;

    /**
     * Constant defining the cost of a common item.
     */
    public static final int LEGENDARYSELLPRICE = 75;

    /**
     * Constant definiing the description of attack boost item
     */
    public static final String ATTACKBOOSTDESC = "Increases the attack of a Monster by "; // amount

    /**
     * Feedback message when attack boost is used
     */
    public static final String ATTACKBOOSTFEEDBACK = "%s's ATTACK was increased by %d to %d";

    /**
     * Constant definiing the description of health boost item
     */
    public static final String HEALTHBOOSTDESC = "Increases the health of a Monster by "; // amount

    /**
     * Feedback message when health boost is used
     */
    public static final String HEALTHBOOSTFEEDBACK = "%s's HEALTH was increased by %d to %d";

    /**
     * Constant definiing the description of speed boost item
     */
    public static final String SPEEDBOOSTDESC = "Increases the speed of a Monster by "; // amount

    /**
     * Feedback message when speed boost is used
     */
    public static final String SPEEDBOOSTFEEDBACK = "%s's SPEED was increased by %d to %d";
    /**
     * Constant definiing the description of random boost item
     */
    public static final String RANDOMBOOSTDESC = "Increases a random stat of the monster by "; // amount

    /**
     * Feedback message when random boost is used
     */
    public static final String RANDOMBOOSTFEEDBACK = "%s's %s was increased by %d to %d";
    /**
     * Constant definiing the description of select trigger item
     */
    public static final String SELECTTRIGGERDESC = "Sets the Ability Trigger of a Monster to "; // Trigger

    /**
     * Feedback message when select trigger is used
     */
    public static final String SELECTTRIGGERFEEDBACK = "%s's ability trigger was changed to %s";
    /**
     * Constant definiing the description of random trigger item
     */
    public static final String RANDOMTRIGGERDESC = "Sets the Ability Trigger of a Monster randomly"; // NOTHING
                                                                                                     // ADDITIONAL
    /**
     * Feedback message when random trigger is used
     */
    public static final String RANDOMTRIGGERFEEDBACK = "%%s's ability trigger was changed to %s";
}
