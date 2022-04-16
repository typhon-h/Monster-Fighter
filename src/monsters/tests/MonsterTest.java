package monsters.tests;

import monsters.*;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.*;

import main.Trigger;

public class MonsterTest { // TODO: testing for valid arguments on constructor?

    private Monster monster;

    @BeforeEach
    public void setUp() throws Exception {
        monster = new ClinkMonster();
    }

    @Test
    public void recieveDamageTest() {
        int startingHealth = monster.getCurrentHealth();
        monster.takeDamage(1);
        // Checks damage is dealt
        assertEquals(startingHealth - 1, monster.getCurrentHealth());
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

    @Test
    public void onHurtTriggerTest() {
        ArrayList<Monster> expectedAbilities = new ArrayList<>(Arrays.asList(monster));
        monster.setTrigger(Trigger.ONHURT);
        Queue<Monster> triggeredAbilities = monster.takeDamage(1);
        // Hurt ability was triggered
        assertEquals(expectedAbilities, triggeredAbilities);

        monster.restore();
        monster.setTrigger(Trigger.ONFAINT);
        triggeredAbilities = monster.takeDamage(1);
        // Hurt ability was not triggered
        assertNotEquals(expectedAbilities, triggeredAbilities);
    }

    @Test
    public void healthOverflowTest() {
        // Checks health doesn't overflow
        monster.takeDamage(monster.getCurrentHealth() + 1);
        assertEquals(0, monster.getCurrentHealth());

        monster.restore();
        monster.setCurrentHealth(0);
        monster.takeDamage(1);
        assertEquals(0, monster.getCurrentHealth());

        monster.restore();
        monster.setCurrentHealth(-1);
        assertEquals(0, monster.getCurrentHealth());
    }

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

    @Test
    public void onFaintTriggerTest() {
        ArrayList<Monster> expectedAbilities = new ArrayList<>(Arrays.asList(monster));
        monster.setTrigger(Trigger.ONFAINT);
        Queue<Monster> triggeredAbilities = monster.takeDamage(monster.getCurrentHealth());
        // Faint ability was triggered
        assertEquals(triggeredAbilities, expectedAbilities);
        assertFalse(monster.getStatus()); // Check fainted

        monster.restore();
        monster.setTrigger(Trigger.ONHURT);
        triggeredAbilities = monster.takeDamage(monster.getCurrentHealth());
        // Hurt ability was not triggered
        assertNotEquals(expectedAbilities, triggeredAbilities);
        assertFalse(monster.getStatus()); // Check fainted
    }

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
