package bl0rg.vj.midi;

import java.lang.reflect.Method;

import promidi.Controller;

class CcParamCallback extends MidiCallback {
	int ccNumber;

	CcParamCallback(Object app, String methodName, int ccNumber)
			throws Exception {
		super(app, methodName);
		this.ccNumber = ccNumber;
	}

	public boolean checkParams(Method method) {
		Class[] methodParams = method.getParameterTypes();
		if ((methodParams.length == 1)
				&& (methodParams[0].getName().equals("promidi.Controller"))) {
			return true;
		} else {
			return false;
		}
	}

	void eventOn(Controller cc) {
		if ((ccNumber < 0) || (ccNumber == cc.getNumber())) {
			try {
				method.invoke(app, new Object[] { cc });
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error on calling plug: "
						+ methodName);
			}
		}
	}
}
