package application;

import java.util.HashMap;
import java.util.Map;

public class ConfigVariables {
	// selModIdList contains list of module selected on selection window
	public static Map<String,Boolean> selModIdList = new HashMap<String,Boolean>();
	// MODTYNFLDMAP_XMLFILE contains xml file name for module type map 
	public static String MODTYNFLDMAP_XMLFILE;
	// modulesFldr contains module folder name
	public static String modulesFldr;
	
	public static String getModulesFldr() {
		return modulesFldr;
	}
	public static void setModulesFldr(String modulesFldr) {
		ConfigVariables.modulesFldr = modulesFldr;
	}
	public static Map<String,Boolean> getSelModIdList() {
		return selModIdList;
	}
	public static void setSelModIdList(Map<String,Boolean> selModIdList) {
		ConfigVariables.selModIdList = selModIdList;
	}
	
	public static String getMODTYNFLDMAP_XMLFILE() {
		return MODTYNFLDMAP_XMLFILE;
	}
	public static void setMODTYNFLDMAP_XMLFILE(String MODTYNFLDMAPXMLFILE) {
		MODTYNFLDMAP_XMLFILE = MODTYNFLDMAPXMLFILE;
	}
}
