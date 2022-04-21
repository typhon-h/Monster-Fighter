package main;

import java.util.ArrayList;
import exceptions.*;
import monsters.Monster;

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
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    /**
     * Current size of the team
     */
    private int teamSize = monsters.size();

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster starting monster
     * 
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException monster already in team
     */
    public Team(Monster monster) throws TeamSizeException, DuplicateMonsterException {
        this.addMonster(monster);
        teamSize = monsters.size();
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * 
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException monster already in team
     */
    public Team(Monster monster1, Monster monster2) throws TeamSizeException, DuplicateMonsterException {
        this.addMonster(monster1);
        this.addMonster(monster2);
        teamSize = monsters.size();
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * @param monster3 monster in third position
     * 
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException monster already in team
     */
    public Team(Monster monster1, Monster monster2, Monster monster3)
            throws TeamSizeException, DuplicateMonsterException {
        this.addMonster(monster1);
        this.addMonster(monster2);
        this.addMonster(monster3);
        teamSize = monsters.size();
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * @param monster3 monster in third position
     * @param monster4 monster in fourth position
     * 
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException monster already in team
     */
    public Team(Monster monster1, Monster monster2, Monster monster3,
            Monster monster4) throws TeamSizeException, DuplicateMonsterException {
        this.addMonster(monster1);
        this.addMonster(monster2);
        this.addMonster(monster3);
        this.addMonster(monster4);
        teamSize = monsters.size();
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * @param monster3 monster in third position
     * @param monster4 monster in fourth position
     * @param monster5 monster in fifth position
     * 
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException monster already in team
     */
    public Team(Monster monster1, Monster monster2, Monster monster3,
            Monster monster4, Monster monster5) throws TeamSizeException, DuplicateMonsterException {
        this.addMonster(monster1);
        this.addMonster(monster2);
        this.addMonster(monster3);
        this.addMonster(monster4);
        this.addMonster(monster5);
        teamSize = monsters.size();
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * @param monster3 monster in third position
     * @param monster4 monster in fourth position
     * @param monster5 monster in fifth position
     * @param monster6 monster in sixth position
     * 
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException monster already in team
     */
    public Team(Monster monster1, Monster monster2, Monster monster3,
            Monster monster4, Monster monster5, Monster monster6) throws TeamSizeException, DuplicateMonsterException {
        this.addMonster(monster1);
        this.addMonster(monster2);
        this.addMonster(monster3);
        this.addMonster(monster4);
        this.addMonster(monster5);
        this.addMonster(monster6);
        teamSize = monsters.size();
    }

    /**
     * Gets the first monster in the team that has not fainted
     *
     * @return the first available monster
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
     * Get all alive monsters
     * 
     * @return ArrayList of alive monsters
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
     * Gets the current monsters in the team
     *
     * @return ArrayList of current monsters in team
     */
    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    /**
     * Adds a monster to the team if there is space
     *
     * @param monster monster to be added
     * @throws TeamSizeException         team is full
     * @throws DuplicateMonsterException monster already in team
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
     * Remove a monster from the team
     *
     * @param monster monster to be removed
     * @throws TeamSizeException team is at minimum size
     */
    public void removeMonster(Monster monster) throws TeamSizeException {
        if (monsters.size() == MINTEAMSIZE) {
            throw new TeamSizeException("Team must have at least " + MINTEAMSIZE + " monster");
        } else {
            monsters.remove(monster);
            teamSize = monsters.size();
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
        if (currentIndex < this.teamSize - 1) {
            monsters.remove(monster);
            monsters.add(++currentIndex, monster);
        }
    }

    /**
     * Gets number of monsters in the team
     * 
     * @return teamSize - number of monsters in the team
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
}
