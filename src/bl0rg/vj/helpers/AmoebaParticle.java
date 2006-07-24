/**
 * 
 */
package bl0rg.vj.helpers;

import processing.core.PApplet;

public class AmoebaParticle {
	/**
	 * 
	 */
	private final PApplet app;
	Vec2D v,vD;
	public float dir,dirMod,speed;
	public int age;
	int stateCnt;
	
	public AmoebaParticle(PApplet app) {
		this.app = app;
		v=new Vec2D(0,0);
		vD=new Vec2D(0,0);
		age=0;
	}
	
	public void init(int x, int y, float _dir) {
		dir=_dir;
		
		float prob=this.app.random(100);
		if(prob<80) age=15+(int)(this.app.random(30));
		else if(prob<98) age=45+(int)(this.app.random(30));
		else age=100+(int)(this.app.random(50));
		
		if(this.app.random(100)<80) speed=this.app.random(2)+0.5f;
		else speed=this.app.random(2)+2;
		
		if(this.app.random(100)<80) dirMod=20;
		else dirMod=60;
		
		v.set(x, y);
		initMove();
		dir=_dir;
		stateCnt=10;
	}
	
	void initMove() {
		if(this.app.random(100)>50) dirMod=-dirMod;
		dir+=dirMod;
		
		vD.set(speed,0);
		vD.rotate(PApplet.radians(dir+90));
		
		stateCnt=10+(int)(this.app.random(5));
		if(this.app.random(100)>90) stateCnt+=30;
	}
	
	public void update() {
		age--;
		
		if(age>=30) {
			vD.rotate(PApplet.radians(1));
			vD.mult(1.01f);
		}
		
		v.add(vD);
		this.app.fill(255-age,0,100,150);
		
		this.app.pushMatrix();
		this.app.translate(v.x,v.y);
		this.app.rotate(PApplet.radians(dir));
		this.app.rect(0,0,1,1);
		this.app.popMatrix();
		
		if(age==0) {
			if(this.app.random(100)>50) this.app.fill(200,0,0,200);
			else this.app.fill(00,200,255,200);
			float size=2+this.app.random(4);
			if(this.app.random(100)>95) size+=5;
			this.app.ellipse(v.x,v.y,size,size);
		}
		if(v.x<0 || v.x>this.app.width || v.y<0 || v.y>this.app.height) age=0;
		
		if(age<30) {
			stateCnt--;
			if(stateCnt==0) {
				initMove();
			}
		}
	} 
	
}