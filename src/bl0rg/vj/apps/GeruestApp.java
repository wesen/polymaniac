package bl0rg.vj.apps;

import processing.core.PApplet;
import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;
import bl0rg.vj.reflection.ParameterSetting;

public class GeruestApp extends MidiApp {
	public GeruestApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		this(parent, midiHandler, mappings, GeruestApp.getDefaultSettings());
	}
	
	public GeruestApp(PApplet parent, MidiHandler midiHandler) {
		this(parent, midiHandler, GeruestApp.getDefaultMappings(), GeruestApp.getDefaultSettings());
	}
	
	public GeruestApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings, ParameterSetting[] settings) {
		super(parent, midiHandler, mappings, settings);
	}
	
   public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] { 
				new MidiAppNoteMapping("blabla", "", MidiHandler.MIDI_DRUM_CHANNEL, -1),
				new MidiAppNoteMapping("blabla", "", MidiHandler.MIDI_BASS_CHANNEL, -1) };
   }
   
   public static ParameterSetting[] getDefaultSettings() {
	   return new ParameterSetting[] {
			   new ParameterSetting("visible", new Boolean(true))
	   };
   }

	public synchronized void draw() {
	}

	public synchronized void setup() {
		
	}
}
