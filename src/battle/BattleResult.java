package battle;

/**
 * Different results which can occur from a battle.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public enum BattleResult {
    /**
     * Battle result has not been decided
     */
    NULL,
    /**
     * {@link main.Player Player} has won the battle
     */
    WIN,
    /**
     * {@link main.Player Player} has lost the battle
     */
    LOSS
}
