//
//from processing.unlekker.net
//

package bl0rg.vj.apps;

import java.awt.*;
import java.awt.image.BufferedImage;

import processing.core.PApplet;
import processing.core.PGraphics2;
import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.*;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;

public class LineApp extends MidiApp {
	public LineApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		super(parent, midiHandler, mappings);
	}
	
	public LineApp(PApplet parent, MidiHandler midiHandler) {
		super(parent, midiHandler, LineApp.getDefaultMappings());
	}
	
	public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] {
				new MidiAppNoteMapping("eventResetLines", "", MidiHandler.MIDI_EVENT_CHANNEL, -1)
		};
	}
	
	Particle part[];
	int num;
	int colors[][],colNum,colBG;
	boolean doSaveFrame=false,doSetBackground=true;
	
	public synchronized void setup() {
		// size(600,400);
		// smooth();

		colors=new int[100][3];
		addColor(255,255,255);
		for(int i=0; i<5; i++) addColor(0,100+i*20,255);
		addColor(100,50,0);
		addColor(255,50,0);
		addColor(255,100,0);
		addColor(255,150,0);
		
//		colNum=0;
//		for(int i=0; i<20; i++) addColor(i*12,i*12,i*12);
		
		/*
		 colNum=0;
		 for(int i=0; i<20; i++) addColor(i*12,i*12,i*12);
		 */
		colBG=0;
		//((int)random(10000))%colNum;
		
		num=30;
		part=new Particle[num];
		for(int i=0; i<num; i++) {
			part[i]=new Particle();
			part[i].col=i%colNum;
		}
		
		// colorMode(255, 255, 255, 0);
		setBuffered(true);
	}
	
	public synchronized void draw() {
		rectMode(CENTER_RADIUS);
		ellipseMode(CENTER);
		smooth();
		
	 if (doSetBackground) {
		  background(0, 0, 0 , 0);
//			background(colors[colBG][0],
//					colors[colBG][1],
//					colors[colBG][2]);
		doSetBackground=false;
		}
	 
	  	noStroke();
		for(int i=0; i<num; i++) part[i].update();
		
		
		/*
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics myG = image.getGraphics();
		myG.setColor(new Color(255, 0, 0, 255));
		myG.fillRect(0, 0, width, height);
		myG.setColor(new Color(255, 0, 255, 0));
		myG.fillRect(0, 0, width, height);
		((PGraphics2)g).g2.drawImage(image, 0, 0, null);
	*/
		
		// colorMode(255, 255, 255, 255);
		
		
//		if(doSaveFrame) {
//			saveFrame();
//			doSaveFrame=false;
//		}
	}
	
	void addColor(int r,int g, int b) {
		colors[colNum][0]=r;
		colors[colNum][1]=g;
		colors[colNum][2]=b;
		colNum++;
	}
	
	public void eventResetLines() {
		colBG=(colBG+1)%colNum;
		colBG=0;
		doSetBackground=true;
		
		for(int i=0; i<num; i++) part[i].init();
	}
	
	
	class Particle {
		Vec2D v,vOld,dir;
		int col,age,num,cnt;
		int state,LINE=0,CIRCLE=1;
		float rad,rot,speed,px,py;
		
		Particle() {
			v=new Vec2D(0,0);
			vOld=new Vec2D(0,0);
			dir=new Vec2D(0,0);
			speed=random(2)+0.25f;
			num=300;
			init();
			cnt=(int)random(300);
		}
		
		void initState(int _state) {
			if(_state==LINE) {
				state=CIRCLE;      
				if(random(100)>50) {
					rot=3;
					age=30;
					speed=1;
				}
				else {
					rot=-2;
					age=45;
					speed=0.5f;
				}
				rot=radians(rot);
			}
			else {
				state=LINE;
				rot=0;
				speed=random(2)+0.25f;
				age=(int)random(50)+20;
				if(random(100)>90) age+=50;
			}
		}
		
		void init() {
			v.set(random(width),random(height));
			dir.set(1,0);
			dir.rotate(radians(30));//random(PI*2));
			
			do {
				col=((int)random(10000))%colNum;
			} while(col==colBG); 
			col=((int)random(10000))%(colNum-1)+1;
			
			initState(CIRCLE);    
			if(random(100)<90) rad=4;
			else rad=(int)(8+random(5));
		}
		
		void update() {
			vOld.set(v);
			dir.rotate(rot);
			v.add(dir.x*speed,dir.y*speed);
			
			age--;
			if(age==0) initState(state);
			
			if(v.x<0 || v.x>width || v.y<0 || v.y>height) {
				if(v.x<0) v.x=width;
				else if(v.x>width) v.x=0; 
				if(v.y<0) v.y=height;
				else if(v.y>height) v.y=0; 
				vOld.set(v);
			}
			noFill();
			fill(colors[col][0],colors[col][1],colors[col][2], 255);
			ellipse(v.x,v.y, rad,rad);//3,3);
//			line(vOld.x,vOld.y,v.x,v.y);//, 2,2);
			
			if(cnt%100==0) {
				noFill();
				fill(colors[col][0],colors[col][1],colors[col][2], 255);
				ellipse(v.x,v.y, rad,rad);
			}
			
			cnt++;
			if(cnt%2500<10) dir.rotate(radians(4.5f));  
//			for(int i=0; i<num-1; i++) rect(x[i],y[i], 1,1);
			/*    noFill();
			 stroke(colors[col][0],colors[col][1],colors[col][2]);
			 for(int i=0; i<(num/4-1); i++) 
			 line(x[num-1],y[num-1],x[i*4],y[i*4]);
			 */
//			line(x[i*10],y[i*10], x[(i+1)*10],y[(i+1)*10]);
		}
		
	}
	
//	Vec2D - simple 2D vector class
//	processing.unlekker.net
	
	class Vec2D {
		float x,y;
		
		Vec2D(float _x,float _y) {
			x=_x;
			y=_y;
		}
		
		Vec2D(Vec2D v) {
			x=v.x;
			y=v.y;
		}
		
		void set(float _x,float _y) {
			x=_x;
			y=_y;
		}
		
		void set(Vec2D v) {
			x=v.x;
			y=v.y;
		}
		
		void add(float _x,float _y) {
			x+=_x;
			y+=_y;
		}
		
		void add(Vec2D v) {
			x+=v.x;
			y+=v.y;
		}
		
		void sub(float _x,float _y) {
			x-=_x;
			y-=_y;
		}
		
		void sub(Vec2D v) {
			x-=v.x;
			y-=v.y;
		}
		
		void mult(float m) {
			x*=m;
			y*=m;
		}
		
		void div(float m) {
			x/=m;
			y/=m;
		}
		
		float length() {
			return sqrt(x*x+y*y);
		}
		
		float angle() {
			return atan2(y,x);
		}
		
		void normalise() {
			float l=length();
			if(l!=0) {
				x/=l;
				y/=l;
			}
		}
		
		Vec2D tangent() {
			return new Vec2D(-y,x);
		}
		
		void rotate(float val) {
			// Floats are not precise enough, so doubles are used for the calculations
			double cosval=Math.cos(val);
			double sinval=Math.sin(val);
			double tmpx=x*cosval - y*sinval;
			double tmpy=x*sinval + y*cosval;
			
			x=(float)tmpx;
			y=(float)tmpy;
		}
	}
	
}
