package bl0rg.vj.polymaniac;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import processing.core.PApplet;

public class PolymaniacPresentFrame extends PolymaniacFrame {
	Label label;
	
	PolymaniacPresentFrame(PApplet applet, GraphicsConfiguration configuration ) {
		super(applet, configuration);
	    
		Color backgroundColor = Color.black; //BLACK;
		Color stopColor = Color.gray; //GRAY;

		GraphicsDevice displayDevice = configuration.getDevice();
		
		setUndecorated(true);
        setBackground(backgroundColor);
        displayDevice.setFullScreenWindow(this);

        add(applet);
        Dimension fullscreen = getSize();
        applet.setBounds((fullscreen.width - applet.width) / 2,
                         (fullscreen.height - applet.height) / 2,
                         applet.width, applet.height);

        /* No Stop button  why size is all when bla?? */

        label = new Label("");
        label.setForeground(stopColor);
        add(label);

        /*
        Dimension labelSize = label.getPreferredSize();
        // sometimes shows up truncated on mac
        System.out.println("label width is " + labelSize.width);
        labelSize = new Dimension(100, labelSize.height);
        label.setSize(labelSize);
        label.setLocation(20, fullscreen.height - labelSize.height - 20);
        */
        
   	}
	
	public void startFrame() {
		super.startFrame();
		label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	applet.stop();
              System.exit(0);
            }
          });
        applet.requestFocus(); // ask for keydowns
	}
}
