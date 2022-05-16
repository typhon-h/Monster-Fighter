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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SellShopPanel extends JPanel implements Updatable {

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
    private ArrayList<Entity> shopContent;

    private final ButtonGroup content = new ButtonGroup();
    private JPanel preview;
    private JLabel lblPreviewEntityImg;
    private JTextPane textPanePreviewEntityDesc;
    private JButton btnSell;
    private JLabel lblGold;
    private JLabel lblPlayerGold;
    private JTextPane textPaneEntity12;
    private JRadioButton rdBtnEntity12;
    private JRadioButton rdBtnEntity11;
    private JTextPane textPaneEntity11;
    private JRadioButton rdBtnEntity9;
    private JTextPane textPaneEntity9;
    private JRadioButton rdBtnEntity10;
    private JTextPane textPaneEntity10;
    private JTextPane textPaneEntity8;
    private JRadioButton rdBtnEntity8;
    private JTextPane textPaneEntity7;
    private JRadioButton rdBtnEntity7;
    private JScrollPane scrollPane;

    /**
     * Create the panel.
     */
    public SellShopPanel() {
        super();
        setName("SellShop");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);

        JLabel lblSellShopTitle = new JLabel("Sell Shop");
        lblSellShopTitle.setBounds(395, 6, 150, 37);
        lblSellShopTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblSellShopTitle);

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        scrollPane.setBounds(6, 44, 560, 490);
        add(scrollPane);

        JPanel entities = new JPanel();
        entities.setPreferredSize(new Dimension(480, 950));
        scrollPane.setViewportView(entities);
        entities.setBackground(this.getBackground());
        entities.setLayout(null);

        rdBtnEntity1 = new JRadioButton("");
        rdBtnEntity1.addActionListener(selected -> {
            updatePreview();
        });
        content.add(rdBtnEntity1);
        rdBtnEntity1.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity1.setBounds(6, 6, 120, 120);
        entities.add(rdBtnEntity1);

        rdBtnEntity2 = new JRadioButton("");
        rdBtnEntity2.addActionListener(selected -> {
            updatePreview();
        });
        content.add(rdBtnEntity2);
        rdBtnEntity2.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity2.setBounds(284, 6, 120, 120);
        entities.add(rdBtnEntity2);

        rdBtnEntity3 = new JRadioButton("");
        rdBtnEntity3.addActionListener(selected -> {
            updatePreview();
        });
        content.add(rdBtnEntity3);
        rdBtnEntity3.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity3.setBounds(6, 165, 120, 120);
        entities.add(rdBtnEntity3);

        rdBtnEntity4 = new JRadioButton("");
        rdBtnEntity4.addActionListener(selected -> {
            updatePreview();
        });
        content.add(rdBtnEntity4);
        rdBtnEntity4.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity4.setBounds(284, 165, 120, 120);
        entities.add(rdBtnEntity4);

        rdBtnEntity5 = new JRadioButton("");
        rdBtnEntity5.addActionListener(selected -> {
            updatePreview();
        });
        content.add(rdBtnEntity5);
        rdBtnEntity5.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity5.setBounds(6, 360, 120, 120);
        entities.add(rdBtnEntity5);

        rdBtnEntity6 = new JRadioButton("");
        content.add(rdBtnEntity6);
        rdBtnEntity6.addActionListener(selected -> {
            updatePreview();
        });
        rdBtnEntity6.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity6.setBounds(284, 364, 120, 120);
        entities.add(rdBtnEntity6);

        rdBtnEntity7 = new JRadioButton("");
        content.add(rdBtnEntity7);
        rdBtnEntity7.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity7.setBounds(6, 530, 120, 120);
        entities.add(rdBtnEntity7);

        rdBtnEntity8 = new JRadioButton("");
        content.add(rdBtnEntity8);
        rdBtnEntity8.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity8.setBounds(284, 530, 120, 120);
        entities.add(rdBtnEntity8);

        rdBtnEntity9 = new JRadioButton("");
        content.add(rdBtnEntity9);
        rdBtnEntity9.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity9.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity9.setBounds(6, 689, 120, 120);
        entities.add(rdBtnEntity9);

        rdBtnEntity10 = new JRadioButton("");
        content.add(rdBtnEntity10);
        rdBtnEntity10.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity10.setBounds(284, 689, 120, 120);
        entities.add(rdBtnEntity10);

        rdBtnEntity11 = new JRadioButton("");
        content.add(rdBtnEntity11);
        rdBtnEntity11.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity11.setBounds(6, 848, 120, 120);
        entities.add(rdBtnEntity11);

        rdBtnEntity12 = new JRadioButton("");
        content.add(rdBtnEntity12);
        rdBtnEntity12.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity12.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity12.setBounds(284, 848, 120, 120);
        entities.add(rdBtnEntity12);

        textPaneEntity1 = new JTextPane();
        textPaneEntity1.setEditable(false);
        textPaneEntity1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity1.setBounds(138, 6, 120, 120);
        textPaneEntity1.setBackground(this.getBackground());
        entities.add(textPaneEntity1);

        textPaneEntity2 = new JTextPane();
        textPaneEntity2.setEditable(false);
        textPaneEntity2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity2.setBounds(416, 6, 120, 120);
        textPaneEntity2.setBackground(this.getBackground());
        entities.add(textPaneEntity2);

        textPaneEntity3 = new JTextPane();
        textPaneEntity3.setEditable(false);
        textPaneEntity3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity3.setBounds(138, 165, 120, 120);
        textPaneEntity3.setBackground(this.getBackground());
        entities.add(textPaneEntity3);

        textPaneEntity4 = new JTextPane();
        textPaneEntity4.setEditable(false);
        textPaneEntity4.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity4.setBounds(416, 165, 120, 120);
        textPaneEntity4.setBackground(this.getBackground());
        entities.add(textPaneEntity4);

        textPaneEntity5 = new JTextPane();
        textPaneEntity5.setEditable(false);
        textPaneEntity5.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity5.setBounds(138, 364, 120, 120);
        textPaneEntity5.setBackground(this.getBackground());
        entities.add(textPaneEntity5);

        textPaneEntity6 = new JTextPane();
        textPaneEntity6.setEditable(false);
        textPaneEntity6.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity6.setBounds(416, 364, 120, 120);
        textPaneEntity6.setBackground(this.getBackground());
        entities.add(textPaneEntity6);

        textPaneEntity7 = new JTextPane();
        textPaneEntity7.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity7.setEditable(false);
        textPaneEntity7.setBackground(Color.GRAY);
        textPaneEntity7.setBounds(138, 530, 120, 120);
        entities.add(textPaneEntity7);

        textPaneEntity8 = new JTextPane();
        textPaneEntity8.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity8.setEditable(false);
        textPaneEntity8.setBackground(Color.GRAY);
        textPaneEntity8.setBounds(416, 530, 120, 120);
        entities.add(textPaneEntity8);

        textPaneEntity9 = new JTextPane();
        textPaneEntity9.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity9.setEditable(false);
        textPaneEntity9.setBackground(Color.GRAY);
        textPaneEntity9.setBounds(138, 689, 120, 120);
        entities.add(textPaneEntity9);

        textPaneEntity10 = new JTextPane();
        textPaneEntity10.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity10.setEditable(false);
        textPaneEntity10.setBackground(Color.GRAY);
        textPaneEntity10.setBounds(416, 689, 120, 120);
        entities.add(textPaneEntity10);

        textPaneEntity11 = new JTextPane();
        textPaneEntity11.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity11.setEditable(false);
        textPaneEntity11.setBackground(Color.GRAY);
        textPaneEntity11.setBounds(138, 848, 120, 120);
        entities.add(textPaneEntity11);

        textPaneEntity12 = new JTextPane();
        textPaneEntity12.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPaneEntity12.setEditable(false);
        textPaneEntity12.setBackground(Color.GRAY);
        textPaneEntity12.setBounds(416, 848, 120, 120);
        entities.add(textPaneEntity12);

        contentButtons = new ArrayList<JRadioButton>(Arrays.asList(
                rdBtnEntity1,
                rdBtnEntity2,
                rdBtnEntity3,
                rdBtnEntity4,
                rdBtnEntity5,
                rdBtnEntity6,
                rdBtnEntity7,
                rdBtnEntity8,
                rdBtnEntity9,
                rdBtnEntity10,
                rdBtnEntity11,
                rdBtnEntity12));

        contentDescriptions = new ArrayList<JTextPane>(Arrays.asList(
                textPaneEntity1,
                textPaneEntity2,
                textPaneEntity3,
                textPaneEntity4,
                textPaneEntity5,
                textPaneEntity6,
                textPaneEntity7,
                textPaneEntity8,
                textPaneEntity9,
                textPaneEntity10,
                textPaneEntity11,
                textPaneEntity12));

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

        btnSell = new JButton("Sell");
        btnSell.addActionListener(sell -> {
            sellEntity();
        });
        btnSell.setBounds(6, 437, 379, 47);
        preview.add(btnSell);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(back -> {
            MainContainer.showScreen("MainMenu");
        });
        btnBack.setBounds(6, 3, 82, 40);
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

    public void updateContent() {
        for (int i = contentButtons.size() - 1; i >= 0; i--) {
            updateEntity(contentButtons.get(i), contentDescriptions.get(i), i);
        }
        textPaneEntity1.setCaretPosition(0);
    }

    private void selectFirstAvailableEntity() {
        ArrayList<JRadioButton> options = new ArrayList<JRadioButton>(Arrays.asList(
                rdBtnEntity1,
                rdBtnEntity2,
                rdBtnEntity3,
                rdBtnEntity4,
                rdBtnEntity5,
                rdBtnEntity6,
                rdBtnEntity7,
                rdBtnEntity8,
                rdBtnEntity9,
                rdBtnEntity10,
                rdBtnEntity11,
                rdBtnEntity12));

        for (JRadioButton btn : options) {
            if (btn.getActionCommand() != "-1") {
                btn.setSelected(true);
                return;
            } else {
                btn.setSelected(false);
            }
        }

    }

    private void sellEntity() {
        if (content.getSelection().getActionCommand() != "-1") {
            int index = Integer.parseInt(content.getSelection().getActionCommand());
            Entity entityToSell = shopContent.get(index);
            InfoPopUp sellFeedback;
            if (entityToSell instanceof Item) {
                sellFeedback = new InfoPopUp(MainContainer.game.getSellShop().sell((Item) entityToSell));
            } else {
                sellFeedback = new InfoPopUp(MainContainer.game.getSellShop().sell((Monster) entityToSell));
            }

            sellFeedback.setVisible(true);
            lblPlayerGold.setText("" + MainContainer.game.getPlayer().getGold());
            update();

        } else {
            ErrorPopUp noSelection = new ErrorPopUp("Select an Item/Monster");
            noSelection.setVisible(true);
        }

    }

    private void updateEntity(JRadioButton image, JTextPane desc, int index) {
        try {
            Entity entity = shopContent.get(index);
            image.setEnabled(true);
            image.setText(entity.getName() + " Image");
            desc.setText("\n" + entity.getSellPrice() + "G \n"
                    + entity.getRarity().name() + "\n"
                    + entity.getName());
            image.setActionCommand("" + index);

        } catch (IndexOutOfBoundsException e) {
            desc.setText("\n\nEmpty");
            // TODO: Remove Image
            image.setEnabled(false);
            image.setActionCommand("-1");
        }

    }

    private void updatePreview() {
        if (content.getSelection().getActionCommand() != "-1") {
            int index = Integer.parseInt(content.getSelection().getActionCommand());
            Entity entity = shopContent.get(index);
            lblPreviewEntityImg.setText(entity.getName() + " Image");
            textPanePreviewEntityDesc.setText(entity.toString());
            btnSell.setEnabled(true);
        } else {
            lblPreviewEntityImg.setText("Shop is Empty");
            textPanePreviewEntityDesc.setText("");
            btnSell.setEnabled(false);
        }

    }

    public void update() {
        shopContent = MainContainer.game.getSellShop().getContent();
        lblPlayerGold.setText("" + MainContainer.game.getPlayer().getGold());
        updateContent();
        selectFirstAvailableEntity();
        updatePreview();
    }
}