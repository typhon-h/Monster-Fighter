package items;

import main.Rarity;
import monsters.Monster;

/**
 * An item that increases the base attack damage of a {@link monsters.Monster} by
 * a set amount based on the {@link main.Rarity} of the item.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
public class AttackBoost extends Item{
    /**
     * Constructor for AttackBoost.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     * @param newRarity      {@link main.Rarity} of the item.
     */
    public AttackBoost(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);
    }

    /**
     * Increases the base attack damage of a given {@link monsters.Monster} by an amount
     * based on the {@link main.Rarity} of the item.
     *
     * @param monster The {@link monsters.Monster} to use the item on.
     */
    public void use(Monster monster) {
        monster.increaseBaseAttackDamage(getStatBoostAmount());
        monster.increaseSellPrice(this.getSellPrice());
    }
}
