package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.GameEnvironment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

public class MainContainer {

    private JFrame monsterGameFrame;
    protected static GameEnvironment game;
    protected static final int SCREENWIDTH = 960;
    protected static final int SCREENHEIGHT = 540;
    protected static final Dimension DEFAULTDIMENSION = new Dimension(SCREENWIDTH, SCREENHEIGHT);
    private static JPanel mainContainerPanel;
    private static CardLayout cardLayout;

    private static SetupPanel setUpPanel;
    private static BuyShopPanel buyShopPanel;

    private static SellShopPanel sellShopPanel;

    private static InventoryPanel inventoryPanel;
    private static MainMenuPanel mainMenuPanel;
    private static TeamPanel teamPanel;
    private static BattleSelectionPanel battleSelectionPanel;

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
        monsterGameFrame.setSize(new Dimension(SCREENWIDTH, SCREENHEIGHT + 35));
        monsterGameFrame.setMinimumSize(new Dimension(SCREENWIDTH, SCREENHEIGHT + 35));
        monsterGameFrame.setBounds(100, 100, SCREENWIDTH, SCREENHEIGHT + 35);
        monsterGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: Each menu panel needs to be its own class?
        mainContainerPanel = new JPanel();
        monsterGameFrame.getContentPane().add(mainContainerPanel, BorderLayout.CENTER);
        cardLayout = new CardLayout(0, 0);
        mainContainerPanel.setLayout(cardLayout);

        setUpPanel = new SetupPanel();
        mainContainerPanel.add(setUpPanel, "Setup");

        // Show default setup panel
        showScreen("Setup");
    }

    public static void showScreen(String screenName) {
        for (Component c : mainContainerPanel.getComponents()) {
            if (c.getName() == screenName && c instanceof Updatable) {
                ((Updatable) c).update();
                break;
            }

        }

        cardLayout.show(mainContainerPanel, screenName);
    }

    public static void setUpScreens() {
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
    }

}
