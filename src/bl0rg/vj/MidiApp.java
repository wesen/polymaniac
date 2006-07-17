package bl0rg.vj;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;
import bl0rg.vj.reflection.MidiAppParamMapping;
import bl0rg.vj.reflection.MidiAppParamNoteMapping;
import processing.core.PApplet;

public class MidiApp extends OffscreenApplet {
	  int drawOrder = 0;
	  MidiHandler midiHandler;

	  public static MidiAppMapping[] getDefaultMappings() {
		  return new MidiAppMapping[] {};
	  }
	  
	  public MidiApp(PApplet parent) {
		  super(parent);
	  }
	  public MidiApp(PApplet parent, MidiHandler midiHandler) {
		  this(parent);
		  midiHandler = midiHandler;
	  }
	  
	  public MidiApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping mappings[]) {
		  this(parent, midiHandler);
		  for (int i = 0; i < mappings.length; i++) {
			  System.out.println("registering mapping " + mappings[i] +  " of class " + mappings[i].getClass().getName());
			 mappings[i].midiRegisterMapping(this, midiHandler);
		  }
	  }
	  
	  public int getDrawOrder() {
	    return drawOrder;
	  }

	  public void setDrawOrder(int drawOrder) {
	    this.drawOrder = drawOrder;
	  }

	  public synchronized void startApp() {
	  }

	  public synchronized void stopApp() {
	  }

	  public synchronized void showApp() {
	  }

	  public synchronized void hideApp() {
	  }
}
