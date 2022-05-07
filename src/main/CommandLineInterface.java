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

    /**
     * Value of user input
     */
    private int input = -1;

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
    public int getInput(ArrayList<String> options) {
        input = -1; // Default always out of valid range

        while (!(input >= 0 && input < options.size())) {
            displayOptions(options);

            // Get input
            if (scanner.hasNextInt()) {
                input = scanner.nextInt() - 1;
            } else { // Input is not of type int
                scanner.next();
                System.out.println("\n\nPlease enter a valid integer\n");
                continue;
            }

            // Input is out of range
            if (input < 0 || input >= options.size()) {
                System.out.println("\n\nOption is out of range\n");
                continue;
            }
        }

        return input;
    }

    // ***************USED FOR DEVELOPMENT TESTING*********************
    // public static void main(String args[]) {
    // CommandLineInterface cli = new CommandLineInterface();
    // ArrayList<String> options = new ArrayList<String>();
    // options.add("test1");
    // options.add("test2");
    // System.out.println("Result: " + cli.getInput(options));
    // }
    // ****************************************************************
}
