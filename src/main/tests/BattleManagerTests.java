package main.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import items.ItemConstants;
import main.BattleManager;
import main.Difficulty;
import main.Player;
import main.Rarity;
import main.Team;
import monsters.ClinkMonster;
import monsters.Monster;

class BattleManagerTests {
    Player player;
    Team playerTeam;
    BattleManager battleManager;

    @BeforeEach
    void setUp() throws Exception {
        playerTeam = new Team(new ClinkMonster());
        player = new Player(playerTeam, 0);

        battleManager = new BattleManager(player);
    }

    // @Test
    // public void getRandomMonsterTest() {
    //     HashSet<Class<?>> monsterClasses = new HashSet<Class<?>>();

    //     int i = 0;
    //     while (monsterClasses.size() < Monster.NUMMONSTERS && i < 1000) {
    //         monsterClasses.add(battleManager.getRandomMonster(Difficulty.HARD).getClass());
    //         i++;
    //     }
    //     System.out.println(monsterClasses.toString());
    //     assertEquals(monsterClasses.size(), Monster.NUMMONSTERS);
    // }
    
    /**
     * Test cases to check
     *
     * @return A stream of arguments as the test case
     */
    private static Stream<Arguments> dayAndMonsterNum() {
        return Stream.of(
                Arguments.arguments(1, 1),
                Arguments.arguments(14, 6),
                Arguments.arguments(7, 3));
    }
    
    @ParameterizedTest
    @MethodSource("dayAndMonsterNum")
    public void generateTeamSizeTest(int day, int numMonsters) {
        Team team = battleManager.generateTeam(day, 15, Difficulty.HARD);
        
        assertEquals(team.getTeamSize(), numMonsters);
    }
}
