package application.moduleJarHandle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import application.ConfigVariables;

public class LoadModuleJars {
	URL url = null;
	URL[] urls;

	public ClassLoader loadJars(String moduleId, String jarName) throws MalformedURLException {
		String jarPath = ConfigVariables.modulesFldr + "/" + moduleId.split("::")[0] + "/ModuleJars/" + jarName;
		File file = new File(jarPath);
		url = null;
		urls = null;
		url = file.toURI().toURL();
		urls = new URL[] { url };
		ClassLoader cl = new URLClassLoader(urls);
		return cl;		
	}
}
