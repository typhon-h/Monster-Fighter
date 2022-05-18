package gui;

/**
 * Interface for objects that are able to be previewed in the GUI preview tab
 * @author Harrison Tyson
 * @version 1.0, May. 22
 *
 */
public interface Previewable {

	/**
	 * String representation of the object
	 * @return string description of the object
	 */
    public String toString();

    /**
     * Name to call the object
     * @return  name of the object
     */
    public String getName(); // replace with get image
}
