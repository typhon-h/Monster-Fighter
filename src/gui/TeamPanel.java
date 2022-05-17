package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import main.Entity;
import monsters.Monster;
import static gui.MainContainer.DEFAULTDIMENSION;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

public class TeamPanel extends JPanel implements Updatable {

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
        private JPanel preview;
        private JLabel lblPreviewEntityImg;
        private JTextPane textPanePreviewEntityDesc;
        private JButton btnMoveDown;

        /**
         * Create the panel.
         */
        public TeamPanel() {
                super();
                setName("Team");
                setMinimumSize(DEFAULTDIMENSION);
                setSize(DEFAULTDIMENSION);
                setVerifyInputWhenFocusTarget(false);
                this.setBackground(Color.GRAY);
                setLayout(null);

                JLabel lblSellShopTitle = new JLabel("Team");
                lblSellShopTitle.setBounds(395, 6, 150, 37);
                lblSellShopTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
                add(lblSellShopTitle);

                JPanel shopContent = new JPanel();
                shopContent.setBounds(6, 44, 556, 490);
                shopContent.setBackground(this.getBackground());
                add(shopContent);
                shopContent.setLayout(null);

                rdBtnEntity1 = new JRadioButton("");
                rdBtnEntity1.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnEntity1);
                rdBtnEntity1.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnEntity1.setBounds(23, 7, 120, 120);
                shopContent.add(rdBtnEntity1);

                rdBtnEntity2 = new JRadioButton("");
                rdBtnEntity2.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnEntity2);
                rdBtnEntity2.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnEntity2.setBounds(23, 129, 120, 120);
                shopContent.add(rdBtnEntity2);

                rdBtnEntity3 = new JRadioButton("");
                rdBtnEntity3.addActionListener(selected -> {
                        updatePreview();
                });
                content.add(rdBtnEntity3);
                rdBtnEntity3.setHorizontalAlignment(SwingConstants.CENTER);
                rdBtnEntity3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
                rdBtnEntity3.setBounds(23, 250, 120, 120);
                shopContent.add(rdBtnEntity3);

                rdBtnEntity4 = new JRadioButton("");
                rdBtnEntity4.addActionListener(selected -> {
                        updatePreview();
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

                contentButtons      = new ArrayList<JRadioButton>(Arrays.asList(
                                rdBtnEntity1,
                                rdBtnEntity2,
                                rdBtnEntity3,
                                rdBtnEntity4));

                contentDescriptions = new ArrayList<JTextPane>(Arrays.asList(
                                textPaneEntity1,
                                textPaneEntity2,
                                textPaneEntity3,
                                textPaneEntity4));

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

                btnMoveDown = new JButton("Move Down");
                btnMoveDown.addActionListener(moveDown -> {
                        moveDown();
                });
                btnMoveDown.setBounds(33, 437, 137, 47);
                preview.add(btnMoveDown);

                JButton btnMoveUp = new JButton("Move Up");
                btnMoveUp.setBounds(219, 437, 137, 47);
                btnMoveUp.addActionListener(moveUp -> {
                        moveUp();
                });
                preview.add(btnMoveUp);

                JButton btnBack = new JButton("Back");
                btnBack.addActionListener(back -> {
                        MainContainer.showScreen("MainMenu");
                });
                btnBack.setBounds(6, 3, 82, 40);
                add(btnBack);

                updateContent();
                selectFirstAvailableEntity();
                updatePreview();
        }

        public void updateContent() {
                for (int i = 0; i < contentButtons.size(); i++) {
                        updateEntity(contentButtons.get(i), contentDescriptions.get(i), i);
                }

        }

        private void selectFirstAvailableEntity() {

                for (JRadioButton btn : contentButtons) {
                        if (btn.getActionCommand() != "-1") {
                                btn.setSelected(true);
                                return;
                        } else {
                                content.clearSelection();
                        }
                }

        }

        private void moveUp() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Monster monsterToMove = team.get(index);
                        InfoPopUp moveFeedback;
                        if (index == 0) {
                                moveFeedback = new InfoPopUp(monsterToMove.getName() + " is already first");
                        } else {
                                MainContainer.game.getPlayer().getTeam().moveMonsterUp(monsterToMove);
                                moveFeedback = new InfoPopUp(monsterToMove.getName() + " has been moved up");
                        }
                        Point point = this.getLocationOnScreen();
                        moveFeedback.setLocation(point.x +
                                          (gui.MainContainer.SCREENWIDTH / 2) -
                                          moveFeedback.getWidth() / 2,
                                          point.y +
                                          (gui.MainContainer.SCREENHEIGHT / 2) -
                                          moveFeedback.getHeight() / 2);
                        moveFeedback.setVisible(true);
                        update();

                } else {
                        ErrorPopUp noSelection = new ErrorPopUp("Select a Monster");
                        Point point = this.getLocationOnScreen();
                        noSelection.setLocation(point.x +
                                          (gui.MainContainer.SCREENWIDTH / 2) -
                                          noSelection.getWidth() / 2,
                                          point.y +
                                          (gui.MainContainer.SCREENHEIGHT / 2) -
                                          noSelection.getHeight() / 2);
                        noSelection.setVisible(true);
                }

        }

        private void moveDown() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Monster monsterToMove = team.get(index);
                        InfoPopUp moveFeedback;
                        if (index == team.size() - 1) {
                                moveFeedback = new InfoPopUp(monsterToMove.getName() + " is already last");
                        } else {
                                MainContainer.game.getPlayer().getTeam().moveMonsterDown(monsterToMove);
                                moveFeedback = new InfoPopUp(monsterToMove.getName() + " has been moved down");
                        }
                        Point point = this.getLocationOnScreen();
                        moveFeedback.setLocation(point.x +
                                          (gui.MainContainer.SCREENWIDTH / 2) -
                                          moveFeedback.getWidth() / 2,
                                          point.y +
                                          (gui.MainContainer.SCREENHEIGHT / 2) -
                                          moveFeedback.getHeight() / 2);
                        moveFeedback.setVisible(true);
                        moveFeedback.setVisible(true);
                        update();

                } else {
                        ErrorPopUp noSelection = new ErrorPopUp("Select a Monster");
                        Point point = this.getLocationOnScreen();
                        noSelection.setLocation(point.x +
                                          (gui.MainContainer.SCREENWIDTH / 2) -
                                          noSelection.getWidth() / 2,
                                          point.y +
                                          (gui.MainContainer.SCREENHEIGHT / 2) -
                                          noSelection.getHeight() / 2);
                        noSelection.setVisible(true);
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

        private void updatePreview() {
                if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
                        int index = Integer.parseInt(content.getSelection().getActionCommand());
                        Entity entity = team.get(index);
                        lblPreviewEntityImg.setText(entity.getName() + " Image");
                        textPanePreviewEntityDesc.setText(entity.toString());
                        btnMoveDown.setEnabled(true);
                } else {
                        lblPreviewEntityImg.setText("Shop is Empty");
                        textPanePreviewEntityDesc.setText("");
                        btnMoveDown.setEnabled(false);
                }

        }

        public void update() {
                updateContent();
                selectFirstAvailableEntity();
                updatePreview();
        }
}
