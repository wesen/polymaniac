//	 Intersecting Circles, Instantaneous
//	 J. Tarbell                              + complexification.net
//	 Albuquerque, New Mexico
//	 May, 2004
	//
//	 a REAS collaboration for the            + groupc.net
//	 Whitney Museum of American Art ARTPORT  + artport.whitney.org
//	 Robert Hodgin                           + flight404.com
//	 William Ngan                            + metaphorical.net

//	 Processing 0085 Beta syntax update
//	 j.tarbell   April, 2005


package bl0rg.vj.apps;

import java.awt.Color;

import processing.core.PApplet;
import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;

public class CirclesApp extends MidiApp {
	public CirclesApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		super(parent, midiHandler, mappings);
	}
	public CirclesApp(PApplet parent, MidiHandler midiHandler) {
		this(parent, midiHandler, CirclesApp.getDefaultMappings());
		dim = min(parent.width, parent.height);
	}
	
	
   public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] {}; 
   }

   //	 dimensions
	int dim ;
	int num = 50;
	int time = 0;

//	 object array
	Disc[] discs;

//	 initialization
	public void setup() {
	  // size(dim,dim); //,P3D);
//	  size(dim,dim,P3D);
      ellipseMode(CENTER_RADIUS);
//	  background(0);
      framerate(5);

	  // make some discs
	  discs = new Disc[num];
	  
	  // arrange in anti-collapsing circle
	  for (int i=0;i<num;i++) {
	    float fx = 0.4f*dim*cos(TWO_PI*i/num);
	    float fy = 0.4f*dim*sin(TWO_PI*i/num);
	    float x = random(dim/2) + fx;
	    float y = random(dim/2) + fy;
	    float r = 5+random(45);
	    int bt = 1;
	    if (random(100)<50) bt=-1;
	    discs[i] = new Disc(i,x,y,bt*fx/1000.0f,bt*fy/1000.0f,r);
	  }
	}

//	 main
	public void draw() {
      background(0);
      
 	  // move discs
	  for (int c=0;c<num;c++) {
		    discs[c].render();
		    discs[c].renderPxRiders();
	    discs[c].move();
	  }
	  time++;
	}

//	 disc object
	class Disc {
	  // index identifier
	  int id;
	  // position
	  float x,y;
	  // radius
	  float r, dr;
	  // velocity
	  float vx,vy;

	  //  pixel riders  
	  int numr;
	  int maxr = 40;
	  PxRider[] pxRiders;

	  Disc(int Id, float X, float Y, float Vx, float Vy, float R) {
	    // construct
	    id=Id;
	    x=X;
	    y=Y;
	    vx=Vx;
	    vy=Vy;
	    dr=R;
	    r=0;
	    
	    numr = (int)(R/1.62);
	    if (numr>maxr) numr=maxr;
	    
	    // create pixel riders
	    pxRiders = new PxRider[maxr];
	    for (int n=0;n<maxr;n++) {
	      pxRiders[n] = new PxRider();
	    }
	  }

	  void draw() {
	    stroke(0,50);
	    noFill();
	    ellipse(x,y,r,r);
	  }

	  void render() {
	    // find intersecting points with all ascending discs
	    for (int n=id+1;n<num;n++) {
	      if (n!=id) {
	      // find distance to other disc
	      float dx = discs[n].x-x;
	      float dy = discs[n].y-y;
	      float d = sqrt(dx*dx+dy*dy);
	      // intersection test
	      if (d<(discs[n].r+r)) {
	        // complete containment test
	        if (d>abs(discs[n].r-r)) {
	          // find solutions
	          float a = (r*r - discs[n].r*discs[n].r + d*d ) / (2*d);
	          
	          float p2x = x + a*(discs[n].x - x)/d;
	          float p2y = y + a*(discs[n].y - y)/d;
	          
	          float h = sqrt(r*r - a*a);
	          
	          float p3ax = p2x + h*(discs[n].y - y)/d;
	          float p3ay = p2y - h*(discs[n].x - x)/d;
	          
	          float p3bx = p2x - h*(discs[n].y - y)/d;
	          float p3by = p2y + h*(discs[n].x - x)/d;
	          
	          // P3a and P3B may be identical - ignore this case (for now)
	          stroke(255);
	          point(p3ax,p3ay);
	          point(p3bx,p3by);
	        }
	      }
	      }
	    }
	  }

	  void move() {
	    // add velocity to position
	    x+=vx;
	    y+=vy;
	    // bound check
	    if (x+r<0) x+=dim+r+r;
	    if (x-r>dim) x-=dim+r+r;
	    if (y+r<0) y+=dim+r+r;
	    if (y-r>dim) y-=dim+r+r;

	    // increase to destination radius
	    if (r<dr) {
	      r+=0.1;
	    }
	  }
	  
	  void renderPxRiders() {
	    for (int n=0;n<numr;n++) {
	      pxRiders[n].move(x,y,r);
	    }
	  }

	  // pixel rider object  
	  class PxRider {
	    float t;
	    float vt;
	    int mycharge;
	    
	    PxRider() {
	      t=random(TWO_PI);
	      vt=0.0f;
	    }
	    
	    void move(float x, float y, float r) {
	      // add velocity to theta
	      t=(t+vt+PI)%TWO_PI - PI;
	      
	      vt+=random(-0.001f,0.001f);
	    
	      // apply friction brakes
	      if (abs(vt)>0.02) vt*=0.9;

	      // draw      
	      float px = x+r*cos(t);
	      float py = y+r*sin(t);
	      int c = get((int)px,(int)py);
	      if (brightness(c)>48) {
	        glowpoint(px,py);
	        mycharge = 164;
	      } else {
	        stroke(mycharge);
	        point(px,py);
	        mycharge*=0.98;
	      }

	    }
	  }
	}


//	 methods
	void glowpoint(float px, float py) {
	  for (int i=-2;i<3;i++) {
	    for (int j=-2;j<3;j++) {
	      float a = 0.8f - i*i*0.1f - j*j*0.1f;
	      stroke(255,a*256);
	      point(px+i,py+j);
	    }
	  }
	}


}
