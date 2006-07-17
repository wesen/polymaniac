package bl0rg.vj.reflection;

import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;

public class MidiAppNoteMapping extends MidiAppMapping {
	public String methodOff;
	public int notePitch;
	
	public MidiAppNoteMapping(String methodOn, String methodOff, int channel, int notePitch) {
		super(methodOn, channel);
		this.methodOff = methodOff;
		this.notePitch = notePitch;
	}
	
	public void midiRegisterMapping(MidiApp app, MidiHandler midiHandler) {
		  midiHandler.registerMidiAppNote(app, method, methodOff, channel, notePitch );		
	}
	
	public String toString() {
		return "note mapping for \"" + method + "\", \"" + methodOff + "\" on channel " + channel + " on note " + notePitch;
	}
}
