package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuyShopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public BuyShopPanel() {
        super();
        setName("BuyShop");
        setMinimumSize(new Dimension(MainContainer.SCREENWIDTH, MainContainer.SCREENHEIGHT));
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);
        
        
        JLabel lblBuyShopTitle = new JLabel("Buy Shop");
        lblBuyShopTitle.setBounds(395, 6, 150, 37);
        lblBuyShopTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        add(lblBuyShopTitle);
	}

}
