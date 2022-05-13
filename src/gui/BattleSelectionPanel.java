package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BattleSelectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public BattleSelectionPanel() {
        super();
        setName("BattleSelection");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);
        
        
        JLabel lblBattleSelectionTitle = new JLabel("Battle Selection");
        lblBattleSelectionTitle.setBounds(378, 6, 246, 37);
        lblBattleSelectionTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblBattleSelectionTitle);
	}

}
