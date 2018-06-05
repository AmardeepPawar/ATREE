package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.beans.ModulePropertyBean;
import application.beans.ModulesGroupPropertyBean;
import application.moduleJarHandle.CreateInstanceOfDesignClass;
import application.moduleJarHandle.LoadModuleJars;
import javafx.scene.image.Image;

public class SetModuleProperty {

	static DocumentBuilderFactory factory;
	static DocumentBuilder builder;
	static Document doc;
	Attr moduleTypeAttr;
	String modId;
	String jarFileNm;
	String setUpClassNm;
	CreateInstanceOfDesignClass desgnClsLod = new CreateInstanceOfDesignClass();
	LoadModuleJars ldModJar = new LoadModuleJars();
	public static List<String> jarNotFnd = new ArrayList<String>();

	public String[] readFileList(String folderName) {
		String[] list = null;
		try {
			File dir = new File(folderName);
			list = dir.list();
		} catch (Exception c) {
			System.out.println(c);
		}
		return list;
	}

	public void setModuleProperty(String modGrpId) {
		KeyValueMapping keyValMap = KeyValueMapping.getInstance();
		Map<String, ModulePropertyBean> modMap = keyValMap.getModulePropertyBean();
		Map<String, ModulesGroupPropertyBean> modGrpMap = keyValMap.getModulesGroupPropertyBean();
		String fileName = ReadConfigurationFileClass.getConfigFileObj().getPropertyValue("MODULES_FOLDER") + "//"
				+ modGrpMap.get(modGrpId).getModuleGrpFldr() + "//" + "ModulesMenifest.xml";
		File inputFile = new File(fileName);

		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = builder.parse(inputFile);
			doc.getDocumentElement().normalize();
			Node rootElement = doc.getDocumentElement();
			NodeList moduleTypeList = rootElement.getChildNodes();
			for (int i = 0; i < moduleTypeList.getLength(); i++) {
				if (moduleTypeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					ModulePropertyBean modProp = new ModulePropertyBean();
					Node ModuleType = moduleTypeList.item(i);
					moduleTypeAttr = (Attr) ModuleType.getAttributes().getNamedItem("id");
					modId = modGrpId + "::" + moduleTypeAttr.getValue();
					modProp.setModId(moduleTypeAttr.getValue());
					moduleTypeAttr = (Attr) ModuleType.getAttributes().getNamedItem("name");
					modProp.setModName(moduleTypeAttr.getValue());
					moduleTypeAttr = (Attr) ModuleType.getAttributes().getNamedItem("jarFile");
					jarFileNm = moduleTypeAttr.getValue();
					modProp.setJarFileName(jarFileNm);
					moduleTypeAttr = (Attr) ModuleType.getAttributes().getNamedItem("icon");
					modProp.setIcon(moduleTypeAttr.getValue());
					Image moduleIcon = new Image(
							"file:" + ConfigVariables.modulesFldr + "//" + modGrpMap.get(modGrpId).getModuleGrpFldr()
									+ "//ModuleIcons//" + moduleTypeAttr.getValue());
					modProp.setImgOfModule(moduleIcon);
					moduleTypeAttr = (Attr) ModuleType.getAttributes().getNamedItem("setUpClassPath");
					modProp.setStartModuleSetupClassName(moduleTypeAttr.getValue());
					setUpClassNm = moduleTypeAttr.getValue();
					moduleTypeAttr = (Attr) ModuleType.getAttributes().getNamedItem("executionClassPath");
					modProp.setStartModuleExecutionClassName(moduleTypeAttr.getValue());
					modProp.setModGrpId(modGrpId);
					

					
					try {
						modProp.setClassLdrObj(ldModJar.loadJars(modGrpId, jarFileNm));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					modProp.setStartModuleSetupObj(
							desgnClsLod.createSetUpClassInstance(setUpClassNm, modProp.getClassLdrObj()));
					modMap.put(modId, modProp);
				}
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			jarNotFnd.add(inputFile.toString());
		}
	}
}
