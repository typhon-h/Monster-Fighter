package gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import items.Item;
import main.Entity;
import main.Rarity;
import monsters.Monster;

public class BuyShopPanel extends EntityViewer implements Updatable {

        private static final long serialVersionUID = 1L;
        private JRadioButton rdBtnCommonMonster;
        private JRadioButton rdBtnCommonItem;
        private JRadioButton rdBtnRareItem;
        private JRadioButton rdBtnRareMonster;
        private JRadioButton rdBtnLegendaryItem;
        private JRadioButton rdBtnLegendaryMonster;
        private JTextPane textPaneCommonItem;
        private JTextPane textPaneCommonMonster;
        private JTextPane textPaneRareItem;
        private JTextPane textPaneRareMonster;
        private JTextPane textPaneLegendaryItem;
        private JTextPane textPaneLegendaryMonster;

        private ArrayList<Entity> shopContent = MainContainer.game.getBuyShop().getContent();
        private final ButtonGroup content = new ButtonGroup();
        private JButton btnBuy;

        /**
         * Create the panel.
         */
        public BuyShopPanel() {
                super(true, true, true);
                setName("BuyShop");

                JLabel lblBuyShopTitle = new JLabel("Buy Shop");
                lblBuyShopTitle.setBounds(430, 6, 150, 37);
                lblBuyShopTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
                add(lblBuyShopTitle);

                JPanel shopContent = new JPanel();
                shopContent.setBounds(6, 44, 551, 490);
                shopContent.setBackground(this.getBackground());
                add(shopContent);
                shopContent.setLayout(null);

                rdBtnCommonItem = new JRadioButton("");
                rdBtnCommonItem.addActionListener(selected -> {
                        super.updatePreview(content, this.shopContent.toArray());
                });
                content.add(rdBtnCommonItem);
                rdBtnCommonItem.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnCommonItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnCommonItem.setBounds(6, 6, 120, 120);
                shopContent.add(rdBtnCommonItem);

                rdBtnCommonMonster = new JRadioButton("");
                rdBtnCommonMonster.addActionListener(selected -> {
                        super.updatePreview(content, this.shopContent.toArray());
                });
                content.add(rdBtnCommonMonster);
                rdBtnCommonMonster.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnCommonMonster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnCommonMonster.setBounds(284, 6, 120, 120);
                shopContent.add(rdBtnCommonMonster);

                rdBtnRareItem = new JRadioButton("");
                rdBtnRareItem.addActionListener(selected -> {
                        super.updatePreview(content, this.shopContent.toArray());
                });
                content.add(rdBtnRareItem);
                rdBtnRareItem.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnRareItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnRareItem.setBounds(6, 165, 120, 120);
                shopContent.add(rdBtnRareItem);

                rdBtnRareMonster = new JRadioButton("");
                rdBtnRareMonster.addActionListener(selected -> {
                        super.updatePreview(content, this.shopContent.toArray());
                });
                content.add(rdBtnRareMonster);
                rdBtnRareMonster.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnRareMonster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnRareMonster.setBounds(284, 165, 120, 120);
                shopContent.add(rdBtnRareMonster);

                rdBtnLegendaryItem = new JRadioButton("");
                rdBtnLegendaryItem.addActionListener(selected -> {
                        super.updatePreview(content, this.shopContent.toArray());
                });
                content.add(rdBtnLegendaryItem);
                rdBtnLegendaryItem.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnLegendaryItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnLegendaryItem.setBounds(6, 364, 120, 120);
                shopContent.add(rdBtnLegendaryItem);

                rdBtnLegendaryMonster = new JRadioButton("");
                content.add(rdBtnLegendaryMonster);
                rdBtnLegendaryMonster.addActionListener(selected -> {
                        super.updatePreview(content, this.shopContent.toArray());
                });
                rdBtnLegendaryMonster.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnLegendaryMonster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnLegendaryMonster.setBounds(284, 364, 120, 120);
                shopContent.add(rdBtnLegendaryMonster);

                textPaneCommonItem = new JTextPane();
                textPaneCommonItem.setEditable(false);
                textPaneCommonItem.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneCommonItem.setBounds(138, 6, 120, 120);
                textPaneCommonItem.setBackground(this.getBackground());
                shopContent.add(textPaneCommonItem);

                textPaneCommonMonster = new JTextPane();
                textPaneCommonMonster.setEditable(false);
                textPaneCommonMonster.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneCommonMonster.setBounds(416, 6, 120, 120);
                textPaneCommonMonster.setBackground(this.getBackground());
                shopContent.add(textPaneCommonMonster);

                textPaneRareItem = new JTextPane();
                textPaneRareItem.setEditable(false);
                textPaneRareItem.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneRareItem.setBounds(138, 165, 120, 120);
                textPaneRareItem.setBackground(this.getBackground());
                shopContent.add(textPaneRareItem);

                textPaneRareMonster = new JTextPane();
                textPaneRareMonster.setEditable(false);
                textPaneRareMonster.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneRareMonster.setBounds(416, 165, 120, 120);
                textPaneRareMonster.setBackground(this.getBackground());
                shopContent.add(textPaneRareMonster);

                textPaneLegendaryItem = new JTextPane();
                textPaneLegendaryItem.setEditable(false);
                textPaneLegendaryItem.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneLegendaryItem.setBounds(138, 364, 120, 120);
                textPaneLegendaryItem.setBackground(this.getBackground());
                shopContent.add(textPaneLegendaryItem);

                textPaneLegendaryMonster = new JTextPane();
                textPaneLegendaryMonster.setEditable(false);
                textPaneLegendaryMonster.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneLegendaryMonster.setBounds(416, 364, 120, 120);
                textPaneLegendaryMonster.setBackground(this.getBackground());
                shopContent.add(textPaneLegendaryMonster);

                btnBuy = new JButton("Buy");
                btnBuy.setBounds(690, 487, 295, 47);
                add(btnBuy);
                btnBuy.addActionListener(buy -> {
                        buyEntity();
                });

                update();
        }

        private void updateContent() {
                int commonItem = getEntity(Item.class, Rarity.COMMON);
                updateEntity(rdBtnCommonItem, textPaneCommonItem, commonItem);

                int commonMonster = getEntity(Monster.class, Rarity.COMMON);
                updateEntity(rdBtnCommonMonster, textPaneCommonMonster, commonMonster);

                int rareItem = getEntity(Item.class, Rarity.RARE);
                updateEntity(rdBtnRareItem, textPaneRareItem, rareItem);

                int rareMonster = getEntity(Monster.class, Rarity.RARE);
                updateEntity(rdBtnRareMonster, textPaneRareMonster, rareMonster);

                int legendaryItem = getEntity(Item.class, Rarity.LEGENDARY);
                updateEntity(rdBtnLegendaryItem, textPaneLegendaryItem, legendaryItem);

                int legendaryMonster = getEntity(Monster.class, Rarity.LEGENDARY);
                updateEntity(rdBtnLegendaryMonster, textPaneLegendaryMonster, legendaryMonster);

        }

        private void updateEntity(JRadioButton image, JTextPane desc, int index) {
                if (index == -1) {
                        desc.setText("\n\nSold Out");
                        image.setEnabled(false);
                        image.setActionCommand("-1");
                } else {
                        image.setEnabled(true);
                        image.setIcon(MainContainer.imageResize(shopContent.get(index).getImage(), image.getWidth(),
                                        image.getHeight()));
                        desc.setText("\n" + shopContent.get(index).getBuyPrice() + "G \n"
                                        + shopContent.get(index).getRarity().name() + "\n"
                                        + shopContent.get(index).getName());
                        image.setActionCommand("" + index);
                }

        }

        private int getEntity(Class<?> type, Rarity rarity) {
                for (Entity e : shopContent) {
                        if (type.isInstance(e) && e.getRarity() == rarity) {
                                return shopContent.indexOf(e);
                        }
                }

                return -1;
        }

        private void buyEntity() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Entity entityToBuy = shopContent.get(index);
                        if (entityToBuy instanceof Item) {
                                new PopUp("Info",
                                                MainContainer.game.getBuyShop().buy((Item) entityToBuy),
                                                this.getLocationOnScreen());
                        } else {
                                new PopUp("Info",
                                                MainContainer.game.getBuyShop().buy((Monster) entityToBuy),
                                                this.getLocationOnScreen());
                        }

                        update();

                } else {
                        new PopUp("Error", "Select an Item/Monster", this.getLocationOnScreen());
                }

        }

        public void update() {
                updateContent();
                super.updatePlayerInfo();
                super.selectFirstAvailableButton(this.content);
                super.updatePreview(content, this.shopContent.toArray());
        }
}
