package main;

import java.util.ArrayList;

import exceptions.*;
import items.Item;
import items.RandomStatBoost;
import monsters.Monster;

public class RandomEvent {

    public static ArrayList<String> randomBoost(Team team, int currentDay) {
        ArrayList<String> results = new ArrayList<String>();
        Item boost = new RandomStatBoost(Rarity.COMMON);

        for (Monster monster : team.getMonsters()) {
            double probabilityBound = RandomEventConstants.STATBOOSTPROBABILITY;
            // TODO: probability manip based on current day and faint count and difficulty

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

    public static ArrayList<String> randomMonsterLeave(Team team) {
        ArrayList<String> results = new ArrayList<String>();

        for (Monster monster : team.getMonsters()) {
            double probabilityBound = RandomEventConstants.MONSTERLEAVEPROBABILITY;
            // TODO: probability manip based on faint count and difficulty

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

    public static String randomMonsterJoin(Team team, int currentDay) {
        double probabilityBound = RandomEventConstants.MONSTERJOINPROBABILITY;
        // TODO: probability manip based on current day and difficulty

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
