package gui;

import javax.swing.ImageIcon;

/**
 * Interface for objects that are able to be previewed in the GUI preview tab
 * 
 * @author Harrison Tyson
 * @version 1.0, May. 22
 *
 */
public interface Previewable {

    /**
     * String representation of the object
     * 
     * @return string description of the object
     */
    public String toString();

    /**
     * Image of the object
     * 
     * @return image of the object
     */
    public ImageIcon getImage();
}
