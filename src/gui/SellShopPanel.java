package gui;

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
 * @version 1.1 Mar, 2022s
 */
public class SellShopPanel extends EntityViewer implements Updatable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Entity> shopContent;

    private ButtonGroup contentButtons;

    private JButton btnSell;

    /**
     * Create the panel with all the buttons and boxes without content
     */
    public SellShopPanel() {
        super("Sell Shop", true, true, true);
        setName("SellShop");

        super.createContentPanel(MainContainer.game.getSellShop().getContent(), EntityViewer.DEFAULTCONTENTWIDTH,
                EntityViewer.DEFAULTCONTENTWIDTH, EntityViewer.DEFAULTCONTENTX,
                EntityViewer.DEFAULTCONTENTY);

        btnSell = new JButton("Sell");
        btnSell.addActionListener(sell -> {
            sellEntity();
        });
        btnSell.setBounds(690, 487, 295, 47);
        add(btnSell);
    }

    /**
     * Sell the selected item
     */
    private void sellEntity() {
        shopContent = MainContainer.game.getSellShop().getContent();
        contentButtons = super.contentPanels.get(0).getButtons();
        int index = Integer.parseInt(contentButtons.getSelection().getActionCommand());
        Entity entityToSell = shopContent.get(index);
        if (entityToSell instanceof Item) {
            new PopUp("Info", MainContainer.game.getSellShop().sell((Item) entityToSell),
                    this.getLocationOnScreen());
        } else {
            new PopUp("Info", MainContainer.game.getSellShop().sell((Monster) entityToSell),
                    this.getLocationOnScreen());
        }
        update();
    }

    /**
     * Update the screen with the new information
     */
    public void update() {
        shopContent = MainContainer.game.getSellShop().getContent();

        ArrayList<ArrayList<Entity>> panelsContent = new ArrayList<ArrayList<Entity>>();
        // Add in order of panel display
        panelsContent.add(shopContent);

        super.updateContentPanels(panelsContent);
        super.updatePlayerInfo();
        contentButtons = super.contentPanels.get(0).getButtons();
        super.selectFirstAvailableButton(contentButtons);
        super.updatePreview(contentButtons, shopContent.toArray());
    }
}
