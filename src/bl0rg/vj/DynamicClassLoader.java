package bl0rg.vj;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;

//import java.io.*;

public class DynamicClassLoader {
	PolymaniacClassLoader myCL;
	
	class ClassFilenameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			if (name.endsWith(".class")) return true;
			else return (new File(dir, name)).isDirectory();
		}
	}
	
	class PolymaniacClassLoader extends ClassLoader {
		
		public Class readClass(String filename) {
			Class c;
			try {
				File file = new File(filename);
				FileReader in = new FileReader(file);
				long l = file.length();
				char cBuf[] = new char[(int)l + 1];
				in.read(cBuf, 0, (int)l);
				byte []buf = (new String(cBuf)).getBytes();
				c = defineClass(filename, buf, 0, (int)l);
				return c; 
			} catch (Throwable e) {
				System.err.println("Could not load " + filename);
				return null;
			}
		}
			
		
	}
	
	DynamicClassLoader() {
		myCL = new PolymaniacClassLoader();
	}
	
	public Class[] loadClassDirectory(String directory) {
		ArrayList loadedClasses = new ArrayList();
		File dir = new File(directory);
		FilenameFilter filter = new ClassFilenameFilter();
		String[] files = dir.list(filter);
		for (int i = 0; i < files.length; i++) {
			Class c = myCL.readClass(files[i]);
			if (c != null)
				loadedClasses.add(c);
		}
		Class []result = new Class[loadedClasses.size()];
		for (int i = 0; i < loadedClasses.size(); i++)
			result[i] = (Class)loadedClasses.get(i);
		return result;
	}
}
