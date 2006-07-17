package bl0rg.vj;

import java.awt.*;

import processing.core.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PolymaniacMainFrame extends PolymaniacFrame {
    public static PolymaniacMainFrame CreateMainFrame(PApplet applet, GraphicsDevice displayDevice) {
		if (displayDevice == null) {
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			displayDevice = environment.getDefaultScreenDevice();
		}

		PolymaniacMainFrame frame = new PolymaniacMainFrame(applet, displayDevice.getDefaultConfiguration());
		applet.frame = frame;
		
		return frame;
    }
    
	PolymaniacMainFrame(PApplet applet, GraphicsConfiguration configuration ) {
		super(applet, configuration);
		add(applet);

		Insets insets = getInsets();
		int windowW = Math.max(applet.width, PApplet.MIN_WINDOW_WIDTH) + insets.left + insets.right;
		int windowH = Math.max(applet.height, PApplet.MIN_WINDOW_HEIGHT) + insets.top 	+ insets.bottom;

		int usableWindowH = windowH - insets.top - insets.bottom;
		applet.setBounds((windowW - applet.width) / 2, insets.top
				+ (usableWindowH - applet.height) / 2, applet.width,
				applet.height);
	}
	
	public void startFrame() {
		super.startFrame();
		applet.setupFrameResizeListener();
		applet.requestFocus(); // ask for keydowns
	}
}
