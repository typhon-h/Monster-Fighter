package gui;

import static gui.MainContainer.DEFAULTDIMENSION;

import java.awt.Color;
import java.awt.Font;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;


public class EntityViewer extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblPlayerGold;
    private JLabel lblPlayerScore;
    private JLabel lblCurrentDay;

    private JLabel lblPreviewEntityImg;
    private JTextPane textPanePreviewEntityDesc;

    private JButton btnBack;

    public EntityViewer(boolean hasPlayerInfo, boolean hasPreview, boolean hasBack) {
        super();
        setMinimumSize(DEFAULTDIMENSION);
        setSize(DEFAULTDIMENSION);
        setVerifyInputWhenFocusTarget(false);
        this.setBackground(Color.GRAY);
        setLayout(null);

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

    private void initializePreview() {
        JPanel preview = new JPanel();
        preview.setBorder(new LineBorder(new Color(0, 0, 0)));
        preview.setBounds(690, 50, 295, 432);
        preview.setBackground(this.getBackground());
        add(preview);
        preview.setLayout(null);

        lblPreviewEntityImg = new JLabel("Selected Entity Image");
        lblPreviewEntityImg.setBounds(48, 6, 200, 200);
        preview.add(lblPreviewEntityImg);

        textPanePreviewEntityDesc = new JTextPane();
        textPanePreviewEntityDesc.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        textPanePreviewEntityDesc.setEditable(false);
        textPanePreviewEntityDesc.setBounds(6, 249, 283, 176);
        textPanePreviewEntityDesc.setBackground(this.getBackground());
        preview.add(textPanePreviewEntityDesc);
    }

    protected void updatePlayerInfo() {
        lblPlayerGold.setText("" + MainContainer.game.getPlayer().getGold());
        lblPlayerScore.setText("" + MainContainer.game.getPlayer().getScore());
        lblCurrentDay.setText(MainContainer.game.getCurrentDay() + "/" + MainContainer.game.getTotalDays());
    }

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
                textPanePreviewEntityDesc.setText(((Previewable) objects[index]).toString());
                return;
            }

        }

        lblPreviewEntityImg.setText("Nothing to do here");
        textPanePreviewEntityDesc.setText("");

    }

    protected void selectFirstAvailableButton(ButtonGroup content) {
        List<AbstractButton> availableButtons = Collections.list(content.getElements());
        if (availableButtons.size() > 0) { // TODO: I think this will work once buttons are dynamic??
            availableButtons.get(0).setSelected(true);
        } else {
            content.clearSelection();
        }

    }
}
