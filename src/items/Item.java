package items;

import main.Entity;
import main.Rarity;
import monsters.Monster;
import exceptions.UnusableItemException;

/**
 * Generic Item.
 * Used to define common traits between all the specific items.
 *
 * @author Jackie Jone
 * @version 1.2, Apr 2022
 */
public abstract class Item extends Entity {

    /**
     * Creates a new Item using {@link main.Entity}
     *
     * @param newName        The name of the {@link main.Player}
     * @param newDescription Description of the item
     * @param newRarity      {@link main.Rarity} of the item
     */
    public Item(String newName,
            String newDescription,
            Rarity newRarity) {
        super(newName, newDescription, newRarity);
    }

    /**
     * Creates new Item and Sets the {@link main.Rarity} of the item.
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
     * Returns the amount to increment a stat of a {@link monsters.Monster}
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
     * @return string describing item effect
     */
    public abstract String use(Monster monster) throws UnusableItemException;

    /**
     * Gets the string representation of the item
     *
     * @return The string representation of the item
     */
    @Override
    public String toString() {
        String output;
        output = this.getName() + " (" +
        this.getRarity() + ")\n" +
        this.getDescription();

        return output;
    }
}
