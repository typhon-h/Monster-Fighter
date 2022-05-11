package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;
import items.Item;
import monsters.Monster;

/**
 * Interface to communicate between a user and the game environment.
 *
 * @author Harrison Tyson
 * @version 1.1, May 2022.
 */
public class CommandLineInterface {
    /**
     * Reads in input tokens from std.in
     */
    private Scanner scanner = new Scanner(System.in);

    private GameEnvironment game;
    /**
     * Number of white spaces between menus
     */
    private static final int headerWhiteSpacing = 2;
    /**
     * Number of white spaces between messages
     */
    private static final int msgWhiteSpacing = 0;
    /**
     * Character used for the border of headers
     */
    private static final char headerChar = '-';
    /**
     * Character used for border of messages
     */
    private static final char msgChar = '*';

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
        while (!game.isGameOver()) {
            TextFormat.printHeader("Main Menu", headerWhiteSpacing, headerChar);
            System.out.println(String.format(" Day: %d/%d     Score: %d      Gold: %d",
                    game.getCurrentDay(),
                    game.getTotalDays(),
                    game.getPlayer().getScore(),
                    game.getPlayer().getGold()));

            ArrayList<String> options = new ArrayList<String>(Arrays.asList(
                    "Buy Shop", // 0
                    "Sell Shop", // 1
                    "View Team", // 2
                    "View Battles" // 3
            ));
            if (game.getBattleState().getResult() != BattleResult.NULL) {
                options.add("Sleep"); // 4
            }
            int option = getOption(options);
            switch (option) {
                case 0: // Buy Shop
                    buyShopMenu();
                    break;
                case 1: // Sell Shop
                    sellShopMenu();
                    break;
                case 2: // View Team
                    viewTeamMenu();
                    break;
                case 3: // View Battles
                    viewBattlesMenu();
                    break;
                case 4: // Sleep
                    game.sleep();
                    TextFormat.printHeader("You have advanced to the next day",
                                            msgWhiteSpacing,
                                            msgChar);
                    break;
                default:
                    break;
            }
        }

        // TODO: End of game stuff here, or new method
    }

    /**
     * Returns the details of player in a string format
     *
     * @param player
     */
    private String getPlayerDetails(Player player) {
        String outputString;
        ArrayList<Monster> playerMonsters;

        outputString = player.getName() +
                        "\nRewards: " + player.getGold() +
                        "G | " + player.getScore() +
                        " Points\n-----------------------------\n";

        playerMonsters = player.getTeam().getMonsters();
        for (int i = 0; i < playerMonsters.size(); i++) {
            outputString += playerMonsters.get(i).getName();
            if (i != playerMonsters.size() - 1) {
                outputString += " | ";
            }
        }

        return outputString;
    }

    public void viewBattlesMenu() { // TODO: implement
        while (true) {
            int confirm;
            int option;
            Player selectedOpponent;

            TextFormat.printHeader("Battles", headerWhiteSpacing, headerChar);

            ArrayList<String> options = new ArrayList<String>(Arrays.asList("Back"));

            for (Player opponent : game.getBattleState().getOpponents()) {
                options.add(getPlayerDetails(opponent));
            }

            option = getOption(options);

            if (option == 0) {
                return;

            } else if (game.getPlayer().getTeam().getAliveMonsters().isEmpty()) {
                try {
                    TextFormat.printHeader("Unable to battle, all your monsters have fainted",
                                            msgWhiteSpacing,
                                            msgChar);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                selectedOpponent = game.getBattleState().getOpponents().get(option - 1);
                System.out.println("\nFight " + selectedOpponent.getName() + "?");
                confirm = getOption(new ArrayList<String>(Arrays.asList("Yes", "No")));

                if (confirm == 0) {
                    game.getBattleState().setOpponent(selectedOpponent);
                    displayBattle();
                    // Start battle here
                    return;
                }
            }
        }
    }

    private void displayBattle() {
        BattleManager battle = game.getBattleState();
        Player opponent = battle.getCurrOpponent();
        battle.simulateBattle();
        BattleEvent currState = battle.nextEvent();

        try {
            while (currState != null) {
                System.out.println(currState.getDescription());
                game.getPlayer().setTeam(currState.getAllyTeam());
                TimeUnit.MILLISECONDS.sleep(300);
                currState = battle.nextEvent();
            }

                // Rewards
                switch (battle.getResult()) {
                    case WIN:
                        TextFormat.printHeader("You have won!", msgWhiteSpacing, msgChar);
                        System.out.println("Rewards: " + opponent.getGold() + "G | " +
                                        opponent.getScore() + " Points");
                        battle.giveRewards();
                        break;
                    case LOSS:
                        TextFormat.printHeader("You have lost!", msgWhiteSpacing, msgChar);
                        break;
                    default:
                        break;
            }
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Set the player's team to the resulting team
        // TODO: Problem the new team has the same current stats, i.e.
        // stats could be more than base value.

        // TODO: This is not updating the player's team to the current team
        // game.getPlayer().setTeam(battle.getPlayer().getTeam());
    }

    // TODO: !!!!!!!!! VIEW INVENTORY !!!!!!!

    public void viewTeamMenu() {// TODO: implement
        // TODO: view team and re-arrage
    }

    public void sellShopMenu() {// TODO: implement
        ArrayList<Entity> playerContent;

        while (true) {
            TextFormat.printHeader("Sell Shop", headerWhiteSpacing, headerChar);
            System.out.println("Gold: " + game.getPlayer().getGold());

            ArrayList<String> options = new ArrayList<String>(Arrays.asList("Back"));

            playerContent = game.getSellShop().getContent();
            for (Entity content : playerContent) {
                String listing;
                listing = content.getName() +
                        "(" + content.getRarity() + ") " +
                        content.getSellPrice() + "G";
                options.add(listing);
            }

            int option = getOption(options);

            String sellMessage;

            if (option == 0) {
                // TODO: Go back;
                return;
            } else {
                if (game.getSellShop().getContent().get(option - 1) instanceof Item) {

                    sellMessage = game.getSellShop().sell(
                            (Item) game.getSellShop().getContent().get(option - 1));
                } else {
                    sellMessage = game.getSellShop().sell(
                            (Monster) game.getSellShop().getContent().get(option - 1));
                }
                System.out.println(sellMessage);
            }
        }
    }

    public void buyShopMenu() {
        ArrayList<Entity> shopContent;

        while (true) {
            TextFormat.printHeader("Buy Shop", headerWhiteSpacing, headerChar);
            System.out.println("Gold: " + game.getPlayer().getGold());

            ArrayList<String> options = new ArrayList<String>(Arrays.asList("Back"));

            // Add shop stock into options
            shopContent = game.getBuyShop().getContent();
            for (Entity stock : shopContent) {
                String listing;
                listing = stock.getName() +
                        "(" + stock.getRarity() + ") " +
                        stock.getBuyPrice() + "G\n" +
                        stock.getDescription();
                        // TODO: Use to string method of monster
                        // TODO: Concat price to string repr

                options.add(listing);
            }

            int option = getOption(options);
            String buyMessage;

            if (option == 0) {
                // TODO: Go back;
                return;
            } else {
                if (game.getBuyShop().getContent().get(option - 1) instanceof Item) {

                    buyMessage = game.getBuyShop().buy(
                            (Item) game.getBuyShop().getContent().get(option - 1));
                } else {
                    buyMessage = game.getBuyShop().buy(
                            (Monster) game.getBuyShop().getContent().get(option - 1));
                }
                System.out.println(buyMessage);
            }
        }
    }

    // ***************USED FOR DEVELOPMENT TESTING*********************
    public static void main(String args[]) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.setUp();
        cli.mainMenu();
        // System.out.println(cli.game.getPlayer().getName() + "\n" +
        // cli.game.getPlayer().getTeam().getFirstAliveMonster().getName() + "\n" +
        // cli.game.getPlayer().getTeam().getFirstAliveMonster().getClass() + "\n" +
        // cli.game.getDifficulty() + "\n" +
        // cli.game.getTotalDays());
    }
    // ****************************************************************
}
