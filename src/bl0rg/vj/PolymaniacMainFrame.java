package bl0rg.vj;

import java.awt.*;

import processing.core.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PolymaniacMainFrame extends PolymaniacFrame {
	PolymaniacMainFrame(PApplet applet, GraphicsConfiguration configuration ) {
		super(applet, configuration);
		
		pack(); // get insets. get more.
		Insets insets = getInsets();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		int windowW = Math.max(applet.width, PApplet.MIN_WINDOW_WIDTH) + insets.left
				+ insets.right;
		int windowH = Math.max(applet.height, PApplet.MIN_WINDOW_HEIGHT) + insets.top
				+ insets.bottom;

		setSize(windowW, windowH);
		setLocation((screen.width - applet.width) / 2, (screen.height - applet.height) / 2);
		setLayout(null);
		
		applet.frame = this;

		add(applet);

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
