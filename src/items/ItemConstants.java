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
     * of a {@link monsters.Monster monster}.
     */
    public static final int COMMONSTATBOOST = 1;

    /**
     * Constant defining the amount a rare item should boost a stat
     * of a {@link monsters.Monster monster}.
     */
    public static final int RARESTATBOOST = 3;

    /**
     * Constant defining the amount a legendary item should boost a stat
     * of a {@link monsters.Monster monster}.
     */
    public static final int LEGENDARYSTATBOOST = 5;

    /**
     * Constant defining the cost of a common {@link items.Item item}.
     */
    public static final int COMMONBUYPRICE = 15;

    /**
     * Constant defining the cost of a rare {@link items.Item item}.
     */
    public static final int RAREBUYPRICE = 45;

    /**
     * Constant defining the cost of a legendary {@link items.Item item}.
     */
    public static final int LEGENDARYBUYPRICE = 75;

    /**
     * Constant defining the cost of a common {@link items.Item item}.
     */
    public static final int COMMONSELLPRICE = 10;

    /**
     * Constant defining the cost of a rare {@link items.Item item}.
     */
    public static final int RARESELLPRICE = 35;

    /**
     * Constant defining the cost of a legendary {@link items.Item item}.
     */
    public static final int LEGENDARYSELLPRICE = 60;

    /**
     * Constant defining the average buy price of all {@link items.Item items}.
     */
    public static final int AVERAGEITEMBUYPRICE = (int) Math.round((COMMONBUYPRICE + RAREBUYPRICE + LEGENDARYBUYPRICE) / 3);

    /**
     * Constant defining the average price of a boost point
     */
    public static final float AVERAGEBOOSTPERBUYPRICE = (float) (COMMONSTATBOOST + RARESTATBOOST + LEGENDARYSTATBOOST) /
                                                        (float) (COMMONBUYPRICE + RAREBUYPRICE + LEGENDARYBUYPRICE);
}
