package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JLabel;

public class MainContainer {

    private JFrame monsterGameFrame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainContainer window = new MainContainer();
                    window.monsterGameFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainContainer() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        monsterGameFrame = new JFrame();
        monsterGameFrame.setBounds(100, 100, 450, 300);
        monsterGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        // TODO: Each menu panel needs to be its own class?
        JPanel mainContainerPanel = new JPanel();
        monsterGameFrame.getContentPane().add(mainContainerPanel, BorderLayout.CENTER);
        CardLayout cardLayout = new CardLayout(0, 0);
        mainContainerPanel.setLayout(cardLayout);
        
        SetupPanel setUpPanel = new SetupPanel();
        mainContainerPanel.add(setUpPanel, "Setup");
        

        
        JPanel mainMenuPanel = new JPanel();
        mainContainerPanel.add(mainMenuPanel, "Main menu");
        mainMenuPanel.setBackground(Color.orange);
        
        // TODO: Make this JPanel a new class that inherits JPanel with arguments
        // for settings the content of the panel.
        // TODO: dynamically rename this Panel
        JPanel commonMenuPanel = new JPanel();
        mainContainerPanel.add(commonMenuPanel, "Menu");
        
        JPanel gameOverPanel = new JPanel();
        mainContainerPanel.add(gameOverPanel, "Game over");
        
        JPanel battlePanel = new JPanel();
        mainContainerPanel.add(battlePanel, "Battle");
        
        // Show default setup panel
        cardLayout.show(mainContainerPanel, "SetupPanel"); // <-- Change string arg to change JPanel shown
        
        // TODO: Use this dummy panel to create UI then just copy paste code over to own panel classes for setup?
        // Could do this whole thing another way if you would like
        JPanel dummyDevPanel = new JPanel();
        mainContainerPanel.add(dummyDevPanel, "name_22009086645600");
        
    }

}
