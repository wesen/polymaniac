package bl0rg.vj.polymaniac;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

import bl0rg.vj.ContainerApp;
import bl0rg.vj.MidiApp;
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
	PolymaniacFrame mainFrame = null;
	
	int displayWidth = 320;
	int displayHeight = 240;
	
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
		super.stop();
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
			if (app.isVisible()) {
				if (!app.isBuffered()) {
					app.g.defaults();
					app.g.resetMatrix();
				}
				g.resetMatrix();
				app.handleDisplay();
			}
		}
		for (int i = 0; i < foregroundApps.size(); i++) {
			MidiApp app = (MidiApp)foregroundApps.get(i);
			if (app.isVisible()) {
				if (!app.isBuffered()) {
						app.g.defaults();
						app.g.resetMatrix();
				}
				g.resetMatrix();
				app.handleDisplay();
			}
		}	
	}


	public void createMidiHandler() {
		midiHandler = new MidiHandler(this, midiDeviceName);
		midiHandler.openMidi();
	}
	
	public void setResolution(int width, int height) {
		this.displayWidth = width;
		this.displayHeight = height;
	}
	
	public void setup() 
	{
		  size(displayWidth, displayHeight, JAVA2D);
	//	  smooth();
		  framerate(30);

		  createMidiHandler();
		  
//		  try {
			  ContainerApp container = new ContainerApp(this, midiHandler,
					  new MidiApp[] { //new BaumApp(this, midiHandler),
					                            //new LineApp(this, midiHandler)
					  new AmoebaApp(this, midiHandler)
			  },
					   new MidiApp[] { //new CirclesApp(this, midiHandler),
					                            new EyeApp(this, midiHandler) });
			  addBackgroundApp(container);
					  
//			  	MidiAppReflection baumReflection;
//				baumReflection = new MidiAppReflection("BaumApp");
//				 addForegroundApp(new PixelRobotApp(this, midiHandler));
			//	addForegroundApp(new LineApp(this, midiHandler));
			//	addBackgroundApp(new EyeApp(this, midiHandler));
//				 MidiApp baumApp = new BaumApp(this, midiHandler, baumReflection.getDefaultMappings());
				//	addForegroundApp(baumApp);
//				addBackgroundApp(new CirclesApp(this, midiHandler));
				//MidiApp baumApp = baumReflection.getClassInstance(this, midiHandler, baumReflection.getDefaultMappings());
				//addForegroundApp(new BaumApp(baumApp, midiHandler, baumReflection.getDefaultMappings()));
				// midiHandler.registerMidiAppNote(baumApp, "addNode", "removeNode", MidiHandler.MIDI_SYNTH_CHANNEL);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//				System.out.println("Could not find baumapp class");
//			}
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
	static public final String ARGS_EXTERNAL = "--external";
	static public final String ARGS_RESOLUTION = "--resolution";
	static public final String ARGS_SHOWDEVICES = "--listdevices";
	
	public static GraphicsDevice findGraphicsDevice(String displayName) {
		int deviceIndex = -1;
		try {
			deviceIndex = Integer.parseInt(displayName);
		} catch (NumberFormatException e) {
			// do nothing
		}
		
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice devices[] = environment.getScreenDevices();
        if ((deviceIndex >= 0) && (deviceIndex < devices.length)) {
        	return devices[deviceIndex];
        } else {
        	for (int i = 0; i < devices.length; i++) {
//        		System.out.println("comparing " + devices[i].getIDstring() + " against \\" + displayName);
        		if (devices[i].getIDstring().equals("\\" + displayName)) {
        			return devices[i];
        		}
        	}
        }
        System.err.println("Display " + displayName + " does not exist, using the default display instead.");
        return null;
	}
	
	public static void printGraphicsDevices() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice devices[] = environment.getScreenDevices();
    	for (int i = 0; i < devices.length; i++) {
    		System.out.println(i + ". " + devices[i]);
    	}
	}

	public static void main(String args[]) {
		printDisplayInformation();
		
		boolean present = false;
	
		String externalDisplayName = "";
		String midiDeviceName = "MIDI Yoke NT:  3";
		String appClassName = "bl0rg.vj.polymaniac.Polymaniac";
		// parse parameters
		int argIndex = 0;
		String param = null, value = null;
		int width = 320;
		int height = 240;
		
		GraphicsDevice displayDevice = null;
		GraphicsDevice externalDevice = null;
		while (argIndex < args.length) {
			int equals = args[argIndex].indexOf('=');
			if (equals != -1) {
				param = args[argIndex].substring(0, equals);
				value = args[argIndex].substring(equals + 1);
			} else {
				param = args[argIndex];
			}
			
			if (param.equals(ARGS_SHOWDEVICES)) {
				printGraphicsDevices();
				return;
			} else if (param.equals(ARGS_STANDALONE)) {
				appClassName = "bl0rg.vj.polymaniac.PolymaniacStandalone";
			} else if (param.equals(ARGS_CLASS)) {
				appClassName= value;
			} else if (param.equals(ARGS_EXTERNAL)) {
				externalDevice = findGraphicsDevice(value);
			} else if (param.equals(ARGS_DISPLAY)) {
				displayDevice = findGraphicsDevice(value);
			} else if (param.equals(ARGS_MIDI)) {
				midiDeviceName = value;
			}else if (param.equals(ARGS_PRESENT)) {
	            present = true;
			} else if (param.equals(ARGS_RESOLUTION)) {
				String []res = value.split("x");
				if (res.length != 2) {
					System.err.println("Could not parse resolution: " + value);
				} else {
					width = Integer.parseInt(res[0]);
					height = Integer.parseInt(res[1]);
				}
			}

			argIndex++;
		}
		
		if (displayDevice == null) {
			System.out.println("using default display device");
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
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
			String [] methods = midiApp.getEventMethodNames();
			System.out.println("event methods for " + midiApp.getName());
			for (int j = 0; j < methods.length; j++) {
				System.out.println("\t" + methods[j]);
			}
			methods = midiApp.getParamMethodNames();
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
		
		app.setResolution(width, height);
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
		
		PolymaniacFrame mainFrame;
	    if (present) {
	    	mainFrame = new PolymaniacPresentFrame(app, displayDevice.getDefaultConfiguration());
	    } else {
	    	mainFrame = new PolymaniacMainFrame(app, displayDevice.getDefaultConfiguration());
	    }
	    app.mainFrame = mainFrame;
		mainFrame.startFrame();
		
		if (externalDevice != null) {
			// switch configuration on external device if possible XXX
			PolymaniacExternalFrame externalFrame = new PolymaniacExternalFrame(app, externalDevice.getDefaultConfiguration());
			externalFrame.startFrame();
		}
	}
}
