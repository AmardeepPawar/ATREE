package application.moduleJarHandle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import application.KeyValueMapping;
import application.beans.ModulePropertyBean;
import atreeInterfaces.*;

public class CreateInstanceOfDesignClass {
	
	@SuppressWarnings("unchecked")
	public void createSetUpClassInstance(String moduleId)
	{
		KeyValueMapping mapper = KeyValueMapping.getInstance();
		ModulePropertyBean modulProp = mapper.getModulePropertyBean().get(moduleId);
		String setupClassName = modulProp.getStartModuleSetupClassName();
		ClassLoader modClsLdr = modulProp.getClassLdrObj();
		Class<ModuleSetup> myClass = null;
		
		try {
			myClass = (Class<ModuleSetup>) modClsLdr.loadClass(setupClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Constructor<ModuleSetup> ctr = null;
		try {
			ctr = myClass.getConstructor();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModuleSetup object = null;
		try {
			object = (ModuleSetup) ctr.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modulProp.setStartModuleSetupObj(object);			
	}
	
	@SuppressWarnings("unchecked")
	public ModuleSetup createSetUpClassInstance(String className, ClassLoader cl)
	{
		String setupClassName = className;
		ClassLoader modClsLdr = cl;
		Class<ModuleSetup> myClass = null;
		System.out.println(className);
		try {
			myClass = (Class<ModuleSetup>) modClsLdr.loadClass(setupClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Constructor<ModuleSetup> ctr = null;
		try {
			ctr = myClass.getConstructor();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModuleSetup object = null;
		try {
			object = (ModuleSetup) ctr.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;		
	}

/*	public ReadParamFromFile createReadParamClassInstance(String className)
	{
		
	}

	public WriteSetupParamToFile createWriteParamClassInstance(String className)
	{
		
	}*/
}
