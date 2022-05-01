package main;

import java.util.ArrayList;
import java.util.Random;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;

import items.ItemConstants;
import items.RandomStatBoost;
import monsters.*;

import static main.Difficulty.getDifficultyMultiplier;

/**
 * A class for managing all the battles and functionality of battles
 * which a player can participate in.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public class BattleManager {
    private Player allyPlayer;
    private ArrayList<Player> opponents;
    private Player currentOpponent = null;
    private BattleResult battleResult = BattleResult.NULL;
    private static Random rng = new Random();

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
     * @return A {@link monsters.Monster Monster} with a random trigger.
     */
    private Monster getRandomMonster(Difficulty difficulty) {

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
         * Get the optimal triggers for the monster, select a random one
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
     * @return A {@link main.Team Team} containing monsters.
     */
    public Team generateTeam(int currentDay, int maxDays, Difficulty difficulty) {
        // TODO: change team size and update tests
        int teamSize = (int) Math.ceil(((double) currentDay * (double) Team.getMaxTeamSize()) / (double) maxDays);

        // Create a team with a size based on the current
        Team team = null;
        for (int i = 0; i < teamSize; i++) {
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
        }

        // Use boost item on random monsters in the team
        int totalPoints = (int) (Difficulty.getDifficultyMultiplier(difficulty) *
                Math.ceil((float) ItemConstants.AVERAGEBOOSTPERBUYPRICE *
                        (float) allyPlayer.getGold() +
                        (float) allyPlayer.getItemPoints()));
        int expendedPoints = 0;
        RandomStatBoost boost = new RandomStatBoost("Boost", "Desc", Rarity.COMMON);

        while (expendedPoints < totalPoints) {
            int MonsterIndex = rng.nextInt(team.getTeamSize());
            Monster monsterToUseItemOn = team.getMonsters().get(MonsterIndex);
            boost.use(monsterToUseItemOn);

            expendedPoints += ItemConstants.COMMONSTATBOOST;
        }

        return team;
    }

    /**
     * Generates the different opponents what the player can battle against.
     *
     * @param currentDay The current day of the game.
     * @param maxDays    The maximum number of days any game can last.
     * @param difficulty The difficulty of the current game.
     */
    // TODO: change all Difficulty to use difficulty constant in GameEnvironment
    public void generateOpponents(int currentDay, int maxDays, Difficulty difficulty) {
        // Reset current opponent to null.
        currentOpponent = null;

        // TODO: change magic numbers to constants
        // gold = starting gold + goldPerDay * reverse of difficulty multiplier *
        // currentDay
        int gold = (int) (30f + (30f * (1f + 1f - getDifficultyMultiplier(difficulty)) * currentDay));
        // points = basePoints * 1.1 ^ day * difficulty multiplier
        int points = (int) Math
                .round(100f * (float) Math.pow(1.1, (double) currentDay) * getDifficultyMultiplier(difficulty));

        ArrayList<Player> newOpponents = new ArrayList<Player>();
        for (int i = 0; i < BattleConstants.NUMOPPONENTS; i++) {
            Team team = generateTeam(currentDay, maxDays, difficulty);
            Player newOpponent = new Player(team, gold);
            newOpponent.incrementScore(points);

            newOpponents.add(newOpponent);
        }

        this.opponents = newOpponents;
    }

    /**
     * Gets the possible opponents which the player can face against.
     *
     * @return A list of {@link main.Player Opponents} that the player can face
     *         against.
     */
    public ArrayList<Player> getOpponents() {
        return opponents;
    }

    /**
     * Sets the {@link main.Player opponent} which the {@link main.Player player} is
     * battling against
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
    private ArrayList<BattleEvent> fight(Monster allyMonster, Monster enemyMonster) {
        ArrayList<BattleEvent> eventLog = new ArrayList<BattleEvent>();

        Monster firstMonster;
        Monster secondMonster;
        if (allyMonster.getSpeed() >= enemyMonster.getSpeed()) {
            firstMonster = allyMonster;
            secondMonster = enemyMonster;
        } else {
            firstMonster = enemyMonster;
            secondMonster = allyMonster;
        }

        Monster[][] monsterArray = new Monster[][] {
                { firstMonster, secondMonster },
                { secondMonster, firstMonster } };

        for (Monster[] monster : monsterArray) {
            Monster monster1 = monster[0];
            Monster monster2 = monster[1];

            monster2.takeDamage(monster1.getCurrentAttackDamage());

            String description = monster1.getName() + " dealt " + monster1.getCurrentAttackDamage() + " damage to "
                    + monster2.getName();
            Trigger trigger;

            if (!monster2.getStatus()) {
                trigger = Trigger.ONFAINT;
                eventLog.add(new BattleEvent(allyPlayer.getTeam(), currentOpponent.getTeam(),
                        description + ". " + monster2.getName() + " fainted."));
            } else {
                trigger = Trigger.ONHURT;
                eventLog.add(new BattleEvent(allyPlayer.getTeam(), currentOpponent.getTeam(), description));
            }

            BattleEvent ability = runAbility(monster2, trigger);
            if (ability != null) {
                eventLog.add(ability);
            }

        }

        return eventLog;
    }

    /**
     * Should return a BattleEvent object not void
     *
     * @param monster Monster to run ability on
     * @param trigger Current trigger that is checked for
     */
    private BattleEvent runAbility(Monster monster, Trigger trigger) {

        if (monster.getTrigger() == trigger) {
            return monster.ability(allyPlayer.getTeam(), currentOpponent.getTeam());
        }

        return null;
    }

    /**
     * Simulates the battle then returns a history log of all the event that
     * occurred during the battle. A ArrayList<BattleEvent> object should be
     * returned, not void
     */
    public ArrayList<BattleEvent> simulateBattle() {
        /**
         * 1. BEFOREATTACK trigger
         * 2. fight
         * 5. After attack trigger
         */

        // Check BEFOREATTACK trigger
        // eventLog.addAll(runAbility(monster1, Trigger.BEFOREATTACK));

        // // Check AFTERATTACK trigger
        // eventLog.addAll(runAbility(monster1, Trigger.AFTERATTACK));
    }

    /**
     * Gets the next event from the event log after simulating the battle.
     * Should return a BattleEvent object, no void
     */
    public BattleEvent nextEvent() {

    }

    /**
     * Gets the player in the BattleManager
     */
    public Player getPlayer() {
        return this.allyPlayer;
    }
}
