package main;

import java.util.ArrayList;
import java.util.Scanner;

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

    // private GameEnvironment game = null;

    /**
     * Displays the options on the command line
     * 
     * @param options {@link ArrayList} of option strings
     */
    private void displayOptions(ArrayList<String> options) {

        // Options are prompted with index + 1 for readability
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
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

        displayOptions(options);
        while (!(input >= 0 && input < options.size())) {
            input = getInt() - 1;
            // Input is out of range
            if (input < 0 || input >= options.size()) {
                System.out.println("Option is out of range");
                continue;
            }
        }

        return input;
    }

    public int getInt() {
        int value;
        while (scanner.hasNext() && !scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer");
            scanner.next();
        }

        value = scanner.nextInt();

        clearBuffer();

        return value;
    }

    public String getString() {
        String value;
        while (scanner.hasNext()) {
            System.out.println("Please enter a valid string");
            scanner.next();
        }

        value = scanner.next();

        clearBuffer();

        return value;
    }

    private void clearBuffer() {
        scanner = new Scanner(System.in);
    }

    // ***************USED FOR DEVELOPMENT TESTING*********************
    public static void main(String args[]) {
        CommandLineInterface cli = new CommandLineInterface();
        ArrayList<String> options = new ArrayList<String>();
        options.add("test1");
        options.add("test2");
        System.out.println("Result: " + cli.getOption(options));
    }
    // ****************************************************************
}
