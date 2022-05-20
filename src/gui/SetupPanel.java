package gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
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

/**
 * Panel for setting up the game
 * @author Harrison Tyson
 * @version 1.0 Mar, 2022
 */
public class SetupPanel extends EntityViewer {

    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldPlayerName;
    private final ButtonGroup difficultyOptions = new ButtonGroup();
    private final ButtonGroup starterMonsters = new ButtonGroup();
    private JTextField textFieldMonsterNickname;
    private JSlider sliderDays;
    private ArrayList<Monster> availableStarters;
    private String nameValidation = "[a-zA-Z]{3,15}";

    private JRadioButton btnMonster1Img;
    private JRadioButton btnMonster2Img;
    private JRadioButton btnMonster3Img;
    private JLabel lblMonster1Name;
    private JLabel lblMonster2Name;
    private JLabel lblMonster3Name;
    private JRadioButton rdbtnDifficultyEasy;
    private JRadioButton rdbtnDifficultyNormal;
    private JRadioButton rdbtnDifficultyHard;
    
    /**
     * Set up the panel with all the fields to be entered in
     */
    public SetupPanel() {
        super(false, true, false);

        JLabel lblSetupTitle = new JLabel("Game Setup");
        lblSetupTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        lblSetupTitle.setBounds(430, 6, 193, 37);
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

        rdbtnDifficultyEasy = new JRadioButton("Easy");
        rdbtnDifficultyEasy.setMnemonic(KeyEvent.VK_E);
        rdbtnDifficultyEasy.setBorder(UIManager.getBorder("RadioButton.border"));
        difficultyOptions.add(rdbtnDifficultyEasy);
        rdbtnDifficultyEasy.setBounds(10, 131, 141, 23);
        rdbtnDifficultyEasy.setOpaque(false);
        rdbtnDifficultyEasy.setFocusable(false);
        jPanelGameSettings.add(rdbtnDifficultyEasy);

        rdbtnDifficultyNormal = new JRadioButton("Normal");
        rdbtnDifficultyNormal.setMnemonic(KeyEvent.VK_N);
        rdbtnDifficultyNormal.setSelected(true);
        difficultyOptions.add(rdbtnDifficultyNormal);
        rdbtnDifficultyNormal.setBounds(10, 185, 141, 23);
        rdbtnDifficultyNormal.setOpaque(false);
        rdbtnDifficultyNormal.setFocusable(false);
        jPanelGameSettings.add(rdbtnDifficultyNormal);

        rdbtnDifficultyHard = new JRadioButton("Hard");
        rdbtnDifficultyHard.setMnemonic(KeyEvent.VK_H);
        difficultyOptions.add(rdbtnDifficultyHard);
        rdbtnDifficultyHard.setBounds(10, 236, 141, 23);
        rdbtnDifficultyHard.setOpaque(false);
        rdbtnDifficultyHard.setFocusable(false);
        jPanelGameSettings.add(rdbtnDifficultyHard);

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
        sliderDays.setOpaque(false);
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

        // starter monster selection
        setStartMonsterSelection();

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

        btnMonster1Img = new JRadioButton();
        btnMonster1Img.addActionListener(update -> {
            super.updatePreview(starterMonsters, availableStarters.toArray());
        });
        btnMonster1Img.setActionCommand("0");
        starterMonsters.add(btnMonster1Img);
        btnMonster1Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster1Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster1Img.setBounds(39, 23, 100, 100);
        btnMonster1Img.setFocusable(false);
        btnMonster1Img.setIcon(null);
        btnMonster1Img.setOpaque(false);
        jPanelStarterMonsters.add(btnMonster1Img);

        lblMonster1Name = new JLabel(availableStarters.get(0).getName());
        lblMonster1Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster1Name.setBounds(6, 130, 165, 16);
        jPanelStarterMonsters.add(lblMonster1Name);

        btnMonster2Img = new JRadioButton();
        btnMonster2Img.addActionListener(update -> {
            super.updatePreview(starterMonsters, availableStarters.toArray());
        });
        btnMonster2Img.setActionCommand("1");
        starterMonsters.add(btnMonster2Img);
        btnMonster2Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster2Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster2Img.setBounds(39, 158, 100, 100);
        btnMonster2Img.setFocusable(false);
        btnMonster2Img.setOpaque(false);
        jPanelStarterMonsters.add(btnMonster2Img);

        lblMonster2Name = new JLabel(availableStarters.get(1).getName());
        lblMonster2Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster2Name.setBounds(6, 265, 165, 16);
        jPanelStarterMonsters.add(lblMonster2Name);

        btnMonster3Img = new JRadioButton();
        btnMonster3Img.addActionListener(update -> {
            super.updatePreview(starterMonsters, availableStarters.toArray());
        });
        btnMonster3Img.setActionCommand("2");
        starterMonsters.add(btnMonster3Img);
        btnMonster3Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster3Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster3Img.setBounds(39, 300, 100, 100);
        btnMonster3Img.setFocusable(false);
        btnMonster3Img.setOpaque(false);
        jPanelStarterMonsters.add(btnMonster3Img);

        lblMonster3Name = new JLabel(availableStarters.get(2).getName());
        lblMonster3Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster3Name.setBounds(6, 407, 165, 16);
        jPanelStarterMonsters.add(lblMonster3Name);

        JLabel lblNickname = new JLabel("Give Nickname (optional):");
        lblNickname.setBounds(690, 500, 173, 16);
        add(lblNickname);

        textFieldMonsterNickname = new JTextField();
        textFieldMonsterNickname.setBorder(new LineBorder(new Color(0, 0, 0)));
        textFieldMonsterNickname.setBounds(865, 500, 120, 26);
        add(textFieldMonsterNickname);
        textFieldMonsterNickname.setColumns(10);

        JButton btnStartGame = new JButton("Play");
        btnStartGame.addActionListener(setup -> {
            setUpGame();
        });
        btnStartGame.setBounds(22, 489, 650, 45);
        btnStartGame.setFocusable(false);
        add(btnStartGame);

        updateMonsterSelectionLabels();
        super.selectFirstAvailableButton(starterMonsters);
        super.updatePreview(starterMonsters, availableStarters.toArray());
    }
    
    /**
     * reset the values on the setup screen back to default
     */
    protected void resetGame() {
        this.sliderDays.setValue(10);
        this.textFieldPlayerName.setText(null);
        this.difficultyOptions.clearSelection();
        this.rdbtnDifficultyNormal.setSelected(true);
        this.setStartMonsterSelection();
        this.updateMonsterSelectionLabels();
        super.selectFirstAvailableButton(starterMonsters);
        super.updatePreview(starterMonsters, availableStarters.toArray());
        MainContainer.showScreen("Setup");
    }

    /**
     * Generate and populate the starter monsters the player can choose from
     */
    private void setStartMonsterSelection() {
        int numStarters = GameEnvironment.NUMSTARTERMONSTERS;
        ArrayList<Monster> possibleStarters = GameEnvironment.generateMonsters();
        availableStarters = new ArrayList<Monster>();
        for (int i = 0; i < numStarters; i++) {
            Monster m = possibleStarters.get(GameEnvironment.rng.nextInt(possibleStarters.size()));
            availableStarters.add(m);
            possibleStarters.remove(m);
        }
    }

    /**
     * Update the buttons for selecting mosnters
     */
    private void updateMonsterSelectionLabels() {
        btnMonster1Img.setIcon(MainContainer.imageResize(availableStarters.get(0).getImage(), btnMonster1Img.getWidth(),
                btnMonster1Img.getWidth()));
        btnMonster2Img.setIcon(MainContainer.imageResize(availableStarters.get(1).getImage(), btnMonster2Img.getWidth(),
                btnMonster2Img.getWidth()));
        btnMonster3Img.setIcon(MainContainer.imageResize(availableStarters.get(2).getImage(), btnMonster3Img.getWidth(),
                btnMonster3Img.getWidth()));
        lblMonster1Name.setText(availableStarters.get(0).getName());
        lblMonster2Name.setText(availableStarters.get(1).getName());
        lblMonster3Name.setText(availableStarters.get(2).getName());
    }

    /**
     * Set up the game with the values the player entered in
     */
    private void setUpGame() {
        // Player Name
        String playerName = textFieldPlayerName.getText();
        if (!playerName.matches(nameValidation)) {
            new PopUp("Error", "<html>Name must be 3-15 characters<br />No numbers or special characters</html>",
                    this.getLocationOnScreen());
            textFieldPlayerName.setText("");
            textFieldPlayerName.grabFocus();
            return;
        }

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
        String selectedStarter = starterMonsters.getSelection().getActionCommand();
        int index = Integer.parseInt(selectedStarter);
        Monster starter = availableStarters.get(index);
        String monsterName = textFieldMonsterNickname.getText().strip();
        if (monsterName.length() > 0) {
            if (!monsterName.matches(nameValidation)) {
                new PopUp("Error",
                        "<html>Monster name must be 3-15 characters<br />No numbers or special characters</html>",
                        this.getLocationOnScreen());
                textFieldMonsterNickname.setText("");
                textFieldMonsterNickname.grabFocus();
                return;
            } else {
                starter.setName(monsterName);
            }

        }

        // CREATE GAME
        try {
            Team team = new Team(starter);
            Player player = new Player(playerName, team, GameEnvironment.STARTINGGOLD);
            MainContainer.game = new GameEnvironment(player, numDays, gameDifficulty);
            MainContainer.setUpScreens();
            MainContainer.showScreen("MainMenu");
        } catch (TeamSizeException | DuplicateMonsterException e) {
            new PopUp("Error", e.getMessage(), this.getLocationOnScreen());
        }

    }
}
