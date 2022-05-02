package items;

import main.Rarity;
import monsters.Monster;

/**
 * An item that increases the speed of a {@link monsters.Monster} by
 * a set amount based on the {@link main.Rarity} of the item.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
public class SpeedBoost extends Item {
        /**
         * Constructor for SpeedBoost.
         *
         * @param newRarity {@link main.Rarity} of the item.
         */
        public SpeedBoost(Rarity newRarity) {
                super("Speed Boost",
                                ItemConstants.SPEEDBOOSTDESC + Item.getStatBoostAmount(newRarity),
                                newRarity);
        }

        /**
         * Increases the speed of a given {@link monsters.Monster} by an amount
         * based on the {@link main.Rarity} of the item.
         *
         * @param monster The {@link monsters.Monster} to use the item on.
         * @return string describing item effect
         */
        public String use(Monster monster) {
                monster.increaseSpeed(getStatBoostAmount());
                monster.increaseSellPrice(this.getSellPrice());
                return String.format(ItemConstants.SPEEDBOOSTFEEDBACK, monster.getName(), getStatBoostAmount());
        }
}
