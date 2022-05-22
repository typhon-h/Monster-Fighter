package ui.gui;

import java.util.ArrayList;

import items.Item;
import main.Entity;
import monsters.Monster;

import javax.swing.ButtonGroup;

import javax.swing.JButton;

/**
 * JPanel for displaying the contents of the player that can be sold
 * 
 * @author Jackie Jone
 * @author Harrison Tyson
 * @version 1.1 May, 2022
 */
public class BuyShopPanel extends EntityViewer implements Updatable {
    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Button to complete buy action
     */
    private JButton btnBuy;

    /**
     * Buttons associated to shop content
     */
    ButtonGroup contentButtons;

    /**
     * {@link main.Entity Entities} in the shop
     */
    ArrayList<Entity> shopContent;

    /**
     * Create the panel with all the buttons and boxes without content
     */
    public BuyShopPanel() {
        super("Buy Shop", true, true, true);
        setName("BuyShop");

        super.createContentPanel();

        btnBuy = new JButton("Buy");
        btnBuy.addActionListener(sell -> {
            buyEntity();
        });
        btnBuy.setBounds(690, 487, 295, 47);
        add(btnBuy);
    }

    /**
     * Purchase the item for the player and update the screen
     */
    private void buyEntity() {
        shopContent = MainContainer.game.getBuyShop().getContent();
        contentButtons = super.contentPanels.get(0).getButtons();
        if (shopContent.size() > 0) {
            int index = Integer.parseInt(contentButtons.getSelection().getActionCommand());
            Entity entityToBuy = shopContent.get(index);
            if (entityToBuy instanceof Item) {
                new PopUp("Info", MainContainer.game.getBuyShop().buy((Item) entityToBuy),
                        this.getLocationOnScreen());
            } else {
                new PopUp("Info", MainContainer.game.getBuyShop().buy((Monster) entityToBuy),
                        this.getLocationOnScreen());
            }
            update();
        } else {
            new PopUp("Error", "Please select something to purchase", this.getLocationOnScreen());
        }
    }

    /**
     * Update the screen with the new information
     */
    public void update() {
        shopContent = MainContainer.game.getBuyShop().getContent();

        ArrayList<Object[]> panelsContent = new ArrayList<Object[]>();
        // Add in order of panel display
        panelsContent.add(shopContent.toArray());

        super.updateContentPanels(panelsContent);
        super.updatePlayerInfo();
        contentButtons = super.contentPanels.get(0).getButtons();
        super.selectFirstAvailableButton(contentButtons);
        super.updatePreview(contentButtons, shopContent.toArray());
    }
}
