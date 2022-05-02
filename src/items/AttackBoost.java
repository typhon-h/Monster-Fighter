package items;

import main.Rarity;
import monsters.Monster;

/**
 * An item that increases the base attack damage of a {@link monsters.Monster}
 * by
 * a set amount based on the {@link main.Rarity} of the item.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
public class AttackBoost extends Item {
        /**
         * Constructor for AttackBoost.
         *
         * @param newRarity {@link main.Rarity} of the item.
         */
        public AttackBoost(Rarity newRarity) {
                super("Attack Boost",
                                ItemConstants.ATTACKBOOSTDESC + Item.getStatBoostAmount(newRarity),
                                newRarity);
        }

        /**
         * Increases the base attack damage of a given {@link monsters.Monster} by an
         * amount
         * based on the {@link main.Rarity} of the item.
         *
         * @param monster The {@link monsters.Monster} to use the item on.
         * @return string describing item effect
         */
        public String use(Monster monster) {
                monster.increaseBaseAttackDamage(getStatBoostAmount());
                monster.increaseSellPrice(this.getSellPrice());
                new String();
                return String.format(ItemConstants.ATTACKBOOSTFEEDBACK, monster.getName(), getStatBoostAmount());
        }
}
