package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import items.Item;
import main.Entity;
import monsters.Monster;

import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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

        private ButtonGroup contentButtons = new ButtonGroup();
        private JButton btnSell;
        private JScrollPane scrollPane;

        private JPanel entityDisplay;

        private static final int entityDisplayWidth = 600;
        private static final int entityDisplayHeight = 480;
        private static final int entityWidth = 120;
        private static final int entityHeight = 120;
        private static final int entityContainerWidth = entityWidth * 2;
        private static final int entityContainerGap = (entityDisplayWidth -
                        (2 * entityContainerWidth)) / 4;
        private Dimension entityDisplayDimension;

        /**
         * Create the panel with all the buttons and boxes without content
         */
        public SellShopPanel() {
                super("Sell Shop", true, true, true);
                setName("SellShop");

                FlowLayout entityContainerLayout = new FlowLayout(FlowLayout.LEFT,
                                entityContainerGap,
                                entityContainerGap);
                entityDisplayDimension = new Dimension(entityDisplayWidth,
                                entityDisplayHeight);
                entityDisplay = new JPanel();
                entityDisplay.setLayout(entityContainerLayout);
                entityDisplay.setMaximumSize(new Dimension(entityDisplayWidth, 2000));
                entityDisplay.setPreferredSize(entityDisplayDimension);
                entityDisplay.setBackground(getBackground());

                scrollPane = new JScrollPane();
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
                scrollPane.setBounds(6, 50, entityDisplayWidth + 35, entityDisplayHeight);
                scrollPane.setMaximumSize(scrollPane.getSize());
                scrollPane.setViewportView(entityDisplay);
                this.add(scrollPane);

                btnSell = new JButton("Sell");
                btnSell.addActionListener(sell -> {
                        sellEntity();
                });
                btnSell.setBounds(690, 487, 295, 47);
                add(btnSell);
        }

        /**
         * Populate the content on the panel with entitys that can be sold
         */
        private void updateContent() {
                btnSell.setEnabled(false);
                entityDisplay.removeAll();
                contentButtons = new ButtonGroup();
                shopContent = MainContainer.game.getSellShop().getContent();

                int height = (int) ((Math.ceil((float) shopContent.size() / 2.0f)) *
                                (entityHeight + entityContainerGap));
                height = height > entityDisplayHeight ? height : entityDisplayHeight;
                entityDisplayDimension = new Dimension(entityDisplayWidth,
                                height);
                entityDisplay.setPreferredSize(entityDisplayDimension);
                entityDisplay.updateUI();
                scrollPane.updateUI();

                EtchedBorder entityContainerBorder = new EtchedBorder(EtchedBorder.LOWERED,
                                Color.black, null);
                Dimension entityContainerDimension = new Dimension(entityContainerWidth,
                                entityHeight);
                JPanel entityContainer;
                JRadioButton entityButton;
                JTextPane entityTextPane;
                for (Entity entity : shopContent) {
                        btnSell.setEnabled(true);
                        entityContainer = new JPanel();
                        entityContainer.setPreferredSize(entityContainerDimension);
                        entityContainer.setLayout(new GridLayout(1, 2));
                        entityContainer.setOpaque(false);
                        entityButton = new JRadioButton();
                        entityButton.setIcon(MainContainer.imageResize(entity.getImage(),
                                        entityWidth,
                                        entityHeight));
                        entityButton.setOpaque(false);
                        entityButton.setBorder(entityContainerBorder);
                        entityButton.setActionCommand(String.valueOf(shopContent.indexOf(entity)));
                        entityButton.addActionListener(selected -> {
                                // TODO: Set border around selected object or a background or something
                                super.updatePreview(contentButtons, shopContent.toArray());
                        });
                        contentButtons.add(entityButton);
                        entityContainer.add(entityButton);

                        entityTextPane = new JTextPane();
                        entityTextPane.setText("\n" + entity.getSellPrice() + "G \n"
                                        + entity.getRarity().name() + "\n"
                                        + entity.getName());
                        entityTextPane.setEditable(false);
                        entityTextPane.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                        entityTextPane.setOpaque(false);
                        entityTextPane.setBorder(entityContainerBorder);
                        entityContainer.add(entityTextPane);

                        entityDisplay.add(entityContainer);
                }

        }

        /**
         * Sell the selected item
         */
        private void sellEntity() {
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
                updateContent();
                super.updatePlayerInfo();
                super.selectFirstAvailableButton(contentButtons);
                super.updatePreview(contentButtons, shopContent.toArray());
        }
}
