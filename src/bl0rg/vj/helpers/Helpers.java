package bl0rg.vj.helpers;

import java.util.Random;

import bl0rg.vj.MidiApp;

public class Helpers {

	public static int[] getRandomPosition(MidiApp app) {
		return new int[] { (int)app.random(app.width), (int)app.random(app.height) };
	}
	
	public static int[] getRandomPosition(MidiApp app, int[] margins) {
		return new int[] { (int)app.random(app.width - 2 * margins[0]) + margins[0],
				(int)app.random(app.height - 2 * margins[1]) + margins[1] };
	}
}
