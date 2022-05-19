package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import static gui.MainContainer.game;

public class MainMenuPanel extends EntityViewer implements Updatable {

    private static final long serialVersionUID = 1L;

    private int buttonsWidth = (MainContainer.SCREENWIDTH * 9) / 10;
    private int buttonsHeight = 45;

    public MainMenuPanel() {
        super(true, false, false);
        setName("MainMenu");

        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0,
            ((MainContainer.SCREENHEIGHT - 150) - buttonsHeight * 6) / 6));
        buttonsContainer.setBounds(MainContainer.SCREENWIDTH / 2 - buttonsWidth / 2, 50,
                     buttonsWidth, MainContainer.SCREENHEIGHT - 150);
        buttonsContainer.setOpaque(false);
        this.add(buttonsContainer);

        JButton btnBattles = new JButton("Battle");
        btnBattles.addActionListener(battle -> {
            MainContainer.showScreen("BattleSelection");
        });
        btnBattles.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        buttonsContainer.add(btnBattles);

        JButton btnBuyShop = new JButton("Buy Shop");
        btnBuyShop.addActionListener(battle -> {
            MainContainer.showScreen("BuyShop");
        });
        btnBuyShop.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        buttonsContainer.add(btnBuyShop);

        JButton btnSellShop = new JButton("Sell Shop");
        btnSellShop.addActionListener(battle -> {
            MainContainer.showScreen("SellShop");
        });
        btnSellShop.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        buttonsContainer.add(btnSellShop);

        JButton btnInventory = new JButton("Inventory");
        btnInventory.addActionListener(battle -> {
            MainContainer.showScreen("Inventory");
        });
        btnInventory.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        buttonsContainer.add(btnInventory);

        JButton btnTeam = new JButton("Team");
        btnTeam.addActionListener(battle -> {
            MainContainer.showScreen("Team");
        });
        btnTeam.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        buttonsContainer.add(btnTeam);

        JButton btnSleep = new JButton("Sleep");
        btnSleep.addActionListener(sleep -> {
            String sleepEvents = String.join("<br>", MainContainer.game.sleep());
            String sleepMessage = "<html>You have advanced to the next day<br>" +
                       sleepEvents + "</html>";
            if (game.isGameOver()) {
                MainContainer.showScreen("GameOver");
            } else {
                new PopUp("Sleep", sleepMessage, this.getLocationOnScreen());
                MainContainer.showScreen("MainMenu");
            }
        });
        btnSleep.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        buttonsContainer.add(btnSleep);
    }

    public void update() {
        super.updatePlayerInfo();
    }
}
