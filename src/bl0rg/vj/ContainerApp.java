package bl0rg.vj;

import java.util.ArrayList;

import processing.core.PApplet;
import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;

public class ContainerApp extends MidiApp {
	ArrayList fgApps, bgApps;
	
	public ContainerApp(PApplet parent, MidiHandler midiHandler, MidiApp fgApps[], 
			MidiApp bgApps[], MidiAppMapping[] mappings) {
		super(parent, midiHandler, mappings);
		this.fgApps = new ArrayList();
		for (int i = 0; i < fgApps.length; i++)
			this.fgApps.add(fgApps[i]);
		this.bgApps = new ArrayList();
		for (int i = 0; i < bgApps.length; i++)
			this.bgApps.add(bgApps[i]);
	}
	
	public ContainerApp(PApplet parent, MidiHandler midiHandler, MidiApp fgApps[], MidiApp bgApps[]) {
		this(parent, midiHandler, fgApps, bgApps, ContainerApp.getDefaultMappings());
	}
	
   public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] { };
   }

	public synchronized void handleDisplay() {
		for (int i = 0; i < bgApps.size(); i++) {
			MidiApp app = (MidiApp)bgApps.get(i);
			if (app.isVisible())
				app.handleDisplay();
		}
		for (int i = 0; i < fgApps.size(); i++) {
			MidiApp app = (MidiApp)fgApps.get(i);
			if (app.isVisible())
				app.handleDisplay();
		}
	}

	protected MidiApp[] getApps(ArrayList appList, boolean visible) {
		ArrayList visibleApps = new ArrayList();
		for (int i = 0; i < appList.size(); i++) {
			MidiApp app = (MidiApp)appList.get(i);
			if (app.isVisible() == visible)
				visibleApps.add(app);
		}
		MidiApp result[] = new MidiApp[visibleApps.size()];
		for (int i =0; i < visibleApps.size(); i++)
			result[i] = (MidiApp)visibleApps.get(i);
		return result;
	}
	
	public synchronized MidiApp[] getVisibleFGApps() {
		return getApps(fgApps, true);
	}
	
	public synchronized MidiApp[] getHiddenFGApps() {
		return getApps(fgApps, false);
	}
	
	public synchronized MidiApp[] getVisibleBGApps() {
		return getApps(bgApps, true);
	}
	
	public synchronized MidiApp[] getHiddenBGApps() {
		return getApps(bgApps, false);
	}
		
	public synchronized void addFGApp(MidiApp fgApp) {
		fgApp.setup();
		fgApps.add(fgApp);
	}
	
	public synchronized void addBGApp(MidiApp bgApp) {
		bgApp.setup();
		bgApps.add(bgApp);
	}
	
	public synchronized void clearFGApps() {
		fgApps.clear();
	}

	public synchronized void clearBGApps() {
		bgApps.clear();
	}

	public synchronized void setup() {
		for (int i = 0; i < bgApps.size(); i++) {
			MidiApp app = (MidiApp)bgApps.get(i);
			app.setup();
		}
		for (int i = 0; i < fgApps.size(); i++) {
			MidiApp app = (MidiApp)fgApps.get(i);
			app.setup();
		}
	}
}