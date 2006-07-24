//amoebaAbstract_01
//Copyright 2003 Marius Watz
//http://www.evolutionzone.com/
//
//Abstract computational animation for the exhibition "Abstraction 
//Now", Künstlerhaus Vienna, 29.08-28.09 2003.
//
//You are allowed to play with this code as much as you like, but
//you may not publish pieces based directly on it. The Vec2D class 
//can be used freely in any context.
//

package bl0rg.vj.apps;

import processing.core.PApplet;
import bl0rg.vj.MidiApp;
import bl0rg.vj.helpers.AmoebaParticle;
import bl0rg.vj.helpers.Helpers;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;
import bl0rg.vj.reflection.ParameterSetting;

public class AmoebaApp extends MidiApp {
	public AmoebaApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		this(parent, midiHandler, mappings, AmoebaApp.getDefaultSettings());
	}
	
	public AmoebaApp(PApplet parent, MidiHandler midiHandler) {
		this(parent, midiHandler, AmoebaApp.getDefaultMappings(), AmoebaApp.getDefaultSettings());
	}
	
	public AmoebaApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings, ParameterSetting[] settings) {
		super(parent, midiHandler, mappings, settings);
	}
	
	public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] { 
				new MidiAppNoteMapping("eventNewParticle", "eventStopParticles", MidiHandler.MIDI_EVENT_CHANNEL, -1),
				new MidiAppNoteMapping("eventClearScreen", "", MidiHandler.MIDI_DRUM_CHANNEL, 62),
		};
	}
	
	public static ParameterSetting[] getDefaultSettings() {
		return new ParameterSetting[] {
				new ParameterSetting("Visible", new Boolean(true))
		};
	}
	
	int num,cnt,px,py;
	AmoebaParticle[] particles;
	boolean initialised=false,clearScreen=false;
	float lastRelease=-1;
	
	public synchronized void setup() {

		setBuffered(true);
	//	size(700,400);
	//	background(255);
		smooth();
		rectMode(CENTER);
		ellipseMode(CENTER);
		
		cnt=0;
		num=50;
		particles=new AmoebaParticle[num];
		for(int i=0; i<num; i++) particles[i]=new AmoebaParticle(this);
		
		px=-1;
		py=-1;
	}
	
	public synchronized void draw() {
		if(clearScreen) {
			  background(0, 0, 0 , 0);
			clearScreen=false;
		}
		
		
		noStroke();
		/*
		 if(!mousePressed) cnt++;
		 if(cnt%40==0 && !mousePressed) {
		 fill(255,255,255,20);
		 rect(width/2,height/2,700,400);
		 }
		 */
		
//		if(mousePressed) {
//			int partNum,i;
//			float dir;
//			
//			if(px==-1) {
//				px=mouseX;
//				py=mouseY;
//				dir=0;
//			}
//			else if(px==mouseX && py==mouseY) dir=(int)(random(36))*10;
//			else dir=degrees(atan2(mouseY-py,mouseX-px))-90;
//			
//			i=0;
//			while(i<num) {
//				if(particles[i].age<1) {
//					particles[i].init(dir);
//					break;
//				}
//				i++;
//			}
//			
//			px=mouseX;
//			py=mouseY;
//		}
//		
		for(int i=0; i<num; i++) 
			if(particles[i].age>0) particles[i].update();
	}
	
	public void eventNewParticle() {
		int [] pos = Helpers.getRandomPosition(this, new int[] {20, 20});
		// System.out.println("Creating new particles at " + pos[0] + ", " + pos[1]);
		for (int i = 0; i < num; i++) {
			// float dir = degrees(atan2(pos[1] - py, pos[0] - px)) - 90;
			float dir = (int)(random(36)) * 10;
				if (particles[i].age < 1) {
				particles[i].init(pos[0], pos[1], dir);
			}
		}
		px = pos[0];
		py = pos[1];
	}
	
	public void eventStopParticles() {
		for (int i = 0; i < num; i++) 
			particles[i].age = 0;
	}
	
	public void eventClearScreen() {
		clearScreen = true;
	}
//	
//	public void mousePressed() {
//		if(!initialised) {
//			clearScreen=true;
//			initialised=true;
//		}
//		float time=millis();
//		if(lastRelease!=-1 && (time-lastRelease)<200) {
//			background(255);
//			for(int i=0; i<num; i++) particles[i].age=0;
//			lastRelease=-1;
//		}
//		else lastRelease=time;
//	}
	
}