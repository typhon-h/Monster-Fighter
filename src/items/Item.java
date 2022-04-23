package items;

import main.Entity;
import main.Rarity;
import monsters.Monster;
import exceptions.UnusableItemException;

/**
 * Generic Item.
 * Subclass of {@link main.Entity} and the superclass of specific items.
 * Used to define common traits between all the specific items.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022
 */
public abstract class Item extends Entity {

    /**
     * Constructor to call the constructor of the superclass, Entity.
     * Sets instance variables
     *
     * @param newName        The name of the player
     * @param newDescription Description of the item
     * @param newRarity      {@link main.Rarity} of the item
     */
    public Item(String newName,
            String newDescription,
            Rarity newRarity) {
        super(newName, newDescription, newRarity);
        // Might need to call setRarity(newRarity) here if the superclass implementation
        // doesnt
        // call the subclass's implementation which overrides the superclass
        // implementation.
    }

    /**
     * Sets the {@link main.Rarity} of the item.
     * Overrides the superclass implementation but uses the superclass
     * implementation.
     *
     * @param rarity New {@link main.Rarity} of the item
     */
    @Override
    public void setRarity(Rarity rarity) {
        super.setRarity(rarity);
        setBuySellPrice();
    }

    /**
     * Set the buy and sell price of the item based on the {@link main.Rarity} of
     * the item.
     */
    private void setBuySellPrice() {
        switch (getRarity()) {
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
            default:
                setBuyPrice(ItemConstants.COMMONBUYPRICE);
                setSellPrice(ItemConstants.COMMONSELLPRICE);
                break;
        }
    }

    /**
     * Returns the amount to increment a statistic of a {@link monsters.Monster}
     * based on the {@link main.Rarity} of the item.
     *
     * @return The amount to increase the statistic by.
     */
    public int getStatBoostAmount() {
        switch (getRarity()) {
            case COMMON:
                return ItemConstants.COMMONSTATBOOST;
            case RARE:
                return ItemConstants.RARESTATBOOST;
            case LEGENDARY:
                return ItemConstants.LEGENDARYSTATBOOST;
            default:
                return ItemConstants.COMMONSTATBOOST;
        }
    }

    /**
     * Returns the amount to increment a statistic of a {@link monsters.Monster}
     * based on a given {@link main.Rarity}.
     *
     * @param rarity the of the item to retrieve stats for
     * @return The amount to increase the statistic by.
     */
    public static int getStatBoostAmount(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return ItemConstants.COMMONSTATBOOST;
            case RARE:
                return ItemConstants.RARESTATBOOST;
            case LEGENDARY:
                return ItemConstants.LEGENDARYSTATBOOST;
            default:
                return ItemConstants.COMMONSTATBOOST;
        }
    }

    /**
     * Applies the unique effect of the item to a given {@link monsters.Monster}.
     *
     * @param monster The {@link monsters.Monster} to apply the effect to.
     * @throws UnusableItemException The item cannot be used.
     */
    public abstract void use(Monster monster) throws UnusableItemException; // Test this with the Exception
}
