package userInterface;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

// This is a helper class, to make the GridBagLayout initializations
// cleaner and more compact in child-classes:

public abstract class GridBagBasedScreen extends JFrame {

	protected void addGridItem(JPanel panel, JComponent comp,
	        int x, int y, int width, int height, int align) {
		
	    GridBagConstraints gcon = new GridBagConstraints();
	    gcon.gridx = x;
	    gcon.gridy = y;
	    gcon.gridwidth = width;
	    gcon.gridheight = height;
	    gcon.weightx = 0.5;       // a hint on apportioning space
	    gcon.weighty = 0.5;
	    gcon.insets = new Insets(5, 5, 5, 5);   // padding
	    gcon.anchor = align;    // applies if fill is NONE
	    gcon.fill = GridBagConstraints.HORIZONTAL;
	    
	    panel.add(comp, gcon);
	}
	
	protected void addGridItem(JPanel panel, JComponent comp, int x, int y, int width, int height, int align, int fill, float weighty) {
		
	    GridBagConstraints gcon = new GridBagConstraints();
	    gcon.gridx = x;
	    gcon.gridy = y;
	    gcon.gridwidth = width;
	    gcon.gridheight = height;
	    gcon.weightx = 0.5;       // a hint on apportioning space
	    gcon.weighty = weighty;
	    gcon.insets = new Insets(5, 5, 5, 5);   // padding
	    gcon.anchor = align;    // applies if fill is NONE
	    gcon.fill = fill;
	    
	    panel.add(comp, gcon);
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
