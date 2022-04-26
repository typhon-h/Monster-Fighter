package shop.tests;

import org.junit.jupiter.api.*;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;
import items.*;
import main.*;
import monsters.*;
import shop.BuyShop;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class BuyShopTest {

    /**
     * BuyShop object to be tested
     */
    BuyShop buyShop;
    /**
     * Player that interacts with the shop
     */
    Player player;

    /**
     * Sets up player/shop instances for testing
     * 
     * @throws TeamSizeException         too many members in team
     * @throws DuplicateMonsterException member already in team
     */
    @BeforeEach
    public void setUp() throws TeamSizeException, DuplicateMonsterException {
        Team team = new Team(
                new ClinkMonster(),
                new DittaMonster(),
                new GilMonster());
        player = new Player("Player", team, 0);
        player.addItem(new AttackBoost(Rarity.COMMON));
        player.addItem(new RandomTrigger());

        buyShop = new BuyShop(player);
    }

    /**
     * Checks content in the shop is updated
     * Repeated to account for randomness in shop content
     */
    @RepeatedTest(10)
    public void setContentTest() {
        ArrayList<Entity> previousContent = new ArrayList<>(buyShop.getContent());
        buyShop.setContent();
        assertNotEquals(previousContent, buyShop.getContent());

        for (Rarity rarity : Rarity.values()) {
            int monstersOfRarity = 0;
            int itemsOfRarity = 0;
            for (Entity object : buyShop.getContent()) {
                if (object instanceof Monster && object.getRarity() == rarity) {
                    monstersOfRarity++;
                }

                if (object instanceof Item && object.getRarity() == rarity) {
                    itemsOfRarity++;
                }
            }
            assertEquals(1, monstersOfRarity);
            assertEquals(1, itemsOfRarity);
        }
    }

    /**
     * Gets an object from the shop to be bought
     * 
     * @param objectToRetrieve Class instance of the object to be retrieved
     * @return object of given instance in shop
     */
    private Object getObjectFromShop(Class<?> objectToRetrieve) {
        Object objectToBuy = null;
        for (Entity object : buyShop.getContent()) {
            if (objectToRetrieve.isInstance(object)) {
                objectToBuy = object;
                break;
            }
        }
        return objectToBuy;
    }

    /**
     * Checks items can be bought
     */
    @Test
    public void itemBuyTest() {
        // Valid buy
        Item itemToBuy = (Item) getObjectFromShop(Item.class);
        assertFalse(player.getInventory().contains(itemToBuy));

        player.addGold(itemToBuy.getBuyPrice());
        int startingGold = player.getGold();
        String message = buyShop.buy(itemToBuy);

        assertTrue(player.getInventory().contains(itemToBuy));
        assertEquals(startingGold - itemToBuy.getBuyPrice(), player.getGold());
        assertFalse(buyShop.getContent().contains(itemToBuy));
        assertEquals(itemToBuy.getName() + " bought successfully", message);

        // Insufficient gold
        itemToBuy = (Item) getObjectFromShop(Item.class);
        assertFalse(player.getInventory().contains(itemToBuy));
        assertEquals(0, player.getGold());

        message = buyShop.buy(itemToBuy);

        assertFalse(player.getInventory().contains(itemToBuy));
        assertTrue(buyShop.getContent().contains(itemToBuy));
        assertEquals(itemToBuy.getBuyPrice() + " more gold is required", message);

        // Inventory Full
        while (player.getNumFreeSlots() > 0) {
            player.addItem(new AttackBoost(Rarity.COMMON));
        }
        player.addGold(itemToBuy.getBuyPrice());
        assertFalse(player.getInventory().contains(itemToBuy));
        message = buyShop.buy(itemToBuy);

        assertFalse(player.getInventory().contains(itemToBuy));
        assertTrue(buyShop.getContent().contains(itemToBuy));
        assertEquals("Sorry, you do not have enough space in your inventory", message);

        // Item not in shop
        itemToBuy = new AttackBoost(Rarity.COMMON);
        assertFalse(buyShop.getContent().contains(itemToBuy));
        message = buyShop.buy(itemToBuy);
        assertFalse(player.getInventory().contains(itemToBuy));
        assertFalse(buyShop.getContent().contains(itemToBuy));
        assertEquals("Buy Error: item not found", message);

    }

    /**
     * Checks monsters can be bought
     * 
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException member is already in team
     */
    @Test
    public void monsterBuyTest() throws TeamSizeException, DuplicateMonsterException {
        // Valid buy
        Monster monsterToBuy = (Monster) getObjectFromShop(Monster.class);
        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));

        player.addGold(monsterToBuy.getBuyPrice());
        int startingGold = player.getGold();
        String message = buyShop.buy(monsterToBuy);

        assertTrue(player.getTeam().getMonsters().contains(monsterToBuy));
        assertEquals(startingGold - monsterToBuy.getBuyPrice(), player.getGold());
        assertFalse(buyShop.getContent().contains(monsterToBuy));
        assertEquals(monsterToBuy.getName() + " bought successfully", message);

        // Insufficient gold
        monsterToBuy = (Monster) getObjectFromShop(Monster.class);
        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertEquals(0, player.getGold());

        message = buyShop.buy(monsterToBuy);

        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertTrue(buyShop.getContent().contains(monsterToBuy));
        assertEquals(monsterToBuy.getBuyPrice() + " more gold is required", message);

        // Inventory Full
        while (player.getTeam().getTeamSize() < Team.getMaxTeamSize()) {
            player.getTeam().addMonster(new ClinkMonster());
        }
        player.addGold(monsterToBuy.getBuyPrice());
        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        message = buyShop.buy(monsterToBuy);

        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertTrue(buyShop.getContent().contains(monsterToBuy));
        assertEquals("Team is full", message);

        // Duplicate Monster
        player.getTeam().removeMonster(player.getTeam().getMonsters().get(0));
        monsterToBuy = player.getTeam().getMonsters().get(0);
        buyShop.getContent().add(monsterToBuy);
        message = buyShop.buy(monsterToBuy);
        assertEquals("Monster is already in the team", message);

        // Monster not in shop
        monsterToBuy = new ClinkMonster();
        assertFalse(buyShop.getContent().contains(monsterToBuy));
        message = buyShop.buy(monsterToBuy);
        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertFalse(buyShop.getContent().contains(monsterToBuy));
        assertEquals("Buy Error: monster not found", message);

    }
}
