package main;

import java.util.ArrayList;
import java.util.Random;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;
import monsters.*;

/**
 * A class for managing all the battles and functionality of battles
 * which a player can prticipate in.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public class BattleManager {
    Player allyPlayer;
    ArrayList<Player> opponents;
    Player currentOpponent = null;
    BattleResult battleResult = BattleResult.NULL;

    /**
     * Constructor to for BattleManager, creates a new BattleManager
     * for a given player.
     *
     * @param player The player which the BattleManager is for.
     */
    public BattleManager(Player player) {
        allyPlayer = player;
    }

    /**
     * Generates a random monster with different triggers based
     * on the difficulty of the game.
     *
     * @param difficulty The difficulty of the game.
     * @return           A {@link monsters.Monster Monster} with a random trigger.
     */
    private Monster getRandomMonster(Difficulty difficulty) {
        Random rng = new Random();

        Monster monster = null;
        switch (rng.nextInt(monsters.Monster.NUMMONSTERS)) {
            case 0:
                monster = new ClinkMonster();
                break;
            case 1:
                monster = new DittaMonster();
                break;
            case 2:
                monster = new GilMonster();
                break;
            case 3:
                monster = new JynxMonster();
                break;
            case 4:
                monster = new LuciferMonster();
                break;
            case 5:
                monster = new TeddyMonster();
                break;
            default:
                monster = new ClinkMonster();
                break;
        }

        /*
         * Get the optimal triggers for the mosnter, select a random one
         * and apply it to the monster.
         */
        Trigger[] possibleTriggers = BattleConstants.getTriggers(monster, difficulty);
        Trigger selectedTrigger = possibleTriggers[rng.nextInt(possibleTriggers.length)];
        monster.setTrigger(selectedTrigger);

        return monster;
    }

    /**
     * Generates a team of monsters.
     *
     * @param currentDay The current day in the game.
     * @param maxDays    The maximum number of days in the game.
     * @param difficulty The difficulty of the game.
     * @return           A {@link main.Team Team} containing monsters.
     */
    private Team generateTeam(int currentDay, int maxDays, Difficulty difficulty) {
        int teamSize = (int) Math.ceil(currentDay / maxDays) * Team.getMaxTeamSize();

        Team team = null;
        for (int i=0; i<teamSize; i++) {
            Monster monster = getRandomMonster(difficulty);
            try {
                if (team == null) {
                    team = new Team(monster);
                } else {
                    team.addMonster(monster);
                }
            } catch (TeamSizeException e) {
                e.printStackTrace();
            } catch (DuplicateMonsterException e) {
                e.printStackTrace();
            }

            // Add items to monsters
        }

        return team;
    }

    /**
     * Generates the different oponents what the player can battle against.
     *
     * @param currentDay The current day of the game.
     * @param maxDays    The maximum number of days any game can last.
     * @param difficulty The difficulty of the current game.
     */
    public void generateOpponents(int currentDay, int maxDays, Difficulty difficulty) {
        /*
         * Math.ceil(CurrentDay / AbsolouteTotalDays) * 6
         *
         * Number of items available: TotalBoostAmount player has + average boost / gold * gold
         * -> Give opponents % player boost amount scaled on difficulty.
         */

        for (int i=0; i<BattleConstants.NUMOPPONENTS; i++) {
            // Generate team, generate gold -> generate player -> add player to opponets
            Team team = generateTeam(currentDay, maxDays, difficulty);
            // TODO: opponent gold should be based on day and difficulty multiplier.
            // TODO: opponent points should be based on day and difficulty.
        }
    }

    /**
     * Gets the possible opponents which the player can face against.
     *
     * @return A list of {@link main.Player Opponents} that the player can face against.
     */
    public ArrayList<Player> getOpponents() {
        return opponents;
    }

    /**
     * Sets the {@link main.Player opponent} which the {@link main.Player player} is battling against
     *
     * @param opponent The selected {@link main.Player opponent} to battle against
     */
    public void setOpponent(Player opponent) {
        currentOpponent = opponent;
        opponents.remove(opponent);
    }

    /**
     * Distributes rewards to the player if the player has defeated the opponent.
     */
    public void giveRewards() {
        if (battleResult == BattleResult.WIN && currentOpponent != null) {
            allyPlayer.addGold(currentOpponent.getGold());
            allyPlayer.incrementScore(currentOpponent.getScore());
        }

        battleResult = BattleResult.NULL;
    }

    /**
     * Should return a BattleEvent object not void
     *
     * @param allyMonster  Ally monster that is currently fighting
     * @param enemyMonster Enemy monster that is currently fighting
     */
    private BattleEvent fight(Monster allyMonster, Monster enemyMonster) {

    }

    /**
     * Should return a BattleEvent object not void
     *
     * @param monster Monster to run ability on
     * @param trigger Current trigger that is checked for
     */
    private BattleEvent runAbility(Monster monster, Trigger trigger) {

    }

    /**
     * Simulates the battle then returns a history log of all the event that
     * occured during the battle. A ArrayList<BattleEvent> object should be returned, not void
     */
    public ArrayList<BattleEvent> simulateBattle() {

    }


    /**
     * Gets the next event from the event log after simulating the battle.
     * Should return a BattleEvent object, no void
     */
    public BattleEvent nextEvent() {

    }
}
