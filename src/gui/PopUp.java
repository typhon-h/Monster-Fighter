package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * A class for creating new warning/error/information popups on the screen
 * 
 * @author Harrison Tyson
 * @version 1.0 Mar 2022
 */
public class PopUp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int popUpWidth = 350;
    private int popUpHeight = 150;

    /**
     * Create the new frame of the popup to be displayed
     */
    public PopUp(String title, String message, Point location) {
        setType(Type.POPUP);
        setAlwaysOnTop(true);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(0, 0, popUpWidth, 150);
        contentPane = new JPanel();
        // contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBounds(0, 0, popUpWidth, popUpHeight);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setContentPane(contentPane);

        JLabel lblmessage = new JLabel(message);
        lblmessage.setVerticalAlignment(SwingConstants.TOP);
        lblmessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblmessage.setPreferredSize(new Dimension(popUpWidth, ((popUpHeight - 34) * 3) / 4));
        contentPane.add(lblmessage);

        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(close -> {
            this.dispose();
        });
        btnOK.setPreferredSize(new Dimension(popUpWidth, (popUpHeight - 34) / 4));
        btnOK.setFocusable(false);
        contentPane.add(btnOK);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocation(location.x +
                (gui.MainContainer.SCREENWIDTH / 2) -
                this.getWidth() / 2,
                location.y +
                        (gui.MainContainer.SCREENHEIGHT / 2) -
                        this.getHeight() / 2);

        setVisible(true);

        // Closes when loses focus
        this.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                e.getWindow().dispose();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                // Do Nothing

            }

        });

        this.getRootPane().setDefaultButton(btnOK);
    }

}
