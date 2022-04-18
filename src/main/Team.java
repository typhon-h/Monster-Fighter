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
    public Team(Monster monster) {
        this.monsters = new ArrayList<Monster>();
        this.monsters.add(monster);
        this.teamSize = 1;
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     */
    public Team(Monster monster1, Monster monster2) {
        this.monsters = new ArrayList<Monster>();
        this.monsters.add(monster1);
        this.monsters.add(monster2);
        this.teamSize = 2;
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * @param monster3 monster in third position
     */
    public Team(Monster monster1, Monster monster2, Monster monster3) {
        this.monsters = new ArrayList<Monster>();
        this.monsters.add(monster1);
        this.monsters.add(monster2);
        this.monsters.add(monster3);
        this.teamSize = 3;
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * @param monster3 monster in third position
     * @param monster4 monster in fourth position
     */
    public Team(Monster monster1, Monster monster2, Monster monster3,
            Monster monster4) {
        this.monsters = new ArrayList<Monster>();
        this.monsters.add(monster1);
        this.monsters.add(monster2);
        this.monsters.add(monster3);
        this.monsters.add(monster4);
        this.teamSize = 4;
    }

    /**
     * Constructor for Team. Creates a team with the starting monster
     *
     * @param monster1 monster in first position
     * @param monster2 monster in second position
     * @param monster3 monster in third position
     * @param monster4 monster in fourth position
     * @param monster5 monster in fifth position
     */
    public Team(Monster monster1, Monster monster2, Monster monster3,
            Monster monster4, Monster monster5) {
        this.monsters = new ArrayList<Monster>();
        this.monsters.add(monster1);
        this.monsters.add(monster2);
        this.monsters.add(monster3);
        this.monsters.add(monster4);
        this.monsters.add(monster5);
        this.teamSize = 5;
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
     */
    public Team(Monster monster1, Monster monster2, Monster monster3,
            Monster monster4, Monster monster5, Monster monster6) {
        this.monsters = new ArrayList<Monster>();
        this.monsters.add(monster1);
        this.monsters.add(monster2);
        this.monsters.add(monster3);
        this.monsters.add(monster4);
        this.monsters.add(monster5);
        this.monsters.add(monster6);
        this.teamSize = 6;
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
     * Get all alive monsters
     * 
     * @return ArrayList of alive monsters
     */
    public ArrayList<Monster> getAliveMonsters() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        for (Monster monster : this.monsters) {
            if (monster.getStatus()) {
                monsters.add(monster);
            }
        }
        return monsters;
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
