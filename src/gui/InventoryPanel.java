package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import items.Item;
import main.Entity;
import monsters.Monster;

public class InventoryPanel extends EntityViewer implements Updatable {

        private static final long serialVersionUID = 1L;
        private JRadioButton rdBtnEntity1;
        private JRadioButton rdBtnEntity2;
        private JRadioButton rdBtnEntity3;
        private JRadioButton rdBtnEntity4;
        private JRadioButton rdBtnEntity5;
        private JRadioButton rdBtnEntity6;
        private JTextPane textPaneEntity1;
        private JTextPane textPaneEntity2;
        private JTextPane textPaneEntity3;
        private JTextPane textPaneEntity4;
        private JTextPane textPaneEntity5;
        private JTextPane textPaneEntity6;
        // contentButtons maps 1-to-1 with contentDescriptions
        private ArrayList<JRadioButton> contentButtons;
        private ArrayList<JTextPane> contentDescriptions;

        private ArrayList<JRadioButton> monsterButtons;
        private ArrayList<JTextPane> monsterDescriptions;

        private ArrayList<Item> inventory = MainContainer.game.getPlayer().getInventory();
        private final ButtonGroup content = new ButtonGroup();
        private JButton btnUse;
        private final ButtonGroup monsters = new ButtonGroup();

        /**
         * Create the panel.
         */
        public InventoryPanel() {
                super(true, true, true);
                setName("Inventory");

                JLabel lblInventoryTitle = new JLabel("Inventory");
                lblInventoryTitle.setBounds(430, 6, 150, 37);
                lblInventoryTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
                add(lblInventoryTitle);

                JPanel inventoryContent = new JPanel();
                inventoryContent.setBorder(new LineBorder(new Color(0, 0, 0)));
                inventoryContent.setBounds(6, 50, 370, 490);
                inventoryContent.setBackground(this.getBackground());
                add(inventoryContent);
                inventoryContent.setLayout(null);

                rdBtnEntity1 = new JRadioButton("");
                rdBtnEntity1.addActionListener(selected -> {
                        super.updatePreview(content, inventory.toArray());
                });
                content.add(rdBtnEntity1);
                rdBtnEntity1.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity1.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnEntity1.setBounds(6, 9, 70, 70);
                inventoryContent.add(rdBtnEntity1);

                rdBtnEntity2 = new JRadioButton("");
                rdBtnEntity2.addActionListener(selected -> {
                        super.updatePreview(content, inventory.toArray());
                });
                content.add(rdBtnEntity2);
                rdBtnEntity2.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity2.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnEntity2.setBounds(6, 91, 70, 70);
                inventoryContent.add(rdBtnEntity2);

                rdBtnEntity3 = new JRadioButton("");
                rdBtnEntity3.addActionListener(selected -> {
                        super.updatePreview(content, inventory.toArray());
                });
                content.add(rdBtnEntity3);
                rdBtnEntity3.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity3.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnEntity3.setBounds(6, 168, 70, 70);
                inventoryContent.add(rdBtnEntity3);

                rdBtnEntity4 = new JRadioButton("");
                rdBtnEntity4.addActionListener(selected -> {
                        super.updatePreview(content, inventory.toArray());
                });
                content.add(rdBtnEntity4);
                rdBtnEntity4.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity4.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnEntity4.setBounds(6, 250, 70, 70);
                inventoryContent.add(rdBtnEntity4);

                rdBtnEntity5 = new JRadioButton("");
                rdBtnEntity5.addActionListener(selected -> {
                        super.updatePreview(content, inventory.toArray());
                });
                content.add(rdBtnEntity5);
                rdBtnEntity5.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity5.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnEntity5.setBounds(6, 332, 70, 70);
                inventoryContent.add(rdBtnEntity5);

                rdBtnEntity6 = new JRadioButton("");
                content.add(rdBtnEntity6);
                rdBtnEntity6.addActionListener(selected -> {
                        super.updatePreview(content, inventory.toArray());
                });
                rdBtnEntity6.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity6.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnEntity6.setBounds(6, 414, 70, 70);
                inventoryContent.add(rdBtnEntity6);

                textPaneEntity1 = new JTextPane();
                textPaneEntity1.setEditable(false);
                textPaneEntity1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity1.setBounds(88, 20, 265, 70);
                textPaneEntity1.setBackground(this.getBackground());
                inventoryContent.add(textPaneEntity1);

                textPaneEntity2 = new JTextPane();
                textPaneEntity2.setEditable(false);
                textPaneEntity2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity2.setBounds(88, 91, 265, 70);
                textPaneEntity2.setBackground(this.getBackground());
                inventoryContent.add(textPaneEntity2);

                textPaneEntity3 = new JTextPane();
                textPaneEntity3.setEditable(false);
                textPaneEntity3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity3.setBounds(88, 168, 265, 70);
                textPaneEntity3.setBackground(this.getBackground());
                inventoryContent.add(textPaneEntity3);

                textPaneEntity4 = new JTextPane();
                textPaneEntity4.setEditable(false);
                textPaneEntity4.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity4.setBounds(88, 250, 265, 70);
                textPaneEntity4.setBackground(this.getBackground());
                inventoryContent.add(textPaneEntity4);

                textPaneEntity5 = new JTextPane();
                textPaneEntity5.setEditable(false);
                textPaneEntity5.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity5.setBounds(88, 332, 265, 70);
                textPaneEntity5.setBackground(this.getBackground());
                inventoryContent.add(textPaneEntity5);

                textPaneEntity6 = new JTextPane();
                textPaneEntity6.setEditable(false);
                textPaneEntity6.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity6.setBounds(88, 414, 265, 70);
                textPaneEntity6.setBackground(this.getBackground());
                inventoryContent.add(textPaneEntity6);

                contentButtons = new ArrayList<JRadioButton>(Arrays.asList(
                                rdBtnEntity1,
                                rdBtnEntity2,
                                rdBtnEntity3,
                                rdBtnEntity4,
                                rdBtnEntity5,
                                rdBtnEntity6));

                contentDescriptions = new ArrayList<JTextPane>(Arrays.asList(
                                textPaneEntity1,
                                textPaneEntity2,
                                textPaneEntity3,
                                textPaneEntity4,
                                textPaneEntity5,
                                textPaneEntity6));

                btnUse = new JButton("Use");
                btnUse.addActionListener(sell -> {
                        useItem();
                });
                btnUse.setBounds(690, 487, 295, 47);
                add(btnUse);

                JPanel teamMonsters = new JPanel();
                teamMonsters.setBorder(new LineBorder(new Color(0, 0, 0)));
                teamMonsters.setLayout(null);
                teamMonsters.setBackground(Color.GRAY);
                teamMonsters.setBounds(387, 50, 296, 490);
                add(teamMonsters);

                JRadioButton rdBtnMonster1 = new JRadioButton("");
                monsters.add(rdBtnMonster1);
                rdBtnMonster1.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnMonster1.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnMonster1.setBounds(6, 9, 70, 70);
                teamMonsters.add(rdBtnMonster1);

                JRadioButton rdBtnMonster2 = new JRadioButton("");
                monsters.add(rdBtnMonster2);
                rdBtnMonster2.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnMonster2.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnMonster2.setBounds(6, 91, 70, 70);
                teamMonsters.add(rdBtnMonster2);

                JRadioButton rdBtnMonster3 = new JRadioButton("");
                monsters.add(rdBtnMonster3);
                rdBtnMonster3.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnMonster3.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnMonster3.setBounds(6, 168, 70, 70);
                teamMonsters.add(rdBtnMonster3);

                JRadioButton rdBtnMonster4 = new JRadioButton("");
                monsters.add(rdBtnMonster4);
                rdBtnMonster4.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnMonster4.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnMonster4.setBounds(6, 250, 70, 70);
                teamMonsters.add(rdBtnMonster4);

                JRadioButton rdBtnMonster5 = new JRadioButton("");
                monsters.add(rdBtnMonster5);
                rdBtnMonster5.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnMonster5.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnMonster5.setBounds(6, 332, 70, 70);
                teamMonsters.add(rdBtnMonster5);

                JRadioButton rdBtnMonster6 = new JRadioButton("");
                monsters.add(rdBtnMonster6);
                rdBtnMonster6.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnMonster6.setBorder(new LineBorder(new Color(0, 0, 0)));
                rdBtnMonster6.setBounds(6, 414, 70, 70);
                teamMonsters.add(rdBtnMonster6);

                JTextPane textPaneMonster1 = new JTextPane();
                textPaneMonster1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneMonster1.setEditable(false);
                textPaneMonster1.setBackground(Color.GRAY);
                textPaneMonster1.setBounds(88, 30, 200, 70);
                teamMonsters.add(textPaneMonster1);

                JTextPane textPaneMonster2 = new JTextPane();
                textPaneMonster2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneMonster2.setEditable(false);
                textPaneMonster2.setBackground(Color.GRAY);
                textPaneMonster2.setBounds(88, 91, 200, 70);
                teamMonsters.add(textPaneMonster2);

                JTextPane textPaneMonster3 = new JTextPane();
                textPaneMonster3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneMonster3.setEditable(false);
                textPaneMonster3.setBackground(Color.GRAY);
                textPaneMonster3.setBounds(88, 168, 200, 70);
                teamMonsters.add(textPaneMonster3);

                JTextPane textPaneMonster4 = new JTextPane();
                textPaneMonster4.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneMonster4.setEditable(false);
                textPaneMonster4.setBackground(Color.GRAY);
                textPaneMonster4.setBounds(88, 250, 200, 70);
                teamMonsters.add(textPaneMonster4);

                JTextPane textPaneMonster5 = new JTextPane();
                textPaneMonster5.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneMonster5.setEditable(false);
                textPaneMonster5.setBackground(Color.GRAY);
                textPaneMonster5.setBounds(88, 332, 200, 70);
                teamMonsters.add(textPaneMonster5);

                JTextPane textPaneMonster6 = new JTextPane();
                textPaneMonster6.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneMonster6.setEditable(false);
                textPaneMonster6.setBackground(Color.GRAY);
                textPaneMonster6.setBounds(88, 414, 200, 70);
                teamMonsters.add(textPaneMonster6);

                monsterButtons = new ArrayList<JRadioButton>(Arrays.asList(
                                rdBtnMonster1,
                                rdBtnMonster2,
                                rdBtnMonster3,
                                rdBtnMonster4,
                                rdBtnMonster5,
                                rdBtnMonster6));

                monsterDescriptions = new ArrayList<JTextPane>(Arrays.asList(
                                textPaneMonster1,
                                textPaneMonster2,
                                textPaneMonster3,
                                textPaneMonster4,
                                textPaneMonster5,
                                textPaneMonster6));

                JLabel lblMonsters = new JLabel("Target Monster");
                lblMonsters.setBounds(135, 6, 101, 16);
                inventoryContent.add(lblMonsters);

                JLabel lblItems = new JLabel("Select Item");
                lblItems.setBounds(135, 6, 76, 37);
                teamMonsters.add(lblItems);
        }

        private void updateContent() {
                for (int i = 0; i < contentButtons.size(); i++) {
                        if (i >= inventory.size()) {
                                updateEntity(contentButtons.get(i), contentDescriptions.get(i), null);
                        } else {
                                updateEntity(contentButtons.get(i), contentDescriptions.get(i), inventory.get(i));
                        }
                }

        }

        private void useItem() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1"
                                && monsters.getSelection() != null
                                && monsters.getSelection().getActionCommand() != "-1") {
                        int itemIndex = Integer.parseInt(content.getSelection().getActionCommand());
                        int monsterIndex = Integer.parseInt(monsters.getSelection().getActionCommand());
                        Item itemToUse = inventory.get(itemIndex);
                        Monster monsterAffected = MainContainer.game.getPlayer().getTeam().getMonsters()
                                        .get(monsterIndex);
                        new PopUp("Info", MainContainer.game.getPlayer().useItem(itemToUse, monsterAffected),
                                        this.getLocationOnScreen());

                        update();

                } else {
                        new PopUp("Error", "Select an Item/Monster", this.getLocationOnScreen());
                }

        }

        private void updateEntity(JRadioButton image, JTextPane desc, Entity e) {
                if (e == null) {
                        desc.setText("");
                        // TODO: Remove Image
                        image.setEnabled(false);
                        image.setActionCommand("-1");
                        image.setVisible(false);
                } else {
                        image.setEnabled(true);
                        image.setText(e.getName() + " Image");
                        desc.setText("\n" + e.getRarity().name() + " " + e.getName());
                        int index = (e instanceof Item) ? inventory.indexOf(e)
                                        : MainContainer.game.getPlayer().getTeam().getMonsters().indexOf(e);
                        image.setActionCommand("" + index);
                        image.setVisible(true);
                }

        }

        private void updateTeam() {
                ArrayList<Monster> team = MainContainer.game.getPlayer().getTeam().getMonsters();

                monsters.clearSelection();
                for (int i = 0; i < monsterButtons.size(); i++) {
                        if (i >= team.size()) {
                                updateEntity(monsterButtons.get(i), monsterDescriptions.get(i), null);
                        } else {
                                updateEntity(monsterButtons.get(i), monsterDescriptions.get(i), team.get(i));
                                monsterButtons.get(i).setEnabled(true);
                        }

                        if (content.getSelection() == null) {
                                monsterButtons.get(i).setEnabled(false);
                        }
                }

        }

        public void update() {
                updateContent();
                super.updatePlayerInfo();
                super.selectFirstAvailableButton(content);
                updateTeam();
                super.updatePreview(content, inventory.toArray());

        }
}
