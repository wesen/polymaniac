package bl0rg.vj.reflection;

import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;

public class MidiAppParamNoteMapping extends MidiAppMapping {
	public int notePitch;
	
	public MidiAppParamNoteMapping(String method, int channel, int notePitch) {
		super(method, channel);
		this.notePitch = notePitch;
	}
	public void midiRegisterMapping(MidiApp app, MidiHandler midiHandler) {
		  midiHandler.registerMidiAppNoteParam(app, method, channel, notePitch);

	}
	public String toString() {
		return "param note mapping for \"" + method + "\" on channel " + channel + " on note " + notePitch;
	}
}
