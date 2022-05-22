package ui.gui;

import java.awt.Font;

import static ui.gui.BattleSimPanel.populateTeamPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Player;

/**
 * JPanel for displaying the game over screen
 * 
 * @author Jackie Jone
 * @version 1.0 May, 2022
 */
public class GameOverPanel extends EntityViewer implements Updatable {
        /**
         * Default serial version id
         */
        private static final long serialVersionUID = 1L;

        /**
         * Width of a monster icon
         */
        private final int MONSTERDISPLAYWIDTH = 100;

        /**
         * Height of a monster icon
         */
        private final int MONSTERICONHEIGHT = 100;

        /**
         * Height of monster name
         */
        private final int MONSTERLABELHEIGHT = 20;

        /**
         * Height of team panel
         */
        private int teamDisplayHeight = MONSTERICONHEIGHT + MONSTERLABELHEIGHT * 2;

        /**
         * Width of team panel
         */
        private int teamDisplayWidth = (MainContainer.SCREENWIDTH * 3) / 4;

        /**
         * Padding for team panel
         */
        private int teamDisplayPadding = (teamDisplayWidth - MONSTERDISPLAYWIDTH * 4) / 5;

        /**
         * Default label height
         */
        private final int COMMONLABELHEIGHT = 75;

        /**
         * Default label width
         */
        private final int COMMONLABELWIDTH = 200;

        /**
         * Width of results panel
         */
        private int commonLabelContainerWidth = (MainContainer.SCREENWIDTH * 6) / 7;

        /**
         * Panel for team
         */
        private JPanel monsterContainerPanel;

        /**
         * Panel for results
         */
        private JPanel labelContainerPanel;

        /**
         * Label for player name
         */
        private JLabel lblPlayerName;

        /**
         * Label for num days
         */
        private JLabel lblNumDays;

        /**
         * Label for player gold
         */
        private JLabel lblGold;

        /**
         * Label for player score
         */
        private JLabel lblScore;

        /**
         * Initialize the game over panel with all the labels, panels, and buttons
         */
        public GameOverPanel() {
                super("Game Over", false, false, false);
                setName("GameOver");

                FlowLayout monsterContainerLayout = new FlowLayout(FlowLayout.CENTER,
                                teamDisplayPadding, 0);
                monsterContainerPanel = new JPanel();
                monsterContainerPanel.setBounds((MainContainer.SCREENWIDTH / 2 - teamDisplayWidth / 2),
                                200, teamDisplayWidth, teamDisplayHeight);
                monsterContainerPanel.setOpaque(false);
                monsterContainerPanel.setLayout(monsterContainerLayout);
                add(monsterContainerPanel);

                FlowLayout LabelContainerLayout = new FlowLayout(FlowLayout.CENTER,
                                10, 0);

                labelContainerPanel = new JPanel();
                labelContainerPanel.setBounds((MainContainer.SCREENWIDTH / 2 - commonLabelContainerWidth / 2),
                                120, commonLabelContainerWidth, COMMONLABELHEIGHT);
                labelContainerPanel.setLayout(LabelContainerLayout);
                labelContainerPanel.setOpaque(false);
                add(labelContainerPanel);

                lblPlayerName = new JLabel();
                lblPlayerName.setPreferredSize(new Dimension(COMMONLABELWIDTH, COMMONLABELHEIGHT));
                lblPlayerName.setFont(new Font("Lucida Grande", Font.BOLD, 18));
                lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
                lblPlayerName.setVerticalAlignment(SwingConstants.CENTER);
                ;
                labelContainerPanel.add(lblPlayerName);

                lblNumDays = new JLabel();
                lblNumDays.setPreferredSize(new Dimension(COMMONLABELWIDTH, COMMONLABELHEIGHT));
                lblNumDays.setFont(new Font("Lucida Grande", Font.BOLD, 18));
                lblNumDays.setHorizontalAlignment(SwingConstants.CENTER);
                lblNumDays.setVerticalAlignment(SwingConstants.CENTER);
                labelContainerPanel.add(lblNumDays);

                lblGold = new JLabel();
                lblGold.setPreferredSize(new Dimension(COMMONLABELWIDTH, COMMONLABELHEIGHT));
                lblGold.setFont(new Font("Lucida Grande", Font.BOLD, 18));
                lblGold.setHorizontalAlignment(SwingConstants.CENTER);
                lblGold.setVerticalAlignment(SwingConstants.CENTER);
                labelContainerPanel.add(lblGold);

                lblScore = new JLabel();
                lblScore.setPreferredSize(new Dimension(COMMONLABELWIDTH, COMMONLABELHEIGHT));
                lblScore.setFont(new Font("Lucida Grande", Font.BOLD, 18));
                lblScore.setHorizontalAlignment(SwingConstants.CENTER);
                lblScore.setVerticalAlignment(SwingConstants.CENTER);
                labelContainerPanel.add(lblScore);

                JButton btnPlayAgain = new JButton();
                btnPlayAgain.setText("Play Again");
                btnPlayAgain.setBounds((MainContainer.SCREENWIDTH - 2 * 350) / 3, 425, 350, 75);
                btnPlayAgain.addActionListener(again -> {
                        MainContainer.resetGame();
                });
                this.add(btnPlayAgain);

                JButton btnExit = new JButton();
                btnExit.setText("Exit");
                btnExit.setBounds(((MainContainer.SCREENWIDTH - 2 * 350) / 3) * 2 + 350,
                                425, 350, 75);
                btnExit.addActionListener(exit -> {
                        System.exit(0);
                });
                this.add(btnExit);
        }

        /**
         * Update the game over screen with the player's information
         */
        public void update() {
                Player player = MainContainer.game.getPlayer();

                lblPlayerName.setText(player.getName());
                lblNumDays.setText("Days: " + String.valueOf(MainContainer.game.getTotalDays()));
                lblGold.setText("Gold: " + player.getGold() + "G");
                lblScore.setText("Score: " + String.valueOf(player.getScore()));

                // Populate team display container
                monsterContainerPanel.removeAll();
                populateTeamPanel(monsterContainerPanel,
                                player.getTeam().getMonsters(),
                                false);

        }
}
