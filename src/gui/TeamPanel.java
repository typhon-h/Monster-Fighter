package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TeamPanel() {
        super();
        setName("Team");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);
        
        
        JLabel lblTeamTitle = new JLabel("Team");
        lblTeamTitle.setBounds(410, 6, 95, 37);
        lblTeamTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblTeamTitle);
	}

}
