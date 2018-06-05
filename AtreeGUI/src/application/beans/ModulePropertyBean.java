package application.beans;

import atreeInterfaces.ModuleSetup;
import javafx.scene.image.Image;

public class ModulePropertyBean {
	ClassLoader classLdrObj;
	ModuleSetup startModuleSetupObj;
	Object startModuleExecutionObj;
	String Icon;
	String modId;
	String modName;	
	String jarFileName;
	String modGrpId;
	String startModuleSetupClassName;
	String startModuleExecutionClassName;
	Image imgOfModule;

	public Image getImgOfModule() {
		return imgOfModule;
	}
	public void setImgOfModule(Image imgOfModule) {
		this.imgOfModule = imgOfModule;
	}
	public String getModGrpId() {
		return modGrpId;
	}
	public void setModGrpId(String modGrpId) {
		this.modGrpId = modGrpId;
	}
	public String getJarFileName() {
		return jarFileName;
	}
	public void setJarFileName(String jarFileName) {
		this.jarFileName = jarFileName;
	}
	public String getIcon() {
		return Icon;
	}
	public void setIcon(String icon) {
		Icon = icon;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModName() {
		return modName;
	}
	public void setModName(String modName) {
		this.modName = modName;
	}

	public ClassLoader getClassLdrObj() {
		return classLdrObj;
	}
	public void setClassLdrObj(ClassLoader classLdrObj) {
		this.classLdrObj = classLdrObj;
	}
	public ModuleSetup getStartModuleSetupObj() {
		return startModuleSetupObj;
	}
	public void setStartModuleSetupObj(ModuleSetup startModuleSetupObj) {
		this.startModuleSetupObj = startModuleSetupObj;
	}
	public Object getStartModuleExecutionObj() {
		return startModuleExecutionObj;
	}
	public void setStartModuleExecutionObj(Object startModuleExecutionObj) {
		this.startModuleExecutionObj = startModuleExecutionObj;
	}
	public String getStartModuleSetupClassName() {
		return startModuleSetupClassName;
	}
	public void setStartModuleSetupClassName(String startModuleSetupClassName) {
		this.startModuleSetupClassName = startModuleSetupClassName;
	}
	public String getStartModuleExecutionClassName() {
		return startModuleExecutionClassName;
	}
	public void setStartModuleExecutionClassName(String startModuleExecutionClassName) {
		this.startModuleExecutionClassName = startModuleExecutionClassName;
	}

}
