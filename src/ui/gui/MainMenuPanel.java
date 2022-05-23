package ui.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Main menu panel of the game to navigate to the other displays
 * 
 * @author Jackie Jone
 * @author Harrison Tyson
 * @version 1.1 May, 2022
 */
public class MainMenuPanel extends EntityViewer implements Updatable {

    /**
     * Default serial version iD
     */
    private static final long serialVersionUID = 1L;

    /**
     * Width of buttons
     */
    private int buttonsWidth = (MainContainer.SCREENWIDTH * 9) / 10;

    /**
     * Height of buttons
     */
    private final int BUTTONSHEIGHT = 45;

    /**
     * Initialize the main menu with all the navigation buttons
     */
    public MainMenuPanel() {
        super("Main Menu", true, false, false);
        setName("MainMenu");

        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0,
                ((MainContainer.SCREENHEIGHT - 150) - BUTTONSHEIGHT * 6) / 6));
        buttonsContainer.setBounds(MainContainer.SCREENWIDTH / 2 - buttonsWidth / 2, 50,
                buttonsWidth, MainContainer.SCREENHEIGHT - 150);
        buttonsContainer.setOpaque(false);
        this.add(buttonsContainer);

        JButton btnBattles = new JButton("Battle");
        btnBattles.addActionListener(battle -> {
            MainContainer.showScreen("BattleSelection");
        });
        btnBattles.setPreferredSize(new Dimension(buttonsWidth, BUTTONSHEIGHT));
        buttonsContainer.add(btnBattles);

        JButton btnBuyShop = new JButton("Buy Shop");
        btnBuyShop.addActionListener(battle -> {
            MainContainer.showScreen("BuyShop");
        });
        btnBuyShop.setPreferredSize(new Dimension(buttonsWidth, BUTTONSHEIGHT));
        buttonsContainer.add(btnBuyShop);

        JButton btnSellShop = new JButton("Sell Shop");
        btnSellShop.addActionListener(battle -> {
            MainContainer.showScreen("SellShop");
        });
        btnSellShop.setPreferredSize(new Dimension(buttonsWidth, BUTTONSHEIGHT));
        buttonsContainer.add(btnSellShop);

        JButton btnInventory = new JButton("Inventory");
        btnInventory.addActionListener(battle -> {
            MainContainer.showScreen("Inventory");
        });
        btnInventory.setPreferredSize(new Dimension(buttonsWidth, BUTTONSHEIGHT));
        buttonsContainer.add(btnInventory);

        JButton btnTeam = new JButton("Team");
        btnTeam.addActionListener(battle -> {
            MainContainer.showScreen("Team");
        });
        btnTeam.setPreferredSize(new Dimension(buttonsWidth, BUTTONSHEIGHT));
        buttonsContainer.add(btnTeam);

        JButton btnSleep = new JButton("Sleep");
        btnSleep.addActionListener(sleep -> {
            String sleepEvents = String.join("\n", MainContainer.game.sleep());
            String sleepMessage = "You have advanced to the next day\n" +
                    sleepEvents + "\n";
            if (MainContainer.game.isGameOver()) {
                MainContainer.showScreen("GameOver");
            } else {
                new PopUp("Sleep", sleepMessage, this.getLocationOnScreen());
                MainContainer.showScreen("MainMenu");
            }
        });
        btnSleep.setPreferredSize(new Dimension(buttonsWidth, BUTTONSHEIGHT));
        buttonsContainer.add(btnSleep);
    }

    /**
     * Update the information of the player on the panel
     */
    public void update() {
        super.updatePlayerInfo();
    }
}
