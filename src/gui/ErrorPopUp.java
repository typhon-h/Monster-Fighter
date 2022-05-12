package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class ErrorPopUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ErrorPopUp(String message) {
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setTitle("Error");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblErrorMessage = new JLabel(message);
		lblErrorMessage.setVerticalAlignment(SwingConstants.TOP);
		lblErrorMessage.setBounds(6, 6, 338, 72);
		contentPane.add(lblErrorMessage);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(close -> {this.dispose();});
		btnOK.setBounds(16, 87, 328, 29);
		contentPane.add(btnOK);
	}

}
