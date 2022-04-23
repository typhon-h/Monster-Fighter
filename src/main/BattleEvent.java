package main;

import monsters.Monster;

/**
 * A class to store snapshots of events that occur during
 * a battle between the player and an opponent.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public class BattleEvent {
    /**
     * {@link monsters.Monster Monster} which has been affected with it's updated
     * variables
     */
    public Monster monster;
    /**
     * Index/Position of the {@link monsters.Monster monster} that was affected
     */
    public int monsterIndex;
    /**
     * The team in which the affected {@link monsters.Monster monster} belongs to
     */
    public boolean teamToAdjust;
    /**
     * Description of the event that occured
     */
    public String description;

    /**
     * Constructor for BattleEvent. Sets the object's variables
     *
     * @param newMonster      The {@link monsters.Monster Monster} that was affected
     * @param newMonsterIndex The index of the {@link monsters.Monster Monster} that was affected
     * @param newTeamToAdjust The team in which the affected {@link monsters.Monster Monster} was in
     * @param newDescription  The description of the event that occured
     */
    public BattleEvent(Monster newMonster, int newMonsterIndex,
                       boolean newTeamToAdjust, String newDescription) {
        monster = newMonster;
        monsterIndex = newMonsterIndex;
        teamToAdjust = newTeamToAdjust;
        description = newDescription;
    }
}
