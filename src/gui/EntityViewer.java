package gui;

import static gui.MainContainer.DEFAULTDIMENSION;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextPane;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.Entity;

/**
 * A superclass used to place the common elements of each panel onto the
 * subclass panels
 * 
 * @author Harrison Tyson
 * @version 1.0 May, 2022
 */
public class EntityViewer extends JPanel {

    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for player gold
     */
    private JLabel lblPlayerGold;

    /**
     * Label for player score
     */
    private JLabel lblPlayerScore;

    /**
     * Label for current day
     */
    private JLabel lblCurrentDay;

    /**
     * Label for image of previewed entity
     */
    private JLabel lblPreviewEntityImg;

    /**
     * Description of previewed entity
     */
    private JTextPane textPanePreviewEntityDesc;

    /**
     * Button to return to menu
     */
    private JButton btnBack;

    /**
     * {@link ArrayList ArrayList} of {@link gui.ContentPanel ContentPanels} on
     * screen
     */
    protected ArrayList<ContentPanel> contentPanels = new ArrayList<ContentPanel>();

    /**
     * Default x position of a content panel
     */
    private static final int DEFAULTCONTENTX = 6;

    /**
     * Default y position of a content panel
     */
    private static final int DEFAULTCONTENTY = 50;

    /**
     * Default width of a content panel
     */
    private static final int DEFAULTCONTENTWIDTH = 600;

    /**
     * Default height of a content panel
     */
    private static final int DEFAULTCONTENTHEIGHT = 480;

    /**
     * Default number of entities wide within a content panel
     */
    private static final int DEFAULTDISPLAYWIDE = 2;

    /**
     * Create and place the common elements on the panel, called by the subclass
     * 
     * @param title         {@link String String} title of the panel to be displayed
     * @param hasPlayerInfo Flag for whether the panel needs the player information
     * @param hasPreview    Flag for whether the panel needs a preview panel
     * @param hasBack       Flag for whether the panel needs a back button
     */
    public EntityViewer(String title, boolean hasPlayerInfo, boolean hasPreview, boolean hasBack) {
        super();
        setMinimumSize(DEFAULTDIMENSION);
        setSize(DEFAULTDIMENSION);
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setBounds(430, 0, 190, 37);
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 23));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle);

        if (hasPlayerInfo) {
            initializePlayerInfo();
        }

        if (hasPreview) {
            initializePreview();
        }

        if (hasBack) {
            btnBack = new JButton("Back");
            btnBack.addActionListener(back -> {
                MainContainer.showScreen("MainMenu");
            });
            btnBack.setBounds(6, 6, 82, 40);
            add(btnBack);
        }

    }

    /**
     * Initialize the player information
     */
    private void initializePlayerInfo() {

        JLabel lblName = new JLabel("Name: ");
        lblName.setFont(new Font("Lucida Grande", Font.BOLD, 19));
        lblName.setBounds(90, 6, 70, 21);
        add(lblName);

        JLabel lblPlayerName = new JLabel(MainContainer.game.getPlayer().getName());
        lblPlayerName.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        lblPlayerName.setBounds(150, 6, 166, 21);
        add(lblPlayerName);

        JLabel lblDay = new JLabel("Day:");
        lblDay.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        lblDay.setBounds(280, 6, 51, 21);
        add(lblDay);

        lblCurrentDay = new JLabel(
                MainContainer.game.getCurrentDay() + "/" + MainContainer.game.getTotalDays());
        lblCurrentDay.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblCurrentDay.setBounds(330, 6, 61, 20);
        add(lblCurrentDay);

        JLabel lblScore = new JLabel("Score: ");
        lblScore.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        lblScore.setBounds(660, 6, 70, 21);
        add(lblScore);

        lblPlayerScore = new JLabel("" + MainContainer.game.getPlayer().getScore());
        lblPlayerScore.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblPlayerScore.setBounds(730, 6, 61, 20);
        add(lblPlayerScore);

        JLabel lblGold = new JLabel("Gold:");
        lblGold.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        lblGold.setBounds(800, 6, 61, 21);
        add(lblGold);

        lblPlayerGold = new JLabel("" + MainContainer.game.getPlayer().getGold());
        lblPlayerGold.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblPlayerGold.setBounds(860, 6, 61, 20);
        add(lblPlayerGold);

    }

    /**
     * Initialize the preview panel
     */
    private void initializePreview() {
        JPanel preview = new JPanel();
        preview.setBorder(new LineBorder(new Color(0, 0, 0)));
        preview.setBounds(690, 50, 295, 432);
        preview.setBackground(this.getBackground());
        add(preview);
        preview.setLayout(null);

        lblPreviewEntityImg = new JLabel("Selected Entity Image");
        lblPreviewEntityImg.setBounds(48, 6, 200, 200);
        lblPreviewEntityImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviewEntityImg.setVerticalAlignment(SwingConstants.CENTER);
        preview.add(lblPreviewEntityImg);

        textPanePreviewEntityDesc = new JTextPane();
        textPanePreviewEntityDesc.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPanePreviewEntityDesc.setEditable(false);
        textPanePreviewEntityDesc.setBounds(6, 249, 283, 176);
        textPanePreviewEntityDesc.setBackground(this.getBackground());
        preview.add(textPanePreviewEntityDesc);
    }

    /**
     * Update the player information
     */
    protected void updatePlayerInfo() {
        lblPlayerGold.setText("" + MainContainer.game.getPlayer().getGold());
        lblPlayerScore.setText("" + MainContainer.game.getPlayer().getScore());
        lblCurrentDay.setText(MainContainer.game.getCurrentDay() + "/" + MainContainer.game.getTotalDays());
    }

    /**
     * Update the preview panel
     * 
     * @param options Button group with the selected button
     * @param objects Previewable objects to get the image for
     */
    protected void updatePreview(ButtonGroup options, Object[] objects) {
        if (options.getSelection() != null) {
            int index = Integer.parseInt(options.getSelection().getActionCommand());
            if (objects.length == 0 || index < 0 || index >= objects.length) { // TODO: remove if statement once dynamic
                                                                               // buttons
                return;
            }
            Object o = objects[index];
            if (o instanceof Previewable) {
                lblPreviewEntityImg.setIcon(((Previewable) objects[index]).getImage());
                lblPreviewEntityImg.setText(null);
                textPanePreviewEntityDesc.setText(((Previewable) objects[index]).toString());
                return;
            }

        }

        lblPreviewEntityImg.setText("Nothing to do here");
        textPanePreviewEntityDesc.setText("");
        lblPreviewEntityImg.setIcon(null);
    }

    /**
     * Select the first button available in the button group
     * 
     * @param content The button group to select the button from
     */
    protected void selectFirstAvailableButton(ButtonGroup content) {
        List<AbstractButton> availableButtons = Collections.list(content.getElements());
        if (availableButtons.size() > 0) { // TODO: I think this will work once buttons are dynamic??
            availableButtons.get(0).setSelected(true);
        } else {
            content.clearSelection();
        }

    }

    /**
     * Adds a content panel to screen with specified dimensions
     * 
     * @param width          width of panel
     * @param height         height of panel
     * @param posX           x position of panel in frame
     * @param posY           y position of panel in frame
     * @param numDisplayWide number of entities in a single row
     */
    protected void createContentPanel(int width, int height, int posX, int posY, int numDisplayWide) {
        ContentPanel panelToAdd = new ContentPanel(width, height, posX, posY, numDisplayWide, this.getBackground());
        add(panelToAdd.getPanel());
        contentPanels.add(panelToAdd);
    }

    /**
     * Adds a content panel to screen with default dimensions
     */
    protected void createContentPanel() {
        ContentPanel panelToAdd = new ContentPanel(DEFAULTCONTENTWIDTH, DEFAULTCONTENTHEIGHT, DEFAULTCONTENTX,
                DEFAULTCONTENTY, DEFAULTDISPLAYWIDE, this.getBackground());
        add(panelToAdd.getPanel());
        contentPanels.add(panelToAdd);
    }

    /**
     * Updates all content panels
     * 
     * @param content {@link ArrayList ArrayList} of Object arrays containing
     *                content to update the panels with
     */
    protected void updateContentPanels(ArrayList<Object[]> content) {
        if (content.size() != contentPanels.size()) {
            throw new RuntimeException("Available content does not match content panels");
        }
        for (int i = 0; i < contentPanels.size(); i++) {
            ContentPanel panel = contentPanels.get(i);
            Object[] contentOfPanel = content.get(i);
            panel.setContent(contentOfPanel);
            if (i == 0) { // Only the first content panel has a select action
                panel.update(update -> {
                    updatePreview(panel.getButtons(), panel.getContent().toArray());
                }, getDescriptions(panel.getContent()));
            } else {
                panel.update(null, getDescriptions(panel.getContent()));
            }
        }
    }

    /**
     * Gets content description based on screen it is displayed on
     * 
     * @param content {@link ArrayList ArrayList} of {@link main.Entity entities} to
     *                be displayed
     * @return {@link ArrayList ArrayList} of string descriptions
     */
    private ArrayList<String> getDescriptions(ArrayList<Entity> content) {
        ArrayList<String> desc = new ArrayList<String>();
        for (Entity e : content) {
            if (this instanceof BuyShopPanel) {
                desc.add("\n" + e.getBuyPrice() + "G \n"
                        + e.getRarity().name() + "\n"
                        + e.getName());
            } else if (this instanceof SellShopPanel) {
                desc.add("\n" + e.getSellPrice() + "G \n"
                        + e.getRarity().name() + "\n"
                        + e.getName());
            } else if (this instanceof TeamPanel) {
                desc.add("\nPosition: " + (MainContainer.game.getPlayer().getTeam().getMonsters().indexOf(e) + 1) + "\n"
                        + e.getName());
            } else if (this instanceof InventoryPanel) {
                desc.add("\n" + e.getRarity().name() + "\n" + e.getName());
            }
        }

        return desc;
    }

}
