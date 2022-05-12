package gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SetupPanel extends JPanel {

    /**
     *  Default serial version ID
     */
    private static final long serialVersionUID = 1L;
    
    // TODO: Create panel, can make dummy using swing GUI thing and copy over code
    public SetupPanel() {
        super();
        this.setBackground(Color.blue);
        JLabel lblNewLabel = new JLabel("Hello");
        this.add(lblNewLabel);
    }

}
