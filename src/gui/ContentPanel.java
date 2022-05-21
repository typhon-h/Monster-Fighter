package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import main.Entity;

import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Panel to display content as selectable radio buttons with descriptions
 */
public class ContentPanel extends JPanel {

    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Content displayed on the panel
     */
    private ArrayList<Entity> contentToDisplay;

    /**
     * Buttons connected to the content
     */
    private ButtonGroup contentButtons = new ButtonGroup();

    /**
     * Pane where content is displayed
     */
    private JScrollPane scrollPane;

    /**
     * Panel containing entities
     */
    private JPanel entityDisplay;

    /**
     * Default entity width
     */
    private static final int ENTITYWIDTH = 120;

    /**
     * Default entity height
     */
    private static final int ENTITYHEIGHT = 120;

    /**
     * Width of the panel
     */
    private int panelWidth;

    /**
     * Height of the panel
     */
    private int panelHeight;

    /**
     * Width of each {@link main.Entity entity} container
     */
    private int entityContainerWidth;

    /**
     * Space between entities in Panel
     */
    private int entityContainerGap;

    /**
     * Size of the entity display
     */
    private Dimension entityDisplayDimension;
    
    /**
     * Number of entities to display on each row
     */
    private int numRowEntites;

    /**
     * Create a content panel display with specified dimensions
     * 
     * @param width           width of the panel
     * @param height          height of the panel
     * @param posX            x position within the frame
     * @param posY            y position within the fram
     * @param numDisplayWide  number of elements displayed across
     * @param backgroundColor background color of panel
     */
    public ContentPanel(int width, int height, int posX, int posY,
            int numDisplayWide, Color backgroundColor) {
        this.panelHeight = height;
        this.panelWidth = width;
        this.setBackground(backgroundColor);
        

        // Container has image and description
        entityContainerWidth = ENTITYWIDTH * 2;
        entityContainerGap = (width -
                (numDisplayWide * entityContainerWidth)) / 4;
        
        this.numRowEntites = width / (entityContainerWidth + entityContainerGap);

        FlowLayout entityContainerLayout = new FlowLayout(FlowLayout.LEFT,
                entityContainerGap,
                entityContainerGap);
        entityDisplayDimension = new Dimension(width,
                height);
        entityDisplay = new JPanel();
        entityDisplay.setLayout(entityContainerLayout);
        // TODO: Remove this commented out code if no bugs :)
//        entityDisplay.setMaximumSize(new Dimension(width, 2000));
        entityDisplay.setPreferredSize(entityDisplayDimension);
        entityDisplay.setBackground(getBackground());

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        scrollPane.setBounds(posX, posY, width + 35, height);
        scrollPane.setMaximumSize(scrollPane.getSize());
        scrollPane.setViewportView(entityDisplay);
        this.add(scrollPane);
    }

    /**
     * Updates the panel with display content
     * 
     * @param buttonAction action when button is selected
     * @param descriptions ArrayList of descriptions for each button
     */
    public void update(ActionListener buttonAction, ArrayList<String> descriptions) {
        while (descriptions.size() < contentToDisplay.size()) {
            descriptions.add("No description available");
        }
        while (descriptions.size() > contentToDisplay.size()) {
            descriptions.remove(descriptions.size() - 1);
        }

        entityDisplay.removeAll();
        contentButtons = new ButtonGroup();

        EtchedBorder entityContainerBorder = new EtchedBorder(EtchedBorder.LOWERED,
                Color.black, null);
        Dimension entityContainerDimension = new Dimension(entityContainerWidth,
                ENTITYHEIGHT);
        JPanel entityContainer;
        JRadioButton entityButton;
        JTextPane entityTextPane;
        // Create buttons
        for (int i = 0; i < contentToDisplay.size(); i++) {
            Entity entity = contentToDisplay.get(i);
            String desc = descriptions.get(i);
            entityContainer = new JPanel();
            entityContainer.setPreferredSize(entityContainerDimension);
            entityContainer.setLayout(new GridLayout(1, 2));
            entityContainer.setOpaque(false);
            entityButton = new JRadioButton();
            entityButton.setIcon(MainContainer.imageResize(entity.getImage(),
                    ENTITYWIDTH,
                    ENTITYHEIGHT));
            entityButton.setOpaque(false);
            entityButton.setBorder(entityContainerBorder);
            entityButton.setActionCommand(String.valueOf(contentToDisplay.indexOf(entity)));
            entityButton.addActionListener(buttonAction);
            contentButtons.add(entityButton);
            entityContainer.add(entityButton);

            entityTextPane = new JTextPane();
            entityTextPane.setText(desc);
            entityTextPane.setEditable(false);
            entityTextPane.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
            entityTextPane.setOpaque(false);
            entityTextPane.setBorder(entityContainerBorder);
            entityContainer.add(entityTextPane);

            entityDisplay.add(entityContainer);
        }

        int height = (int) ((Math.ceil((float) contentToDisplay.size() / (float) numRowEntites)) *
                (ENTITYHEIGHT + entityContainerGap));
        height = height > this.panelHeight ? height : this.panelHeight;
        entityDisplayDimension = new Dimension(this.panelWidth,
                height);
        entityDisplay.setPreferredSize(entityDisplayDimension);
        entityDisplay.updateUI();
        scrollPane.updateUI();
    }

    /**
     * Gets content associated with panel
     * 
     * @return {@link ArrayList ArrayList} of panel content
     */
    public ArrayList<Entity> getContent() {
        return contentToDisplay;
    }

    /**
     * Sets the content of the panel
     * 
     * @param content array of objects that are instances of {@link main.Entity
     *                Entity}
     */
    public void setContent(Object[] content) {
        contentToDisplay = new ArrayList<Entity>();
        for (Object e : content) {
            if (e instanceof Entity) {
                contentToDisplay.add((Entity) e);
            } else {
                throw new IllegalArgumentException("Arguments must be instance of Entity");
            }
        }
    }

    /**
     * Gets the buttons in the panel
     * 
     * @return {@link ButtonGroup ButtonGroup} of content buttons
     */
    public ButtonGroup getButtons() {
        return contentButtons;
    }

    /**
     * Gets the visible content section of the panel
     * 
     * @return {@link JScrollPane ScrollPane} containing active content
     */
    public JScrollPane getPanel() {
        return scrollPane;
    }

}
