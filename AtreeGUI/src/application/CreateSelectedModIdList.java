package application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import application.beans.ModulesGroupPropertyBean;

public class CreateSelectedModIdList {

	static DocumentBuilderFactory factory;
	static String modulesFolderMapFile = ConfigVariables.MODTYNFLDMAP_XMLFILE;
	static File inputFile = new File(modulesFolderMapFile);
	static DocumentBuilder builder;
	static Document doc;
	static CreateSelectedModIdList obj = new CreateSelectedModIdList();
	String ModId;
	Map<String, Boolean> tmp = new HashMap<String, Boolean>();

	private CreateSelectedModIdList() {

	}

	public static CreateSelectedModIdList getInstance() {
		if (obj == null) {
			obj = new CreateSelectedModIdList();
		}
		return obj;
	}

	public void createSelModIdList(ArrayList<String> moduleTypeArrayList) {
		String ModGrpId = null;
		tmp.putAll(ConfigVariables.selModIdList);
		ConfigVariables.selModIdList.clear();
		KeyValueMapping keyValMap = KeyValueMapping.getInstance();
		Map<String, ModulesGroupPropertyBean> modGrpMap = keyValMap.getModulesGroupPropertyBean();
		for (String moduleType : moduleTypeArrayList) {
			ModGrpId = modGrpMap.get(moduleType).getModuleGrpId();
			if (!tmp.containsKey(ModGrpId)) {
				ConfigVariables.selModIdList.put(ModGrpId, false);
			} else {
				ConfigVariables.selModIdList.put(ModGrpId, true);
			}
		}
	}
}
