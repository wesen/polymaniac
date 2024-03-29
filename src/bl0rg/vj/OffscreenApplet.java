package bl0rg.vj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.DirectColorModel;

import processing.core.*;

public class OffscreenApplet extends PApplet {// extends MidiApp {
	PApplet parentApplet;
	boolean buffered;
	PGraphics oldG;

	OffscreenApplet(PApplet parentApplet) {
		this.parentApplet = parentApplet;
		init();
		this.g = parentApplet.g;
	}
	
	public void setBuffered(boolean buffered) {
		setBuffered(buffered, JAVA2D);
	}
	
	public boolean isBuffered() {
		return buffered;
	}
	
	public void setBuffered(boolean buffered, String iRenderer) {
		this.buffered = buffered;
		if (buffered) {
			try {
				g = null;
				size(parentApplet.width, parentApplet.height, iRenderer);
				g.backgroundColor = 0;
			} catch (RuntimeException e) {
				setup();
				System.out.println("runtime, call setup");
			}
		}
	}

	public void size(int iwidth, int iheight, String irenderer, String ipath) {
		if (buffered)
			super.size(iwidth, iheight, irenderer, ipath);
		else
			System.out.println("You can't use size in a MidiApp, this call has no effect");
	  }
	  

	public void init() {
		println(getClass().getName() + " init");

		finished = false;

		redraw = true;
		looping = true;
		firstMouse = true;

		sizeMethods = new RegisteredMethods();
		preMethods = new RegisteredMethods();
		drawMethods = new RegisteredMethods();
		postMethods = new RegisteredMethods();
		mouseEventMethods = new RegisteredMethods();
		keyEventMethods = new RegisteredMethods();
		disposeMethods = new RegisteredMethods();

		try {
			if (sketchPath == null) {
				sketchPath = System.getProperty("user.dir");
			}
		} catch (Exception e) {
		} // may be a security problem

	//	size(parentApplet.width, parentApplet.height);
	//	println("size " + parentApplet.width + " x " + parentApplet.height);
		// start() ??
	}

	public void start() {
		println("myApp start");
	}

	public void stop() {
		println("myApp stop");
	}

	synchronized public void redraw() {
		println("myApp redraw");
	}

	synchronized public void loop() {
		println("myApp loop");
		looping = true;
	}

	synchronized public void noLoop() {
		println("myApp noLoop");
	}

	public void paint() {
		println("myApp paint");
	}

	public void run() {
		println("myApp run");
	}

	// this is the shit
	synchronized public void handleDisplay() {
		//	    println("myApp handleDisplay");
		//    super.handleDisplay();
		if (looping || redraw) {
			g.beginFrame();
			//    println("framceount " + frameCount);
			if (frameCount == 0) {
				try {
					// println("calling setup");
					setup();
				} catch (Exception e) {
					e.printStackTrace();
					//	          throw e;
				}

				this.width = g.width;
				this.height = g.height;
				this.defaultSize = false;
			} else {
				preMethods.handle();
				pmouseX = dmouseX;
				pmouseY = dmouseY;
				draw();

				dmouseX = mouseX;
				dmouseY = mouseY;

				dequeueMouseEvents();

				dequeueKeyEvents();
				drawMethods.handle();

				redraw = false; // unset 'redraw' flag in case it was set
			}
			g.endFrame();
			frameCount++;
			postMethods.handle();
			
			if (buffered) {
				//parentApplet.g.beginFrame();
				// ((PGraphics)parentApplet.g).g2.drawImage(((PGraphics2)g).image, 0, 0,null);
				parentApplet.g.blitPGraphics(g);
				// parentApplet.image(get(), 0, 0);
				//parentApplet.g.endFrame();
			}
		}
	}

	public PImage get() {
		return g.get();
	}
}
