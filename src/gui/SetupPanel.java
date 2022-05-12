package gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyEvent;
import java.awt.Insets;
import javax.swing.ImageIcon;

public class SetupPanel extends JPanel {

    /**
     *  Default serial version ID
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldPlayerName;
    private final ButtonGroup difficultyOptions = new ButtonGroup();
    private final ButtonGroup starterMonsters = new ButtonGroup();
    private JTextField textFieldMonsterNickname;
    
    // TODO: Create panel, can make dummy using swing GUI thing and copy over code
    
    public SetupPanel() {
        super();
        setName("Game Setup");
        setMinimumSize(new Dimension(960, 540));
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
        
        
        JSlider sliderDays = new JSlider();
        sliderDays.setPaintLabels(true);
        sliderDays.setMinorTickSpacing(1);
        sliderDays.setMajorTickSpacing(5);
        sliderDays.setSnapToTicks(true);
        sliderDays.setValue(10);
        sliderDays.setMinimum(5);
        sliderDays.setMaximum(15);
        sliderDays.setBounds(6, 357, 265, 52);
        jPanelGameSettings.add(sliderDays);
        
        
        
        JLabel lblNumDays = new JLabel(""+sliderDays.getValue());
        lblNumDays.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblNumDays.setBounds(57, 306, 61, 16);
        jPanelGameSettings.add(lblNumDays);
        sliderDays.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		lblNumDays.setText(""+sliderDays.getValue());
        	}
        });
        
        
        JPanel jPanelStarterMonsters = new JPanel();
        jPanelStarterMonsters.setBorder(null);
        jPanelStarterMonsters.setBounds(394, 55, 177, 429);
        add(jPanelStarterMonsters);
        jPanelStarterMonsters.setLayout(null);
        jPanelStarterMonsters.setBackground(this.getBackground());
        
        JRadioButton btnMonster1Img = new JRadioButton("Monster1 Image");
        starterMonsters.add(btnMonster1Img);
        btnMonster1Img.setSelected(true);
        btnMonster1Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster1Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster1Img.setBounds(39, 23, 100, 100);
        jPanelStarterMonsters.add(btnMonster1Img);
        btnMonster1Img.setIcon(null);
        
        JLabel lblStarters = new JLabel("Start Monster:");
        lblStarters.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblStarters.setBounds(6, 0, 165, 25);
        jPanelStarterMonsters.add(lblStarters);
        
        JLabel lblMonster1Name = new JLabel("Monster1 Name");
        lblMonster1Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster1Name.setBounds(6, 130, 165, 16);
        jPanelStarterMonsters.add(lblMonster1Name);
        
        JRadioButton btnMonster2Img = new JRadioButton("Monster2 Image");
        starterMonsters.add(btnMonster2Img);
        btnMonster2Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster2Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster2Img.setBounds(39, 158, 100, 100);
        jPanelStarterMonsters.add(btnMonster2Img);
        
        JLabel lblMonster2Name = new JLabel("Monster2 Name");
        lblMonster2Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster2Name.setBounds(6, 265, 165, 16);
        jPanelStarterMonsters.add(lblMonster2Name);
        
        JRadioButton btnMonster3Img = new JRadioButton("Monster3 Image");
        starterMonsters.add(btnMonster3Img);
        btnMonster3Img.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnMonster3Img.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnMonster3Img.setBounds(39, 300, 100, 100);
        jPanelStarterMonsters.add(btnMonster3Img);
        
        JLabel lblMonster3Name = new JLabel("Monster3 Name");
        lblMonster3Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonster3Name.setBounds(6, 407, 165, 16);
        jPanelStarterMonsters.add(lblMonster3Name);
        
        JPanel jPanelMonsterInfo = new JPanel();
        jPanelMonsterInfo.setBounds(660, 56, 294, 415);
        add(jPanelMonsterInfo);
        jPanelMonsterInfo.setLayout(null);
        jPanelMonsterInfo.setBackground(this.getBackground());
        
        JLabel lblSelectedMonsterImg = new JLabel("Selected Monster Image");
        lblSelectedMonsterImg.setBounds(78, 1, 150, 150);
        jPanelMonsterInfo.add(lblSelectedMonsterImg);
        
        JLabel lblSelectedMonsterInfo = new JLabel("Selected Monster Info");
        lblSelectedMonsterInfo.setBounds(2, 156, 293, 176);
        jPanelMonsterInfo.add(lblSelectedMonsterInfo);
        
        JLabel lblNickname = new JLabel("Give Nickname (optional):");
        lblNickname.setBounds(3, 357, 173, 16);
        jPanelMonsterInfo.add(lblNickname);
        
        textFieldMonsterNickname = new JTextField();
        textFieldMonsterNickname.setBorder(new LineBorder(new Color(0, 0, 0)));
        textFieldMonsterNickname.setBounds(6, 383, 282, 26);
        jPanelMonsterInfo.add(textFieldMonsterNickname);
        textFieldMonsterNickname.setColumns(10);
        
        JButton btnStartGame = new JButton("Play");
        btnStartGame.setBounds(22, 489, 932, 45);
        add(btnStartGame);
    }
}
