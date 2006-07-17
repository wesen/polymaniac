package bl0rg.vj.midi;

import java.util.List;
import promidi.*;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.*;

public class MidiHandler {
	String midiDeviceName;

	MidiIO midiIo;

	processing.core.PApplet parent;

	// MidiApp containers for Bass, Drums, Events, Synths
	public static final int MIDI_DRUM_CHANNEL = 1;

	public static final int MIDI_BASS_CHANNEL = 0;

	public static final int MIDI_EVENT_CHANNEL = 2;

	public static final int MIDI_SYNTH_CHANNEL = 3;

	private List noteEventList[];

	private List ccEventList[];

	private List noteParamEventList[];

	/*
	 MidiHandler() {
	 }
	 */

	public MidiHandler(processing.core.PApplet parent) {
		this(parent, "");
	}
	
	public MidiHandler(processing.core.PApplet parent, String midiDeviceName) {
		this.parent = parent;
		this.midiDeviceName = midiDeviceName;
		noteEventList = new ArrayList[4];
		ccEventList = new ArrayList[4];
		noteParamEventList = new ArrayList[4];
		for (int i = 0; i < 4; i++) {
			noteEventList[i] = new ArrayList();
			ccEventList[i] = new ArrayList();
			noteParamEventList[i] = new ArrayList();
		}



	}
	
	public void openMidi() {
		boolean midiInputOpened = false;

		midiIo = MidiIO.getInstance(parent);

		//	    midiIo.printInputDevices();
		for (int i = 0; i < midiIo.numberOfInputDevices(); i++) {
			String name = midiIo.getInputDeviceName(i);
			if (name.equals(midiDeviceName)) {
				System.out.println("opening device " + name);
				midiIo.openInput(i, MIDI_EVENT_CHANNEL);
				midiIo.openInput(i, MIDI_DRUM_CHANNEL);
				midiIo.openInput(i, MIDI_BASS_CHANNEL);
				midiIo.openInput(i, MIDI_SYNTH_CHANNEL);
				//	        midiIo.plug(this, "eventOnBass", i, 0);
				//	        midiIo.plug(this, "eventOnDrums", i, 1);

				midiInputOpened = true;
			}
		}

		if (!midiInputOpened) {
			System.out.println("No midi input device opened");
		}
	}

	public void close() {
		System.out.println("Closing midi inputs");
		midiIo.closeInputs();
	}

	// register an event for all notes on the channel
	public void registerMidiAppNote(Object app, String methodOn, String methodOff,
			int channel) {
		registerMidiAppNote(app, methodOn, methodOff, channel, -1);
	}

	// register an event for a specific note on the channel
	public void registerMidiAppNote(Object app, String methodOn, String methodOff,
			int channel, int notePitch) {
		if ((channel >= noteEventList.length) || (channel < 0))
			return;
		try {
			NoteCallback callback = new NoteCallback(app, methodOn, methodOff,
					notePitch);
			System.out.println("registering callback : " + callback);
			noteEventList[channel].add(callback);
		} catch (Exception e) {
			System.out.println("Could not create callback for methods " + methodOn
					+ " and " + methodOff);
			e.printStackTrace();
		}
	}

	// register a CC parameter callback
	public void registerMidiAppCCParam(Object app, String method, int channel) {
		registerMidiAppCCParam(app, method, channel, -1);
	}

	// register a CC parameter callback
	public void registerMidiAppCCParam(Object app, String method, int channel,
			int ccNumber) {
		if ((channel >= ccEventList.length) || (channel < 0))
			return;

		try {
			CcParamCallback callback = new CcParamCallback(app, method,
					ccNumber);
			ccEventList[channel].add(callback);
		} catch (Exception e) {
			System.out.println("Could not create cc callback for method " + method);
			e.printStackTrace();
		}
	}

	// register a noteOn parameter callback for all notes on the channels
	public void registerMidiAppNoteParam(Object app, String method, int channel) {
		registerMidiAppNoteParam(app, method, channel, -1);
	}

	// reigster a noteOn parameter callback for a specific note on the channel
	public void registerMidiAppNoteParam(Object app, String method, int channel,
			int notePitch) {
		if ((channel >= noteParamEventList.length) || (channel < 0))
			return;

		try {
			NoteParamCallback callback = new NoteParamCallback(app, method,
					notePitch);
			noteParamEventList[channel].add(callback);
		} catch (Exception e) {
			System.out.println("Could not create note param callback for method " + method);
			e.printStackTrace();
		}
	}

	public void removeMidiApp(Object app, List list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			MidiCallback callback = (MidiCallback) list.get(i);
			if (callback.getApp() == app) {
				list.remove(i);
			}
		}
	}

	// remove the application from all callback lists
	public void removeMidiApp(Object app) {
		for (int i = 0; i < noteEventList.length; i++) {
			removeMidiApp(app, noteEventList[i]);
		}
		for (int i = 0; i < ccEventList.length; i++) {
			removeMidiApp(app, ccEventList[i]);
		}
		for (int i = 0; i < noteParamEventList.length; i++) {
			removeMidiApp(app, noteParamEventList[i]);
		}
	}

	/* maybe handle in PApplet without plug, forward and handle */

	public void eventOn(Note note, int channel) {
		//	    println("note event on channel " + channel);
		if ((channel < noteEventList.length) && (channel >= 0)) {
			for (int i = 0; i < noteEventList[channel].size(); i++) {
				NoteCallback callback = (NoteCallback) noteEventList[channel].get(i);
				callback.eventOn(note);
 //  System.out.println("found note param callback " + callback);
			}
		}
		if ((channel < noteParamEventList.length) && (channel >= 0)) {
			for (int i = 0; i < noteParamEventList[channel].size(); i++) {
				NoteCallback callback = (NoteCallback) noteParamEventList[channel].get(i);
	//			      System.out.println("found note param callback " + callback);
				callback.eventOn(note);
			}
		}
	}

	public void eventOn(Controller cc, int channel) {
		//	    println("cc event on channel " + channel);
		if ((channel < ccEventList.length) && (channel >= 0)) {
			for (int i = 0; i < ccEventList[channel].size(); i++) {
				MidiCallback callback = (MidiCallback) ccEventList[channel]
						.get(i);
				callback.eventOn(cc);
			}
		}
	}

}
