package monsters.tests;

import monsters.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import main.Trigger;

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
     * Checks that monster recieves damage correctly
     * Covers:
     * valid: 1
     * invalid: 0
     * invalid: -1
     */
    @Test
    public void recieveDamageTest() {
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
     * Check ONHURT event is triggered
     * Covers: takeDamage
     * Valid: recieves damage and has ONHURT
     * Invalid: recieves damage and has ONFAINT
     * Invalid: recieves damage and faints and has ONHURT
     */
    @Test
    public void onHurtTriggerTest() {
        monster.setTrigger(Trigger.ONHURT);
        Monster triggeredAbility = monster.takeDamage(monster.getBaseHealth() - 1); // Non lethal
        // Hurt ability was triggered
        assertEquals(monster, triggeredAbility);

        monster.restore();
        monster.setTrigger(Trigger.ONFAINT);
        triggeredAbility = monster.takeDamage(monster.getBaseHealth() - 1); // Non lethal
        // Hurt ability was not triggered
        assertNull(triggeredAbility);

        monster.restore();
        monster.setTrigger(Trigger.ONHURT);
        triggeredAbility = monster.takeDamage(monster.getBaseHealth() + 1); // Lethal
        assertNull(triggeredAbility);
    }

    /**
     * Checks health cannot overflow to zero
     * Covers: takeDamage, setCurrentHealth
     * monster recieves more damage than current health
     * monster recieves damage with 0 health
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
     * Check ONFAINT event is triggered
     * Covers: takeDamage
     * Valid: recieves damage and faints and has ONFAINT
     * Invalid: recieves damage and has ONFAINT
     * Invalid: recieves damage and has ONHURT
     */
    @Test
    public void onFaintTriggerTest() {
        monster.setTrigger(Trigger.ONFAINT);
        Monster triggeredAbility = monster.takeDamage(monster.getCurrentHealth());
        // Faint ability was triggered
        assertEquals(monster, triggeredAbility);
        assertFalse(monster.getStatus()); // Check fainted

        monster.restore();
        triggeredAbility = monster.takeDamage(monster.getBaseHealth() - 1); // Non lethal
        // Faint ability was not triggered
        assertNull(triggeredAbility);
        assertTrue(monster.getStatus()); // Check fainted

        monster.restore();
        monster.setTrigger(Trigger.ONHURT);
        triggeredAbility = monster.takeDamage(monster.getCurrentHealth());
        // Hurt ability was not triggered
        assertNull(triggeredAbility);
        assertFalse(monster.getStatus()); // Check fainted

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
}
