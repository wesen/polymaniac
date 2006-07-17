package bl0rg.vj.apps;

import processing.core.PApplet;
import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;

public class EyeApp extends MidiApp {
	class Eye {
		int ex, ey;
		int size;
		float angle = 0.0f;
		
		Eye(int x, int y, int s) {
			ex = x;
			ey = y;
			size = s;
		}
		
		void update(int mx, int my) {
			angle = atan2(my-ey, mx-ex);
		}
		
		void display() {
			pushMatrix();
			translate(ex, ey);
			fill(255);
			ellipse(0, 0, size, size);
			rotate(angle);
			fill(153);
			ellipse(size/4, 0, size/2, size/2);
			popMatrix();
		}
	}

	public EyeApp(PApplet parent, MidiHandler midiHandler) {
		this(parent, midiHandler, EyeApp.getDefaultMappings());
	}
	public EyeApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		super(parent, midiHandler, mappings);
	}
	
   public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] { 
				new MidiAppNoteMapping("moveTop", "moveBottom", MidiHandler.MIDI_DRUM_CHANNEL, 60) };
		}

	public synchronized void draw() {
		   noStroke();
		   e1.display();
		  e2.display();
		  e3.display();
		  e4.display();
		  e5.display();
	}
	
	public synchronized void moveTop() {
		  e1.update(width, height);
		  e2.update(width, height);
		  e3.update(width, height);
		  e4.update(width, height);
		  e5.update(width, height);
	}
	
	public synchronized void moveBottom() {
		  e1.update(0, 0);
		  e2.update(0, 0);
		  e3.update(0, 0);
		  e4.update(0, 0);
		  e5.update(0, 0);
				
	}

		Eye e1, e2, e3, e4, e5;
		
   public void setup() {
	   // size(pare, 200);
//	   smooth();
	   e1 = new Eye( 50,  16,  80);
	   e2 = new Eye( 64,  85,  40);  
	   e3 = new Eye( 90, 200, 120);
	   e4 = new Eye(150,  44,  40); 
	   e5 = new Eye(175, 120,  80);
   }
}
