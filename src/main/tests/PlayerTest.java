package main.tests;

import main.*;
import monsters.*;
import items.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import exceptions.*;

/**
 * Testing for Player Class
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class PlayerTest {
    Player player;

    /**
     * Create a player instance for testing
     * 
     * @throws TeamSizeException         too many members in team
     * @throws DuplicateMonsterException same monster added to team more than once
     */
    @BeforeEach
    public void setUp() throws TeamSizeException, DuplicateMonsterException {
        Team defaultTeam = new Team(new ClinkMonster());
        player = new Player("MyPlayer", defaultTeam, 5);
    }

    /**
     * Test constructors setup default variables correctly
     * 
     * @throws DuplicateMonsterException if same monster is added more than once
     * @throws TeamSizeException         if more team members than max allowed
     */
    @Test
    public void constructorTest() throws TeamSizeException, DuplicateMonsterException {
        Team defaultTeam = new Team(new ClinkMonster());
        Player player1 = new Player("MyPlayer", defaultTeam, 0);
        Player opponent = new Player(defaultTeam, 0);
        assertEquals("MyPlayer", player1.getName());
        assertEquals("Opponent", opponent.getName());
        assertEquals(defaultTeam, player1.getTeam());
        assertEquals(defaultTeam, opponent.getTeam());
        assertEquals(0, player1.getGold());
        assertEquals(0, opponent.getGold());
        assertEquals(0, player1.getScore());
        assertEquals(0, opponent.getScore());
        assertEquals(0, player1.getInventory().size());
        assertEquals(0, opponent.getInventory().size());
    }

    /**
     * Checks gold is added correctly
     */
    @Test
    public void addGoldTest() {
        // Add valid amount of gold
        int startingGold = player.getGold();
        player.addGold(1);
        assertEquals(startingGold + 1, player.getGold());

        // Add negative gold
        IllegalArgumentException negativeGold = assertThrows(IllegalArgumentException.class, () -> {
            player.addGold(-1);
        });
        assertEquals("Argument cannot be negative", negativeGold.getMessage());
    }

    /**
     * Checks gold is removed correctly
     * Covers: removeGold
     * Valid: removes 1 gold
     * Invalid: negative argument
     * Valid: removes exact gold possessed
     * Invalid: removes more than posseses
     * 
     * @throws InsufficientFundsException if more gold is removed than possessed
     */
    @Test
    public void removeGoldTest() throws InsufficientFundsException {
        // Remove valid amount of gold
        int startingGold = player.getGold();
        player.removeGold(1);
        assertEquals(startingGold - 1, player.getGold());

        // Remove negative gold
        IllegalArgumentException negativeGold = assertThrows(IllegalArgumentException.class, () -> {
            player.removeGold(-1);
        });
        assertEquals("Argument cannot be negative", negativeGold.getMessage());

        // Remove exact amount of gold
        player.removeGold(player.getGold());
        assertEquals(0, player.getGold());

        // Remove additional gold
        InsufficientFundsException overDraw = assertThrows(InsufficientFundsException.class, () -> {
            player.removeGold(1);
        });

        assertEquals("1 more gold is required", overDraw.getMessage());

    }

    /**
     * Checks items can be added to player inventory
     */
    @Test
    public void addItemTest() {
        Item testItem = new AttackBoost(Rarity.COMMON);
        // Check items can be added until full
        int count = 0;
        while (player.getNumFreeSlots() > 0) {
            assertTrue(player.addItem(testItem));
            assertEquals(++count, player.getInventory().size());
        }

        // Checks can't accept more than max
        assertFalse(player.addItem(testItem));
        assertEquals(count, player.getInventory().size());
    }

    /**
     * Checks removing items from player inventory
     * Covers: removeItem
     */
    @Test
    public void removeItem() {
        // Check all items can be removed
        addItemTest();
        while (player.getInventory().size() > 0) {
            assertTrue(player.removeItem(player.getInventory().get(0)));
        }

        // Check item not in inventory can't be removed
        Item testItem = new AttackBoost(Rarity.COMMON);
        assertFalse(player.removeItem(testItem));
    }

    /**
     * Checks score is added correctly
     */
    @Test
    public void incrementScoreTest() {
        // Add valid score
        int startingScore = player.getScore();
        player.incrementScore(1);
        assertEquals(startingScore + 1, player.getScore());

        // Add negative score
        IllegalArgumentException negativeScore = assertThrows(IllegalArgumentException.class, () -> {
            player.incrementScore(-1);
        });
        assertEquals("Argument cannot be negative", negativeScore.getMessage());
    }

    /**
     * Test cases to check
     * 
     * @return A stream of arguments as the test case
     */
    private static Stream<Arguments> itemsToTest() {
        return Stream.of(
                Arguments.arguments(new AttackBoost(Rarity.COMMON)),
                Arguments.arguments(new HealthBoost(Rarity.COMMON)),
                Arguments.arguments(new RandomStatBoost(Rarity.COMMON)),
                Arguments.arguments(new RandomTrigger()),
                Arguments.arguments(new SelectTrigger(Trigger.STARTOFBATTLE)),
                Arguments.arguments(new SpeedBoost(Rarity.COMMON)));
    }

    /**
     * Tests item can be used correctly
     */
    @ParameterizedTest
    @MethodSource("itemsToTest")
    public void useItemTest(Item testItem) {
        player.getTeam().getFirstAliveMonster().setTrigger(Trigger.NOABILITY);
        // Valid item used
        player.addItem(testItem);
        assertTrue(player.getInventory().contains(testItem));
        int prevItemPoints = player.getItemPoints();
        // Item message not being checked in valid cases as a lot of variation based on
        // the item used
        player.useItem(testItem, player.getTeam().getFirstAliveMonster());
        assertEquals(prevItemPoints + testItem.getStatBoostAmount(), player.getItemPoints());
        assertFalse(player.getInventory().contains(testItem));

        // Player does not have item
        prevItemPoints = player.getItemPoints();
        String message = player.useItem(testItem, player.getTeam().getFirstAliveMonster());
        assertEquals(prevItemPoints, player.getItemPoints());
        assertEquals("Error: player does not possess this item", message);
    }

    @Test
    public void unusableItemTest() {
        Item testItem = new SelectTrigger(player.getTeam().getFirstAliveMonster().getTrigger());
        player.addItem(testItem);
        assertTrue(player.getInventory().contains(testItem));
        int prevItemPoints = player.getItemPoints();
        String message = player.useItem(testItem, player.getTeam().getFirstAliveMonster());
        assertEquals(prevItemPoints, player.getItemPoints());
        assertTrue(player.getInventory().contains(testItem));
        assertEquals("Monster: " + player.getTeam().getFirstAliveMonster().getName() + " already has trigger + "
                + player.getTeam().getFirstAliveMonster().getTrigger(), message);
    }

}