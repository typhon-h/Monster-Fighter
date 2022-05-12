package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.GameEnvironment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

public class MainContainer {

    private JFrame monsterGameFrame;
    protected static GameEnvironment game;
    protected static final int SCREENWIDTH = 960;
    protected static final int SCREENHEIGHT = 540;
    private static JPanel mainContainerPanel;
    private static CardLayout cardLayout;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainContainer window = new MainContainer();
                    window.monsterGameFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainContainer() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        monsterGameFrame = new JFrame();
        monsterGameFrame.setResizable(false);
        monsterGameFrame.setTitle("Monster Fighter");
        monsterGameFrame.setMinimumSize(new Dimension(SCREENWIDTH, SCREENHEIGHT + 35));
        monsterGameFrame.setBounds(100, 100, SCREENWIDTH, SCREENHEIGHT + 35);
        monsterGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: Each menu panel needs to be its own class?
        mainContainerPanel = new JPanel();
        monsterGameFrame.getContentPane().add(mainContainerPanel, BorderLayout.CENTER);
        cardLayout = new CardLayout(0, 0);
        mainContainerPanel.setLayout(cardLayout);

        SetupPanel setUpPanel = new SetupPanel();
        mainContainerPanel.add(setUpPanel, "Setup");

        JPanel mainMenuPanel = new JPanel();
        mainContainerPanel.add(mainMenuPanel, "MainMenu");
        mainMenuPanel.setBackground(Color.orange);

        // TODO: Make this JPanel a new class that inherits JPanel with arguments
        // for settings the content of the panel.
        // TODO: dynamically rename this Panel
        JPanel commonMenuPanel = new JPanel();
        mainContainerPanel.add(commonMenuPanel, "Menu");

        JPanel battlePanel = new JPanel();
        mainContainerPanel.add(battlePanel, "Battle");

        JPanel gameOverPanel = new JPanel();
        battlePanel.add(gameOverPanel);

        // Show default setup panel
        showScreen("Setup");
    }
    
    
    public static void showScreen(String screenName) {
    	cardLayout.show(mainContainerPanel, screenName);
    }

}
