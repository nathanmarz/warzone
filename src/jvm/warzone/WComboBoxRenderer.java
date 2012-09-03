package warzone;

import java.awt.*;
import javax.swing.*;


class WComboBoxRenderer extends JLabel
                       implements ListCellRenderer {
	
	private Font uhOhFont;
	private WResource resource;
	private WViewer viewer;
	
    public WComboBoxRenderer(WResource resource1, WViewer viewer1) {
        setOpaque(true);
        viewer = viewer1;
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        resource = resource1;
    }

    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {
        //Get the selected index. (The index param isn't
        //always valid, so just use the value.)
    	WStoreItem selected = (WStoreItem)value;

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
        ImageIcon icon = new ImageIcon(resource.getUnitPic(selected.getUnit(), viewer.whoseTurn()));
        String bufferStr = "";
        if(selected.getPrice()<10) bufferStr = "   ";
        String str = bufferStr+selected.getPrice();
        setIcon(icon);
        
        setText(str);
        setFont(list.getFont());
       

       return this;
    }

}
