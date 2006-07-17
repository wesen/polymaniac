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
	
	public String[] getEventMethods() {
		ArrayList methodList = new ArrayList();
		Method [] methods = midiAppClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (name.startsWith("event")) {
				methodList.add(name);
			}
		}
		return (String[])methodList.toArray();
	}
	
	public String[] getParamMethods() {
		ArrayList methodList = new ArrayList();
		Method [] methods = midiAppClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (name.startsWith("param")) {
				methodList.add(name);
			}
		}
		return (String[])methodList.toArray();
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
