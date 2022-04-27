package items;

/**
 * Class containing constants for items based on {@link main.Rarity} level
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
     * Constant defining the average buy price of all items.
     */
    public static final int AVERAGEITEMBUYPRICE = (int) Math.round((COMMONBUYPRICE + RAREBUYPRICE + LEGENDARYBUYPRICE) / 3);

    public static final float AVERAGEBOOSTPERBUYPRICE = (COMMONSTATBOOST + RARESTATBOOST + LEGENDARYSTATBOOST) /
                                                        (COMMONBUYPRICE + RAREBUYPRICE + LEGENDARYBUYPRICE);
}
