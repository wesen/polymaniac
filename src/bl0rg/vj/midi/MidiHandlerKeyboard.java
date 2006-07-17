package bl0rg.vj.midi;

import processing.core.PApplet;

public class MidiHandlerKeyboard extends MidiHandler {
	public void openMidi() {
		// do nothing
	}
	
	public void close() {
	}

	public MidiHandlerKeyboard(PApplet parent) {
		super(parent);
	}
	
	public MidiHandlerKeyboard(PApplet parent, String midiDeviceName) {
		super(parent, midiDeviceName);
	}
}
