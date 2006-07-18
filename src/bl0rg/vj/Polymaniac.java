 package bl0rg.vj;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

import bl0rg.vj.apps.*;
import bl0rg.vj.midi.*;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppReflection;
import processing.core.PApplet;
import promidi.*;
import traer.animation.Smoother3D;
import traer.physics.Particle;
import traer.physics.ParticleSystem;
import traer.physics.Spring;

public class Polymaniac extends PApplet { //extends MidiApp {
	// Image buffer;
	MidiHandler midiHandler; 

	ArrayList backgroundApps = new ArrayList();
	ArrayList foregroundApps = new ArrayList();
	String midiDeviceName = "MIDI Yoke NT:  3";
	Component mirrorComponent;
	PolymaniacFrame mainFrame = null;
	
	public void clearApps(ArrayList appList) {
		for (Iterator i = appList.iterator(); i.hasNext();) {
			MidiApp app = (MidiApp)i.next();
			midiHandler.removeMidiApp(app);
		}
		appList.clear();
	}
	
	public void clearBackgroundApps() {
		clearApps(backgroundApps);
	}
	
	public void stop() {
		if (midiHandler != null)
			midiHandler.close();
	}
	
	public void clearForegroundApps() {
		clearApps(foregroundApps);
	}
	
	public void addBackgroundApp(MidiApp app) {
		backgroundApps.add(app);
	}
	
	public void addForegroundApp(MidiApp app) {
		foregroundApps.add(app);
	}
	
	public void setMirrorComponent(Component component) {
		mirrorComponent = component;
	}

	//	 press "s" to unload midi
	public void keyReleased() {
		if (key == 'f') {
			if (mainFrame != null) {
				if (mainFrame.getClass().getName().equals("bl0rg.vj.PolymaniacMainFrame")) {
					switchToPresentMode();
				} else if (mainFrame.getClass().getName().equals("bl0rg.vj.PolymaniacPresentFrame")) {
					switchToMainMode();
				}
			}
		}
		/* Workaround not needed anymore
		 if (key == 's') {
			println("closing midi inputs");
			midiHandler.close();
		}
		*/
	}
	
	public void switchToPresentMode() {
		PolymaniacFrame frame = new PolymaniacPresentFrame(this, mainFrame.getGraphicsConfiguration());
		mainFrame.setEnabled(false);
		mainFrame.setVisible(false);
		frame.startFrame();
		mainFrame = frame;
	}
	
	public void switchToMainMode() {
		PolymaniacFrame frame = new PolymaniacMainFrame(this, mainFrame.getGraphicsConfiguration());
		mainFrame.setEnabled(false);
		mainFrame.setVisible(false);
		frame.startFrame();
		mainFrame = frame;
	}
	
	synchronized public void handleDisplay() {
		super.handleDisplay();

			for (int i = 0; i < backgroundApps.size(); i++) {
			MidiApp app = (MidiApp)backgroundApps.get(i);
			app.handleDisplay();
		}
		for (int i = 0; i < foregroundApps.size(); i++) {
			MidiApp app = (MidiApp)foregroundApps.get(i);
			app.handleDisplay();
		}	
	}


	public void createMidiHandler() {
		midiHandler = new MidiHandler(this, midiDeviceName);
		midiHandler.openMidi();
	}
	
	public void setup() 
	{
		  size(320, 240, JAVA2D);
	//	  smooth();
		  framerate(30);

		  createMidiHandler();
		  
		  try {
			  	MidiAppReflection baumReflection;
				baumReflection = new MidiAppReflection("BaumApp");
				 addForegroundApp(new PixelRobotApp(this, midiHandler));
				addForegroundApp(new LineApp(this, midiHandler));
				//addBackgroundApp(new EyeApp(this, midiHandler));
				 MidiApp baumApp = new BaumApp(this, midiHandler, baumReflection.getDefaultMappings());
					addForegroundApp(baumApp);
				addBackgroundApp(new CirclesApp(this, midiHandler));
				//MidiApp baumApp = baumReflection.getClassInstance(this, midiHandler, baumReflection.getDefaultMappings());
				//addForegroundApp(new BaumApp(baumApp, midiHandler, baumReflection.getDefaultMappings()));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Could not find baumapp class");
			}
	}

	public void noteOn(Note note, int deviceNumber, int channel) {
	  midiHandler.eventOn(note, channel);
	}

	public void noteOn(Controller cc, int deviceNumber, int channel) {
	  midiHandler.eventOn(cc, channel);
	}

	public void draw() {
		 background(0);
		// fill(0, 255, 0);
		// rect(0, 0, 100, 100);
	//	ellipse(50, 50, 30, 30);
	}
		
	public static void printDisplayInformation() {
        GraphicsEnvironment environment =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
          GraphicsDevice devices[] = environment.getScreenDevices();
          
          for (int i = 0; i < devices.length; i++) {
        	  System.out.println(i + ". " + devices[i].getIDstring() + ": " + devices[i].getDisplayMode().getWidth() + "x" + devices[i].getDisplayMode().getHeight());
          }
          //return;      
	}
	
	static public final String ARGS_STANDALONE = "--standalone";
	static public final String ARGS_CLASS = "--class";
	static public final String ARGS_MIDI = "--midi";
	
	public static void main(String args[]) {
		printDisplayInformation();
		
		boolean present = false;
		
		String midiDeviceName = "MIDI Yoke NT:  3";
		String appClassName = "bl0rg.vj.Polymaniac";
		// parse parameters
		int argIndex = 0;
		String param = null, value = null;
		
		GraphicsDevice displayDevice = null;
		while (argIndex < args.length) {
			int equals = args[argIndex].indexOf('=');
			if (equals != -1) {
				param = args[argIndex].substring(0, equals);
				value = args[argIndex].substring(equals + 1);
			} else {
				param = args[argIndex];
			}
			
			if (param.equals(ARGS_STANDALONE)) {
				appClassName = "bl0rg.vj.PolymaniacStandalone";
			} else if (param.equals(ARGS_CLASS)) {
				appClassName= value;
			} else if (param.equals(ARGS_DISPLAY)) {
				int deviceIndex = Integer.parseInt(value) - 1;
				
				GraphicsEnvironment environment =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice devices[] = environment.getScreenDevices();
				if ((deviceIndex >= 0) && (deviceIndex < devices.length)) {
					displayDevice = devices[deviceIndex];
				} else {
					System.err.println("Display " + value + " does not exist, " + "using the default display instead.");
				}
			} else if (param.equals(ARGS_MIDI)) {
				midiDeviceName = value;
			}else if (param.equals(ARGS_PRESENT)) {
	            present = true;
			}

			
			argIndex++;
		}
		
		if (displayDevice == null) {
			GraphicsEnvironment environment =
				GraphicsEnvironment.getLocalGraphicsEnvironment();
			displayDevice = environment.getDefaultScreenDevice();
		}
		
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		// midiApps = MidiAppReflection.findAllLoadedMidiApps();
		for (Iterator i = MidiAppReflection.findAllLoadedMidiApps(); i.hasNext();) {
			MidiAppReflection midiApp = (MidiAppReflection)i.next();
			MidiAppMapping mappings[] = midiApp.getDefaultMappings();
			System.out.println("mappings for " + midiApp.getName());
			for (int j = 0; j < mappings.length; j++) {
				System.out.println("\t" + mappings[j].toString());
			}
			String [] methods = midiApp.getEventMethods();
			System.out.println("event methods for " + midiApp.getName());
			for (int j = 0; j < methods.length; j++) {
				System.out.println("\t" + methods[j]);
			}
			methods = midiApp.getParamMethods();
			System.out.println("param methods for " + midiApp.getName());
			for (int j = 0; j < methods.length; j++) {
				System.out.println("\t" + methods[j]);
			}
		}
		
		Polymaniac app = null;
		try {
			Class appClass = Class.forName(appClassName);
			app = (Polymaniac)appClass.getDeclaredConstructor(new Class[] {}).newInstance(new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not instantiate app");
			System.exit(-1);
		}
		
		app.midiDeviceName = midiDeviceName;
		app.init();
		//		wait until the applet has figured out its width
		// hoping that this won't hang if the applet has an exception
		while (app.defaultSize && !app.finished) {
			try {
				Thread.sleep(5);
				
			} catch (InterruptedException e) {
			}
		}
		
		if (displayDevice == null) {
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			displayDevice = environment.getDefaultScreenDevice();
		}
		
		PolymaniacFrame mainFrame;
	    if (present) {
	    	mainFrame = new PolymaniacPresentFrame(app, displayDevice.getDefaultConfiguration());
	    } else {
	    	mainFrame = new PolymaniacMainFrame(app, displayDevice.getDefaultConfiguration());
	    }
	    app.mainFrame = mainFrame;
		mainFrame.startFrame();
		
		
		// PolymaniacExternalFrame externalFrame = new PolymaniacExternalFrame(app);
		//	 externalFrame.startFrame();
	}
}
