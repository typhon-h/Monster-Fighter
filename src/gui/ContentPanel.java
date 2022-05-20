package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import main.Entity;

import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ContentPanel extends JPanel {

    private ArrayList<Entity> contentToDisplay;
    private ButtonGroup contentButtons = new ButtonGroup();
    private JScrollPane scrollPane;

    private JPanel entityDisplay;

    private static final int entityWidth = 120;
    private static final int entityHeight = 120;
    private int panelWidth;
    private int panelHeight;
    private int entityContainerWidth;
    private int entityContainerGap;
    private Dimension entityDisplayDimension;

    public ContentPanel(int width, int height, int posX, int posY,
            int numDisplayWide, Color backgroundColor) {
        this.panelHeight = height;
        this.panelWidth = width;
        this.setBackground(backgroundColor);
        entityContainerWidth = entityWidth * 2;
        entityContainerGap = (width -
                (numDisplayWide * entityContainerWidth)) / 4;

        FlowLayout entityContainerLayout = new FlowLayout(FlowLayout.LEFT,
                entityContainerGap,
                entityContainerGap);
        entityDisplayDimension = new Dimension(width,
                height);
        entityDisplay = new JPanel();
        entityDisplay.setLayout(entityContainerLayout);
        entityDisplay.setMaximumSize(new Dimension(width, 2000));
        entityDisplay.setPreferredSize(entityDisplayDimension);
        entityDisplay.setBackground(getBackground());

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        scrollPane.setBounds(posX, posY, width + 35, height);
        scrollPane.setMaximumSize(scrollPane.getSize());
        scrollPane.setViewportView(entityDisplay);
        this.add(scrollPane);
    }

    public void update(ActionListener buttonAction, ArrayList<String> descriptions) {
        while (descriptions.size() < contentToDisplay.size()) {
            descriptions.add("No description available");
        }
        while (descriptions.size() > contentToDisplay.size()) {
            descriptions.remove(descriptions.size() - 1);
        }

        entityDisplay.removeAll();
        contentButtons = new ButtonGroup();

        int height = (int) ((Math.ceil((float) contentToDisplay.size() / 2.0f)) *
                (entityHeight + entityContainerGap));
        height = height > this.panelHeight ? height : this.panelHeight;
        entityDisplayDimension = new Dimension(this.panelWidth,
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
        for (int i = 0; i < contentToDisplay.size(); i++) {
            Entity entity = contentToDisplay.get(i);
            String desc = descriptions.get(i);
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
            entityButton.setActionCommand(String.valueOf(contentToDisplay.indexOf(entity)));
            entityButton.addActionListener(buttonAction);
            contentButtons.add(entityButton);
            entityContainer.add(entityButton);

            entityTextPane = new JTextPane();
            entityTextPane.setText(desc);
            entityTextPane.setEditable(false);
            entityTextPane.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
            entityTextPane.setOpaque(false);
            entityTextPane.setBorder(entityContainerBorder);
            entityContainer.add(entityTextPane);

            entityDisplay.add(entityContainer);
        }

    }

    public ArrayList<Entity> getContent() {
        return contentToDisplay;
    }

    public void setContent(Object[] content) {
        contentToDisplay = new ArrayList<Entity>();
        for (Object e : content) {
            if (e instanceof Entity) {
                contentToDisplay.add((Entity) e);
            } else {
                throw new IllegalArgumentException("Arguments must be instance of Entity");
            }
        }
    }

    public ButtonGroup getButtons() {
        return contentButtons;
    }

    public JScrollPane getPanel() {
        return scrollPane;
    }

}
