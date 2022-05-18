package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Point;

public class PopUp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public PopUp(String title, String message, Point location) {
        setType(Type.POPUP);
        setAlwaysOnTop(true);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(gui.MainContainer.SCREENWIDTH / 2, gui.MainContainer.SCREENHEIGHT / 2, 350, 150);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblmessage = new JLabel(message);
        lblmessage.setVerticalAlignment(SwingConstants.TOP);
        lblmessage.setBounds(6, 6, 338, 72);
        contentPane.add(lblmessage);

        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(close -> {
            this.dispose();
        });
        btnOK.setBounds(16, 87, 328, 29);
        contentPane.add(btnOK);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLocation(location.x +
                (gui.MainContainer.SCREENWIDTH / 2) -
                this.getWidth() / 2,
                location.y +
                        (gui.MainContainer.SCREENHEIGHT / 2) -
                        this.getHeight() / 2);

        this.setVisible(true);
    }

}
