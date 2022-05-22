package ui.gui;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

import monsters.Monster;

/**
 * JPanel for displaying the contents of the player that can be sold
 * 
 * @author Jackie Jone
 * @author Harrison Tyson
 * @version 1.1 May, 2022
 */
public class TeamPanel extends EntityViewer implements Updatable {
    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Player Team
     */
    private ArrayList<Monster> playerTeam;

    /**
     * Buttons associated with PlayerTeam
     */
    private ButtonGroup contentButtons = new ButtonGroup();

    /**
     * Button to perform move down action
     */
    private JButton btnMoveDown;

    /**
     * Button to perform move up action
     */
    private JButton btnMoveUp;

    /**
     * Create the panel with all the buttons and boxes without content
     */
    public TeamPanel() {
        super("Team", true, true, true);
        setName("Team");

        super.createContentPanel(600, 350, 30, 100, 2);

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
        playerTeam = MainContainer.game.getPlayer().getTeam().getMonsters();

        ArrayList<Object[]> panelsContent = new ArrayList<Object[]>();
        // Add in order of panel display
        panelsContent.add(playerTeam.toArray());

        super.updateContentPanels(panelsContent);
        super.updatePlayerInfo();
        contentButtons = super.contentPanels.get(0).getButtons();
        super.selectFirstAvailableButton(contentButtons);
        super.updatePreview(contentButtons, playerTeam.toArray());
    }
}
