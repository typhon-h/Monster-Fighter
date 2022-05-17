package gui;

import javax.swing.JButton;

public class MainMenuPanel extends EntityViewer implements Updatable {

        private static final long serialVersionUID = 1L;

        public MainMenuPanel() {
                super(false, false);
                setName("MainMenu");

                JButton btnBattles = new JButton("Battle");
                btnBattles.addActionListener(battle -> {
                        MainContainer.showScreen("BattleSelection");
                });
                btnBattles.setBounds(26, 98, 908, 45);
                add(btnBattles);

                JButton btnBuyShop = new JButton("Buy Shop");
                btnBuyShop.addActionListener(battle -> {
                        MainContainer.showScreen("BuyShop");
                });
                btnBuyShop.setBounds(26, 186, 908, 45);
                add(btnBuyShop);

                JButton btnSellShop = new JButton("Sell Shop");
                btnSellShop.addActionListener(battle -> {
                        MainContainer.showScreen("SellShop");
                });
                btnSellShop.setBounds(26, 278, 908, 45);
                add(btnSellShop);

                JButton btnInventory = new JButton("Inventory");
                btnInventory.addActionListener(battle -> {
                        MainContainer.showScreen("Inventory");
                });
                btnInventory.setBounds(26, 363, 908, 45);
                add(btnInventory);

                JButton btnTeam = new JButton("Team");
                btnTeam.addActionListener(battle -> {
                        MainContainer.showScreen("Team");
                });
                btnTeam.setBounds(25, 456, 908, 45);
                add(btnTeam);
        }

        public void update() {
                super.updatePlayerInfo();
        }
}
