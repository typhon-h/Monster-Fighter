package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static gui.MainContainer.DEFAULTDIMENSION;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import main.Player;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Dimension;

import static gui.MainContainer.game;

public class BattleSelectionPanel extends JPanel implements Updatable {

    private static final long serialVersionUID = 1L;

    private JLabel lblPreviewEntityImg;
    private JTextPane textPanePreviewEntityDesc;
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
        super();
        setName("BattleSelection");
        setMinimumSize(DEFAULTDIMENSION);
        setSize(DEFAULTDIMENSION);
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);

        JLabel lblBattleSelectionTitle = new JLabel("Battle Selection");
        lblBattleSelectionTitle.setBounds(378, 6, 246, 37);
        lblBattleSelectionTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblBattleSelectionTitle);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(back -> {
            MainContainer.showScreen("MainMenu");
        });
        btnBack.setBounds(6, 6, 82, 40);
        add(btnBack);

        JPanel preview = new JPanel();
        preview.setLayout(null);
        preview.setBorder(new LineBorder(new Color(0, 0, 0)));
        preview.setBackground(Color.GRAY);
        preview.setBounds(561, 54, 385, 475);
        add(preview);

        lblPreviewEntityImg = new JLabel("Selected Entity Image");
        lblPreviewEntityImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPreviewEntityImg.setBounds(98, 6, 200, 200);
        preview.add(lblPreviewEntityImg);

        textPanePreviewEntityDesc = new JTextPane();
        textPanePreviewEntityDesc.setFont(new Font("Dialog", Font.PLAIN, 20));
        textPanePreviewEntityDesc.setEditable(false);
        textPanePreviewEntityDesc.setBackground(Color.GRAY);
        textPanePreviewEntityDesc.setBounds(6, 249, 373, 176);
        preview.add(textPanePreviewEntityDesc);

        btnBattle = new JButton("Battle");
        btnBattle.addActionListener(battle -> {
            goToBattle();
        });
        btnBattle.setBounds(4, 424, 379, 47);
        preview.add(btnBattle);

        // Init battles panel
        pnlBattles = new JPanel();
        pnlBattles.setMinimumSize(new Dimension(pnlBattlesWidth, pnlBattlesHeight));
        pnlBattles.setSize(new Dimension(pnlBattlesWidth, pnlBattlesHeight));
        pnlBattles.setBounds(16, 54, pnlBattlesWidth, pnlBattlesHeight);
        pnlBattles.setBackground(Color.GRAY);
        add(pnlBattles);

        FlowLayout pnlBattlesLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        pnlBattles.setLayout(pnlBattlesLayout);

    }

    public void update() {
        btnBattle.setEnabled(false);

        // Clear preview
        lblPreviewEntityImg.setText("Selected Entity Image");
        lblPreviewEntityImg.setIcon(null);
        textPanePreviewEntityDesc.setText("");

        // Reset battles panel and re-populate it with the new data battles.
        // Remove old battles
        pnlBattles.removeAll();

        // Clear battleButtons group
        battleButtons = new ButtonGroup();
        ArrayList<Player> opponents = game.getBattleState().getOpponents();

        buttonGap = (pnlBattlesHeight - (opponents.size() * radioButtonHeight)) / (opponents.size() + 1);
        ((FlowLayout) pnlBattles.getLayout()).setVgap(buttonGap);
        System.out.println(buttonGap);

        for (Player opponent : opponents) {
            JRadioButton oppButton = new JRadioButton(getOpponentDescription(opponent));
            oppButton.setActionCommand(String.valueOf(opponents.indexOf(opponent)));
            oppButton.addActionListener(selected -> {
                updatePreview();
            });
            oppButton.setMaximumSize(new Dimension(pnlBattlesWidth, radioButtonHeight));
            oppButton.setMinimumSize(new Dimension(pnlBattlesWidth, radioButtonHeight));
            oppButton.setPreferredSize(new Dimension(pnlBattlesWidth, radioButtonHeight));
            oppButton.setSize(new Dimension(pnlBattlesWidth, radioButtonHeight));
            oppButton.setBackground(Color.WHITE);
            oppButton.setFont(oppButton.getFont().deriveFont(25.0f));
            pnlBattles.add(oppButton);

            battleButtons.add(oppButton);
        }
    }

    private String getOpponentDescription(Player opponent) {
        String outputString = opponent.getName() + ":  ";
        outputString += opponent.getTeam().toString();

        return outputString;
    }

    private void updatePreview() {
        int battleIndex = Integer.parseInt(battleButtons.getSelection().getActionCommand());
        Player battle = game.getBattleState().getOpponents().get(battleIndex);
        lblPreviewEntityImg.setText(battle.getName() + " Image");
        // lblPreviewEntityImg.setIcon(battle.getImage());
        textPanePreviewEntityDesc.setText(battle.toString());
        btnBattle.setEnabled(true);
    }

    private void goToBattle() {
        int battleIndex = Integer.parseInt(battleButtons.getSelection().getActionCommand());
        Player opponent = game.getBattleState().getOpponents().get(battleIndex);
        game.getBattleState().setOpponent(opponent);
        MainContainer.showScreen("MainMenu");
        // MainContainer.showScreen("FightBattle");
    }
}
