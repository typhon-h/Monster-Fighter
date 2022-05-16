package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import items.Item;
import main.Entity;
import main.Rarity;
import monsters.Monster;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

public class BuyShopPanel extends JPanel implements Updatable {

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
        private JPanel preview;
        private JLabel lblPreviewEntityImg;
        private JTextPane textPanePreviewEntityDesc;
        private JButton btnBuy;
        private JLabel lblGold;
        private JLabel lblPlayerGold;

        /**
         * Create the panel.
         */
        public BuyShopPanel() {
                super();
                setName("BuyShop");
                setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
                setVerifyInputWhenFocusTarget(false);
                this.setBackground(Color.GRAY);
                setLayout(null);

                JLabel lblBuyShopTitle = new JLabel("Buy Shop");
                lblBuyShopTitle.setBounds(395, 6, 150, 37);
                lblBuyShopTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
                add(lblBuyShopTitle);

                JPanel shopContent = new JPanel();
                shopContent.setBounds(6, 44, 551, 490);
                shopContent.setBackground(this.getBackground());
                add(shopContent);
                shopContent.setLayout(null);

                rdBtnCommonItem = new JRadioButton("");
                rdBtnCommonItem.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnCommonItem);
                rdBtnCommonItem.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnCommonItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnCommonItem.setBounds(6, 6, 120, 120);
                shopContent.add(rdBtnCommonItem);

                rdBtnCommonMonster = new JRadioButton("");
                rdBtnCommonMonster.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnCommonMonster);
                rdBtnCommonMonster.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnCommonMonster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnCommonMonster.setBounds(284, 6, 120, 120);
                shopContent.add(rdBtnCommonMonster);

                rdBtnRareItem = new JRadioButton("");
                rdBtnRareItem.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnRareItem);
                rdBtnRareItem.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnRareItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnRareItem.setBounds(6, 165, 120, 120);
                shopContent.add(rdBtnRareItem);

                rdBtnRareMonster = new JRadioButton("");
                rdBtnRareMonster.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnRareMonster);
                rdBtnRareMonster.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnRareMonster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnRareMonster.setBounds(284, 165, 120, 120);
                shopContent.add(rdBtnRareMonster);

                rdBtnLegendaryItem = new JRadioButton("");
                rdBtnLegendaryItem.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnLegendaryItem);
                rdBtnLegendaryItem.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnLegendaryItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnLegendaryItem.setBounds(6, 364, 120, 120);
                shopContent.add(rdBtnLegendaryItem);

                rdBtnLegendaryMonster = new JRadioButton("");
                content.add(rdBtnLegendaryMonster);
                rdBtnLegendaryMonster.addActionListener(selected -> {
                        updatePreview();
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

                preview = new JPanel();
                preview.setBorder(new LineBorder(new Color(0, 0, 0)));
                preview.setBounds(569, 44, 385, 490);
                preview.setBackground(this.getBackground());
                add(preview);
                preview.setLayout(null);

                lblPreviewEntityImg = new JLabel("Selected Entity Image");
                lblPreviewEntityImg.setBounds(98, 6, 200, 200);
                preview.add(lblPreviewEntityImg);

                textPanePreviewEntityDesc = new JTextPane();
                textPanePreviewEntityDesc.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
                textPanePreviewEntityDesc.setEditable(false);
                textPanePreviewEntityDesc.setBounds(6, 249, 373, 176);
                textPanePreviewEntityDesc.setBackground(this.getBackground());
                preview.add(textPanePreviewEntityDesc);

                btnBuy = new JButton("Buy");
                btnBuy.addActionListener(buy -> {
                        buyEntity();
                });
                btnBuy.setBounds(6, 437, 379, 47);
                preview.add(btnBuy);

                JButton btnBack = new JButton("Back");
                btnBack.addActionListener(back -> {
                        MainContainer.showScreen("MainMenu");
                });
                btnBack.setBounds(6, 6, 82, 40);
                add(btnBack);

                lblGold = new JLabel("Gold:");
                lblGold.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
                lblGold.setBounds(818, 6, 51, 26);
                add(lblGold);

                lblPlayerGold = new JLabel("" + MainContainer.game.getPlayer().getGold());
                lblPlayerGold.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
                lblPlayerGold.setBounds(881, 6, 61, 26);
                add(lblPlayerGold);

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

        private void selectFirstAvailableEntity() {
                ArrayList<JRadioButton> options = new ArrayList<JRadioButton>(Arrays.asList(
                                rdBtnCommonItem,
                                rdBtnCommonMonster,
                                rdBtnRareItem,
                                rdBtnRareMonster,
                                rdBtnLegendaryItem,
                                rdBtnLegendaryMonster));

                for (JRadioButton btn : options) {
                        if (btn.getActionCommand() != "-1") {
                                btn.setSelected(true);
                                return;
                        } else {
                                btn.setSelected(false);
                        }
                }

        }

        private void buyEntity() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Entity entityToBuy = shopContent.get(index);
                        InfoPopUp buyFeedback;
                        if (entityToBuy instanceof Item) {
                                buyFeedback = new InfoPopUp(MainContainer.game.getBuyShop().buy((Item) entityToBuy));
                        } else {
                                buyFeedback = new InfoPopUp(MainContainer.game.getBuyShop().buy((Monster) entityToBuy));
                        }

                        buyFeedback.setVisible(true);
                        update();

                } else {
                        ErrorPopUp noSelection = new ErrorPopUp("Select an Item/Monster");
                        noSelection.setVisible(true);
                }

        }

        private void updateEntity(JRadioButton image, JTextPane desc, int index) {
                if (index == -1) {
                        desc.setText("\n\nPurchased");
                        // TODO: Remove Image
                        image.setEnabled(false);
                        image.setActionCommand("-1");
                } else {
                        image.setEnabled(true);
                        image.setText(shopContent.get(index).getName() + " Image");
                        desc.setText("\n" + shopContent.get(index).getBuyPrice() + "G \n"
                                        + shopContent.get(index).getRarity().name() + "\n"
                                        + shopContent.get(index).getName());
                        image.setActionCommand("" + index);
                }

        }

        private void updatePreview() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Entity entity = shopContent.get(index);
                        lblPreviewEntityImg.setText(entity.getName() + " Image");
                        textPanePreviewEntityDesc.setText(entity.toString());
                        btnBuy.setEnabled(true);
                } else {
                        lblPreviewEntityImg.setText("Shop is Empty");
                        textPanePreviewEntityDesc.setText("");
                        btnBuy.setEnabled(false);
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

        public void update() {
                lblPlayerGold.setText("" + MainContainer.game.getPlayer().getGold());
                updateContent();
                selectFirstAvailableEntity();
                updatePreview();
        }
}
