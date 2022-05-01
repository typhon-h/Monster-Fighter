package main;

/**
 * A class to store snapshots of events that occur during
 * a battle between the player and an opponent.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
public class BattleEvent {
    /**
     * snapshot of ally {@link main.Team Team}
     */
    public Team allyTeam;
    /**
     * snapshot of enemy {@link main.Team Team}
     */
    public Team opponentTeam;
    /**
     * Description of the event that occurred
     */
    public String description;

    /**
     * Constructor for BattleEvent. Sets the object's variables
     *
     * @param newMonster      The {@link monsters.Monster Monster} that was affected
     * @param newTeamToAdjust The team in which the affected {@link monsters.Monster Monster} was in
     * @param newDescription  The description of the event that occurred
     */

    /**
     * Constructor for BattleEvent. Sets the object's instance variables
     *
     * @param newAllyTeam     Ally team to take snapshot of
     * @param newOpponentTeam Enemy team to take snapshot of
     * @param newDescription  Description of the event that occurred
     */
    public BattleEvent(Team newAllyTeam, Team newOpponentTeam,
                       String newDescription) {

        try {
            this.allyTeam = (Team) newAllyTeam.clone();
            this.opponentTeam = (Team) newOpponentTeam.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        this.description = newDescription;
    }
}
