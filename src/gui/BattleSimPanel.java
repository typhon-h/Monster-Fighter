package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import main.Team;
import monsters.Monster;

import static gui.MainContainer.game;

public class BattleSimPanel extends EntityViewer implements Updatable {

    /**
     * default serial version ID
     */
    private static final long serialVersionUID = 1L;

    FlowLayout leftLayout;
    FlowLayout rightLayout;
    JPanel     leftTeam;
    JPanel     rightTeam;
    JTextArea  battleLogDisplay;
    JButton    btnContinue;
    JButton    btnSkip;
    JCheckBox  chkAutoPlay;

    private int monsterDisplayWidth = 100;
    private int monsterIconHeight   = 100;
    private int monsterLabelHeight  = 20;
    private int teamDisplayHeight   = monsterIconHeight + monsterLabelHeight * 2;
    private int teamDisplayWidth    = 450;
    
    /**
     * Create the panel.
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

        JScrollPane scroll = new JScrollPane(battleLogDisplay);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds((MainContainer.SCREENWIDTH / 2 - 850 / 2), 280, 850, 200);
        this.add(scroll);
        
        // TODO: Create JCheckBox - Auto play
        // TODO: Create JButton   - Next event
        // TODO: Create JButton   - Skip to end
        
        JPanel pnlButtonsContainer = new JPanel();
        pnlButtonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlButtonsContainer.setBounds((MainContainer.SCREENWIDTH / 2 - 850 / 2),
                                      490, 850, 40);
        setOpaque(false);
        this.add(pnlButtonsContainer);
        
        
        btnContinue = new JButton();
        btnContinue.setBounds(0, 0, 850 / 2, 40);
        btnContinue.setPreferredSize(new Dimension(850 / 2, 40));
        btnContinue.setText("Continue");
        // TODO: add event handler
        pnlButtonsContainer.add(btnContinue);
        
        btnSkip = new JButton();
        btnSkip.setPreferredSize(new Dimension(850 / 2 - 140, 40));
        btnSkip.setText("Skip");
        // TODO: add event handler
        pnlButtonsContainer.add(btnSkip);
        
        chkAutoPlay = new JCheckBox();
        chkAutoPlay.setPreferredSize(new Dimension(100, 40));
        chkAutoPlay.setVerticalAlignment(SwingConstants.CENTER);
        chkAutoPlay.setText("Auto Play");
        chkAutoPlay.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        chkAutoPlay.setOpaque(false);
        // TODO: add event handler
        pnlButtonsContainer.add(chkAutoPlay);  
    }

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

    protected void showTeams(Team left, Team right) {
        // Clear both containers
        leftTeam.removeAll();
        rightTeam.removeAll();
        
        populateTeamPanel(leftTeam, left, true);

        if (right != null) {
            populateTeamPanel(rightTeam, right, false);
        }
    }

    @Override
    public void update() {
        showTeams(game.getBattleState().getPlayer().getTeam(),
                  game.getBattleState().getCurrOpponent().getTeam());
    }

}
