package gui;

import static gui.MainContainer.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.Player;

public class BattleSelectionPanel extends EntityViewer implements Updatable {

    private static final long serialVersionUID = 1L;

	private JButton btnBattle;

	private ButtonGroup battleButtons;

    private final int radioButtonHeight = 120;
    private final int pnlBattlesHeight = 475;
    private final int pnlBattlesWidth = 535;
    private int buttonGap;

    private JPanel pnlBattles;

    /**
     * Create the panel.
     */
    public BattleSelectionPanel() {
        super(true, true, true);
        setName("BattleSelection");

        JLabel lblBattleSelectionTitle = new JLabel("Battle Selection");
        lblBattleSelectionTitle.setBounds(385, 6, 246, 37);
        lblBattleSelectionTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblBattleSelectionTitle);

        btnBattle = new JButton("Battle");
        btnBattle.addActionListener(battle -> {
            goToBattle();
        });
        btnBattle.setBounds(690, 487, 295, 47);
        add(btnBattle);

        // Init battles panel
        pnlBattles = new JPanel();
        pnlBattles.setBounds(16, 54, pnlBattlesWidth, pnlBattlesHeight);
        pnlBattles.setBackground(this.getBackground());
        add(pnlBattles);

        FlowLayout pnlBattlesLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        pnlBattles.setLayout(pnlBattlesLayout);

    }

    @Override
    public void update() {
        btnBattle.setEnabled(false);
        btnBattle.setText("Battle");

        // TODO: Re-factor code so instead creating new button group, just remove button from button
        // group, remove button from pnlBattles and resize FlowLayout setVgap. - Need to reset when Opponents get generated
        // Reset battles panel and re-populate it with the new data battles.
        // Remove old battles
        pnlBattles.removeAll();

        // Clear battleButtons group
        battleButtons = new ButtonGroup();
        ArrayList<Player> opponents = game.getBattleState().getOpponents();

        buttonGap = (pnlBattlesHeight - (opponents.size() * radioButtonHeight)) / (opponents.size() + 1);
        ((FlowLayout) pnlBattles.getLayout()).setVgap(buttonGap);

        for (Player opponent : opponents) {
            JRadioButton oppButton = new JRadioButton(getOpponentDescription(opponent));
            oppButton.setActionCommand(String.valueOf(opponents.indexOf(opponent)));
            oppButton.addActionListener(selected -> {
                super.updatePreview(battleButtons, opponents.toArray());
            });

            oppButton.setPreferredSize(new Dimension(pnlBattlesWidth, radioButtonHeight));
            oppButton.setBackground(Color.WHITE);
            oppButton.setFont(oppButton.getFont().deriveFont(25.0f));
            pnlBattles.add(oppButton);

            battleButtons.add(oppButton);
            btnBattle.setEnabled(true);
        }
        
        if (game.getPlayer().getTeam().getAliveMonsters().isEmpty()) {
            btnBattle.setEnabled(false);
            btnBattle.setText("All Monsters Fainted");
        }

        super.updatePlayerInfo();
        super.selectFirstAvailableButton(battleButtons);
        super.updatePreview(battleButtons, opponents.toArray());
    }

    private String getOpponentDescription(Player opponent) {
        String outputString = opponent.getName() + ":  ";
        outputString += opponent.getTeam().toString();

        return outputString;
    }

    private void goToBattle() {
        int battleIndex = Integer.parseInt(battleButtons.getSelection().getActionCommand());
        Player opponent = game.getBattleState().getOpponents().get(battleIndex);
        game.getBattleState().setOpponent(opponent);
        MainContainer.showScreen("BattleSimulation");
    }
}
