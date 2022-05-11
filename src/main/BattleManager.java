package main;

import java.util.ArrayList;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;

import items.ItemConstants;
import items.RandomStatBoost;
import monsters.*;

import static main.Difficulty.getDifficultyMultiplier;

/**
 * A class for managing all the battles and functionality of battles
 * which a {@link main.Player player} can participate in.
 *
 * @author Jackie Jone
 * @version 1.1, Apr 2022.
 */
public class BattleManager {

    /**
     * The {@link main.Player player} participating the battle
     */
    private Player allyPlayer;
    /**
     * List of opponents for the {@link main.Player player} to chose from
     */
    private ArrayList<Player> opponents;
    /**
     * Opponent {@link main.Player player} will battle against
     *
     * @default null
     */
    private Player currentOpponent = null;
    /**
     * Result of the battle
     *
     * @default BattleResult.NULL
     */
    private BattleResult battleResult = BattleResult.NULL;
    /**
     * ArrayList of BattleEvents detailing what happened during battle
     */
    private ArrayList<BattleEvent> eventLog = new ArrayList<BattleEvent>();
    /**
     * Current position in the event log
     */
    private int currentEventIndex = 0;

    /**
     * Constructor to for BattleManager, creates a new BattleManager
     * for a given player.
     *
     * @param player The player which the BattleManager is for.
     */
    public BattleManager(Player player, Difficulty difficulty, int maxDays) {
        allyPlayer = player;
        generateOpponents(1, maxDays, difficulty);
    }

    /**
     * Generates a random {@link monsters.Monster mosnter} with different
     * {@link main.Trigger triggers} based
     * on the {@link main.Difficulty difficulty} of the game.
     *
     * @param difficulty The {@link main.Difficulty difficulty} of the game.
     * @return A {@link monsters.Monster Monster} with a random {@link main.Trigger
     *         trigger}.
     */
    private Monster getRandomMonster(Difficulty difficulty) {

        Monster monster = null;
        monster = GameEnvironment.generateMonsters().get(GameEnvironment.rng.nextInt(Monster.NUMMONSTERS));

        /*
         * Get the optimal triggers for the monster, select a random one
         * and apply it to the monster.
         */
        Trigger[] possibleTriggers = BattleConstants.getTriggers(monster.getClass(), difficulty);
        Trigger selectedTrigger = possibleTriggers[GameEnvironment.rng.nextInt(possibleTriggers.length)];
        monster.setTrigger(selectedTrigger);

        return monster;
    }

    /**
     * Generates a {@link main.Team team} of {@link monsters.Monster monsters}.
     *
     * @param currentDay The current day in the game.
     * @param maxDays    The maximum number of days in the game.
     * @param difficulty The {@link main.Difficulty difficulty} of the game.
     * @return A {@link main.Team Team} containing {@link monsters.Monster
     *         monsters}.
     */
    public Team generateTeam(int currentDay, int maxDays, Difficulty difficulty) {
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
        // TODO: Fix this \/ so its not as broken
        // Use boost item on random monsters in the team
        int totalPoints = (int) (Difficulty.getDifficultyMultiplier(difficulty) *
                      Math.floor((float) ItemConstants.AVERAGEBOOSTPERBUYPRICE *
                                (float) allyPlayer.getItemPoints()));
        int expendedPoints = 0;
        RandomStatBoost boost = new RandomStatBoost(Rarity.COMMON);

        while (expendedPoints < totalPoints) {
            int MonsterIndex = GameEnvironment.rng.nextInt(team.getTeamSize());
            Monster monsterToUseItemOn = team.getMonsters().get(MonsterIndex);
            boost.use(monsterToUseItemOn);

            expendedPoints += ItemConstants.COMMONSTATBOOST;
        }

        return team;
    }

    /**
     * Generates the different {@link main.Player opponent} which the
     * {@link main.Player player} can
     * battle against.
     *
     * @param currentDay The current day of the game.
     * @param maxDays    The maximum number of days any game can last.
     * @param difficulty The {@link main.Difficulty difficulty} of the current game.
     */
    public void generateOpponents(int currentDay, int maxDays, Difficulty difficulty) {
        battleResult = BattleResult.NULL;
        // Reset current opponent to null.
        currentOpponent = null;

        // TODO: change magic numbers to constants
        // gold = starting gold + goldPerDay * inverse of difficulty multiplier *
        // currentDay
        int gold = (int) (30f + (30f * (1f + 1f - getDifficultyMultiplier(difficulty)) * currentDay));
        // points = basePoints * 1.1 ^ day * difficulty multiplier
        int points = (int) Math
                .round(100f * (float) Math.pow(1.1, (double) currentDay) * getDifficultyMultiplier(difficulty));

        ArrayList<Player> newOpponents = new ArrayList<Player>();
        for (int i = 0; i < BattleConstants.NUMOPPONENTS; i++) {
            Team team = generateTeam(currentDay, maxDays, difficulty);
            Player newOpponent = new Player("Battle " + (i + 1), team, gold);
            newOpponent.incrementScore(points);

            newOpponents.add(newOpponent);
        }

        this.opponents = newOpponents;
    }

    /**
     * Gets the {@link main.Player opponents} which the {@link main.Player player}
     * can face
     * against.
     *
     * @return A list of {@link main.Player Opponents} that the {@link main.Player
     *         player} can face against.
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
        if (!opponents.remove(opponent)) {
            System.out.println("Opponent does not exist");
        }

    }

    /**
     * Distributes rewards to the {@link main.Player player} if the
     * {@link main.Player player} has defeated the {@link main.Player opponent}.
     */
    public void giveRewards() {
        if (battleResult == BattleResult.WIN && currentOpponent != null) {
            allyPlayer.addGold(currentOpponent.getGold());
            allyPlayer.incrementScore(currentOpponent.getScore());
        }
    }

    /**
     * Two {@link main.Team teams} fight by having first {@link monsters.Monster
     * monsters} deal damage to each other
     *
     * @param allyTeam     A copy of the ally {@link main.Team team} to fight
     * @param opponentTeam A copy of the enemy {@link main.Team team} to fight
     *
     * @return ArrayList of {@link main.BattleEvent BattleEvents} describing what
     *         happened
     */
    private ArrayList<BattleEvent> fight(Team allyTeam, Team opponentTeam) {
        ArrayList<BattleEvent> eventLog = new ArrayList<BattleEvent>();

        // Find fastest monster at the front of the team
        Team firstTeam;
        Team secondTeam;
        if (allyTeam.getFirstAliveMonster().getSpeed() >= opponentTeam.getFirstAliveMonster().getSpeed()) {
            firstTeam = allyTeam;
            secondTeam = opponentTeam;
        } else {
            firstTeam = opponentTeam;
            secondTeam = allyTeam;
        }

        // Run the monster attack in order of fastest speed
        Team[][] teamOrder = new Team[][] {
                { firstTeam, secondTeam },
                { secondTeam, firstTeam } };

        for (Team[] team : teamOrder) {
            Team team1 = team[0];
            Team team2 = team[1];

            Monster attackingMonster = team1.getFirstAliveMonster();
            Monster receivingMonster = team2.getFirstAliveMonster();

            BattleEvent ability;
            // Check BEFOREATTACK Trigger

            ability = runAbility(allyTeam.getMonsters().contains(attackingMonster), team1, team2,
                    attackingMonster, Trigger.BEFOREATTACK);
            if (ability != null) {
                eventLog.add(ability);
            }

            // Does damage
            receivingMonster.takeDamage(attackingMonster.getCurrentAttackDamage());

            String description = attackingMonster.getName() +
                    " dealt " +
                    attackingMonster.getCurrentAttackDamage() +
                    " damage to " +
                    receivingMonster.getName();

            Trigger trigger;
            if (!receivingMonster.getStatus()) {
                trigger = Trigger.ONFAINT;
                description += ". " + receivingMonster.getName() + " fainted.";
            } else {
                trigger = Trigger.ONHURT;
            }

            // Fight event
            eventLog.add(new BattleEvent(allyTeam, opponentTeam, description));
            // Check ONHURT/ONFAINT Trigger
            ability = runAbility(allyTeam.getMonsters().contains(receivingMonster), team2, team1,
                    receivingMonster, trigger);
            if (ability != null) {
                eventLog.add(ability);
            }

            // Check AFTERATTACK trigger
            ability = runAbility(allyTeam.getMonsters().contains(attackingMonster), team1, team2,
                    attackingMonster, Trigger.AFTERATTACK);
            if (ability != null) {
                eventLog.add(ability);
            }

            if (team1.getAliveMonsters().isEmpty() ||
                    team2.getAliveMonsters().isEmpty()) {
                return eventLog;
            }
        }

        return eventLog;
    }

    /**
     * Checks {@link monsters.Monster monster} has a {@link main.Team team} and runs
     * their {@link monsters.Monster#ability}
     *
     * @param allyTeam  The ally {@link main.Team team} respective to the
     *                  {@link monsters.Monster monster}
     * @param enemyTeam The enemy {@link main.Team team} respective to the
     *                  {@link monsters.Monster monster}
     * @param monster   {@link monsters.Monster Monster} to run
     *                  {@link monsters.Monster#ability} of
     * @param trigger   Current {@link main.Trigger trigger} that is checked for
     *
     * @return {@link main.BattleEvent BattleEvent} describing the
     *         {@link monsters.Monster#ability}
     */
    private BattleEvent runAbility(boolean isPlayer, Team allyTeam, Team enemyTeam, Monster monster,
            Trigger trigger) {
        if (monster.getTrigger() == trigger) {
            return monster.ability(isPlayer, allyTeam, enemyTeam);
        }

        return null;
    }

    /**
     * Simulates the battle and sets the eventLog of the battleManager with the
     * {@link main.BattleEvent events} that occurred during the battle.
     */
    public void simulateBattle() {
        battleResult = BattleResult.NULL;
        ArrayList<BattleEvent> newEventLog = new ArrayList<BattleEvent>();
        Team allyTeamCopy;
        Team opponentTeamCopy;

        try {
            allyTeamCopy = (Team) allyPlayer.getTeam().clone();
            opponentTeamCopy = (Team) currentOpponent.getTeam().clone();

            BattleEvent ability;
            // Check for START OF BATTLE triggers for ally team then opponent team
            for (Monster monster : allyTeamCopy.getAliveMonsters()) {
                ability = runAbility(true, allyTeamCopy, opponentTeamCopy, monster, Trigger.STARTOFBATTLE);
                if (ability != null) {
                    newEventLog.add(ability);
                }
            }

            for (Monster monster : opponentTeamCopy.getAliveMonsters()) {
                ability = runAbility(false, opponentTeamCopy, allyTeamCopy, monster, Trigger.STARTOFBATTLE);
                if (ability != null) {
                    newEventLog.add(ability);
                }
            }

            while (!allyTeamCopy.getAliveMonsters().isEmpty() && !opponentTeamCopy.getAliveMonsters().isEmpty()) {
                newEventLog.addAll(fight(allyTeamCopy, opponentTeamCopy));
            }

            if (allyTeamCopy.getAliveMonsters().isEmpty()) {
                battleResult = BattleResult.LOSS;
            } else {
                battleResult = BattleResult.WIN;
            }

        } catch (CloneNotSupportedException e) { // Should never happen as clone is implemented
            e.printStackTrace();
        }

        this.eventLog = newEventLog;
        this.currentEventIndex = 0;
    }

    /**
     * Gets the next {@link main.BattleEvent event} in the event log.
     *
     * @return Next available {@link main.BattleEvent event} in the event log or
     *         null if there is no event.
     */
    public BattleEvent nextEvent() {
        if (this.currentEventIndex < this.eventLog.size()) {
            BattleEvent event = this.eventLog.get(this.currentEventIndex++);
            return event;
        }

        return null;
    }

    /**
     * Gets the {@link main.Player player} in the BattleManager.
     *
     * @return the active {@link main.Player player}.
     */
    public Player getPlayer() {
        return this.allyPlayer;
    }

    /**
     * Gets the current {@link main.Player opponent} in the BattleManager.
     *
     * @return the current selected {@link main.Player opponent}.
     */
    public Player getCurrOpponent() {
        return this.currentOpponent;
    }

    /**
     * Gets the result of a battle after the battle has been simulated
     *
     * @return Enum value defining wether the player has won or lost.
     */
    public BattleResult getResult() {
        return battleResult;
    }
}
