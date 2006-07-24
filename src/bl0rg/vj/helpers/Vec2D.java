/**
 * 
 */
package bl0rg.vj.helpers;

import processing.core.PApplet;

public class Vec2D {
	public float x,y;
	
	public Vec2D(float _x,float _y) {
		x=_x;
		y=_y;
	}
	
	public Vec2D(Vec2D v) {
		x=v.x;
		y=v.y;
	}
	
	public void set(float _x,float _y) {
		x=_x;
		y=_y;
	}
	
	public void set(Vec2D v) {
		x=v.x;
		y=v.y;
	}
	
	public void add(float _x,float _y) {
		x+=_x;
		y+=_y;
	}
	
	public void add(Vec2D v) {
		x+=v.x;
		y+=v.y;
	}
	
	public void sub(float _x,float _y) {
		x-=_x;
		y-=_y;
	}
	
	public void sub(Vec2D v) {
		x-=v.x;
		y-=v.y;
	}
	
	public void mult(float m) {
		x*=m;
		y*=m;
	}
	
	public void div(float m) {
		x/=m;
		y/=m;
	}
	
	public float length() {
		return PApplet.sqrt(x*x+y*y);
	}
	
	public float angle() {
		return (float)Math.atan2(y,x);
	}
	
	public void normalise() {
		float l=length();
		if(l!=0) {
			x/=l;
			y/=l;
		}
	}
	
	public Vec2D tangent() {
		return new Vec2D(-y,x);
	}
	
	public void rotate(float val) {
		// Due to float not being precise enough, double is used for the calculations
		double cosval=Math.cos(val);
		double sinval=Math.sin(val);
		double tmpx=x*cosval - y*sinval;
		double tmpy=x*sinval + y*cosval;
		
		x=(float)tmpx;
		y=(float)tmpy;
	}
}