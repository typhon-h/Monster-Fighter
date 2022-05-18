package gui;

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
import javax.swing.border.EtchedBorder;

import monsters.Monster;

public class TeamPanel extends EntityViewer implements Updatable {

        private static final long serialVersionUID = 1L;
        private JRadioButton rdBtnEntity1;
        private JRadioButton rdBtnEntity2;
        private JRadioButton rdBtnEntity3;
        private JRadioButton rdBtnEntity4;
        private JTextPane textPaneEntity1;
        private JTextPane textPaneEntity2;
        private JTextPane textPaneEntity3;
        private JTextPane textPaneEntity4;
        // contentButtons maps 1-to-1 with contentDescriptions
        private ArrayList<JRadioButton> contentButtons;
        private ArrayList<JTextPane> contentDescriptions;

        private ArrayList<Monster> team = MainContainer.game.getPlayer().getTeam().getMonsters();
        private final ButtonGroup content = new ButtonGroup();
        private JButton btnMoveDown;
        private JButton btnMoveUp;

        /**
         * Create the panel.
         */
        public TeamPanel() {
                super(true, true, true);
                setName("Team");

                JLabel lblSellShopTitle = new JLabel("Team");
                lblSellShopTitle.setBounds(430, 6, 150, 37);
                lblSellShopTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
                add(lblSellShopTitle);

                JPanel shopContent = new JPanel();
                shopContent.setBounds(6, 44, 556, 490);
                shopContent.setBackground(this.getBackground());
                add(shopContent);
                shopContent.setLayout(null);

                rdBtnEntity1 = new JRadioButton("");
                rdBtnEntity1.addActionListener(selected -> {
                        super.updatePreview(content, team.toArray());
                });
                content.add(rdBtnEntity1);
                rdBtnEntity1.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnEntity1.setBounds(23, 7, 120, 120);
                shopContent.add(rdBtnEntity1);

                rdBtnEntity2 = new JRadioButton("");
                rdBtnEntity2.addActionListener(selected -> {
                        super.updatePreview(content, team.toArray());
                });
                content.add(rdBtnEntity2);
                rdBtnEntity2.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnEntity2.setBounds(23, 129, 120, 120);
                shopContent.add(rdBtnEntity2);

                rdBtnEntity3 = new JRadioButton("");
                rdBtnEntity3.addActionListener(selected -> {
                        super.updatePreview(content, team.toArray());
                });
                content.add(rdBtnEntity3);
                rdBtnEntity3.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnEntity3.setBounds(23, 250, 120, 120);
                shopContent.add(rdBtnEntity3);

                rdBtnEntity4 = new JRadioButton("");
                rdBtnEntity4.addActionListener(selected -> {
                        super.updatePreview(content, team.toArray());
                });
                content.add(rdBtnEntity4);
                rdBtnEntity4.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnEntity4.setBounds(23, 372, 120, 120);
                shopContent.add(rdBtnEntity4);

                textPaneEntity1 = new JTextPane();
                textPaneEntity1.setEditable(false);
                textPaneEntity1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity1.setBounds(155, 7, 120, 120);
                textPaneEntity1.setBackground(this.getBackground());
                shopContent.add(textPaneEntity1);

                textPaneEntity2 = new JTextPane();
                textPaneEntity2.setEditable(false);
                textPaneEntity2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity2.setBounds(155, 129, 120, 120);
                textPaneEntity2.setBackground(this.getBackground());
                shopContent.add(textPaneEntity2);

                textPaneEntity3 = new JTextPane();
                textPaneEntity3.setEditable(false);
                textPaneEntity3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity3.setBounds(155, 250, 120, 120);
                textPaneEntity3.setBackground(this.getBackground());
                shopContent.add(textPaneEntity3);

                textPaneEntity4 = new JTextPane();
                textPaneEntity4.setEditable(false);
                textPaneEntity4.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
                textPaneEntity4.setBounds(156, 373, 120, 120);
                textPaneEntity4.setBackground(this.getBackground());
                shopContent.add(textPaneEntity4);

                contentButtons = new ArrayList<JRadioButton>(Arrays.asList(
                                rdBtnEntity1,
                                rdBtnEntity2,
                                rdBtnEntity3,
                                rdBtnEntity4));

                contentDescriptions = new ArrayList<JTextPane>(Arrays.asList(
                                textPaneEntity1,
                                textPaneEntity2,
                                textPaneEntity3,
                                textPaneEntity4));

                btnMoveDown = new JButton("Move Down");
                btnMoveDown.addActionListener(moveDown -> {
                        moveDown();
                });
                btnMoveDown.setBounds(690, 487, 137, 47);
                add(btnMoveDown);

                btnMoveUp = new JButton("Move Up");
                btnMoveUp.addActionListener(moveUp -> {
                        moveUp();
                });
                btnMoveUp.setBounds(848, 487, 137, 47);
                add(btnMoveUp);

                update();
        }

        public void updateContent() {
                for (int i = 0; i < contentButtons.size(); i++) {
                        updateEntity(contentButtons.get(i), contentDescriptions.get(i), i);
                }

        }

        private void moveUp() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Monster monsterToMove = team.get(index);
                        if (index == 0) {
                                new PopUp("Info", monsterToMove.getName() + " is already first",
                                                this.getLocationOnScreen());
                        } else {
                                MainContainer.game.getPlayer().getTeam().moveMonsterUp(monsterToMove);
                                new PopUp("Info", monsterToMove.getName() + " has been moved up",
                                                this.getLocationOnScreen());
                        }

                        update();

                } else {
                        new PopUp("Error", "Select a Monster", this.getLocationOnScreen());
                }

        }

        private void moveDown() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Monster monsterToMove = team.get(index);
                        if (index == team.size() - 1) {
                                new PopUp("Info", monsterToMove.getName() + " is already last",
                                                this.getLocationOnScreen());
                        } else {
                                MainContainer.game.getPlayer().getTeam().moveMonsterDown(monsterToMove);
                                new PopUp("Info", monsterToMove.getName() + " has been moved down",
                                                this.getLocationOnScreen());
                        }

                        update();

                } else {
                        new PopUp("Error", "Select a Monster", this.getLocationOnScreen());
                }

        }

        private void updateEntity(JRadioButton image, JTextPane desc, int index) {
                if (index == -1 || index >= team.size()) {
                        desc.setText("");
                        // TODO: Remove Image
                        image.setEnabled(false);
                        image.setActionCommand("-1");
                        image.setVisible(false);
                } else {
                        image.setEnabled(true);
                        image.setText(team.get(index).getName() + " Image");
                        desc.setText("\n" + team.get(index).getRarity().name() + "\n"
                                        + team.get(index).getName());
                        image.setActionCommand("" + index);
                        image.setVisible(true);
                }

        }

        public void update() {
                updateContent();
                super.updatePlayerInfo();
                super.selectFirstAvailableButton(content);
                super.updatePreview(content, team.toArray());
        }
}
