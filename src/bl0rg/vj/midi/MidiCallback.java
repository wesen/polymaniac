package bl0rg.vj.midi;

import java.lang.reflect.*;

import promidi.*;

class MidiCallback {
	Object app;

	String methodName;

	Method method;

	MidiCallback(Object app, String methodName) throws Exception {
		this.app = app;
		this.methodName = methodName;
		this.method = getCallbackMethod(methodName);
	}

	Object getApp() {
		return app;
	}

	// find the callbackmethod for the current callback class
	public Method getCallbackMethod(String methodName) throws Exception {
		Class appClass = app.getClass();
		Method[] appMethods = appClass.getDeclaredMethods();
		for (int i = 0; i < appMethods.length; i++) {
			if (appMethods[i].getName().equals(methodName)) {
				if (checkParams(appMethods[i])) {
					return appMethods[i];
				}
			}
		}
		throw new Exception();
	}

	public boolean checkParams(Method method) {
		return hasNoParams(method);
	}

	public boolean hasNoParams(Method method) {
		Class[] methodParams = method.getParameterTypes();
		return (methodParams.length == 0);
	}

	void eventOn(MidiEvent event) {
		System.out.println("empty note on");
	}
}



