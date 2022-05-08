package monsters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Testing for generic monster class.
 * Covers all non trivial methods (not getters/setters)
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class MonsterTest {

    private Monster monster;

    /**
     * Set up a monster to test methods on before each test
     *
     * @throws Exception any error that occurs
     */
    @BeforeEach
    public void setUp() throws Exception {
        monster = new ClinkMonster();
    }

    /**
     * Checks that monster receives damage correctly
     * Covers:
     * valid: 1
     * invalid: 0
     * invalid: -1
     */
    @Test
    public void receiveDamageTest() {
        monster.takeDamage(monster.getBaseHealth() - 1); // non lethal
        // Checks damage is dealt
        assertEquals(1, monster.getCurrentHealth());
        // Base health is not effected
        assertNotEquals(monster.getBaseHealth(), monster.getCurrentHealth());

        // 0 damage
        monster.restore();
        IllegalArgumentException damageException = assertThrows(IllegalArgumentException.class, () -> {
            monster.takeDamage(0);
        });
        assertEquals("Argument must be positive", damageException.getMessage());

        // Negative damage
        damageException = assertThrows(IllegalArgumentException.class, () -> {
            monster.takeDamage(-1);
        });
        assertEquals("Argument must be positive", damageException.getMessage());

    }

    /**
     * Checks health cannot overflow to zero
     * Covers: takeDamage, setCurrentHealth
     * monster receives more damage than current health
     * monster receives damage with 0 health
     * monster health is set negative
     */
    @Test
    public void healthOverflowTest() {
        // Checks health doesn't overflow
        monster.takeDamage(monster.getBaseHealth() + 1); // Lethal
        assertEquals(0, monster.getCurrentHealth());

        monster.restore();
        monster.setCurrentHealth(0);
        monster.takeDamage(monster.getBaseHealth() + 1); // Lethal
        assertEquals(0, monster.getCurrentHealth());

        monster.restore();
        monster.setCurrentHealth(-1);
        assertEquals(0, monster.getCurrentHealth());
    }

    /**
     * Check that when a monster faints faint conditions are met
     * Covers: takeDamage
     * health is 0
     * status is false
     * faintCount is incremented
     */
    @Test
    public void faintTest() {
        int initialFaintCount = monster.getFaintCount();
        monster.takeDamage(monster.getCurrentHealth());
        // No health
        assertEquals(0, monster.getCurrentHealth());
        // Fainted status
        assertFalse(monster.getStatus());
        // Increased faint count
        assertEquals(initialFaintCount + 1, monster.getFaintCount());

    }

    /**
     * Checks restore sets instance variables correctly
     * Covers: restore
     * currentHealth set to baseHealth
     * currentAttackDamage set to baseAttackDamage
     * status set to true
     */
    @Test
    public void restoreTest() {
        // Check stats restored when below base values
        monster.setCurrentHealth(monster.getBaseHealth() - 1);
        monster.setCurrentAttackDamage(monster.getBaseAttackDamage() - 1);
        monster.setStatus(false);
        monster.restore();
        assertEquals(monster.getBaseHealth(), monster.getCurrentHealth());
        assertEquals(monster.getBaseAttackDamage(), monster.getCurrentAttackDamage());
        assertTrue(monster.getStatus());

        // Check stats restored when above base values
        monster.setCurrentHealth(monster.getBaseHealth() + 1);
        monster.setCurrentAttackDamage(monster.getBaseAttackDamage() + 1);
        monster.restore();
        assertEquals(monster.getBaseHealth(), monster.getCurrentHealth());
        assertEquals(monster.getBaseAttackDamage(), monster.getCurrentAttackDamage());
        assertTrue(monster.getStatus());

        // Check stats restored when equal to base stats
        monster.restore();
        assertEquals(monster.getBaseHealth(), monster.getCurrentHealth());
        assertEquals(monster.getBaseAttackDamage(), monster.getCurrentAttackDamage());
        assertTrue(monster.getStatus());
    }

    /**
     * Checks stat increase methods increase stats
     * Covers: increaseBaseHealth, increaseBaseAttackDamage
     * Valid: increased by positive
     * Invalid: increased by 0
     * Invalid: increased by negative
     */
    @Test
    public void statIncreaseTest() {
        int startBaseHealth = monster.getBaseHealth();
        int startBaseAttackDamage = monster.getBaseAttackDamage();
        // Increase by positive
        monster.increaseBaseHealth(1);
        monster.increaseBaseAttackDamage(1);

        // Check base stats increased
        assertEquals(startBaseHealth + 1, monster.getBaseHealth());
        assertEquals(startBaseAttackDamage + 1, monster.getBaseAttackDamage());

        // Check current stats updated
        assertEquals(monster.getBaseHealth(), monster.getCurrentHealth());
        assertEquals(monster.getBaseAttackDamage(), monster.getCurrentAttackDamage());

        // Increase by 0
        IllegalArgumentException healthException = assertThrows(IllegalArgumentException.class, () -> {
            monster.increaseBaseHealth(0);
        });
        IllegalArgumentException attackDamageException = assertThrows(IllegalArgumentException.class, () -> {
            monster.increaseBaseAttackDamage(0);
        });
        assertEquals("Argument must be positive", healthException.getMessage());
        assertEquals("Argument must be positive", attackDamageException.getMessage());

        // Increase by negative
        healthException = assertThrows(IllegalArgumentException.class, () -> {
            monster.increaseBaseHealth(-1);
        });
        attackDamageException = assertThrows(IllegalArgumentException.class, () -> {
            monster.increaseBaseAttackDamage(-1);
        });
        assertEquals("Argument must be positive", healthException.getMessage());
        assertEquals("Argument must be positive", attackDamageException.getMessage());
    }

    /**
     * Test that a {@link monsters.Monster monster} can be {@link Cloneable cloned}
     * 
     * @throws CloneNotSupportedException if cannot be cloned
     */
    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Monster clonedMonster = (Monster) monster.clone();
        assertNotEquals(clonedMonster, monster);

        assertEquals(monster.getName(), clonedMonster.getName());
        assertEquals(monster.getAbilityDescription(), clonedMonster.getAbilityDescription());
        assertEquals(monster.getBaseAttackDamage(), clonedMonster.getBaseAttackDamage());
        assertEquals(monster.getBaseHealth(), clonedMonster.getBaseHealth());
        assertEquals(monster.getBuyPrice(), clonedMonster.getBuyPrice());
        assertEquals(monster.getCurrentAttackDamage(), clonedMonster.getCurrentAttackDamage());
        assertEquals(monster.getCurrentHealth(), clonedMonster.getCurrentHealth());
        assertEquals(monster.getDescription(), clonedMonster.getDescription());
        assertEquals(monster.getFaintCount(), clonedMonster.getFaintCount());
        assertEquals(monster.getRarity(), clonedMonster.getRarity());
        assertEquals(monster.getSellPrice(), clonedMonster.getSellPrice());
        assertEquals(monster.getSpeed(), clonedMonster.getSpeed());
        assertEquals(monster.getStatus(), clonedMonster.getStatus());
        assertEquals(monster.getTrigger(), clonedMonster.getTrigger());
    }
}
