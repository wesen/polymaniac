package bl0rg.vj.apps;

import processing.core.PApplet;
import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;

public class FoobarApp extends MidiApp {
	public FoobarApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		super(parent, midiHandler, mappings);
	}
	
	public FoobarApp(PApplet parent, MidiHandler midiHandler) {
		super(parent, midiHandler, FoobarApp.getDefaultMappings());
	}
	
   public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] { 
				new MidiAppNoteMapping("DrumHigh", "", MidiHandler.MIDI_DRUM_CHANNEL, -1),
				new MidiAppNoteMapping("BassHigh", "", MidiHandler.MIDI_BASS_CHANNEL, -1)
		};	
   }

   private int BackgroundVal = 255;
   private int DrumPitch = 0;

   public void doDrums() {
		if (DrumPitch==1 && BackgroundVal-10>0) {
			background(BackgroundVal-=10);
	} else {
			background(BackgroundVal=0);
			DrumPitch = 0;
	}
   }
   
   class BassBullet {
	   private int BassFrameCount = 0;
	   private int BassX1=0, BassY1=0, BassWidth=30, BassHeight=30;
   }
   
   BassBullet b = new BassBullet();
   
   public void doBass() {
	   if (b.BassFrameCount > 0 && b.BassX1<200 && b.BassY1<200) {
		   ellipseMode(CENTER);
		   ellipse(b.BassX1+=1, b.BassY1+=1, b.BassWidth, b.BassHeight);
		   fill(b.BassFrameCount--);
	   } else {
		   b.BassX1=0; b.BassY1=0; b.BassFrameCount=0;
	   }
   }
   
	public synchronized void draw() {
		doDrums(); doBass();
	}
	
	public void DrumHigh() {
		BackgroundVal = 255;
		DrumPitch = 1;
	}
	
	public void BassHigh() {
		b.BassFrameCount = 255;
	}
	
}
