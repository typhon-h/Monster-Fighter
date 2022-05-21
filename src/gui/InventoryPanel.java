package gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import items.Item;
import monsters.Monster;

/**
 * JPanel for displaying the contents of the player that can be sold
 * 
 * @author Jackie Jone
 * @author Harrison Tyson
 * @version 1.1 May, 2022
 */
public class InventoryPanel extends EntityViewer implements Updatable {

    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Player inventory
     */
    private ArrayList<Item> playerInventory;

    /**
     * Player team
     */
    private ArrayList<Monster> playerTeam;

    /**
     * Group of buttons associated with inventory
     */
    private ButtonGroup itemButtons = new ButtonGroup();

    /**
     * Group of buttons associated with team
     */
    private ButtonGroup monsterButtons = new ButtonGroup();

    /**
     * Button for use item action
     */
    private JButton btnUse;

    /**
     * Create the panel with all the buttons and boxes without content
     */
    public InventoryPanel() {
        super("Inventory", true, true, true);
        setName("Inventory");

        super.createContentPanel(300, 480, 6, 50, 1);
        super.createContentPanel(300, 480, 350, 50, 1);

        JLabel lblItemsHeading = new JLabel("Items");
        lblItemsHeading.setBounds(68, 20, 190, 37);
        lblItemsHeading.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        lblItemsHeading.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblItemsHeading);

        JLabel lblMonstersHeading = new JLabel("Monsters");
        lblMonstersHeading.setBounds(430, 20, 190, 37);
        lblMonstersHeading.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        lblMonstersHeading.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMonstersHeading);

        btnUse = new JButton("Use");
        btnUse.addActionListener(sell -> {
            useItem();
        });
        btnUse.setBounds(690, 487, 295, 47);
        add(btnUse);
    }

    /**
     * Use the selected item on a the selected monster
     */
    private void useItem() {
        if (itemButtons.getSelection() != null && monsterButtons.getSelection() != null) {
            int itemIndex = Integer.parseInt(itemButtons.getSelection().getActionCommand());
            int monsterIndex = Integer.parseInt(monsterButtons.getSelection().getActionCommand());
            Item itemToUse = playerInventory.get(itemIndex);
            Monster monsterAffected = playerTeam.get(monsterIndex);
            new PopUp("Info", MainContainer.game.getPlayer().useItem(itemToUse, monsterAffected),
                    this.getLocationOnScreen());

            update();
        } else {
            new PopUp("Error", "Select Item/Monster", this.getLocationOnScreen());
        }

        update();

    }

    /**
     * Update the screen with the new information
     */
    public void update() {
        playerInventory = MainContainer.game.getPlayer().getInventory();
        playerTeam = MainContainer.game.getPlayer().getTeam().getMonsters();

        ArrayList<Object[]> panelsContent = new ArrayList<Object[]>();
        // Add in order of panel display
        panelsContent.add(playerInventory.toArray());
        panelsContent.add(playerTeam.toArray());
        super.updateContentPanels(panelsContent);

        super.updatePlayerInfo();
        itemButtons = super.contentPanels.get(0).getButtons();
        monsterButtons = super.contentPanels.get(1).getButtons();
        super.selectFirstAvailableButton(itemButtons);
        super.selectFirstAvailableButton(monsterButtons);

        super.updatePreview(itemButtons, playerInventory.toArray());
    }
}
