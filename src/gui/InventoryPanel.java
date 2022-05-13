package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InventoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public InventoryPanel() {
        super();
        setName("Inventory");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);
        
        
        JLabel lblInventoryTitle = new JLabel("Inventory");
        lblInventoryTitle.setBounds(392, 6, 159, 37);
        lblInventoryTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblInventoryTitle);
	}

}
