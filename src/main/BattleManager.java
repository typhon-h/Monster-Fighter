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
    // TODO: use global random variable instead
    private ArrayList<BattleEvent> eventLog = new ArrayList<BattleEvent>();
    private int currentEventIndex = 0;

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
        switch (GameEnvironment.rng.nextInt(monsters.Monster.NUMMONSTERS)) {
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
        Trigger selectedTrigger = possibleTriggers[GameEnvironment.rng.nextInt(possibleTriggers.length)];
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
     * @param allyTeam     A copy of the ally team to fight
     * @param opponentTeam A copy of the enemy team to fight
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

            Monster monster1 = team1.getFirstAliveMonster();
            Monster monster2 = team2.getFirstAliveMonster();

            // Does damage
            monster2.takeDamage(monster1.getCurrentAttackDamage());

            String description = monster1.getName() + " dealt " + monster1.getCurrentAttackDamage() + " damage to "
                    + monster2.getName();
            BattleEvent ability;

            // Check BEFOREATTACK Trigger
            ability = runAbility(team1, team2, monster1, Trigger.BEFOREATTACK);
            if (ability != null) {
                eventLog.add(ability);
            }

            Trigger trigger;
            if (!monster2.getStatus()) {
                trigger = Trigger.ONFAINT;
                description += ". " + monster2.getName() + " fainted.";
            } else {
                trigger = Trigger.ONHURT;
            }

            // Fight event
            eventLog.add(new BattleEvent(allyTeam, opponentTeam, description));
            // Check ONHURT/ONFAINT Trigger
            ability = runAbility(team2, team1, monster2, trigger);
            if (ability != null) {
                eventLog.add(ability);
            }

            // Check AFTERATTACK trigger
            ability = runAbility(team1, team2, monster1, Trigger.AFTERATTACK);
            if (ability != null) {
                eventLog.add(ability);
            }

        }

        return eventLog;
    }

    /**
     * Should return a BattleEvent object not void
     *
     * @param allyTeam  The ally team respective to the monster
     * @param enemyTeam The enemy team respective to the monster
     * @param monster   Monster to run ability of
     * @param trigger   Current trigger that is checked for
     */
    private BattleEvent runAbility(Team allyTeam, Team enemyTeam, Monster monster, Trigger trigger) {
        if (monster.getTrigger() == trigger) {
            return monster.ability(allyTeam, enemyTeam);
        }

        return null;
    }

    /**
     * Simulates the battle and sets the eventLog of the battleManager with the
     * events that occurred during the battle.
     */
    public void simulateBattle() {
        ArrayList<BattleEvent> newEventLog = new ArrayList<BattleEvent>();

        Team allyTeamCopy;
        Team opponentTeamCopy;

        try {
            allyTeamCopy = (Team) allyPlayer.getTeam().clone();
            opponentTeamCopy = (Team) currentOpponent.getTeam().clone();

            // Check for START OF BATTLE triggers for ally team then opponent team
            for (Monster monster : allyTeamCopy.getAliveMonsters()) {
                newEventLog.add(runAbility(allyTeamCopy, opponentTeamCopy, monster, Trigger.STARTOFBATTLE));
            }

            for (Monster monster : opponentTeamCopy.getAliveMonsters()) {
                newEventLog.add(runAbility(opponentTeamCopy, allyTeamCopy, monster, Trigger.STARTOFBATTLE));
            }

            while (!allyTeamCopy.getAliveMonsters().isEmpty() && !opponentTeamCopy.getAliveMonsters().isEmpty()) {
                newEventLog.addAll(fight(allyTeamCopy, opponentTeamCopy));
            }

        } catch (CloneNotSupportedException e) { // Should never happen as clone is implemented
            e.printStackTrace();
        }

        this.eventLog = newEventLog;
        this.currentEventIndex = 0;
    }

    /**
     * Gets the next event in the event log.
     *
     * @return Next available event in the event log or null if there is no event.
     */
    public BattleEvent nextEvent() {
        if (this.currentEventIndex < this.eventLog.size()) {
            return this.eventLog.get(this.currentEventIndex++);
        }

        return null;
    }

    /**
     * Gets the player in the BattleManager
     */
    public Player getPlayer() {
        return this.allyPlayer;
    }
}
