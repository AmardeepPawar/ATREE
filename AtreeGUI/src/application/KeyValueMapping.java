package application;

import java.util.HashMap;
import java.util.Map;

import application.beans.ModulePropertyBean;
import application.beans.ModulesGroupPropertyBean;
import javafx.scene.control.Tooltip;

public class KeyValueMapping {
	static KeyValueMapping clsObj = new KeyValueMapping();
	private KeyValueMapping()
	{		
	}
	
	public static KeyValueMapping getInstance()
	{
		if(clsObj == null )
		{
		  clsObj = new KeyValueMapping();
		}
		return clsObj;		
	}
	Map<String, Tooltip> keyAndTipMap = new HashMap<String, Tooltip>();
	public Map<String, Tooltip> getKeyAndTipMap() {
		return keyAndTipMap;
	}

	public void setKeyAndTipMap(Map<String, Tooltip> keyAndTipMap) {
		this.keyAndTipMap = keyAndTipMap;
	}
	Map<String,ModulesGroupPropertyBean> modulesGroupPropertyBean = new HashMap<String,ModulesGroupPropertyBean>();
	Map<String,ModulePropertyBean> modulePropertyBean = new HashMap<String,ModulePropertyBean>();

	public Map<String, ModulesGroupPropertyBean> getModulesGroupPropertyBean() {
		return modulesGroupPropertyBean;
	}

	public void setModulesGroupPropertyBean(Map<String, ModulesGroupPropertyBean> modulesGroupPropertyBean) {
		this.modulesGroupPropertyBean = modulesGroupPropertyBean;
	}

	public Map<String, ModulePropertyBean> getModulePropertyBean() {
		return modulePropertyBean;
	}

	public void setModulePropertyBean(Map<String, ModulePropertyBean> modulePropertyBean) {
		this.modulePropertyBean = modulePropertyBean;
	}
}
