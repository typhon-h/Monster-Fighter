package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SellShopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public SellShopPanel() {
        super();
        setName("SellShop");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);
        
        
        JLabel lblSellShopTitle = new JLabel("Sell Shop");
        lblSellShopTitle.setBounds(400, 6, 151, 37);
        lblSellShopTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblSellShopTitle);
	}

}
