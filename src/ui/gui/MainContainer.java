package ui.gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.GameEnvironment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Main container of the game and entry point of the GUI.
 * Sets up the main JFrame of the game and the main container JPanel to hold
 * all the other JPanels of the game.
 * 
 * @author Jackie Jone
 * @author Harrison Tyson
 * @version 1.3 May, 2022
 */
public class MainContainer {
    /**
     * Main application frame
     */
    private JFrame monsterGameFrame;

    /**
     * Active game
     */
    protected static GameEnvironment game;

    /**
     * Height of menu bar
     */
    private static final int TOPBARHEIGHT = 35;

    /**
     * Buffer to account for window border offsets
     */
    private static final int SCREENWIDTHADJUST = 50;

    /**
     * Width of screen
     */
    protected static final int SCREENWIDTH = 960 + SCREENWIDTHADJUST;

    /**
     * Height of screen
     */
    protected static final int SCREENHEIGHT = 540 + TOPBARHEIGHT;

    /**
     * Default screen size
     */
    protected static final Dimension DEFAULTDIMENSION = new Dimension(SCREENWIDTH, SCREENHEIGHT);

    /**
     * Container to hold all game screens
     */
    private static JPanel mainContainerPanel;

    /**
     * Screen manager to switch between different screens
     */
    private static CardLayout cardLayout;

    /**
     * Screen for SetUp Menu
     */
    private static SetupPanel setUpPanel;

    /**
     * Screen for BuyShop Menu
     */
    private static BuyShopPanel buyShopPanel;

    /**
     * Screen for SellShop Menu
     */
    private static SellShopPanel sellShopPanel;

    /**
     * Screen for Inventory Menu
     */
    private static InventoryPanel inventoryPanel;

    /**
     * Screen for MainMenu
     */
    private static MainMenuPanel mainMenuPanel;

    /**
     * Screen for Team Menu
     */
    private static TeamPanel teamPanel;

    /**
     * Screen for BattleSelection Menu
     */
    private static BattleSelectionPanel battleSelectionPanel;

    /**
     * Screen for Battle Simulation
     */
    private static BattleSimPanel battleSimulationPanel;

    /**
     * Screen for Game Over
     */
    private static GameOverPanel gameOverPanel;

    /**
     * Create the application.
     */
    public MainContainer() {
        monsterGameFrame = new JFrame();
        monsterGameFrame.setResizable(false);
        monsterGameFrame.setTitle("Monster Fighter");
        monsterGameFrame.setSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        monsterGameFrame.setMinimumSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        monsterGameFrame.setBounds(115, 100, SCREENWIDTH, SCREENHEIGHT);
        monsterGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        monsterGameFrame.setLocationRelativeTo(null);

        mainContainerPanel = new JPanel();
        monsterGameFrame.getContentPane().add(mainContainerPanel, BorderLayout.CENTER);
        cardLayout = new CardLayout(0, 0);
        mainContainerPanel.setLayout(cardLayout);

        setUpPanel = new SetupPanel();
        setUpPanel.setName("Setup");
        mainContainerPanel.add(setUpPanel, "Setup");

        // Show default setup panel
        showScreen("Setup");
    }

    /**
     * Show a given screen in the application
     * 
     * @param screenName The name of the screen to be shown
     */
    public static void showScreen(String screenName) {
        for (Component c : mainContainerPanel.getComponents()) {
            if (c.getName() == screenName && c instanceof Updatable) {
                ((Updatable) c).update();
                break;
            }

        }

        cardLayout.show(mainContainerPanel, screenName);
    }

    /**
     * Set up all the screens in the game
     */
    public static void setUpScreens() {
        for (Component component : mainContainerPanel.getComponents()) {
            if (component.getName() != "Setup") {
                mainContainerPanel.remove(component);
            }
        }

        mainMenuPanel = new MainMenuPanel(); // TODO: extract menu names to constants
        mainContainerPanel.add(mainMenuPanel, "MainMenu");

        buyShopPanel = new BuyShopPanel();
        mainContainerPanel.add(buyShopPanel, "BuyShop");

        sellShopPanel = new SellShopPanel();
        mainContainerPanel.add(sellShopPanel, "SellShop");

        inventoryPanel = new InventoryPanel();
        mainContainerPanel.add(inventoryPanel, "Inventory");

        teamPanel = new TeamPanel();
        mainContainerPanel.add(teamPanel, "Team");

        battleSelectionPanel = new BattleSelectionPanel();
        mainContainerPanel.add(battleSelectionPanel, "BattleSelection");

        battleSimulationPanel = new BattleSimPanel();
        mainContainerPanel.add(battleSimulationPanel, "BattleSimulation");

        gameOverPanel = new GameOverPanel();
        mainContainerPanel.add(gameOverPanel, "GameOver");
    }

    /**
     * Reset the game to be played again
     */
    protected static void resetGame() {
        setUpPanel.resetGame();
        game = null;
    }

    /**
     * Resize a image to a desired size
     * 
     * @param image        The image to be resized
     * @param targetWidth  The width to resize to
     * @param targetHeight The height to resize to
     * @return The resized image
     */
    protected static ImageIcon imageResize(ImageIcon image, int targetWidth, int targetHeight) {
        Image tmp = image.getImage();
        Image scaled = tmp.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    /**
     * Gets game window frame
     * 
     * @return master game JFrame
     */
    public JFrame getGameFrame() {
        return monsterGameFrame;
    }
}
