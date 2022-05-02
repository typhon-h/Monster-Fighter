package main;

import java.util.ArrayList;

import exceptions.*;
import items.Item;
import items.RandomStatBoost;
import monsters.Monster;

/**
 * Contains all random events that occur during the game.
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class RandomEvent {

    /**
     * Randomly applies a {@link items.RandomStatBoost boost} to members of a
     * {@link main.Team team}
     * based on a probability constant
     * influenced by current day and faint count
     *
     * @param team       {@link main.Team team} to apply the boost to
     * @param currentDay current day of the game, used to manipulate probability
     * @param difficulty {@link main.Difficulty difficulty} of the game
     * 
     * @return ArrayList of String effects that occurred
     */
    public static ArrayList<String> randomBoost(Team team, int currentDay, Difficulty difficulty) {
        ArrayList<String> results = new ArrayList<String>();
        Item boost = new RandomStatBoost(Rarity.COMMON);

        for (Monster monster : team.getMonsters()) {
            double probabilityBound = RandomEventConstants.STATBOOSTPROBABILITY;
            probabilityBound += ((double) currentDay - (double) monster.getFaintCount())
                    * RandomEventConstants.MODIFIERMULTIPLIER;
            probabilityBound *= 1 /
                    Difficulty.getDifficultyMultiplier(difficulty);

            if (probabilityBound > 1) {
                probabilityBound = 1;
            } else if (probabilityBound < 0) {
                probabilityBound = 0;
            }

            if (GameEnvironment.rng.nextDouble() < probabilityBound) {
                try {
                    results.add("During the night " + boost.use(monster));
                } catch (UnusableItemException e) {
                    // Do nothing
                }
            }
        }
        return results;
    }

    /**
     * Randomly removes {@link monsters.Monster monsters} from a {@link main.Team
     * team} based on a probability constant influenced by faint count
     *
     * @param team       {@link main.Team team} to remove {@link monsters.Monster
     *                   monsters} from
     * @param difficulty {@link main.Difficulty difficulty} of the game
     * @return ArrayList of String effects that occurred
     */
    public static ArrayList<String> randomMonsterLeave(Team team, Difficulty difficulty) {
        ArrayList<String> results = new ArrayList<String>();
        ArrayList<Monster> monsters = new ArrayList<Monster>(team.getMonsters());
        for (Monster monster : monsters) {
            double probabilityBound = RandomEventConstants.MONSTERLEAVEPROBABILITY;
            probabilityBound += ((double) monster.getFaintCount()) * RandomEventConstants.MODIFIERMULTIPLIER;
            probabilityBound *= Difficulty.getDifficultyMultiplier(difficulty);

            if (probabilityBound > 1) {
                probabilityBound = 1;
            }
            if (GameEnvironment.rng.nextDouble() < probabilityBound) {
                try {
                    team.removeMonster(monster);
                    results.add(monster.getName() + " got sick of fainting and ran away");
                } catch (TeamSizeException e) {
                    // Do nothing
                }
            }
        }
        return results;
    }

    /**
     * Randomly add a {@link monsters.Monster monster} to a {@link main.Team team}
     * based on a probability constant
     * influenced by available spaces in {@link main.Team team}
     *
     * @param team       {@link main.Team team} to add {@link monsters.Monster
     *                   monster} to
     * @param difficulty {@link main.Difficulty difficulty} of the game
     * @return String describing {@link monsters.Monster monster} joining, else null
     */
    public static String randomMonsterJoin(Team team, Difficulty difficulty) {
        double probabilityBound = RandomEventConstants.MONSTERJOINPROBABILITY;
        probabilityBound += (Team.getMaxTeamSize() - team.getTeamSize() - 1) * RandomEventConstants.MODIFIERMULTIPLIER;
        probabilityBound *= 1 / Difficulty.getDifficultyMultiplier(difficulty);

        if (probabilityBound > 1) {
            probabilityBound = 1;
        }

        ArrayList<Monster> possibleMonsters = GameEnvironment.generateMonsters();
        if (GameEnvironment.rng.nextDouble() < probabilityBound) {
            try {
                Monster monsterToAdd = possibleMonsters.get(GameEnvironment.rng.nextInt(possibleMonsters.size()));
                team.addMonster(monsterToAdd);
                return monsterToAdd.getName() + " joined your team overnight";
            } catch (TeamSizeException | DuplicateMonsterException e) {
                // Do nothing
            }
        }
        return null; // Event did not occur
    }
}
