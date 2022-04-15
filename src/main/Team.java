package main;
import java.util.ArrayList;
/**
 * Team of monsters owned by the player
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class Team {
    /**
     * Maximum team size
     *
     * @default 6
     */
    private final static int MAXTEAMSIZE = 6;
    /**
     * Minimum team size
     *
     * @default 1
     */
    private final static int MINTEAMSIZE = 1;
    /**
     * ArrayList of monsters in team
     */
    private ArrayList<Monster> monsters;
    /**
     * Current size of the team
     */
    private int teamSize;

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster starting monster
     */
    Team(Monster monster) {
        this.monsters = new ArrayList<Monster>();
        this.monsters.add(monster);
        this.teamSize = 1;
    }

    /**
     * Gets the first monster in the team that has not fainted
     *
     * @return the first available monster
     * @throws TeamStatusException the whole team has fainted
     */
    public Monster getFirstAliveMonster() throws TeamStatusException {
        for (Monster monster : monsters) {
            if (monster.getStatus()) {
                return monster;
            }
        }
        throw new TeamStatusException();
    }

    /**
     * Gets the current monsters in the team
     *
     * @return ArrayList of current monsters in team
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Adds a monster to the team if there is space
     *
     * @param monster monster to be added
     * @throws TeamSizeException team is full
     */
    public void addMonster(Monster monster) throws TeamSizeException {
        if (teamSize == MAXTEAMSIZE) {
            throw new TeamSizeException("Team is full");
        } else {
            monsters.add(monster);
            teamSize++;
        }
    }

    /**
     * Remove a monster from the team
     *
     * @param monster monster to be removed
     * @throws TeamSizeException team is at minimum size
     */
    public void removeMonster(Monster monster) throws TeamSizeException {
        if (teamSize == MINTEAMSIZE) {
            throw new TeamSizeException("Team must have at least " + MINTEAMSIZE + " monster");
        } else {
            // Prevents teamsize decrementing on no change
            if (monsters.remove(monster)) { // Removing monster that does not exist does nothing
                teamSize--;
            }
        }
    }

    /**
     * Move a monster up a position in the team
     *
     * @param monster monster to be moved
     */
    public void moveMonsterUp(Monster monster) {
        int currentIndex = monsters.indexOf(monster);
        if (currentIndex > 0) {
            monsters.remove(monster);
            monsters.add(--currentIndex, monster);
        }
    }

    /**
     * Move a monster down a position in the team
     *
     * @param monster monster to be moved
     */
    public void moveMonsterDown(Monster monster) {
        int currentIndex = monsters.indexOf(monster);
        if (currentIndex < teamSize - 1) {
            monsters.remove(monster);
            monsters.add(++currentIndex, monster);
        }
    }
}
