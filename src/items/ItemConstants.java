package items;

/**
 * Class containing constants for {@link items.Item item}s based on
 * {@link main.Rarity} level
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public final class ItemConstants {
        /**
         * Constant defining the amount a {@link main.Rarity#COMMON common}
         * {@link items.Item item} should boost a stat
         * of a {@link monsters.Monster monster}.
         */
        public static final int COMMONSTATBOOST = 1;

        /**
         * Constant defining the amount a {@link main.Rarity#RARE rare}
         * {@link items.Item item} should boost a
         * stat
         * of a {@link monsters.Monster monster}.
         */
        public static final int RARESTATBOOST = 3;

        /**
         * Constant defining the amount a {@link main.Rarity#LEGENDARY legendary}
         * {@link items.Item item} should boost
         * a stat
         * of a {@link monsters.Monster monster}.
         */
        public static final int LEGENDARYSTATBOOST = 5;

        /**
         * Constant defining the cost of a {@link main.Rarity#COMMON common}
         * {@link items.Item item}.
         */
        public static final int COMMONBUYPRICE = 15;

        /**
         * Constant defining the cost of a {@link main.Rarity#RARE rare}
         * {@link items.Item item}.
         */
        public static final int RAREBUYPRICE = 45;

        /**
         * Constant defining the cost of a {@link main.Rarity#LEGENDARY legendary}
         * {@link items.Item item}.
         */
        public static final int LEGENDARYBUYPRICE = 75;

        /**
         * Constant defining the cost of a {@link main.Rarity#COMMON common}
         * {@link items.Item item}.
         */
        public static final int COMMONSELLPRICE = 10;

        /**
         * Constant defining the cost of a {@link main.Rarity#RARE rare}
         * {@link items.Item item}.
         */
        public static final int RARESELLPRICE = 35;

        /**
         * Constant defining the cost of a {@link main.Rarity#LEGENDARY legendary}
         * {@link items.Item item}.
         */
        public static final int LEGENDARYSELLPRICE = 60;

        /**
         * Constant defining the average buy price of all {@link items.Item items}.
         */
        public static final int AVERAGEITEMBUYPRICE = (int) Math
                        .round((COMMONBUYPRICE + RAREBUYPRICE + LEGENDARYBUYPRICE) / 3);

        /**
         * Constant defining the average price of a boost point
         */
        public static final float AVERAGEBOOSTPERBUYPRICE = (float) (COMMONSTATBOOST + RARESTATBOOST
                        + LEGENDARYSTATBOOST) /
                        (float) (COMMONBUYPRICE + RAREBUYPRICE + LEGENDARYBUYPRICE);

        /**
         * Constant defining the description of {@link items.AttackBoost attack boost}
         * {@link items.Item item}
         */
        public static final String ATTACKBOOSTDESC = "Increases the attack of a Monster by "; // amount

        /**
         * Feedback message when {@link items.AttackBoost attack boost} is used
         */
        public static final String ATTACKBOOSTFEEDBACK = "%s's ATTACK was increased by %d";

        /**
         * Constant defining the description of {@link items.HealthBoost health boost}
         * {@link items.Item item}
         */
        public static final String HEALTHBOOSTDESC = "Increases the health of a Monster by "; // amount

        /**
         * Feedback message when {@link items.HealthBoost health boost} is used
         */
        public static final String HEALTHBOOSTFEEDBACK = "%s's HEALTH was increased by %d";

        /**
         * Constant defining the description of {@link items.SpeedBoost speed boost}
         * {@link items.Item item}
         */
        public static final String SPEEDBOOSTDESC = "Increases the speed of a Monster by "; // amount

        /**
         * Feedback message when {@link items.SpeedBoost speed boost} is used
         */
        public static final String SPEEDBOOSTFEEDBACK = "%s's SPEED was increased by %d";
        /**
         * Constant defining the description of {@link items.RandomStatBoost random
         * boost} {@link items.Item item}
         */
        public static final String RANDOMBOOSTDESC = "Increases a random stat of the monster by "; // amount

        /**
         * Feedback message when {@link items.RandomStatBoost random boost} is used
         */
        public static final String RANDOMBOOSTFEEDBACK = "%s's %s was increased by %d";
        /**
         * Constant defining the description of {@link items.SelectTrigger select
         * trigger} {@link items.Item item}
         */
        public static final String SELECTTRIGGERDESC = "Sets the Ability Trigger of a Monster to "; // Trigger

        /**
         * Feedback message when {@link items.SelectTrigger select trigger} is used
         */
        public static final String SELECTTRIGGERFEEDBACK = "%s's ability trigger was changed to %s";
        /**
         * Constant defining the description of {@link items.RandomTrigger random
         * trigger} {@link items.Item item}
         */
        public static final String RANDOMTRIGGERDESC = "Sets the Ability Trigger of a Monster randomly"; // NOTHING
                                                                                                         // ADDITIONAL
        /**
         * Feedback message when {@link items.RandomTrigger random trigger} is used
         */
        public static final String RANDOMTRIGGERFEEDBACK = "%s's ability trigger was changed to %s";
}
