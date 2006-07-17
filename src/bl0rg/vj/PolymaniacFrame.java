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
		
		Color backgroundColor = Color.black; //BLACK;
		Color stopColor = Color.gray; //GRAY;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		// remove the grow box by default
		// users who want it back can call frame.setResizable(true)
		setResizable(false);

		pack(); // get insets. get more.
		Insets insets = getInsets();

		int windowW = Math.max(applet.width, PApplet.MIN_WINDOW_WIDTH) + insets.left
				+ insets.right;
		int windowH = Math.max(applet.height, PApplet.MIN_WINDOW_HEIGHT) + insets.top
				+ insets.bottom;

		setSize(windowW, windowH);
		setLocation((screen.width - applet.width) / 2, (screen.height - applet.height) / 2);
		setLayout(null);
	
			int usableWindowH = windowH - insets.top - insets.bottom;
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

