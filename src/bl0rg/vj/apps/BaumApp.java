package bl0rg.vj.apps;

import bl0rg.vj.*;
import bl0rg.vj.midi.MidiHandler;
import bl0rg.vj.reflection.MidiAppMapping;
import bl0rg.vj.reflection.MidiAppNoteMapping;
import processing.core.*;
import traer.physics.*;
import traer.animation.*;

public class BaumApp extends MidiApp {
	final float NODE_SIZE = 10;
	final float EDGE_LENGTH = 20;
	final float EDGE_STRENGTH = 0.2f;
	final float SPACER_STRENGTH = 1000;

	ParticleSystem physics;
	Smoother3D centroid;

	public void keyReleased() {
		addNode();
	}
	
	public BaumApp(PApplet parent, MidiHandler midiHandler, MidiAppMapping[] mappings) {
		super(parent, midiHandler, mappings);
		physics = new ParticleSystem(0, 0.25f);
		centroid = new Smoother3D(0.8f);

		initialize();
	}
	
   public static MidiAppMapping[] getDefaultMappings() {
		return new MidiAppMapping[] { 
				new MidiAppNoteMapping("addNode", "", MidiHandler.MIDI_DRUM_CHANNEL, -1),
				new MidiAppNoteMapping("initialize", "", MidiHandler.MIDI_BASS_CHANNEL, -1) };
   }

	public synchronized void draw() {
		ellipseMode(CENTER);
		// println("physics draw");
		physics.tick(1.0f);
		if (physics.numberOfParticles() > 1)
			updateCentroid();
		centroid.tick();

//		background(255);
		// background(0xffffffff);
		translate(width / 2, height / 2);
		scale(centroid.z());
		translate(-centroid.x(), -centroid.y());

		drawNetwork();
	}

	// PROCESSING /////////////////////////////////////

	void drawNetwork() {
		// draw vertices
		fill(160);
		noStroke();
		for (int i = 0; i < physics.numberOfParticles(); ++i) {
			// check if i has not modified itself due to event from midi
			Particle v = physics.getParticle(i);
			ellipse(v.position().x(), v.position().y(), NODE_SIZE, NODE_SIZE);
		}

		// draw edges 
		stroke(0);
		beginShape(LINES);
		for (int i = 0; i < physics.numberOfSprings(); ++i) {
			Spring e = physics.getSpring(i);
			Particle a = e.getOneEnd();
			Particle b = e.getTheOtherEnd();
			vertex(a.position().x(), a.position().y());
			vertex(b.position().x(), b.position().y());
		}
		endShape();

	}

	// ME ////////////////////////////////////////////

	void updateCentroid() {
		float xMax = Float.NEGATIVE_INFINITY, xMin = Float.POSITIVE_INFINITY, yMin = Float.POSITIVE_INFINITY, yMax = Float.NEGATIVE_INFINITY;

		for (int i = 0; i < physics.numberOfParticles(); ++i) {
			Particle p = physics.getParticle(i);
			xMax = max(xMax, p.position().x());
			xMin = min(xMin, p.position().x());
			yMin = min(yMin, p.position().y());
			yMax = max(yMax, p.position().y());
		}
		float deltaX = xMax - xMin;
		float deltaY = yMax - yMin;
		if (deltaY > deltaX)
			centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, height
					/ (deltaY + 50));
		else
			centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, width
					/ (deltaX + 50));
	}

	void addSpacersToNode(Particle p, Particle r) {
		for (int i = 0; i < physics.numberOfParticles(); ++i) {
			Particle q = physics.getParticle(i);
			if (p != q && p != r)
				physics.makeAttraction(p, q, -SPACER_STRENGTH, 20);
		}
	}

	void makeEdgeBetween(Particle a, Particle b) {
		physics.makeSpring(a, b, EDGE_STRENGTH, EDGE_STRENGTH, EDGE_LENGTH);
	}

	public synchronized void initialize() {
		physics.clear();
		physics.makeParticle();
		centroid.setValue(0, 0, 1.0f);
	}

	public synchronized void addNode() {
		Particle p = physics.makeParticle();
		Particle q = physics.getParticle((int) random(0, physics
				.numberOfParticles() - 1));
		while (q == p)
			q = physics.getParticle((int) random(0,
					physics.numberOfParticles() - 1));
		addSpacersToNode(p, q);
		makeEdgeBetween(p, q);
		p.moveTo(q.position().x() + random(-1, 1), q.position().y()
				+ random(-1, 1), 0);
	}

}