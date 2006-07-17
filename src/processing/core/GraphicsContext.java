package processing.core;

public class GraphicsContext {
	PGraphics g;
	public void GraphicsContext(PGraphics g) {
		this.g = g;
	}
	

	public float m00, m01, m02, m03;
	public float m10, m11, m12, m13;
	public float m20, m21, m22, m23;
	public float m30, m31, m32, m33;
	  
	public void saveTranslateContext() {
		m00 = g.m00;
		m01 = g.m01;
		m02 = g.m02;
		m03 = g.m03;
		m10 = g.m10;
		m11 = g.m11;
		m12 = g.m12;
		m13 = g.m13;
		m20 = g.m20;
		m21 = g.m21;
		m22 = g.m22;
		m23 = g.m23;
		m30 = g.m30;
		m31 = g.m31;
		m32 = g.m32;
		m33 = g.m33;
	}
	
	public void restoreTranslateContext() {
		g.m00 = m00;
		g.m01 = m01;
		g.m02 = m02;
		g.m03 = m03;
		g.m10 = m10;
		g.m11 = m11;
		g.m12 = m12;
		g.m13 = m13;
		g.m20 = m20;
		g.m21 = m21;
		g.m22 = m22;
		g.m23 = m23;
		g.m30 = m30;
		g.m31 = m31;
		g.m32 = m32;
		g.m33 = m33;
	}
	
}
