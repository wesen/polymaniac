package bl0rg.vj.reflection;

import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;

public class MidiAppMapping {
		public String method;
		public int channel;
		
		MidiAppMapping(String method, int channel) {
			this.method = method;
			this.channel = channel;
		}
		
		public void midiRegisterMapping(MidiApp app, MidiHandler midiHandler) {
			
		}
		
		public String toString() {
			return "mapping for \"" + method + "\" on channel " + channel;
		}
	}
	