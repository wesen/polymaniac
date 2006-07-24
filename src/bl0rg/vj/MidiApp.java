package bl0rg.vj;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;
import bl0rg.vj.reflection.MidiAppParamMapping;
import bl0rg.vj.reflection.MidiAppParamNoteMapping;
import bl0rg.vj.reflection.ParameterSetting;
import processing.core.PApplet;
import promidi.Controller;
import promidi.Note;

public class MidiApp extends OffscreenApplet {
	  int drawOrder = 0;
	  MidiHandler midiHandler;
	  protected boolean visible = true;

	  public static MidiAppMapping[] getDefaultMappings() {
		  return new MidiAppMapping[] {};
	  }
	  
	  public static ParameterSetting[] getDefaultSettings() {
		  return new ParameterSetting[] {};
	  }
	 
	  public MidiApp(PApplet parent) {
		  this(parent, null, new MidiAppMapping [] {}, new ParameterSetting[] {});
	  }
	  public MidiApp(PApplet parent, MidiHandler midiHandler) {
		  this(parent, midiHandler, new MidiAppMapping [] {}, new ParameterSetting[] {});
	  }
	  
	  public MidiApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping mappings[]) {
		  this(parent, midiHandler, mappings, new ParameterSetting[] {});

	  }
	  
	  public MidiApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping mappings[],
			  ParameterSetting settings[]) {
		  super(parent);
		  this.midiHandler = midiHandler;
		  if (mappings != null) {
			  for (int i = 0; i < mappings.length; i++) {
				  System.out.println("registering mapping " + mappings[i] +  " of class " + mappings[i].getClass().getName());
				  mappings[i].midiRegisterMapping(this, midiHandler);
			  }
		  }
		  if (settings != null) {
			  for (int i = 0; i < settings.length; i++) {
				  System.out.println("setting parameter " + settings[i].getName() +  " to value " + settings[i].getValue());
				  settings[i].setParameter(this);
			  }
		  }
	  }
	  
	  public int getDrawOrder() {
	    return drawOrder;
	  }

	  public void setDrawOrder(int drawOrder) {
	    this.drawOrder = drawOrder;
	  }

	  public synchronized void setVisible(boolean visible) {
		  this.visible = visible;
	  }
	  
	  public synchronized boolean isVisible() {
		  return visible;
	  }
	  
	  // visible parameter
		public void setParamVisible(Note note) {
			setVisible(note.getVelocity() > 0);
		}
		
		public Boolean getParamVisible() {
			return new Boolean(visible);
		}
		
		public void setParamVisible(Boolean visible) {
			this.visible = visible.booleanValue();
		}
		
		public void setParamNodeColor(boolean visible) {
			this.visible = visible;
		}
		
}
