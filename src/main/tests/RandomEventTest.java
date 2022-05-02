package main.tests;

import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;
import items.ItemConstants;
import main.Difficulty;
import main.GameEnvironment;
import main.RandomEvent;
import main.RandomEventConstants;
import main.Team;
import monsters.ClinkMonster;
import monsters.Monster;

/**
 * Tests probabilities of all Random Events
 * 
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class RandomEventTest {
    // Seed 11124 has even DOUBLE distribution across 100 values
    int repititions = 100;
    long seed = 11124;
    Team team;

    /**
     * Generates team with specified members and faint count for testing random
     * events
     * 
     * @param numMembers      number of monsters in team
     * @param faintPerMonster faint count for each monster
     * @throws TeamSizeException         too many members in team
     * @throws DuplicateMonsterException monster already in team
     */
    private void resetTeam(int numMembers, int faintPerMonster)
            throws TeamSizeException, DuplicateMonsterException {
        team = new Team(new ClinkMonster());
        for (int i = 0; i < numMembers - 1; i++) {
            team.addMonster(new ClinkMonster());
        }
        for (int i = 0; i < faintPerMonster; i++) {
            for (Monster m : team.getMonsters()) {
                m.incrementFaintCount();
            }
        }
    }

    /**
     * Sets rng seed to acheive even distribution for testing
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many members in team
     */
    @BeforeEach
    public void beforeEach() throws TeamSizeException, DuplicateMonsterException {
        GameEnvironment.setSeed(seed);
        resetTeam(1, 0);
    }

    /**
     * Checks events returns correct feedback
     * 
     * @throws TeamSizeException         too many members in team
     * @throws DuplicateMonsterException monster already in team
     */
    @Test
    public void randomBoostReturnTest() throws TeamSizeException, DuplicateMonsterException {
        resetTeam(2, 0);
        // Only one monster gets boosted
        ArrayList<String> events = RandomEvent.randomBoost(team, 0, Difficulty.NORMAL);
        assertEquals(1, events.size());
        Monster monsterEffected = team.getFirstAliveMonster();
        assertEquals(String.format("During the night " + ItemConstants.SPEEDBOOSTFEEDBACK, monsterEffected.getName(),
                ItemConstants.COMMONSTATBOOST), events.get(0));

        // No monsters
        GameEnvironment.setSeed(2180);
        events = RandomEvent.randomBoost(team, 0, Difficulty.NORMAL);
        assertEquals(0, events.size());

        // More than one monster gets boosted
        GameEnvironment.setSeed(4097);
        events = RandomEvent.randomBoost(team, 0, Difficulty.NORMAL);
        assertEquals(2, events.size());
        ArrayList<Monster> monstersEffected = team.getMonsters();
        assertEquals(String.format("During the night " + ItemConstants.HEALTHBOOSTFEEDBACK,
                monstersEffected.get(0).getName(),
                ItemConstants.COMMONSTATBOOST), events.get(0));
        assertEquals(String.format("During the night " + ItemConstants.ATTACKBOOSTFEEDBACK,
                monstersEffected.get(0).getName(),
                ItemConstants.COMMONSTATBOOST), events.get(1));
    }

    /**
     * Checks random stat boost occurs at base probability with no additional
     * factors
     */
    @Test
    public void randomBoostBaseTest() {
        int expectedOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            if (GameEnvironment.rng.nextDouble() < RandomEventConstants.STATBOOSTPROBABILITY) {
                expectedOccurences++;
                GameEnvironment.rng.nextInt(); // Advance for choosing item effect
            }
        }

        // Reset seed
        GameEnvironment.setSeed(seed);

        int actualOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            ArrayList<String> events = RandomEvent.randomBoost(team, 0, Difficulty.NORMAL);
            if (events.size() > 0) {
                actualOccurences++;
            }
        }

        assertEquals(expectedOccurences, actualOccurences);
    }

    /**
     * Checks random stat boost occurs with difficulty modifying probability of
     * boost
     */
    @ParameterizedTest
    @EnumSource(Difficulty.class)
    public void randomBoostDifficultyTest(Difficulty difficulty) {
        int expectedOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            if (GameEnvironment.rng.nextDouble() < RandomEventConstants.STATBOOSTPROBABILITY
                    * (1 / Difficulty.getDifficultyMultiplier(difficulty))) {
                expectedOccurences++;
                GameEnvironment.rng.nextInt(); // Advance for choosing item effect
            }
        }

        // Reset seed
        GameEnvironment.setSeed(seed);

        int actualOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            ArrayList<String> events = RandomEvent.randomBoost(team, 0, difficulty);
            if (events.size() > 0) {
                actualOccurences++;
            }
        }

        assertEquals(expectedOccurences, actualOccurences);
    }

    /**
     * Checks random stat boost occurs at base probability with current day modifier
     */
    @Test
    public void randomBoostDayTest() {
        for (int currentDay = 0; currentDay < GameEnvironment.MAXDAYS; currentDay++) {

            int expectedOccurences = 0;
            GameEnvironment.setSeed(seed);
            for (int i = 0; i < repititions; i++) {
                if (GameEnvironment.rng.nextDouble() < RandomEventConstants.STATBOOSTPROBABILITY
                        + currentDay * RandomEventConstants.MODIFIERMULTIPLIER) {
                    expectedOccurences++;
                    GameEnvironment.rng.nextInt(); // Advance for choosing item effect
                }
            }

            // Reset seed
            GameEnvironment.setSeed(seed);

            int actualOccurences = 0;
            for (int i = 0; i < repititions; i++) {
                ArrayList<String> events = RandomEvent.randomBoost(team, currentDay, Difficulty.NORMAL);
                if (events.size() > 0) {
                    actualOccurences++;
                }
            }

            assertEquals(expectedOccurences, actualOccurences);
        }
    }

    /**
     * Check increasing days increases probability of boost
     */
    @Test
    public void randomBoostDayIncrementTest() {
        int prevOccurences = 0;
        int currentOccurences = 0;
        for (int i = 0; i < GameEnvironment.MAXDAYS; i++) {
            GameEnvironment.setSeed(seed);
            for (int j = 0; j < repititions; j++) {
                ArrayList<String> events = RandomEvent.randomBoost(team, i, Difficulty.NORMAL);
                if (events.size() > 0) {
                    currentOccurences++;
                }
            }
            assertTrue(prevOccurences == 1 || currentOccurences > prevOccurences); // 1 if reaches max probability
            prevOccurences = currentOccurences;
            currentOccurences = 0;
        }
    }

    /**
     * Checks random stat boost occurs at base probability with faint modifier
     */
    @Test
    public void randomBoostFaintTest() {
        int timesToFaint = 15;

        int expectedOccurences = 0;

        for (int i = 0; i < timesToFaint; i++) {
            GameEnvironment.setSeed(seed); // Reset seed
            for (int j = 0; j < repititions; j++) {
                if (GameEnvironment.rng.nextDouble() < RandomEventConstants.STATBOOSTPROBABILITY
                        - (i * RandomEventConstants.MODIFIERMULTIPLIER)) {
                    expectedOccurences++;
                    GameEnvironment.rng.nextInt(); // Advance for choosing item effect
                }
            }
        }

        int actualOccurences = 0;

        for (int i = 0; i < timesToFaint; i++) {
            GameEnvironment.setSeed(seed);// Reset seed
            for (int j = 0; j < repititions; j++) {
                ArrayList<String> events = RandomEvent.randomBoost(team, 0, Difficulty.NORMAL);
                if (events.size() > 0) {
                    actualOccurences++;
                }
            }

            team.getFirstAliveMonster().incrementFaintCount();
        }

        assertEquals(expectedOccurences, actualOccurences);
    }

    /**
     * Check increasing faint decreases probability
     */
    @Test
    public void randomBoostFaintDecrementTest() {
        int prevOccurences = 0;
        int currentOccurences = 0;
        for (int i = 0; i < 15; i++) {
            GameEnvironment.setSeed(seed); // reset seed
            for (int j = 0; j < repititions; j++) {
                ArrayList<String> events = RandomEvent.randomBoost(team, 0, Difficulty.NORMAL);
                if (events.size() > 0) {
                    currentOccurences++;
                }
            }
            team.getFirstAliveMonster().incrementFaintCount();
            assertTrue(prevOccurences == 0 || currentOccurences < prevOccurences); // 0 if reaches min probability
            prevOccurences = currentOccurences;
            currentOccurences = 0;
        }
    }

    /**
     * Checks event returns correct feedback
     * 
     * @throws TeamSizeException         too many members in team
     * @throws DuplicateMonsterException monster already in team
     */
    @Test
    public void randomMonsterLeaveReturnTest() throws TeamSizeException, DuplicateMonsterException {
        resetTeam(3, 1);

        // No monsters
        GameEnvironment.setSeed(2180);
        ArrayList<String> events = RandomEvent.randomMonsterLeave(team, Difficulty.NORMAL);
        assertEquals(0, events.size());

        // Only one monster gets boosted
        GameEnvironment.setSeed(8390);
        Monster monsterEffected = team.getFirstAliveMonster();
        events = RandomEvent.randomMonsterLeave(team, Difficulty.NORMAL);
        assertEquals(1, events.size());
        assertEquals(monsterEffected.getName() + " got sick of fainting and ran away", events.get(0));

        resetTeam(3, 1);
        // More than one monster leaves
        GameEnvironment.setSeed(4640);
        events = RandomEvent.randomMonsterLeave(team, Difficulty.NORMAL);
        assertEquals(2, events.size());
        ArrayList<Monster> monstersEffected = team.getMonsters();
        assertEquals(monstersEffected.get(0).getName() + " got sick of fainting and ran away", events.get(0));

    }

    /**
     * Check monster leave occurs at base probability
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many members in team
     */
    @Test
    public void randomMonsterLeaveBaseTest() throws TeamSizeException, DuplicateMonsterException {
        int expectedOccurences = 0;

        resetTeam(2, 0);
        boolean justLeft = false; // Ignore second remove call since can't remove last monster
        for (int i = 0; i < repititions; i++) {
            double bound = RandomEventConstants.MONSTERLEAVEPROBABILITY;

            for (int j = 0; j < team.getTeamSize(); j++) {
                if (GameEnvironment.rng.nextDouble() < bound && !justLeft) {
                    expectedOccurences++;
                    justLeft = true;
                }
            }
            justLeft = false;
        }

        // Reset seed
        GameEnvironment.setSeed(seed);
        int actualOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            ArrayList<String> events = RandomEvent.randomMonsterLeave(team, Difficulty.NORMAL);
            if (events.size() > 0) {
                actualOccurences++;
            }
            resetTeam(2, 0);
        }

        assertEquals(expectedOccurences, actualOccurences);
    }

    /**
     * Checks random monster leave occurs with difficulty modifying probability of
     * leave
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many members in team
     */
    @ParameterizedTest
    @EnumSource(Difficulty.class)
    public void randomMonsterLeaveDifficultyTest(Difficulty difficulty)
            throws TeamSizeException, DuplicateMonsterException {
        int expectedOccurences = 0;
        int faintCount = (int) (1 / (2 * RandomEventConstants.MODIFIERMULTIPLIER)); // increase the base probability
                                                                                    // from 0 to check difficulty

        resetTeam(2, faintCount);
        boolean justLeft = false; // Ignore second remove call since can't remove last monster
        for (int i = 0; i < repititions; i++) {
            double bound = RandomEventConstants.MONSTERLEAVEPROBABILITY;
            bound += (RandomEventConstants.MODIFIERMULTIPLIER * (double) faintCount);
            bound *= Difficulty.getDifficultyMultiplier(difficulty);

            for (int j = 0; j < team.getTeamSize(); j++) {
                if (GameEnvironment.rng.nextDouble() < bound && !justLeft) {
                    expectedOccurences++;
                    justLeft = true;
                }
            }
            justLeft = false;
        }

        // Reset seed
        GameEnvironment.setSeed(seed);

        int actualOccurences = 0;
        for (int i = 0; i < repititions; i++) {

            ArrayList<String> events = RandomEvent.randomMonsterLeave(team, difficulty);
            if (events.size() > 0) {
                actualOccurences++;
            }
            resetTeam(2, faintCount);
        }

        assertEquals(expectedOccurences, actualOccurences);
    }

    /**
     * Checks random monster leave occurs with faint count modifying probability of
     * leave
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many members in team
     */
    @Test
    public void randomMonsterLeaveFaintTest() throws TeamSizeException, DuplicateMonsterException {
        // Number of faints for monsters to have so there is a high enough probability
        // to see effect of difficulty
        int numFaints = (int) (1 / RandomEventConstants.MODIFIERMULTIPLIER);
        int prevOccurences = 0;

        for (int faintCount = 0; faintCount < numFaints; faintCount++) {
            int expectedOccurences = 0;
            resetTeam(2, faintCount);
            GameEnvironment.setSeed(seed); // reset seed
            boolean justLeft = false; // Ignore second remove call since can't remove last monster
            for (int i = 0; i < repititions; i++) {
                double bound = RandomEventConstants.MONSTERLEAVEPROBABILITY;
                bound += (RandomEventConstants.MODIFIERMULTIPLIER * (double) faintCount);

                for (int j = 0; j < team.getTeamSize(); j++) {
                    if (GameEnvironment.rng.nextDouble() < bound && !justLeft) {
                        expectedOccurences++;
                        justLeft = true;
                    }
                }
                justLeft = false;
            }

            // Reset seed
            GameEnvironment.setSeed(seed);
            int actualOccurences = 0;
            for (int i = 0; i < repititions; i++) {

                ArrayList<String> events = RandomEvent.randomMonsterLeave(team, Difficulty.NORMAL);
                if (events.size() > 0) {
                    actualOccurences++;
                }
                resetTeam(2, faintCount);
            }

            assertEquals(expectedOccurences, actualOccurences);
            assertTrue(prevOccurences == 0 || prevOccurences == repititions - 1 || actualOccurences > prevOccurences);
            prevOccurences = actualOccurences;
        }
    }

    /**
     * Checks event returns correct feedback
     * 
     * @throws TeamSizeException         too many members in team
     * @throws DuplicateMonsterException monster already in team
     */
    @Test
    public void randomMonsterJoinReturnTest() throws TeamSizeException, DuplicateMonsterException {
        resetTeam(1, 0);

        // No monsters
        GameEnvironment.setSeed(2180);
        String event = RandomEvent.randomMonsterJoin(team, Difficulty.NORMAL);
        assertNull(event);

        // Monster joins
        GameEnvironment.setSeed(8390);
        ArrayList<Monster> prevTeam = new ArrayList<>(team.getMonsters());
        event = RandomEvent.randomMonsterJoin(team, Difficulty.NORMAL);
        assertNotNull(event);
        boolean monsterHasJoined = false;
        for (Monster m : team.getMonsters()) {
            if (!prevTeam.contains(m) && !monsterHasJoined) {
                assertEquals(m.getName() + " joined your team overnight", event);
                monsterHasJoined = true;
            } else if (!prevTeam.contains(m) && monsterHasJoined) {
                throw new IllegalStateException("Error: more than one monster joined overnight");
            }
        }
    }

    /**
     * Check monster join occurs at base probability
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many monsters in team
     */
    @Test
    public void randomMonsterJoinBaseTest() throws TeamSizeException, DuplicateMonsterException {
        int expectedOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            if (GameEnvironment.rng.nextDouble() < RandomEventConstants.MONSTERJOINPROBABILITY) {
                expectedOccurences++;
                GameEnvironment.rng.nextInt(); // Advance for selecting a monster to add
            }
        }

        // Reset seed
        GameEnvironment.setSeed(seed);

        int actualOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            resetTeam(3, 0);
            String events = RandomEvent.randomMonsterJoin(team, Difficulty.NORMAL);
            if (events != null) {
                actualOccurences++;
            }
        }

        assertEquals(expectedOccurences, actualOccurences);
    }

    /**
     * Checks random monster leave occurs with difficulty modifying probability of
     * leave
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many members in team
     */
    @ParameterizedTest
    @EnumSource(Difficulty.class)
    public void randomMonsterJoinDifficultyTest(Difficulty difficulty)
            throws TeamSizeException, DuplicateMonsterException {
        int expectedOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            double bound = RandomEventConstants.MONSTERJOINPROBABILITY;
            bound *= (1 / Difficulty.getDifficultyMultiplier(difficulty));
            if (GameEnvironment.rng.nextDouble() < bound) {
                expectedOccurences++;
                GameEnvironment.rng.nextInt(); // Advance for selecting a monster to add
            }
        }

        // Reset seed
        GameEnvironment.setSeed(seed);

        int actualOccurences = 0;
        for (int i = 0; i < repititions; i++) {
            resetTeam(3, 0);
            String events = RandomEvent.randomMonsterJoin(team, difficulty);
            if (events != null) {
                actualOccurences++;
            }
        }

        assertEquals(expectedOccurences, actualOccurences);
    }

    /**
     * Checks random monster join occurs with team size modifying probability of
     * join
     * 
     * @throws DuplicateMonsterException monster already in team
     * @throws TeamSizeException         too many members in team
     */
    @Test
    public void randomMonsterJoinTeamSizeTest() throws TeamSizeException, DuplicateMonsterException {
        int prevOccurences = 0;
        for (int teamSize = 1; teamSize <= Team.getMaxTeamSize(); teamSize++) {
            int expectedOccurences = 0;
            GameEnvironment.setSeed(seed); // Reset seed
            for (int j = 0; j < repititions; j++) {
                resetTeam(teamSize, 0);
                double bound = RandomEventConstants.MONSTERJOINPROBABILITY;
                bound += ((Team.getMaxTeamSize() - team.getTeamSize() - 1) * RandomEventConstants.MODIFIERMULTIPLIER);
                if (GameEnvironment.rng.nextDouble() < bound) {
                    expectedOccurences++;
                    GameEnvironment.rng.nextInt(); // Advance for selecting a monster to add
                }
            }

            // Reset seed
            GameEnvironment.setSeed(seed);

            int actualOccurences = 0;
            for (int j = 0; j < repititions; j++) {
                resetTeam(teamSize, 0);
                String events = RandomEvent.randomMonsterJoin(team, Difficulty.NORMAL);
                if (events != null) {
                    actualOccurences++;
                }
            }

            assertEquals(expectedOccurences, actualOccurences);
            assertTrue(prevOccurences == 0 || actualOccurences < prevOccurences); // prev is 0 on first check
        }
    }
}
