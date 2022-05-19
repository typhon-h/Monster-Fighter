package gui;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import static gui.BattleSimPanel.populateTeamPanel;
import main.Player;

public class GameOverPanel extends EntityViewer implements Updatable{
    /**
     *  Default serial version id
     */
    private static final long serialVersionUID = 1L;

    private int monsterDisplayWidth = 100;
    private int monsterIconHeight   = 100;
    private int monsterLabelHeight  = 20;
    private int teamDisplayHeight   = monsterIconHeight + monsterLabelHeight * 2;
    private int teamDisplayWidth    = (MainContainer.SCREENWIDTH * 3) / 4;
    private int teamDisplayPadding  = (teamDisplayWidth - monsterDisplayWidth * 4) / 5;
    private int commonLabelHeight   = 75;
    private int commonLabelWidth    = 200;
    private int commonLabelContainerWidth = (MainContainer.SCREENWIDTH * 6) / 7;

    private JPanel monsterContainerPanel;
    private JPanel labelContainerPanel;
    private JLabel lblPlayerName;
    private JLabel lblNumDays;
    private JLabel lblGold;
    private JLabel lblScore;

    private static Player player;

    public GameOverPanel() {
        super(false, false, false);
        setName("GameOver");

        JLabel lblGameOverTitle = new JLabel("Game Over");
        lblGameOverTitle.setBounds((MainContainer.SCREENWIDTH / 2 - 100),
                                    6, 200, 37);
        lblGameOverTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        lblGameOverTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblGameOverTitle.setVerticalAlignment(SwingConstants.CENTER);
        add(lblGameOverTitle);


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
                120, commonLabelContainerWidth, commonLabelHeight);
        labelContainerPanel.setLayout(LabelContainerLayout);
        labelContainerPanel.setOpaque(false);
        add(labelContainerPanel);

        lblPlayerName = new JLabel();
        lblPlayerName.setPreferredSize(new Dimension(commonLabelWidth, commonLabelHeight));
        lblPlayerName.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayerName.setVerticalAlignment(SwingConstants.CENTER);;
        labelContainerPanel.add(lblPlayerName);


        lblNumDays = new JLabel();
        lblNumDays.setPreferredSize(new Dimension(commonLabelWidth, commonLabelHeight));
        lblNumDays.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblNumDays.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumDays.setVerticalAlignment(SwingConstants.CENTER);
        labelContainerPanel.add(lblNumDays);


        lblGold = new JLabel();
        lblGold.setPreferredSize(new Dimension(commonLabelWidth, commonLabelHeight));
        lblGold.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblGold.setHorizontalAlignment(SwingConstants.CENTER);
        lblGold.setVerticalAlignment(SwingConstants.CENTER);
        labelContainerPanel.add(lblGold);

        lblScore = new JLabel();
        lblScore.setPreferredSize(new Dimension(commonLabelWidth, commonLabelHeight));
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

    public void update() {
        player = MainContainer.game.getPlayer();

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
