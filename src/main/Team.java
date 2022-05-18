package main;

import java.util.ArrayList;
import exceptions.*;
import monsters.Monster;

/**
 * Team of {@link monsters.Monster monsters} owned by the {@link main.Player
 * player}
 *
 * @author Harrison Tyson
 * @version 1.2, Apr 2022.
 */
public class Team implements Cloneable {
    /**
     * Maximum team size
     *
     * @default 4
     */
    private final static int MAXTEAMSIZE = 4;
    /**
     * Minimum team size
     *
     * @default 1
     */
    private final static int MINTEAMSIZE = 1;
    /**
     * ArrayList of {@link monsters.Monster monsters} in team
     */
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    /**
     * Current size of the team
     */
    private int teamSize = monsters.size();

    /**
     * Creates a new team with starting {@link monsters.Monster monsters}
     *
     * @param newMonsters starting {@link monsters.Monster monsters/s}
     *
     * @throws TeamSizeException         team is full or empty
     * @throws DuplicateMonsterException {@link monsters.Monster monster} already in
     *                                   team
     */
    public Team(Monster... newMonsters) throws TeamSizeException, DuplicateMonsterException {
        if (newMonsters.length > MAXTEAMSIZE) {
            throw new TeamSizeException("Team can only contain " + MAXTEAMSIZE + " Monsters");
        } else if (newMonsters.length < MINTEAMSIZE) {
            throw new TeamSizeException("Team must have at least " + MINTEAMSIZE + " monsters");
        }

        for (Monster monster : newMonsters) {
            this.addMonster(monster);
        }
        teamSize = monsters.size();
    }

    /**
     * Gets the first {@link monsters.Monster monster} in the team that has not
     * fainted
     *
     * @return the first available {@link monsters.Monster monster}
     */
    public Monster getFirstAliveMonster() {
        for (Monster monster : monsters) {
            if (monster.getStatus()) {
                return monster;
            }
        }
        return null;
    }

    /**
     * Get all alive {@link monsters.Monster monsters}
     *
     * @return ArrayList of alive {@link monsters.Monster monsters}
     */
    public ArrayList<Monster> getAliveMonsters() {
        ArrayList<Monster> aliveMonsters = new ArrayList<Monster>();
        for (Monster monster : this.monsters) {
            if (monster.getStatus()) {
                aliveMonsters.add(monster);
            }
        }
        return aliveMonsters;
    }

    /**
     * Gets the current {@link monsters.Monster monsters} in the team
     *
     * @return {@link ArrayList ArrayList} of current {@link monsters.Monster
     *         monsters} in team
     */
    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    /**
     * Adds a {@link monsters.Monster monster} to the team if there is space
     *
     * @param monster {@link monsters.Monster monster} to be added
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException {@link monsters.Monster monster} already in
     *                                   team
     */
    public void addMonster(Monster monster) throws TeamSizeException, DuplicateMonsterException {
        if (monsters.size() == MAXTEAMSIZE) {
            throw new TeamSizeException("Team is full");
        } else if (monsters.contains(monster)) {
            throw new DuplicateMonsterException("Monster is already in the team");
        } else {
            monsters.add(monster);
            teamSize = monsters.size();
        }
    }

    /**
     * Remove a {@link monsters.Monster monster} from the team
     *
     * @param monster {@link monsters.Monster monster} to be removed
     * @throws TeamSizeException team is at minimum size
     */
    public void removeMonster(Monster monster) throws TeamSizeException {
        if (monsters.size() == MINTEAMSIZE) {
            throw new TeamSizeException("Team must have at least " + MINTEAMSIZE + " monsters");
        } else {
            monsters.remove(monster);
            teamSize = monsters.size();
        }
    }

    /**
     * Remove a {@link monsters.Monster monster} from the team
     *
     * @param index index of {@link monsters.Monster monster} to be removed
     * @throws TeamSizeException team is at minimum size
     */
    public void removeMonster(int index) throws TeamSizeException {
        if (monsters.size() == MINTEAMSIZE) {
            throw new TeamSizeException("Team must have at least " + MINTEAMSIZE + " monster");
        } else {
            monsters.remove(index);
            teamSize = monsters.size();
        }
    }

    /**
     * Move a {@link monsters.Monster monster} up a position in the team
     *
     * @param monster {@link monsters.Monster monster} to be moved
     */
    public void moveMonsterUp(Monster monster) {
        int currentIndex = monsters.indexOf(monster);
        if (currentIndex > 0) {
            monsters.remove(monster);
            monsters.add(--currentIndex, monster);
        }
    }

    /**
     * Move a {@link monsters.Monster monster} down a position in the team
     *
     * @param monster {@link monsters.Monster monster} to be moved
     */
    public void moveMonsterDown(Monster monster) {
        int currentIndex = monsters.indexOf(monster);
        if (currentIndex < this.teamSize - 1) {
            monsters.remove(monster);
            monsters.add(++currentIndex, monster);
        }
    }

    /**
     * Gets number of {@link monsters.Monster monsters} in the team
     *
     * @return number of {@link monsters.Monster monsters} in the team
     */
    public int getTeamSize() {
        return this.monsters.size();
    }

    /**
     * Get max team size
     *
     * @return maximum team size
     */
    public static int getMaxTeamSize() {
        return Team.MAXTEAMSIZE;
    }

    /**
     * Get min team size
     *
     * @return minimum team size
     */
    public static int getMinTeamSize() {
        return Team.MINTEAMSIZE;
    }

    /**
     * Performs a deep copy of the team, also making copies of all
     * {@link monsters.Monster monsters}
     */
    public Object clone() throws CloneNotSupportedException {
        try {
            Team teamCopy = (Team) super.clone();
            teamCopy.monsters = new ArrayList<Monster>();

            for (Monster m : this.monsters) {
                teamCopy.addMonster((Monster) m.clone());
            }

            return teamCopy;
        } catch (TeamSizeException | DuplicateMonsterException e) { // won't happen since implements Cloneable
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String toString() {
        String outputString = "";
        for (int i = 0; i < this.getTeamSize(); i++) {
            outputString += this.getMonsters().get(i).getName();
            if (i != this.getTeamSize() - 1) {
                outputString += "  ";
            }
        }

        return outputString;
    }
}
