package bl0rg.vj.apps;

import processing.core.PApplet;
import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;

public class GeruestApp extends MidiApp {
	public GeruestApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		super(parent, midiHandler, mappings);
	}
	
   public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] { 
				new MidiAppNoteMapping("blabla", "", MidiHandler.MIDI_DRUM_CHANNEL, -1),
				new MidiAppNoteMapping("blabla", "", MidiHandler.MIDI_BASS_CHANNEL, -1) };
   }

	public synchronized void draw() {
	}

}
