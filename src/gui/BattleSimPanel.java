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
import battle.BattleManager;
import battle.BattleResult;
import main.Player;
import main.Team;
import monsters.Monster;

import static gui.MainContainer.game;

public class BattleSimPanel extends EntityViewer implements Updatable {

    /**
     * default serial version ID
     */
    private static final long serialVersionUID = 1L;
    
    FlowLayout  leftLayout;
    FlowLayout  rightLayout;
    JPanel      leftTeam;
    JPanel      rightTeam;
    JTextArea   battleLogDisplay;
    JButton     btnContinue;
    JButton     btnSkip;
    JCheckBox   chkAutoPlay;
    
    BattleEvent currEvent;
    Timer       timer;
    
    BattleManager battleManager = game.getBattleState();
    
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

    private int monsterDisplayWidth = 100;
    private int monsterIconHeight   = 100;
    private int monsterLabelHeight  = 20;
    private int teamDisplayHeight   = monsterIconHeight + monsterLabelHeight * 2;
    private int teamDisplayWidth    = 450;
    
    /**
     * Create the simulation panel
     */
    public BattleSimPanel() {
        super(true, false, false);
        setName("BattleSimulation");

        JLabel lblBattleSimTitle = new JLabel("Battle");
        lblBattleSimTitle.setBounds((MainContainer.SCREENWIDTH / 2 - 45),
                                    6, 90, 37);
        lblBattleSimTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        lblBattleSimTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblBattleSimTitle.setVerticalAlignment(SwingConstants.CENTER);
        add(lblBattleSimTitle);

        leftLayout = new FlowLayout(FlowLayout.RIGHT, 10, 0);
        rightLayout = new FlowLayout(FlowLayout.LEFT, 10, 0);
        
        
        leftTeam = new JPanel();
        leftTeam.setBounds((MainContainer.SCREENWIDTH / 4 - teamDisplayWidth / 2),
                           130, teamDisplayWidth, teamDisplayHeight);
        leftTeam.setOpaque(false);
        leftTeam.setLayout(leftLayout);
        this.add(leftTeam);

        rightTeam = new JPanel();
        rightTeam.setBounds((MainContainer.SCREENWIDTH / 2 +
                            (MainContainer.SCREENWIDTH / 4 - teamDisplayWidth / 2)),
                            130, teamDisplayWidth, teamDisplayHeight);
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
        btnSkip.setPreferredSize(new Dimension(850 / 2 - 140, 40));
        btnSkip.setText("Skip");
        btnSkip.setFocusable(false);
        btnSkip.addActionListener(skip -> {
            skipAllEvents();
        });
        pnlButtonsContainer.add(btnSkip);
        
        chkAutoPlay = new JCheckBox();
        chkAutoPlay.setPreferredSize(new Dimension(100, 40));
        chkAutoPlay.setVerticalAlignment(SwingConstants.CENTER);
        chkAutoPlay.setText("Auto Play");
        chkAutoPlay.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        chkAutoPlay.setOpaque(false);
        chkAutoPlay.setFocusable(false);
        // TODO: add event handler
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
    private void populateTeamPanel(JPanel panel, Team team, boolean reverse) {
        ArrayList<Monster> aliveTeam = team.getAliveMonsters();
        
        for (int i =0; i < aliveTeam.size(); i++) {
            Monster monster = aliveTeam.get(!reverse ? i : aliveTeam.size()- 1 - i);
            JPanel monsterContainer = new JPanel();
            monsterContainer.setPreferredSize(new Dimension(monsterDisplayWidth,
                                                            teamDisplayHeight));
            monsterContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            monsterContainer.setOpaque(false);

            JLabel monsterStatsLabel = new JLabel();
            monsterStatsLabel.setPreferredSize(new Dimension(monsterDisplayWidth,
                                                             monsterLabelHeight));
            monsterStatsLabel.setBounds(0, 0, monsterDisplayWidth, monsterLabelHeight);
            monsterStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            monsterStatsLabel.setVerticalAlignment(SwingConstants.CENTER);
            monsterStatsLabel.setText(monster.getCurrentHealth() + " | " +
                                      monster.getCurrentAttackDamage());
            monsterStatsLabel.setOpaque(false);
            monsterStatsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));;


            JLabel monsterIcon = new JLabel();
            monsterIcon.setPreferredSize(new Dimension(monsterDisplayWidth,
                                                       monsterIconHeight));
            monsterIcon.setBounds(0, 0, monsterDisplayWidth, monsterIconHeight);            
            // monsterIcon.setIcon(new ImageIcon(**Url of icon**))
            monsterIcon.setText(monster.getName());
            monsterIcon.setOpaque(true);

            JLabel monsterNameLabel = new JLabel();
            monsterNameLabel.setPreferredSize(new Dimension(monsterDisplayWidth,
                                                             monsterLabelHeight));
            monsterNameLabel.setBounds(0, 0, monsterDisplayWidth, monsterLabelHeight);
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
        
        populateTeamPanel(leftTeam, left, true);

        if (right != null) {
            populateTeamPanel(rightTeam, right, false);
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
            
            Player battle = battleManager.getCurrOpponent();
            String rewardsString = "Rewards: " + battle.getGold() + "G " + battle.getScore() + " Points";
            if (battleManager.getResult() == BattleResult.WIN) {
                 new PopUp("Winner", rewardsString, this.getLocationOnScreen());
                 battleLogDisplay.setText(battleLogDisplay.getText() +
                                          "\nAll the opponent monsters fainted\nYOU WON!\n" +
                                          rewardsString);
            } else if (battleManager.getResult() == BattleResult.LOSS) {
                battleLogDisplay.setText(battleLogDisplay.getText() +
                        "\nAll your monsters have fainted\nYOU LOST!");
            }
            // Distribute rewards
            battleManager.giveRewards();
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
            currEvent = battleManager.nextEvent();
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
            currEvent = battleManager.nextEvent();
        }
        game.getPlayer().setTeam(prevEvent.getAllyTeam());
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
                    currEvent = battleManager.nextEvent();
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
        showTeams(battleManager.getPlayer().getTeam(),
                  battleManager.getCurrOpponent().getTeam());
        
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
        battleManager.simulateBattle();
        currEvent = battleManager.nextEvent();
    }

}
