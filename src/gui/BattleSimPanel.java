package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import battle.BattleEvent;
import battle.BattleResult;
import main.Player;
import main.Team;
import monsters.Monster;

/**
 * A swing panel made for displaying the simulation of the battle to the player
 * 
 * @author Jackie Jone
 * @version 1.0 May, 2022
 */
public class BattleSimPanel extends EntityViewer implements Updatable {

    /**
     * default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Ally team layout
     */
    private FlowLayout leftLayout;

    /**
     * Enemy team layout
     */
    private FlowLayout rightLayout;

    /**
     * Ally team panel
     */
    private JPanel leftTeam;

    /**
     * Enemy team panel
     */
    private JPanel rightTeam;

    /**
     * Text area for battle events
     */
    private JTextArea battleLogDisplay;

    /**
     * Button to procede to next battle event
     */
    private JButton btnContinue;

    /**
     * Button to skip to end of battle
     */
    private JButton btnSkip;

    /**
     * Toggleable to enable/disable auto battle
     */
    private JCheckBox chkAutoPlay;

    /**
     * Most recent battle event
     */
    private BattleEvent currEvent;

    /**
     * Timer for auto battle
     */
    private Timer timer;

    /**
     * Default monster width
     */
    private static final int MONSTERDISPLAYWIDTH = 100;

    /**
     * Default monster height
     */
    private static final int MONSTERICONHEIGHT = 100;

    /**
     * Default monster text height
     */
    private static final int MONSTERLABELHEIGHT = 20;

    /**
     * Team panel height
     */
    private static int teamDisplayHeight = MONSTERICONHEIGHT + MONSTERLABELHEIGHT * 2;

    /**
     * Team panel width
     */
    private static final int TEAMDISPLAYWIDTH = 450;

    /**
     * ActionListener that displays the next event in the
     * simulation.
     */
    private ActionListener next = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayNextEvent();
        }
    };

    /**
     * ActionListener that displays the main menu screen
     */
    private ActionListener mainMenu = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainContainer.showScreen("MainMenu");
        }
    };

    /**
     * Create the simulation panel with the default buttons and set up the
     * area which the monsters are displayed.
     */
    public BattleSimPanel() {
        super("Battle", true, false, false);
        setName("BattleSimulation");

        leftLayout = new FlowLayout(FlowLayout.RIGHT, 10, 0);
        rightLayout = new FlowLayout(FlowLayout.LEFT, 10, 0);

        leftTeam = new JPanel();
        leftTeam.setBounds((MainContainer.SCREENWIDTH / 4 - TEAMDISPLAYWIDTH / 2),
                130, TEAMDISPLAYWIDTH, teamDisplayHeight);
        leftTeam.setOpaque(false);
        leftTeam.setLayout(leftLayout);
        this.add(leftTeam);

        rightTeam = new JPanel();
        rightTeam.setBounds((MainContainer.SCREENWIDTH / 2 +
                (MainContainer.SCREENWIDTH / 4 - TEAMDISPLAYWIDTH / 2)),
                130, TEAMDISPLAYWIDTH, teamDisplayHeight);
        rightTeam.setOpaque(false);
        rightTeam.setLayout(rightLayout);
        this.add(rightTeam);

        battleLogDisplay = new JTextArea();
        battleLogDisplay.setBounds(0, 0, 750, 200);
        battleLogDisplay.setEditable(false);
        battleLogDisplay.setMargin(new Insets(0, 5, 0, 5));
        battleLogDisplay.setLineWrap(true);
        battleLogDisplay.setWrapStyleWord(true);
        battleLogDisplay.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

        JScrollPane scroll = new JScrollPane(battleLogDisplay);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds((MainContainer.SCREENWIDTH / 2 - 850 / 2), 280, 850, 200);
        this.add(scroll);

        JPanel pnlButtonsContainer = new JPanel();
        pnlButtonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlButtonsContainer.setBounds((MainContainer.SCREENWIDTH / 2 - 850 / 2),
                490, 850, 40);
        pnlButtonsContainer.setOpaque(false);
        this.add(pnlButtonsContainer);

        btnContinue = new JButton();
        btnContinue.setBounds(0, 0, 850 / 2, 40);
        btnContinue.setPreferredSize(new Dimension(850 / 2, 40));
        btnContinue.setText("Continue");
        btnContinue.setFocusable(false);
        btnContinue.addActionListener(next);
        pnlButtonsContainer.add(btnContinue);

        btnSkip = new JButton();
        btnSkip.setPreferredSize(new Dimension(850 / 2 - 190, 40));
        btnSkip.setText("Skip");
        btnSkip.setFocusable(false);
        btnSkip.addActionListener(skip -> {
            skipAllEvents();
        });
        pnlButtonsContainer.add(btnSkip);

        chkAutoPlay = new JCheckBox();
        chkAutoPlay.setPreferredSize(new Dimension(150, 40));
        chkAutoPlay.setVerticalAlignment(SwingConstants.CENTER);
        chkAutoPlay.setText("Auto Play");
        chkAutoPlay.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        chkAutoPlay.setOpaque(false);
        chkAutoPlay.setFocusable(false);
        chkAutoPlay.addActionListener(auto -> {
            autoPlay();
        });
        pnlButtonsContainer.add(chkAutoPlay);
    }

    /**
     * Populates a given panel with monsters
     *
     * @param panel   The panel to populate with monsters
     * @param team    The team to populate the panel with
     * @param reverse Reverse the order which the team is displayed
     */
    protected static void populateTeamPanel(JPanel panel, ArrayList<Monster> team, boolean reverse) {
        for (int i = 0; i < team.size(); i++) {
            Monster monster = team.get(!reverse ? i : team.size() - 1 - i);
            JPanel monsterContainer = new JPanel();
            monsterContainer.setPreferredSize(new Dimension(MONSTERDISPLAYWIDTH,
                    teamDisplayHeight));
            monsterContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            monsterContainer.setOpaque(false);

            JLabel monsterStatsLabel = new JLabel();
            monsterStatsLabel.setPreferredSize(new Dimension(MONSTERDISPLAYWIDTH,
                    MONSTERLABELHEIGHT));
            monsterStatsLabel.setBounds(0, 0, MONSTERDISPLAYWIDTH, MONSTERLABELHEIGHT);
            monsterStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            monsterStatsLabel.setVerticalAlignment(SwingConstants.CENTER);
            monsterStatsLabel.setText(monster.getCurrentAttackDamage() + " | " +
                    monster.getCurrentHealth());

            monsterStatsLabel.setOpaque(false);
            monsterStatsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
            ;

            JLabel monsterIcon = new JLabel();
            monsterIcon.setPreferredSize(new Dimension(MONSTERDISPLAYWIDTH,
                    MONSTERICONHEIGHT));
            monsterIcon.setBounds(0, 0, MONSTERDISPLAYWIDTH, MONSTERICONHEIGHT);
            monsterIcon.setIcon(
                    MainContainer.imageResize(monster.getImage(), monsterIcon.getWidth(), monsterIcon.getHeight()));
            monsterIcon.setText(monster.getName());
            monsterIcon.setOpaque(false);

            JLabel monsterNameLabel = new JLabel();
            monsterNameLabel.setPreferredSize(new Dimension(MONSTERDISPLAYWIDTH,
                    MONSTERLABELHEIGHT));
            monsterNameLabel.setBounds(0, 0, MONSTERDISPLAYWIDTH, MONSTERLABELHEIGHT);
            monsterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            monsterNameLabel.setVerticalAlignment(SwingConstants.CENTER);
            monsterNameLabel.setText(monster.getName());
            monsterNameLabel.setOpaque(false);

            monsterContainer.add(monsterStatsLabel);
            monsterContainer.add(monsterIcon);
            monsterContainer.add(monsterNameLabel);
            panel.add(monsterContainer);
        }

    }

    /**
     * Displays the two teams on the screen
     *
     * @param left  The team to be displayed on the left
     * @param right The team to be displayed on the right
     */
    protected void showTeams(Team left, Team right) {
        // Clear both containers
        leftTeam.removeAll();
        rightTeam.removeAll();

        populateTeamPanel(leftTeam, left.getAliveMonsters(), true);

        if (right != null) {
            populateTeamPanel(rightTeam, right.getAliveMonsters(), false);
        }

        leftTeam.updateUI();
        rightTeam.updateUI();
    }

    /**
     * Check if all the events of the simulation has been
     * displayed and distribute rewards
     */
    private void checkSimulationOver() {
        if (currEvent == null) {
            btnSkip.setEnabled(false);
            chkAutoPlay.setEnabled(false);
            btnContinue.setEnabled(true);
            btnContinue.setText("Main Menu");
            for (ActionListener action : btnContinue.getActionListeners()) {
                btnContinue.removeActionListener(action);
            }
            btnContinue.addActionListener(mainMenu);

            Player battle = MainContainer.game.getBattleState().getCurrOpponent();
            String rewardsString = "Rewards: " + battle.getGold() + "G " + battle.getScore() + " Points";
            if (MainContainer.game.getBattleState().getResult() == BattleResult.WIN) {
                new PopUp("Winner", rewardsString, this.getLocationOnScreen());
                battleLogDisplay.setText(battleLogDisplay.getText() +
                        "\nAll the opponent monsters fainted\nYOU WON!\n" +
                        rewardsString);
            } else if (MainContainer.game.getBattleState().getResult() == BattleResult.LOSS) {
                battleLogDisplay.setText(battleLogDisplay.getText() +
                        "\nAll your monsters have fainted\nYOU LOST!");
            }
            // Distribute rewards
            MainContainer.game.getBattleState().giveRewards();
            updatePlayerInfo();
        }
    }

    /**
     * Manually display the next event
     */
    private void displayNextEvent() {
        if (currEvent != null) {
            showTeams(currEvent.getAllyTeam(), currEvent.getOpponentTeam());
            battleLogDisplay.setText(battleLogDisplay.getText() + "\n" +
                    currEvent.getDescription());
            MainContainer.game.getPlayer().setTeam(currEvent.getAllyTeam());
            currEvent = MainContainer.game.getBattleState().nextEvent();
        }
        checkSimulationOver();
    }

    /**
     * Skip to the end of the battle
     */
    private void skipAllEvents() {
        BattleEvent prevEvent = currEvent;
        while (currEvent != null) {
            battleLogDisplay.setText(battleLogDisplay.getText() + "\n" +
                    currEvent.getDescription());
            prevEvent = currEvent;
            currEvent = MainContainer.game.getBattleState().nextEvent();
        }
        MainContainer.game.getPlayer().setTeam(prevEvent.getAllyTeam());
        showTeams(prevEvent.getAllyTeam(), prevEvent.getOpponentTeam());

        checkSimulationOver();
    }

    /**
     * Automatically display the events with delay
     */
    private void autoPlay() {
        if (chkAutoPlay.isSelected()) {
            btnContinue.setEnabled(false);
            btnSkip.setEnabled(false);
            timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currEvent != null) {
                        battleLogDisplay.setText(battleLogDisplay.getText() + "\n" +
                                currEvent.getDescription());
                        showTeams(currEvent.getAllyTeam(), currEvent.getOpponentTeam());
                        MainContainer.game.getPlayer().setTeam(currEvent.getAllyTeam());
                        currEvent = MainContainer.game.getBattleState().nextEvent();
                    } else {
                        timer.stop();
                        chkAutoPlay.setSelected(false);
                        chkAutoPlay.setEnabled(false);
                        checkSimulationOver();
                    }
                }

            });
            timer.start();
        } else {
            timer.stop();
            btnContinue.setEnabled(true);
            btnSkip.setEnabled(true);
        }

    }

    /**
     * Update all the common components that have new
     * information to be updated.
     */
    @Override
    public void update() {
        // Update team display
        showTeams(MainContainer.game.getBattleState().getPlayer().getTeam(),
                MainContainer.game.getBattleState().getCurrOpponent().getTeam());

        // Reset buttons
        btnSkip.setEnabled(true);
        chkAutoPlay.setEnabled(true);

        for (ActionListener action : btnContinue.getActionListeners()) {
            btnContinue.removeActionListener(action);
        }
        btnContinue.addActionListener(next);
        btnContinue.setText("Continue");

        // Reset event log display
        battleLogDisplay.setText(null);

        // Simulate battle, ready for display
        MainContainer.game.getBattleState().simulateBattle();
        currEvent = MainContainer.game.getBattleState().nextEvent();
    }

}
