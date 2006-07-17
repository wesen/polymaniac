package bl0rg.vj.reflection;

import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;

public class MidiAppParamMapping extends MidiAppMapping {
	public int ccNumber;
	
	public MidiAppParamMapping(String method, int channel, int ccNumber) {
		super(method, channel);
		this.ccNumber = ccNumber;
	}
	public void midiRegisterMapping(MidiApp app, MidiHandler midiHandler) {
		  midiHandler.registerMidiAppCCParam(app, method, channel, ccNumber);
	}
	
	public String toString() {
		return "param mapping for \"" + method + "\"on channel " + channel + " on cc " + ccNumber;
	}
}
