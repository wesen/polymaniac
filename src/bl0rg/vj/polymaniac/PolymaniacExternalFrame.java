package bl0rg.vj.polymaniac;

import bl0rg.vj.*;
import processing.core.*;
import java.applet.Applet;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;

import bl0rg.vj.DelegateComponent;

public class PolymaniacExternalFrame extends PolymaniacFrame implements Runnable {
	DelegateComponent component;
	PolymaniacExternalFrame(Polymaniac applet) {
		super(applet);
		component = new DelegateComponent(applet);
		add(component);
	}
	
	public void startFrame() {
		super.startFrame();
		((Polymaniac)applet).setMirrorComponent(this);
	}
	
	public void start() {
		System.out.println("start");
	}
	
	public void run() {
		while(true) {
			System.out.println("run");
		//	realOne.paint(getGraphics());
			getToolkit().sync();
		}
	}
}
