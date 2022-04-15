package items;

import main.Rarity;
import monsters.Monster;

/**
 * An item that increases a random stat of a Monster
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class RandomStatBoost extends Item {

    /**
     * Constructor for RandomStatBoost
     * 
     * @param newName        name of the item
     * @param newDescription description of the item
     * @param newRarity      rarity of the description
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

    // TODO: Implement this method based on item and rarity
    public void use(Monster monster) {

    }
}
