package bl0rg.vj.midi;

import java.lang.reflect.Method;

import promidi.Note;

class NoteParamCallback extends MidiCallback {
	int notePitch;

	NoteParamCallback(Object app, String methodName, int notePitch)
			throws Exception {
		super(app, methodName);
		this.notePitch = notePitch;
	}

	public boolean checkParams(Method method) {
		Class[] methodParams = method.getParameterTypes();
		if ((methodParams.length == 1)
				&& (methodParams[0].getName().equals("promidi.Note"))) {
			return true;
		} else {
			return false;
		}
	}

	void eventOn(Note note) {
		if ((notePitch < 0) || (notePitch == note.getPitch())) {
			try {
				method.invoke(app, new Object[] { note });
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error on calling plug: "
						+ methodName);
			}
		}
	}
}
