package items;

import main.Rarity;
import monsters.Monster;
import java.util.Random;

/**
 * An item that increases a random stat of a Monster.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class RandomStatBoost extends Item {
    /**
     * Constructor for RandomStatBoost.
     *
     * @param newName        name of the item.
     * @param newDescription description of the item.
     * @param newRarity      rarity of the description.
     */
    public RandomStatBoost(String newName, String newDescription, Rarity newRarity) {
        super(newName, newDescription, newRarity);

        switch (newRarity) {
            case COMMON:
                setBuyPrice(ItemConstants.COMMONBUYPRICE);
                setSellPrice(ItemConstants.COMMONSELLPRICE);
                break;

            case RARE:
                setBuyPrice(ItemConstants.RAREBUYPRICE);
                setSellPrice(ItemConstants.RARESELLPRICE);
                break;

            case LEGENDARY:
                setBuyPrice(ItemConstants.LEGENDARYBUYPRICE);
                setSellPrice(ItemConstants.LEGENDARYSELLPRICE);
                break;
        }

    }

    /**
     * Randomly boosts either the base health or base attack damage of a monster.
     * The amount boosted is based on the rarity of the item.
     *
     * @param monster The moster to boost the stat of.
     */
    public void use(Monster monster) {
        // Generates a new random object with a random seed.
        // TODO: Test if this actually does something different on seperate method calls.
        Random random = new Random();

        int statBoostAmount;

        switch (getRarity()) {
            case COMMON:
                statBoostAmount = ItemConstants.COMMONSTATBOOST;
                break;
            case RARE:
                statBoostAmount = ItemConstants.RARESTATBOOST;
                break;
            case LEGENDARY:
                statBoostAmount = ItemConstants.LEGENDARYSTATBOOST;
                break;
            default:
                statBoostAmount = ItemConstants.COMMONSTATBOOST;
        }

        // TODO: Turn this into an enum? change that random 3 into a constant inside generic monster?
        if (random.nextInt(3) == 0) {
            monster.increaseBaseHealth(statBoostAmount);
        } else {
            monster.increaseBaseAttackDamage(statBoostAmount);
        }
    }
}
