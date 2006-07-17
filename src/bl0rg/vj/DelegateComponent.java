package bl0rg.vj;

import processing.core.*;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DelegateComponent extends Component implements Runnable {
	Component realOne;
	Thread t;
	
	DelegateComponent(Component realOne) {
		super();
		this.realOne = realOne;
		Rectangle bounds = realOne.getBounds();
		Dimension dim = realOne.getSize();
		setSize(dim.width, dim.height);
		setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		t = new Thread(this);
		t.start();
	}
	
	public void paint(Graphics g) {
		realOne.paint(g);
	}
	
	public void update(Graphics g) {
		realOne.update(g);
	}
	
	public static final int FRAMERATE = 30;
	public void run() {
		while (true) {
			// framerate throttling
			try {
				Thread.sleep(1000 / FRAMERATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paint(getGraphics());
			getToolkit().sync();
		}
	}
}
