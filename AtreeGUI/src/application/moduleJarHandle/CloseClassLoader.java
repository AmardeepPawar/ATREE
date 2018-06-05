package application.moduleJarHandle;

import java.io.IOException;
import java.net.URLClassLoader;

import application.KeyValueMapping;
import application.beans.ModulePropertyBean;

public class CloseClassLoader {

	public void closeClassLoader(String moduleId) {
		KeyValueMapping mapper = KeyValueMapping.getInstance();
		ModulePropertyBean modulProp = mapper.getModulePropertyBean().get(moduleId);
		URLClassLoader clsLod = (URLClassLoader) modulProp.getClassLdrObj();
		try {
			clsLod.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modulProp.setClassLdrObj(null);
	}
}
