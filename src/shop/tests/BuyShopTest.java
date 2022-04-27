package shop.tests;

import org.junit.jupiter.api.*;

import exceptions.*;
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
        // Content was refreshed
        ArrayList<Entity> previousContent = new ArrayList<>(buyShop.getContent());
        buyShop.setContent();
        assertNotEquals(previousContent, buyShop.getContent());

        // Check contains 1 monster and 1 item of each rarity
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
        Object object = null;
        for (Entity entity : buyShop.getContent()) {
            if (objectToRetrieve.isInstance(entity)) {
                object = entity;
                break;
            }
        }
        return object;
    }

    /**
     * Valid item purchase
     */
    @Test
    public void validItemBuyTest() {
        Item itemToBuy = (Item) getObjectFromShop(Item.class);
        assertFalse(player.getInventory().contains(itemToBuy));

        player.addGold(itemToBuy.getBuyPrice());
        int startingGold = player.getGold();
        String message = buyShop.buy(itemToBuy);

        assertTrue(player.getInventory().contains(itemToBuy));
        assertEquals(startingGold - itemToBuy.getBuyPrice(), player.getGold());
        assertFalse(buyShop.getContent().contains(itemToBuy));
        assertEquals(itemToBuy.getName() + " bought successfully", message);
    }

    /**
     * Checks can't buy item with insufficient gold
     */
    @Test
    public void noGoldItemBuyTest() {
        Item itemToBuy = (Item) getObjectFromShop(Item.class);
        assertFalse(player.getInventory().contains(itemToBuy));
        assertEquals(0, player.getGold());

        String message = buyShop.buy(itemToBuy);

        assertFalse(player.getInventory().contains(itemToBuy));
        assertTrue(buyShop.getContent().contains(itemToBuy));
        assertEquals(itemToBuy.getBuyPrice() + " more gold is required", message);

    }

    /**
     * Checks can't buy item with full inventory
     */
    @Test
    public void fullInventoryItemBuyTest() {
        Item itemToBuy = (Item) getObjectFromShop(Item.class);
        assertFalse(player.getInventory().contains(itemToBuy));

        // Fill inventory
        while (player.getNumFreeSlots() > 0) {
            player.addItem(new AttackBoost(Rarity.COMMON));
        }

        player.addGold(itemToBuy.getBuyPrice());
        assertFalse(player.getInventory().contains(itemToBuy));

        String message = buyShop.buy(itemToBuy);

        assertFalse(player.getInventory().contains(itemToBuy));
        assertTrue(buyShop.getContent().contains(itemToBuy));
        assertEquals("Sorry, you do not have enough space in your inventory", message);
    }

    /**
     * Checks can't buy an item not in the shop
     */
    @Test
    public void noItemBuyTest() {
        Item itemToBuy = new AttackBoost(Rarity.COMMON);
        assertFalse(buyShop.getContent().contains(itemToBuy));

        String message = buyShop.buy(itemToBuy);

        assertFalse(player.getInventory().contains(itemToBuy));
        assertFalse(buyShop.getContent().contains(itemToBuy));
        assertEquals("Buy Error: item not found", message);
    }

    /**
     * Valid monster purchase
     */
    @Test
    public void validMonsterBuyTest() {
        Monster monsterToBuy = (Monster) getObjectFromShop(Monster.class);
        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));

        player.addGold(monsterToBuy.getBuyPrice());
        int startingGold = player.getGold();
        String message = buyShop.buy(monsterToBuy);

        assertTrue(player.getTeam().getMonsters().contains(monsterToBuy));
        assertEquals(startingGold - monsterToBuy.getBuyPrice(), player.getGold());
        assertFalse(buyShop.getContent().contains(monsterToBuy));
        assertEquals(monsterToBuy.getName() + " bought successfully", message);
    }

    /**
     * Checks can't buy monster with no gold
     */
    @Test
    public void noGoldMonsterBuyTest() {
        Monster monsterToBuy = (Monster) getObjectFromShop(Monster.class);
        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertEquals(0, player.getGold());

        String message = buyShop.buy(monsterToBuy);

        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertTrue(buyShop.getContent().contains(monsterToBuy));
        assertEquals(monsterToBuy.getBuyPrice() + " more gold is required", message);
    }

    /**
     * Checks can't buy monster with full team
     * 
     * @throws TeamSizeException         tries to add more members than max team
     *                                   size
     * @throws DuplicateMonsterException monster already in team
     */
    @Test
    public void fullInventoryMonsterBuyTest() throws TeamSizeException, DuplicateMonsterException {
        Monster monsterToBuy = (Monster) getObjectFromShop(Monster.class);

        // Fill team
        while (player.getTeam().getTeamSize() < Team.getMaxTeamSize()) {
            player.getTeam().addMonster(new ClinkMonster());
        }

        player.addGold(monsterToBuy.getBuyPrice());
        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        String message = buyShop.buy(monsterToBuy);

        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertTrue(buyShop.getContent().contains(monsterToBuy));
        assertEquals("Team is full", message);
    }

    /**
     * Checks can't buy monster already in team
     */
    @Test
    public void duplicateMonsterBuyTest() {
        Monster monsterToBuy = player.getTeam().getMonsters().get(0);
        buyShop.getContent().add(monsterToBuy);
        player.addGold(monsterToBuy.getBuyPrice());

        String message = buyShop.buy(monsterToBuy);

        assertEquals("Monster is already in the team", message);
    }

    /**
     * Checks can't buy monster not in shop
     */
    @Test
    public void noMonsterBuyTest() {
        Monster monsterToBuy = new ClinkMonster();
        assertFalse(buyShop.getContent().contains(monsterToBuy));

        String message = buyShop.buy(monsterToBuy);

        assertFalse(player.getTeam().getMonsters().contains(monsterToBuy));
        assertFalse(buyShop.getContent().contains(monsterToBuy));
        assertEquals("Buy Error: monster not found", message);
    }
}
