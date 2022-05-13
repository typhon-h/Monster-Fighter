package gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import exceptions.DuplicateMonsterException;
import exceptions.TeamSizeException;
import main.Difficulty;
import main.GameEnvironment;
import main.Player;
import main.Team;
import monsters.Monster;

import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JTextPane;

public class SetupPanel extends JPanel {

    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldPlayerName;
    private final ButtonGroup difficultyOptions = new ButtonGroup();
    private final ButtonGroup starterMonsters = new ButtonGroup();
    private JTextField textFieldMonsterNickname;
    private JSlider sliderDays;
    private JLabel lblSelectedMonsterImg;
    private JTextPane textPaneMonsterInfo;
    private ArrayList<Monster> availableStarters;

    // TODO: Create panel, can make dummy using swing GUI thing and copy over code

    public SetupPanel() {
        super();
        setName("Setup");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);

        JLabel lblSetupTitle = new JLabel("Game Setup");
        lblSetupTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        lblSetupTitle.setBounds(378, 6, 193, 37);
        add(lblSetupTitle);

        JPanel jPanelGameSettings = new JPanel();
        jPanelGameSettings.setBorder(null);
        jPanelGameSettings.setBackground(this.getBackground());
        jPanelGameSettings.setBounds(30, 56, 277, 415);
        add(jPanelGameSettings);

        JLabel lblDifficulty = new JLabel("Difficulty: ");
        lblDifficulty.setBounds(0, 94, 106, 25);
        lblDifficulty.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

        textFieldPlayerName = new JTextField();
        textFieldPlayerName.setBorder(new LineBorder(new Color(0, 0, 0)));
        textFieldPlayerName.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        textFieldPlayerName.setBounds(6, 34, 265, 48);
        textFieldPlayerName.setColumns(10);

        JLabel lblPlayerName = new JLabel("Name:");
        lblPlayerName.setBounds(0, 0, 67, 16);
        lblPlayerName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        jPanelGameSettings.setLayout(null);
        jPanelGameSettings.add(lblDifficulty);
        jPanelGameSettings.add(textFieldPlayerName);
        jPanelGameSettings.add(lblPlayerName);

        JRadioButton rdbtnDifficultyEasy = new JRadioButton("Easy");
        rdbtnDifficultyEasy.setMnemonic(KeyEvent.VK_E);
        rdbtnDifficultyEasy.setBorder(UIManager.getBorder("RadioButton.border"));
        difficultyOptions.add(rdbtnDifficultyEasy);
        rdbtnDifficultyEasy.setBounds(10, 131, 141, 23);
        jPanelGameSettings.add(rdbtnDifficultyEasy);

        JRadioButton rdbtnDifficultyHard = new JRadioButton("Hard");
        rdbtnDifficultyHard.setMnemonic(KeyEvent.VK_H);
        difficultyOptions.add(rdbtnDifficultyHard);
        rdbtnDifficultyHard.setBounds(10, 236, 141, 23);
        jPanelGameSettings.add(rdbtnDifficultyHard);

        JRadioButton rdbtnDifficultyNormal = new JRadioButton("Normal");
        rdbtnDifficultyNormal.setMnemonic(KeyEvent.VK_N);
        rdbtnDifficultyNormal.setSelected(true);
        difficultyOptions.add(rdbtnDifficultyNormal);
        rdbtnDifficultyNormal.setBounds(10, 185, 141, 23);
        jPanelGameSettings.add(rdbtnDifficultyNormal);

        JLabel lblDays = new JLabel("Days: ");
        lblDays.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblDays.setBounds(0, 301, 67, 25);
        jPanelGameSettings.add(lblDays);

        sliderDays = new JSlider();
        sliderDays.setPaintLabels(true);
        sliderDays.setMinorTickSpacing(1);
        sliderDays.setMajorTickSpacing(5);
        sliderDays.setSnapToTicks(true);
        sliderDays.setValue(10);
        sliderDays.setMinimum(5);
        sliderDays.setMaximum(15);
        sliderDays.setBounds(6, 357, 265, 52);
        jPanelGameSettings.add(sliderDays);

        JLabel lblNumDays = new JLabel("" + sliderDays.getValue());
        lblNumDays.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblNumDays.setBounds(57, 306, 61, 16);
        jPanelGameSettings.add(lblNumDays);
        sliderDays.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                lblNumDays.setText("" + sliderDays.getValue());
            }
        });

        int numStarters = GameEnvironment.NUMSTARTERMONSTERS;
        ArrayList<Monster> possibleStarters = GameEnvironment.generateMonsters();
        availableStarters = new ArrayList<Monster>();
        for (int i = 0; i < numStarters; i++) {
            Monster m = possibleStarters.get(GameEnvironment.rng.nextInt(possibleStarters.size()));
            availableStarters.add(m);
            possibleStarters.remove(m);
        }

        JPanel jPanelStarterMonsters = new JPanel();
        jPanelStarterMonsters.setBorder(null);
        jPanelStarterMonsters.setBounds(394, 55, 177, 429);
        add(jPanelStarterMonsters);
        jPanelStarterMonsters.setLayout(null);
        jPanelStarterMonsters.setBackground(this.getBackground());

        JLabel lblStarters = new JLabel("Start Monster:");
        lblStarters.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblStarters.setBounds(6, 0, 165, 25);
        jPanelStarterMonsters.add(lblStarters);

        JRadioButton btnMonster1Img = new JRadioButton(availableStarters.get(0).getName() + " Image");
        btnMonster1Img.addActionListener(update -> {
            updateMonsterPreview(availableStarters.get(0));
        });
        btnMonster1Img.setActionCommand("1");
        starterMonsters.add(btnMonster1Img);
        btnMonster1Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster1Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster1Img.setBounds(39, 23, 100, 100);
        jPanelStarterMonsters.add(btnMonster1Img);
        btnMonster1Img.setIcon(null);

        JLabel lblMonster1Name = new JLabel(availableStarters.get(0).getName());
        lblMonster1Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster1Name.setBounds(6, 130, 165, 16);
        jPanelStarterMonsters.add(lblMonster1Name);

        JRadioButton btnMonster2Img = new JRadioButton(availableStarters.get(1).getName() + " Image");
        btnMonster2Img.addActionListener(update -> {
            updateMonsterPreview(availableStarters.get(1));
        });
        btnMonster2Img.setActionCommand("2");
        starterMonsters.add(btnMonster2Img);
        btnMonster2Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster2Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster2Img.setBounds(39, 158, 100, 100);
        jPanelStarterMonsters.add(btnMonster2Img);

        JLabel lblMonster2Name = new JLabel(availableStarters.get(1).getName());
        lblMonster2Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster2Name.setBounds(6, 265, 165, 16);
        jPanelStarterMonsters.add(lblMonster2Name);

        JRadioButton btnMonster3Img = new JRadioButton(availableStarters.get(2).getName() + " Image");
        btnMonster3Img.addActionListener(update -> {
            updateMonsterPreview(availableStarters.get(2));
        });
        btnMonster3Img.setActionCommand("3");
        starterMonsters.add(btnMonster3Img);
        btnMonster3Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster3Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster3Img.setBounds(39, 300, 100, 100);
        jPanelStarterMonsters.add(btnMonster3Img);

        JLabel lblMonster3Name = new JLabel(availableStarters.get(2).getName());
        lblMonster3Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster3Name.setBounds(6, 407, 165, 16);
        jPanelStarterMonsters.add(lblMonster3Name);

        JPanel jPanelMonsterInfo = new JPanel();
        jPanelMonsterInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
        jPanelMonsterInfo.setBounds(660, 56, 294, 415);
        add(jPanelMonsterInfo);
        jPanelMonsterInfo.setLayout(null);
        jPanelMonsterInfo.setBackground(this.getBackground());

        lblSelectedMonsterImg = new JLabel("Selected Monster Image");
        lblSelectedMonsterImg.setBounds(78, 1, 150, 150);
        jPanelMonsterInfo.add(lblSelectedMonsterImg);

        textPaneMonsterInfo = new JTextPane();
        textPaneMonsterInfo.setEditable(false);
        textPaneMonsterInfo.setBackground(this.getBackground());
        textPaneMonsterInfo.setBounds(3, 159, 285, 186);
        jPanelMonsterInfo.add(textPaneMonsterInfo);

        btnMonster1Img.setSelected(true); // DEFAULT SELECTED
        updateMonsterPreview(availableStarters.get(0));

        JLabel lblNickname = new JLabel("Give Nickname (optional):");
        lblNickname.setBounds(3, 357, 173, 16);
        jPanelMonsterInfo.add(lblNickname);

        textFieldMonsterNickname = new JTextField();
        textFieldMonsterNickname.setBorder(new LineBorder(new Color(0, 0, 0)));
        textFieldMonsterNickname.setBounds(6, 383, 282, 26);
        jPanelMonsterInfo.add(textFieldMonsterNickname);
        textFieldMonsterNickname.setColumns(10);

        JButton btnStartGame = new JButton("Play");
        btnStartGame.addActionListener(setup -> {
            setUpGame();
        });
        btnStartGame.setBounds(22, 489, 932, 45);
        add(btnStartGame);
    }

    private void updateMonsterPreview(Monster monsterToPreview) {
        lblSelectedMonsterImg.setText(monsterToPreview.getName() + " Image"); // TODO: change to image
        textPaneMonsterInfo.setText(monsterToPreview.toString());
    }

    private void setUpGame() {
        // Player Name
        if (textFieldPlayerName.getText().strip().length() < 1) {
            ErrorPopUp error = new ErrorPopUp("Name cannot be blank");
            error.setVisible(true);
            return;
        }
        String playerName = textFieldPlayerName.getText().strip();

        // Difficulty
        Difficulty gameDifficulty;
        String selectedDifficulty = difficultyOptions.getSelection().getActionCommand();

        if (selectedDifficulty == "Easy") {
            gameDifficulty = Difficulty.EASY;
        } else if (selectedDifficulty == "Hard") {
            gameDifficulty = Difficulty.HARD;
        } else { // DEFAULT
            gameDifficulty = Difficulty.NORMAL;
        }

        // Day
        int numDays = sliderDays.getValue();

        // Starter
        Monster starter;
        String selectedStarter = starterMonsters.getSelection().getActionCommand();

        if (selectedStarter == "2") {
            starter = availableStarters.get(1);
        } else if (selectedStarter == "3") {
            starter = availableStarters.get(2);
        } else { // DEFAULT
            starter = availableStarters.get(0);
        }

        if (textFieldMonsterNickname.getText().strip().length() > 1) {
            starter.setName(textFieldMonsterNickname.getText().strip());
        }

        // CREATE GAME
        Team team;
        try {
            team = new Team(starter);
            Player player = new Player(playerName, team, GameEnvironment.STARTINGGOLD);
            MainContainer.game = new GameEnvironment(player, numDays, gameDifficulty);
            MainContainer.setUpScreens();
            MainContainer.showScreen("MainMenu");
        } catch (TeamSizeException | DuplicateMonsterException e) {
            ErrorPopUp error = new ErrorPopUp(e.getMessage());
            error.setVisible(true);
            return;
        }

    }
}
