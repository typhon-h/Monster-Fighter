package main.tests;

import main.*;
import monsters.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Testing for {@link main.Team Team} class.
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class TeamTest {

    /**
     * {@link main.Team Team to be tested}
     */
    Team team;

    /**
     * Set up a full {@link main.Team Team to be tested} for testing
     *
     * @throws TeamSizeException         if too many team members
     * @throws DuplicateMonsterException if same monster is added twice
     */
    @BeforeEach
    public void setUp() throws TeamSizeException, DuplicateMonsterException {
        team = new Team(new ClinkMonster());
        while (team.getTeamSize() < Team.getMaxTeamSize()) {
            team.addMonster(new ClinkMonster());
        }
    }

    /**
     * Checks that a {@link main.Team Team to be tested} cannot be created with more
     * than
     * {@link main.Team#MAXTEAMSIZE} and
     * less than {@link main.Team#MINTEAMSIZE} monsters.
     */
    @Test
    public void constructorTeamSizeTest() {
        TeamSizeException exception = assertThrows(TeamSizeException.class, () -> {
            team = new Team(new ClinkMonster());
            while (team.getTeamSize() < Team.getMaxTeamSize() + 1) {
                team.addMonster(new ClinkMonster());
            }
        });
        assertEquals("Team is full", exception.getMessage());

        exception = assertThrows(TeamSizeException.class, () -> new Team());
        assertEquals("Team must have at least " + Team.getMinTeamSize() + " monsters", exception.getMessage());
    }

    /**
     * Test cases to check
     *
     * @return A stream of arguments as the test case
     */
    private static Stream<Arguments> positionsToTest() {

        return Stream.of(
                Arguments.arguments(new int[] {}),
                Arguments.arguments(new int[] { 0 }),
                Arguments.arguments(new int[] { 1 }),
                Arguments.arguments(new int[] { 2 }),
                Arguments.arguments(new int[] { 3 }),
                Arguments.arguments(new int[] { 0, 1 }),
                Arguments.arguments(new int[] { 0, 1, 2 }),
                Arguments.arguments(new int[] { 0, 1, 3 }),
                Arguments.arguments(new int[] { 2, 3 }));
    }

    /**
     * Tests alive {@link monsters.Monster monsters} are able to be retrieved with
     * different combinations
     * of fainted {@link monsters.Monster monsters}
     *
     * @param positionsToFaint array of indexes of {@link monsters.Monster monsters}
     *                         to faint
     */
    @ParameterizedTest
    @MethodSource("positionsToTest")
    public void aliveMonstersTest(int[] positionsToFaint) {
        ArrayList<Monster> expected = new ArrayList<>(team.getMonsters());
        Monster monsterAtPosition;
        for (int position : positionsToFaint) { // Faint monsters and alter expected
            monsterAtPosition = team.getMonsters().get(position);
            expected.remove(monsterAtPosition);
            monsterAtPosition.takeDamage(monsterAtPosition.getCurrentHealth());
            assertFalse(monsterAtPosition.getStatus()); // Check fainted
        }
        assertEquals(expected, team.getAliveMonsters());
        assertEquals(expected.get(0), team.getFirstAliveMonster());
    }

    /**
     * Tests when all {@link monsters.Monster monsters} have fainted
     */
    @Test
    public void noAliveMonsterTest() {
        for (Monster monster : team.getMonsters()) { // Faint all monsters
            monster.takeDamage(monster.getCurrentHealth());
            assertFalse(monster.getStatus()); // Check fainted
        }

        assertNull(team.getFirstAliveMonster()); // No first alive monster

        ArrayList<Monster> expected = new ArrayList<Monster>();
        assertEquals(expected, team.getAliveMonsters()); // Empty list
    }

    /**
     * Checks {@link monsters.Monster monsters} are added correctly
     *
     * @throws TeamSizeException         if too many team members
     * @throws DuplicateMonsterException if same monster is added twice
     */
    @Test
    public void addMonsterTest() throws TeamSizeException, DuplicateMonsterException {
        team = new Team(new ClinkMonster());
        int count = 1;
        while (team.getTeamSize() < Team.getMaxTeamSize()) {
            team.addMonster(new ClinkMonster());
            assertEquals(++count, team.getTeamSize());
        }

        TeamSizeException exception = assertThrows(TeamSizeException.class, () -> {
            team.addMonster(new ClinkMonster());
        });
        assertEquals("Team is full", exception.getMessage());
    }

    /**
     * Checks duplicate {@link monsters.Monster monsters} cannot be added to a
     * {@link main.Team team}
     *
     * @throws TeamSizeException         if too many team members
     * @throws DuplicateMonsterException if same monster is added twice
     */
    @Test
    public void duplicateMonsterTest() throws TeamSizeException, DuplicateMonsterException {
        Monster monster = new ClinkMonster();
        team = new Team(monster);
        DuplicateMonsterException exception = assertThrows(DuplicateMonsterException.class, () -> {
            team.addMonster(monster);
        });
        assertEquals("Monster is already in the team", exception.getMessage());

        exception = assertThrows(DuplicateMonsterException.class, () -> {
            team = new Team(monster, monster);
        });
        assertEquals("Monster is already in the team", exception.getMessage());

    }

    /**
     * Checks {@link monsters.Monster monsters} are correctly removed from various
     * positions in {@link main.Team team}
     *
     * @param positionsToRemove array of indexes of {@link monsters.Monster
     *                          monsters} to remove
     * @throws TeamSizeException if tries to remove more than
     *                           {@link main.Team#MINTEAMSIZE minimum team size}
     */
    @ParameterizedTest
    @MethodSource("positionsToTest")
    public void removeMonsterTest(int[] positionsToRemove) throws TeamSizeException {
        ArrayList<Monster> expected = new ArrayList<>(team.getMonsters());
        ArrayList<Monster> originalTeam = new ArrayList<>(team.getMonsters());
        Monster monsterAtPosition;
        for (int position : positionsToRemove) { // Remove monsters and alter expected
            monsterAtPosition = originalTeam.get(position);
            expected.remove(monsterAtPosition);
            team.removeMonster(monsterAtPosition);
        }
        assertEquals(expected, team.getMonsters());
    }

    /**
     * Checks {@link monsters.Monster monsters} not in the {@link main.Team team}
     * cannot be removed
     *
     * @throws TeamSizeException if too many team members
     */
    @Test
    public void removeMonsterOverflowTest() throws TeamSizeException {
        while (team.getTeamSize() > Team.getMinTeamSize()) {
            team.removeMonster(team.getMonsters().get(0));
        }

        TeamSizeException exception = assertThrows(TeamSizeException.class, () -> {
            team.removeMonster(team.getMonsters().get(0));
        });

        assertEquals("Team must have at least " + Team.getMinTeamSize() + " monsters", exception.getMessage());
    }

    /**
     * Checks {@link monsters.Monster monster} is able to be moved up in the
     * {@link main.Team team}
     */
    @Test
    public void moveMonsterUpTest() {
        ArrayList<Monster> startingTeam = new ArrayList<>(team.getMonsters());
        Monster topOfTeam = team.getMonsters().get(0);
        Monster monsterToMove = team.getMonsters().get(1);

        // Moves to top
        team.moveMonsterUp(monsterToMove);
        assertEquals(team.getMonsters().get(0), monsterToMove);
        assertEquals(team.getMonsters().get(1), topOfTeam);
        assertEquals(startingTeam.subList(2, team.getTeamSize()), team.getMonsters().subList(2, team.getTeamSize()));

        // Nothing happens if at top
        team.moveMonsterUp(monsterToMove);
        assertEquals(team.getMonsters().get(0), monsterToMove);
        assertEquals(team.getMonsters().get(1), topOfTeam);
        assertEquals(startingTeam.subList(2, team.getTeamSize()), team.getMonsters().subList(2, team.getTeamSize()));

    }

    /**
     * Checks {@link monsters.Monster monster} is able to be moved down in the
     * {@link main.Team team}
     */
    @Test
    public void moveMonsterDownTest() {
        ArrayList<Monster> startingTeam = new ArrayList<>(team.getMonsters());
        Monster bottomOfTeam = team.getMonsters().get(team.getTeamSize() - 1);
        Monster monsterToMove = team.getMonsters().get(team.getTeamSize() - 2);

        // Moves to bottom
        team.moveMonsterDown(monsterToMove);
        assertEquals(team.getMonsters().get(team.getTeamSize() - 1), monsterToMove);
        assertEquals(team.getMonsters().get(team.getTeamSize() - 2), bottomOfTeam);
        assertEquals(startingTeam.subList(0, team.getTeamSize() - 2),
                team.getMonsters().subList(0, team.getTeamSize() - 2));

        // Nothing happens if at bottom
        team.moveMonsterDown(monsterToMove);
        assertEquals(team.getMonsters().get(team.getTeamSize() - 1), monsterToMove);
        assertEquals(team.getMonsters().get(team.getTeamSize() - 2), bottomOfTeam);
        assertEquals(startingTeam.subList(0, team.getTeamSize() - 2),
                team.getMonsters().subList(0, team.getTeamSize() - 2));
    }
}
