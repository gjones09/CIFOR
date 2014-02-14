package c4ClusterReport;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GUI_Layout
	{
	    public		static	int			newWidth;
	    public		static	int			newHeight;
	    public		static	int			insetX;
	    public		static	int			insetY;
	    
	    static void ckDimensions() {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			
	        insetX 		= 0;
	        insetY 		= 0;
			newWidth 	= 0;
			newHeight 	= 0;

			if 		(d.width > 1900) {newWidth = d.width-300; newHeight = d.height-100; insetX = 10; insetY = 20;} //insetX = 100
			else if	(d.width < 1900) {newWidth = d.width-75; newHeight = d.height-50; insetX = 20; insetY = 20;}
	    	}
	}
