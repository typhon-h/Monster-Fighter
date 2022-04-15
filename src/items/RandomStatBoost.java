package items;

import main.Item;
import main.Rarity;
import main.Monster;

/**
 * An item that increases a random stat of a Monster
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022
 */
public class RandomStatBoost extends Item {

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
    public void use(Monster mosnter) {

    }
}
