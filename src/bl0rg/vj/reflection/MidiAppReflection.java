package bl0rg.vj.reflection;


import java.lang.reflect.*;
import java.util.*;

import bl0rg.vj.MidiApp;
import bl0rg.vj.midi.MidiHandler;

import processing.core.PApplet;

public class MidiAppReflection {
	
	static List appLoaderList = new ArrayList();
	
	public static void loadAppClasses(String path) {
		AppLoader appLoader = new AppLoader(path);
		appLoader.loadApps();
		appLoaderList.add(appLoader);
	}
	
	// STATIC
	public static Iterator listClasses(ClassLoader CL)
			throws NoSuchFieldException, IllegalAccessException {
		Class CL_class = CL.getClass();
		while (CL_class != java.lang.ClassLoader.class) {
			CL_class = CL_class.getSuperclass();
		}
		java.lang.reflect.Field ClassLoader_classes_field = CL_class
				.getDeclaredField("classes");
		ClassLoader_classes_field.setAccessible(true);
		Vector classes = (Vector) ClassLoader_classes_field.get(CL);
		return classes.iterator();
	}	
	
	// CLASS
	
	Class midiAppClass;
	
	public MidiAppReflection(String className) throws ClassNotFoundException {
		this(Class.forName("bl0rg.vj.apps." + className));
	}
	
	public MidiAppReflection(Class appClass) {
		this.midiAppClass = appClass;
	}
	
	public String getName() {
		return midiAppClass.getName();
	}
	
	public String[] getParameters() {
		return null;
	}
	
	Method[] getMethods(String prefix) {
		ArrayList methodList = new ArrayList();
		Method[] methods = midiAppClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (name.startsWith(prefix))
					methodList.add(methods[i]);
		}
		Method []methodsArray = new Method[methodList.size()];
		for (int i = 0; i < methodsArray.length; i++) {
			methodsArray[i] = (Method)methodList.get(i);
		}
		return methodsArray;
	}
	
	String[] getMethodNames(String prefix) {
		ArrayList methodList = new ArrayList();
		Method[] methods = midiAppClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (name.startsWith(prefix))
					methodList.add(name);
		}
		String []methodsArray = new String[methodList.size()];
		for (int i = 0; i < methodsArray.length; i++) {
			methodsArray[i] = (String)methodList.get(i);
		}
		return methodsArray;
	}
	
	public String[] getParameterNames() {
		String []paramMethods = getMethodNames("getParam");
		String []result = new String[paramMethods.length];
		for (int i = 0; i < paramMethods.length; i++) {
			result[i] = paramMethods[i].substring(8);
		}
		return result;
	}
	
	public String[] getEventMethodNames() {
		return getMethodNames("event");
	}
	
	public String[] getParamMethodNames() {
		return getMethodNames("setParam");
	}
	
	public static Object getParameter(Object obj, String parameter) {
		if (!(obj instanceof MidiApp)) { 
			return null;
		}
		Class midiAppClass = obj.getClass();
		Method method;
		try {
			method = midiAppClass.getMethod("getParam" + parameter, new Class[] {});
			return method.invoke(obj, new Object[] {});
		} catch (Exception e) {
			System.out.println("COuld not get parameter " +parameter + " of object " + obj);
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setParameter(Object obj, String parameter, Object value) {
		if (!(obj instanceof MidiApp)) {
			System.out.println(obj + " is not a midiapp");
			return;
		}
		Class midiAppClass = obj.getClass();
		Class valueClass = value.getClass();
		Method method;
		try {
			method = midiAppClass.getMethod("setParam" + parameter, new Class[] { valueClass });
			method.invoke(obj, new Object[] { value });
		} catch (Exception e) {
			System.out.println("COuld not set parameter " +parameter + " of object " + obj + " to value " + value + " of class " + valueClass);
			e.printStackTrace();
			return;
		}
		
	}
	
	public MidiApp getClassInstance(PApplet parent, MidiHandler midiHandler, MidiAppMapping mappings[]) {
		try {
			Constructor constructor = midiAppClass.getConstructor(new Class[] { parent.getClass(), midiHandler.getClass() });
			MidiApp newInstance = (MidiApp)constructor.newInstance(new Object[] { parent, midiHandler});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public MidiAppMapping[] getDefaultMappings() {
		try {
		Method method = midiAppClass.getMethod("getDefaultMappings", new Class[] {});
		return (MidiAppMapping[])method.invoke(null, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
			return new MidiAppMapping[] {};
		}
	}
	
	public static Iterator findAllLoadedMidiApps() {
		ArrayList appList = new ArrayList();
		try {
			ClassLoader myCL = MidiAppReflection.class.getClassLoader();
			while (myCL != null) {
				System.out.println("ClassLoader: bla  " + myCL);
				for (Iterator iter = listClasses(myCL); iter.hasNext();) {
					//String name = (String)iter.next();
					Class classObj = (Class)iter.next();
					String name = classObj.getName();
					if (name.startsWith("bl0rg.vj.apps.")) {
						appList.add(new MidiAppReflection(classObj));
					}
				}
				myCL = myCL.getParent();
			}
		} catch (Exception e) {
		}
		
		return appList.iterator();
	}
}
