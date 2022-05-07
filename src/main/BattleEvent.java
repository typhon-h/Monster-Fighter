package main;

/**
 * A class to store snapshots of events that occur
 * during
 * a battle between the {@link main.Player player} and an opponent.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
public class BattleEvent {
    /**
     * snapshot of ally {@link main.Team Team}
     */
    private Team allyTeam;
    /**
     * snapshot of enemy {@link main.Team Team}
     */
    private Team opponentTeam;
    /**
     * Description of the event that occurred
     */
    private String description;

    /**
     * Constructor for BattleEvent. Sets the object's instance variables
     *
     * @param newAllyTeam     Ally {@link main.Team team} to take snapshot of
     * @param newOpponentTeam Enemy {@link main.Team team} to take snapshot of
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

    public Team getAllyTeam() {
        return allyTeam;
    }

    public Team getOpponentTeam() {
        return opponentTeam;
    }

    public String getDescription() {
        return description;
    }
}
