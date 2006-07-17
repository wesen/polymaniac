package bl0rg.vj.midi;

import java.lang.reflect.Method;

import promidi.Note;

class NoteCallback extends NoteParamCallback {
	String methodOffName;

	Method methodOff;

	boolean midiArgument = false;

	NoteCallback(Object app, String methodOnName, String methodOffName,
			int notePitch) throws Exception {
		super(app, methodOnName, notePitch);
		this.methodOffName = methodOffName;
		if (!methodOffName.equals("")) {
			this.methodOff = getCallbackMethod(methodOffName);
		} else {
			this.methodOff = null;
		}
	}

	public boolean checkParams(Method method) {
		if (hasNoParams(method)) {
			this.midiArgument = false;
			return true;
		} else {
			this.midiArgument = true;
			return super.checkParams(method);
		}
	}

	void eventOn(Note note) {
		if ((notePitch < 0) || (notePitch == note.getPitch())) {
			if (note.getVelocity() == 0){
				if (methodOff != null) {
					try {
						if (midiArgument) {
							methodOff.invoke(app, new Object[] { note });
						} else {
							methodOff.invoke(app, new Object[] {});
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("Error on calling plug: "
								+ methodName);
					}
				}
			} else {
				if (method != null) {
					try {
						if (midiArgument) {
							method.invoke(app, new Object[] { note });
						} else {
							method.invoke(app, new Object[] {});
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("Error on calling plug: "
								+ methodName);
					}
				}
			}
		}
	}
}
