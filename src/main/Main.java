package main;

import ui.cli.CommandLineInterface;
import ui.gui.MainContainer;
import java.awt.EventQueue;


/**
 * Contains main methods to execute the program
 * @author Harrison Tyson
 * @author Jackie Jone
 * @version 1.0, May. 2022
 *
 */
public class Main {
    /**
     * Main application execution
     *
     * @param args Arguments passed in when running program
     */
    public static void main(String args[]) {
        runGUI();
    }

    /**
     * Runs game as CommandLine Application
     */
    @SuppressWarnings("unused")
    private static void runCLI() {
        CommandLineInterface cli = new CommandLineInterface();
        cli.setUp();

        while (!cli.isGameOver()) {
            cli.mainMenu();
        }

        cli.gameOverScreen();
    }

    /**
     * Runs game as GUI Application
     */
    private static void runGUI() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainContainer window = new MainContainer();
                    window.getGameFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
