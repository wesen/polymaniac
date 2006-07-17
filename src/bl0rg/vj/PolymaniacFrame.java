package bl0rg.vj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import processing.core.PApplet;

public class PolymaniacFrame extends Frame {
	PApplet applet;
	
	PolymaniacFrame(PApplet applet) {
		this(applet, (GraphicsConfiguration)null);
	}
	
    PolymaniacFrame(PApplet applet, GraphicsConfiguration configuration) {
		super(configuration);
		this.applet = applet;
		
		// remove the grow box by default
		// users who want it back can call frame.setResizable(true)
		setResizable(false);

	}
	
	public void startFrame() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				applet.stop();
				System.exit(0);
			}
		});
		
		// all set for rockin
		if (applet.displayable()) {
			setVisible(true);
		}
	}
}

