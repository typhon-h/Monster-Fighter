package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;
import monsters.Monster;

/**
 * Interface to communicate between a user and the game environment.
 * 
 * @author Harrison Tyson
 * @version 1.0, May 2022.
 */
public class CommandLineInterface {
    /**
     * Reads in input tokens from std.in
     */
    private Scanner scanner = new Scanner(System.in);

    private GameEnvironment game;

    /**
     * Displays the options on the command line
     * 
     * @param options {@link ArrayList} of option strings
     */
    private void displayOptions(ArrayList<String> options) {

        // Options are prompted with 1-index for readability
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i) + "\n");
        }
        System.out.println("Enter option number: ");
    }

    /**
     * Prompts a user for an input from a set of options
     * 
     * @param options list of options to choose from
     * @return index of the chosen option in the options list
     */
    public int getOption(ArrayList<String> options) {
        int input = -1; // Out of valid range

        displayOptions(options); // display options with 1-index
        while (!(input >= 0 && input < options.size())) {
            input = getInt() - 1; // convert back to 0-index
            // Input is out of range
            if (input < 0 || input >= options.size()) {
                System.out.println("Option is out of range");
                continue;
            }
        }

        return input;
    }

    private int getInt() {
        int value;
        // Loop consuming tokens until int
        while (scanner.hasNext() && !scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer");
            scanner.next();
        }

        value = scanner.nextInt();

        clearBuffer();

        return value;
    }

    private String getString() {
        String value;

        value = scanner.next();

        clearBuffer();

        return value;
    }

    /**
     * Clear any additional tokens inputted
     */
    private void clearBuffer() {
        scanner = new Scanner(System.in);
    }

    public void setUp() {
        // Name
        System.out.println("What is your name? ");
        String playerName = getString();

        // Difficulty
        System.out.println("Select Difficulty");
        int difficultyChoice = getOption(new ArrayList<String>(Arrays.asList(
                Difficulty.EASY.name(), // 0
                Difficulty.NORMAL.name(), // 1
                Difficulty.HARD.name()))); // 2
        Difficulty gameDifficulty;
        switch (difficultyChoice) {
            case 0:
                gameDifficulty = Difficulty.EASY;
                break;
            case 1:
                gameDifficulty = Difficulty.NORMAL;
                break;
            case 2:
                gameDifficulty = Difficulty.HARD;
                break;
            default:
                gameDifficulty = Difficulty.NORMAL;
                break;
        }

        // Days
        int numDays = 0;
        while (numDays < GameEnvironment.MINDAYS || numDays > GameEnvironment.MAXDAYS) {
            System.out.println("Select Number of Days (" +
                    GameEnvironment.MINDAYS + "-" + GameEnvironment.MAXDAYS +
                    "): ");
            numDays = getInt();
        }

        // Starter - generate
        int numStarters = 3; // TODO: make constant
        ArrayList<Monster> possibleStarters = GameEnvironment.generateMonsters();
        ArrayList<Monster> availableStarters = new ArrayList<Monster>();
        ArrayList<String> options = new ArrayList<String>();
        for (int i = 0; i < numStarters; i++) {
            Monster m = possibleStarters.get(GameEnvironment.rng.nextInt(possibleStarters.size()));
            availableStarters.add(m);
            options.add(m.toString());
            possibleStarters.remove(m);
        }
        // Starter - display/select
        System.out.println("Select a starting monster");
        int starterChoice = getOption(options);
        Monster starter = availableStarters.get(starterChoice);
        // Starter - name
        System.out.println("Do you want to give your monster a nickname? ");
        int setMonsterNickname = getOption(new ArrayList<String>(Arrays.asList(
                "Yes", // 0
                "No"))); // 1
        if (setMonsterNickname == 0) {
            System.out.println("Enter a nickname for your monster: ");
            starter.setName(getString());
        }

        // Set Game
        try {
            Team team = new Team(starter);
            Player player = new Player(playerName, team, 30); // TODO: make constant
            game = new GameEnvironment(player, numDays, gameDifficulty);
        } catch (TeamSizeException e) { // Will never occur
            e.printStackTrace();
        } catch (DuplicateMonsterException e) { // Will never occur
            e.printStackTrace();
        }

    }

    public void mainMenu() {

    }

    // ***************USED FOR DEVELOPMENT TESTING*********************
    public static void main(String args[]) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.setUp();
        System.out.println(cli.game.getPlayer().getName() + "\n" +
                cli.game.getPlayer().getTeam().getFirstAliveMonster().getName() + "\n" +
                cli.game.getPlayer().getTeam().getFirstAliveMonster().getClass() + "\n" +
                cli.game.getDifficulty() + "\n" +
                cli.game.getTotalDays());
    }
    // ****************************************************************
}
