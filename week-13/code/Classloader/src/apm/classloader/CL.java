package apm.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class CL {
	
	static Class<?> newClass(String path, String classname) throws Exception {
		URL url = new File(path).toURI().toURL();
		URLClassLoader cl = new URLClassLoader(new URL[] {url});
		Class<?> c = cl.loadClass(classname);
		cl.close();
		return c;
	}
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		newClass("/", "apm.classloader.Singleton");
		newClass("/", "apm.classloader.Singleton");
		System.out.println("done");
	}

}
