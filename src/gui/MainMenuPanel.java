package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MainMenuPanel() {
        super();
        setName("MainMenu");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);
        
        
        JLabel lblMainMenuTitle = new JLabel("Main Menu");
        lblMainMenuTitle.setBounds(378, 6, 193, 37);
        lblMainMenuTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblMainMenuTitle);
        
        JLabel lblDay = new JLabel("Day:");
        lblDay.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblDay.setBounds(223, 6, 51, 21);
        add(lblDay);
        
        JLabel lblGold = new JLabel("Gold:");
        lblGold.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblGold.setBounds(838, 9, 61, 16);
        add(lblGold);
        
        JLabel lblName = new JLabel("Name: ");
        lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblName.setBounds(6, 6, 70, 16);
        add(lblName);
        
        JLabel lblScore = new JLabel("Score: ");
        lblScore.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblScore.setBounds(583, 9, 70, 16);
        add(lblScore);
        
        JLabel lblPlayerGold = new JLabel(""+MainContainer.game.getPlayer().getGold());
        lblPlayerGold.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblPlayerGold.setBounds(893, 11, 61, 16);
        add(lblPlayerGold);
        
        JLabel lblPlayerScore = new JLabel(""+MainContainer.game.getPlayer().getScore());
        lblPlayerScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblPlayerScore.setBounds(644, 11, 61, 16);
        add(lblPlayerScore);
        
        JLabel lblCurrentDay = new JLabel(MainContainer.game.getCurrentDay() + "/"+ MainContainer.game.getTotalDays());
        lblCurrentDay.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblCurrentDay.setBounds(269, 10, 61, 16);
        add(lblCurrentDay);
        
        JLabel lblPlayerName = new JLabel(MainContainer.game.getPlayer().getName());
        lblPlayerName.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblPlayerName.setBounds(74, 8, 166, 16);
        add(lblPlayerName);
        
        JButton btnBattles = new JButton("Battle");
        btnBattles.addActionListener(battle -> {MainContainer.showScreen("BattleSelection");});
        btnBattles.setBounds(26, 98, 908, 45);
        add(btnBattles);
        
        JButton btnBuyShop = new JButton("Buy Shop");
        btnBuyShop.addActionListener(battle -> {MainContainer.showScreen("BuyShop");});
        btnBuyShop.setBounds(26, 186, 908, 45);
        add(btnBuyShop);
        
        JButton btnSellShop = new JButton("Sell Shop");
        btnSellShop.addActionListener(battle -> {MainContainer.showScreen("SellShop");});
        btnSellShop.setBounds(26, 278, 908, 45);
        add(btnSellShop);
        
        JButton btnInventory = new JButton("Inventory");
        btnInventory.addActionListener(battle -> {MainContainer.showScreen("Inventory");});
        btnInventory.setBounds(26, 363, 908, 45);
        add(btnInventory);
        
        JButton btnTeam = new JButton("Team");
        btnTeam.addActionListener(battle -> {MainContainer.showScreen("Team");});
        btnTeam.setBounds(25, 456, 908, 45);
        add(btnTeam);
	}
}
