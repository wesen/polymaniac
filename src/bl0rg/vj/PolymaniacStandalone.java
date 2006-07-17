package bl0rg.vj;

import java.util.Iterator;

import promidi.Note;

import bl0rg.vj.midi.*;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppReflection;

public class PolymaniacStandalone extends Polymaniac {
	public void keyPressed() {
		String synthString = "1234567890";
		String drumString = "qweertyuiop";
		String bassString = "asdfghjkl";
		String eventString = "zxcvbnm";
		if (eventString.indexOf(key) >= 0) {
			sendNoteOn(MidiHandler.MIDI_EVENT_CHANNEL, eventString.indexOf(key) + 60);
		} else if (synthString.indexOf(key) >= 0) {
			sendNoteOn(MidiHandler.MIDI_SYNTH_CHANNEL, synthString.indexOf(key) + 60);
		} else if (bassString.indexOf(key) >= 0) {
			sendNoteOn(MidiHandler.MIDI_BASS_CHANNEL, bassString.indexOf(key) + 60);
		} else if (drumString.indexOf(key) >= 0) {
			sendNoteOn(MidiHandler.MIDI_DRUM_CHANNEL, drumString.indexOf(key) + 60);
		} else {
			System.out.println("unknown key");
		}
	}
	
	public void keyReleased() {
		String synthString = "1234567890";
		String drumString = "qweertyuiop";
		String bassString = "asdfghjkl";
		String eventString = "zxcvbnm";
		if (eventString.indexOf(key) >= 0) {
			sendNoteOff(MidiHandler.MIDI_EVENT_CHANNEL, eventString.indexOf(key) + 60);
		} else if (synthString.indexOf(key) >= 0) {
			sendNoteOff(MidiHandler.MIDI_SYNTH_CHANNEL, synthString.indexOf(key) + 60);
		} else if (bassString.indexOf(key) >= 0) {
			sendNoteOff(MidiHandler.MIDI_BASS_CHANNEL, bassString.indexOf(key) + 60);
		} else if (drumString.indexOf(key) >= 0) {
			sendNoteOff(MidiHandler.MIDI_DRUM_CHANNEL, drumString.indexOf(key) + 60);
		} else {
			System.out.println("unknown key");
		}
	}
	
	void sendNoteOn(int channel, int notePitch) {
		Note note = new Note(notePitch, 100, 10);
		midiHandler.eventOn(note, channel);
	}
	
	void sendNoteOff(int channel, int notePitch) {
		Note note = new Note(notePitch, 0, 10);
		midiHandler.eventOn(note, channel);
	}
	
	public void createMidiHandler() {
		midiHandler = new MidiHandlerKeyboard(this);
		midiHandler.openMidi();
	}
}
