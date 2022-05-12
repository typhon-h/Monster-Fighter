package shop;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;
import items.*;
import monsters.*;
import main.*;

/**
 * Testing for Sell Shop class.
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class SellShopTest {

    /**
     * Player that interacts with the shop
     */
    Player player;
    /**
     * SellShop object to be tested
     */
    SellShop shop;

    /**
     * Sets up a player/shop instance for testing
     * 
     * @throws TeamSizeException         Too many monsters in team
     * @throws DuplicateMonsterException Same monster in team more than once
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
        shop = new SellShop(player);
    }

    /**
     * Checks shop content matches items possessed by player
     */
    @Test
    public void contentMatchTest() {
        assertEquals(player.getInventory().size() + player.getTeam().getMonsters().size(),
                shop.getContent().size());

        for (Item item : player.getInventory()) {
            assertTrue(shop.getContent().contains(item));
        }

        for (Monster monster : player.getTeam().getMonsters()) {
            assertTrue(shop.getContent().contains(monster));
        }
    }

    /**
     * Tests content is updated correctly
     * 
     * @throws TeamSizeException         Too many monsters in team
     * @throws DuplicateMonsterException Same monster in team more than once
     */
    @Test
    public void setContentTest() throws TeamSizeException, DuplicateMonsterException {
        contentMatchTest();
        player.addItem(new AttackBoost(Rarity.RARE));
        player.getTeam().addMonster(new ClinkMonster());
        shop.setContent();
        contentMatchTest();
    }

    /**
     * Tests valid item sale
     */
    @Test
    public void validItemSellTest() {
        // Valid sale
        int startingGold = player.getGold();
        Item itemToSell = player.getInventory().get(0);
        String message = shop.sell(itemToSell);

        assertEquals(startingGold + itemToSell.getSellPrice(), player.getGold());
        assertEquals(itemToSell.getName() + " was sold for " + itemToSell.getSellPrice(), message);
        assertFalse(player.getInventory().contains(itemToSell));
        assertFalse(shop.getContent().contains(itemToSell));
        contentMatchTest();
    }

    /**
     * Checks item player does not own cannot be sold
     */
    @Test
    public void noItemSellTest() {
        // Player does not have item
        Item itemToSell = new AttackBoost(Rarity.LEGENDARY);
        int startingGold = player.getGold();

        assertFalse(player.getInventory().contains(itemToSell));
        assertFalse(shop.getContent().contains(itemToSell));

        String message = shop.sell(itemToSell);

        assertEquals(startingGold, player.getGold());
        assertEquals("Sell Error: item was not found", message);
        contentMatchTest();
    }

    /**
     * Checks valid monster sale
     */
    @Test
    public void validMonsterSellTest() {
        // Valid sale
        int startingGold = player.getGold();
        Monster monsterToSell = player.getTeam().getMonsters().get(0);
        String message = shop.sell(monsterToSell);

        assertEquals(startingGold + monsterToSell.getSellPrice(), player.getGold());
        assertEquals(monsterToSell.getName() + " was sold for " + monsterToSell.getSellPrice(), message);
        assertFalse(player.getTeam().getMonsters().contains(monsterToSell));
        assertFalse(shop.getContent().contains(monsterToSell));
        contentMatchTest();
    }

    /**
     * Checks monster cannot be sold if not owned by player
     */
    @Test
    public void noMonsterSellTest() {
        // Player does not have Monster
        Monster monsterToSell = new ClinkMonster();
        int startingGold = player.getGold();

        assertFalse(player.getTeam().getMonsters().contains(monsterToSell));
        assertFalse(shop.getContent().contains(monsterToSell));

        String message = shop.sell(monsterToSell);

        assertEquals(startingGold, player.getGold());
        assertEquals("Sell Error: monster was not found", message);
        contentMatchTest();
    }

    /**
     * Checks last monster cannot be sold
     * 
     * @throws TeamSizeException         If team is created with more members than
     *                                   maximum
     * @throws DuplicateMonsterException monster is already in team
     */
    @Test
    public void lastMonsterSellTest() throws TeamSizeException, DuplicateMonsterException {
        // Player selling their last monster
        int startingGold = player.getGold();
        player.setTeam(new Team(new ClinkMonster())); // Team with one monster
        shop.setContent();
        Monster monsterToSell = player.getTeam().getMonsters().get(0);

        String message = shop.sell(monsterToSell);

        assertTrue(player.getTeam().getMonsters().contains(monsterToSell));
        assertTrue(shop.getContent().contains(monsterToSell));
        assertEquals(startingGold, player.getGold());
        assertEquals("Team must have at least " + Team.getMinTeamSize() + " monsters", message);
        contentMatchTest();
    }

}
