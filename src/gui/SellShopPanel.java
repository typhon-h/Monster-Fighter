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
import monsters.Monster;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SellShopPanel extends EntityViewer implements Updatable {

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
    private JButton btnSell;
    private JTextPane textPaneEntity7;
    private JTextPane textPaneEntity8;
    private JTextPane textPaneEntity9;
    private JTextPane textPaneEntity10;
    private JTextPane textPaneEntity11;
    private JTextPane textPaneEntity12;
    private JRadioButton rdBtnEntity8;
    private JRadioButton rdBtnEntity7;
    private JRadioButton rdBtnEntity9;
    private JRadioButton rdBtnEntity10;
    private JRadioButton rdBtnEntity11;
    private JRadioButton rdBtnEntity12;
    private JScrollPane scrollPane;

    /**
     * Create the panel.
     */
    public SellShopPanel() {
        super(true, true, true);
        setName("SellShop");

        JLabel lblSellShopTitle = new JLabel("Sell Shop");
        lblSellShopTitle.setBounds(430, 6, 150, 37);
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
            super.updatePreview(content, shopContent.toArray());
        });
        content.add(rdBtnEntity1);
        rdBtnEntity1.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity1.setBounds(6, 6, 120, 120);
        entities.add(rdBtnEntity1);

        rdBtnEntity2 = new JRadioButton("");
        rdBtnEntity2.addActionListener(selected -> {
            super.updatePreview(content, shopContent.toArray());
        });
        content.add(rdBtnEntity2);
        rdBtnEntity2.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity2.setBounds(284, 6, 120, 120);
        entities.add(rdBtnEntity2);

        rdBtnEntity3 = new JRadioButton("");
        rdBtnEntity3.addActionListener(selected -> {
            super.updatePreview(content, shopContent.toArray());
        });
        content.add(rdBtnEntity3);
        rdBtnEntity3.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity3.setBounds(6, 165, 120, 120);
        entities.add(rdBtnEntity3);

        rdBtnEntity4 = new JRadioButton("");
        rdBtnEntity4.addActionListener(selected -> {
            super.updatePreview(content, shopContent.toArray());
        });
        content.add(rdBtnEntity4);
        rdBtnEntity4.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity4.setBounds(284, 165, 120, 120);
        entities.add(rdBtnEntity4);

        rdBtnEntity5 = new JRadioButton("");
        rdBtnEntity5.addActionListener(selected -> {
            super.updatePreview(content, shopContent.toArray());
        });
        content.add(rdBtnEntity5);
        rdBtnEntity5.setHorizontalAlignment(SwingConstants.CENTER);
        rdBtnEntity5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rdBtnEntity5.setBounds(6, 360, 120, 120);
        entities.add(rdBtnEntity5);

        rdBtnEntity6 = new JRadioButton("");
        content.add(rdBtnEntity6);
        rdBtnEntity6.addActionListener(selected -> {
            super.updatePreview(content, shopContent.toArray());
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

        btnSell = new JButton("Sell");
        btnSell.addActionListener(sell -> {
            sellEntity();
        });
        btnSell.setBounds(690, 487, 295, 47);
        add(btnSell);

        update();
    }

    private void updateContent() {
        shopContent = MainContainer.game.getSellShop().getContent();
        for (int i = contentButtons.size() - 1; i >= 0; i--) {
            updateEntity(contentButtons.get(i), contentDescriptions.get(i), i);
        }
        textPaneEntity1.setCaretPosition(0); // Set scroll to position 0
    }

    private void sellEntity() {
        if (content.getSelection() != null && content.getSelection().getActionCommand() != "-1") {
            int index = Integer.parseInt(content.getSelection().getActionCommand());
            Entity entityToSell = shopContent.get(index);
            if (entityToSell instanceof Item) {
                new PopUp("Info", MainContainer.game.getSellShop().sell((Item) entityToSell),
                        this.getLocationOnScreen());
            } else {
                new PopUp("Info", MainContainer.game.getSellShop().sell((Monster) entityToSell),
                        this.getLocationOnScreen());
            }

            update();

        } else {
            new PopUp("Error", "Select an Item/Monster", this.getLocationOnScreen());
        }

    }

    private void updateEntity(JRadioButton image, JTextPane desc, int index) {
        try {
            Entity entity = shopContent.get(index);
            image.setEnabled(true);
            image.setIcon(MainContainer.imageResize(entity.getImage(), image.getWidth(),
                    image.getHeight()));
            desc.setText("\n" + entity.getSellPrice() + "G \n"
                    + entity.getRarity().name() + "\n"
                    + entity.getName());
            image.setActionCommand("" + index);

        } catch (IndexOutOfBoundsException e) {
            desc.setText("");
            image.setEnabled(false);
            image.setActionCommand("-1");
            image.setText("");
        }

    }

    public void update() {
        updateContent();
        super.updatePlayerInfo();
        super.selectFirstAvailableButton(content);
        super.updatePreview(content, shopContent.toArray());
    }
}
