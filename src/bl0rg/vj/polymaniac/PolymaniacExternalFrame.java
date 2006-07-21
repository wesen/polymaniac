package bl0rg.vj.polymaniac;

import bl0rg.vj.*;
import processing.core.*;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import bl0rg.vj.DelegateComponent;

public class PolymaniacExternalFrame extends PolymaniacFrame  {
	DelegateComponent component;
	Label label;
	
	PolymaniacExternalFrame(Polymaniac applet, GraphicsConfiguration configuration) {
		super(applet, configuration);
		
		Color backgroundColor = Color.black; //BLACK;
		Color stopColor = Color.gray; //GRAY;

		GraphicsDevice displayDevice = configuration.getDevice();
		
		setUndecorated(true);
        setBackground(backgroundColor);
        displayDevice.setFullScreenWindow(this);

		component = new DelegateComponent(applet);
		add(component);
		// setSize(applet.width, applet.height);
        // add(applet);
        Dimension fullscreen = getSize();
        component.setXY((fullscreen.width - applet.width) / 2,
                (fullscreen.height - applet.height) / 2);
        component.setSize(applet.width, applet.height);
        Thread t = new Thread(component);
        component.t = t;
        t.start();
        // component.setLocation(100, 100);

        
        /* No Stop button  why size is all when bla?? */
//
//        label = new Label("");
//        label.setForeground(stopColor);
//        add(label);
//
//        Dimension labelSize = label.getPreferredSize();
//        // sometimes shows up truncated on mac
//        System.out.println("label width is " + labelSize.width);
//        labelSize = new Dimension(100, labelSize.height);
//        label.setSize(labelSize);
//        label.setLocation(20, fullscreen.height - labelSize.height - 20);
	}
		
//	public void start() {
//		System.out.println("start");
//	}
//	
//	public void run() {
//		while(true) {
//			System.out.println("run");
//		//	realOne.paint(getGraphics());
//			getToolkit().sync();
//		}
//	}
}
