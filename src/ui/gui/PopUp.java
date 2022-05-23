package ui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

/**
 * A class for creating new warning/error/information popups on the screen
 * 
 * @author Harrison Tyson
 * @version 1.0 May 2022
 */
public class PopUp extends JFrame {

    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Panel for popup content
     */
    private JPanel contentPane;

    /**
     * Default width
     */
    private final int POPUPWIDTH = 350;

    /**
     * Default height
     */
    private final int POPUPHEIGHT = 150;

    /**
     * Create the PopUp Window
     * 
     * @param title    title of the window
     * @param message  message to be displayed
     * @param location location of parent window
     */
    public PopUp(String title, String message, Point location) {
        super();
        setAlwaysOnTop(true);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(0, 0, POPUPWIDTH, 150);
        contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(POPUPWIDTH, POPUPHEIGHT));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JTextArea messageDisplay = new JTextArea();
        messageDisplay.setBounds(0, 0, POPUPWIDTH, POPUPHEIGHT - 50);
        messageDisplay.setEditable(false);
        messageDisplay.setLineWrap(true);
        messageDisplay.setWrapStyleWord(true);
        messageDisplay.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        messageDisplay.setBackground(getBackground());

        JScrollPane scroll = new JScrollPane(messageDisplay);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(0, 0, POPUPWIDTH, POPUPHEIGHT - 55);
        scroll.setBackground(getBackground());
        ;
        scroll.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
                Color.black, null));
        this.add(scroll);

        messageDisplay.setText(message);
        messageDisplay.setCaretPosition(0);

        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(close -> {
            this.dispose();
        });
        btnOK.setPreferredSize(new Dimension(POPUPWIDTH, (POPUPHEIGHT - 34) / 4));
        btnOK.setFocusable(false);
        btnOK.setBounds(0, POPUPHEIGHT - 55, POPUPWIDTH, 25);
        contentPane.add(btnOK);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocation(location.x +
                (ui.gui.MainContainer.SCREENWIDTH / 2) -
                this.getWidth() / 2,
                location.y +
                        (ui.gui.MainContainer.SCREENHEIGHT / 2) -
                        this.getHeight() / 2);

        this.setAutoRequestFocus(true);

        this.setAlwaysOnTop(true);

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

        setVisible(true);

        this.getRootPane().setDefaultButton(btnOK);
    }

}
