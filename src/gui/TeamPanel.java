package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

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
public class TeamPanel extends EntityViewer implements Updatable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Monster> playerTeam;

    private ButtonGroup contentButtons = new ButtonGroup();
    private JScrollPane scrollPane;
    private JButton btnMoveDown;
    private JButton btnMoveUp;

    private JPanel entityDisplay;

    private static final int entityDisplayWidth = 600;
    private static final int entityDisplayHeight = 350;
    private static final int entityWidth = 120;
    private static final int entityHeight = 120;
    private static final int entityContainerWidth = entityWidth * 2;
    private static final int entityContainerGap = (entityDisplayWidth -
            (2 * entityContainerWidth)) / 4;
    private Dimension entityDisplayDimension;

    /**
     * Create the panel with all the buttons and boxes without content
     */
    public TeamPanel() {
        super("team", true, true, true);
        setName("Team");

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
        scrollPane.setBounds(30, 100, entityDisplayWidth + 35, entityDisplayHeight);
        scrollPane.setMaximumSize(scrollPane.getSize());
        scrollPane.setViewportView(entityDisplay);
        this.add(scrollPane);

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
    }

    /**
     * Populate the content on the panel with entitys that can be sold
     */
    private void updateContent() {
        btnMoveUp.setEnabled(false);
        btnMoveDown.setEnabled(false);
        entityDisplay.removeAll();
        contentButtons = new ButtonGroup();
        playerTeam = MainContainer.game.getPlayer().getTeam().getMonsters();

        int height = (int) ((Math.ceil((float) playerTeam.size() / 2.0f)) *
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
        for (Entity entity : playerTeam) {
            btnMoveUp.setEnabled(true);
            btnMoveDown.setEnabled(true);
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
            entityButton.setActionCommand(String.valueOf(playerTeam.indexOf(entity)));
            entityButton.addActionListener(selected -> {
                // TODO: Set border around selected object or a background or something
                super.updatePreview(contentButtons, playerTeam.toArray());
            });
            contentButtons.add(entityButton);
            entityContainer.add(entityButton);

            entityTextPane = new JTextPane();
            entityTextPane
                    .setText("\n" + "Position: " + (playerTeam.indexOf(entity) + 1) + "\n" + entity.getRarity().name()
                            + "\n"
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
     * Move a monster down in the team
     */
    private void moveDown() {
        int index = Integer.parseInt(contentButtons.getSelection().getActionCommand());
        Monster monsterToMove = playerTeam.get(index);
        if (index == playerTeam.size() - 1) {
            new PopUp("Info", monsterToMove.getName() + " is already last",
                    this.getLocationOnScreen());
        } else {
            MainContainer.game.getPlayer().getTeam().moveMonsterDown(monsterToMove);
            new PopUp("Info", monsterToMove.getName() + " has been moved down",
                    this.getLocationOnScreen());
        }

        update();

    }

    /**
     * Move a monster up in the team
     */
    private void moveUp() {
        int index = Integer.parseInt(contentButtons.getSelection().getActionCommand());
        Monster monsterToMove = playerTeam.get(index);
        if (index == 0) {
            new PopUp("Info", monsterToMove.getName() + " is already first",
                    this.getLocationOnScreen());
        } else {
            MainContainer.game.getPlayer().getTeam().moveMonsterUp(monsterToMove);
            new PopUp("Info", monsterToMove.getName() + " has been moved up",
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
        super.updatePreview(contentButtons, playerTeam.toArray());
    }
}
