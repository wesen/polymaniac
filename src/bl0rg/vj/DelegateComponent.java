package bl0rg.vj;

import processing.core.*;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DelegateComponent extends Canvas implements Runnable {
	PApplet realOne;
	public Thread t;
	int x = 0, y = 0;
	
	public DelegateComponent(PApplet realOne) {
		super();
		this.realOne = realOne;
		// setSize(dim.width, dim.height);
		// setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
//		t = new Thread(this);
//		t.start();
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	synchronized public void paint(Graphics g) {
	//	System.out.println("delegate paint");
		//synchronized(realOne) {
		// hack hack
			if ((g != null) && (realOne.g != null) && (realOne.g.image != null)) {
				//realOne.paint(g);
				g.drawImage(realOne.g.image, x, y, null);
			//}
		}
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public static final int FRAMERATE = 30;
	public void run() {
		while (true) {
			// framerate throttling
			try {
				Thread.sleep(1000 / FRAMERATE);
				// System.out.println("throttle");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// paint(getGraphics());
			repaint();
			getToolkit().sync();
		}
	}
}
